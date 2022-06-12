package com.example.quanlisach;

import androidx.annotation.GravityInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlisach.dao.DemoDB;
import com.example.quanlisach.dao.PhieuMuonDAO;
import com.example.quanlisach.dao.ThuThuDAO;
import com.example.quanlisach.fragment.ChangePassFragment;
import com.example.quanlisach.fragment.DoanhThuFragment;
import com.example.quanlisach.fragment.LoaiSachFragment;
import com.example.quanlisach.fragment.PhieuMuonFragment;
import com.example.quanlisach.fragment.SachFragment;
import com.example.quanlisach.fragment.ThanhVienFragment;
import com.example.quanlisach.fragment.TopFragment;
import com.example.quanlisach.model.ThuThu;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    Toolbar toolbar;
    View mHeaderView;
    TextView edUser;
    PhieuMuonDAO dao;
    //lấy được tên đăng nhập
    ThuThuDAO thuThuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar1);

        //xét toolbar thay thế cho actionbar
        setSupportActionBar(toolbar);
        ActionBar ab =getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.menu);
        ab.setDisplayHomeAsUpEnabled(true);

        NavigationView  nv = findViewById(R.id.vnView);

        //hiển thị user trong header
        mHeaderView = nv.getHeaderView(0);
        edUser =mHeaderView.findViewById(R.id.txtUser);
        Intent i = getIntent();
        String user =i.getStringExtra("user");
        thuThuDAO = new ThuThuDAO(this);
        ThuThu thuThu= thuThuDAO.getID(user);
        String username= thuThu.getHoTen();
        edUser.setText("Welcome "+username+ " ! ");
        //hiển thị phiếu phiếu mượn làm màn hình chính khi login vào đầu tiên
        setTitle("Quản lí phiếu mượn");
        PhieuMuonFragment phieuMuonFragment = new PhieuMuonFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,phieuMuonFragment)
                .commit();

        //tạo sự kiện khi click vào actionbar
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //tạo ra menu dùng switch-case
                switch (item.getItemId()){
                    case R.id.nav_PhieuMuon:
                        setTitle("Quản lí phiếu mượn");
                        Toast.makeText(getApplicationContext(), "Quản lí phiếu mượn", Toast.LENGTH_SHORT).show();
                        PhieuMuonFragment phieuMuonFragment = new PhieuMuonFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,phieuMuonFragment)
                                .commit();
                        break;
                    case R.id.nav_LoaiSach:
                        setTitle("Quản lí Loại Sách");
                        Toast.makeText(getApplicationContext(), "Quản lí Loại Sách", Toast.LENGTH_SHORT).show();
                        LoaiSachFragment loaiSachFragment = new LoaiSachFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,loaiSachFragment)
                                .commit();
                        break;
                    case R.id.nav_Sach:
                        setTitle("Quản lí sách");
                        Toast.makeText(getApplicationContext(), "Quản lí sách", Toast.LENGTH_SHORT).show();
                        SachFragment sachFragment = new SachFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,sachFragment)
                                .commit();
                        break;
                    case R.id.nav_ThanhVien:
                        setTitle("Quản lí Thành Viên");
                        Toast.makeText(getApplicationContext(), "Quản lí Thành Viên", Toast.LENGTH_SHORT).show();
                        ThanhVienFragment thanhVienFragment = new ThanhVienFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,thanhVienFragment)
                                .commit();
                        break;
                    case R.id.sub_Top:
                        setTitle("Top 10 sách cho thuê nhìu nhất");
                        Toast.makeText(getApplicationContext(), "Top 10 sách cho thuê nhìu nhất", Toast.LENGTH_SHORT).show();
                        TopFragment topFragment = new TopFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,topFragment)
                                .commit();
                        break;
                    case R.id.sub_DoanhThu:
                        setTitle("Thống Kê Doanh Thu");
                        Toast.makeText(getApplicationContext(), "Thống Kê Doanh Thu", Toast.LENGTH_SHORT).show();
                        DoanhThuFragment doanhThuFragment = new DoanhThuFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,doanhThuFragment)
                                .commit();
                        break;
                    case R.id.sub_AddUser:
                        setTitle("Thêm người dùng");
                        Toast.makeText(getApplicationContext(), "Thêm người dùng", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.sub_Pass:
                        setTitle("Thay đổi mật khẩu");
                        Toast.makeText(getApplicationContext(), "Thay đổi mật khẩu", Toast.LENGTH_SHORT).show();
                        ChangePassFragment changePassFragment = new ChangePassFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,changePassFragment)
                                .commit();
                        break;
                    case R.id.sub_Logout:
                        setTitle("Thoát chương trình");
                        Toast.makeText(getApplicationContext(), "Thoát chương trình", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(i);
                        break;
                }
                drawer.closeDrawers();
                return false;
            }
        });
//tạo database
//        DemoDB demoDB = new DemoDB(getApplicationContext());
//        demoDB.thanhVien();
//        demoDB.thuThu();

    }
    //Thiết kế 1 itemSelect để thêm menu vào
    @Override
    public  boolean onOptionsItemSelected(@NonNull MenuItem item){
        //khai báo 1 item ID
        int  id = item.getItemId();
        if (id == android.R.id.home)
            drawer.openDrawer(GravityCompat.START);
        return  super.onOptionsItemSelected(item);
    }
}