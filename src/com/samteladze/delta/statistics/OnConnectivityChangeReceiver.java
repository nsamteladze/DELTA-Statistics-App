package com.samteladze.delta.statistics;

import java.util.ArrayList;

import com.samteladze.delta.statistics.Utils.FileManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class OnConnectivityChangeReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{	
	    ConnectivityManager mConnectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE );
	    NetworkInfo activeNetInfo = mConnectivity.getActiveNetworkInfo();
	    ArrayList<NetworkInfo> allNetInfo = new ArrayList<NetworkInfo>();
	    allNetInfo.add(mConnectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE));
	    allNetInfo.add(mConnectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI));
	    allNetInfo.add(mConnectivity.getNetworkInfo(ConnectivityManager.TYPE_WIMAX));
	    
	    if ( activeNetInfo != null )
	    {
	    	FileManager.Log(OnConnectivityChangeReceiver.class.getSimpleName(),
	    					"Active network type: " +  activeNetInfo.getTypeName());
	    	FileManager.Log(OnConnectivityChangeReceiver.class.getSimpleName(),
							"Active network subtype: " +  activeNetInfo.getSubtypeName());
	    	FileManager.Log(OnConnectivityChangeReceiver.class.getSimpleName(),
							"Active network coarse-grained state: " +  activeNetInfo.getState().name());
	    	FileManager.Log(OnConnectivityChangeReceiver.class.getSimpleName(),
							"Active network fine-grained state: " +  activeNetInfo.getDetailedState().name());
	    }
	    else
	    {
	    	FileManager.Log(OnConnectivityChangeReceiver.class.getSimpleName(),
							"No active network");
	    }
	    for (NetworkInfo netInfo : allNetInfo)
	    {
	    	if (netInfo != null)
	    	{
		    	FileManager.Log(OnConnectivityChangeReceiver.class.getSimpleName(),
								"Network type: " +  netInfo.getTypeName());
		    	FileManager.Log(OnConnectivityChangeReceiver.class.getSimpleName(),
								"Network subtype: " +  netInfo.getSubtypeName());
		    	FileManager.Log(OnConnectivityChangeReceiver.class.getSimpleName(),
								"Network coarse-grained state: " +  netInfo.getState().name());
		    	FileManager.Log(OnConnectivityChangeReceiver.class.getSimpleName(),
								"Network fine-grained state: " +  netInfo.getDetailedState().name());
	    	}
	    	else
	    	{
	    		FileManager.Log(OnConnectivityChangeReceiver.class.getSimpleName(),
								"No network");
	    	}
	    }
	}

}
