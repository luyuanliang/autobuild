package org.web.autobuild.domain;


/**
 * 类ServiceException.java的实现描述：封装业务异常
 * 
 * @author Luyl 2016年10月24日 上午10:23:00
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	// 错误码
	private String errorCode;
	// 结果描述，用于排查问题
	private String description;
	// 参数
	private String parameter;
	// 终端用户的提示信息，不推荐使用
	private String message;

	public ServiceException(String errorCode, String description) {
		this.errorCode = errorCode;
		this.description = description;
	}

	public ServiceException(String errorCode, String description, String parameter, String message) {
		this.errorCode = errorCode;
		this.description = description;
		this.parameter = parameter;
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
