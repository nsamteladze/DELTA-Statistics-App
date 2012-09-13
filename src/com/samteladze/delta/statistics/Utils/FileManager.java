/* TODO
 * Make log a separate class
 */

package com.samteladze.delta.statistics.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

import android.os.Environment;

public class FileManager
{
	public static final String	APP_STAT_FILE_PATH	= "DeltaStatistics/Statistics/AppStat.txt";
	public static final String	NET_STAT_FILE_PATH	= "DeltaStatistics/Statistics/NetStat.txt";
	private static final String	LOG_FILE_PATH	= "DeltaStatistics/log.txt";
	private static final String STAT_FOLDER_PATH = "DeltaStatistics/Statistics";

	public static void SaveAppStatistics(String statistics)
	{
		File statFile = new File(Environment.getExternalStorageDirectory(), APP_STAT_FILE_PATH);

		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(statFile, false));

			out.write(statistics);

			out.close();
			
			Log(FileManager.class.getSimpleName(), "Applications statistics was saved.");
		} 
		catch (Exception e)
		{
			e.printStackTrace(FileManager.GetLogPrintStream());
			
			Log(FileManager.class.getSimpleName(), "ERROR! Could not save applications statistics.");
		}
	}
	
	public static void SaveNetStatistics(String statistics)
	{
		File statFile = new File(Environment.getExternalStorageDirectory(), NET_STAT_FILE_PATH);

		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(statFile, statFile.exists()));

			out.write(statistics);

			out.close();
			
			Log(FileManager.class.getSimpleName(), "Network statistics was saved.");
		} 
		catch (Exception e)
		{
			e.printStackTrace(FileManager.GetLogPrintStream());
			
			Log(FileManager.class.getSimpleName(), "ERROR! Could not save network statistics.");
		}
	}
	
	public static void CleanNetStatistics()
	{
		File statFile = new File(Environment.getExternalStorageDirectory(), NET_STAT_FILE_PATH);
		
		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(statFile, false));
			out.close();
			
			Log(FileManager.class.getSimpleName(), "Network statistics was cleaned.");
		} 
		catch (Exception e)
		{
			e.printStackTrace(FileManager.GetLogPrintStream());
			
			Log(FileManager.class.getSimpleName(), "ERROR! Could not clean network statistics.");
		}
	}

	public static boolean HasFolderStructure()
	{
		File statDataFolder = new File(Environment.getExternalStorageDirectory(), STAT_FOLDER_PATH);

		// Test statistics data folder
		if (!statDataFolder.exists() || !statDataFolder.isDirectory())
		{
			return (false);
		}

		return (true);
	}

	public static void CreateFolderStructure()
	{
		File statDataFolder = new File(
				Environment.getExternalStorageDirectory(), STAT_FOLDER_PATH);

		// Create folders structure
		if (!statDataFolder.exists())
		{
			if (statDataFolder.mkdirs())
			{		        	
				FileManager.Log(FileManager.class.getSimpleName(), "Folders structure was created.");
			}
			else
			{
				/* TODO
				 * Throw exception if folders were not created
				 */
			}
		}
		
		// Create log file
		File logFile = new File(Environment.getExternalStorageDirectory(), LOG_FILE_PATH);
		
		// If there is a directory with the same name as log file
		if (logFile.isDirectory())
		{
			for (File fileInDirectory : logFile.listFiles())
			{
				fileInDirectory.delete();
			}
			
			if (logFile.delete())
			{
				
			}
			else
			{
				/* TODO
				 * Throw exception if logFile directory has not been deleted
				 */
			}
		}
		
		// If log file does not exists
		if (!logFile.exists())
		{
			try
			{
				if (logFile.createNewFile())
				{
					FileManager.Log(FileManager.class.getSimpleName(), "Log file was created");
				}
				else
				{
					/* TODO
					 * Throw exception if log file was not created
					 */
				}
			} 
			catch (IOException e)
			{
				e.printStackTrace(System.err);
				
				/* TODO
				 * Throw exception if log file that log file was not created
				 */
			}
		}
	}

	public static void Log(String tag, String logMessage)
	{
		File logFile = new File(Environment.getExternalStorageDirectory(), LOG_FILE_PATH);
		
		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(logFile, logFile.exists()));

			out.write(new Date().toString() + " | " + tag + " : " + logMessage + Constants.LayoutNextLine);

			out.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace(System.err);
			
			/* TODO
			 * Throw exception that we could not log information
			 */
		}
	}
	
	public static PrintStream GetLogPrintStream()
	{		
		try
		{
			return (new PrintStream(new File(Environment.getExternalStorageDirectory(), LOG_FILE_PATH)));
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace(System.err);	
			
			/* TODO
			 * Throw exception that we could not return log file
			 */
		}
		
		return System.out;
	}
	
	public static File GetAppStatisticsFile()
	{
		return (new File(Environment.getExternalStorageDirectory(), APP_STAT_FILE_PATH));
	}
	
	public static File GetNetStatisticsFile()
	{
		return (new File(Environment.getExternalStorageDirectory(), NET_STAT_FILE_PATH));
	}
}
