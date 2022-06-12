package com.example.quanlisach.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlisach.R;
import com.example.quanlisach.dao.LoaiSachDAO;
import com.example.quanlisach.fragment.SachFragment;
import com.example.quanlisach.model.LoaiSach;
import com.example.quanlisach.model.Sach;
import com.example.quanlisach.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class SachAdapter extends ArrayAdapter<Sach> {
    Context context;
    SachFragment fragment;
    List<Sach> list;

    TextView tvMaSach,tvTenSach,tvGiaThue,tvLoaiSach;
    ImageView imgDel;

    LoaiSachDAO loaiSachDAO;

    public SachAdapter(@NonNull Context context, SachFragment fragment, ArrayList<Sach> list) {
        super(context, 0,list);
        this.context = context;
        this.fragment = fragment;
        this.list = (ArrayList<Sach>) list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        //nếu v = null thì gọi tới cái xml thanh_viên_item vừa tạo
        if (v== null){
            LayoutInflater inflater=(LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.sach_item,null);
        }

        //khai báo 1 thành viên bằng 1 cái list danh sách
        final Sach item = list.get(position);
        //nếu item mà khác null thì sẽ trả về 1 mã thành viên
        if (item != null){
            try {
                tvMaSach = v.findViewById(R.id.tvMaSach);
                tvMaSach.setText("Mã sách: "+item.getMaSach());

                tvTenSach =  v.findViewById(R.id.tvTenSach);
                tvTenSach.setText("Tên sách: "+item.getTenSach());

                tvGiaThue = v.findViewById(R.id.tvGiaThue);
                tvGiaThue.setText("Giá thuê: "+item.getGiaThue());

                loaiSachDAO = new LoaiSachDAO(context);
                LoaiSach loaiSach = loaiSachDAO.getID(String.valueOf(item.getMaLoai()));

                tvLoaiSach = v.findViewById(R.id.tvLoaiSach);
                tvLoaiSach.setText("Loại sách: "+loaiSach.getTenLoai());

                imgDel = v.findViewById(R.id.imgDeleteS);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gọi phương thức xóa khi click vào img Delete
                //gọi bên fragment vào adapter
                fragment.xoa(String.valueOf(item.getMaSach()));//mãTV tự tăng
            }
        });
        //return về converview
        return v;
    }


}
