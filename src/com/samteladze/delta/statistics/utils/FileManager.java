
package com.samteladze.delta.statistics.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.os.Environment;

public class FileManager
{
	// Files with statistics to send to the server
	public static final String	APP_STAT_FILE_PATH	= "DeltaStatistics/Statistics/AppStat.txt";
	public static final String	NET_STAT_FILE_PATH	= "DeltaStatistics/Statistics/NetStat.txt";
	// Files with statistics to e-mail to the user
	public static final String USER_APP_STAT_FILE_PATH = "DeltaStatistics/Statistics/UserAppStat.txt";
	public static final String USER_NET_STAT_FILE_PATH	= "DeltaStatistics/Statistics/UserNetStat.txt";
	
	private static final String STAT_FOLDER_PATH = "DeltaStatistics/Statistics";
	
	public static final long MAX_NET_FILE_SIZE = 1 * 512 * 1024; // 0.5 MB
	
	// TEMP
	private static final String TEMP_FILE_PATH = "DeltaStatistics/temp.txt";

	// File path should point to a file on the device's external storage
	public static void SaveStatistics(String statistics, String statisticsFilePath, boolean replaceFlag)
	{
		File statFile = new File(Environment.getExternalStorageDirectory(), statisticsFilePath);
		
		try
		{
			// TEST for multiple languages support
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(statFile, !replaceFlag), "UTF8"));
			
			//BufferedWriter out = new BufferedWriter(new FileWriter(statFile, !replaceFlag));

			out.write(statistics);

			out.flush();
			out.close();
			
			LogManager.Log(FileManager.class.getSimpleName(), "Statistics was saved to " + statisticsFilePath);
		} 
		catch (Exception e)
		{
			e.printStackTrace(System.err);
			
			LogManager.Log(FileManager.class.getSimpleName(), "ERROR! Could not save statistics to " + statisticsFilePath);
			LogManager.Log(CommunicationManager.class.getSimpleName(), e.toString());
		}
	}
	
	// File path should point to a file on the device's external storage
	public static void CleanFile(String filePath)
	{
		File statFile = new File(Environment.getExternalStorageDirectory(), filePath);
		
		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(statFile, false));
			out.close();
			
			LogManager.Log(FileManager.class.getSimpleName(), "File " + filePath + " was cleaned");
		} 
		catch (Exception e)
		{
			e.printStackTrace(System.err);
			
			LogManager.Log(FileManager.class.getSimpleName(), "ERROR! Could not clean file " + filePath);
			LogManager.Log(CommunicationManager.class.getSimpleName(), e.toString());
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
		File statDataFolder = new File(Environment.getExternalStorageDirectory(), STAT_FOLDER_PATH);

		// Create folders structure
		if (!statDataFolder.exists())
		{
			if (statDataFolder.mkdirs())
			{		        	
				LogManager.Log(FileManager.class.getSimpleName(), "Folders structure was created.");
			}
		}				
	}
	
	public static File GetFile(String filePath)
	{
		File file = new File(Environment.getExternalStorageDirectory(), filePath);
		
		if (file.exists() && !file.isDirectory())
		{
			return (new File(Environment.getExternalStorageDirectory(), filePath));
		}
		
		return null;
	}
	
	public static boolean FileExists(String filePath)
	{
		File file = new File(Environment.getExternalStorageDirectory(), filePath);
		
		return (file.exists() && !file.isDirectory());
	}
	
	public static long GetFileSize(String filePath)
	{
		long fileSize = 0;
		
		File file = new File(Environment.getExternalStorageDirectory(), filePath);
		
		if (file.exists() && !file.isDirectory())
		{
			return file.length();
		}
		
		return fileSize;
	}
	
	// TEMP
	public static File TagFile(String filePath, String tag, String deviceID)
	{
		File file = new File(Environment.getExternalStorageDirectory(), filePath);

		StringBuilder text = new StringBuilder();

		try 
		{
		    BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;

		    while ((line = br.readLine()) != null) 
		    {
		        text.append(line);
		        text.append(Constants.LayoutNextLine);
		    }
		}
		catch (IOException e) 
		{
			e.printStackTrace(System.err);	
			
			LogManager.Log(FileManager.class.getSimpleName(), "ERROR! Could not read from file " + filePath);
			LogManager.Log(FileManager.class.getSimpleName(), e.toString());
			
			return null;
		}
		
		File tagFile = new File(Environment.getExternalStorageDirectory(), TEMP_FILE_PATH);
		
		try
		{
			// TEST
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tagFile, false), "UTF8"));
			
			//BufferedWriter out = new BufferedWriter(new FileWriter(tagFile, false));

			out.write(deviceID + Constants.LayoutNextLine);
			out.write(tag + Constants.LayoutNextLine + Constants.LayoutEndSection);
			out.write(text.toString());

			out.flush();
			out.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace(System.err);	
			
			LogManager.Log(FileManager.class.getSimpleName(), "ERROR! Could not add tag to file " + filePath);
			LogManager.Log(FileManager.class.getSimpleName(), e.toString());
			
			return null;
		}
		
		return tagFile;
	}
	
	// TEMP
	public static void DeleteTempFile()
	{
		File tempFile = new File(Environment.getExternalStorageDirectory(), TEMP_FILE_PATH);
		
		if (tempFile.exists() && !tempFile.isDirectory())
		{
			if (!tempFile.delete())
			{
				LogManager.Log(FileManager.class.getSimpleName(), "ERROR! Could not delete temp file");
			}
		}
	}
}
