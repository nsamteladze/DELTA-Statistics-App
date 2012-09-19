package com.samteladze.delta.statistics.utils;

public class Constants 
{
	public static final String LayoutNextLine = "\r\n";
	public static final String LayoutEndItem = "$$$\r\n";
	public static final String LayoutEndSection = "%%%\r\n";
	public static final String LayoutSeparator = "------------------------------\r\n";
	
	public static final String EmptyStr = "";	
	
	public static final String DateFormat = "MM/dd/yyyy h:mm:ss aa";	
	
	public static final String StatisticsPackageNameText = "Package name: ";
	public static final String StatisticsAppNameText = "App name: ";
	public static final String StatisticsVersionNameText = "Version name: ";
	public static final String StatisticsVersionCodeText = "Version code: ";
	public static final String StatisticsFirstInstallTimeText = "First install time: ";
	public static final String StatisticsLastUpdateTimeText = "Last update time: ";
	public static final String StatisticsCodeSizeText = "Code size: ";
	public static final String StatisticsNumberOfAppsText = "Number of applications: ";
	public static final String StatisticsDeviceIDText = "Device ID: ";
	
	public static final String StatisticsActiveNetTypeText = "Active network type: ";
	public static final String StatisticsActiveNetSubtypeText = "Active network subtype: ";
	public static final String StatisticsActiveNetCoarseStateText = "Active network coarse-grained state: ";
	public static final String StatisticsActiveNetFineStateText = "Active network fine-grained state: ";
	public static final String StatisticsWifiNetTypeText = "Network type: ";
	public static final String StatisticsWifiNetSubtypeText = "Wi-Fi network subtype: ";
	public static final String StatisticsWifiNetCoarseStateText = "Wi-Fi network coarse-grained state: ";
	public static final String StatisticsWifiNetFineStateText = "Wi-Fi network fine-grained state: ";
	public static final String StatisticsMobileNetTypeText = "Network type: ";
	public static final String StatisticsMobileNetSubtypeText = "Mobile network subtype: ";
	public static final String StatisticsMobileNetCoarseStateText = "Mobile network coarse-grained state: ";
	public static final String StatisticsMobileNetFineStateText = "Mobile network fine-grained state: ";
	public static final String StatisticsNoActiveNetText = "No active network";
	public static final String StatisticsNoWifiNetText = "No Wi-Fi network";
	public static final String StatisticsNoMobileNetText = "No Mobile network";
	
	public static final String StatisticsNone = "none";
	
	public static final String NotificationProgressText = "Collecting statistics...";
	public static final String NotificationFinishText = "Statistics collection finished"; 
	public static final String NotificationAppNameText = "DELTA Statistics";
	
	public static final String MsgNoStatistics = "Statistics for your device has not been collected yet. " +
												 "Please click <Collect statistics> button to collect it now.";
}
