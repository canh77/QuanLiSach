package com.example.quanlisach.database;

public class Data_SQL {
    public static final String INSERT_THU_THU =
            "Insert into ThuThu (maTT, hoTen, matKhau) values" +
            "('admin4', 'Trần Admin', 'admin'),"+
            "('canhvt5', 'Tân Cảnh', '123'),"+
            "('sangtvt6', 'Tấn Sang', '456'),"+
            "('admin7', 'Trần Admin', 'admin')";
//
    public static final String INSERT_THANH_VIEN =
            "insert into ThanhVien (hoTen, namSinh) values " +
                    "('Võ Tân Cảnh', '2002')," +
                    "('Võ Văn Minh', '1971')," +
                    "('Trần Thị Cúc', '1974')," +
                    "('Võ Thị Kim Nhị', '1994')," +
                    "('Trương Võ Cát Tiên', '2016')," +
                    "('Trương Thanh Qúy', '1994')";
//
    public static final String INSERT_LOAI_SACH=
            "insert into LoaiSach (tenLoai) values" +
                    "('Android')," +
                    "('Java')," +
                    "('Website')," +
                    "('Kinh Doanh')," +
                    "('C')," +
                    "('Game')";
    public static final String INSERT_SACH=
            "insert into Sach(tenSach, giaThue, maLoai) values" +
                    "('Java cơ bản', '12000', '2')," +
                    "('C# & C++', '10000', '5')," +
                    "('Kỹ năng bán hàng', '5400', '4')," +
                    "('Java nâng cao', '25000', '2')," +
                    "('Android cơ bản', '16000', '1')";
//
    public static final String INSERT_PHIEU_MUON =
            "insert into PhieuMuon(maTT, maTV, maSach, tienThue, ngay, traSach) values " +
                    "('admin','1','1','25000','2021/10/5','1')," +
                    "('admin','2','2','19000','2021/10/12','0')," +
                    "('admin','4','3','10000','2021/10/15','0')," +
                    "('admin','5','4','25000','2021/10/17','1')," +
                    "('admin','2','1','12000','2021/10/19','1')";
}
