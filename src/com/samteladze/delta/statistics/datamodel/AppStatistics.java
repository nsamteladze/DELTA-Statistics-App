package com.samteladze.delta.statistics.datamodel;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.samteladze.delta.statistics.utils.*;

public class AppStatistics 
{
	public String packageName;
	public String appName;	
	public String versionName;
	public int versionCode;
	public long lastUpdateTime;
	
	public AppStatistics()
	{
		packageName = Constants.EmptyStr;
		appName = Constants.EmptyStr;
		versionName = Constants.EmptyStr;
		versionCode = 0;
		lastUpdateTime = 0;
	}
	
	public String Format(StatisticsFormat format)
	{
		if (format == StatisticsFormat.Machine)
		{
			return GenerateMachineStatistics();
		}
		else if (format == StatisticsFormat.UserFriendly)
		{
			return GenerateUserFriendlyStatistics(); 
		}
		
		return Constants.EmptyStr;
	}
	
	private String GenerateUserFriendlyStatistics()
	{
		String generatedStatistics = "";
		
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		generatedStatistics += Constants.StatisticsPackageNameText + packageName + Constants.LayoutNextLine;
		generatedStatistics += Constants.StatisticsAppNameText + appName + Constants.LayoutNextLine;
		generatedStatistics += Constants.StatisticsVersionNameText + versionName + Constants.LayoutNextLine;
		generatedStatistics += Constants.StatisticsVersionCodeText + versionCode + Constants.LayoutNextLine;
		generatedStatistics += Constants.StatisticsLastUpdateTimeText + 
				   			   dateFormat.format(new Date(lastUpdateTime)) + Constants.LayoutNextLine;
		generatedStatistics += Constants.LayoutSeparator;
		
		return generatedStatistics;
	}
	
	private String GenerateMachineStatistics()
	{
		String generatedStatistics = "";
		
		generatedStatistics += packageName + Constants.LayoutNextLine;
		generatedStatistics += appName + Constants.LayoutNextLine;
		generatedStatistics += versionName + Constants.LayoutNextLine;
		generatedStatistics += versionCode + Constants.LayoutNextLine;
		generatedStatistics += lastUpdateTime + Constants.LayoutNextLine;
		generatedStatistics += Constants.LayoutEndItem;
		
		return generatedStatistics;
	}
}
