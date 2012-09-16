package com.samteladze.delta.statistics;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

import com.samteladze.delta.statistics.Utils.*;

public class DeltaStatisticsActivity extends Activity 
{
	// Time delay in AlarmManager before the first alarm is fired
	public static final int ALARM_DELAY = 120000;
	// Time between alarms in AlarmManager
	public static final long ALARM_PERIOD = AlarmManager.INTERVAL_DAY;
	private static Context _context;
	// Unique request code for PendingIntent (used to distinguish intents)
	public static final int INTENT_REQUEST_CODE = 0;
	
    /** Called when the activity is first created. */
    @Override 
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);       
        DeltaStatisticsActivity._context = getApplicationContext();
        
        CreateRequiredFolders();
        
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
 	 		
 	 		FileManager.Log(DeltaStatisticsActivity.class.getSimpleName(), "Alarm was set.");
 		} 
 		else
 		{
 			FileManager.Log(DeltaStatisticsActivity.class.getSimpleName(), "Alarm alredy exists. New alarm was no set.");
 		}
    }    
    
    @SuppressWarnings("unused")
	private void SendStatisticsToMail(Intent sendIntent)
    {
    	try 
    	{
    		startActivity(Intent.createChooser(sendIntent, "Send e-mail..."));
        } 
    	catch (Exception e) 
    	{
    		e.printStackTrace(FileManager.GetLogPrintStream());
    		
            Toast.makeText(_context, "No e-mail clients installed", Toast.LENGTH_LONG).show();
    	}
    }
    
    private void CreateRequiredFolders()
    {
        if (!FileManager.HasFolderStructure())
        {
        	FileManager.CreateFolderStructure();
        }
    }
}