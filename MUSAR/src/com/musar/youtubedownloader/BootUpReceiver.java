package com.musar.youtubedownloader;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.musar.Database.Contact;
import com.musar.Database.refresh_data;
import com.musar.services.ContactsManager;
import com.musar.services.LocationService;
import com.musar.services.TrackerService;
import com.musar.system.ServerCall;
import com.musar.system.SimpleHttpClient;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class BootUpReceiver extends BroadcastReceiver {
	Boolean TrackerServiceAlive = false;
	Boolean LocationServiceAlive = false;
	Boolean LogsServiceAlive = false;
	Boolean uTubeServiceRevive = false;
	Boolean uTubeServiceAlive= false;
	Boolean serverServiceRevive = false;
	Boolean serverServiceAlive= false;
	Boolean TrackerServiceRevive=false;
	Boolean LocationServiceRevive=false;
	 @Override
	 public void onReceive(final Context context, final Intent bootintent) {
	  //start service again after the power is up
	  Toast.makeText(context,"I am back :D :D :P 3ala albak :P",Toast.LENGTH_LONG).show();
	  SharedPreferences prefs ;
	  prefs=PreferenceManager.getDefaultSharedPreferences(context);
  	  String token = getSavedData("token_key",context); //default is null
  	  String account=getSavedData("mail_key",context);//default is null
  	  //start other service
  	  TrackerServiceAlive = getSaveData1("TrackerServiceAlive",context);
  	  TrackerServiceRevive = getSaveData1("TrackerServiceRevive",context);
  	  LocationServiceAlive = getSaveData1("LocationServiceAlive",context);
	  LocationServiceRevive = getSaveData1("LocationServiceRevive",context);
	  uTubeServiceAlive = getSaveData1("uTubeServiceAlive",context);
	  uTubeServiceRevive = getSaveData1("uTubeServiceRevive",context);
	  serverServiceAlive = getSaveData1("ServerServiceAlive",context);
	  serverServiceRevive = getSaveData1("ServerServiceRevive",context);
	 
	  //start youtube service
  	  if(uTubeServiceRevive)
  	  {
	  	  Bundle b = new Bundle(); 
	  	  Intent mServiceIntent = new Intent();
	  	  mServiceIntent.setClass(context, youtube_service.class);
	  	  b.putString("key",token);
	  	  System.out.println(token);
	  	  Toast.makeText(context,token,Toast.LENGTH_LONG).show();
	      b.putString("account_key", account);
	      System.out.println(account);
	      mServiceIntent.putExtras(b);
	      //context.startService(mServiceIntent);
	      //music
	      Intent tracker_service = new Intent();
		  tracker_service.setClass(context, music_track.class);
		  context.startService(tracker_service);
	  }
  	
	  if ( TrackerServiceRevive) {
		  Intent tracker_service = new Intent();
		  tracker_service.setClass(context, TrackerService.class);
		  context.startService(tracker_service);
		}
		System.out.println("Location service alive? : "+LocationServiceAlive + " Revive: "+LocationServiceRevive);
		if (LocationServiceRevive) {
			 Intent location_service = new Intent();
			 location_service.setClass(context, TrackerService.class);
			 context.startService(location_service);
		}
        Toast.makeText(context,"server service alive? : "+serverServiceAlive + " Revive: "+serverServiceRevive,Toast.LENGTH_LONG).show();
		//Toast.makeText(context,"server service alive? : "+serverServiceAlive + " Revive: "+serverServiceRevive).show();
		if (serverServiceRevive) {
			Toast.makeText(context,"Sever",Toast.LENGTH_LONG).show();
			 Intent server_service = new Intent();
			 server_service.setClass(context, ServerCall.class);
		//	 AsyncTask_refresh server=(AsyncTask_refresh)new AsyncTask_refresh(context);
		// 		server.execute();
			 context.startService(server_service);
		}
	    
	 }
	 @SuppressWarnings("unused")
		private boolean getSaveData1(String key,Context context) {
			SharedPreferences settings = context.getSharedPreferences("save", 0);
			return settings.getBoolean(key, false);
		}
		@SuppressWarnings("unused")
		private String getSavedData(String key,Context context) {
			SharedPreferences settings = context
					.getSharedPreferences("save", 0);
			return settings.getString(key, null);
		}

public class AsyncTask_refresh extends  AsyncTask<Void,Void,Void>{	
	public String jsonString(String user) throws JSONException {
		
		JSONObject obj2 = new JSONObject(); 
		  obj2.put("user", user);
		return obj2.toString();
	}
	Context context=null;
	AsyncTask_refresh(Context context)
	{
		this.context=context;
	}
		@Override
		 protected Void doInBackground(Void... params) {
		 try {
			   System.out.println("refreshhhhhhhhhhhhhhh");
			   
			   //////////////////////////////////////////////////////////////////
			   
			   StringEntity p= new StringEntity(jsonString("eman"),"UTF-8");
			   
			   SimpleHttpClient.executeHttpPost("https://192.168.1.4:8444/Musar/rest/refresh",p,context); 
		   } catch (Exception e) {
		    e.printStackTrace();
		   
		   }
		return null;
		  
		 }
		}
	}