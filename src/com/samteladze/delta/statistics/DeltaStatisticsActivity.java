package com.samteladze.delta.statistics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.samteladze.delta.statistics.Utils.*;
import com.samteladze.delta.statistics.DataModel.*;

public class DeltaStatisticsActivity extends Activity 
{
	private static Context _context;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);       
        DeltaStatisticsActivity._context = getApplicationContext();
        
        FileManager fileManager = new FileManager(_context);
        CommunicationManager communicationManager = new CommunicationManager(_context);
        
        AppStatisticsProvider appStatisticsProvider = new AppStatisticsProvider(_context);
        appStatisticsProvider.CollectStatistics();
        
        fileManager.SaveStatistics(appStatisticsProvider.GetStatistics(StatisticsFormat.UserFriendly));
        communicationManager.SendStatisticsToServer(); 
    }    
    
    private void SendStatisticsToMail(Intent sendIntent)
    {
    	try 
    	{
    		startActivity(Intent.createChooser(sendIntent, "Send e-mail..."));
        } 
    	catch (Exception e) 
    	{
    		e.printStackTrace(System.err);
    		
            Toast.makeText(_context, "No e-mail clients installed", Toast.LENGTH_SHORT).show();
    	}
    }
}