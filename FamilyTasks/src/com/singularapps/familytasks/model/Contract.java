package com.singularapps.familytasks.model;

import android.net.Uri;

public class Contract {
	public static final String PROVIDER_NAME = "com.singularapps.familytasks.provider";
	public static final String AUTHORITY ="com.singularapps.familytasks.provider";
	public static final Uri TASKS_URI = Uri.parse("content://"+ PROVIDER_NAME + "/tasks");
	public static final Uri USERS_URI = Uri.parse("content://"+ PROVIDER_NAME + "/users");

	public static final class TasksColumns {
		public static final String ID = "_id";
		public static final String TITLE = "title";
		public static final String CATEGORY_NAME = "category_name";
		public static final String DUE_DATE = "due_date";
		public static final String PRIORITY = "priority";
		public static final String STATUS = "status";
		public static final String OWNER = "owner";
	}
	
	public static final class UsersColumns {
		public static final String ID = "_id";
		public static final String NAME = "name";
	}
	
	public static final String TABLE_USERS = "users";
	public static final String TABLE_TASKS = "tasks";
	
	// MIME types
	public static final String URI_TYPE_TASKS_ITEM = "vnd.android.cursor.item/vnd.singularapps.tasks";
	public static final String URI_TYPE_TASKS_DIR = "vnd.android.cursor.dir/vnd.singularapps.tasks";
}
