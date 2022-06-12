package com.example.quanlisach.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlisach.R;
import com.example.quanlisach.dao.SachDAO;
import com.example.quanlisach.dao.ThanhVienDAO;
import com.example.quanlisach.fragment.PhieuMuonFragment;
import com.example.quanlisach.model.PhieuMuon;
import com.example.quanlisach.model.Sach;
import com.example.quanlisach.model.ThanhVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PhieuMuonAdapter extends ArrayAdapter<PhieuMuon> {

    private  Context context;
    PhieuMuonFragment fragment;
    private ArrayList<PhieuMuon> lists;
    TextView tvMaPM,tvTenTV,tvTenSach,tvTienThue,tvNgay,tvTraSach;
    ImageView imgDel;
    SachDAO sachDAO;
    ThanhVienDAO thanhVienDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public PhieuMuonAdapter(@NonNull Context context, PhieuMuonFragment fragment, ArrayList<PhieuMuon> lists) {
        super(context, 0, lists);
        this.context=context;
        this.lists= lists;
        this.fragment= fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        //nếu v = null thì gọi tới cái xml thanh_viên_item vừa tạo
        if ( v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.phieu_muon_item, null);
        }

        //khai báo 1 thành viên bằng 1 cái list danh sách
        final PhieuMuon phieuMuon = lists.get(position);
        if (phieuMuon != null){
            tvMaPM = v.findViewById(R.id.tvMaPM);
            tvMaPM.setText("Mã phiếu: "+phieuMuon.getMaPM());

            sachDAO = new SachDAO(context);
            Sach sach =sachDAO.getID(String.valueOf(phieuMuon.getMaSach()));
            tvTenSach = v.findViewById(R.id.tvTenSach);
            tvTenSach.setText("Tên sách: "+sach.getTenSach());

            thanhVienDAO = new ThanhVienDAO(context);
            ThanhVien thanhVien = thanhVienDAO.getID(String.valueOf(phieuMuon.getMaTV()));
            tvTenTV = v.findViewById(R.id.tvTenTV);
            tvTenTV.setText("Thành viên: "+thanhVien.getHoTen());

            tvTienThue = v.findViewById(R.id.tvTienThue);
            tvTienThue.setText("Tiền thuê: "+phieuMuon.getTienThue());


            tvNgay = v.findViewById(R.id.tvNgayPM);
            tvNgay.setText("Ngày thuê: "+sdf.format(phieuMuon.getNgay()));


            tvTraSach=v.findViewById(R.id.tvTraSach);
            if (phieuMuon.getTraSach()==1){
                tvTraSach.setTextColor(Color.BLUE);
                tvTraSach.setText("Đã trả sách");
            }else {
                tvTraSach.setTextColor(Color.RED);
                tvTraSach.setText("Chưa trả sách");
            }
            imgDel = v.findViewById(R.id.imgDeletePM);
        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gọi phương thức xóa khi click vào img Delete
                //gọi bên fragment vào adapter
                fragment.xoa(String.valueOf(phieuMuon.getMaPM()));//mãTV tự tăng
            }
        });
        return  v;

    }
}
