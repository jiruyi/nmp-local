package com.matrictime.network.dao.domain.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.LoginStatusEnum;
import com.matrictime.network.dao.domain.RoleDomainService;
import com.matrictime.network.dao.mapper.NmplMenuMapper;
import com.matrictime.network.dao.mapper.NmplRoleMapper;
import com.matrictime.network.dao.mapper.NmplRoleMenuRelationMapper;
import com.matrictime.network.dao.mapper.NmplUserMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.modelVo.NmplMenuVo;
import com.matrictime.network.modelVo.NmplRoleVo;
import com.matrictime.network.request.RoleRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.response.RoleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${token.timeOut}")
    private Integer timeOut;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer save(RoleRequest roleRequest)throws Exception
    {
        NmplRole nmplRole = new NmplRole();
        BeanUtils.copyProperties(roleRequest,nmplRole);
        NmplRoleExample nmplRoleExample = new NmplRoleExample();
        nmplRoleExample.or().andRoleCodeEqualTo(roleRequest.getRoleCode()).andIsExistEqualTo((byte) 1);
        nmplRoleExample.or().andRoleNameEqualTo(roleRequest.getRoleName()).andIsExistEqualTo((byte) 1);
        List<NmplRole> nmplRoles = nmplRoleMapper.selectByExample(nmplRoleExample);
        if (!CollectionUtils.isEmpty(nmplRoles)){
            throw new SystemException("存在相同角色名称或权限字符");
        }

        nmplRole.setIsExist(Byte.valueOf("1"));
        nmplRoleMapper.insertSelective(nmplRole);
        List<Long>menuList = new ArrayList<>();
        menuList = roleRequest.getMenuId();
        menuList.add(63L);
        menuList = roleHandle(new HashSet<>(menuList));
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
        nmplUserExample.createCriteria().andRoleIdEqualTo(String.valueOf(roleRequest.getRoleId())).andIsExistEqualTo(true);
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
            nmplRoleExample.or().andRoleCodeEqualTo(roleRequest.getRoleCode()).andIsExistEqualTo((byte) 1);
            nmplRoleExample.or().andRoleNameEqualTo(roleRequest.getRoleName()).andIsExistEqualTo((byte) 1);
            List<NmplRole>nmplRoles = nmplRoleMapper.selectByExample(nmplRoleExample);
            if (!CollectionUtils.isEmpty(nmplRoles)){
                if(!nmplRoles.get(0).getRoleId().equals(roleRequest.getRoleId())){
                    throw new SystemException("存在相同角色名称或权限字符");
                }
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
        menuList.add(63L);
        menuList = roleHandle(new HashSet<>(menuList));
        if (!CollectionUtils.isEmpty(menuList)){
            for (Long meduId : menuList) {
                NmplRoleMenuRelation nmplRoleMenuRelation = new NmplRoleMenuRelation();
                nmplRoleMenuRelation.setRoleId(nmplRole.getRoleId());
                nmplRoleMenuRelation.setMenuId(meduId);
                nmplRoleMenuRelationMapper.insert(nmplRoleMenuRelation);
            }
        }
        NmplUserExample nmplUserExample = new NmplUserExample();
        nmplUserExample.createCriteria().andRoleIdEqualTo(String.valueOf(roleRequest.getRoleId())).andIsExistEqualTo(true);
        List<NmplUser> nmplUserList = nmplUserMapper.selectByExample(nmplUserExample);
        for (NmplUser user : nmplUserList) {
            if(redisTemplate.opsForValue().get(user.getUserId()+ DataConstants.USER_LOGIN_STATUS)!=null){
                redisTemplate.opsForValue().set(user.getUserId()+DataConstants.USER_LOGIN_STATUS,
                        LoginStatusEnum.UPDATE.getCode(),timeOut, TimeUnit.HOURS);
                redisTemplate.delete(user.getUserId()+ DataConstants.USER_LOGIN_JWT_TOKEN);
                Subject subject = (Subject) ThreadContext.get(user.getUserId()+"_SUBJECT_KEY");
                if(subject!=null){
                    subject.logout();
                }
            }
        }
        return result;
    }

    @Override
    public List<NmplRoleVo> queryByConditions(RoleRequest roleRequest) throws Exception{
        NmplRoleExample nmplRoleExample = new NmplRoleExample();
        NmplRoleExample.Criteria criteria = nmplRoleExample.createCriteria();
        if (!roleRequest.isAdmin()){
            criteria.andRoleIdNotEqualTo(DataConstants.SUPER_ADMIN);
        }
        if (roleRequest.getRoleName()!=null){
            criteria.andRoleNameLike("%"+roleRequest.getRoleName()+"%");
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (roleRequest.getStartTime() != null) {
            criteria.andCreateTimeGreaterThanOrEqualTo(sf.parse(roleRequest.getStartTime()));
        }
        if(roleRequest.getEndTime() != null){
            criteria.andCreateTimeLessThanOrEqualTo(sf.parse(roleRequest.getEndTime()));
        }
        criteria.andIsExistEqualTo(Byte.valueOf("1"));

        nmplRoleExample.setOrderByClause("create_time desc");
        List<NmplRole> nmplRoleList = nmplRoleMapper.selectByExample(nmplRoleExample);

        List<NmplRoleVo> nmplRoles = new ArrayList<>();

        //4.0.1----增加创建人信息
        List<NmplUser> nmplUserList = nmplUserMapper.selectByExample(null);
        Map<String,String> map = new HashMap<>();
        for (NmplUser user : nmplUserList) {
            map.put(String.valueOf(user.getUserId()),user.getNickName());
        }

        for (NmplRole nmplRole : nmplRoleList) {
            NmplRoleVo role = new NmplRoleVo();
            BeanUtils.copyProperties(nmplRole,role);
            role.setCreateUserName(map.getOrDefault(role.getCreateUser(),""));
            nmplRoles.add(role);
        }

        return nmplRoles;
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

    public List<Long> roleHandle(Set<Long> third){
        List<NmplMenuVo> menuList = nmplMenuMapper.queryAllMenu();
        Map<Long,Long> map = new HashMap<>();
        for (NmplMenuVo nmplMenuVo : menuList) {
            map.put(nmplMenuVo.getMenuId(),nmplMenuVo.getParentMenuId());
        }
        Set<Long> second = new HashSet<>();
        Set<Long> first = new HashSet<>();
        for (Long integer : third) {
            if(map.get(integer)==-1){
                continue;
            }
            if(second.contains(map.get(integer))){
                continue;
            }else {
                second.add(map.get(integer));
            }
        }
        for (Long integer : second) {
            if(map.get(integer)==-1){
                continue;
            }
            if(first.contains(map.get(integer))){
                continue;
            }else {
                first.add(map.get(integer));
            }
        }
        third.addAll(second);
        third.addAll(first);
        return new ArrayList<>(third);
    }


    @Override
    public List<NmplRoleVo> queryCreateRole(RoleRequest roleRequest) throws Exception {
        NmplRoleExample nmplRoleExample = new NmplRoleExample();
        NmplRoleExample.Criteria criteria = nmplRoleExample.createCriteria();
        //如果不是管理员用户则将管理员过滤
        if (roleRequest.getRoleId()== DataConstants.SUPER_ADMIN){
            roleRequest.setAdmin(true);
        }
        if(roleRequest.getRoleId()==DataConstants.COMMON_ADMIN){
            roleRequest.setAdmin(true);
            criteria.andRoleIdNotEqualTo(DataConstants.COMMON_ADMIN);
        }
        if (!roleRequest.isAdmin()){
            criteria.andCreateUserEqualTo(roleRequest.getCreateUser());
        }
        criteria.andIsExistEqualTo(Byte.valueOf("1"));
        criteria.andRoleIdNotEqualTo(DataConstants.SUPER_ADMIN);
        List<NmplRole> nmplRoleList = nmplRoleMapper.selectByExample(nmplRoleExample);
        nmplRoleList = nmplRoleList.stream().sorted(Comparator.comparing(NmplRole::getCreateTime).reversed()).collect(Collectors.toList());
        List<NmplRoleVo> nmplRoles = new ArrayList<>();
        for (NmplRole nmplRole : nmplRoleList) {
            NmplRoleVo role = new NmplRoleVo();
            BeanUtils.copyProperties(nmplRole,role);
            nmplRoles.add(role);
        }
        return nmplRoles;
    }
}
