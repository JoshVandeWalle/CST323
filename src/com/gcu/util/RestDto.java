package com.gcu.util;

import java.util.List;

public class RestDto<T>
{
	private List<T> data;
	
	private int code;
	
	private String message;

	public RestDto(List<T> data, int code, String message) {
		super();
		this.data = data;
		this.code = code;
		this.message = message;
	}

	public List<T> getData() {
		return data;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	
}
