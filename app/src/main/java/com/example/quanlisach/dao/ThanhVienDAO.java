package com.example.quanlisach.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.quanlisach.database.DbHelper;
import com.example.quanlisach.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    private SQLiteDatabase db;

    //tạo ra 1 hàm construstor ko tham số

    public ThanhVienDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //viết hàm insert ThanhVien
    public  long insert(ThanhVien obj){
        ContentValues values = new ContentValues();
        values.put("hoTen",obj.getHoTen());
        values.put("namSinh",obj.getNamSinh());
        return db.insert("ThanhVien",null,values);
    }

    //update thanh viên
    public  int update(ThanhVien obj){
        ContentValues values = new ContentValues();
        values.put("hoTen",obj.getHoTen());
        values.put("namSinh",obj.getNamSinh());
        return db.update("ThanhVien",values,"maTV=?",new String[]{String.valueOf(obj.getMaTV())});
    }

    //delete thành viên theo ID
    public  int delete(String id){
        return  db.delete("ThanhVien","maTV=?", new String[]{id});
    }

    //getData hiển thị tất cả dữu liệu lên
    @SuppressLint("Range")
    public List<ThanhVien> getData(String sql, String ... selectionArgs){
        //tạo ra 1 ArrayList Thành viên
        List<ThanhVien> list = new ArrayList<>();
        //Cursor là truy vấn dữ liệu
        Cursor c =db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            ThanhVien obj = new ThanhVien();
            obj.setMaTV(Integer.parseInt(c.getString(c.getColumnIndex("maTV"))));
            obj.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            obj.setNamSinh(c.getString(c.getColumnIndex("namSinh")));
            //Insert được vào thì nó sẽ in ra dưới dạng toString
            Log.i("//============",obj.toString());
            list.add(obj);
        }
        return  list;
    }

    //getAll lấy tất cả các dữ liệu
    public  List<ThanhVien> getAll(){
        //truy vấn tới bảng sql lấy dữ liệu từ bảng thanhvien
        String sql  ="SELECT * FROM ThanhVien";
        //trả về all data trong sql
        return getData(sql);
    }

    //get data theo Id
    public  ThanhVien getID(String id){
        String sql = "SELECT * FROM ThanhVien WHERE maTV=?";
        List<ThanhVien> list = getData(sql,id);
        return list.get(0);
    }
}
