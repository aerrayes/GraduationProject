package com.musar.Database;

import java.util.ArrayList;
import java.util.List;

public class app_recommended {
	String app_name;
	Double rate;
	int id;
	String app_package;
	String IconURL;
	public app_recommended(){}
	public app_recommended(String app_name,Double rate,String app_package,String IconURL)
	{
		this.app_name=app_name;
		this.rate=rate;
		this.app_package=app_package;
		this.IconURL=IconURL;
	}
	public void set_name(String app_name){this.app_name=app_name;}
	public void set_rate(Double rate){this.rate=rate;}
	public void set_id(int id){this.id=id;}
	public void set_app_Package(String app_package){this.app_package=app_package;}
	public void setIconURL(String Icon){this.IconURL=Icon;}
	
	public String get_name(){return this.app_name;}
	public Double get_rate(){return this.rate;}
	public int get_id(){return id;}
	public String get_package(){return this.app_package;}
	public String getIcon(){return IconURL;}
	
}
