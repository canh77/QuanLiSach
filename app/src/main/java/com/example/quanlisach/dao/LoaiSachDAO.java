package com.example.quanlisach.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlisach.database.DbHelper;
import com.example.quanlisach.model.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {
    private SQLiteDatabase db;

    public LoaiSachDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    //insert LoaiSach
    public long insert(LoaiSach obj){
        ContentValues values = new ContentValues();
        values.put("tenLoai",obj.getTenLoai());
        return db.insert("LoaiSach",null,values);
    }

    //update
    public  int update(LoaiSach obj){
        ContentValues values = new ContentValues();
        values.put("tenLoai",obj.getTenLoai());
        return  db.update("LoaiSach",values,"maLoai =?",new String[]{String.valueOf(obj.getMaLoai())});
    }

    //delete
    public int delete(String id){
        return  db.delete("LoaiSach","maLoai=?",new String[]{id});
    }

    //getData nhiu tham so
    @SuppressLint("Range")
    public List<LoaiSach> getData(String sql, String ... selectionArgs){
        List<LoaiSach> list = new ArrayList<LoaiSach>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            LoaiSach obj = new LoaiSach();
            obj.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex("maLoai"))));
            obj.setTenLoai(c.getString(c.getColumnIndex("tenLoai")));
            list.add(obj);
        }
        return  list;
    }

    //getAll data
    public List<LoaiSach> getAll(){
        String sql = "SELECT * FROM LoaiSach";
        return getData(sql);
    }
    //getData theo ID
    public LoaiSach getID(String id){
        String sql = "SELECT * FROM LoaiSach WHERE maLoai=? ";
        List<LoaiSach> list  = getData(sql,id);
        return list.get(0);
    }

}
