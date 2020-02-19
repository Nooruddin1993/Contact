package com.snf.c0765774;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private static final String TAG = "EmployeeActivity";
    //    SQLiteDatabase mDatabase;
    DatabaseHelper mDatabase;
    List<Contact> contactList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        listView = findViewById(R.id.lvContacts);
        contactList = new ArrayList<>();

//        mDatabase = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);
        mDatabase = new DatabaseHelper(this);
        loadContacts();
    }

    private void loadContacts() {

        /*
        String sql = "SELECT * FROM employees";
        Cursor cursor = mDatabase.rawQuery(sql, null);

         */
        Cursor cursor = mDatabase.getAllContacts();

        if (cursor.moveToFirst()) {
            do {
                contactList.add(new Contact(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4)
                ));
            } while (cursor.moveToNext());
            cursor.close();

            // show items in a listView
            // we use a custom adapter to show employees

            ContactAdapter employeeAdapter = new ContactAdapter(this, R.layout.list_layout_contact, contactList, mDatabase);
            listView.setAdapter(employeeAdapter);


        }
    }
}
