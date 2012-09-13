package com.samteladze.delta.statistics;

import com.samteladze.delta.statistics.DataModel.StatisticsFormat;
import com.samteladze.delta.statistics.Utils.FileManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnConnectivityChangeReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{	
		NetStatisticsProvider netStatProvider = new NetStatisticsProvider(context.getApplicationContext());
		netStatProvider.CollectStatistics();
		
		FileManager.SaveNetStatistics(netStatProvider.GetStatistics(StatisticsFormat.UserFriendly));
	}

}
