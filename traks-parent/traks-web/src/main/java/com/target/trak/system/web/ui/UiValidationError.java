package com.target.trak.system.web.ui;

public class UiValidationError {

	private String id;
	private String msg;

	public UiValidationError(String id, String msg) {
		this.id = id;
		this.msg = msg;
	}

	public UiValidationError() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
