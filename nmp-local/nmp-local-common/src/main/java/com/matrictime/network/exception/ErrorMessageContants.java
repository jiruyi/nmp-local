package com.matrictime.network.exception;

/**
 * @description 错误信息
 * @author jiruyi@jzsg.com.cn
 * @create 2021/7/14 16:50
 */
public class ErrorMessageContants {

	public static final String SYSTEM_EXCEPTION = "系统异常，请联系管理员！";
	
	public static final String SYSTEM_ERROR = "系统异常，";

	public static final String PARAM_IS_NULL_MSG = "必输入参为空";

	public static final String USERNAME_IS_NULL_MSG = "用户名或者密码为空";

	public static final String LoginType_IS_ERROR_MSG = "登录方式不存在";

	public static final String PHONE_CODE_IS_NULL_MSG = "手机号或者短信验证码为空";

	public static final String USERNAME_NO_EXIST_MSG = "用户名不存在";

	public static final String SMS_CODE_NO_EXIST_MSG = "验证码已过期";

	public static final String SMS_CODE_ERROR_MSG = "验证码不正确，请核对后再试";

	public static final String PHONE_NO_EXIST_MSG = "手机号不存在";

	public static final String PASSWORD_ERROR_LIMIT_MSG = "密码错误已经超过三次,请稍后再试";

	public static final String JWT_VERIFY_FAIL_MSG = "jwt验证失败";

	public static final String USERNAME_OR_PASSWORD_ERROR_MSG = "用户名或者密码错误";

	public static final String LOGOUT_SUCCESS_MSG = "成功退出";

	public static final String DEVICE_ID_IS_NULL_MSG = "设备id不能为空";

	public static final String OPER_DESC_IS_NULL_MSG = "操作信息不能为空";
	public static final String OPER_TYPE_IS_NULL_MSG = "操作类型不能为空";

	public static final String DEVICE_NOT_EXIST_MSG = "设备不存在";

	public static final String DEVICE_NOT_ACTIVE_MSG = "设备未激活或者设备非下线状态";

	public static final String PHONE_SMS_COUNT_LIMIT = "您的手机短信发送过于频繁";

	public static final String PHONE_IS_NOT_EXIST = "手机号不存在,请核对后再试";

	public static final String DEVICE_IS_NOT_EXIST = "设备不存在";

	public static final String PARENT_SON_RELATION_ERROR = "父子关系与设备等级不匹配";

	public static final String NO_ROUTE_EXIST_ERROR = "没有路由路线可达";

	public static final String DEVICE_AUTH_FAIL_MSG = "设备认证失败";

	public static final String JSON_ERROR_FAIL_MSG = "JSON解析失败";

}
