package com.singularapps.familytasks.network;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.singularapps.familytasks.model.Tasks;
import com.singularapps.familytasks.model.Contract;

public class ServerAccessor {
	public void putTasks(Tasks taskToAdd) throws Exception {
		String url = "http://192.168.0.199:8000/tasks/";
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "application/json");
		HttpResponse httpResponse;
		
		JSONObject task = new JSONObject();
		task.put("title", taskToAdd.title);
		task.put("category_name", taskToAdd.category_name);
		task.put(Contract.TasksColumns.DUE_DATE, taskToAdd.due_date);
		task.put(Contract.TasksColumns.PRIORITY, taskToAdd.priority);
		task.put(Contract.TasksColumns.STATUS, taskToAdd.status);
		task.put("owner", taskToAdd.owner);
		StringEntity se = new StringEntity(task.toString());
		se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));
		httpPost.setEntity(se);
		httpResponse = client.execute(httpPost);
	}
}