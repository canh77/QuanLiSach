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
import com.example.quanlisach.fragment.LoaiSachFragment;
import com.example.quanlisach.model.LoaiSach;
import com.example.quanlisach.model.ThanhVien;

import java.util.ArrayList;

public class LoaiSachAdapter extends ArrayAdapter<LoaiSach> {
    private Context context;
    LoaiSachFragment fragment;
    private ArrayList<LoaiSach> lists;
    TextView tvMaLoai , tvTenLoai;
    ImageView imgDel;

    public LoaiSachAdapter(@NonNull Context context, LoaiSachFragment fragment, ArrayList<LoaiSach> lists) {
        super(context, 0,lists);
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
            v = inflater.inflate(R.layout.loai_sach_item,null);
        }

        //khai báo 1 thành viên bằng 1 cái list danh sách
        final LoaiSach item = lists.get(position);
        //nếu item mà khác null thì sẽ trả về 1 mã thành viên
        if (item != null){
            tvMaLoai = v.findViewById(R.id.tvMaLoai);
            tvMaLoai.setText("Mã loại sách: " + item.getMaLoai());
            tvTenLoai = v.findViewById(R.id.tvTenLoai);
            tvTenLoai.setText("Tên sách: " + item.getTenLoai());
            imgDel = v.findViewById(R.id.imgDeleteLS);
        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gọi phương thức xóa khi click vào img Delete
                //gọi bên fragment vào adapter
                fragment.xoa(String.valueOf(item.getMaLoai()));//mãLoại tự tăng
            }
        });
        //return về converview
        return v;
    }
}
