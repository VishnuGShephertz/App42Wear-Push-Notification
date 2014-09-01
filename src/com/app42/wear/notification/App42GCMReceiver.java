package com.app42.wear.notification;

import android.content.Context;
import com.google.android.gcm.GCMBroadcastReceiver;
/**
 * 
 * @author Vishnu Garg
 * @Shephertz
 * 
 */
public class App42GCMReceiver extends GCMBroadcastReceiver{
	@Override
	protected String getGCMIntentServiceClassName(Context context) { 
		return "com.app42.wear.notification.App42GCMService"; 
	} 
}