package com.musar.services;

import java.util.ArrayList;


import com.musar.Database.Contact;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.TelephonyManager;
import android.util.Base64;

public class ContactsManager  {

	/**
	 * @param args
	 */
	Context service;
	ArrayList<Contact>contact=new ArrayList<Contact>();
	public ArrayList<Integer>images=new ArrayList<Integer>();
	public ContactsManager(Context service){this.service=service;}
	
	// // getting users phone
		public String getUsersphone(){
			  @SuppressWarnings("static-access")
			TelephonyManager telMgr = (TelephonyManager)service.getSystemService(service.TELEPHONY_SERVICE);
			  String phone_number=telMgr.getLine1Number();
	            System.out.println("Mobile Number : "+ telMgr.getLine1Number());
	            System.out.println("Sim Serial Number : "+ telMgr.getSimSerialNumber());
	           
	            return phone_number;
		}
		//-----------------------
		public ArrayList<Contact> GetContacts(Context context) throws Exception{
			System.out.println("contactttttttttttttttttttttttttttttttttttttttttttttt");
			    String contactNumber = null;
			    //int contactNumberType = Phone.TYPE_MOBILE;
			    String nameOfContact = null;
			    if (true) {
			        ContentResolver cr = context.getContentResolver();
			        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,null, null, null);
			        if (cur.getCount() > 0) {
			            while (cur.moveToNext()) {
			                String id = cur.getString(cur.getColumnIndex(BaseColumns._ID));
			                nameOfContact = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
			                Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+ " = ?", new String[] { id },null);
			                          
			                while (phones.moveToNext()) {
			                  contactNumber = phones.getString(phones.getColumnIndex(Phone.NUMBER));
			                  int photo_id=phones.getInt(phones.getColumnIndex(Phone.PHOTO_ID));
			                  //I think the decoder of id must be after it will come from the server (security and so on)
			                  //System.out.println("photooooooo id"+photo_id);
			                  //byte [] image=queryContactImage(photo_id,context);
			                  //String image_2=byteToBase64(image);
			                  images.add(photo_id);
			                  //contactNumberType = phones.getInt(phones.getColumnIndex(Phone.TYPE));
			                  //System.out.println("Contact"+ "...Contact Name ...." + nameOfContact+ "...contact Number..." + contactNumber+"image:"+image_2);
			                  Contact c=new Contact(nameOfContact,contactNumber);
			                  contact.add(c);
			                  }
			                  phones.close();
			                }

			            }
			        }// end of contact name cursor
			        cur.close();

			    }
			    return contact;
			}
		  public static String byteToBase64(byte[] data) {
			 
			  if(data!=null){
				  System.out.println("string:"+new String (data));
				  return  new String(data);
			  }
			  else return null;
		  }  
		  public static byte[] base64ToByte(String data) throws Exception { 
			  if(data!=null)
			  { return Base64.decode(data, Base64.DEFAULT);}
			  return null;
		  }  


}
