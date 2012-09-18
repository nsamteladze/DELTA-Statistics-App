package com.samteladze.delta.statistics;

import com.samteladze.delta.statistics.utils.LogManager;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnNotificationDeleteReceiver extends BroadcastReceiver 
{
    @Override
    public void onReceive(Context context, Intent intent) 
    {
    	LogManager.Log(OnNotificationDeleteReceiver.class.getSimpleName(), "Delete intent received");
    	NotificationManager mgrNotification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    	mgrNotification.cancel(AppStatisticsProvider.FINISH_NOTIFICATION_ID);
    }
}