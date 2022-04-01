package com.matrictime.network.domain.impl;

import com.matrictime.network.api.request.GroupReq;
import com.matrictime.network.dao.mapper.GroupMapper;
import com.matrictime.network.dao.model.Group;
import com.matrictime.network.dao.model.GroupExample;
import com.matrictime.network.domain.GroupDomainService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupDomainServiceImpl implements GroupDomainService {
    @Autowired
    GroupMapper groupMapper;

    @Override
    public Integer createGroup(GroupReq groupReq) {
        Group group = new Group();
        BeanUtils.copyProperties(groupReq,group);
        return groupMapper.insertSelective(group);
    }

    @Override
    public Integer deleteGroup(GroupReq groupReq) {
        if(groupReq.getGroupId()==null){

        }
        Group group = new Group();
        BeanUtils.copyProperties(groupReq,group);
        group.setIsExist(false);
        return groupMapper.updateByPrimaryKeySelective(group);
    }

    @Override
    public Integer modifyGroup(GroupReq groupReq) {
        if(groupReq.getGroupId()==null){

        }
        Group group = new Group();
        BeanUtils.copyProperties(groupReq,group);
        return groupMapper.updateByPrimaryKeySelective(group);
    }

    @Override
    public List<Group> queryGroup(GroupReq groupReq) {
        GroupExample groupExample = new GroupExample();
        GroupExample.Criteria criteria = groupExample.createCriteria();
        if(groupReq.getOwner()!=null){
            criteria.andOwnerEqualTo(groupReq.getOwner());
        }
        if(groupReq.getGroupName()!=null){
            criteria.andGroupNameEqualTo(groupReq.getGroupName());
        }
        return groupMapper.selectByExample(groupExample);
    }
}
