package com.snf.c0765774;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper mDatabase;
    EditText editTextFirstName, editTextLastName, editTextPhoneNumber, editTextAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextFirstName = findViewById(R.id.etFirstName);
        editTextLastName = findViewById(R.id.etLastName);
        editTextPhoneNumber = findViewById(R.id.etPhoneNumber);
        editTextAddress = findViewById(R.id.etAddress);

        findViewById(R.id.btnAddContact).setOnClickListener(this);
        findViewById(R.id.tvContacts).setOnClickListener(this);

  mDatabase = new DatabaseHelper(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddContact:
                addContact();
                break;
            case R.id.tvContacts:
                // start activity to another activity to see the list of employees
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void addContact() {
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        if (firstName.isEmpty()) {
            editTextFirstName.setError("name field is mandatory");
            editTextFirstName.requestFocus();
            return;
        }

        if (phoneNumber.isEmpty()) {
            editTextPhoneNumber.setError("salary field cannot be empty");
            editTextPhoneNumber.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            editTextAddress.setError("Address cannot be empty");
            editTextAddress.requestFocus();
            return;
        }

        /*
        String sql = "INSERT INTO employees (name, department, joiningdate, salary)" +
                "VALUES (?, ?, ?, ?)";
        mDatabase.execSQL(sql, new String[]{name, dept, joiningDate, salary});
        Toast.makeText(this, "Employee added", Toast.LENGTH_SHORT).show();
         */

        if (mDatabase.addContact(firstName, lastName, Double.parseDouble(phoneNumber), address))
            Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Contact not added", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        editTextFirstName.setText("");
        editTextLastName.setText("");
        editTextPhoneNumber.setText("");
        editTextAddress.setText("");
    }
}
