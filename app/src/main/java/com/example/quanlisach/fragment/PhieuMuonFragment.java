package com.example.quanlisach.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlisach.R;
import com.example.quanlisach.adapter.PhieuMuonAdapter;
import com.example.quanlisach.adapter.SachAdapter;
import com.example.quanlisach.adapter.SachSpinnerAdapter;
import com.example.quanlisach.adapter.ThanhVienSpinnerAdapter;
import com.example.quanlisach.dao.PhieuMuonDAO;
import com.example.quanlisach.dao.SachDAO;
import com.example.quanlisach.dao.ThanhVienDAO;
import com.example.quanlisach.model.PhieuMuon;
import com.example.quanlisach.model.Sach;
import com.example.quanlisach.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PhieuMuonFragment extends Fragment {
    ListView lvPhieuMuon;
    ArrayList<PhieuMuon> list;
    static PhieuMuonDAO dao;
    PhieuMuonAdapter adapter;
    PhieuMuon item;

    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaPM;
    Spinner spTV,spSach;
    TextView tvNgay,tvTienThue;
    CheckBox chkTraSach;
    Button btnSave,btnCancel;

    //khai báo để đổ dữ liệu SpThanhVien
    ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    ArrayList<ThanhVien> listThanhVien;
    ThanhVienDAO thanhVienDAO;
    ThanhVien thanhVien;
    int maThanhVien;

    //khai báo để đổ dữ liệu SpSach
    SachSpinnerAdapter sachSpinnerAdapter;
    ArrayList<Sach> listSach;
    SachDAO sachDAO;
    Sach sach;
    int maSach,tienThue;
    int positionTV,positionSach;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        lvPhieuMuon= v.findViewById(R.id.lvPhieuMuon);
        dao = new PhieuMuonDAO(getActivity());
        fab =v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(getActivity(),0);
            }
        });

        //khi nhấn giữ thành viên thì sẽ hiện lên dialog update
        // setOnItemLongClickListener
        lvPhieuMuon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                item= list.get(position);
                openDialog(getActivity(),1);//update =1
                return false;
            }
        });
        capNhatLv();
        return v;
    }


    //cập nhật lên listView
    void capNhatLv(){
        //gán dữ liệu từu SQL dô cái list gọi dao.getAll
        list = (ArrayList<PhieuMuon>) dao.getAll();
        //khai báo adapter để get dữ liệu vào
        adapter = new PhieuMuonAdapter(getActivity(),this,list);
        //xét dữ liệu lên adappter
        lvPhieuMuon.setAdapter(adapter);
    }

    //khi click vào button thì sẽ mở hộp thoại Dialog
    protected  void  openDialog(final Context context, final int type){
        //khai báo custom dialog
        dialog = new Dialog(context);
        //set convertView dẫn đến Dialog
        dialog.setContentView(R.layout.phieu_muon_dialog);

        edMaPM = dialog.findViewById(R.id.edMaPM);
        spSach = dialog.findViewById(R.id.spMaSach);
        spTV = dialog.findViewById(R.id.spMaTV);
        tvNgay =dialog.findViewById(R.id.tvNgay);
        tvTienThue=dialog.findViewById(R.id.tvTienThue);
        chkTraSach=dialog.findViewById(R.id.chkTraSach);
        btnSave= dialog.findViewById(R.id.btnSavePM);
        btnCancel= dialog.findViewById(R.id.btnCancelPM);
        edMaPM.setEnabled(false);

        //set NgayThue ,mặc định là ngày hiện tại
        tvNgay.setText("Ngày thuê: "+sdf.format(new Date()));

        ///đổ dữ liệu lên spinner ThanhViên
        thanhVienDAO = new ThanhVienDAO(context);
        //tạo ra 1 arrayList để đổ dữu liệu
        listThanhVien = new ArrayList<ThanhVien>();
        listThanhVien= (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(context,listThanhVien);
        spTV.setAdapter(thanhVienSpinnerAdapter);

        spTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                maThanhVien=listThanhVien.get(position).getMaTV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //đổ dữ liệu lên spinner Sách
        sachDAO = new SachDAO(context);
        //tạo ra 1 arrayList để đổ dữu liệu
        listSach = new ArrayList<Sach>();
        listSach= (ArrayList<Sach>) sachDAO.getAll();
        sachSpinnerAdapter = new SachSpinnerAdapter(context,listSach);
        spSach.setAdapter(sachSpinnerAdapter);

        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                maSach=listSach.get(position).getMaSach();
                tienThue=listSach.get(position).getGiaThue();
                tvTienThue.setText("Tiền thuê: "+tienThue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //edit setData len forrm
        if (type != 0) {
            edMaPM.setText(String.valueOf(item.getMaPM()));
            for (int i = 0; i < listThanhVien.size(); i++)
                if (item.getMaTV() == (listThanhVien.get(i).getMaTV())) {
                    positionTV = i;
                }
            spTV.setSelection(positionTV);

            for (int i = 0; i < listSach.size(); i++)
                if (item.getMaSach() == (listSach.get(i).getMaSach())) {
                    positionSach = i;
                }
            spSach.setSelection(positionSach);

            tvNgay.setText("Ngày thuê: "+sdf.format(item.getNgay()));
            tvTienThue.setText("Tiền thuê"+item.getTienThue());
            if (item.getTraSach()==1){
                chkTraSach.setChecked(true);
            }else {
                chkTraSach.setChecked(false);
            }

        }

        //khi click vào btnCancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //khi click vào btnSave
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lấy dữ liệu bên PM đổ vào
                item = new PhieuMuon();
                item.setMaSach(maSach);
                item.setMaTV(maThanhVien);
                item.setNgay(new Date());
                item.setTienThue(tienThue);

                //check trả sách
                if (chkTraSach.isChecked()) {
                    item.setTraSach(1);
                }else {
                    item.setTraSach(0);
                }
                if (type == 0){
                    //type = 0 là Insert
                    if (dao.insert(item)>0){
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    //type = 1  là update
                    item.setMaPM(Integer.parseInt(edMaPM.getText().toString()));
                    if (dao.update(item)>0){
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                capNhatLv();
                dialog.dismiss();
            }
        });


//        //hiện lên khi người dùng click vào dialog
        dialog.show();
    }
    //phương thức xóa khi nhấn vào img
    public  void  xoa (final String Id){
        //Sử dụng AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không ?");
        //không xóa sẽ trở về ban đầu
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //gọi functionDelete
                        dao.delete(Id);
                        //cập nhật listview
                        capNhatLv();
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton(
                "Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        //hiện dialog lên
        builder.show();
    }
}