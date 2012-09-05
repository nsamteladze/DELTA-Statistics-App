package com.samteladze.delta.statistics.DataModel;

import java.util.Date;

import android.text.format.DateFormat;

import com.samteladze.delta.statistics.Utils.*;

public class AppStatistics 
{
	public String packageName;
	public String appName;	
	public String versionName;
	public int versionCode;
	public long firstIntallTime;
	public long lastUpdateTime;
	public long codeSize;
	
	public AppStatistics()
	{
		packageName = Constants.EmptyStr;
		appName = Constants.EmptyStr;
		versionName = Constants.EmptyStr;
		versionCode = 0;
		firstIntallTime = 0;
		lastUpdateTime = 0;
		codeSize = 0;
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
		
		generatedStatistics += Constants.StatisticsPackageNameText + packageName + Constants.LayoutNextLine;
		generatedStatistics += Constants.StatisticsAppNameText + appName + Constants.LayoutNextLine;
		generatedStatistics += Constants.StatisticsVersionNameText + versionName + Constants.LayoutNextLine;
		generatedStatistics += Constants.StatisticsVersionCodeText + versionCode + Constants.LayoutNextLine;
		generatedStatistics += Constants.StatisticsCodeSizeText + codeSize + Constants.LayoutNextLine;
		generatedStatistics += Constants.StatisticsFirstInstallTimeText + 
							   DateFormat.format(Constants.DateFormat, new Date(firstIntallTime)).toString() +
							   Constants.LayoutNextLine;
		generatedStatistics += Constants.StatisticsLastUpdateTimeText + 
				   			   DateFormat.format(Constants.DateFormat, new Date(lastUpdateTime)).toString() +
				   			   Constants.LayoutNextLine;
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
		generatedStatistics += codeSize + Constants.LayoutNextLine;
		generatedStatistics += firstIntallTime + Constants.LayoutNextLine;
		generatedStatistics += lastUpdateTime + Constants.LayoutNextLine;
		generatedStatistics += Constants.LayoutEndItem;
		
		return generatedStatistics;
	}
}
