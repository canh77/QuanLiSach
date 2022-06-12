package com.example.quanlisach.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.quanlisach.database.DbHelper;
import com.example.quanlisach.model.ThanhVien;
import com.example.quanlisach.model.ThuThu;
//muốn hàm DAO thực hiện phải qua DemoDB kiểm tra thử được chưa


public class DemoDB {
    private SQLiteDatabase db;
    ThanhVienDAO thanhVienDAO;
    ThuThuDAO thuThuDAO;
    static  final String TAG ="//==========";
    //tạo hàm cst ko tham số
    public DemoDB(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        thanhVienDAO = new ThanhVienDAO(context);
        thuThuDAO = new ThuThuDAO(context);
    }

    //Kiểm tra hàm ThanhVienDAO
    public  void thanhVien(){
        ThanhVien tv = new ThanhVien(1,"Võ Tân Cảnh","2002");
//        if (thanhVienDAO.insert(tv)>0){
//            Log.i(TAG,"Thêm thành viên thành công");
//        }else {
//            Log.i(TAG,"Thêm thành viên thất bại");
//        }
        //insert
        Log.i(TAG,"===========sau khi thêm==============");
        //khi gọi gàm getAll thì nó sẽ gọi qua hàm getAll bên ThanhVienDAO(get Data)
        // --> khai báo toString bên hàm model ThanhVien và thêm 1 dòng Log dưới getdata
        Log.i(TAG,"Tổng số thành viên: "+thanhVienDAO.getAll().size());

        //update
        Log.i(TAG,"==========Sau khi sửa===============");
        tv = new ThanhVien(2,"Võ Tân Cảnh 77-H1","2002");
        thanhVienDAO.update(tv);
        Log.i(TAG,"Tổng số thành viên: "+thanhVienDAO.getAll().size());

//        //delete
        thanhVienDAO.delete("4");
        Log.i(TAG,"===========sau khi xóa==============");
        Log.i(TAG,"Tổng số thành viên: "+thanhVienDAO.getAll().size());
    }

    //xử lí hàm ThuThuDAO
    public  void  thuThu(){
        ThuThu tt = new ThuThu("admin","Nguyen Admin","admin");
        thuThuDAO.insert(tt);
        Log.i(TAG,"===========sau khi thêm==============");
        Log.i(TAG,"Tổng số thành viên: "+thuThuDAO.getAll().size());

        tt = new ThuThu("admin","Trần Admin","123456");
        thuThuDAO.updatePass(tt);
        Log.i(TAG,"===========sau khi sửa==============");
        Log.i(TAG,"Tổng số thành viên: "+thuThuDAO.getAll().size());

        if(thuThuDAO.checkLogin("admin","123456")>0){
            Log.i(TAG,"Đăng nhập thành công");
        }else {
            Log.i(TAG,"Đăng nhập thất bại");
        }

    }

}
