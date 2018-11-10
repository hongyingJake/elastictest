package com.hongying.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

//refreshInterval 从不刷新缓存，索引更新会添加到缓存中，默认1S
@Document(indexName="mtes",type="employee",replicas=1,shards=1,refreshInterval="-1")
public class Employee {
	@Id
	private String id;
	@Field
	private String firstName;
	@Field
	private String lastName;
	@Field
	private Integer age = 0;
	@Field
	private String about;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}
}
