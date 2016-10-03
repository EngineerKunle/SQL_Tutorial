package com.engineerkunle.sqliteapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.engineerkunle.sqliteapplication.Utils.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EngineerKunle on 26/09/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper{

    // when changing Schema database version needs to be incremented
    private static final int DATABASE_VERSION = 0;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts table columns name
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_name";


    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + " (" + KEY_ID +
                " INTERGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_PH_NO + " TEXT" + " )";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        //create again
        onCreate(db);
    }

    // Adding new Contact
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.get_name());
        values.put(KEY_PH_NO, contact.get_phone_number());

        //Insert rows
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); //close database

    }

    // Getting single contact
    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] { KEY_ID,
                KEY_NAME, KEY_PH_NO };
        String selection =  KEY_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(id) };

        Cursor cursor = db.query(TABLE_CONTACTS, columns ,selection,
                whereArgs, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        //return contact
        return contact;
    }

    // Getting all contacts
    public List<Contact> getAllcontacts(){

        List<Contact> contactList = new ArrayList<Contact>();
        //Select Query
        String query = "SELECT * FROM WHERE " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                Contact contact = new Contact();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_name(cursor.getString(1));
                contact.set_phone_number(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }

    // Getting Contact counts
    public int getContactsCount(){return 0;}

    // Updating single contact
    public int updateContact(Contact contact){return  0;}

    public void deleteContact(Contact contact){}

}
