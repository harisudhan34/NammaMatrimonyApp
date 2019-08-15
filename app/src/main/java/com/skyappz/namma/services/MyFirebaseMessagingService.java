package com.skyappz.namma.services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.skyappz.namma.database.AppDatabaseHelper;
import com.skyappz.namma.utils.AppConstants;
import com.skyappz.namma.utils.NotificationUtils;
import com.skyappz.namma.utils.Preferences;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    Preferences preferences;
    AppDatabaseHelper appDatabaseHelper;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        preferences = new Preferences(getApplicationContext());
        appDatabaseHelper = new AppDatabaseHelper(getApplicationContext());

        if (remoteMessage == null)
            return;

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                if (preferences.isLoginned()) {
                    Map<String, String> params = remoteMessage.getData();
                    handleNotificationsParams(params);
                    onMessageReceived();
                    Intent intent = new Intent();
                    intent.setAction("com.skyappz.message");
                    sendBroadcast(intent);
                }

            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void onMessageReceived() {
        int messageCount = preferences.getMessageCount();
        messageCount++;
        preferences.setMessageCount(messageCount);
    }

    private void handleNotificationsParams(Map<String, String> params) {
        /*Notifications notification = getNotificationFromResponse(params);
        ArrayList<Notifications> notifications = new ArrayList<>();
        notifications.add(notification);
        appDatabaseHelper.addNotification(notifications);
        Intent resultIntent = new Intent(getApplicationContext(), PhotosActivity.class);
        showNotificationMessage(getApplicationContext(), notification.getTitle(), notification.getMsg(), "" + System.currentTimeMillis(), resultIntent);
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {


        } else {

        }*/
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(AppConstants.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            // If the app is in background, firebase itself handles the notification
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

}