package com.singularapps.familytasks;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

import com.singularapps.familytasks.model.Contract;

public class MainActivity extends ListActivity {
	Cursor cursor;
	SimpleCursorAdapter adapter;
	private static final int ADD_ITEM = Menu.FIRST;
	private static final int DEL_ITEM = ADD_ITEM + 1;
	Account mAccount;
	public static final String ACCOUNT_TYPE = "familytasks.com";
	public static final String ACCOUNT = "dummyaccount";
	public static final long MILLISECONDS_PER_SECOND = 1000L;
	public static final long SECONDS_PER_MINUTE = 60L;
	public static final long SYNC_INTERVAL_IN_MINUTES = 1L;
	public static final long SYNC_INTERVAL =
			SYNC_INTERVAL_IN_MINUTES *
			SECONDS_PER_MINUTE *
			MILLISECONDS_PER_SECOND;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mAccount = CreateSyncAccount(this);
		ContentResolver.setIsSyncable(mAccount, Contract.AUTHORITY, 1);
		ContentResolver.setSyncAutomatically(mAccount, Contract.AUTHORITY, true);
		ContentResolver.addPeriodicSync(mAccount, Contract.AUTHORITY, new Bundle(), 10);
		
		
		fillData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(0, ADD_ITEM, 0, "Add");
		menu.add(0, DEL_ITEM, 0, "Del");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case ADD_ITEM:
			createTask();
			break;
		case DEL_ITEM:
			deleteTask();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void fillData() {
		CursorLoader cursorLoader = new CursorLoader(this, Contract.TASKS_URI,
										new String[] { Contract.TasksColumns.ID, Contract.TasksColumns.TITLE },
										null,
										null,
										null);
		cursor = cursorLoader.loadInBackground();
		adapter = new SimpleCursorAdapter(this, R.layout.items_row, cursor, 
					new String[] { Contract.TasksColumns.TITLE },
					new int[] { R.id.text1 }, 0);
		setListAdapter(adapter);
	}
	
	public void createTask() {
		ContentValues values = new ContentValues();
		values.put(Contract.TasksColumns.TITLE, "Task1");
		getContentResolver().insert(Contract.TASKS_URI, values);
		fillData();
	}
	
	public void deleteTask() {}


	public static Account CreateSyncAccount(Context context) {
		Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
		AccountManager accountManager =
			(AccountManager) context.getSystemService(ACCOUNT_SERVICE);
	
		if (accountManager.addAccountExplicitly(newAccount, null, null)) {
		}
		else {		
		}
		return newAccount;
	}
}