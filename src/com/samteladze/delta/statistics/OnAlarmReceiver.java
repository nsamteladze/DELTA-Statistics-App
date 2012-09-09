package com.samteladze.delta.statistics;

import com.samteladze.delta.statistics.Utils.FileManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.commonsware.cwac.wakeful.WakefulIntentService;

public class OnAlarmReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		WakefulIntentService.sendWakefulWork(context, AppStatisticsService.class);
		
		FileManager.Log("OnAlarmReceiver | Alarm was received. Starting AppStatisticsService.");
	}
}
