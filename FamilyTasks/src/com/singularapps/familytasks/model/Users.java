package com.singularapps.familytasks.model;

public class Users {
	long _id;
	String name;
	
	public Users() {
	}
	
	public Users(long _id, String name) {
		this._id  =_id;
		this.name = name;
	}
	
	public void setId(long _id) {
		this._id = _id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getId() {
		return this._id;
	}
	
	public String getName() {
		return this.name;
	}
}
