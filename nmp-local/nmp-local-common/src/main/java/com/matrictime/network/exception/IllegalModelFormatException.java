package com.matrictime.network.exception;

/**
 * @param
 * @return
 * @description 字段校验异常类
 * @author jiruyi@jzsg.com.cn
 * @create 2021/7/15 9:29
 */
public class IllegalModelFormatException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public String code ;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public IllegalModelFormatException() {
		super();
	}

	public IllegalModelFormatException(String message, Throwable cause, boolean enableSuppression,
                                       boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalModelFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalModelFormatException(Throwable cause) {
		super(cause);
	}

	public IllegalModelFormatException(String message) {
		super(message);
	}

	public IllegalModelFormatException(String code, String message) {
		super(message);
		this.code = code;
	}

	public IllegalModelFormatException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	@Override
	public String toString() {
		return "IllegalModelFormatException [code=" + code + ", getMessage()=" + getMessage() + "]";
	}

}
