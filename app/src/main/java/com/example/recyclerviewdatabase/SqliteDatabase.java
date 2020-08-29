package com.example.recyclerviewdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class SqliteDatabase extends SQLiteOpenHelper {



    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "cotizacion";
    private static final String TABLE_COTIZACION = "repuestos";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "repuesto";
    private static final String COLUMN_VALOR = "valor";
    private static final String COLUMN_CANT = "cantidadProd";

    SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //En este metodo creamos la tabla de nuestra base da datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUOTATION_TABLE = "CREATE TABLE "
                + TABLE_COTIZACION + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_VALOR + " INTEGER,"
                + COLUMN_CANT + " INTEGER" + ")";
        db.execSQL(CREATE_QUOTATION_TABLE);
    }

    // Este metodo se ejecutará cada vez que se cambie la versón de la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COTIZACION);
        onCreate(db);
    }

    ArrayList<Repuestos> listReplacement() {
        String sql = "select * from " + TABLE_COTIZACION;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Repuestos> storeReplacement = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String valorpro = cursor.getString(2);
                String cantipro = cursor.getString(3);

                int totalProd = 0;
                int a=Integer.parseInt(valorpro);
                int b=Integer.parseInt(cantipro);
                totalProd = a*b;

                storeReplacement.add(new Repuestos(id, name, valorpro, cantipro, totalProd));

                Log.i(name,valorpro);


            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeReplacement;
    }
    void addQuotaReplacement(Repuestos repuestos) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, repuestos.getName());
        values.put(COLUMN_VALOR, repuestos.getPhno());
        values.put(COLUMN_CANT, repuestos.getCantidadProd());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_COTIZACION, null, values);
    }
    void updateQuotaReplacement(Repuestos repuestos) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, repuestos.getName());
        values.put(COLUMN_VALOR, repuestos.getPhno());
        values.put(COLUMN_CANT, repuestos.getCantidadProd());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_COTIZACION, values, COLUMN_ID + " = ?", new String[]{String.valueOf(repuestos.getId())});
    }
    void deleteReplacement(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COTIZACION, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
