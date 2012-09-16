package com.samteladze.delta.statistics.datamodel;

import com.samteladze.delta.statistics.utils.Constants;

import android.net.NetworkInfo;

public class NetStatistics
{
	public NetworkInfo activeNetInfo;
	public NetworkInfo wifiNetInfo;
	public NetworkInfo mobileNetInfo;
	
	public NetStatistics()
	{
		activeNetInfo = null;
		wifiNetInfo = null;
		mobileNetInfo = null;
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
		
		if (activeNetInfo != null)
		{
			generatedStatistics += Constants.StatisticsActiveNetTypeText + activeNetInfo.getTypeName() + Constants.LayoutNextLine;
			generatedStatistics += Constants.StatisticsActiveNetSubtypeText + activeNetInfo.getSubtypeName() + Constants.LayoutNextLine;
			generatedStatistics += Constants.StatisticsActiveNetCoarseStateText + activeNetInfo.getState().name() + Constants.LayoutNextLine;
			generatedStatistics += Constants.StatisticsActiveNetFineStateText + activeNetInfo.getDetailedState().name() + Constants.LayoutNextLine;
		}
		else
		{
			generatedStatistics += Constants.StatisticsNoActiveNetText + Constants.LayoutNextLine;
		}
				
		if (wifiNetInfo != null)
		{
			generatedStatistics += Constants.StatisticsWifiNetTypeText + wifiNetInfo.getTypeName() + Constants.LayoutNextLine;
			generatedStatistics += Constants.StatisticsWifiNetSubtypeText + wifiNetInfo.getSubtypeName() + Constants.LayoutNextLine;
			generatedStatistics += Constants.StatisticsWifiNetCoarseStateText + wifiNetInfo.getState().name() + Constants.LayoutNextLine;
			generatedStatistics += Constants.StatisticsWifiNetFineStateText + wifiNetInfo.getDetailedState().name() + Constants.LayoutNextLine;
		}
		else
		{
			generatedStatistics += Constants.StatisticsNoWifiNetText + Constants.LayoutNextLine;
		}
		
		if (mobileNetInfo != null)
		{
			generatedStatistics += Constants.StatisticsMobileNetTypeText + mobileNetInfo.getTypeName() + Constants.LayoutNextLine;
			generatedStatistics += Constants.StatisticsMobileNetSubtypeText + mobileNetInfo.getSubtypeName() + Constants.LayoutNextLine;
			generatedStatistics += Constants.StatisticsMobileNetCoarseStateText + mobileNetInfo.getState().name() + Constants.LayoutNextLine;
			generatedStatistics += Constants.StatisticsMobileNetFineStateText + mobileNetInfo.getDetailedState().name() + Constants.LayoutNextLine;
		}
		else
		{
			generatedStatistics += Constants.StatisticsNoMobileNetText + Constants.LayoutNextLine;
		}
		
		generatedStatistics += Constants.LayoutSeparator;
		
		return generatedStatistics;
	}
	
	private String GenerateMachineStatistics()
	{
		String generatedStatistics = "";
		
		if (activeNetInfo != null)
		{
			generatedStatistics += activeNetInfo.getTypeName() + Constants.LayoutNextLine;
			generatedStatistics += activeNetInfo.getSubtypeName() + Constants.LayoutNextLine;
			generatedStatistics += activeNetInfo.getState().name() + Constants.LayoutNextLine;
			generatedStatistics += activeNetInfo.getDetailedState().name() + Constants.LayoutNextLine;
		}
		else
		{
			generatedStatistics += Constants.StatisticsNone + Constants.LayoutNextLine;
		}
		
		generatedStatistics += Constants.LayoutEndItem;
				
		if (wifiNetInfo != null)
		{
			generatedStatistics += wifiNetInfo.getTypeName() + Constants.LayoutNextLine;
			generatedStatistics += wifiNetInfo.getSubtypeName() + Constants.LayoutNextLine;
			generatedStatistics += wifiNetInfo.getState().name() + Constants.LayoutNextLine;
			generatedStatistics += wifiNetInfo.getDetailedState().name() + Constants.LayoutNextLine;
		}
		else
		{
			generatedStatistics += Constants.StatisticsNone + Constants.LayoutNextLine;
		}
		
		generatedStatistics += Constants.LayoutEndItem;
		
		if (mobileNetInfo != null)
		{
			generatedStatistics += mobileNetInfo.getTypeName() + Constants.LayoutNextLine;
			generatedStatistics += mobileNetInfo.getSubtypeName() + Constants.LayoutNextLine;
			generatedStatistics += mobileNetInfo.getState().name() + Constants.LayoutNextLine;
			generatedStatistics += mobileNetInfo.getDetailedState().name() + Constants.LayoutNextLine;
		}
		else
		{
			generatedStatistics += Constants.StatisticsNone + Constants.LayoutNextLine;
		}
		
		generatedStatistics += Constants.LayoutEndItem;
		generatedStatistics += Constants.LayoutEndSection;
		
		return generatedStatistics;
	}
}
