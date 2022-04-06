package com.matrictime.network.domain.impl;

import com.matrictime.network.api.modelVo.GroupVo;
import com.matrictime.network.api.request.GroupReq;
import com.matrictime.network.api.response.GroupResp;
import com.matrictime.network.dao.mapper.GroupMapper;
import com.matrictime.network.dao.model.Group;
import com.matrictime.network.dao.model.GroupExample;
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
    GroupMapper groupMapper;

    @Override
    public Integer createGroup(GroupReq groupReq) {
        if(groupReq.getGroupId()==null||groupReq.getOwner()==null){
            throw new SystemException("缺少参数");
        }
        Group group = new Group();
        BeanUtils.copyProperties(groupReq,group);
        return groupMapper.insertSelective(group);
    }

    @Override
    public Integer deleteGroup(GroupReq groupReq) {
        if(groupReq.getGroupId()==null){
            throw new SystemException("缺少ID参数");
        }
        Group group = new Group();
        BeanUtils.copyProperties(groupReq,group);
        group.setIsExist(false);
        return groupMapper.updateByPrimaryKeySelective(group);
    }

    @Override
    public Integer modifyGroup(GroupReq groupReq) {
        if(groupReq.getGroupId()==null){
            throw new SystemException("缺少ID参数");
        }
        Group group = new Group();
        BeanUtils.copyProperties(groupReq,group);
        return groupMapper.updateByPrimaryKeySelective(group);
    }

    @Override
    public GroupResp queryGroup(GroupReq groupReq) {
        GroupExample groupExample = new GroupExample();
        GroupExample.Criteria criteria = groupExample.createCriteria();
        if(groupReq.getOwner()!=null){
            criteria.andOwnerEqualTo(groupReq.getOwner());
        }
        if(groupReq.getGroupName()!=null){
            criteria.andGroupNameEqualTo(groupReq.getGroupName());
        }
        List<Group> groups = groupMapper.selectByExample(groupExample);
        List<GroupVo> groupVos = new ArrayList<>();
        for (Group group : groups) {
            GroupVo groupVo = new GroupVo();
            BeanUtils.copyProperties(group,groupVo);
            groupVos.add(groupVo);
        }
        GroupResp groupResp = new GroupResp();
        groupResp.setGroupVoList(groupVos);
        return groupResp;
    }
}
