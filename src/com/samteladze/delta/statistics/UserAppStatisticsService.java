package com.samteladze.delta.statistics;

import android.content.Context;
import android.content.Intent;

import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.samteladze.delta.statistics.datamodel.StatisticsFormat;
import com.samteladze.delta.statistics.utils.FileManager;
import com.samteladze.delta.statistics.utils.LogManager;

public class UserAppStatisticsService extends WakefulIntentService
{	
	public UserAppStatisticsService()
	{
		super("UserAppStatisticsService");
	}

	@Override
	protected void doWakefulWork(Intent intent)
	{
		Context context = this.getApplicationContext();
		
		LogManager.Log(UserAppStatisticsService.class.getSimpleName(), "Starting the service");
		
        AppStatisticsProvider appStatisticsProvider = new AppStatisticsProvider(context);
        appStatisticsProvider.CollectStatistics(true); 
        
        FileManager.SaveStatistics(appStatisticsProvider.GetStatistics(StatisticsFormat.UserFriendly), FileManager.USER_APP_STAT_FILE_PATH, true);       
	}
}
