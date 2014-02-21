package com.singularapps.familytasks.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class DatabaseAdapter extends ContentProvider {
	static final String TAG = "DatabaseAdapter";
	static final String DATABASE_NAME = "familytasks";
	static final int DATABASE_VERSION = 1;
	static final String CREATE_TABLE_TASKS = 
			"create table tasks (_id integer primary key autoincrement,"
			+ "title text, category_name text, due_date text, priority text, status text, owner text);";
	static final String CREATE_TABLE_USERS = 
			"create table users (_id integer primary key autoincrement,"
			+ "name text);";
	
	static final int TASKS = 1;
	static final int TASKS_ID = 2;
	static final int USERS = 3;
	static final int USERS_ID = 4;
	
	private static final UriMatcher uriMatcher;
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(Contract.PROVIDER_NAME, "tasks", TASKS);
		uriMatcher.addURI(Contract.PROVIDER_NAME, "tasks/#", TASKS_ID);
		uriMatcher.addURI(Contract.PROVIDER_NAME, "users", USERS);
		uriMatcher.addURI(Contract.PROVIDER_NAME, "users/#", USERS_ID);
	}
	
	SQLiteDatabase db;
	DatabaseHelper DBHelper;

	public boolean onCreate() {
		Context context = getContext();
		DatabaseHelper DBHelper = new DatabaseHelper(context);
		db = DBHelper.getWritableDatabase();
		return (db == null)? false:true;
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(CREATE_TABLE_TASKS);
				db.execSQL(CREATE_TABLE_USERS);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database...");
			db.execSQL("DROP TABLE IF EXISTS tasks");
			db.execSQL("DROP TABLE IF EXISTS users");
		}
	}
	
	public void close() {
		DBHelper.close();
	}

	@Override
	public String getType(Uri uri) {
		switch(uriMatcher.match(uri)) {
			case TASKS:
				return Contract.URI_TYPE_TASKS_DIR;
			case TASKS_ID:
				return Contract.URI_TYPE_TASKS_ITEM;
			default:
				throw new IllegalArgumentException("Unsupported UTRI:" + uri);
		}
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
		
		switch(uriMatcher.match(uri)) {
			case TASKS:
				sqlBuilder.setTables(Contract.TABLE_TASKS);
				break;
			case TASKS_ID:
				sqlBuilder.setTables(Contract.TABLE_TASKS);
				sqlBuilder.appendWhere(Contract.TasksColumns.ID + " = " + uri.getPathSegments().get(1));
				break;
			case USERS:
				sqlBuilder.setTables(Contract.TABLE_USERS);
				break;
			case USERS_ID:
				sqlBuilder.setTables(Contract.TABLE_USERS);
				sqlBuilder.appendWhere(Contract.UsersColumns.ID + " = " + uri.getPathSegments().get(1));
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		if (sortOrder == null || sortOrder == "") sortOrder = Contract.TasksColumns.TITLE;
		
		Cursor c = sqlBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		return c;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri _uri = null;
		long _ID;
		switch(uriMatcher.match(uri)) {
			case TASKS: 
				_ID = db.insert(Contract.TABLE_TASKS, "", values);
				if (_ID > 0) {
					_uri = ContentUris.withAppendedId(Contract.TASKS_URI, _ID);
					getContext().getContentResolver().notifyChange(_uri, null);
				}
				break;
			case USERS:
				_ID = db.insert(Contract.TABLE_USERS, "", values);
				if (_ID > 0) {
					_uri = ContentUris.withAppendedId(Contract.USERS_URI, _ID);
					getContext().getContentResolver().notifyChange(_uri, null);
				}
				break;
			default:
				throw new SQLException("Failed to insert row into" + uri);
		}
		return uri;
	}
	
	@Override
	public int delete(Uri uri, String arg1, String[] arg2) {
		int count = 0;
		switch(uriMatcher.match(uri)) {
			case TASKS:
				count = db.delete(Contract.TABLE_TASKS, arg1, arg2);
				break;
			case TASKS_ID:
				count = db.delete(Contract.TABLE_TASKS, Contract.TasksColumns.ID + " = "
						+ uri.getPathSegments().get(1) + (!TextUtils.isEmpty(arg1) ? " AND (" +
						arg1 + ')' : ""), arg2);
				break;
			case USERS:
				count = db.delete(Contract.TABLE_USERS, arg1, arg2);
				break;
			case USERS_ID:
				count = db.delete(Contract.TABLE_USERS, Contract.UsersColumns.ID + " = "
						+ uri.getPathSegments().get(1) + (!TextUtils.isEmpty(arg1) ? " AND (" +
						arg1 + ')' : ""), arg2);
				break;
			default:
				throw new IllegalArgumentException("Unknow URI "+ uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
	
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int count = 0;
		switch(uriMatcher.match(uri)) {
			case TASKS:
				count = db.update(Contract.TABLE_TASKS, values, selection, selectionArgs);
				break;
			case TASKS_ID:
				count = db.update(Contract.TABLE_TASKS, values, Contract.TasksColumns.ID + " = "
						+ uri.getPathSegments().get(1) + (!TextUtils.isEmpty(selection) ? " AND (" 
						+ selection + ')' : ""), selectionArgs);
				break;
			case USERS:
				count = db.update(Contract.TABLE_USERS, values, selection, selectionArgs);
				break;
			case USERS_ID:
				count = db.update(Contract.TABLE_USERS, values, Contract.UsersColumns.ID + " = "
						+ uri.getPathSegments().get(1) + (!TextUtils.isEmpty(selection) ? " AND (" 
						+ selection + ')' : ""), selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		return count;		
	}
}