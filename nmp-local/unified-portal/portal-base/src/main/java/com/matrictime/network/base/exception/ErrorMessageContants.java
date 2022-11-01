package com.matrictime.network.base.exception;

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

	public static final String PHONE_EXIST_ERROR_MSG = "手机号已存在";

	public static final String NO_ADMIN_ERROR_MSG = "您不是超管,无权修改";

	public static final String USER_NO_EXIST_ERROR_MSG = "用户不存在";

	public static final String OLD_PASSWORD_ERROR_MSG = "初始密码不正确";

	public static final String TWO_PASSWORD_ERROR_MSG = "两次密码不一致";

	public static final String LOGINACCOUNT_EXIST_ERROR_MSG = "登录账号已存在";

	public static final String PHONE_CODE_IS_NULL_MSG = "手机号或者短信验证码为空";

	public static final String USERNAME_NO_EXIST_MSG = "用户名不存在";

	public static final String SMS_CODE_NO_EXIST_MSG = "验证码已过期";

	public static final String SMS_CODE_ERROR_MSG = "验证码不正确，请核对后再试";

	public static final String PHONE_NO_EXIST_MSG = "手机号不存在";

	public static final String PASSWORD_ERROR_LIMIT_MSG = "密码错误已经超过三次,请稍后再试";

	public static final String JWT_VERIFY_FAIL_MSG = "jwt验证失败";

	public static final String USERNAME_OR_PASSWORD_ERROR_MSG = "用户名或者密码错误";

	public static final String LOGOUT_SUCCESS_MSG = "成功退出";

	public static final String OPER_DESC_IS_NULL_MSG = "操作信息不能为空";

	public static final String PHONE_IS_NOT_EXIST = "手机号不存在,请核对后再试";

	public static final String JSON_ERROR_FAIL_MSG = "JSON解析失败";

	public static final String NO_CREATEUSER_ERROR_MSG = "并非该用户的创始人,无权修改";

	public static final String PARAM_LENTH_ERROR_MSG ="参数长度异常";

	public static final String PARAM_FORMAT_ERROR_MSG = "参数格式异常";

	public static final String EQUAL_PASSWORD_ERROR_MSG = "新旧密码不可相同";

	public static final String IMG_TOO_LARGE = "图片大于10M";

	public static final String IMG_IS_NOT_PNG = "图片格式错误，请重新上传png格式图片";

	public static final String UPLOAD_FILE_FAIL = "上传图片失败";

}
