package com.nianien.test.bean;

import java.util.List;

public class DemoBean {

	private List<String> names;

	private List<CXString<Integer>> ages;

	public List<CXString<Integer>> getAges() {
		return ages;
	}

	public void setAges(List<CXString<Integer>> ages) {
		this.ages = ages;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}
}
