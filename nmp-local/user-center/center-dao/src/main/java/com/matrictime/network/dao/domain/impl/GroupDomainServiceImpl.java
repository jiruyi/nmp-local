package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.api.modelVo.GroupVo;
import com.matrictime.network.api.request.GroupReq;
import com.matrictime.network.api.request.UserGroupReq;
import com.matrictime.network.dao.domain.GroupDomainService;
import com.matrictime.network.dao.mapper.GroupInfoMapper;
import com.matrictime.network.dao.mapper.UserGroupMapper;
import com.matrictime.network.dao.mapper.ext.UserGroupExtMapper;
import com.matrictime.network.dao.model.GroupInfo;
import com.matrictime.network.dao.model.GroupInfoExample;
import com.matrictime.network.dao.model.UserGroup;
import com.matrictime.network.dao.model.UserGroupExample;
import com.matrictime.network.exception.SystemException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupDomainServiceImpl implements GroupDomainService {
    @Autowired
    GroupInfoMapper groupMapper;
    @Autowired
    UserGroupMapper userGroupMapper;
    @Resource
    UserGroupExtMapper userGroupExtMapper;

    @Override
    @Transactional
    public Integer createGroup(GroupReq groupReq) {
        if(groupReq.getOwner()==null){
            throw new SystemException("缺少参数");
        }
        if(groupReq.getGroupName()==null||groupReq.getGroupName().equals("")){
            throw new SystemException("组名为空");
        }
        GroupInfoExample groupInfoExample = new GroupInfoExample();
        groupInfoExample.createCriteria().andGroupNameEqualTo(groupReq.getGroupName()).andOwnerEqualTo(groupReq.getOwner())
                .andIsExistEqualTo(true);
        List<GroupInfo> groupInfos = groupMapper.selectByExample(groupInfoExample);
        if(!CollectionUtils.isEmpty(groupInfos)){
            throw new SystemException("已有重复组名");
        }
        GroupInfo group = new GroupInfo();
        BeanUtils.copyProperties(groupReq,group);
        groupMapper.insertSelective(group);

        if(!CollectionUtils.isEmpty(groupReq.getUserIdList())){
            for (String s : groupReq.getUserIdList()) {
                UserGroup userGroup = new UserGroup();
                userGroup.setUserId(s);
                userGroup.setGroupId(String.valueOf(group.getGroupId()));
                userGroupMapper.insertSelective(userGroup);
            }
        }
        return group.getGroupId().intValue();
    }

    @Override
    @Transactional
    public Integer deleteGroup(GroupReq groupReq) {
        if(groupReq.getGroupId()==null){
            throw new SystemException("缺少ID参数");
        }
        GroupInfo group = new GroupInfo();
        BeanUtils.copyProperties(groupReq,group);
        group.setIsExist(false);
        int n = groupMapper.updateByPrimaryKeySelective(group);

        UserGroupExample userGroupExample = new UserGroupExample();
        userGroupExample.createCriteria().andGroupIdEqualTo(String.valueOf(groupReq.getGroupId())).andIsExistEqualTo(true);
        List<UserGroup> userGroupList = userGroupMapper.selectByExample(userGroupExample);
        for (UserGroup userGroup : userGroupList) {
            userGroup.setIsExist(false);
            UserGroupReq userGroupReq = new UserGroupReq();
            BeanUtils.copyProperties(userGroup,userGroupReq);
            userGroupExtMapper.updateByUserIdAndGroupId(userGroupReq);
        }
        return n;
    }

    @Override
    public Integer modifyGroup(GroupReq groupReq) {
        if(groupReq.getGroupId()==null){
            throw new SystemException("缺少ID参数");
        }
        if(groupReq.getGroupName()!=null){
            GroupInfo groupInfo = groupMapper.selectByPrimaryKey(groupReq.getGroupId());
            GroupInfoExample groupInfoExample = new GroupInfoExample();
            groupInfoExample.createCriteria().andGroupNameEqualTo(groupReq.getGroupName()).andOwnerEqualTo(groupInfo.getOwner())
                    .andIsExistEqualTo(true);
            List<GroupInfo> groupInfos = groupMapper.selectByExample(groupInfoExample);
            if(!CollectionUtils.isEmpty(groupInfos)){
                if(!groupInfos.get(0).getGroupId().equals(groupReq.getGroupId())){
                    throw new SystemException("已有重复组名");
                }
            }
        }
        GroupInfo group = new GroupInfo();
        BeanUtils.copyProperties(groupReq,group);
        return groupMapper.updateByPrimaryKeySelective(group);
    }

    @Override
    public List<GroupVo> queryGroup(GroupReq groupReq) {
        GroupInfoExample groupExample = new GroupInfoExample();
        GroupInfoExample.Criteria criteria = groupExample.createCriteria();
        if(groupReq.getOwner()!=null){
            criteria.andOwnerEqualTo(groupReq.getOwner());
        }
        if(groupReq.getGroupName()!=null){
            criteria.andGroupNameEqualTo(groupReq.getGroupName());
        }
        criteria.andIsExistEqualTo(true);
        List<GroupInfo> groups = groupMapper.selectByExample(groupExample);
        List<GroupVo> groupVos = new ArrayList<>();
        for (GroupInfo group : groups) {
            GroupVo groupVo = new GroupVo();
            BeanUtils.copyProperties(group,groupVo);
            groupVos.add(groupVo);
        }
        return groupVos;
    }
}
