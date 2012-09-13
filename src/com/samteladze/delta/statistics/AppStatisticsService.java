package com.samteladze.delta.statistics;

import android.content.Intent;
import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.samteladze.delta.statistics.DataModel.StatisticsFormat;
import com.samteladze.delta.statistics.Utils.CommunicationManager;
import com.samteladze.delta.statistics.Utils.FileManager;

public class AppStatisticsService extends WakefulIntentService
{
	public AppStatisticsService()
	{
		super("AppStatisticsService");
	}

	@Override
	protected void doWakefulWork(Intent intent)
	{
		FileManager.Log("AppStatisticsService", "Starting the service.");
		
        AppStatisticsProvider appStatisticsProvider = new AppStatisticsProvider(this.getApplicationContext());
        appStatisticsProvider.CollectStatistics(); 
        
        FileManager.SaveAppStatistics(appStatisticsProvider.GetStatistics(StatisticsFormat.UserFriendly));
        CommunicationManager.SendStatisticsToServer();     		
	}
}
