package com.samteladze.delta.statistics;

import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

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
        
        for (ApplicationInfo appInfo : installedApplications)
        {
        	if (!IsSystemApp(appInfo))
        	{
        		statisticsNotSystem += appInfo.packageName;
        		statisticsNotSystem += Constants.NextLine;
        		statisticsNotSystem += appInfo.loadLabel(packageManager);
        		statisticsNotSystem += Constants.NextLine;
        		statisticsNotSystem += Constants.EndItem;
	                	     	
	        	++numberOfNotSystemAppsCounter;
        	}
        	else if (IsUpdatedSystemApp(appInfo))
        	{
        		statisticsSystemUpdated += appInfo.packageName;
        		statisticsSystemUpdated += Constants.NextLine;
        		statisticsSystemUpdated += appInfo.loadLabel(packageManager);
        		statisticsSystemUpdated += Constants.NextLine;
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
