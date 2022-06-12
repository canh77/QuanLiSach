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
import com.example.quanlisach.fragment.ThanhVienFragment;
import com.example.quanlisach.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienAdapter extends ArrayAdapter<ThanhVien> {
     //khai báo 1 context
     private  Context context;
     //khai báo 1 thành viên Fragment
     ThanhVienFragment fragment;
     //tạo 1 arrayList Thành vien để chứa các danh sách thành viên
     private ArrayList<ThanhVien> lists;
     //tạo các widget có editText
    TextView tvMaTV,tvTenTV,tvNamSinh;
    ImageView imgDel;

    //khai báo construtor có 3 tham số
    public ThanhVienAdapter(@NonNull Context context, ThanhVienFragment fragment, ArrayList<ThanhVien> lists) {
        super(context, 0, lists);
        this.context = context;
        this.fragment = fragment;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //khai báo 1 convertView
        View v = convertView;
        //nếu v = null thì gọi tới cái xml thanh_viên_item vừa tạo
        if (v== null){
            LayoutInflater inflater=(LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.thanh_vien_item,null);
        }

        //khai báo 1 thành viên bằng 1 cái list danh sách
        final  ThanhVien item = lists.get(position);
        //nếu item mà khác null thì sẽ trả về 1 mã thành viên
        if (item != null){
            tvMaTV = v.findViewById(R.id.tvMaTV);
            tvMaTV.setText("Mã thành viên: " + item.getMaTV());
            tvTenTV = v.findViewById(R.id.tvTenTV);
            tvTenTV.setText("Tên thành viên: " + item.getHoTen());
            tvNamSinh= v.findViewById(R.id.tvNamSinh);
            tvNamSinh.setText("Năm sinh: " + item.getNamSinh());
            imgDel = v.findViewById(R.id.imgDeleteLS);
        }
            imgDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //gọi phương thức xóa khi click vào img Delete
                    //gọi bên fragment vào adapter
                    fragment.xoa(String.valueOf(item.getMaTV()));//mãTV tự tăng
                }
            });
        //return về converview
        return v;
    }
}
