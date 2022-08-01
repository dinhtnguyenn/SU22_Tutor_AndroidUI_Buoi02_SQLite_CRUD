package com.dinhnt.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KhoanThuDB extends SQLiteOpenHelper {

    public KhoanThuDB(Context context){
        super(context, "KHOANTHUDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE KHOANTHU(id integer primary key autoincrement, ten text, tien text)";
        sqLiteDatabase.execSQL(query);

        String ins1 = "INSERT INTO KHOANTHU VALUES(1,'tiền lương','5000'),(2, 'tiền thưởng', '1500'),(3, 'tiền lụm được', '100')";
        sqLiteDatabase.execSQL(ins1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS KHOANTHU";
        sqLiteDatabase.execSQL(query);
    }
}
