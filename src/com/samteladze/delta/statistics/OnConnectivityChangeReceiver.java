package com.samteladze.delta.statistics;

import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.samteladze.delta.statistics.utils.LogManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnConnectivityChangeReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{		
		WakefulIntentService.sendWakefulWork(context, NetStatisticsService.class);
		
		LogManager.Log(OnConnectivityChangeReceiver.class.getSimpleName(), "Network state was changed. Starting NetStatisticsService");
	}

}
