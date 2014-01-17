package com.hatc.base.utils;

import java.io.Serializable;

public class ValuePair implements Serializable {

	private static final long serialVersionUID = 1L;

	private String key;

	private String value;

	public ValuePair(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public void putKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	public void putValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
