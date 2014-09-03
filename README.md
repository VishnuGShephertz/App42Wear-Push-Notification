App42Wear-Push-Notification
===========================

This sample project explain how can we App42 PushNotification API in different ways to send Push Notification to device as well as sink them 
with Android Wearables.
>Please go through with [Android Wear Getting Started] (http://blogs.shephertz.com/2014/07/24/android-wear-getting-started/)if you are new to Android Wear.

Here you can learn how to configure different type of PushNotification using App42 PushNotification API on device as well as on Android Wearable.</br>
1. Basic Push Notification</br>
2. Image based Push Notification</br>
3. Big Content Based Push Notification</br>
4. Multiple Page based Push Notifications</br>


# Running Sample

This is a sample Android app is made by using App42  API. It uses PushNotification API of App42 platform.
Here are the few easy steps to run this sample app.

1. [Register] (https://apphq.shephertz.com/register) with App42 platform.
2. Create an app once, you are on Quick start page after registration.
3. If you are already registered, login to [AppHQ] (http://apphq.shephertz.com) console and create an app from App Manager Tab.
4. Download the project from [here] (https://github.com/VishnuGShephertz/App42Wear-Push-Notification/archive/master.zip) and unzip it.
5. Configure with Eclipse that have latest ADT (Android Developer Tool).
```
A. Import this project in Eclipse.
B. Check Android Dependencies and Android Private libraries in order and import section on Build Path.
```
6. Configure with Android Studio.
```
A. Import this project in your Android Studio.
B. Add library jar files from libs folder by adding library modules in Android Studio.
```
6. Import this project in your Android Studio.

```
A. Replace api-Key and secret-Key that you have received in step 2 or 3 at line number 39 and 40.
B. Replace your user-id by which you want to register your application for PushNotification at line number 31.
```

6. Create a project and get your Project Number from [google developer console](https://cloud.google.com/console/project). It would be available in Overview section of your created project.<div style="clear:both"></div>
7. Select your created project and click on APIs option in Google developer console and enable Google Cloud Messaging for Android service.<div style="clear:both"></div>
8. Click on Credentials(in APIs option) from left menu -> Create New Key -> Server Key.<div style="clear:both"></div>
9. Keep Accept requests from these server IP addresses as blank and click on Create button.<div style="clear:both"></div>
10. Go to [AppHQ] (http://apphq.shephertz.com) click on Push Notification and select Android Settings in Settings option.<div style="clear:both"></div>
11. Select your app and provider as GCM and copy server key that is generated in Google developer console in above step and submit it.<div style="clear:both"></div>
12. Open CommonUtilities.java file of sample project and make following changes.

```
A. Replace your GcmProjectNo by your Google Project No at line no90 
```
