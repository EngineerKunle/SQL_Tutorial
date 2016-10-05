package com.engineerkunle.sqliteapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.engineerkunle.sqliteapplication.Utils.Contact;
import com.engineerkunle.sqliteapplication.database.DatabaseHandler;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler mDatabaseHandler = new DatabaseHandler(this);

        System.out.print("Inserting data \n");

        mDatabaseHandler.addContact(new Contact("Kunle","28280298274"));
        mDatabaseHandler.addContact(new Contact("Sam Cooke","582856968274"));
        mDatabaseHandler.addContact(new Contact("James Brown","582236968274"));
        mDatabaseHandler.addContact(new Contact("Michael Jackson","582236736364"));
        mDatabaseHandler.addContact(new Contact("supremes diana","6677736736364"));

        System.out.print("reading data");

        List<Contact> contacts = mDatabaseHandler.getAllcontacts();

        for(Contact c : contacts){
            String log = "\nID: " + c.getId() + "\n" + "Name: " + c.getName() + "\n" + "Phone: "
                    + c.getPhoneNumber();

            System.out.print(log);
          // mDatabaseHandler.close();
        }

    }

}
