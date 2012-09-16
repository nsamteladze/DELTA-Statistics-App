package com.samteladze.delta.statistics;

import com.samteladze.delta.statistics.utils.LogManager;

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
		
		LogManager.Log(OnAlarmReceiver.class.getSimpleName(), "Alarm was received. Starting AppStatisticsService");
	}
}
