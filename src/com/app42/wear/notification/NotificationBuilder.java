package com.app42.wear.notification;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preview.support.wearable.notifications.WearableNotifications;
import android.support.v4.app.NotificationCompat;
import com.app42.wear.notification.App42Push.PageNotification;
import com.example.app42pushnotification.R;

/**
 * 
 * @author Vishnu Garg
 * @Shephertz
 * 
 */
public class NotificationBuilder {
	private static String currentMsg="";

	private static WearableNotifications.Builder getNotification(
			Context context, App42Push app42Push) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context).setContentText(app42Push.getMessage())
				.setContentTitle(app42Push.getTitle())
				.setSmallIcon(R.drawable.ic_launcher);
		WearableNotifications.Builder wearableBuilder = new WearableNotifications.Builder(
				builder);
		return wearableBuilder;
	}

	private static Notification getBasicNotification(Context context,
			App42Push app42Push) {
		WearableNotifications.Builder builder = getNotification(context,
				app42Push);
		builder.getCompatBuilder().addAction(getAction(context));
		return builder.build();
	}

	private static NotificationCompat.Action getAction(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra(App42GCMService.EXTRA_MESSAGE, currentMsg);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		return new NotificationCompat.Action.Builder(R.drawable.ic_result_open,
				"Open on Phone", pendingIntent).build();
	}

	private static Notification getImageNotification(Context context,
			App42Push app42Push) {

		NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
		style.bigPicture(getBitmapFromAssets(context, app42Push.getImage()));
		style.setBigContentTitle(app42Push.getTitle());
		style.setSummaryText(app42Push.getMessage());
		WearableNotifications.Builder builder = getNotification(context,
				app42Push);
		builder.getCompatBuilder().addAction(getAction(context));
		builder.getCompatBuilder().setStyle(style);
		builder.build();
		return builder.build();
	}

	private static Notification getBigNotification(Context context,
			App42Push app42Push) {
		NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
		style.bigText(app42Push.getbigText());
		style.setBigContentTitle(app42Push.getTitle());
		style.setSummaryText(app42Push.getMessage());
		WearableNotifications.Builder builder = getNotification(context,
				app42Push);
		builder.getCompatBuilder().addAction(getAction(context));
		builder.getCompatBuilder().setStyle(style);
		return builder.build();
	}

	public static Bitmap getBitmapFromAssets(Context context, String imageName) {
		AssetManager assetManager = context.getAssets();
		InputStream istr;
		try {
			istr = assetManager.open(imageName);
			Bitmap bitmap = BitmapFactory.decodeStream(istr);
			return bitmap;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private static Notification getMultiPageNotification(Context context,
			App42Push app42Push) {
		ArrayList<Notification> notificationPages = new ArrayList<Notification>();
		ArrayList<PageNotification> pageNotifications = new ArrayList<PageNotification>();
		int size = pageNotifications.size();
		for (int i = 0; i < size; ++i) {
			PageNotification pageNotify = pageNotifications.get(i);
			NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
			style.bigText(pageNotify.getPageContent());
			style.setBigContentTitle(pageNotify.getPageTitle());
			style.setSummaryText(pageNotify.getpageSummary());
			NotificationCompat.Builder builder = new NotificationCompat.Builder(
					context);
			builder.setStyle(style);
			notificationPages.add(builder.build());
		}
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context);
		builder.setLargeIcon(getBitmapFromAssets(context, app42Push.getImage()));

		builder.setContentTitle(app42Push.getTitle());
		builder.setContentText(app42Push.getMessage());
		builder.setSmallIcon(R.drawable.ic_launcher);

		Notification notification = builder.extend(
				new NotificationCompat.WearableExtender()
						.addPages(notificationPages)).build();
		return notification;
	}

	static Notification generateNotification(Context context,
			App42Push app42Push,String message) {
		currentMsg=message;
		if (app42Push.getCode() == PushCode.Basic) {
			return getBasicNotification(context, app42Push);
		} else if (app42Push.getCode() == PushCode.BigText) {
			return getBigNotification(context, app42Push);
		} else if (app42Push.getCode() == PushCode.Image) {
			return getImageNotification(context, app42Push);
		} else if (app42Push.getCode() == PushCode.MultiPage) {
			return getMultiPageNotification(context, app42Push);
		}
		return null;

	}
}
