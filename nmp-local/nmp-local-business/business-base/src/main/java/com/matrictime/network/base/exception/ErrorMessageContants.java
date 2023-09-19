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

	public static final String NO_ADMIN_ERROR_MSG = "您不是超管,无此权限";

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

	public static final String DEVICE_LIST_IS_NULL_MSG = "上报列表不能为空";

	public static final String DEVICE_IP_IS_NULL_MSG = "设备IP不能为空";

	public static final String OPER_DESC_IS_NULL_MSG = "操作信息不能为空";
	public static final String OPER_TYPE_IS_NULL_MSG = "设备类型不能为空";

	public static final String DEVICE_NOT_EXIST_MSG = "设备不存在";

	public static final String DEVICE_NOT_ACTIVE_MSG = "设备未激活或者设备非下线状态";

	public static final String PHONE_SMS_COUNT_LIMIT = "您的手机短信发送过于频繁";

	public static final String PHONE_IS_NOT_EXIST = "手机号不存在,请核对后再试";

	public static final String DEVICE_IS_NOT_EXIST = "设备不存在";

	public static final String PARENT_SON_RELATION_ERROR = "父子关系与设备等级不匹配";

	public static final String NO_ROUTE_EXIST_ERROR = "没有路由路线可达";

	public static final String DEVICE_AUTH_FAIL_MSG = "设备认证失败";

	public static final String JSON_ERROR_FAIL_MSG = "JSON解析失败";

	public static final String SIGNAL_I_EXCEPTION_MSG = "信令已开启追踪";

	public static final String SIGNAL_O_EXCEPTION_MSG = "信令关闭参数校验有误";

	public static final String NO_CREATEUSER_ERROR_MSG = "并非该用户的创始人,无权修改";

	public static final String PARAM_LENTH_ERROR_MSG ="参数长度异常";

	public static final String PARAM_FORMAT_ERROR_MSG = "参数格式异常";

	public static final String EQUAL_PASSWORD_ERROR_MSG = "新旧密码不可相同";

	public static final String ADMIN_DELETE_ERROR_MSG = "无法删除管理员";

	public static final String ADMIN_UPDATE_ERROR_MSG = "无法修改管理员";

	public static final String NETWORK_ID_IS_NULL_MSG = "入网Id不能为空";

	public static final String ID_IS_NULL_MSG = "Id不能为空";


	public static final String IP_FORMAT_ERROR_MSG = "ip格式不正确";

	public static final String PORT_FORMAT_ERROR_MSG = "端口格式不正确";

	public static final String NOT_EXIST_VILLAGE ="小区不存在";

	public static final String DEVICEID_REPEAT = "设备ID重复";

	public static final String IP_REPEAT = "设备IP重复";

	public static final String DEVICENAME_LENTH_ERROR_MSG="设备名长度超过限制";

	public static final String NO_LOAD_VERSION = "所选设备已运行或无加载版本";

	public static final String NO_DEVICE_CAN_RUN = "所选设备都无法执行命令";

	public static final String PROHIBIT_REPORT="上报项为空或禁止上报时,无法手动上报";

	public static final String REPORT_ERROR = "上报失败,请稍后重试";

	public static final String DATACOLLECT_NOT_EXIST = "数据采集设备不存在";
}
