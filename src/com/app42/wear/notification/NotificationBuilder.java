package com.app42.wear.notification;

import java.util.ArrayList;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
	private  String currentMsg = "";
	public  final int ImageWidth = 280;
	public  final int Imageheight = 280;

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

	private  Notification buildBasicNotification(Context context,
			App42Push app42Push) {
		WearableNotifications.Builder builder = buildWearableNotification(context,
				app42Push);
		builder.getCompatBuilder().addAction(getAction(context));
		return builder.build();
	}

	private  NotificationCompat.Action getAction(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra(App42GCMService.EXTRA_MESSAGE, currentMsg);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		return new NotificationCompat.Action.Builder(R.drawable.ic_result_open,
				"Open on Phone", pendingIntent).build();
	}

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
		builder.addAction(getAction(context));
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
	 Notification generateNotification(Context context,
			App42Push app42Push, String message) {
		currentMsg = message;
		if (app42Push.getCode() == PushCode.Basic) {
			return buildBasicNotification(context, app42Push);
		} else if (app42Push.getCode() == PushCode.BigText) {
			return buildBigNotification(context, app42Push);
		} else if (app42Push.getCode() == PushCode.Image) {
			return buildImageNotification(context, app42Push);
		} else if (app42Push.getCode() == PushCode.MultiPage) {
			return buildMultiPageNotification(context, app42Push);
		}
		return null;

	}
}
