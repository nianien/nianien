package com.nianien.test.bean;

public class CXString<T> {

	private String value;

	public CXString(String value) {
		this.value = value;
	}

	public String toString() {
		return value.toString();
	}
}
