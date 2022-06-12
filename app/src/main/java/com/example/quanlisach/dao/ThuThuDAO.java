package com.example.quanlisach.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.quanlisach.database.DbHelper;
import com.example.quanlisach.model.ThanhVien;
import com.example.quanlisach.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    private SQLiteDatabase db;

    public ThuThuDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db=dbHelper.getWritableDatabase();
    }

    //insert ThuThu
    public long insert(ThuThu obj){
        ContentValues values = new ContentValues();
        values.put("maTT",obj.getMaTT());
        values.put("hoTen",obj.getHoTen());
        values.put("matKhau",obj.getMatKhau());
        return  db.insert("ThuThu",null,values);
    }

    //update passwword theo maTT
    public int updatePass(ThuThu obj){
        ContentValues values = new ContentValues();
        values.put("hoTen",obj.getHoTen());
        values.put("matKhau",obj.getMatKhau());
        return db.update("ThuThu",values,"maTT=?",new String[]{obj.getMaTT()});
    }

    //delete theo ID
    public int delete(String id){
        return  db.delete("ThuThu","maTT=?",new String[]{id});
    }

    //get Data
    @SuppressLint("Range")
    public List<ThuThu> getData(String sql, String ... selectionArgs){
        //tạo ra 1 arrayList ThuThu
        List<ThuThu> list = new ArrayList<ThuThu>();
        //tạo ra biến Cursor để khai báo dữu liệu
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            ThuThu obj = new ThuThu();
            obj.setMaTT(c.getString(c.getColumnIndex("maTT")));
            obj.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            obj.setMatKhau(c.getString(c.getColumnIndex("matKhau")));
            Log.i("//==============",obj.toString());
            //thêm vào danh sách list
            list.add(obj);
        }
        return list;
    }

    //getAll data
    public List<ThuThu> getAll(){
        String sql = "SELECT * FROM ThuThu";
        return getData(sql);
    }
    //get theo ID
    public ThuThu getID(String id){
        String sql = "SELECT * FROM ThuThu WHERE maTT=?";
        List<ThuThu> list = getData(sql,id);
        return  list.get(0);
    }

    //check Login cho thủ thư vì chỉ có thủ thư mới đăng nhập vào hệ thống được
    public  int checkLogin(String id,String password){
        String sql = "SELECT * FROM ThuThu WHERE  maTT=? AND matKhau=?";
        List<ThuThu> list = getData(sql,id,password);
        if (list .size() == 0)
            return  -1;
        return 1;
        }

}
