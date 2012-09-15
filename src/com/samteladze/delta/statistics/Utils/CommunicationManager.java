package com.samteladze.delta.statistics.Utils;

import java.io.File;
import java.io.FileInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import android.content.Intent;
import android.net.Uri;

public class CommunicationManager
{
	private static final String SERVER_URL = "http://xdp-apps.org/stat-collector/collect";
	private static final String MAIL_TEXT = "Current statistics.";
	private static final String MAIL_SUBJECT = "Delta Statistics";
	private static final String MAIL_ADDRESS = "delta.statistics@gmail.com";
	
	public static Intent CreatEmailStatisticsIntent()
	{
		Uri statFileUri = Uri.fromFile(FileManager.GetAppStatisticsFile());

		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.setType("text/plain");
		sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { MAIL_ADDRESS });
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, MAIL_SUBJECT);
		sendIntent.putExtra(Intent.EXTRA_TEXT, MAIL_TEXT);
		sendIntent.putExtra(Intent.EXTRA_STREAM, statFileUri);

		return sendIntent;
	}

	public static boolean SendStatisticsToServer()
	{
		File fileToSend = FileManager.GetAppStatisticsFile();
		
		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(SERVER_URL);

			InputStreamEntity reqEntity = new InputStreamEntity(new FileInputStream(fileToSend), -1);
			reqEntity.setContentType("binary/octet-stream");
			reqEntity.setChunked(true);
			
			httppost.setEntity(reqEntity);

			@SuppressWarnings("unused")
			HttpResponse response = httpclient.execute(httppost);
			
			FileManager.Log(CommunicationManager.class.getSimpleName(), "Statistics data was sent to the server.");
			
			return true;
		} 
		catch (Exception e)
		{
			e.printStackTrace(FileManager.GetLogPrintStream());
			
			FileManager.Log(CommunicationManager.class.getSimpleName(), "ERROR! Could not send statistics data to the server.");
		}
		
		return false;
	}
}
