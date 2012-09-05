package com.samteladze.delta.statistics.Utils;

import java.io.File;

import com.samteladze.delta.statistics.R;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

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
}
