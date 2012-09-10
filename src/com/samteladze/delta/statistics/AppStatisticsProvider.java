package com.samteladze.delta.statistics;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.RemoteException;
import android.provider.Settings.Secure;

import com.samteladze.delta.statistics.DataModel.*;
import com.samteladze.delta.statistics.Utils.*;

public class AppStatisticsProvider
{
	private Context _context;
	private ArrayList<AppStatistics> _statistics;
	
	public AppStatisticsProvider(Context context)
	{
		_context = context;
		_statistics = new ArrayList<AppStatistics>();
	}
		
	public void CollectStatistics()
	{
		// Get PackageManager and list of all the installed applications
		final PackageManager packageManager = _context.getPackageManager();
        List<ApplicationInfo> installedApplications = 
        		packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        
        final Semaphore countCodeSizeSemaphore = new Semaphore(1, true);
        
        for (ApplicationInfo appInfo : installedApplications)
        {        	
        	if (!IsSystemApp(appInfo) || IsUpdatedSystemApp(appInfo))
        	{
        		try
				{
					countCodeSizeSemaphore.acquire();
				} 
        		catch (InterruptedException e)
				{
					e.printStackTrace(System.err);
				}
        		
        		final AppStatistics AppStatistics = new AppStatistics();
        		
        		AppStatistics.packageName = appInfo.packageName;    		
        		AppStatistics.appName = appInfo.loadLabel(packageManager).toString();
        		       		
        		try
        		{       		
	        		Method getPackageSizeInfo = packageManager.getClass().getMethod(
	    		            "getPackageSizeInfo", String.class, IPackageStatsObserver.class);
	    	        	
	        		getPackageSizeInfo.invoke(packageManager, appInfo.packageName,
	    		            new IPackageStatsObserver.Stub() 
	    		        	{    		        	    
	    		        		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
	    		                    throws RemoteException 
	    	                    {
	    		        			AppStatistics.codeSize = pStats.codeSize;
	    		        			countCodeSizeSemaphore.release();
	    		                }
	    		            }); 
        		}
        		catch (Exception e)
        		{
        			e.printStackTrace(System.err);
        		}
        		
        		try 
        		{
        			PackageInfo packageInfo = packageManager.getPackageInfo(appInfo.packageName, 0);					
					
        			AppStatistics.versionName = packageInfo.versionName;	        		
        			AppStatistics.versionCode = packageInfo.versionCode;
        			AppStatistics.firstIntallTime = packageInfo.firstInstallTime;
        			AppStatistics.lastUpdateTime = packageInfo.lastUpdateTime;
				} 
        		catch (Exception exception) 
        		{
        			exception.printStackTrace(System.err);
				}   
        		
        		_statistics.add(AppStatistics);
        	}        	
        } 
        
        FileManager.Log("AppStatisticsProvider | Statistics was collected.");
	}
	
	public String GetStatistics(StatisticsFormat format)
	{
		String statisticsStr = "";
		
		statisticsStr += GetDeviceID(format);
		statisticsStr += GetNumberOfApps(format);
		
		for (AppStatistics appStatistics : _statistics)
		{
			statisticsStr += appStatistics.Format(format);
		}
		
		if (format == StatisticsFormat.Machine)
		{
			statisticsStr += Constants.LayoutEndSection;
		}
		
		return statisticsStr;
	}
	
	private String GetNumberOfApps(StatisticsFormat format)
	{
		String generatedStr = "";
		
		if (format == StatisticsFormat.UserFriendly)
		{
			generatedStr += Constants.StatisticsNumberOfApps + _statistics.size() + Constants.LayoutNextLine;
			generatedStr += Constants.LayoutSeparator;
		}
		else if (format == StatisticsFormat.Machine)
		{
			generatedStr += _statistics.size() + Constants.LayoutNextLine;
			generatedStr += Constants.LayoutEndSection;
		}
		
		return generatedStr;
	}
	
	private String GetDeviceID(StatisticsFormat format)
	{
		String generatedStr = "";
		String deviceID = Secure.getString(_context.getContentResolver(), Secure.ANDROID_ID);
		
		if (format == StatisticsFormat.UserFriendly)
		{
			generatedStr += Constants.StatisticsDeviceID + deviceID + Constants.LayoutNextLine;
			generatedStr += Constants.LayoutSeparator;
		}
		else if (format == StatisticsFormat.Machine)
		{
			generatedStr += deviceID + Constants.LayoutNextLine;
			generatedStr += Constants.LayoutEndSection;
		}
		
		return generatedStr;
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
