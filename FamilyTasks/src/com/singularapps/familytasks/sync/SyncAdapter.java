package com.singularapps.familytasks.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.singularapps.familytasks.model.Tasks;
import com.singularapps.familytasks.network.ServerAccessor;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    
    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;
    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }
    
    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    
    }
    
    @Override
    public void onPerformSync(
            Account account,
            Bundle extras,
            String authority,
            ContentProviderClient provider,
            SyncResult syncResult) {
    	
    	try {
    		Log.d("SyncDemo", "Sincronizando!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    		
    		ServerAccessor sa = new ServerAccessor();
    		Tasks taskToAdd = new Tasks(1,"Tarea1","cat","date","prio","status",1);
    		sa.putTasks(taskToAdd);
    	}
    	catch(Exception e) {}
    /*
     * Put the data transfer code here.
     */
    	
    	// 1. Connection to Server
    	// 2. Downloading and uploading data
    	// 3. Handling data conflicts of determining how current the data is
    	// 4. Clean Up connections and files

    }
}