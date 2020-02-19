package com.snf.c0765774;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class ContactAdapter extends ArrayAdapter {
    Context mContext;
    int layoutRes;
    List<Contact> contacts;
    //    SQLiteDatabase mDatabase;
    DatabaseHelper mDatabase;

    public ContactAdapter(Context mContext, int layoutRes, List<Contact> contacts, DatabaseHelper mDatabase) {
        super(mContext, layoutRes, contacts);
        this.mContext = mContext;
        this.layoutRes = layoutRes;
        this.contacts = contacts;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(layoutRes, null);
        TextView tvFirstName = v.findViewById(R.id.tv_firstname);
        TextView tvLastName = v.findViewById(R.id.tv_lastname);
        TextView tvPhoneNumber = v.findViewById(R.id.tv_phonenumber);
        TextView tvAddress = v.findViewById(R.id.tv_address);

        final Contact contact = contacts.get(position);
        tvFirstName.setText(contact.getFirstname());
        tvLastName.setText(contact.getLastname());
        tvPhoneNumber.setText(String.valueOf(contact.getPhonenumber()));
        tvAddress.setText(contact.getAddress());

        v.findViewById(R.id.btn_update_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact(contact);
            }
        });

        v.findViewById(R.id.btn_delete_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact(contact);
            }
        });

        return v;
    }

    private void deleteContact(final Contact contact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*
                String sql = "DELETE FROM employees WHERE id = ?";
                mDatabase.execSQL(sql, new Integer[]{employee.getId()});

                 */
                if (mDatabase.deleteContact(contact.getId()))
                    loadEmployees();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateContact(final Contact contact) {
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.update_contact, null);
        alert.setView(v);
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();

        final EditText etFirstName = v.findViewById(R.id.etFirstName);
        final EditText etLastName = v.findViewById(R.id.etLastName);
        final EditText etPhoneNumber = v.findViewById(R.id.etPhoneNumber);
        final EditText etAddress = v.findViewById(R.id.etAddress);

        etFirstName.setText(contact.getFirstname());
        etPhoneNumber.setText(String.valueOf(contact.getPhonenumber()));

        v.findViewById(R.id.btn_update_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = etFirstName.getText().toString().trim();
                String lastname = etLastName.getText().toString().trim();
                String phonenumber = etPhoneNumber.getText().toString().trim();
                String address = etAddress.getText().toString().trim();

                if (firstname.isEmpty()) {
                    etFirstName.setError("First Name is mandatory!");
                    etFirstName.requestFocus();
                    return;
                }

                if (address.isEmpty()) {
                    etAddress.setError("Salary field is mandatory!");
                    etAddress.requestFocus();
                    return;
                }

                if (mDatabase.updateContact(contact.getId(), firstname, lastname, Integer.getInteger(phonenumber), address)) {
                    Toast.makeText(mContext,"Contact Updated", Toast.LENGTH_SHORT).show();
                    loadEmployees();
                } else
                    Toast.makeText(mContext,"Contact not updated", Toast.LENGTH_SHORT).show();

//                loadEmployees();
                alertDialog.dismiss();
            }
        });
    }

    private void loadEmployees() {

        /*
        String sql = "SELECT * FROM employees";
        Cursor cursor = mDatabase.rawQuery(sql, null);

         */

        Cursor cursor = mDatabase.getAllContacts();

        contacts.clear();
        if (cursor.moveToFirst()) {

            do {
                contacts.add(new Contact(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4)
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }

        notifyDataSetChanged();
    }
}
