/**
 * 
 */
package com.app42.wear.notification;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;
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
public class App42GCMService extends GCMBaseIntentService {

	static int msgCount = 0;
	static final String DISPLAY_MESSAGE_ACTION = "com.example.app42pushnotification.DISPLAY_MESSAGE";
	static final String EXTRA_MESSAGE = "message";

	public App42GCMService() {
		super("");
	}

	@Override
	protected void onError(Context arg0, String msg) {
		Log.i(TAG, "onError " + msg);

	}
	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.i(TAG, "Received message "
				+ intent.getExtras().getString("message"));
		String message = intent.getExtras().getString("message");
		App42Push app42Push=Utils.getApp42Push(message);
		displayMessage(context, message);
		Notification notification=new NotificationBuilder().generateNotification(context,app42Push, message);
				 NotificationManagerCompat notificationManagerCompat =
				 NotificationManagerCompat.from(this);
				 notificationManagerCompat.notify(001, notification);
	}

	@Override
	protected void onRegistered(Context arg0, String regId) {
		Log.i(TAG, "Device registered: regId = " + regId);
		registerWithApp42(regId);

	}

	private void registerWithApp42(String regId) {
		App42Log.debug(" Registering on Server ....");
		App42API.buildPushNotificationService().storeDeviceToken(
				App42API.getLoggedInUser(), regId, new App42CallBack() {
					@Override
					public void onSuccess(Object paramObject) {
						// TODO Auto-generated method stub
						App42Log.debug(" ..... Registeration Success ....");
						GCMRegistrar.setRegisteredOnServer(App42API.appContext,
								true);
					}

					@Override
					public void onException(Exception paramException) {
						App42Log.debug(" ..... Registeration Failed ....");
						App42Log.debug("storeDeviceToken :  Exception : on start up "
								+ paramException);
					}
				});
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		Log.i(TAG, "Device unRegistered: regId = " + arg1);
	}

	/**
	 * Notifies UI to display a message.
	 * <p>
	 * This method is defined in the common helper because it's used both by the
	 * UI and the background service.
	 * 
	 * @param context
	 *            application's context.
	 * @param message
	 *            message to be displayed.
	 */
	public void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}

	public static void resetMsgCount() {
		msgCount = 0;
	}
}
