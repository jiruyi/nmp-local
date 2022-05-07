package com.matrictime.network.domain.impl;

import com.matrictime.network.api.modelVo.GroupVo;
import com.matrictime.network.api.request.GroupReq;
import com.matrictime.network.api.response.GroupResp;
import com.matrictime.network.dao.mapper.GroupInfoMapper;
import com.matrictime.network.dao.model.GroupInfo;
import com.matrictime.network.dao.model.GroupInfoExample;
import com.matrictime.network.domain.GroupDomainService;
import com.matrictime.network.exception.SystemException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupDomainServiceImpl implements GroupDomainService {
    @Autowired
    GroupInfoMapper groupMapper;

    @Override
    public Integer createGroup(GroupReq groupReq) {
        if(groupReq.getOwner()==null){
            throw new SystemException("缺少参数");
        }
        GroupInfo group = new GroupInfo();
        BeanUtils.copyProperties(groupReq,group);
        groupMapper.insertSelective(group);
        return group.getGroupId().intValue();
    }

    @Override
    public Integer deleteGroup(GroupReq groupReq) {
        if(groupReq.getGroupId()==null){
            throw new SystemException("缺少ID参数");
        }
        GroupInfo group = new GroupInfo();
        BeanUtils.copyProperties(groupReq,group);
        group.setIsExist(false);
        return groupMapper.updateByPrimaryKeySelective(group);
    }

    @Override
    public Integer modifyGroup(GroupReq groupReq) {
        if(groupReq.getGroupId()==null){
            throw new SystemException("缺少ID参数");
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
