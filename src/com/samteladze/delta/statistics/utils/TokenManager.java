package com.samteladze.delta.statistics.utils;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

public class TokenManager
{
	public static final String ACCEPT_EULA_TOKEN = "accept.token";
	public static final String AFTER_FIRST_RUN_TOKEN = "after-first.token";
	public static final String TOKENS_PATH = "DeltaStatistics/";
	
	public static void CreateToken(String tokenName)
	{
		if (TokenExists(tokenName))
		{
			return;
		}
		
		File tokenFile = new File(Environment.getExternalStorageDirectory(), TOKENS_PATH + tokenName);
		
		try
		{
			if (tokenFile.createNewFile())
			{
				LogManager.Log(TokenManager.class.getSimpleName(), "Token " + tokenName + " was created");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace(System.err);
			
			LogManager.Log(TokenManager.class.getSimpleName(), "ERROR! Could not create token " + tokenName);
			LogManager.Log(TokenManager.class.getSimpleName(), e.toString());
		}
	}
	
	public static boolean TokenExists(String tokenName)
	{
		File tokenFile = new File(Environment.getExternalStorageDirectory(), TOKENS_PATH + tokenName);
		return (tokenFile.exists() && !tokenFile.isDirectory());
	}
}
