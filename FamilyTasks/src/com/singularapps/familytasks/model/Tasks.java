package com.singularapps.familytasks.model;

public class Tasks {
	public long _id;
	public String title;
	public String category_name;
	public String due_date;
	public String priority;
	public String status;
	public long owner;
	
	public Tasks() {		
	}
	
	public Tasks(long _id, String title, String category_name, String due_date, String priority,
			String status, long owner) {
		this._id = _id;
		this.title  = title;
		this.category_name = category_name;
		this.due_date = due_date;
		this.priority = priority;
		this.status = status;
		this.owner = owner;
	}
	
	public void setId(long _id) {
		this._id = _id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	
	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public void setOwner(int owner) {
		this.owner = owner;
	}
	
	public long getId() {
		return this._id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getCategory_name() {
		return this.category_name;
	}
	
	public String getDue_date() {
		return this.due_date;
	}
	
	public String getPriority() {
		return this.priority;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public long setOwner() {
		return this.owner;
	}


}
