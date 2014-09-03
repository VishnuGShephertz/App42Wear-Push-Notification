package com.app42.wear.notification;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.app42.wear.notification.App42Push.PageNotification;

public class Utils {
	private static final String Title = "title";
	private static final String Message = "message";
	private static final String BigText = "bigText";
	private static final String Image = "image";
	private static final String PageTitle = "pageTitle";
	private static final String pageSummary = "pageSummary";
	private static final String PageContent = "pageContent";
	private static final String Pages = "pages";
	private static final String NotifyCode = "pushCode";

	private static final String DefMessage = "Hey I am using App42 PushNotification in my Android as well as in Android Wear.";
	private static final String DefTitle = "App42 PushNotification";
	private static final String DefImage = "shephertz.png";

	private static final String BigTextContent = "storeDeviceToken  \n sendPushMessageToUser \n sendPushMessageToAll \n createChannelForApp \n subscribeToChannel \n sendPushMessageToChannel";

	static JSONObject buildBasicJson() throws JSONException {
		JSONObject pushJson = new JSONObject();
		pushJson.put(Title, "App42 PushNotification");
		pushJson.put(NotifyCode, PushCode.Basic.getCode());
		pushJson.put(
				Message,
				"Hey I am using App42 PushNotification API for Android as well as for Wearable Notification.");
		return pushJson;
	}

	static JSONObject buildImageJson() throws JSONException {
		JSONObject pushJson = new JSONObject();
		pushJson.put(Title, "App42 PushNotification");
		pushJson.put(NotifyCode, PushCode.Image.getCode());
		pushJson.put(
				Message,
				"Hey I am using App42 PushNotification API for Android as well as for Wearable Notification.");
		pushJson.put(Image, DefImage);
		return pushJson;
	}

	static JSONObject buildBigTextJson() throws JSONException {
		JSONObject pushJson = new JSONObject();
		pushJson.put(Title, "App42 PushNotification");
		pushJson.put(Message, "App42 PushNotification API");
		pushJson.put(BigText, BigTextContent);
		pushJson.put(NotifyCode, PushCode.BigText.getCode());
		return pushJson;
	}

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

	private static JSONObject getAppWarpJson() throws JSONException {
		JSONObject appWarp = new JSONObject();
		appWarp.put(PageTitle, "AppWarp");
		appWarp.put(pageSummary, "Massive Multiplayer Gaming Engine");
		appWarp.put(
				PageContent,
				"AppWarp provides complete Game API for real time Multiplayer Game Development e.g \n Turn-Based \n Match Making \n Challenge Base \n In App Chat");
		return appWarp;
	}

	private static JSONObject getApp42Json() throws JSONException {
		JSONObject app42 = new JSONObject();
		app42.put(PageTitle, "App42");
		app42.put(pageSummary,
				"App42 Complete cloud API for differnet applications.");
		app42.put(
				PageContent,
				"App42 Provides complete cloud API for application development in different SDKs e.g \n PushNotification \n LeaderBoard \n SocialService \n File Storage \n Custom Code");
		return app42;
	}

	private static JSONObject getAppClayJson() throws JSONException {
		JSONObject appClay = new JSONObject();
		appClay.put(PageTitle, "AppClay");
		appClay.put(pageSummary, "Create your own application in minutes.");
		appClay.put(
				PageContent,
				"You can create your own application by drag and drop, provides various features as well for your application.");
		return appClay;
	}

	private static JSONObject getAppHypeJson() throws JSONException {
		JSONObject appHype = new JSONObject();
		appHype.put(PageTitle, "AppHype");
		appHype.put(pageSummary, "AppHype Ad Network for mobile app promotion.");
		appHype.put(
				PageContent,
				"AppHype opens an easy gateway for Android developers to  serve quality Video & Interstitial Ads. Developers can cross promote within their own Apps without spending a penny. ");
		return appHype;
	}

	private static JSONObject getApp42PaasJson() throws JSONException {
		JSONObject paasJson = new JSONObject();
		paasJson.put(PageTitle, "App42Paas");
		paasJson.put(pageSummary, "App42 Platform as a Service");
		paasJson.put(
				pageSummary,
				"App42Paas provides as easy gateway for your Web app deployment.You need not to worry of OS installations, patches, security, firewalls and scalability.It is Highly Available and Reliable Platform.");
		return paasJson;
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

	static App42Push getApp42Push(String message) {
		App42Push app42Push = null;
		try {
			JSONObject pushJson = new JSONObject(message);
			app42Push = getEventNotification(pushJson,
					PushCode.getByCode(pushJson.optInt(NotifyCode, 0)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			app42Push = new App42Push(message, "App42 PushNotification",
					PushCode.Basic);
		}
		return app42Push;
	}

	public static Bitmap loadBitmapAsset(Context context, String asset) {
		InputStream is = null;
		Bitmap bitmap = null;
		try {
			is = context.getAssets().open(asset);
			if (is != null) {
				bitmap = BitmapFactory.decodeStream(is);
			}
		} catch (IOException e) {
			Log.e("App42", e.toString());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					Log.e("App42", "Cannot close InputStream: ", e);
				}
			}
		}
		return bitmap;
	}
}
