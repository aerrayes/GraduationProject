package com.musar.Database;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "Applications";
 
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    
    private static final String TABLE_APPS = "apps";
    private static final String KEY_TYPE = "Type";
    private static final String TOTAL_TIME="totalTime";

    private static final String TABLE_ACTIVTY = "activity";
    private static final String KEY_APPID = "appID";
    private static final String KEY_TIME = "time";
    private static final String KEY_DURATION = "duration";
    
    private static final String TABLE_RECOMMENDED = "recommended_apps";
    private static final String KEY_RECOMMENDED_NAME = "recommended_name";
    private static final String KEY_RECOMMENDED_RATE = "recommended_rate";
    private static final String KEY_RECOMMENDED_PACKAGE = "recommended_package";
    private static final String KEY_RECOMMENDED_ICON = "recommended_icon";
    private static final String KEY_RECOMMENDED_ID = "recommended_ID";
    
    
    private static final String TABLE_RECOMMENDED_USER = "recommended_usersTABLE";
    private static final String KEY_RECOMMENDED_USER = "recommended_user";
    private static final String KEY_RECOMMENDED_USER_IMAGE= "recommended_user_image";
    private static final String KEY_RECOMMENDEDuser_ID = "recommended_user_id";
    private static final String KEY_RECOMMEND_ID="recommend_id";
    
    private static final String TABLE_STARTED_ACTIVTY = "started";
 
    private static final String TABLE_BANNED_APPLICATION = "banned_apps";
    
    public DatabaseHandler(Context context) {
    	
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
       
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        
        String CREATE_APPS_TABLE = "CREATE TABLE " + TABLE_APPS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                +KEY_TYPE + " TEXT," +TOTAL_TIME + " REAL"+ ")";
        String CREATE_ACTIVITY_TABLE = "CREATE TABLE " + TABLE_ACTIVTY + "("
        		+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_APPID +" INTEGER, "
        		+ KEY_TIME + " DATETIME, " + KEY_DURATION +" INTEGER, "
        		+"FOREIGN KEY (" + KEY_APPID + ") REFERENCES "+TABLE_APPS+" ("
        		+ KEY_ID + ")" 
        		+ ");";
        String CREATE_STARTED_ACTIVITY_TABLE = "CREATE TABLE " + TABLE_STARTED_ACTIVTY+ "("
        		+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_APPID +" INTEGER, "
        		+ KEY_TIME + " DATETIME, " + KEY_DURATION +" INTEGER, "
        		+"FOREIGN KEY (" + KEY_APPID + ") REFERENCES "+TABLE_APPS+" ("
        		+ KEY_ID + ")" 
        		+ ");";
        String CREATE_RECOMMENDED_TABLE = "CREATE TABLE " + TABLE_RECOMMENDED + "("
                + KEY_RECOMMENDED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_RECOMMENDED_NAME + " TEXT,"
                + KEY_RECOMMENDED_RATE + " DOUBLE," + KEY_RECOMMENDED_PACKAGE + " TEXT," + KEY_RECOMMENDED_ICON + " TEXT"+")";
        
        String CREATE_RECOMMENDED_USER_TABLE = "CREATE TABLE " + TABLE_RECOMMENDED_USER + "("
                + KEY_RECOMMENDEDuser_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +KEY_RECOMMEND_ID + " INTEGER,"
                + KEY_RECOMMENDED_USER + " TEXT," +KEY_RECOMMENDED_USER_IMAGE + " TEXT,"
                + "FOREIGN KEY (" + KEY_RECOMMEND_ID + ") REFERENCES "+TABLE_RECOMMENDED+" ("
        		+ KEY_RECOMMENDED_ID + ")" + ")";
        
        String CREATE_BANNED_APPLICATION_TABLE = "CREATE TABLE " + TABLE_BANNED_APPLICATION + "("
        		+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT" + ")";
       

        db.execSQL(CREATE_APPS_TABLE);
        db.execSQL(CREATE_ACTIVITY_TABLE);
        db.execSQL(CREATE_STARTED_ACTIVITY_TABLE);
        db.execSQL(CREATE_RECOMMENDED_TABLE);
        db.execSQL(CREATE_RECOMMENDED_USER_TABLE);
        db.execSQL(CREATE_BANNED_APPLICATION_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVTY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STARTED_ACTIVTY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECOMMENDED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECOMMENDED_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BANNED_APPLICATION);
        
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new app
    
    public void addBannedApplication(BannedApplication bannedApp)
    {
    	 SQLiteDatabase db = this.getWritableDatabase();
    	 
         ContentValues values = new ContentValues();
         
         
         values.put(KEY_NAME, bannedApp.getName());
  
         // Inserting Row
         db.insert(TABLE_BANNED_APPLICATION, null, values);
         db.close(); // Closing database connection
    }
   
   
    public void addApp(Application app)
    {
    	 SQLiteDatabase db = this.getWritableDatabase();
    	 
         ContentValues values = new ContentValues();
         values.put(KEY_NAME, app.getName()); 
         values.put(KEY_TYPE, app.getType()); 
         values.put(TOTAL_TIME, app.getTime()); 
         // Inserting Row
         db.insert(TABLE_APPS, null, values);
         db.close(); // Closing database connection
    }
    public void add_recommended_app(app_recommended app)
    {
    	 SQLiteDatabase db = this.getWritableDatabase();
    	 
         ContentValues values = new ContentValues();
         values.put(KEY_RECOMMENDED_NAME, app.get_name()); // Contact Name
         values.put(KEY_RECOMMENDED_RATE, app.get_rate()); // Contact Phone
         values.put(KEY_RECOMMENDED_PACKAGE, app.get_package());
         values.put(KEY_RECOMMENDED_ICON, app.getIcon());
         // Inserting Row
         db.insert(TABLE_RECOMMENDED, null, values);
         db.close(); // Closing database connection
    }
    public void add_recommended_user(recommended_user app)
    {
    	 SQLiteDatabase db = this.getWritableDatabase();
    	 
         ContentValues values = new ContentValues();
         values.put(KEY_RECOMMENDED_USER, app.get_user()); 
         values.put(KEY_RECOMMENDED_USER_IMAGE, app.get_image()); 
         values.put(KEY_RECOMMEND_ID, app.get_App_ID());
         // Inserting Row
         db.insert(TABLE_RECOMMENDED_USER, null, values);
         db.close(); // Closing database connection
    }
    public void addActivity(AppActivity activity)
    {
    	 SQLiteDatabase db = this.getWritableDatabase();
    	 
         ContentValues values = new ContentValues();
         
        
         values.put(KEY_APPID, activity.getAppID());
         values.put(KEY_DURATION, activity.getDuration()); // Contact Name
         values.put(KEY_TIME, activity.getTime()); // Contact Phone
  
         // Inserting Row
         db.insert(TABLE_ACTIVTY, null, values);
         db.close(); // Closing database connection
    }
    
    public void addStartedActivity(StartedActivity activity)
    {
    	 SQLiteDatabase db = this.getWritableDatabase();
    	 
         ContentValues values = new ContentValues();
         
         values.put(KEY_APPID, activity.getAppID());
         values.put(KEY_DURATION, activity.getDuration()); // Contact Name
         values.put(KEY_TIME, activity.getTime()); // Contact Phone
  
         // Inserting Row
         db.insert(TABLE_STARTED_ACTIVTY, null, values);
         db.close(); // Closing database connection
    }
    
    // Getting single app
   
    
    public Application getApp(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_APPS, new String[] { KEY_ID,
                KEY_NAME, KEY_TYPE,TOTAL_TIME }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (!cursor.moveToFirst() )
            return null;
        Application app = new Application(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),Double.parseDouble(cursor.getString(3)));
        // return contact
        return app;
    }
    
    public Application getApp(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_APPS, new String[] { KEY_ID,
                KEY_NAME, KEY_TYPE,TOTAL_TIME }, KEY_NAME + "=?",
                new String[] { name }, null, null, null, null);
        if (!cursor.moveToFirst() )
        	return null;
            //cursor.moveToFirst();
        //else return null;
        Application app = new Application(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),Double.parseDouble(cursor.getString(3)));
        // return contact
        db.close();
        return app;
    }
    public int getAppRecommended(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_RECOMMENDED, new String[] { KEY_RECOMMENDED_ID }, KEY_RECOMMENDED_NAME + "=?",
                new String[] { name }, null, null, null, null);
        if (!cursor.moveToFirst() )
        	return 0;
            //cursor.moveToFirst();
        //else return null;
        int id=Integer.parseInt(cursor.getString(0));
        // return contact
        db.close();
        return id;
    }
    public app_recommended getAppRecommended(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_RECOMMENDED, new String[] { KEY_RECOMMEND_ID,KEY_RECOMMENDED_NAME,KEY_RECOMMENDED_RATE,KEY_RECOMMENDED_PACKAGE,KEY_RECOMMENDED_ICON }, KEY_RECOMMEND_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (!cursor.moveToFirst() )
        	return null;
            //cursor.moveToFirst();
        //else return null;
        app_recommended app=new app_recommended(cursor.getString(1),Double.parseDouble(cursor.getString(2)),cursor.getString(3),cursor.getString(4));
        // return contact
        db.close();
        return app;
    }
    public StartedActivity getStartedActivity(String appID) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_STARTED_ACTIVTY, new String[] { KEY_ID,
                KEY_APPID, KEY_TIME, KEY_DURATION}, KEY_APPID + "=?",
                new String[] { appID }, null, null, null, null);
        if(!cursor.moveToFirst())
        	return null;
	           // cursor.moveToFirst();
	 
	        StartedActivity app = new StartedActivity(Integer.parseInt(cursor.getString(0)),
	        		Integer.parseInt(cursor.getString(1)), cursor.getString(2)
	        		,Integer.parseInt(cursor.getString(3)));
	        // return contact
	        return app;
        
    }
   
    public AppActivity getActivity(String appName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_APPS, new String[] { KEY_ID,
        		KEY_NAME}, KEY_NAME + "=?",
                new String[] { appName }, null, null, null, null);
        if(cursor.moveToFirst()){
        	 Cursor cursor_1 = db.query(TABLE_ACTIVTY, new String[] { KEY_ID,
                     KEY_APPID, KEY_TIME, KEY_DURATION}, KEY_APPID + "=?",
                     new String[] {String.valueOf(Integer.parseInt(cursor.getString(0)))}, null, null, null, null);
        	 if(!cursor_1.moveToFirst())
             	return null;
     	           // cursor.moveToFirst();
     	 
     	        AppActivity app = new AppActivity(Integer.parseInt(cursor_1.getString(0)),
     	        		Integer.parseInt(cursor_1.getString(1)), cursor_1.getString(2)
     	        		,Integer.parseInt(cursor_1.getString(3)));
     	        // return contact
     	        return app;
        }
        else return null;
    }
    // Getting All apps
    public List<app_recommended> getAllApps_recommended() {
        List<app_recommended> appList = new ArrayList<app_recommended>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RECOMMENDED;
      
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                app_recommended app = new app_recommended();
                app.set_id(Integer.parseInt(cursor.getString(0)));
                app.set_name(cursor.getString(1));
                app.set_rate(Double.parseDouble(cursor.getString(2)));
                app.set_app_Package(cursor.getString(3));
                app.setIconURL(cursor.getString(4));
                // Adding contact to list
                appList.add(app);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return appList;
    }
    
    // Getting All apps
    public List<recommended_user> getAlluser_recommended() {
        List<recommended_user> appList = new ArrayList<recommended_user>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RECOMMENDED_USER;
        
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                recommended_user app = new recommended_user();
                
                app.set_id(Integer.parseInt(cursor.getString(0)));
                app.set_APP_id(Integer.parseInt(cursor.getString(1)));
                app.set_user(cursor.getString(2));
                app.set_image(cursor.getString(3));

                // Adding contact to list
                appList.add(app);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return appList;
    }
    public List<recommended_user> get_usersFromApp(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECOMMENDED_USER, new String[] { KEY_RECOMMENDEDuser_ID,
        		KEY_RECOMMEND_ID, KEY_RECOMMENDED_USER, KEY_RECOMMENDED_USER_IMAGE}, KEY_RECOMMEND_ID + "=?",
                     new String[] {String.valueOf(id)}, null, null, null, null);
        List<recommended_user>app_recommended=new ArrayList<recommended_user>();
        if (cursor.moveToFirst()) {
            do {
                recommended_user app = new recommended_user();
                app.set_id(Integer.parseInt(cursor.getString(0)));
                app.set_APP_id(Integer.parseInt(cursor.getString(1)));
                app.set_user(cursor.getString(2));
                app.set_image((cursor.getString(3)));
                // Adding contact to list
                app_recommended.add(app);
            } while (cursor.moveToNext());
        }
        return app_recommended;
    }

    public List<Application> getAllApps() {
        List<Application> appList = new ArrayList<Application>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_APPS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Application app = new Application();
                app.setID(Integer.parseInt(cursor.getString(0)));
                app.setName(cursor.getString(1));
                app.setType(cursor.getString(2));
                app.setTime(Double.parseDouble(cursor.getString(3)));
                // Adding contact to list
                appList.add(app);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return appList;
    }
    public List<AppActivity> getAllActivity() {
        List<AppActivity> actList = new ArrayList<AppActivity>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ACTIVTY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if(cursor!=null){
        if (cursor.moveToFirst()) {
            do {
                AppActivity act = new AppActivity();
                act.setID(Integer.parseInt(cursor.getString(0)));
                act.setAppID(Integer.parseInt(cursor.getString(1)));
                act.setTime(cursor.getString(2));
                act.setDuration(Integer.parseInt(cursor.getString(3)));
                
                String appName = getApp(act.getAppID()).getName();
                act.setAppName(appName);
                // Adding contact to list
                actList.add(act);
            } while (cursor.moveToNext());
        }
        }
        else
        {
        	db.close();
        	return null;
        }
        db.close();
        // return contact list
        return actList;
    }
    
    public List<AppActivity> getAllActivityTodaySortedDecDuration() {
        List<AppActivity> actList = new ArrayList<AppActivity>();
        // Select All Query
        SQLiteDatabase db = this.getWritableDatabase();
        Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE,0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		
		Calendar tom = today;
		tom.add(Calendar.DAY_OF_YEAR, 1);
		//KEY_TIME +" >=? and "+ KEY_TIME +"<?",
        //new String[] {today.toString() ,tom.toString()}//mawdo3 today da ma3aslag ma3aya :( beyedy array mafehash 7aga :(
        Cursor cursor = db.query(TABLE_ACTIVTY, null, null,null ,  null, null, KEY_DURATION + " DESC");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AppActivity act = new AppActivity();
                act.setID(Integer.parseInt(cursor.getString(0)));
                act.setAppID(Integer.parseInt(cursor.getString(1)));
                act.setTime(cursor.getString(2));
                act.setDuration(Integer.parseInt(cursor.getString(3)));
                String appName = getApp(act.getAppID()).getName();
                act.setAppName(appName);
                // Adding contact to list
                actList.add(act);
            } while (cursor.moveToNext());
        }
        else
        {
        	db.close();
        	return null;
        }
        db.close();
        // return contact list
        return actList;
    }
    
    public List<Application> getAllAppsSorting() {
        List<Application> appList = new ArrayList<Application>();
        // Select All Query
        SQLiteDatabase db = this.getWritableDatabase();
        
        Cursor cursor = db.query(TABLE_APPS, null,null,null,  null, null, TOTAL_TIME + " ASC");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Application app = new Application();
                app.setID(Integer.parseInt(cursor.getString(0)));
                app.setName(cursor.getString(1));
                app.setType(cursor.getString(2));
                app.setTime(Double.parseDouble(cursor.getString(3)));
                // Adding contact to list
                appList.add(app);
            } while (cursor.moveToNext());
        }
        else
        {
        	db.close();
        	return null;
        }
        db.close();
        // return contact list
        return appList;
    }
    public List<StartedActivity> getAllStartedActivity() {
        List<StartedActivity> actList = new ArrayList<StartedActivity>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_STARTED_ACTIVTY;
        
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StartedActivity act = new StartedActivity();
                act.setID(Integer.parseInt(cursor.getString(0)));
                act.setAppID(Integer.parseInt(cursor.getString(1)));
                act.setTime(cursor.getString(2));
                act.setDuration(Integer.parseInt(cursor.getString(3)));
                // Adding contact to list
                actList.add(act);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return actList;
    }
    public List<BannedApplication> getAllBannedApps() {
        List<BannedApplication> appList = new ArrayList<BannedApplication>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BANNED_APPLICATION;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	BannedApplication app = new BannedApplication();
                app.setId(Integer.parseInt(cursor.getString(0)));
                app.setName(cursor.getString(1));
                // Adding contact to list
                appList.add(app);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return appList;
    }
    // Updating single application   
    
    public int updateApplication(Application app) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, app.getName());
        values.put(KEY_TYPE, app.getType());
 
        // updating row
        return db.update(TABLE_APPS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(app.getID()) });
    }
 
    public void updateStartedActivity(StartedActivity activity) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_APPID, activity.getAppID());
        values.put(KEY_TIME, activity.getTime());
        values.put(KEY_DURATION, activity.getDuration());
 
        // updating row
        db.update(TABLE_STARTED_ACTIVTY, values, KEY_ID + " = ?",
                new String[] { String.valueOf(activity.getID()) });
        db.close();
        
    }
    
    public void updateAppTime(Application app) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, app.getName());
        values.put(KEY_TYPE, app.getType());
        values.put(TOTAL_TIME, app.getTime());
 
        // updating row
        db.update(TABLE_APPS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(app.getID()) });
        db.close();
    }
    
    public void updateActivity(AppActivity activity) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_APPID, activity.getAppID());
        values.put(KEY_TIME, activity.getTime());
        values.put(KEY_DURATION, activity.getDuration());
 
        // updating row
        db.update(TABLE_ACTIVTY, values, KEY_ID + " = ?",
                new String[] { String.valueOf(activity.getID()) });
        db.close();
    }
   
    
   
    public void clearMostApps()
    {
    
    	List<AppActivity>activities=new ArrayList<AppActivity>();
    	
    	activities=getAllActivity();
    	SQLiteDatabase db = this.getWritableDatabase();
    	for(int i=0;i<activities.size();i++)
    	{
    		db.delete(TABLE_ACTIVTY, KEY_ID + " = ?",
                    new String[] { String.valueOf(activities.get(i).getID()) });
    	}
    	db.close();
    }
    public void clearStartedActivity()
    {
    
    	List<StartedActivity>activities=new ArrayList<StartedActivity>();
    	
    	activities=getAllStartedActivity();
    	SQLiteDatabase db = this.getWritableDatabase();
    	for(int i=0;i<activities.size();i++)
    	{
    		db.delete(TABLE_STARTED_ACTIVTY, KEY_ID + " = ?",
                    new String[] { String.valueOf(activities.get(i).getID()) });
    	}
    	db.close();
    }
    public void clearApps()
    {
    
    	List<Application>apps=new ArrayList<Application>();
    	
    	apps=getAllApps();
    	SQLiteDatabase db = this.getWritableDatabase();
    	for(int i=0;i<apps.size();i++)
    	{
    		db.delete(TABLE_APPS, KEY_ID + " = ?",
                    new String[] { String.valueOf(apps.get(i).getID()) });
    	}
    	db.close();
    }
    public void clearRecommended()
    {
    
    	List<app_recommended>activities=new ArrayList<app_recommended>();
    	
    	activities=getAllApps_recommended();
    	SQLiteDatabase db = this.getWritableDatabase();
    	for(int i=0;i<activities.size();i++)
    	{
    		db.delete(TABLE_RECOMMENDED, KEY_RECOMMENDED_ID + " = ?",
                    new String[] { String.valueOf(activities.get(i).get_id()) });
    	}
    	db.close();
    }
    public void clearRecommendedUser()
    {
    
    	List<recommended_user>activities=new ArrayList<recommended_user>();
    	
    	activities=getAlluser_recommended();
    	SQLiteDatabase db = this.getWritableDatabase();
    	for(int i=0;i<activities.size();i++)
    	{
    		db.delete(TABLE_RECOMMENDED_USER, KEY_RECOMMENDEDuser_ID + " = ?",
                    new String[] { String.valueOf(activities.get(i).get_id()) });
    	}
    	db.close();
    }
    public void clearBannedApps()
    {
    
    	List<BannedApplication>activities=new ArrayList<BannedApplication>();
    	
    	activities=getAllBannedApps();
    	SQLiteDatabase db = this.getWritableDatabase();
    	for(int i=0;i<activities.size();i++)
    	{
    		db.delete(TABLE_BANNED_APPLICATION, KEY_ID + " = ?",
                    new String[] { String.valueOf(activities.get(i).getId()) });
    	}
    	db.close();
    }
    public void deleteStartedActivity(StartedActivity activity) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STARTED_ACTIVTY, KEY_ID + " = ?",
                new String[] { String.valueOf(activity.getID()) });
        db.close();
    }
    public void deleteApp_recommended(int id_app) {
        SQLiteDatabase db = this.getWritableDatabase();
   
        db.delete(TABLE_RECOMMENDED, KEY_RECOMMENDED_ID + " = ?",
                new String[] { String.valueOf(id_app) });
        db.close();
    }
    public void deleteUser_recommended(int id_app) {
    	
        SQLiteDatabase db = this.getWritableDatabase();
  
        db.delete(TABLE_RECOMMENDED_USER, KEY_RECOMMEND_ID + " = ?",
                new String[] { String.valueOf(id_app) });
        db.close();
    }
    public void deleteApplication(Application app) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_APPS, KEY_ID + " = ?",
                new String[] { String.valueOf(app.getID()) });
        db.close();
    }
    // Getting contacts Count
   
    public int getAppsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_APPS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }

	public void addOrUpdateActivity(AppActivity appActivity) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getReadableDatabase();
		Application app = getApp(appActivity.getAppID());
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE,0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		
		Calendar tom = Calendar.getInstance();
		tom.add(Calendar.DAY_OF_YEAR, 1);
		tom.set(Calendar.HOUR_OF_DAY, 0);
		tom.set(Calendar.MINUTE,0);
		tom.set(Calendar.SECOND, 0);
		tom.set(Calendar.MILLISECOND, 0);
		//nafs moshkelet today de :( hena bardo 
        Cursor cursor = db.query(TABLE_ACTIVTY, null, KEY_APPID + "=?",
                new String[] {Integer.toString(app.getID())},  null, null, null);
        if (!cursor.moveToFirst() )
        {
        	addActivity(appActivity);
        }
        else
        {
        	AppActivity act = new AppActivity();
            act.setID(Integer.parseInt(cursor.getString(0)));
            act.setAppID(Integer.parseInt(cursor.getString(1)));
            act.setTime(cursor.getString(2));
            act.setDuration(Integer.parseInt(cursor.getString(3))+appActivity.getDuration());
            updateActivity(act);
        }
	}
	 public void deleteBannedApplication(BannedApplication app) {
	        SQLiteDatabase db = this.getWritableDatabase();
	        db.delete(TABLE_BANNED_APPLICATION, KEY_NAME + " = ?",
	                new String[] { app.getName() });
	        db.close();
	    }
}