package com.example.phone;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import com.example.phone.contacts.Constants;
import com.example.phone.contacts.ContactModel;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;

public class GetContacts {


    SharedPreferences sharedPreferences;
    ArrayList<ContactModel> contacts;
    Gson gson = new Gson();


//    public GetContacts(){
//        return;
//    }

    public GetContacts(Context ctx) {
        sharedPreferences = ctx.getSharedPreferences(Constants.CONTACT_PREFERENCE, Context.MODE_PRIVATE);


        ArrayList<ContactModel> list = new ArrayList<>();
        String lastNumber = "0";
        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(ctx.getContentResolver(),
                            ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));

                    Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id));
                    Uri pURI = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

                    Bitmap photo = null;
                    if (inputStream != null) {
                        photo = BitmapFactory.decodeStream(inputStream);
                    }
                    while (cursorInfo.moveToNext()) {
                        String number = cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        number = number.replaceAll("\\s","");
                        if(number.equals(lastNumber)){

                        }else{
                            lastNumber = number;
                            ContactModel info = new ContactModel();
                            info.id = id;
                            info.name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            info.mobileNumber = number;
                            info.photo = photo;
                            info.photoURI= pURI.toString();
                            list.add(info);
                        }

                    }



                    cursorInfo.close();
                }
            }
            cursor.close();
        }
        contacts = list;

        String contactsJson = gson.toJson(list);
//        Log.d("TAG", "GetContacts: "+contactsJson);
        sharedPreferences.edit().putString(Constants.CONTACTS, contactsJson).apply();
    }

    public ArrayList<ContactModel> getContacts(){
        return contacts;
    }
}
