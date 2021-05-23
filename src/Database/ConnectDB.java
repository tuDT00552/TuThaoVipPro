/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Model.HoatDongCT_XH;
import Model.HoatDongDoan_Hoi;
import Model.KhenThuong;
import Model.KyLuat;
import Model.MienGiam;
import Model.TTCaNhan;
import Model.TTGiaDinh;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author TONG LE TU VAN
 */
public class ConnectDB {

    Connection con = null;
    Statement sta = null;
    ResultSet res = null;
    String DB_Driver = "org.apache.derby.jdbc.ClientDriver";
    String url = "jdbc:derby://localhost:1527/N13_QLSinhVien";
    String user = "N13";
    String pass = "123";
    //ket noi den database

    public void getConnect() {
        try {
            String url = "jdbc:derby://localhost:1527/N13_QLSinhVien";
            String user = "N13";
            String pass = "123";
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = (Connection) DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Khong the ket noi voi database \n" + e);
            e.printStackTrace();
        }
    }

    protected Statement getStatement() throws Exception {
        if (this.sta == null || this.sta.isClosed()) {
            //khoi tao statement moi
            this.sta = this.con.createStatement();
        }
        return this.sta;
    }

    public ResultSet executeQuery(String qr) throws Exception {
        try {
            //thuc thi cau lenh
            this.res = getStatement().executeQuery(qr);
        } catch (Exception e) {
            throw new Exception("Cau lenh Query khong duoc thuc thi");
        }
        return this.res;
        //van mo ket noi de lay thong tin
    }

    public int executeUpdate(String qr) throws Exception {
        int ketqua = 0;
        try {
            // thuc thi cau lenh
            ketqua = getStatement().executeUpdate(qr);
        } catch (Exception e) {
            throw new Exception("Loi queryUpdate khong duoc thuc hien");
        } finally {
            this.con.close();
        }
        return ketqua;
    }

    public ResultSet GetDataQLSV(String jtable) throws SQLException {
        ResultSet kq = null;
        Statement st = con.createStatement();
        kq = st.executeQuery("select * from ACCOUNT");//ok
        return kq;
    }

//----------------------------
//TTCaNhan
    public ArrayList<TTCaNhan> GetDataTTCaNhan() throws SQLException {
        getConnect();
        ArrayList<TTCaNhan> list = new ArrayList<>();
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("select * from TTCANHAN");
        while (result.next()) {
            String masv = result.getString("MASV");
            String tensv = result.getString("TENSV");
            String khoa = result.getString("KHOA");
            String gioitinh = result.getString("GIOITINH");
            String nganh = result.getString("NGANH");
            String lop = result.getString("LOP");
            String ngaysinh = result.getString("NGAYSINH");
            String quequan = result.getString("QUEQUAN");
            String cmnd = result.getString("CMND");
            String sdt = result.getString("SDT");
            String email = result.getString("EMAIL");
            TTCaNhan ttcn = new TTCaNhan(masv, tensv, khoa, gioitinh, nganh, lop, ngaysinh, quequan, cmnd, sdt, email);
            list.add(ttcn);
        }
        return list;
    }

    public void addTTCaNhan(TTCaNhan tCaNhan) throws Exception {
        String them = "insert into TTCANHAN(masv, tensv, khoa, gioitinh, nganh, lop, ngaysinh, quequan, cmnd, sdt, email)"
                + " values(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ttcn = con.prepareStatement(them);
        ttcn.setString(1, tCaNhan.getMaSV());
        ttcn.setString(2, tCaNhan.getTenSV());
        ttcn.setString(3, tCaNhan.getKhoa());
        ttcn.setString(4, tCaNhan.getGioiTinh());
        ttcn.setString(5, tCaNhan.getNganh());
        ttcn.setString(6, tCaNhan.getLop());
        ttcn.setString(7, tCaNhan.getNgaySinh());
        ttcn.setString(8, tCaNhan.getQueQuan());
        ttcn.setString(9, tCaNhan.getCMND());
        ttcn.setString(10, tCaNhan.getSDT());
        ttcn.setString(11, tCaNhan.getEmail());
        ttcn.executeUpdate();
    }

    public void updateTTCaNhan(TTCaNhan tCaNhan) throws Exception {
        String sua = "update TTCANHAN set tensv=?,khoa=?,gioitinh=?,nganh=?,lop=?,ngaysinh=?,quequan=?,cmnd=?,sdt=?,email=? WHERE masv = ?";
        PreparedStatement ttcn = con.prepareStatement(sua);
        ttcn.setString(1, tCaNhan.getTenSV());
        ttcn.setString(2, tCaNhan.getKhoa());
        ttcn.setString(3, tCaNhan.getGioiTinh());
        ttcn.setString(4, tCaNhan.getNganh());
        ttcn.setString(5, tCaNhan.getLop());
        ttcn.setString(6, tCaNhan.getNgaySinh());
        ttcn.setString(7, tCaNhan.getQueQuan());
        ttcn.setString(8, tCaNhan.getCMND());
        ttcn.setString(9, tCaNhan.getSDT());
        ttcn.setString(10, tCaNhan.getEmail());
        ttcn.setString(11, tCaNhan.getMaSV());
        ttcn.executeUpdate();
    }

    public void deleteTTCaNhan(String MaSV) throws Exception {
        String xoa = ("delete from TTCANHAN where MaSV =?");
        PreparedStatement ttcn = con.prepareStatement(xoa);
        ttcn.setString(1, MaSV);
        ttcn.executeUpdate();
    }

    public ArrayList<TTCaNhan> SearchTTCaNhan(String MaSV) throws SQLException {
        getConnect();
        ArrayList<TTCaNhan> list = new ArrayList<>();
        if (MaSV.isEmpty()) {
            list = GetDataTTCaNhan();
        } else {
            Statement st = con.createStatement();
            ResultSet result = st.executeQuery("select * from TTCANHAN where MaSV=" + "'" + MaSV + "'" + "");
            while (result.next()) {
                String masv = result.getString("MASV");
                String tensv = result.getString("TENSV");
                String khoa = result.getString("KHOA");
                String gioitinh = result.getString("GIOITINH");
                String nganh = result.getString("NGANH");
                String lop = result.getString("LOP");
                String ngaysinh = result.getString("NGAYSINH");
                String quequan = result.getString("QUEQUAN");
                String cmnd = result.getString("CMND");
                String sdt = result.getString("SDT");
                String email = result.getString("EMAIL");
                TTCaNhan tt = new TTCaNhan(masv, tensv, khoa, gioitinh, nganh, lop, ngaysinh, quequan, cmnd, sdt, email);
                list.add(tt);
            }
        }
        return list;
    }

    //check mã sinh viên trùng
    public int checkExistsTTCaNhan(String MaSV) throws SQLException {
        int count = 0;
        String query = ("select count(*) from TTCANHAN where MaSV = ?");
        PreparedStatement state = con.prepareStatement(query);
        state.setString(1, MaSV);
        ResultSet rs = state.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        } else {
            System.out.println("error: could not get the record counts");
        }
        return count;
    }

//----------------------------
//TTGiaDinh
    public ArrayList<TTGiaDinh> GetDataTTGiaDinh() throws SQLException {
        getConnect();
        ArrayList<TTGiaDinh> list = new ArrayList<>();
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("select * from TTGIADINH");
        while (result.next()) {
            String masv = result.getString("MASV");
            String tensv = result.getString("TENSV");
            String hotenbo = result.getString("HOTENBO");
            String hotenme = result.getString("HOTENME");
            String SDTlienhe = result.getString("SDTLIENHE");
            String anhemtrai = result.getString("ANHEMTRAI");
            String chiemgai = result.getString("CHIEMGAI");
            String doituongcs = result.getString("DOITUONGCHINHSACH");
            String khuvuco = result.getString("KHUVUCO");
            TTGiaDinh ttgd = new TTGiaDinh(masv, tensv, hotenbo, hotenme, anhemtrai, chiemgai, doituongcs, khuvuco, SDTlienhe);
            list.add(ttgd);
        }
        return list;
    }

    public void addTTGiaDinh(TTGiaDinh tTGiaDinh) throws Exception {
        String add = "insert into TTGiaDinh(masv, tensv, hotenbo, hotenme, anhemtrai, chiemgai, doituongchinhsach, khuvuco, SDTlienhe)"
                + "values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ttgd = con.prepareStatement(add);
        ttgd.setString(1, tTGiaDinh.getMaSV());
        ttgd.setString(2, tTGiaDinh.getTenSV());
        ttgd.setString(3, tTGiaDinh.getHoTenBo());
        ttgd.setString(4, tTGiaDinh.getHoTenMe());
        ttgd.setString(5, tTGiaDinh.getAnhEmTrai());
        ttgd.setString(6, tTGiaDinh.getChiEmGai());
        ttgd.setString(7, tTGiaDinh.getDoiTuongChinhSach());
        ttgd.setString(8, tTGiaDinh.getKhuVucO());
        ttgd.setString(9, tTGiaDinh.getSDTLienHe());
        ttgd.executeUpdate();
    }

    public void updateTTGiaDinh(TTGiaDinh tTGiaDinh) throws Exception {
        String update = "update TTGIADINH set tensv=? ,hotenbo=?, hotenme=?, anhemtrai=?, chiemgai=?, doituongcs=?, khuvuco=?, SDTlienhe=? WHERE masv=?";
        PreparedStatement ttgd = con.prepareStatement(update);
        ttgd.setString(1, tTGiaDinh.getTenSV());
        ttgd.setString(2, tTGiaDinh.getHoTenBo());
        ttgd.setString(3, tTGiaDinh.getHoTenMe());
        ttgd.setString(4, tTGiaDinh.getAnhEmTrai());
        ttgd.setString(5, tTGiaDinh.getChiEmGai());
        ttgd.setString(6, tTGiaDinh.getDoiTuongChinhSach());
        ttgd.setString(7, tTGiaDinh.getKhuVucO());
        ttgd.setString(8, tTGiaDinh.getSDTLienHe());
        ttgd.setString(9, tTGiaDinh.getMaSV());
        ttgd.executeUpdate();
    }

    public void deleteTTGiaDinh(String MaSV) throws Exception {
        String delete = ("delete from TTGIADINH where MaSV=?");
        PreparedStatement ttgd = con.prepareStatement(delete);
        ttgd.setString(1, MaSV);
        ttgd.executeUpdate();
    }

    public ArrayList<TTGiaDinh> SearchTTGiaDinh(String MaSV) throws SQLException {
        getConnect();
        ArrayList<TTGiaDinh> list = new ArrayList<>();
        if (MaSV.isEmpty()) {
            list = GetDataTTGiaDinh();
        } else {
            Statement st = con.createStatement();
            ResultSet result = st.executeQuery("select * from TTGIADINH where MaSV=" + "'" + MaSV + "'" + "");
            while (result.next()) {
                String masv = result.getString("MASV");
                String tensv = result.getString("TENSV");
                String hotenbo = result.getString("HOTENBO");
                String hotenme = result.getString("HOTENME");
                String SDTlienhe = result.getString("SDTLIENHE");
                String anhemtrai = result.getString("ANHEMTRAI");
                String chiemgai = result.getString("CHIEMGAI");
                String doituongcs = result.getString("DOITUONGCHINHSACH");
                String khuvuco = result.getString("KHUVUCO");
                TTGiaDinh ttgd = new TTGiaDinh(masv, tensv, hotenbo, hotenme, anhemtrai, chiemgai, doituongcs, khuvuco, SDTlienhe);
                list.add(ttgd);
            }
        }
        return list;
    }
//----------------------------
//KhenThuong

    public ArrayList<KhenThuong> GetDataKhenThuong() throws SQLException {
        getConnect();
        ArrayList<KhenThuong> list = new ArrayList<>();
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("select * from KHENTHUONG");
        while (result.next()) {
            String masv = result.getString("MASV");
            String tensv = result.getString("TENSV");
            String ndkhenthuong = result.getString("NDKHENTHUONG");
            float diemcong = result.getFloat("DIEMCONG");
            KhenThuong kt = new KhenThuong(masv, tensv, ndkhenthuong, diemcong);
            list.add(kt);
        }
        return list;
    }

    public void addKhenThuong(KhenThuong khenthuong) throws Exception {
        String them = "insert into KHENTHUONG(masv, tensv, ndkhenthuong, diemcong)"
                + " values(?,?,?,?)";
        PreparedStatement kt = con.prepareStatement(them);
        kt.setString(1, khenthuong.getMaSV());
        kt.setString(2, khenthuong.getTenSV());
        kt.setString(3, khenthuong.getNDKhenThuong());
        kt.setFloat(4, khenthuong.getDiemCong());
        kt.executeUpdate();
    }

    public void updateKhenThuong(KhenThuong khenthuong) throws Exception {
        String sua = "update KHENTHUONG set TENSV=?,NDKHENTHUONG=?,DIEMCONG=? where MASV = ?";
        PreparedStatement kt = con.prepareStatement(sua);
        kt.setString(1, khenthuong.getTenSV());
        kt.setString(2, khenthuong.getNDKhenThuong());
        kt.setFloat(3, khenthuong.getDiemCong());
        kt.setString(4, khenthuong.getMaSV());
        kt.executeUpdate();
    }

    public void xoaKhenThuong(String MaSV) throws SQLException {
        String sql = "DELETE FROM KHENTHUONG WHERE MaSV=?";

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, MaSV);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            JOptionPane.showMessageDialog(null, "Xoá thành công.");
        }
    }

    public ArrayList<KhenThuong> timKhenThuong(String MaSV) throws SQLException {
        ArrayList<KhenThuong> list = new ArrayList<>();
        getConnect();
        if (MaSV.isEmpty()) {
            list = GetDataKhenThuong();
        } else {
            Statement st = con.createStatement();
            ResultSet result = st.executeQuery("select * from KHENTHUONG where MaSV=" + "'" + MaSV + "'" + "");
            while (result.next()) {
                String masv = result.getString("MASV");
                String tensv = result.getString("TENSV");
                String ndkhenthuong = result.getString("NDKHENTHUONG");
                float diemcong = result.getFloat("DIEMCONG");
                KhenThuong kt = new KhenThuong(masv, tensv, ndkhenthuong, diemcong);
                list.add(kt);
            }
        }
        return list;
    }
//----------------------------
//KyLuat

    public ArrayList<KyLuat> GetDataKyLuat() throws SQLException {
        getConnect();
        ArrayList<KyLuat> list = new ArrayList<>();
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("select * from KYLUAT");
        while (result.next()) {
            String masv = result.getString("MASV");
            String tensv = result.getString("TENSV");
            String ndkyluat = result.getString("NDKYLUAT");
            float diemtru = result.getFloat("DIEMTRU");
            KyLuat kl = new KyLuat(masv, tensv, ndkyluat, diemtru);
            list.add(kl);
        }
        return list;
    }

    public void addKyLuat(KyLuat kyluat) throws Exception {
        String add = "insert into KYLUAT(masv, tensv, ndkyluat, diemtru)"
                + " values(?,?,?,?)";
        PreparedStatement kt = con.prepareStatement(add);
        kt.setString(1, kyluat.getMaSV());
        kt.setString(2, kyluat.getTenSV());
        kt.setString(3, kyluat.getNDKyLuat());
        kt.setFloat(4, kyluat.getDiemTru());
        kt.executeUpdate();
    }

    public void updateKyLuat(KyLuat kyluat) throws Exception {
        String update = "update KYLUAT set TENSV=?,NDKYLUAT=?,DIEMTRU=? where MASV = ?";
        PreparedStatement kt = con.prepareStatement(update);
        kt.setString(1, kyluat.getTenSV());
        kt.setString(2, kyluat.getNDKyLuat());
        kt.setFloat(3, kyluat.getDiemTru());
        kt.setString(4, kyluat.getMaSV());
        kt.executeUpdate();
    }

    public void deleteKyLuat(String MaSV) throws SQLException {
        String delete = ("delete from KYLUAT where MaSV=?");
        PreparedStatement kl = con.prepareStatement(delete);
        kl.setString(1, MaSV);
        kl.executeUpdate();
    }

    public ArrayList<KyLuat> searchKyLuat(String MaSV) throws SQLException {
        getConnect();
        ArrayList<KyLuat> list = new ArrayList<>();
        if (MaSV.isEmpty()) {
            list = GetDataKyLuat();
        } else {
            Statement st = con.createStatement();
            ResultSet result = st.executeQuery("select * from KYLUAT where MaSV=" + "'" + MaSV + "'" + "");
            while (result.next()) {
                String masv = result.getString("MASV");
                String tensv = result.getString("TENSV");
                String ndkyluat = result.getString("NDKYLUAT");
                float diemtru = result.getFloat("DIEMTRU");
                KyLuat kl = new KyLuat(masv, tensv, ndkyluat, diemtru);
                list.add(kl);
            }
        }
        return list;
    }
//----------------------------
//HoatDongCT_XH

    public ArrayList<HoatDongCT_XH> GetDataHoatDongCT_XH() throws SQLException {
        getConnect();
        ArrayList<HoatDongCT_XH> list = new ArrayList<>();
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("select * from HoatDongCT_XH");
        while (result.next()) {
            String masv = result.getString("MASV");
            String tensv = result.getString("TENSV");
            String ndhoatdong = result.getString("NDHOATDONG");
            float diemcong = result.getFloat("DIEMCONG");
            HoatDongCT_XH ctxh = new HoatDongCT_XH(masv, tensv, ndhoatdong, diemcong);
            list.add(ctxh);
        }
        return list;
    }

    public void addHoatDongCT_XH(HoatDongCT_XH hoatDongCT_XH) throws Exception {
        String add = "insert into HoatDongCT_XH(masv, tensv, ndhoatdong, diemcong)"
                + " values(?,?,?,?)";
        PreparedStatement ctxh = con.prepareStatement(add);
        ctxh.setString(1, hoatDongCT_XH.getMaSV());
        ctxh.setString(2, hoatDongCT_XH.getTenSV());
        ctxh.setString(3, hoatDongCT_XH.getNDHoatDong());
        ctxh.setFloat(4, hoatDongCT_XH.getDiemCong());
        ctxh.executeUpdate();
    }

    public void updateHoatDongCT_XH(HoatDongCT_XH hoatDongCT_XH) throws Exception {
        String update = "update HOATDONGCT_XH set TENSV=?,NDHOATDONG=?,DIEMCONG=? where MASV = ?";
        PreparedStatement ctxh = con.prepareStatement(update);
        ctxh.setString(1, hoatDongCT_XH.getTenSV());
        ctxh.setString(2, hoatDongCT_XH.getNDHoatDong());
        ctxh.setFloat(3, hoatDongCT_XH.getDiemCong());
        ctxh.setString(4, hoatDongCT_XH.getMaSV());
        ctxh.executeUpdate();
    }

    public void deleteHoatDongCT_XH(String MaSV) throws SQLException {
        String delete = ("delete from HOATDONGCT_XH where MaSV=?");
        PreparedStatement ctxh = con.prepareStatement(delete);
        ctxh.setString(1, MaSV);
        ctxh.executeUpdate();
    }

    public ArrayList<HoatDongCT_XH> searchHoatDongCT_XH(String MaSV) throws SQLException {
        getConnect();
        ArrayList<HoatDongCT_XH> list = new ArrayList<>();
        if (MaSV.isEmpty()) {
            list = GetDataHoatDongCT_XH();
        } else {
            Statement st = con.createStatement();
            ResultSet result = st.executeQuery("select * from HOATDONGCT_XH where MaSV=" + "'" + MaSV + "'" + "");
            while (result.next()) {
                String masv = result.getString("MASV");
                String tensv = result.getString("TENSV");
                String ndhoatdong = result.getString("NDHOATDONG");
                float diemcong = result.getFloat("DIEMCONG");
                HoatDongCT_XH ctxh = new HoatDongCT_XH(masv, tensv, ndhoatdong, diemcong);
                list.add(ctxh);
            }
        }
        return list;
    }
//----------------------------
//HoatDongDoan_Hoi

    public ArrayList<HoatDongDoan_Hoi> GetDataHoatDongDoan_Hoi() throws SQLException {
        getConnect();
        ArrayList<HoatDongDoan_Hoi> list = new ArrayList<>();
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("select * from HoatDongDoan_Hoi");
        while (result.next()) {
            String masv = result.getString("MASV");
            String tensv = result.getString("TENSV");
            String ndhoatdong = result.getString("NDHOATDONG");
            float diemcong = result.getFloat("DIEMCONG");
            HoatDongDoan_Hoi dh = new HoatDongDoan_Hoi(masv, tensv, ndhoatdong, diemcong);
            list.add(dh);
        }
        return list;
    }

    public void addHoatDongDoan_Hoi(HoatDongDoan_Hoi hoatDongDoan_Hoi) throws Exception {
        String add = "insert into HoatDongDoan_Hoi(masv, tensv, ndhoatdong, diemcong)"
                + " values(?,?,?,?)";
        PreparedStatement dh = con.prepareStatement(add);
        dh.setString(1, hoatDongDoan_Hoi.getMaSV());
        dh.setString(2, hoatDongDoan_Hoi.getTenSV());
        dh.setString(3, hoatDongDoan_Hoi.getNDHoatDong());
        dh.setFloat(4, hoatDongDoan_Hoi.getDiemCong());
        dh.executeUpdate();
    }

    public void updateHoatDongDoan_Hoi(HoatDongDoan_Hoi hoatDongDoan_Hoi) throws Exception {
        String update = "update HoatDongDoan_Hoi set TENSV=?,NDHOATDONG=?,DIEMCONG=? where MASV = ?";
        PreparedStatement dh = con.prepareStatement(update);
        dh.setString(1, hoatDongDoan_Hoi.getTenSV());
        dh.setString(2, hoatDongDoan_Hoi.getNDHoatDong());
        dh.setFloat(3, hoatDongDoan_Hoi.getDiemCong());
        dh.setString(4, hoatDongDoan_Hoi.getMaSV());
        dh.executeUpdate();
    }

    public void deleteHoatDongDoan_Hoi(String MaSV) throws SQLException {
        String delete = ("delete from HoatDongDoan_Hoi where MaSV=?");
        PreparedStatement dh = con.prepareStatement(delete);
        dh.setString(1, MaSV);
        dh.executeUpdate();
    }

    public ArrayList<HoatDongDoan_Hoi> searchHoatDongDoan_Hoi(String MaSV) throws SQLException {
        getConnect();
        ArrayList<HoatDongDoan_Hoi> list = new ArrayList<>();
        if (MaSV.isEmpty()) {
            list = GetDataHoatDongDoan_Hoi();
        } else {
            Statement st = con.createStatement();
            ResultSet result = st.executeQuery("select * from HoatDongDoan_Hoi where MaSV=" + "'" + MaSV + "'" + "");
            while (result.next()) {
                String masv = result.getString("MASV");
                String tensv = result.getString("TENSV");
                String ndhoatdong = result.getString("NDHOATDONG");
                float diemcong = result.getFloat("DIEMCONG");
                HoatDongDoan_Hoi dh = new HoatDongDoan_Hoi(masv, tensv, ndhoatdong, diemcong);
                list.add(dh);
            }
        }
        return list;
    }
// Mien Giam

    public ArrayList<MienGiam> GetDataMienGiam() throws SQLException {
        getConnect();
        ArrayList<MienGiam> list = new ArrayList<>();
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("SELECT A.MASV,\n"
                + " A.TENSV,\n"
                + " B.DOITUONGCHINHSACH,\n"
                + " B.KHUVUCO,\n"
                + "CASE \n"
                + "WHEN B.DOITUONGCHINHSACH = 'Mồ côi' AND B.KHUVUCO = 'Vùng núi-vùng sâu vùng xa' THEN '5.000.000 VND'\n"
                + "WHEN B.DOITUONGCHINHSACH = 'Khuyết tật' AND B.KHUVUCO = 'Vùng núi-vùng sâu vùng xa' THEN '4.500.000 VND'\n"
                + "WHEN B.DOITUONGCHINHSACH = 'Khuyết tật' THEN '1.500.000 VND'\n"
                + "WHEN B.DOITUONGCHINHSACH = 'Mồ côi' THEN '2.000.000 VND'\n"
                + "WHEN B.KHUVUCO = 'Vùng núi-vùng sâu vùng xa' THEN '2.500.000 VND'\n"
                + "ELSE '0'\n"
                + "END AS MUCMIENGIAM\n"
                + " FROM N13.TTCANHAN A INNER JOIN N13.TTGIADINH B ON A.MASV = B.MASV");
        while (result.next()) {
            String masv = result.getString("MASV");
            String tensv = result.getString("TENSV");
            String doituongchindhsach = result.getString("DOITUONGCHINHSACH");
            String khuvuco = result.getString("KHUVUCO");
            String mucmiengiam = result.getString("MUCMIENGIAM");
            MienGiam mg = new MienGiam(masv, tensv, doituongchindhsach, khuvuco, mucmiengiam);
            list.add(mg);
        }
        return list;
    }

    public ArrayList<MienGiam> searchMienGiam(String Khoa, String Nganh, String Lop) throws SQLException {
        getConnect();
        ArrayList<MienGiam> list = new ArrayList<>();
        if (Khoa.isEmpty() && Nganh.isEmpty() && Lop.isEmpty()) {
            list = GetDataMienGiam();
        } else {
            Statement st = con.createStatement();
            String query = "SELECT A.MASV,\n"
                    + " A.TENSV,\n"
                    + " B.DOITUONGCHINHSACH,\n"
                    + " B.KHUVUCO,\n"
                    + "CASE \n"
                    + "WHEN B.DOITUONGCHINHSACH = 'Mồ côi' AND B.KHUVUCO = 'Vùng núi-vùng sâu vùng xa' THEN '5.000.000 VND'\n"
                    + "WHEN B.DOITUONGCHINHSACH = 'Khuyết tật' AND B.KHUVUCO = 'Vùng núi-vùng sâu vùng xa' THEN '4.500.000 VND'\n"
                    + "WHEN B.DOITUONGCHINHSACH = 'Khuyết tật' THEN '1.500.000 VND'\n"
                    + "WHEN B.DOITUONGCHINHSACH = 'Mồ côi' THEN '2.000.000 VND'\n"
                    + "WHEN B.KHUVUCO = 'Vùng núi-vùng sâu vùng xa' THEN '2.500.000 VND'\n"
                    + "ELSE '0'\n"
                    + "END AS MUCMIENGIAM\n"
                    + " FROM N13.TTCANHAN A INNER JOIN N13.TTGIADINH B ON A.MASV = B.MASV\n"
                    + "WHERE A.Khoa=? AND A.NGANH=? AND A.LOP=?";
            PreparedStatement mg = con.prepareStatement(query);
            mg.setString(1, Khoa);
            mg.setString(2, Nganh);
            mg.setString(3, Lop);
            ResultSet result = mg.executeQuery();
            while (result.next()) {
                String masv = result.getString("MASV");
                String tensv = result.getString("TENSV");
                String doituongchinhsach = result.getString("DOITUONGCHINHSACH");
                String khuvuco = result.getString("KHUVUCO");
                String mucmiengiam = result.getString("MUCMIENGIAM");
                MienGiam mgg = new MienGiam(masv, tensv, doituongchinhsach, khuvuco, mucmiengiam);
                list.add(mgg);
            }
        }
        return list;
    }
}
