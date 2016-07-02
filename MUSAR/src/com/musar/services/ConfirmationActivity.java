package com.musar.services;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.musar.gui.R;
import com.musar.system.SimpleHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConfirmationActivity extends Activity{

	/**
	 * @param args
	 */
	private static String ServerToken;
	private long startTime = 0L;
	private Handler customHandler = new Handler();
	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime = 0L;
	TextView timerValue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirmation_layout);			
			final EditText mEdit;			
			RequestToken();//<<<<<<<<<<<<< 
			Button   mButton;
		   
           mButton = (Button)findViewById(R.id.button1);
		   mEdit   = (EditText)findViewById(R.id.editText1);
		   timerValue = (TextView) findViewById(R.id.timerValue);
		   startTime = SystemClock.uptimeMillis();
		   customHandler.postDelayed(updateTimerThread, 0);
		   
           mButton.setOnClickListener(
			new View.OnClickListener()
			{
			  @SuppressWarnings("deprecation")
			public void onClick(View view)
			   {
				  System.out.println(timerValue.getText().toString());
				  int index=timerValue.getText().toString().indexOf(":");
				  System.out.println(index);
				  int min=Integer.parseInt(timerValue.getText().toString().substring(0, index));
				  int sec=Integer.parseInt(timerValue.getText().toString().substring(index+1, timerValue.getText().toString().length()));
				  System.out.println(min+"sec"+sec);
				  if (ServerToken.equals(mEdit.getText().toString())&&min<4&&sec<59){
					 register register_object=new register();
					 register_object.execute();
					  
				   return; 
			   }
			   else {
				AlertDialog alertDialog = new AlertDialog.Builder( ConfirmationActivity.this).create();
	            // Setting Dialog Title
	            alertDialog.setTitle("Registeration Error!");
	            // Setting Dialog Message
	            alertDialog.setMessage("there is error with your registeration please enter correct token in limited time :D");
	            // Setting OK Button
	            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	           }
	        });
	        // Showing Alert Message
	        alertDialog.show();
				}
			}
			});
        
		}
	 //this must be asynch task because we need to return id and if we implement thread we can't return it from thread
	public class register extends  AsyncTask<Void,Void,Void>{
		@SuppressLint("NewApi")
		public String jsonString(String user) throws JSONException {
			
			JSONObject obj2 = new JSONObject(); 
			  obj2.put("user", user);
			return obj2.toString();
		}
	@Override
	 protected Void doInBackground(Void... params) {
	 try {
		 System.out.println("da5al Asyncktask");
		String phone_number=getData("phone_number");
		StringEntity p= new StringEntity(jsonString(phone_number),"UTF-8");
		String response=SimpleHttpClient.executeHttpPost("https://env-8861173.j.layershift.co.uk/rest/register",p,ConfirmationActivity.this); 
		String res = response.toString();
		String id = res.replaceAll("\\s+", "");
		SharedPreferences settings = getApplicationContext().getSharedPreferences("save", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("id", id);
		editor.commit();
		System.out.println("doneeeeeeeeeeee");
		System.out.println("id   :"+id);
		saveData("ServerServiceRevive", true);
	//	startService(new Intent(getApplicationContext(),MainService.class));
		//save the first time boolean
		saveData("first_time",true);
		finish();
		//open the home page :D
		//Intent intent = new Intent(ConfirmationActivity.this,MainActivity.class);
		//startActivity(intent);
		   }
		 catch (Exception e) {
	    e.printStackTrace();
	   }
	  return null;
	 }
	}
	public void saveData(String key, boolean value) {
		SharedPreferences settings = getApplicationContext()
				.getSharedPreferences("save", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	private String getData(String key)
	{
		SharedPreferences settings = getApplicationContext()
				.getSharedPreferences("save", 0);
		return settings.getString(key, null);
	}
	 private Runnable updateTimerThread = new Runnable() {
		 public void run() {
		  timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
		  updatedTime = timeSwapBuff + timeInMilliseconds;
		  int secs = (int) (updatedTime / 1000);
		  int mins = secs / 60;
		  secs = secs % 60;
		  //int milliseconds = (int) (updatedTime % 1000);
		  timerValue.setText("" + mins + ":"+ String.format("%02d", secs));
		  customHandler.postDelayed(this, 0);
		         }
		     };

	private void RequestToken(){
		SharedPreferences settings = getApplicationContext().getSharedPreferences("save", 0);
		ServerToken= settings.getString("token", null);
	}


}
