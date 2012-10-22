package com.samteladze.delta.statistics;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.samteladze.delta.statistics.utils.*;

public class DeltaStatisticsActivity extends Activity 
{
	// Time delay in AlarmManager before the first alarm is fired
	public static final int ALARM_DELAY = 120000;
	// Time between alarms in AlarmManager
	public static final long ALARM_PERIOD = AlarmManager.INTERVAL_DAY;
	private static Context _context;
	// Unique request code for PendingIntent (used to distinguish intents)
	public static final int INTENT_REQUEST_CODE = 0;
	
	public static boolean COLLECTING = false;
	
    /** Called when the activity is first created. */
    @Override 
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);  
         
        if (!TokenManager.TokenExists(TokenManager.AFTER_FIRST_RUN_TOKEN))
    	{
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage(Constants.MsgFirstRun)
    			   .setTitle(Constants.MsgAppWelcome)
    		       .setCancelable(false)
    		       .setPositiveButton("OK", new DialogInterface.OnClickListener()
		       	   {
    		           public void onClick(DialogInterface dialog, int id) 
    		           {
    		        	   TokenManager.CreateToken(TokenManager.AFTER_FIRST_RUN_TOKEN);
    		           }
    		       });
    		
    		
			AlertDialog alert = builder.create();
    		alert.show();
    	}
        
        DeltaStatisticsActivity._context = getApplicationContext();
        
        CreateRequiredFoldersAndFiles();
       
	    // Get AlarmManager
 		AlarmManager alarmManager = (AlarmManager) _context.getSystemService(Context.ALARM_SERVICE);
 		
 		// Create an Intent to start OnAlarmReceiver
 		Intent alarmIntent = new Intent(_context, OnAlarmReceiver.class);
 		// Create a PendingIntent from alarmIntent
 		PendingIntent alarmTestPendingIntent = PendingIntent.getBroadcast(_context, INTENT_REQUEST_CODE, alarmIntent, PendingIntent.FLAG_NO_CREATE);
 		
 		// If there is no such intent
 		if (alarmTestPendingIntent == null)
 		{
 			PendingIntent alarmPendingIntent = 
 					PendingIntent.getBroadcast(_context, INTENT_REQUEST_CODE, alarmIntent, 0);
 			// Set repeating alarm that will invoke OnAlarmReceiver
 	 		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 
 	 								  SystemClock.elapsedRealtime() + ALARM_DELAY, 
 	 								  ALARM_PERIOD, alarmPendingIntent);   
 	 		
 	 		LogManager.Log(DeltaStatisticsActivity.class.getSimpleName(), "Repeating alarm was set");
 		} 
 		else
 		{
 			LogManager.Log(DeltaStatisticsActivity.class.getSimpleName(), "Alarm alredy exists. New alarm was not set");
 		}
    }    
    
	private void EmailStatistics(Intent sendIntent)
    {
    	try 
    	{
    		startActivity(Intent.createChooser(sendIntent, "Send e-mail..."));
        } 
    	catch (Exception e) 
    	{
    		e.printStackTrace(System.err);
    		
    		LogManager.Log(CommunicationManager.class.getSimpleName(), e.toString());
    		
            Toast.makeText(_context, "No e-mail clients installed", Toast.LENGTH_LONG).show();
    	}
    }
    
    private void CreateRequiredFoldersAndFiles()
    {
        if (!FileManager.HasFolderStructure())
        {
        	FileManager.CreateFolderStructure();
        }
        
        if (!LogManager.LogExists())
        {
        	LogManager.CreateLog();
        }
        
        if (!FileManager.FileExists(FileManager.NET_STAT_FILE_PATH))
        {
        	File netStatsFile = new File(Environment.getExternalStorageDirectory(), FileManager.NET_STAT_FILE_PATH);
        	try
			{
				if (!netStatsFile.createNewFile())
				{
					LogManager.Log(DeltaStatisticsActivity.class.getSimpleName(), "ERROR! Could not create NET_STAT file");
				}
			} 
        	catch (IOException e)
			{
				e.printStackTrace(System.err);
				LogManager.Log(DeltaStatisticsActivity.class.getSimpleName(), e.toString());
			}
        }
    }
    
    public void OnClickCollect(View view)
    {
    	COLLECTING = true;
    	
    	WakefulIntentService.sendWakefulWork(getApplicationContext(), UserAppStatisticsService.class);
		
		LogManager.Log(DeltaStatisticsActivity.class.getSimpleName(), "Collecting statistics");
    }
    
    public void OnClickEmail(View view)
    {
    	if (COLLECTING)
    	{
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage(Constants.MsgCollectionInProcess)
    			   .setTitle(Constants.MsgAppTitle)
    		       .setCancelable(false)
    		       .setPositiveButton("OK", new DialogInterface.OnClickListener()
		       	   {
    		           public void onClick(DialogInterface dialog, int id) 
    		           {

    		           }
    		       });
    		
			AlertDialog alert = builder.create();
    		alert.show();
    		
    		return;
    	}
    	
    	if (!FileManager.FileExists(FileManager.USER_APP_STAT_FILE_PATH))
    	{
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage(Constants.MsgNoStatistics)
    			   .setTitle(Constants.MsgAppTitle)
    		       .setCancelable(false)
    		       .setPositiveButton("OK", new DialogInterface.OnClickListener()
		       	   {
    		           public void onClick(DialogInterface dialog, int id) 
    		           {

    		           }
    		       });
    		
			AlertDialog alert = builder.create();
    		alert.show();
    		
    		return;
    	}
		
    	EmailStatistics(CommunicationManager.CreatEmailDataIntent());
    }
}