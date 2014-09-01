package com.app42.wear.notification;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.app42pushnotification.R;
import com.google.android.gcm.GCMRegistrar;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.App42Log;

/**
 * 
 * @author Vishnu Garg
 * @Shephertz
 * 
 */
public class MainActivity extends Activity {

	private TextView messageText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageText = ((TextView) findViewById(R.id.message));
		App42API.initialize(
				this,
				"e86da38e4f4363bcbce74c431ca10173bf452e47285893e8d2044bb3913b7153",
				"f8fb6584ee2f9cc5b0d46d22cf696e32bd3988e064ae7760b0219e36f46357f3");
		String message = getIntent().getStringExtra(
				App42GCMService.EXTRA_MESSAGE);
		if (message != null)
			messageText.setText(message);
	}

	public void sendMultiPagePush(View view) {
		try {
			sendPushMessage(Utils.buildMultiPageJson(), "vishnu");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// // Notification
		// notification=NotificationBuilder.getMultiPageNotification(this);
		// NotificationManagerCompat notificationManagerCompat =
		// NotificationManagerCompat.from(this);
		// notificationManagerCompat.notify(001, notification);
	}

	public void sendBigTextPush(View view) {
		try {
			sendPushMessage(Utils.buildBigTextJson(), "vishnu");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Notification
		// notification=null;//NotificationBuilder.getBigNotification(this);
		// NotificationManagerCompat notificationManagerCompat =
		// NotificationManagerCompat.from(this);
		// notificationManagerCompat.notify(001, notification);
	}

	public void sendImagePush(View view) {
		try {
			sendPushMessage(Utils.buildImageJson(), "vishnu");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Notification
		// notification=NotificationBuilder.getImageNotification(this);
		// NotificationManagerCompat notificationManagerCompat =
		// NotificationManagerCompat.from(this);
		// notificationManagerCompat.notify(001, notification);
	}

	public void sendBasicPush(View view) {
		try {
			sendPushMessage(Utils.buildBasicJson(), "vishnu");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Notification
		// notification=NotificationBuilder.getBasicNotification(this);
		// NotificationManagerCompat notificationManagerCompat =
		// NotificationManagerCompat.from(this);
		// notificationManagerCompat.notify(001, notification);
	}

	public void registerForPush(View view) {
		App42API.setLoggedInUser("vishnu");
		registerWithApp42("1043599038916");
	}

	private void displayMessage(final String message) {
		MainActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				messageText.setText(message);
			}
		});
	}

	private void sendPushMessage(JSONObject message, String userName) {
		App42API.buildPushNotificationService().sendPushMessageToUser(userName,
				message.toString(), new App42CallBack() {
					@Override
					public void onSuccess(Object arg0) {
						// TODO Auto-generated method stub
						displayMessage(arg0.toString());
					}

					@Override
					public void onException(Exception arg0) {
						// TODO Auto-generated method stub
						displayMessage(arg0.toString());

					}
				});
	}

	private void registerWithApp42(String projectNo) {
		App42Log.debug(" ..... Registeration Check ....");

		final String deviceRegId = GCMRegistrar.getRegistrationId(this);
		if (deviceRegId.equals("")) {
			GCMRegistrar.register(this, projectNo);
		} else {
			App42Log.debug(" Registering on Server ....");
			App42API.buildPushNotificationService().storeDeviceToken(
					App42API.getLoggedInUser(), deviceRegId,
					new App42CallBack() {
						@Override
						public void onSuccess(Object paramObject) {
							App42Log.debug(" ..... Registeration Success ....");
							displayMessage(paramObject.toString());
						}

						@Override
						public void onException(Exception paramException) {
							App42Log.debug(" ..... Registeration Failed ....");
							App42Log.debug("storeDeviceToken :  Exception : on start up "
									+ paramException);
							displayMessage(paramException.toString());
						}
					});
		}
	}

	/*
	 * called when activity is paused
	 * 
	 * @override method of superclass (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	public void onPause() {
		super.onPause();
		unregisterReceiver(mBroadcastReceiver);

	}

	/*
	 * called when activity is resume
	 * 
	 * @override method of superclass (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	public void onResume() {
		super.onResume();
		String message = getIntent().getStringExtra(
				App42GCMService.EXTRA_MESSAGE);
		Log.d("MainActivity-onResume", "Message Recieved :" + message);
		IntentFilter filter = new IntentFilter(
				App42GCMService.DISPLAY_MESSAGE_ACTION);
		filter.setPriority(2);
		registerReceiver(mBroadcastReceiver, filter);
	}

	final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String message = intent
					.getStringExtra(App42GCMService.EXTRA_MESSAGE);
			Log.i("MainActivity-BroadcastReceiver", "Message Recieved " + " : "
					+ message);
			messageText.setText(message);
		}
	};

	public void onStart() {
		super.onStart();

	}

	/*
	 * * This method is called when a Activty is stop disable all the events if
	 * occuring (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	public void onStop() {
		super.onStop();

	}

	/*
	 * This method is called when a Activty is finished or user press the back
	 * button (non-Javadoc)
	 * 
	 * @override method of superclass
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	public void onDestroy() {
		super.onDestroy();

	}

	/*
	 * called when this activity is restart again
	 * 
	 * @override method of superclass
	 */
	public void onReStart() {
		super.onRestart();

	}
}
