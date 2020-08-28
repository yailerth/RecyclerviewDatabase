package com.example.recyclerviewdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class SqliteDatabase extends SQLiteOpenHelper {



    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "repuestos";
    private static final String TABLE_CONTACTS = "repuestos";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "contactName";
    private static final String COLUMN_NO = "phoneNumber";
    private static final String COLUMN_CANT = "cantidadProd";

    SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //En este metodo creamos la tabla de nuestra base da datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE "
                + TABLE_CONTACTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_NO + " INTEGER,"
                + COLUMN_CANT + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Este metodo se ejecutará cada vez que se cambie la versón de la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }


    ArrayList<Contacts> listContacts() {
        String sql = "select * from " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Contacts> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String phno = cursor.getString(2);
                String cantipro = cursor.getString(3);
                storeContacts.add(new Contacts(id, name, phno, cantipro));

                Log.i(name,phno);


            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }
    void addContacts(Contacts contacts) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contacts.getName());
        values.put(COLUMN_NO, contacts.getPhno());
        values.put(COLUMN_CANT, contacts.getCantidadProd());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CONTACTS, null, values);
    }
    void updateContacts(Contacts contacts) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contacts.getName());
        values.put(COLUMN_NO, contacts.getPhno());
        values.put(COLUMN_CANT, contacts.getCantidadProd());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_CONTACTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(contacts.getId())});
    }
    void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
