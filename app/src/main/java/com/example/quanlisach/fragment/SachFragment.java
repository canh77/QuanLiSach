package com.example.quanlisach.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quanlisach.R;
import com.example.quanlisach.adapter.LoaiSachSpinnerAdapter;
import com.example.quanlisach.adapter.SachAdapter;
import com.example.quanlisach.adapter.ThanhVienAdapter;
import com.example.quanlisach.dao.LoaiSachDAO;
import com.example.quanlisach.dao.SachDAO;
import com.example.quanlisach.model.LoaiSach;
import com.example.quanlisach.model.Sach;
import com.example.quanlisach.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class SachFragment extends Fragment {
    ListView lvSach;
    SachDAO sachDAO;
    //khai báo TVDAO để CRUD
     SachAdapter adapter;
     Sach item;
    //khai báo 1 ArrayList ThanhVien để lấy dữu liệu ở bên database vào
    ArrayList<Sach> list;

    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaSach,edTenSach,edGiaThue;
    Spinner spinner;
    Button btnSave,btnCancel;

    LoaiSachSpinnerAdapter spinnerAdapter;
    ArrayList<LoaiSach> listLoaiSach;
    LoaiSachDAO  loaiSachDAO;
    LoaiSach loaiSach;
    int maLoaiSach,position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sach, container, false);
        lvSach = v.findViewById(R.id.lvSach);
        sachDAO= new SachDAO(getActivity());

        capNhatLv();
        fab= v.findViewById(R.id.fab);
        //khi click vào btn thì sẽ show dialog lên
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 0  là insert TV vào
                openDialog(getActivity(),0);
            }
        });
        //khi nhấn giữ thành viên thì sẽ hiện lên dialog update
        // setOnItemLongClickListener
        lvSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                item =list.get(position);
                //1 là update
                openDialog(getActivity(),1);
                return false;
            }
        });
        return  v;
    }

    //cập nhật lên listView
    void capNhatLv(){
        //gán dữ liệu từu SQL dô cái list gọi dao.getAll
        list = (ArrayList<Sach>) sachDAO.getAll();
        //khai báo adapter để get dữ liệu vào
        adapter = new SachAdapter(getActivity(),this,list);
        //xét dữ liệu lên adappter
        lvSach.setAdapter(adapter);
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
                        sachDAO.delete(Id);
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

    //khi click vào button thì sẽ mở hộp thoại Dialog
    protected  void  openDialog(final Context context, final int type){
        //khai báo custom dialog
        dialog = new Dialog(context);
        //set convertView dẫn đến Dialog
        dialog.setContentView(R.layout.sach_dialog);
        edMaSach = dialog.findViewById(R.id.edMaSach);
        edTenSach =dialog.findViewById(R.id.edTenSach);
        edGiaThue = dialog.findViewById(R.id.edGiaThue);
        spinner= dialog.findViewById(R.id.spLoaiSach);
        btnSave= dialog.findViewById(R.id.btnSaveSach);
        btnCancel= dialog.findViewById(R.id.btnCancelSach);

        listLoaiSach = new ArrayList<LoaiSach>();
        loaiSachDAO = new LoaiSachDAO(context);
        listLoaiSach=(ArrayList<LoaiSach>)loaiSachDAO.getAll();

        spinnerAdapter = new LoaiSachSpinnerAdapter(context,listLoaiSach);
        spinner.setAdapter(spinnerAdapter);

        //lấy mã loại sách
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                maLoaiSach = listLoaiSach.get(position).getMaLoai();
                Toast.makeText(context, "Chọn"+listLoaiSach.get(position).getTenLoai(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //kiểm tra type Insert là 0 update là 1
        edMaSach.setEnabled(false);//mã tv tự tăng ko cho ngdung nhập vào
        //kiểm tra khác 0 là update
        if (type != 0){
            //update là phải set dữu liệu vào up được
            edMaSach.setText(String.valueOf(item.getMaSach()));
            edTenSach.setText(item.getTenSach());
            edGiaThue.setText(String.valueOf(item.getGiaThue()));
            for (int i = 0; i < listLoaiSach.size(); i++)
                if (item.getMaLoai() ==     (listLoaiSach.get(i).getMaLoai())){
                    position = i;
                }
            Log.i("demo","posSach"+position);
            spinner.setSelection(position);

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
                item= new Sach();
                item.setTenSach(edTenSach.getText().toString());
                item.setGiaThue(Integer.parseInt(edGiaThue.getText().toString()));
                item.setMaLoai(maLoaiSach);
                if (validate()>0){
                    if (type == 0){
                        //type = 0 là Insert
                        if (sachDAO.insert(item)>0){
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        //type = 1  là update
                        item.setMaSach(Integer.parseInt(edMaSach.getText().toString()));
                        if (sachDAO.update(item)>0){
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //cập nhật và tắt đi dialog
                    capNhatLv();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    public  int validate(){
        int check = 1;
        if (edTenSach.getText().length()==0 || edGiaThue.getText().length()==0){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return  check;
    }


}