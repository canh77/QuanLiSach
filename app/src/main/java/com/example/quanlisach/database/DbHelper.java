package com.example.quanlisach.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    //tạo bảng
    //biến final còn gọi là hằng số phải viết hoa các kí tự khi tạo
    private  static  final String DB_NAME ="PNLIB";
    private  static  final int DB_VERSION = 1;

    //tạo ra hàm constructor ko tham có tham số ==> tạo ra DHelper
    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Tạo bảng Thủ Thư
        String CREATE_TABLE_THU_THU =
                "create table ThuThu (" +
                        "    maTT    TEXT PRIMARY KEY," +
                        "    hoTen   TEXT NOT NULL," +
                        "    matKhau TEXT NOT NULL)";

        db.execSQL(CREATE_TABLE_THU_THU);
        //khia báo tale ThanhVien
        String CREATE_TABLE_THANH_VIEN =
                "create table ThanhVien (" +
                        "    maTV    INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "    hoTen   TEXT NOT NULL," +
                        "    namSinh TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_THANH_VIEN);

        //khia báo tale LoaiSach
        String CREATE_TABLE_LOAI_SACH =
                "create table LoaiSach (" +
                        "    maLoai  INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "    tenLoai TEXT    NOT NULL)";
        db.execSQL(CREATE_TABLE_LOAI_SACH);

        //khai báo tale Sach
        String CREATE_TABLE_SACH =
                "create table Sach (" +
                        "    maSach  INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "    tenSach TEXT    NOT NULL," +
                        "    giaThue INTEGER NOT NULL," +
                        "    maLoai  INTEGER REFERENCES LoaiSach (maLoai) )";
        db.execSQL(CREATE_TABLE_SACH);

        //khia báo tale PhieuMuon
        String CREATE_TABLE_PHIEU_MUON =
                "create table PhieuMuon (" +
                        "    maPM     INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "    maTT     TEXT    REFERENCES ThuThu (maTT)," +
                        "    maTV     TEXT    REFERENCES ThanhVien (maTV)," +
                        "    maSach   INTEGER REFERENCES Sach (maSach)," +
                        "    tienThue INTEGER NOT NULL," +
                        "    ngay     DATE    NOT NULL," +
                        "    traSach  INTEGER NOT NULL)";

        db.execSQL(CREATE_TABLE_PHIEU_MUON);

        // Insert dữ liệu mẫu
        db.execSQL(Data_SQL.INSERT_THU_THU);
        db.execSQL(Data_SQL.INSERT_THANH_VIEN);
        db.execSQL(Data_SQL.INSERT_LOAI_SACH);
        db.execSQL(Data_SQL.INSERT_SACH);
        db.execSQL(Data_SQL.INSERT_PHIEU_MUON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Drop bảng
        String dropTableLoaiThuThu = "drop table if exists ThuThu";
        db.execSQL(dropTableLoaiThuThu);
        String dropTableThanhVien = "drop table if exists ThanhVien";
        db.execSQL(dropTableThanhVien);
        String dropTableSach = "drop table if exists Sach";
        db.execSQL(dropTableSach);
        String dropTablePhieuMuon = "drop table if exists PhieuMuon";
        db.execSQL(dropTablePhieuMuon);
        String dropTableLoaiSach = "drop table if exists LoaiSach";
        db.execSQL(dropTableLoaiSach);

        //sau khi drop thì gọi oncreate
        onCreate(db);

    }
}
