package com.samteladze.delta.statistics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class FileManager 
{
	private Context _context;
	
	public FileManager(Context context)
	{
		this._context = context;
	}
	public void SaveStatistics(String statistics)
	{
		Toast.makeText(_context, "Saving statistics...", Toast.LENGTH_SHORT).show();
		
		FileOutputStream fileOutputStream = null;
    	OutputStreamWriter outputStreamWriter = null;
    	
    	if (!HasFolderStructure())
    	{
    		CreateFolderStructure();
    	}
    	
    	File sdCard = Environment.getExternalStorageDirectory();
    	File statDataFolder = new File(sdCard.getAbsolutePath() + "/" + _context.getString(R.string.StatFilePath));
    
    	try
    	{
    		fileOutputStream = new FileOutputStream(new File(statDataFolder.getAbsolutePath() + "/" + _context.getString(R.string.StatFileName)));
    		outputStreamWriter = new OutputStreamWriter(fileOutputStream);
    		
    		outputStreamWriter.write(statistics);
    		
    		outputStreamWriter.close();
    		fileOutputStream.close();   		
    	}
    	catch (Exception exception)
    	{
    		exception.printStackTrace(System.err);
    		
    		Toast.makeText(_context, "Unable to save statistics", Toast.LENGTH_SHORT).show();
    	}
	}
	
	private boolean HasFolderStructure()
    {    	
    	File sdCard = Environment.getExternalStorageDirectory();
    	File statDataFolder = new File(sdCard.getAbsolutePath() + "/" + _context.getString(R.string.StatFilePath));
    	
    	// Test statistics data folder
    	if (!statDataFolder.exists() || !statDataFolder.isDirectory())
    	{
    		return (false);
    	}
    	
		return (true);    	
    }
    
    private void CreateFolderStructure()
    {
    	Toast.makeText(_context, "Creating a folders structure... ", Toast.LENGTH_LONG).show();
    	
    	File sdCard	= Environment.getExternalStorageDirectory();
    	File statDataFolder = new File(sdCard.getAbsolutePath() + "/" + _context.getString(R.string.StatFilePath));
    	
    	if (!statDataFolder.exists())
    	{
    		if (!statDataFolder.mkdirs())
    		{
    			Toast.makeText(_context, "Unable to create folders structure", Toast.LENGTH_SHORT).show();
    		}
    	}
    }
}
