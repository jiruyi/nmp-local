package com.matrictime.network.dao.domain.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.dao.domain.RoleDomainService;
import com.matrictime.network.dao.mapper.NmplMenuMapper;
import com.matrictime.network.dao.mapper.NmplRoleMapper;
import com.matrictime.network.dao.mapper.NmplRoleMenuRelationMapper;
import com.matrictime.network.dao.mapper.NmplUserMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.modelVo.NmplRoleVo;
import com.matrictime.network.request.RoleRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.response.RoleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@Slf4j
public class RoleDomainServiceImpl implements RoleDomainService {
    @Autowired
    NmplRoleMapper nmplRoleMapper;
    @Autowired
    NmplRoleMenuRelationMapper nmplRoleMenuRelationMapper;
    @Autowired
    NmplUserMapper nmplUserMapper;
    @Autowired
    NmplMenuMapper nmplMenuMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer save(RoleRequest roleRequest)throws Exception
    {
        NmplRole nmplRole = new NmplRole();
        BeanUtils.copyProperties(roleRequest,nmplRole);
        NmplRoleExample nmplRoleExample = new NmplRoleExample();
        nmplRoleExample.or().andRoleCodeEqualTo(roleRequest.getRoleCode());
        nmplRoleExample.or().andRoleNameEqualTo(roleRequest.getRoleName());
        List<NmplRole> nmplRoles = nmplRoleMapper.selectByExample(nmplRoleExample);
        if (!CollectionUtils.isEmpty(nmplRoles)){
            throw new SystemException("存在相同角色名称或角色编码");
        }
//        nmplRole.setCreateTime(new Date());
//        nmplRole.setUpdateTime(new Date());
        nmplRole.setIsExist(Byte.valueOf("1"));
        nmplRoleMapper.insertSelective(nmplRole);
        List<Long>menuList = new ArrayList<>();
        menuList = roleRequest.getMenuId();
        Integer result = 0;
        if (!CollectionUtils.isEmpty(menuList)){
            for (Long meduId : menuList) {
                NmplRoleMenuRelation nmplRoleMenuRelation = new NmplRoleMenuRelation();
                nmplRoleMenuRelation.setRoleId(nmplRole.getRoleId());
                nmplRoleMenuRelation.setMenuId(meduId);
                result = nmplRoleMenuRelationMapper.insert(nmplRoleMenuRelation);
            }
        }
        return result;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer delete(RoleRequest roleRequest)throws Exception {

        //判断该用户是否有关联用户
        List<NmplUser> users = new ArrayList<>();
        NmplUserExample nmplUserExample = new NmplUserExample();
        nmplUserExample.createCriteria().andRoleIdEqualTo(String.valueOf(roleRequest.getRoleId()));
        users = nmplUserMapper.selectByExample(nmplUserExample);
        if (!CollectionUtils.isEmpty(users)){
            throw  new SystemException("该角色有关联用户，无法删除");
        }

        NmplRole nmplRole = new NmplRole();
        nmplRole.setRoleId(roleRequest.getRoleId());
        nmplRole.setIsExist(Byte.valueOf("0"));
        nmplRole.setUpdateTime(new Date());
        nmplRole.setUpdateUser(roleRequest.getUpdateUser());
        return nmplRoleMapper.updateByPrimaryKeySelective(nmplRole);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer modify(RoleRequest roleRequest)throws Exception {
        Integer result = 0;
        //修改角色基本信息
        NmplRole nmplRole = new NmplRole();
        BeanUtils.copyProperties(roleRequest,nmplRole);
        if (roleRequest.getRoleName()!=null||roleRequest.getRoleCode()!=null){
            NmplRoleExample nmplRoleExample = new NmplRoleExample();
            nmplRoleExample.or().andRoleCodeEqualTo(roleRequest.getRoleCode());
            nmplRoleExample.or().andRoleNameEqualTo(roleRequest.getRoleName());
            List<NmplRole>nmplRoles = nmplRoleMapper.selectByExample(nmplRoleExample);
            if (!CollectionUtils.isEmpty(nmplRoles)){
                throw new SystemException("存在相同角色名称或角色编码");
            }
        }
        nmplRole.setUpdateTime(new Date());
        nmplRole.setUpdateUser(roleRequest.getUpdateUser());
        result = nmplRoleMapper.updateByPrimaryKeySelective(nmplRole);

        //将角色之前的权限删除 更新权限信息

        NmplRoleMenuRelationExample nmplRoleMenuRelationExample = new NmplRoleMenuRelationExample();
        nmplRoleMenuRelationExample.createCriteria().andRoleIdEqualTo(roleRequest.getRoleId());
        nmplRoleMenuRelationMapper.deleteByExample(nmplRoleMenuRelationExample);

        //新增该用户权限
        List<Long>menuList = new ArrayList<>();
        menuList = roleRequest.getMenuId();
        if (!CollectionUtils.isEmpty(menuList)){
            for (Long meduId : menuList) {
                NmplRoleMenuRelation nmplRoleMenuRelation = new NmplRoleMenuRelation();
                nmplRoleMenuRelation.setRoleId(nmplRole.getRoleId());
                nmplRoleMenuRelation.setMenuId(meduId);
                nmplRoleMenuRelationMapper.insert(nmplRoleMenuRelation);
            }
        }
        return result;
    }

    @Override
    public PageInfo<NmplRoleVo> queryByConditions(RoleRequest roleRequest) throws Exception{
        NmplRoleExample nmplRoleExample = new NmplRoleExample();
        NmplRoleExample.Criteria criteria = nmplRoleExample.createCriteria();
        if (!roleRequest.isAdmin()){
            criteria.andRoleIdNotEqualTo(DataConstants.SUPER_ADMIN);
        }
        if (roleRequest.getRoleName()!=null){
            criteria.andRoleNameEqualTo(roleRequest.getRoleName());
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (roleRequest.getStartTime() != null) {
            criteria.andCreateTimeGreaterThanOrEqualTo(sf.parse(roleRequest.getStartTime());
        }
        if(roleRequest.getEndTime() != null){
            criteria.andCreateTimeLessThanOrEqualTo(sf.parse(roleRequest.getEndTime());
        }
        criteria.andIsExistEqualTo(Byte.valueOf("1"));
        Page page = PageHelper.startPage(roleRequest.getPageNo(),roleRequest.getPageSize());
        List<NmplRole> nmplRoleList = nmplRoleMapper.selectByExample(nmplRoleExample);

        PageInfo<NmplRoleVo> pageResult =  new PageInfo<>();
        List<NmplRoleVo> nmplRoles = new ArrayList<>();
        for (NmplRole nmplRole : nmplRoleList) {
            NmplRoleVo role = new NmplRoleVo();
            BeanUtils.copyProperties(nmplRole,role);
            nmplRoles.add(role);
        }

        pageResult.setList(nmplRoles);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return pageResult;
    }

    @Override
    public RoleResponse queryOne(RoleRequest roleRequest)throws Exception {
        RoleResponse response = new RoleResponse();
        NmplRole nmplRole = nmplRoleMapper.selectByPrimaryKey(roleRequest.getRoleId());
        BeanUtils.copyProperties(nmplRole,response);

        if (nmplRole.getRoleId()!=DataConstants.SUPER_ADMIN){
            NmplRoleMenuRelationExample nmplRoleMenuRelationExample = new NmplRoleMenuRelationExample();
            nmplRoleMenuRelationExample.createCriteria().andRoleIdEqualTo(roleRequest.getRoleId());
            List<NmplRoleMenuRelation> relations = nmplRoleMenuRelationMapper.selectByExample(nmplRoleMenuRelationExample);
            for (NmplRoleMenuRelation relation : relations) {
                response.getMenuIds().add(relation.getMenuId());
            }
        }else {
            List<NmplMenu> menuList = nmplMenuMapper.selectByExample(null);
            for (NmplMenu nmplMenu : menuList) {
                response.getMenuIds().add(nmplMenu.getMenuId());
            }
        }
        System.out.println(response);
        return response;
    }
}
