package com.dinhnt.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dinhnt.myapplication.database.KhoanThuDB;
import com.dinhnt.myapplication.model.KhoanThu;

import java.util.ArrayList;

public class KhoanThuDAO {
    KhoanThuDB khoanThuDB;
    public KhoanThuDAO(Context context){
        khoanThuDB = new KhoanThuDB(context);
    }

    public ArrayList<KhoanThu> getAll(){
        ArrayList<KhoanThu> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = khoanThuDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM KHOANTHU",null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                list.add(new KhoanThu(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public boolean add(KhoanThu khoanThu){
        SQLiteDatabase sqLiteDatabase = khoanThuDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ten", khoanThu.getTen());
        contentValues.put("tien", khoanThu.getTien());
        long check = sqLiteDatabase.insert("KHOANTHU", null, contentValues);
        if (check == -1){
            return false;
        }
        return true;
    }

    public boolean update(KhoanThu khoanThu){
        SQLiteDatabase sqLiteDatabase = khoanThuDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ten", khoanThu.getTen());
        contentValues.put("tien", khoanThu.getTien());
        long check = sqLiteDatabase.update("KHOANTHU", contentValues, "id = ?", new String[]{String.valueOf(khoanThu.getId())});
        if(check == -1){
            return  false;
        }
        return true;
    }

    public boolean delete(int id){
        SQLiteDatabase sqLiteDatabase = khoanThuDB.getWritableDatabase();
        long check = sqLiteDatabase.delete("KHOANTHU", "id = ?", new String[]{String.valueOf(id)});
        if(check == -1){
            return false;
        }
        return true;
    }
}
