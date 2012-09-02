package com.samteladze.delta.statistics;


import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.format.DateFormat;

public class InstalledAppsStatisticsProvider implements IStatisticsProvider 
{
	private Context _context;
	
	public InstalledAppsStatisticsProvider(Context context)
	{
		this._context = context;
	}
	
	public String GetStatistics() 
	{
		final PackageManager packageManager = _context.getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        
        String statisticsNotSystem = "";
        String statisticsSystemUpdated = "";
        
        int numberOfNotSystemAppsCounter = 0;
        int numberOfSystemUpdatedAppsCounter = 0;
        
        PackageInfo packageInfo = null;
        String dateString = null;
        
        for (ApplicationInfo appInfo : installedApplications)
        {        	
        	if (!IsSystemApp(appInfo))
        	{
        		statisticsNotSystem += appInfo.packageName;
        		statisticsNotSystem += Constants.NextLine;
        		
        		statisticsNotSystem += appInfo.loadLabel(packageManager);
        		statisticsNotSystem += Constants.NextLine;
        		
        		try 
        		{
					packageInfo = packageManager.getPackageInfo(appInfo.packageName, 0);
					
					dateString = DateFormat.format("MM/dd/yyyy", new Date(packageInfo.firstInstallTime)).toString();
					
					statisticsNotSystem += packageInfo.versionName;
	        		statisticsNotSystem += Constants.NextLine;
	        		
	        		statisticsNotSystem += packageInfo.versionCode;
	        		statisticsNotSystem += Constants.NextLine;
	        		
	        		statisticsNotSystem += packageInfo.firstInstallTime;
	        		statisticsNotSystem += Constants.NextLine;
	        		
	        		dateString = DateFormat.format(Constants.DateFormat, new Date(packageInfo.firstInstallTime)).toString();
	        		statisticsNotSystem += dateString;
	        		statisticsNotSystem += Constants.NextLine;
	        		
	        		statisticsNotSystem += packageInfo.lastUpdateTime;
	        		statisticsNotSystem += Constants.NextLine;
	        		
	        		dateString = DateFormat.format(Constants.DateFormat, new Date(packageInfo.lastUpdateTime)).toString();
	        		statisticsNotSystem += dateString;
	        		statisticsNotSystem += Constants.NextLine;
				} 
        		catch (NameNotFoundException e) 
        		{
					e.printStackTrace(System.err);
					
					for (int i = 0; i < Constants.NumberOfFieldsFromPackageInfo; ++i)
					{
						statisticsNotSystem += Constants.NotFound;
		        		statisticsNotSystem += Constants.NextLine;
					}
				}
        		        		
        		statisticsNotSystem += Constants.EndItem;
	                	     	
	        	++numberOfNotSystemAppsCounter;
        	}
        	else if (IsUpdatedSystemApp(appInfo))
        	{
        		statisticsSystemUpdated += appInfo.packageName;
        		statisticsSystemUpdated += Constants.NextLine;
        		
        		statisticsSystemUpdated += appInfo.loadLabel(packageManager);
        		statisticsSystemUpdated += Constants.NextLine;
        		
        		try 
        		{
					packageInfo = packageManager.getPackageInfo(appInfo.packageName, 0);
					
					statisticsSystemUpdated += packageInfo.versionName;
					statisticsSystemUpdated += Constants.NextLine;
	        		
					statisticsSystemUpdated += packageInfo.versionCode;
					statisticsSystemUpdated += Constants.NextLine;
	        		
					statisticsSystemUpdated += packageInfo.firstInstallTime;
					statisticsSystemUpdated += Constants.NextLine;
					
					dateString = DateFormat.format(Constants.DateFormat, new Date(packageInfo.firstInstallTime)).toString();
					statisticsSystemUpdated += dateString;
					statisticsSystemUpdated += Constants.NextLine;
	        		
					statisticsSystemUpdated += packageInfo.lastUpdateTime;
					statisticsSystemUpdated += Constants.NextLine;
					
					dateString = DateFormat.format(Constants.DateFormat, new Date(packageInfo.lastUpdateTime)).toString();
					statisticsSystemUpdated += dateString;
					statisticsSystemUpdated += Constants.NextLine;
				} 
        		catch (NameNotFoundException e) 
        		{
					e.printStackTrace(System.err);
					
					for (int i = 0; i < Constants.NumberOfFieldsFromPackageInfo; ++i)
					{
						statisticsSystemUpdated += Constants.NotFound;
						statisticsSystemUpdated += Constants.NextLine;
					}
				}
        		
        		statisticsSystemUpdated += Constants.EndItem;
        		
        		++numberOfSystemUpdatedAppsCounter;
        	}
        }        
        
        
        statisticsNotSystem += Constants.EndSection;
        statisticsNotSystem += numberOfNotSystemAppsCounter;
        statisticsNotSystem += Constants.NextLine;
        statisticsNotSystem += Constants.EndSection;
        
        statisticsSystemUpdated += Constants.EndSection;
        statisticsSystemUpdated += numberOfSystemUpdatedAppsCounter;
        statisticsSystemUpdated += Constants.NextLine;
        statisticsSystemUpdated += Constants.EndSection;
        
        return (statisticsNotSystem + statisticsSystemUpdated);
	}
	
	private boolean IsSystemApp(ApplicationInfo appInfo) 
	{
	    return ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);	    
	}
	
	private boolean IsUpdatedSystemApp(ApplicationInfo appInfo) 
	{
	    return ((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);	    
	}

}
