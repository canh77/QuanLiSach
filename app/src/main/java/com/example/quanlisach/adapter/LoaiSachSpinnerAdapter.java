package com.example.quanlisach.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlisach.R;
import com.example.quanlisach.model.LoaiSach;
import com.example.quanlisach.model.ThanhVien;

import java.util.ArrayList;

public class LoaiSachSpinnerAdapter extends ArrayAdapter<LoaiSach> {
    Context context;
    ArrayList<LoaiSach> lists;
    TextView tvMaLoaiSach,tvTenLoaiSach;

    public LoaiSachSpinnerAdapter(@NonNull Context context, ArrayList<LoaiSach> lists) {
        super(context, 0,lists);
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        //nếu v = null thì gọi tới cái xml thanh_viên_item vừa tạo
        if (v== null){
            LayoutInflater inflater=(LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loai_sach_item_spinner,null);
        }
        //khai báo 1 thành viên bằng 1 cái list danh sách
        final LoaiSach item = lists.get(position);
        //nếu item mà khác null thì sẽ trả về 1 mã thành viên
        if (item != null){
            tvMaLoaiSach = v.findViewById(R.id.tvMaLoaiSachSp);
            tvMaLoaiSach.setText( item.getMaLoai()+" ");
            tvTenLoaiSach = v.findViewById(R.id.tvTenLoaiSachSp);
            tvTenLoaiSach.setText( item.getTenLoai());
        }
        return  v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        //nếu v = null thì gọi tới cái xml thanh_viên_item vừa tạo
        if (v== null){
            LayoutInflater inflater=(LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loai_sach_item_spinner,null);
        }
        //khai báo 1 thành viên bằng 1 cái list danh sách
        final LoaiSach item = lists.get(position);
        //nếu item mà khác null thì sẽ trả về 1 mã thành viên
        if (item != null){
            tvMaLoaiSach = v.findViewById(R.id.tvMaLoaiSachSp);
            tvMaLoaiSach.setText( item.getMaLoai()+" ");
            tvTenLoaiSach = v.findViewById(R.id.tvTenLoaiSachSp);
            tvTenLoaiSach.setText( item.getTenLoai());
        }
        return  v;

    }
}
