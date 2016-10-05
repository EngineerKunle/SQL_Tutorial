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
    private static final int DATABASE_VERSION = 1;

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
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";

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
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());

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
        //String query = "SELECT * FROM " + TABLE_CONTACTS;
        String[] allColumns = new String[] { KEY_ID,
                KEY_NAME, KEY_PH_NO };
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contact contact = contactToSave(cursor);
            contactList.add(contact);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return contactList;
    }

    private Contact contactToSave(Cursor cursor){
        Contact contact = new Contact();
        contact.setId(cursor.getLong(0));
        contact.setName(cursor.getString(1));
        contact.setPhoneNumber(cursor.getString(2));

        return contact;
    }

    // Updating single contact
    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, contact.getName());
        contentValues.put(KEY_PH_NO, contact.getPhoneNumber());

        return  db.update(TABLE_CONTACTS, contentValues, KEY_ID + " =?"
                , new String[]{String.valueOf(contact.getId())});
    }

    //delete contact
    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " =?",
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    // Getting Contact counts
    public int getContactsCount(){
        String query = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        return cursor.getCount();
    }
}
