package com.example.f_alfonsofernandez.BBDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDHelper extends SQLiteOpenHelper {


    static final String tabla1 = "COMPUESTOS";
    static final String columna1 = "nombre";
    static final String columna2 = "siglas";

    String SQLCrearTable = "CREATE TABLE IF NOT EXISTS " + tabla1 + "(" + columna2 + " VARCHAR(40) PRIMARY KEY, " + columna1 + " VARCHAR(50))";

    String SQLDeleteTable = "DROP TABLE IF EXISTS " + tabla1;

    public BDHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLCrearTable);

        //Insertando Compuestos
        sqLiteDatabase.execSQL("INSERT INTO " + tabla1 + " (nombre,siglas) VALUES ('Acido Sulfúrico','SO4H2'),('Agua','H2O'),('Carbonato Cálcico','CO3CA'),('Anhídrido Carbónico','CO2'),('Monóxido de Carbono','CO')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQLDeleteTable);
    }
}
