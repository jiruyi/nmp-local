
package com.matrictime.network.shiro;


import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.mapper.NmplMenuMapper;
import com.matrictime.network.dao.mapper.NmplUserMapper;
import com.matrictime.network.dao.model.NmplMenu;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.dao.model.NmplUserExample;
import com.matrictime.network.response.UserInfoResp;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 认证
 *
 */
@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private NmplUserMapper nmplUserMapper;
    @Autowired
    private NmplMenuMapper nmplMenuMapper;
    
    /**
     * 授权(验证权限时调用)
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		NmplUser user = RequestContext.getUser();

		//NmplUser user = (NmplUser)principals.getPrimaryPrincipal();
		String roleId = user.getRoleId();
		
		List<String> permsList=null;
		
//		系统管理员，拥有最高权限
   		if(Long.parseLong(roleId) == DataConstants.SUPER_ADMIN){
			List<NmplMenu> menuList = nmplMenuMapper.selectByExample(null);
			permsList = new ArrayList<>(menuList.size());
			for(NmplMenu menu : menuList){
				permsList.add(menu.getPermsCode());
			}
		}else{
			//查询非管理员用户所有权限
			permsList = nmplMenuMapper.queryAllPerms(Long.valueOf(roleId));
		}

		//用户权限列表
		Set<String> permsSet = new HashSet<>();
		for(String perms : permsList){
			if(StringUtils.isBlank(perms)){
				continue;
			}
			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		}
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		return info;
	}

	/**
	 * 认证(登录时调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken)authcToken;

		//查询用户信息
		NmplUser user = null;
		NmplUserExample nmplUserExample = new NmplUserExample();
		nmplUserExample.createCriteria().andUserIdEqualTo(Long.valueOf(token.getUsername()));
		List<NmplUser> nmplUserList = nmplUserMapper.selectByExample(nmplUserExample);
		if (!CollectionUtils.isEmpty(nmplUserList)){
			user = nmplUserList.get(0);
		}
		//账号不存在
		if(user == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}

		//账号锁定
		if(user.getStatus() == false){
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}
		UserInfoResp userInfo = new UserInfoResp();
		BeanUtils.copyProperties(user,userInfo);

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo( userInfo, userInfo.getPassword(),getName());
		return info;
	}

}
