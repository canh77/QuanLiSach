package com.example.quanlisach.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlisach.R;
import com.example.quanlisach.dao.ThuThuDAO;
import com.example.quanlisach.model.ThuThu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class ChangePassFragment extends Fragment {
    TextInputEditText edPassOld,edPass,edRePass;
    Button btnSave,btnCancel;
    ThuThuDAO dao;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_pass, container, false);
        //Mapping fragment
        dao = new ThuThuDAO(getActivity());
        edPassOld = v.findViewById(R.id.edPassOld);
        edPass = v.findViewById(R.id.edPassChange);
        edRePass = v.findViewById(R.id.edRePassChange);
        btnSave = v.findViewById(R.id.btnSaveUserChange);
        btnCancel = v.findViewById(R.id.btnCancelUserChange);
        //xử lí sự kiện btnCancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edPassOld.setText("");
                edPass.setText("");
                edRePass.setText("");
            }
        });

        //xử lí sự kiện save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref =getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                //truyền vào 1 username
                String user  = pref.getString("USERNAME","");
                if (validate()>0){
                    //nết user đã nhạp vào form thì khai báo cho 1 ThuThu và setMatKhau chúng ta nhập vào
                    ThuThu thuThu = dao.getID(user);
                    thuThu.setMatKhau(edPass.getText().toString());
                    if (dao.updatePass(thuThu)>0){
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edPassOld.setText("");
                        edPass.setText("");
                        edRePass.setText("");
                    }else {
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return v;
    }

    //kiểm tra
    public  int validate(){
        int check =1;
        if (edPass.getText().length()==0 || edRePass.getText().length()==0 || edPassOld.getText().length()==0){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
              check = -1;
        }else {
            //đã nhập dầy đủ thông tin và đọc ra pass vừa lưu trong sharperent
            SharedPreferences pref =getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            //lấy ra pass mới nhập
            String passOld = pref.getString("PASSWORD","");
            String pass = edPass.getText().toString();
            String rePass = edRePass.getText().toString();
            //kiểm tra nếu pass vừa đổi ko dúng với pass cũ  sẽ Toast ra Sai
            if (!passOld.equals(edPassOld.getText().toString())){
                Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(rePass)){
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}