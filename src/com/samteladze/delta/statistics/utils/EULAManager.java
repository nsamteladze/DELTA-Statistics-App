package com.samteladze.delta.statistics.utils;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

public class EULAManager
{
	public static final String ACCEPT_TOKEN_NAME = "accept.token";
	public static final String ACCEPT_TOKEN_PATH = "DeltaStatistics/";
	
	public static void CreateAcceptToken()
	{
		if (TokenExists())
		{
			return;
		}
		
		File tokenFile = new File(Environment.getExternalStorageDirectory(), ACCEPT_TOKEN_PATH + ACCEPT_TOKEN_NAME);
		
		try
		{
			if (tokenFile.createNewFile())
			{
				LogManager.Log(EULAManager.class.getSimpleName(), "Token was created");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace(System.err);
			
			LogManager.Log(EULAManager.class.getSimpleName(), "ERROR! Could not create token");
			LogManager.Log(EULAManager.class.getSimpleName(), e.toString());
		}
	}
	
	public static boolean TokenExists()
	{
		File tokenFile = new File(Environment.getExternalStorageDirectory(), ACCEPT_TOKEN_PATH + ACCEPT_TOKEN_NAME);
		return (tokenFile.exists() && !tokenFile.isDirectory());
	}
}
