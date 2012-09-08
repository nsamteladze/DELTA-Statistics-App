package com.samteladze.delta.statistics.Utils;

import java.io.File;
import java.io.FileInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.samteladze.delta.statistics.R;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class CommunicationManager 
{
	private Context _context;
	
	public CommunicationManager(Context context)
	{
		this._context = context;
	}
	
	public Intent CreatEmailStatisticsIntent()
    {
    	Uri fileUri = null;
    	File sdCard = Environment.getExternalStorageDirectory();
    	File statDataFolder = new File(sdCard.getAbsolutePath() + "/" + _context.getString(R.string.StatFilePath));
    	fileUri = Uri.fromFile(new File(statDataFolder.getAbsolutePath() + "/" + _context.getString(R.string.StatFileName)));
    	
    	Intent sendIntent = new Intent(Intent.ACTION_SEND);
    	sendIntent.setType("text/plain");
    	sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { Constants.MailAddress});
    	sendIntent.putExtra(Intent.EXTRA_SUBJECT, Constants.MailSubject);
    	sendIntent.putExtra(Intent.EXTRA_TEXT, Constants.MailText);
    	sendIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
    	
    	return sendIntent;	
    }
	
	public void SendStatisticsToServer()
	{
		String url = "http://xdp-apps.org/stat-collector/collect";
		File file = new File(Environment.getExternalStorageDirectory(), Constants.AbsoluteStatisticsFileName);
		
		try
		{
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost(url);

		    InputStreamEntity reqEntity = new InputStreamEntity(new FileInputStream(file), -1);
		    reqEntity.setContentType("binary/octet-stream");
		    reqEntity.setChunked(true); // Send in multiple parts if needed
		    httppost.setEntity(reqEntity);
		    HttpResponse response = httpclient.execute(httppost);
		} 
		catch (Exception e) 
		{
		    e.printStackTrace(System.err);
		}
	}
}
