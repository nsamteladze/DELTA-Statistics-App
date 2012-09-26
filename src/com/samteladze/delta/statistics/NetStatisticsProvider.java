
package com.samteladze.delta.statistics;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.net.ConnectivityManager;

import com.samteladze.delta.statistics.datamodel.NetStatistics;
import com.samteladze.delta.statistics.datamodel.StatisticsFormat;
import com.samteladze.delta.statistics.utils.Constants;
import com.samteladze.delta.statistics.utils.LogManager;

public class NetStatisticsProvider
{
	private Context _context;
	private NetStatistics _statistics;
	private Date _statCollectionDateTime;
	
	public NetStatisticsProvider(Context context)
	{
		_context = context;
		_statistics = new NetStatistics();
	}
	
	public void CollectStatistics(Date currentDateTime)
	{
		_statCollectionDateTime = currentDateTime;
		
		ConnectivityManager mConnectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE );
		
	    _statistics.activeNetInfo = mConnectivity.getActiveNetworkInfo();
	    _statistics.wifiNetInfo = mConnectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    _statistics.mobileNetInfo = mConnectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    
	    LogManager.Log(NetStatisticsProvider.class.getSimpleName(), "Statistics was collected");
	}
	
	public String GetStatistics(StatisticsFormat format)
	{
		String statisticsStr = "";
		
		statisticsStr += _statCollectionDateTime.toString() + Constants.LayoutNextLine;
		if (format == StatisticsFormat.Machine)
		{
			statisticsStr += Calendar.ZONE_OFFSET + Constants.LayoutNextLine;
			statisticsStr += Calendar.DST_OFFSET + Constants.LayoutNextLine;
		}
		statisticsStr += Constants.LayoutEndItem;
		statisticsStr += _statistics.Format(format);
		
		return statisticsStr;
	}
}
