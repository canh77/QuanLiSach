package com.example.quanlisach.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlisach.database.DbHelper;
import com.example.quanlisach.model.PhieuMuon;
import com.example.quanlisach.model.Sach;
import com.example.quanlisach.model.Top;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {
    private SQLiteDatabase db;
    private  Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public PhieuMuonDAO(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert data
    public  long insert(PhieuMuon obj){
        ContentValues  values = new ContentValues();
        values.put("maTT",obj.getMaTT());
        values.put("maTV",obj.getMaTV());
        values.put("maSach",obj.getMaSach());
        values.put("ngay",sdf.format(obj.getNgay()));
        values.put("tienThue",obj.getTienThue());
        values.put("traSach",obj.getTraSach());

        return  db.insert("PhieuMuon",null,values);
    }

    //update data
    public  int update(PhieuMuon obj){
        ContentValues  values = new ContentValues();
        values.put("maTT",obj.getMaTT());
        values.put("maTV",obj.getMaTV());
        values.put("maSach",obj.getMaSach());
        values.put("ngay",sdf.format(obj.getNgay()));
        values.put("tienThue",obj.getTienThue());
        values.put("traSach",obj.getTraSach());

        return  db.update("PhieuMuon",values,"maPM=?",new String[]{String.valueOf(obj.getMaPM())});
    }

    //delete
    public int delete(String id){
        return db.delete("PhieuMuon","maPM=?",new String[]{id});
    }

    //getData nhiu tham so
    @SuppressLint("Range")
    public  List<PhieuMuon> getData(String sql ,String... selectionArgs){
        List<PhieuMuon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            PhieuMuon obj = new PhieuMuon();
            obj.setMaPM(Integer.parseInt(c.getString(c.getColumnIndex("maPM"))));
            obj.setMaTT(c.getString(c.getColumnIndex("maTT")));
            obj.setMaTV(Integer.parseInt(c.getString(c.getColumnIndex("maTV"))));
            obj.setMaSach(Integer.parseInt(c.getString(c.getColumnIndex("maSach"))));
            obj.setTienThue(Integer.parseInt(c.getString(c.getColumnIndex("tienThue"))));
            try {
                obj.setNgay(sdf.parse(c.getString(c.getColumnIndex("ngay"))));
            }catch (ParseException e){
                e.printStackTrace();
            }
            obj.setTraSach(Integer.parseInt(c.getString(c.getColumnIndex("traSach"))));

            list.add(obj);
        }
        return list;
    }

    //getAll
    public List<PhieuMuon> getAll(){
        String sql = "SELECT * FROM PhieuMuon";
        return  getData(sql);
    }

    //getID
    public PhieuMuon getID(String id){
        String sql = "SELECT * FROM PhieuMuon WHERE maPM=?";
        List<PhieuMuon> list = getData(id,sql);
        return list.get(0);
    }
    //thong ke top 10 sách mượn nhìu nhất
    @SuppressLint("Range")
    public  List<Top> getTop(){
        String sqlTop = "SELECT maSach,count(maSach) as soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        List<Top> list = new ArrayList<Top>();
        SachDAO sachDAO = new SachDAO(context);
        Cursor c = db.rawQuery(sqlTop,null);

        while (c.moveToNext()){
            Top top = new Top();
            @SuppressLint("Range") Sach sach = sachDAO.getID(c.getString(c.getColumnIndex("maSach")));
            top.setTenSach(sach.getTenSach());
            top.setSoLuong(Integer.parseInt(c.getString(c.getColumnIndex("soLuong"))));
            list.add(top);
        }
        return list;
    }

    //Doanh thu
    @SuppressLint("Range")
    public  int getDoanhThu(String tuNgay , String denNgay){
        String sqlDoanhThu="SELECT SUM(tienThue) as doanhThu FROM PhieuMuon WHERE ngay BETWEEN ? AND?";
        List<Integer> list = new ArrayList<Integer>();
        Cursor c = db.rawQuery(sqlDoanhThu,new String[]{tuNgay,denNgay});
        while (c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhThu"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }


}
