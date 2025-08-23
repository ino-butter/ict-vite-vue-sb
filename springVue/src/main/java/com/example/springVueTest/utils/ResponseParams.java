package com.example.springVueTest.utils;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

public class ResponseParams {
	public boolean isSuccess;
	public int code = 0;
	public String message;
	public Object data;
	
	public void setParams(boolean isSuccess, int code, String message, Object data) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public void setIsSuccess(boolean isSuccess){
		this.isSuccess = isSuccess;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setData(Object data) {
		this.data = data;
	}
    @JsonAnyGetter
	public Map<String, Object> getMap() {
		Map<String, Object> innerMap = new HashMap<>();
		innerMap.put("isSuccess", this.isSuccess);
		innerMap.put("code", this.code);
		innerMap.put("message", this.message);
		innerMap.put("data", this.data);
		return innerMap;
	}
}
