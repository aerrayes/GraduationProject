package com.musar.services;

import java.util.Timer;
import java.util.TimerTask;


import com.musar.system.ServerCall;
import com.musar.youtubedownloader.music_track;
import com.musar.youtubedownloader.youtube_service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class MainService extends Service {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	Boolean Revive = true;
	Boolean TrackerServiceAlive = false;
	Boolean LocationServiceAlive = false;
	Boolean LogsServiceAlive = false;
	Boolean ServerServiceAlive=false;
	Boolean uTubeServiceAlive=false;
	Boolean TrackerServiceRevive=false;
	Boolean LocationServiceRevive=false;
	Boolean LogsServiceRevive = false;
	Boolean ServerServiceRevive = false;
	Boolean uTubeServiceRevive = false;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		tickTime(6000);
		return Service.START_STICKY;

	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private void ReviveLogs(){
		LogsServiceAlive = getSaveData1("LogsServiceAlive");
		LogsServiceRevive = getPrefSaveData("LogsServiceRevive");
		if (!LogsServiceAlive && LogsServiceRevive) {
			startService(new Intent(getApplicationContext(),
					LogsService.class));
		}
		if (LogsServiceAlive && !LogsServiceRevive) {
			saveData("LogsServiceAlive",false);
			stopService(new Intent(getApplicationContext(),
					LogsService.class));
		}
	}
	private void reviveServices() {
		
	  //  System.out.println("Tracker service alive? : "+TrackerServiceAlive + " , Revive: " +TrackerServiceRevive);
			TrackerServiceRevive = getPrefSaveData("TrackerServiceRevive");
			TrackerServiceAlive = getSaveData1("TrackerServiceAlive");
			LocationServiceAlive = getSaveData1("LocationServiceAlive");
			LocationServiceRevive = getPrefSaveData("LocationServiceRevive");
			uTubeServiceRevive = getSaveData1("uTubeServiceRevive");
			uTubeServiceAlive = getSaveData1("uTubeServiceAlive");
			ServerServiceRevive = getSaveData1("ServerServiceRevive");
			ServerServiceAlive = getSaveData1("ServerServiceAlive");
		if (!TrackerServiceAlive && TrackerServiceRevive) {
			startService(new Intent(getApplicationContext(),
					TrackerService.class));
		}
		if(TrackerServiceAlive && !TrackerServiceRevive)
		{
			saveData("TrackerServiceAlive",false);
			stopService(new Intent(getApplicationContext(),TrackerService.class));
		}
		//System.out.println("Location service alive? : "+LocationServiceAlive + " Revive: "+LocationServiceRevive);
		if (!LocationServiceAlive&&LocationServiceRevive) {
			startService(new Intent(getApplicationContext(),
					LocationService.class));
		}
		if(LocationServiceAlive && !LocationServiceRevive)
		{
			saveData("LocationServiceAlive",false);
			stopService(new Intent(getApplicationContext(),LocationService.class));
		}
		
	    //Eman's work :D
		if (!uTubeServiceAlive&&uTubeServiceRevive) {
		  System.out.println("youtube is working");
		   //music
		   startService(new Intent(getApplicationContext(),music_track.class));
		}

		if(uTubeServiceAlive && !uTubeServiceRevive)
		{
			//TODO eman check here if that's correct or do we have to stop another service
			stopService(new Intent(getApplicationContext(),music_track.class));
		}
		//server////////////////////////////////////////////////////////////////////
	//	System.out.println("server service alive? : "+ServerServiceAlive + " Revive: "+ServerServiceRevive);
		//I don't need the tracking is working because we can recommend even if tracking has been stopped
		if (!ServerServiceAlive&&ServerServiceRevive ) {
			System.out.println("Server is working");
			startService(new Intent(getApplicationContext(),ServerCall.class));
		}
		if(uTubeServiceAlive && !uTubeServiceRevive)
		{
			stopService(new Intent(getApplicationContext(),youtube_service.class));
			stopService(new Intent(getApplicationContext(),music_track.class));
		}

		//TrackerServiceAlive = getSaveData1("TrackerServiceAlive");
		//LocationServiceAlive = getSaveData1("LocationServiceAlive");
		//LocationServiceRevive = getPrefSaveData("LocationServiceRevive");
		//uTubeServiceRevive = getSaveData1("uTubeServiceRevive");
		//uTubeServiceAlive = getSaveData1("uTubeServiceAlive");
		//ServerServiceRevive = getSaveData1("ServerServiceRevive");
		//ServerServiceAlive = getSaveData1("ServerServiceAlive");
		
	}

	private void tickTime(int TimeInterval) {
		TimerTask tasknew = new TimerTask() {
			@Override
			public void run() {
				//System.out.println("entered main service");
				reviveServices();
			}

		};
		TimerTask callsTask = new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ReviveLogs();
			}
		};
		Timer timer = new Timer();
		int initialDelay = 1000;
		timer.scheduleAtFixedRate(callsTask,initialDelay , TimeInterval);
		// int timeInMS = 1800000;// 30 minutes in milli-seconds.
		
		timer.scheduleAtFixedRate(tasknew, initialDelay, TimeInterval);
	}

	public void saveData(String key, boolean value) {
		SharedPreferences settings = getApplicationContext()
				.getSharedPreferences("save", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	private boolean getPrefSaveData(String key)
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		// then you use
		return prefs.getBoolean(key, false);	
	}
	
	private boolean getSaveData1(String key) {
		SharedPreferences settings = getApplicationContext()
				.getSharedPreferences("save", 0);
		return settings.getBoolean(key, false);
	}
	

	
	@Override
	public void onDestroy() {
		// TODO To be implemented to revive the service again
		Toast.makeText(getApplicationContext(), "Bye bye Main service",
				Toast.LENGTH_LONG).show();
		startService(new Intent(this, MainService.class));
		super.onDestroy();
	}
}
