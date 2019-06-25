package com.jcg.vo;

import java.util.List;

public class Custom extends ResponseVO {
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	Integer id;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	String name;
	public String getFoo() {
		return foo;
	}
	public void setFoo(String foo) {
		this.foo = foo;
	}
	String foo;
	
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	List<String> tags;

}
