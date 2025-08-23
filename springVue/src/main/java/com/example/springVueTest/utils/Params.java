package com.example.springVueTest.utils;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Params {
	 private Map<String, Object> innerMap = new HashMap<>();

	    @JsonAnySetter
	    public void put(String key, Object value) {
	        this.innerMap.put(key, value);
	    }

	    @JsonAnyGetter
	    public Map<String, Object> getMap() {
	        return this.innerMap;
	    }

	    public Object get(String key) {
	        return this.innerMap.get(key);
	    }

	    public Object remove(String key) {
	        return this.innerMap.remove(key);
	    }

	    public boolean containsKey(String key) {
	        return this.innerMap.containsKey(key);
	    }

	    public boolean containsValue(Object value) {
	        return this.innerMap.containsValue(value);
	    }

	    public void clear() {
	        this.innerMap.clear();
	    }

	    public boolean isEmpty() {
	        return this.innerMap.isEmpty();
	    }

	    public void putAll(Map<? extends String, ? extends Object> m) {
	        this.innerMap.putAll(m);
	    }

	    public String toJSONString() throws JsonProcessingException {
	        return new ObjectMapper().writeValueAsString(this.innerMap);
	    }

	    @Override
	    public String toString() {
	        return this.innerMap.toString();
	    }

}