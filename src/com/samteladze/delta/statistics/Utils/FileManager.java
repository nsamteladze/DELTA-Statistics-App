package com.samteladze.delta.statistics.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;

import android.os.Environment;

public class FileManager
{
	/*
	 * TODO Use local constants instead of retrieving information from
	 * Strings.xml resources file
	 */

	public static final String	STAT_FILE_PATH	= "DeltaStatistics/Statistics/stat.txt";
	private static final String	LOG_FILE_PATH	= "DeltaStatistics/log.txt";

	public static void SaveStatistics(String statistics)
	{
		File statFile = new File(Environment.getExternalStorageDirectory(), STAT_FILE_PATH);

		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(statFile, false));

			out.write(statistics);

			out.close();
			
			Log("FileManager", "Statistics was saved.");
		} 
		catch (Exception e)
		{
			e.printStackTrace(System.err);
			
			Log("FileManager", "ERROR! Could not save statistics.");
		}
	}

	public static boolean HasFolderStructure()
	{
		File statDataFolder = new File(Environment.getExternalStorageDirectory(), STAT_FILE_PATH);

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
				Environment.getExternalStorageDirectory(), STAT_FILE_PATH);

		if (!statDataFolder.exists())
		{
			statDataFolder.mkdirs();
			/*
			 * TODO Throw exception if the folder was not created
			 */
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
		}
	}
	
	public static File GetStatisticsFile()
	{
		return (new File(Environment.getExternalStorageDirectory(), STAT_FILE_PATH));
	}
}
