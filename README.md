App42Wear-Push-Notification
===========================

This sample project explain how can we [App42 Push Notification API](http://api.shephertz.com/app42-docs/push-notification-service/) in different ways to send Push Notification to device as well as sink them with Android Wearables.
Please go through with [Android Wear Getting Started] (http://blogs.shephertz.com/2014/07/24/android-wear-getting-started/) if you are new to Android Wear.

Here you can learn how to configure different type of Push Notification using App42 Push Notification API on device as well as on Android Wearable.</br>

1. Basic Push Notification</br>
2. Image based Push Notification</br>
3. Big Content Based Push Notification</br>
4. Multiple Page based Push Notifications</br>


# Running Sample

This is a sample Android app is made by using App42  API. It uses Push Notification API of App42 platform.
Here are the few easy steps to run this sample app.

1. [Register\Login] (https://apphq.shephertz.com/register) with App42 platform and create an app from App Manager Tab. 
2. [Download project] (https://github.com/VishnuGShephertz/App42Wear-Push-Notification/archive/master.zip) and unzip it.
3. Configure with Eclipse that have latest ADT (Android Developer Tool).
```
A. Import this project in Eclipse.
B. Check Android Dependencies and Android Private libraries in order and import section on Build Path.
```

4. Configure with Android Studio.
```
A. Import this project in your Android Studio.
B. Add library jar files from libs folder by adding library modules in Android Studio.
```

5. Make these changes in MainActivity.java file of sample.

```
A. Replace API-Key and Secret-Key that you have received in step 2 or 3 at line number 39 and 40.
B. Replace your user-id by which you want to register your application for Push Notification at line number 31.
```
__GCM Integration__

6. Create a project and get your Project Number from [google developer console](https://cloud.google.com/console/project). It would be available in Overview section of your created project.<div style="clear:both"></div>
7. Select your created project and click on APIs option in Google developer console and enable Google Cloud Messaging for Android service.<div style="clear:both"></div>
8. Click on Credentials(in APIs option) from left menu -> Create New Key -> Server Key.<div style="clear:both"></div>
9. Keep Accept requests from these server IP addresses as blank and click on Create button.<div style="clear:both"></div>
10. Go to [AppHQ] (http://apphq.shephertz.com) click on Push Notification and select Android Settings in Settings option.<div style="clear:both"></div>
11. Select your app and provider as GCM and copy server key that is generated in Google developer console in above step and submit it.<div style="clear:both"></div>
12. Open MainActivity.java file of sample project and make following changes.

```
A. Put your Google Project No at line no90.
```
13. Build your Android Android application and run it on your device (device version should must be 4.4 or above to support Android Wear Notification and for Android Wear Connection).
14. For Android Wear Connectivity with device go through with [Android Wear Getting Started] (http://blogs.shephertz.com/2014/07/24/android-wear-getting-started/).
15. You can also open these Notifications from Android Wear to phone when you click *Open on Phone* option on Android Wear.

# Design Details:

__Registration for Push Notification__ To register for Push Notification on App42 , you have to use method written in App42GCMService.java file, that register your GCM registration Id on App42.
 
```
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
```

__Send Push Notification to User__ You can also send different type of Push Notification using single API and configure accordingly..
 
```
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
```
__Congiguring different type of Push Notification before sending to user__ All sample code written in Utils.java file in sample project.

__1.__ Basic PushNotfication
```
static JSONObject buildBasicJson() throws JSONException {
		JSONObject pushJson = new JSONObject();
		pushJson.put(Title, "App42 Push Notification");
		pushJson.put(NotifyCode, PushCode.Basic.getCode());
		pushJson.put(
				Message,
				"Hey I am using App42 Push Notification API for Android as well as for Wearable Notification.");
		return pushJson;
	}

```
__2.__ Image based Push Notification (image should reside in assets folder of sample)
```
static JSONObject buildImageJson() throws JSONException {
		JSONObject pushJson = new JSONObject();
		pushJson.put(Title, "App42 Push Notification");
		pushJson.put(NotifyCode, PushCode.Image.getCode());
		pushJson.put(
				Message,
				"Hey I am using App42 Push Notification API for Android as well as for Wearable Notification.");
		pushJson.put(Image, DefImage);
		return pushJson;
	}
```
__3.__ Big Text based Push Notification
```
static JSONObject buildBigTextJson() throws JSONException {
		JSONObject pushJson = new JSONObject();
		pushJson.put(Title, "App42 Push Notification");
		pushJson.put(Message, "App42 Push Notification API");
		pushJson.put(BigText, BigTextContent);
		pushJson.put(NotifyCode, PushCode.BigText.getCode());
		return pushJson;
	}
```
__4.__ Multiple Page based Push Notification
 ```
	static JSONObject buildMultiPageJson() throws JSONException {
		JSONObject pushJson = new JSONObject();
		pushJson.put(Title, "Shephertz Technology");
		pushJson.put(NotifyCode, PushCode.MultiPage.getCode());
		pushJson.put(
				Message,
				"Shephertz provides complete cloud EcoSystem for App, Game and Web development. ");
		JSONArray pushPages = new JSONArray();
		pushPages.put(getApp42Json());
		pushPages.put(getAppWarpJson());
		pushPages.put(getApp42PaasJson());
		pushPages.put(getAppClayJson());
		pushPages.put(getAppHypeJson());
		pushJson.put(Pages, pushPages);
		pushJson.put(Image, DefImage);
		return pushJson;
	}
	//Page Notification like
	
	private static JSONObject getApp42Json() throws JSONException {
		JSONObject app42 = new JSONObject();
		app42.put(PageTitle, "App42");
		app42.put(pageSummary,
				"App42 Complete cloud API for differnet applications.");
		app42.put(
				PageContent,
				"App42 Provides complete cloud API for application development in different SDKs e.g \n Push Notification \n LeaderBoard \n SocialService \n File Storage \n Custom Code");
		return app42;
	}
 ```
  __Parsing Push Notification message when receive on Device side__ When the Push Notification message that we sent in above step is received on Android device side we have to parse the notification message accordingly and generate Notfication UI accordingly that is explain in next step.This code is written in Utils.java file of sample project.
   ```
   static App42Push getApp42Push(String message) {
		App42Push app42Push = null;
		try {
			JSONObject pushJson = new JSONObject(message);
			app42Push = getEventNotification(pushJson,
					PushCode.getByCode(pushJson.optInt(NotifyCode, 0)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			app42Push = new App42Push(message, "App42 Push Notification",
					PushCode.Basic);
		}
		return app42Push;
	}
	private static App42Push getEventNotification(JSONObject pushJson,
			PushCode pushCode) throws JSONException {
		App42Push app42Push = new App42Push(pushJson.optString(Message,
				DefMessage), pushJson.optString(Title, DefTitle), pushCode);
		if (pushCode == PushCode.BigText) {
			app42Push.setBigText(pushJson.optString(Title, BigTextContent));
		} else if (pushCode == PushCode.Image) {
			app42Push.setImage(pushJson.optString(Image, DefImage));
		} else if (pushCode == PushCode.MultiPage) {
			app42Push.setImage(pushJson.optString(Image, DefImage));
			JSONArray pageArray = pushJson.getJSONArray(Pages);
			ArrayList<App42Push.PageNotification> pushPages = new ArrayList<PageNotification>();
			int length = pageArray.length();
			for (int i = 0; i < length; i++) {
				JSONObject jsonPage = pageArray.getJSONObject(i);
				PageNotification page = app42Push.createPageNotification(
						jsonPage.optString(PageTitle, DefTitle),
						jsonPage.optString(pageSummary, DefMessage),
						jsonPage.optString(PageContent, BigTextContent));
				pushPages.add(page);
			}
			app42Push.setPageNotifications(pushPages);
		}
		return app42Push;
	}
	
  ```
__Building Notification UI for Android Wear and device__ After prasing the message in above step we have to build Notification UI accordingly the type of message sent using App42 API. All sample code for Notification UI generation in written in NotificationBuilder.java file.
 
__1.__ Builiding Common Wearable Notification UI
  
 ```
 private  WearableNotifications.Builder buildWearableNotification(
			Context context, App42Push app42Push) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context).setContentText(app42Push.getMessage())
				.setContentTitle(app42Push.getTitle())
				.setSmallIcon(R.drawable.logo);
		WearableNotifications.Builder wearableBuilder = new WearableNotifications.Builder(
				builder);
		return wearableBuilder;
	}
 ```
__2.__ Adding Action on Notification Click
  
 ```
 private  NotificationCompat.Action getAction(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra(App42GCMService.EXTRA_MESSAGE, currentMsg);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		return new NotificationCompat.Action.Builder(R.drawable.ic_result_open,
				"Open on Phone", pendingIntent).build();
	}
 ```
__3.__ Building basic Notification UI
 
 ```
 private  Notification buildBasicNotification(Context context,
			App42Push app42Push) {
		WearableNotifications.Builder builder = buildWearableNotification(context,
				app42Push);
		builder.getCompatBuilder().addAction(getAction(context));
		return builder.build();
	}
 ```
__4.__ Building Image based Notification UI
 
 ```
 private  Notification buildImageNotification(Context context,
			App42Push app42Push) {
		NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
		Bitmap imageBitmap =null;
		if (app42Push.getImage() != null) {
			imageBitmap = Bitmap.createScaledBitmap(
					Utils.loadBitmapAsset(context, app42Push.getImage()),
					ImageWidth, Imageheight, false);
		}
		style.bigPicture(imageBitmap);
		style.setBigContentTitle(app42Push.getTitle());
		style.setSummaryText("");
		WearableNotifications.Builder builder = buildWearableNotification(context,
				app42Push);
		builder.getCompatBuilder().addAction(getAction(context));
		builder.getCompatBuilder().setStyle(style);
		builder.build();
		return builder.build();
	}
 ```
__5.__ Building Big Text based Notification UI
 
 ```
private  Notification buildBigNotification(Context context,
			App42Push app42Push) {
		NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
		style.bigText(app42Push.getbigText());
		style.setBigContentTitle(app42Push.getTitle());
		style.setSummaryText("");
		WearableNotifications.Builder builder = buildWearableNotification(context,
				app42Push);
		builder.getCompatBuilder().addAction(getAction(context));
		builder.getCompatBuilder().setStyle(style);
		builder.getCompatBuilder().setContentText(app42Push.getbigText());
		return builder.build();
	}
 ```
__6.__ Building Multiple Page Notification UI
 
 ```
 private  Notification buildMultiPageNotification(Context context,
			App42Push app42Push) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context);
		ArrayList<Notification> notificationPages = buildNotificationPages(
				app42Push.getPageNotifications(), context);
		if (app42Push.getImage() != null) {
			Bitmap imageBitmap = Bitmap.createScaledBitmap(
					Utils.loadBitmapAsset(context, app42Push.getImage()),
					ImageWidth, Imageheight, false);
			builder.setLargeIcon(imageBitmap);
		}
		builder.setContentTitle(app42Push.getTitle());
		builder.setContentText(app42Push.getMessage());
		builder.setSmallIcon(R.drawable.logo);
		Notification notification = builder.extend(
				new NotificationCompat.WearableExtender()
						.addPages(notificationPages)).build();
		return notification;
	}
	private  ArrayList<Notification> buildNotificationPages(
			ArrayList<PageNotification> pageNotifications, Context context) {
		ArrayList<Notification> notificationPages = new ArrayList<Notification>();
		if (pageNotifications != null) {
			int size = pageNotifications.size();
			for (int i = 0; i < size; ++i) {
				PageNotification pageNotify = pageNotifications.get(i);
				NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
				style.bigText(pageNotify.getPageContent());
				style.setBigContentTitle(pageNotify.getPageTitle());
				style.setSummaryText("");
				NotificationCompat.Builder notificationPage = new NotificationCompat.Builder(
						context);
				notificationPage.setStyle(style);
				notificationPage.setSmallIcon(R.drawable.logo);
				notificationPages.add(notificationPage.build());
			}
		}
		return notificationPages;
	}
 ```
__Customizing in your existing Android application__ If you want to customize your own sample for App42 Push Notification in Android Wearable you can use sample code as well as make these change in you Android Manifest.xml file.
 ```
 //Add permissions
 <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.GET_ACCOUNTS" />
    <!-- Keeps the processor from sleeping when a message is received. -->
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <!--
     Creates a custom permission so only this app can receive its messages.
     NOTE: the permission *must* be called PACKAGE.permission.C2D_MESSAGE,
           where PACKAGE is the application's package name.
    -->
    <permission
        android:name="<Your application package>.permission.C2D_MESSAGE"
        android:protectionLevel="signature" />
    <uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
    
    //Add App42GCMReceiver
    <receiver
            android:name="com.app42.wear.notification.App42GCMReceiver"
            android:permission="com.google.android.c2dm.permission.SEND" >
            <intent-filter>

                <!-- Receives the actual messages. -->
                <action android:name="com.google.android.c2dm.intent.RECEIVE" />
                <!-- Receives the registration id. -->
                <action android:name="com.google.android.c2dm.intent.REGISTRATION" />
                <!-- Your package name here -->
                <category android:name="Your application package name" />
            </intent-filter>
        </receiver>
    
    //Add App42GCMService
      <service android:name="com.app42.wear.notification.App42GCMService" >
        </service>
 
  ```
