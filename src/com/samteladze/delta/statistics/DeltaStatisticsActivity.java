package com.samteladze.delta.statistics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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
        
        InstalledAppsStatisticsProvider installedAppsStatisticsProvider = new InstalledAppsStatisticsProvider(_context);
        String installedAppsStatistics = installedAppsStatisticsProvider.GetStatistics();
        
        fileManager.SaveStatistics(installedAppsStatistics);
        Intent sendIntent = communicationManager.CreatEmailStatisticsIntent();      
        SendStatisticsToMail(sendIntent);  
    }    
    
    private void SendStatisticsToMail(Intent sendIntent)
    {
    	try 
    	{
    		startActivity(Intent.createChooser(sendIntent, "Send e-mail..."));
        } 
    	catch (android.content.ActivityNotFoundException exception) 
    	{
    		exception.printStackTrace(System.err);
    		
            Toast.makeText(_context, "No e-mail clients installed", Toast.LENGTH_SHORT).show();
    	}
    }
}