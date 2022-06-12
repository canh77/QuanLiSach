package com.example.quanlisach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlisach.dao.ThuThuDAO;

public class LoginActivity extends AppCompatActivity {
    EditText edUsername,edPassword;
    Button btnLogin,btnCancel;
    CheckBox chkRememberPass;
    String strUser,strPass;
    ThuThuDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Mapping XML;
        setTitle("ĐĂNG NHẬP");
        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancel);
        chkRememberPass = findViewById(R.id.chkRememberPass);
        dao = new ThuThuDAO(this);

        // lưu u và p vào SharedPreferences
        // lưu rồi thì đọc ra đọc user, pass
        // còn chưa lưu thì gán u và p bằng rỗng
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        String user =pref.getString("USERNAME","");
        String pass= pref.getString("PASSWORD","");
        Boolean rem=  pref.getBoolean("REMEMBER",false);
        //là dữ liệu có sẵn mà khi chúng ta vừa nhập ở trên SharedPreferences
        edUsername.setText(user);
        edPassword.setText(pass);
        chkRememberPass.setChecked(rem);

        //viết sự kiện btnCancel khi click vào button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edUsername.setText("");
                edPassword.setText("");
            }
        });

        //btnLogin
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
    }

    //ghi nhớ pass và user vào ô checkbox
    public  void rememberUser(String u,String p,boolean status){//status là  đúng sai
        SharedPreferences pref =getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();//edit là cho phép sửa
        if(!status){//nếu user và pass sai thì clear form
            //phủ định thì xóa tình trạng lưu trước đó
            edit.clear();
        }else {
            //lưu data vào file
            edit.putString("USERNAME",u);
            edit.putString("PASSWORD",p);
            //status là lưu khi đúng định dạng
            edit.putBoolean("REMEMBER",status);
        }
        //lưu lại all data
        edit.commit();
    }

    //Check Login để lấy dữu liệu trên EditText
    public  void checkLogin(){
        //để lấy dữu liệu trên EditText
        strUser = edUsername.getText().toString();
        strPass  = edPassword.getText().toString();
        //kiểm tra xem người dùng có nhập đúng username và password hay chưa
        if(strUser.isEmpty() || strPass.isEmpty()){
            Toast.makeText(getApplicationContext(), "Tên đăng nhập và mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
        }else {
            //kiểm tra hàm check Login với u và p sẽ nằm trong DAO
            if (dao.checkLogin(strUser,strPass)>0){
                Toast.makeText(getApplicationContext(), "Login thành công", Toast.LENGTH_SHORT).show();
                //đăng nhập thành công thì nhớ password lun
                rememberUser(strUser,strPass,chkRememberPass.isChecked());
                //chạy qua main activity
                Intent i =new Intent(getApplicationContext(),MainActivity.class);
                //hiện username
                i.putExtra("user",strUser);
                startActivity(i);
                //finnish sẽ ko quay lại màn hình login nữa
                finish();
            }else {
                Toast.makeText(getApplicationContext(), "Tên đăng nhập và mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }
}