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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quanlisach.R;
import com.example.quanlisach.adapter.ThanhVienAdapter;
import com.example.quanlisach.dao.ThanhVienDAO;
import com.example.quanlisach.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ThanhVienFragment extends Fragment {
    ListView lvThanhVien;
    //khai báo 1 ArrayList ThanhVien để lấy dữu liệu ở bên database vào
    ArrayList<ThanhVien> list;
    //khai báo TVDAO để CRUD
    static ThanhVienDAO dao;
    ThanhVienAdapter adapter;
    ThanhVien item;
    //dialog
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaTV,edTenTV,edNamSinh;
    Button btnSave,btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        lvThanhVien = v.findViewById(R.id.lvThanhVien);
        fab = v.findViewById(R.id.fab);
        //để get dữ liệu từ database lên
        dao =new ThanhVienDAO(getActivity());
        capNhatLv();
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
        lvThanhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
        list =(ArrayList<ThanhVien>) dao.getAll();
        //khai báo adapter để get dữ liệu vào
        adapter = new ThanhVienAdapter(getActivity(),this,list);
        //xét dữ liệu lên adappter
        lvThanhVien.setAdapter(adapter);
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

    //khi click vào button thì sẽ mở hộp thoại Dialog
    protected  void  openDialog(final Context context,final int type){
        //khai báo custom dialog
        dialog = new Dialog(context);
        //set convertView dẫn đến Dialog
        dialog.setContentView(R.layout.thanh_vien_dialog);
        edMaTV = dialog.findViewById(R.id.edMaTV);
        edTenTV =dialog.findViewById(R.id.edTenTV);
        edNamSinh = dialog.findViewById(R.id.edNamSinh);
        btnSave= dialog.findViewById(R.id.btnSaveTV);
        btnCancel= dialog.findViewById(R.id.btnCancelTV);

        //kiểm tra type Insert là 0 update là 1
        edMaTV.setEnabled(false);//mã tv tự tăng ko cho ngdung nhập vào
        //kiểm tra khác 0 là update
        if (type != 0){
            //update là phải set dữu liệu vào up được
            edMaTV.setText(String.valueOf(item.getMaTV()));
            edTenTV.setText(item.getHoTen());
            edNamSinh.setText(item.getNamSinh());
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
                //làm mới tv
                item= new ThanhVien();
                //set dữu liệu vào
                item.setHoTen(edTenTV.getText().toString());
                item.setNamSinh(edNamSinh.getText().toString());
                if (validate()>0){
                    if (type == 0){
                        //type = 0 là Insert
                        if (dao.insert(item)>0){
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        //type = 1  là update
                        item.setMaTV(Integer.parseInt(edMaTV.getText().toString()));
                        if (dao.update(item)>0){
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
        //hiện lên khi người dùng click vào dialog
        dialog.show();
    }
    public  int validate(){
        int check = 1;
        if (edTenTV.getText().length()==0||edNamSinh.getText().length()==0){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return  check;
    }

}