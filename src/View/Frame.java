/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.HoatDongCT_XHController;
import Controller.HoatDongDoan_HoiController;
import Controller.KhenThuongController;
import Controller.KyLuatController;
import Controller.MienGiamController;
import Controller.TTCaNhanController;
import Controller.TTGiaDinhController;
import Customtable.HoatDongCT_XHCustomTable;
import Customtable.HoatDongDoan_HoiCustomTable;
import Customtable.KhenThuongCustomTable;
import Customtable.KyLuatCustomTable;
import Customtable.MienGiamCustomTable;
import Customtable.TTCaNhanCustomTable;
import Customtable.TTGiaDinhCustomtable;
import Database.ConnectDB;
import Model.HoatDongCT_XH;
import Model.HoatDongDoan_Hoi;
import Model.KhenThuong;
import Model.ComboBoxModel;
import Model.KyLuat;
import Model.TTCaNhan;
import Model.TTGiaDinh;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author H
 */
public class Frame extends javax.swing.JFrame {

    // Khai bao list cho combobox
    List<String> listKhoa = new ArrayList<>();
    List<ComboBoxModel> listNganh = new ArrayList<>();
    List<ComboBoxModel> listLop = new ArrayList<>();
    // END
    public static ResultSet rs = null;
    public Statement st;
    ConnectDB cn = new ConnectDB();
    int selectedRow = 0;
    //TTCaNhan
    ArrayList<TTCaNhan> listcanhan = new ArrayList<>();
    TTCaNhanController tTCaNhanController = new TTCaNhanController();
    //TTGiaDinh
    ArrayList<TTGiaDinh> listgiadinh = new ArrayList<>();
    TTGiaDinhController tTGiaDinhController = new TTGiaDinhController();
    //KhenThuong
    ArrayList<KhenThuong> listkhenthuong = new ArrayList<>();
    KhenThuongController khenThuongController = new KhenThuongController();
    //KyLuat
    ArrayList<KyLuat> listkyluat = new ArrayList<>();
    KyLuatController kyLuatController = new KyLuatController();
    //HoatDongCT_XH
    ArrayList<HoatDongCT_XH> listHoatDongCT_XH = new ArrayList<>();
    HoatDongCT_XHController hoatDongCT_XHController = new HoatDongCT_XHController();
    //HoatDongDoan_Hoi
    ArrayList<HoatDongDoan_Hoi> listHoatDongDoan_Hoi = new ArrayList<>();
    HoatDongDoan_HoiController hoatDongDoan_HoiController = new HoatDongDoan_HoiController();
    //Mi???n gi???m
    MienGiamController MienGiamController = new MienGiamController();

    //MienGiam
    public Frame() {
        cn.getConnect();
        initComponents();
        loadTable();
        initDataCombobox();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        resetAllScene();

        canhan_khoa_cbb.addActionListener((ActionEvent e) -> {
            List<ComboBoxModel> listNganhCopy = new ArrayList<>(listNganh);
            listNganhCopy.removeIf(p -> !p.getValue1().equals(canhan_khoa_cbb.getSelectedItem().toString()));
            if (listNganhCopy.size() > 0) {
                List<String> listNganhStr = listNganhCopy.stream()
                        .map(object -> Objects.toString(object.getValue2(), null))
                        .collect(Collectors.toList());
                canhan_nganh_cbb.setModel(new DefaultComboBoxModel<>(listNganhStr.toArray(new String[0])));
                canhan_nganh_cbb.enable();
            } else {
                canhan_nganh_cbb.disable();
            }
        });

        canhan_nganh_cbb.addActionListener((ActionEvent e) -> {
            List<ComboBoxModel> listLopCopy = new ArrayList<>(listLop);
            listLopCopy.removeIf(p -> !p.getValue1().equals(canhan_nganh_cbb.getSelectedItem().toString()));
            if (listLopCopy.size() > 0) {
                List<String> listNganhStr = listLopCopy.stream()
                        .map(object -> Objects.toString(object.getValue2(), null))
                        .collect(Collectors.toList());
                canhan_lop_cbb.setModel(new DefaultComboBoxModel<>(listNganhStr.toArray(new String[0])));
                canhan_lop_cbb.enable();
            } else {
                canhan_lop_cbb.disable();
            }
        });
    }

    public void disableCombobox() {
        canhan_nganh_cbb.disable();
        canhan_lop_cbb.disable();
    }

    public final void resetAllScene() {
        xoaTrangCaNhan();
        xoaTrangGiaDinh();
        xoaTrangHoatDongDoan_Hoi();
        xoaTrangHoatDongCT_XH();
        xoaTrangMienGiam();
        disableCombobox();
    }

    public final void loadTable() {
        table_canhan.setModel(new TTCaNhanCustomTable(tTCaNhanController.getListTTCaNhan()));
        table_giadinh.setModel(new TTGiaDinhCustomtable(tTGiaDinhController.getListTTGiaDinh()));
        table_khenthuong.setModel(new KhenThuongCustomTable(khenThuongController.getListKhenThuong()));
        table_kyluat.setModel(new KyLuatCustomTable(kyLuatController.getListKyLuat()));
        table_chinhtri.setModel(new HoatDongCT_XHCustomTable(hoatDongCT_XHController.getListHoatDongCT_XH()));
        table_doan.setModel(new HoatDongDoan_HoiCustomTable(hoatDongDoan_HoiController.getListHoatDongDoan_Hoi()));
        table_miengiam.setModel(new MienGiamCustomTable(MienGiamController.getListMienGiam()));
        setList();
    }

    public final void initDataCombobox() {
        // tao moi data Khoa
        listKhoa.add("C??ng ngh??? th??ng tin");
        listKhoa.add("C?? kh??");
        canhan_khoa_cbb.setModel(new DefaultComboBoxModel<>(listKhoa.toArray(new String[0])));

        // tao moi data Nganh theo khoa (TenKhoa/TenNganh)
        ComboBoxModel nganh = new ComboBoxModel("C??ng ngh??? th??ng tin", "C??ng ngh??? th??ng tin");
        ComboBoxModel nganh1 = new ComboBoxModel("C??ng ngh??? th??ng tin", "K??? thu???t ph???n m???m");
        ComboBoxModel nganh2 = new ComboBoxModel("C??ng ngh??? th??ng tin", "H??? th???ng th??ng tin");
        ComboBoxModel nganh3 = new ComboBoxModel("C??ng ngh??? th??ng tin", "Khoa h???c m??y t??nh");
        ComboBoxModel nganh4 = new ComboBoxModel("C?? kh??", "H??n x??");
        ComboBoxModel nganh5 = new ComboBoxModel("C?? kh??", "Khoan ?????c");
        ComboBoxModel nganh6 = new ComboBoxModel("C?? kh??", "????? b?? t??ng");
        listNganh = Arrays.asList(nganh, nganh1, nganh2, nganh3, nganh4, nganh5, nganh6);

        // tao moi data Lop theo Nganh (TenNganh/TenLop)
        ComboBoxModel lopcntt = new ComboBoxModel("C??ng ngh??? th??ng tin", "CNTT 1");
        ComboBoxModel lopcntt1 = new ComboBoxModel("C??ng ngh??? th??ng tin", "CNTT 2");
        ComboBoxModel lopktpm = new ComboBoxModel("K??? thu???t ph???n m???m", "KTPM 1");
        ComboBoxModel lopktpm1 = new ComboBoxModel("K??? thu???t ph???n m???m", "KTPM 2");
        ComboBoxModel lophttt = new ComboBoxModel("H??? th???ng th??ng tin", "HTTT 1");
        ComboBoxModel lophttt1 = new ComboBoxModel("H??? th???ng th??ng tin", "HTTT 2");
        ComboBoxModel lopkhmt = new ComboBoxModel("Khoa h???c m??y t??nh", "KHMT 1");
        ComboBoxModel lopkhmt1 = new ComboBoxModel("Khoa h???c m??y t??nh", "KHMT 2");
        ComboBoxModel lophx = new ComboBoxModel("H??n x??", "HX 1");
        ComboBoxModel lophx1 = new ComboBoxModel("H??n x??", "HX 2");
        ComboBoxModel lopkd = new ComboBoxModel("Khoan ?????c", "KD 1");
        ComboBoxModel lopkd1 = new ComboBoxModel("Khoan ?????c", "KD 2");
        ComboBoxModel lopdbt = new ComboBoxModel("????? b?? t??ng", "DBT 1");
        ComboBoxModel lopdbt1 = new ComboBoxModel("????? b?? t??ng", "DBT 2");
        listLop = Arrays.asList(lopcntt, lopcntt1, lopktpm, lopktpm1, lophttt, lophttt1, lopkhmt,
                lopkhmt1, lophx, lophx1, lopkd, lopkd1, lopdbt, lopdbt1);
    }

    public void setList() {
        listcanhan = tTCaNhanController.getListTTCaNhan();
        listgiadinh = tTGiaDinhController.getListTTGiaDinh();
        listkhenthuong = khenThuongController.getListKhenThuong();
        listkyluat = kyLuatController.getListKyLuat();
        listHoatDongCT_XH = hoatDongCT_XHController.getListHoatDongCT_XH();
        listHoatDongDoan_Hoi = hoatDongDoan_HoiController.getListHoatDongDoan_Hoi();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        canhan_khoa_cbb = new javax.swing.JComboBox<>();
        canhan_nganh_cbb = new javax.swing.JComboBox<>();
        canhan_lop_cbb = new javax.swing.JComboBox<>();
        canhan_masv = new javax.swing.JTextField();
        canhan_tensv = new javax.swing.JTextField();
        canhan_namradio = new javax.swing.JRadioButton();
        canhan_nuradio = new javax.swing.JRadioButton();
        canhan_ngaysinh = new javax.swing.JTextField();
        canhan_quequan = new javax.swing.JTextField();
        canhan_cmnd = new javax.swing.JTextField();
        canhan_sdt = new javax.swing.JTextField();
        canhan_email = new javax.swing.JTextField();
        timkiem_canhan = new javax.swing.JButton();
        themmoi_canhan = new javax.swing.JButton();
        sua_canhan = new javax.swing.JButton();
        xoa_canhan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_canhan = new javax.swing.JTable();
        quaylai_canhan = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        gd_masv = new javax.swing.JTextField();
        gd_tensv = new javax.swing.JTextField();
        gd_hotenbo = new javax.swing.JTextField();
        gd_hotenme = new javax.swing.JTextField();
        gd_sdtlienhe = new javax.swing.JTextField();
        gd_anhemtrai = new javax.swing.JTextField();
        gd_chiemgai = new javax.swing.JTextField();
        gd_doituongchinhsach = new javax.swing.JComboBox<>();
        gd_khuvuco = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        timkiem_gd = new javax.swing.JButton();
        themmoi_gd = new javax.swing.JButton();
        sua_gd = new javax.swing.JButton();
        xoa_gd = new javax.swing.JButton();
        quaylai_gd = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_giadinh = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        khenthuong_masv = new javax.swing.JTextField();
        khenthuong_tensv = new javax.swing.JTextField();
        khenthuong_diemcong = new javax.swing.JTextField();
        themmoi_khenthuong = new javax.swing.JButton();
        sua_khenthuong = new javax.swing.JButton();
        xoa_khenthuong = new javax.swing.JButton();
        timkiem_khenthuong = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        khenthuong_ndkhen = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_khenthuong = new javax.swing.JTable();
        quaylai_khenthuong = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        kyluat_masv = new javax.swing.JTextField();
        kyluat_tensv = new javax.swing.JTextField();
        kyluat_diemtru = new javax.swing.JTextField();
        themmoi_kyluat = new javax.swing.JButton();
        sua_kyluat = new javax.swing.JButton();
        xoa_kyluat = new javax.swing.JButton();
        timkiem_kyluat = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        kyluat_noidung = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        table_kyluat = new javax.swing.JTable();
        quaylai_kyluat = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        J = new javax.swing.JTabbedPane();
        jPanel16 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        chinhtri_masv = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        chinhtri_tensv = new javax.swing.JTextField();
        chinhtri_diemcong = new javax.swing.JTextField();
        themmoi_chinhtri = new javax.swing.JButton();
        xoa_chinhtri = new javax.swing.JButton();
        sua_chinhtri = new javax.swing.JButton();
        timkiem_chinhtri = new javax.swing.JButton();
        chinhtri_ndung = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        table_chinhtri = new javax.swing.JTable();
        quaylai_chinhtri = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        doan_masv = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        doan_tensv = new javax.swing.JTextField();
        doan_diemcong = new javax.swing.JTextField();
        themmoi_doan = new javax.swing.JButton();
        xoa_doan = new javax.swing.JButton();
        sua_doan = new javax.swing.JButton();
        timkiem_doan = new javax.swing.JButton();
        doan_ndung = new javax.swing.JComboBox();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        table_doan = new javax.swing.JTable();
        quaylai_doan = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        miengiam_khoa = new javax.swing.JComboBox<>();
        miengiam_nganh = new javax.swing.JComboBox<>();
        miengiam_lop = new javax.swing.JComboBox<>();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        table_miengiam = new javax.swing.JTable();
        timkiem_miengiam = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Khoa");

        jLabel2.setText("Ng??nh");

        jLabel3.setText("L???p");

        jLabel4.setText("M?? sinh vi??n");

        jLabel5.setText("T??n sinh vi??n");

        jLabel6.setText("Gi???i t??nh");

        jLabel7.setText("Ng??y sinh");

        jLabel8.setText("Qu?? qu??n");

        jLabel9.setText("S??? CMND/CCCD");

        jLabel10.setText("SDT");

        jLabel11.setText("Email");

        buttonGroup1.add(canhan_namradio);
        canhan_namradio.setText("Nam");

        buttonGroup1.add(canhan_nuradio);
        canhan_nuradio.setText("N???");

        canhan_sdt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                canhan_sdtActionPerformed(evt);
            }
        });

        canhan_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                canhan_emailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(76, 76, 76)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(canhan_lop_cbb, 0, 123, Short.MAX_VALUE)
                            .addComponent(canhan_masv, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(canhan_tensv, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(canhan_nganh_cbb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(canhan_khoa_cbb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(72, 72, 72)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(79, 79, 79)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(canhan_ngaysinh, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                                    .addComponent(canhan_quequan)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel9))
                                .addGap(50, 50, 50)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(canhan_sdt)
                                    .addComponent(canhan_email)
                                    .addComponent(canhan_cmnd))))
                        .addGap(162, 162, 162))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(canhan_namradio)
                        .addGap(21, 21, 21)
                        .addComponent(canhan_nuradio)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(canhan_ngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(canhan_khoa_cbb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(canhan_nganh_cbb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(canhan_quequan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9)
                    .addComponent(canhan_lop_cbb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(canhan_cmnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel10)
                    .addComponent(canhan_masv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(canhan_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel11)
                    .addComponent(canhan_tensv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(canhan_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(canhan_nuradio)
                    .addComponent(canhan_namradio)
                    .addComponent(jLabel6))
                .addContainerGap())
        );

        timkiem_canhan.setText("T??m ki???m");
        timkiem_canhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timkiem_canhanActionPerformed(evt);
            }
        });

        themmoi_canhan.setText("Th??m m???i");
        themmoi_canhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themmoi_canhanActionPerformed(evt);
            }
        });

        sua_canhan.setText("S???a");
        sua_canhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sua_canhanActionPerformed(evt);
            }
        });

        xoa_canhan.setText("Xo??");
        xoa_canhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoa_canhanActionPerformed(evt);
            }
        });

        table_canhan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Khoa", "Ng??nh", "L???p", "M?? sinh vi??n", "T??n sinh vi??n", "Gi???i t??nh", "Ng??y sinh", "Qu?? qu??n", "S??? CMND/CCCD", "SDT", "Email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_canhan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_canhanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_canhan);

        quaylai_canhan.setText("Quay l???i");
        quaylai_canhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quaylai_canhanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(timkiem_canhan)
                .addGap(57, 57, 57)
                .addComponent(themmoi_canhan)
                .addGap(80, 80, 80)
                .addComponent(sua_canhan)
                .addGap(76, 76, 76)
                .addComponent(xoa_canhan)
                .addGap(68, 68, 68)
                .addComponent(quaylai_canhan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timkiem_canhan)
                    .addComponent(themmoi_canhan)
                    .addComponent(sua_canhan)
                    .addComponent(xoa_canhan)
                    .addComponent(quaylai_canhan))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 228, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Th??ng tin c?? nh??n", jPanel5);

        jLabel12.setText("M?? sinh vi??n");

        jLabel13.setText("T??n sinh vi??n");

        jLabel14.setText("H??? t??n b???");

        jLabel15.setText("H??? t??n m???");

        jLabel20.setText("SDT li??n h???");

        jLabel21.setText("Anh/Em trai");

        jLabel22.setText("Ch???/Em g??i");

        jLabel23.setText("?????i t?????ng ch??nh s??ch");

        jLabel24.setText("Khu v???c ???");

        gd_doituongchinhsach.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "B??nh th?????ng", "M??? c??i", "Khuy???t t???t" }));

        gd_khuvuco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "?????ng b???ng", "???? th???", "V??ng n??i-v??ng s??u v??ng xa", "Bi??n gi???i - H???i ?????o" }));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(96, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel14)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12))
                        .addComponent(jLabel15))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(14, 14, 14)))
                .addGap(72, 72, 72)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gd_sdtlienhe, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gd_hotenme, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gd_hotenbo, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gd_tensv, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gd_masv, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(66, 66, 66)
                        .addComponent(gd_anhemtrai, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel22)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(gd_chiemgai, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel23)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(gd_doituongchinhsach, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel24)
                            .addGap(76, 76, 76)
                            .addComponent(gd_khuvuco, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21)
                        .addComponent(gd_anhemtrai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(gd_masv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel22)
                    .addComponent(gd_tensv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gd_chiemgai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel23)
                    .addComponent(gd_hotenbo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gd_doituongchinhsach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel24)
                    .addComponent(gd_hotenme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gd_khuvuco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(gd_sdtlienhe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        timkiem_gd.setText("T??m ki???m");
        timkiem_gd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timkiem_gdActionPerformed(evt);
            }
        });

        themmoi_gd.setText("Th??m m???i");
        themmoi_gd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                themmoi_gdFocusLost(evt);
            }
        });
        themmoi_gd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themmoi_gdActionPerformed(evt);
            }
        });

        sua_gd.setText("S???a");
        sua_gd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sua_gdActionPerformed(evt);
            }
        });

        xoa_gd.setText("Xo??");
        xoa_gd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoa_gdActionPerformed(evt);
            }
        });

        quaylai_gd.setText("Quay l???i");
        quaylai_gd.setToolTipText("");
        quaylai_gd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quaylai_gdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(timkiem_gd)
                .addGap(66, 66, 66)
                .addComponent(themmoi_gd)
                .addGap(71, 71, 71)
                .addComponent(sua_gd)
                .addGap(82, 82, 82)
                .addComponent(xoa_gd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(quaylai_gd)
                .addGap(64, 64, 64))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timkiem_gd)
                    .addComponent(themmoi_gd)
                    .addComponent(sua_gd)
                    .addComponent(xoa_gd)
                    .addComponent(quaylai_gd))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        table_giadinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "M?? sinh vi??n", "T??n sinh vi??n", "H??? t??n b???", "H??? t??n m???", "SDT li??n h???", "Anh/Em trai", "Ch???/Em g??i", "?????i t?????ng ch??nh s??ch", "Khu v???c ???"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_giadinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_giadinhMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_giadinh);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 849, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(194, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Th??ng tin gia ????nh", jPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jTabbedPane1.addTab("Th??ng tin sinh vi??n", jPanel1);

        jLabel16.setText("N???i dung khen th?????ng");

        jLabel17.setText("??i???m c???ng");

        themmoi_khenthuong.setText("Th??m m???i");
        themmoi_khenthuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themmoi_khenthuongActionPerformed(evt);
            }
        });

        sua_khenthuong.setText("S???a");
        sua_khenthuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sua_khenthuongActionPerformed(evt);
            }
        });

        xoa_khenthuong.setText("X??a");
        xoa_khenthuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoa_khenthuongActionPerformed(evt);
            }
        });

        timkiem_khenthuong.setText("T??m ki???m");
        timkiem_khenthuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timkiem_khenthuongActionPerformed(evt);
            }
        });

        jLabel18.setText("M?? sinh vi??n");

        jLabel19.setText("T??n sinh vi??n");

        table_khenthuong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "M?? sinh vi??n", "T??n sinh vi??n", "N???i dung khen th?????ng", "??i???m c???ng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_khenthuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_khenthuongMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(table_khenthuong);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(250, Short.MAX_VALUE))
        );

        quaylai_khenthuong.setText("Quay l???i");
        quaylai_khenthuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quaylai_khenthuongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addGap(38, 38, 38)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(khenthuong_masv, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                    .addComponent(khenthuong_tensv)
                    .addComponent(khenthuong_diemcong)
                    .addComponent(khenthuong_ndkhen))
                .addGap(93, 93, 93)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(themmoi_khenthuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timkiem_khenthuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sua_khenthuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xoa_khenthuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(quaylai_khenthuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(khenthuong_masv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timkiem_khenthuong))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(khenthuong_tensv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(themmoi_khenthuong)))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(khenthuong_ndkhen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sua_khenthuong)))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(xoa_khenthuong))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(khenthuong_diemcong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))))
                .addGap(18, 18, 18)
                .addComponent(quaylai_khenthuong)
                .addGap(17, 17, 17)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 420, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("Khen th?????ng", jPanel10);

        jLabel25.setText("N???i dung k??? lu???t");

        jLabel26.setText("??i???m tr???");

        themmoi_kyluat.setText("Th??m m???i");
        themmoi_kyluat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themmoi_kyluatActionPerformed(evt);
            }
        });

        sua_kyluat.setText("S???a");
        sua_kyluat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sua_kyluatActionPerformed(evt);
            }
        });

        xoa_kyluat.setText("X??a");
        xoa_kyluat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoa_kyluatActionPerformed(evt);
            }
        });

        timkiem_kyluat.setText("T??m ki???m");
        timkiem_kyluat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timkiem_kyluatActionPerformed(evt);
            }
        });

        jLabel27.setText("M?? sinh vi??n");

        jLabel28.setText("T??n sinh vi??n");

        table_kyluat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "M?? sinh vi??n", "T??n sinh vi??n", "N???i dung k??? lu???t", "??i???m tr???"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_kyluat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_kyluatMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(table_kyluat);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(250, Short.MAX_VALUE))
        );

        quaylai_kyluat.setText("Quay l???i");
        quaylai_kyluat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quaylai_kyluatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26))
                .addGap(38, 38, 38)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kyluat_masv, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                    .addComponent(kyluat_tensv)
                    .addComponent(kyluat_diemtru)
                    .addComponent(kyluat_noidung))
                .addGap(93, 93, 93)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(themmoi_kyluat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timkiem_kyluat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sua_kyluat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xoa_kyluat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(quaylai_kyluat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(kyluat_masv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(timkiem_kyluat))
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel28)
                                    .addComponent(kyluat_tensv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(themmoi_kyluat)))
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel25)
                                    .addComponent(kyluat_noidung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(sua_kyluat)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(xoa_kyluat))
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel26)
                        .addComponent(kyluat_diemtru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addComponent(quaylai_kyluat)
                .addGap(18, 18, 18)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 420, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("K??? lu???t", jPanel11);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 62, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 210, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Khen th?????ng - K??? lu???t", jPanel2);

        jLabel29.setText("N???i dung ho???t ?????ng");

        jLabel30.setText("??i???m c???ng");

        themmoi_chinhtri.setText("Th??m m???i");
        themmoi_chinhtri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themmoi_chinhtriActionPerformed(evt);
            }
        });

        xoa_chinhtri.setText("X??a");
        xoa_chinhtri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoa_chinhtriActionPerformed(evt);
            }
        });

        sua_chinhtri.setText("S???a");
        sua_chinhtri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sua_chinhtriActionPerformed(evt);
            }
        });

        timkiem_chinhtri.setText("T??m ki???m");
        timkiem_chinhtri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timkiem_chinhtriActionPerformed(evt);
            }
        });

        chinhtri_ndung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Gi??? g??n tr???t t??? an to??n x?? h???i", "Ho???t ?????ng nh??n ?????o", "Ho???t ?????ng ?????n ??n ????p ngh??a" }));

        jLabel31.setText("M?? sinh vi??n");

        jLabel32.setText("T??n sinh vi??n");

        table_chinhtri.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "M?? sinh vi??n", "T??n sinh vi??n", "N???i dung ho???t ?????ng", "??i???m c???ng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_chinhtri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_chinhtriMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(table_chinhtri);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        quaylai_chinhtri.setText("Quay l???i");
        quaylai_chinhtri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quaylai_chinhtriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30))
                .addGap(38, 38, 38)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chinhtri_masv, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                    .addComponent(chinhtri_tensv)
                    .addComponent(chinhtri_diemcong)
                    .addComponent(chinhtri_ndung, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(129, 129, 129)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(timkiem_chinhtri, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(themmoi_chinhtri, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sua_chinhtri, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xoa_chinhtri, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(quaylai_chinhtri, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(chinhtri_masv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timkiem_chinhtri))
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(chinhtri_tensv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(themmoi_chinhtri)))
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(chinhtri_ndung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sua_chinhtri)))
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xoa_chinhtri)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quaylai_chinhtri))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chinhtri_diemcong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        J.addTab("Ho???t ?????ng Ch??nh tr??? - X?? h???i", jPanel16);

        jLabel33.setText("N???i dung ho???t ?????ng");

        jLabel34.setText("??i???m c???ng");

        themmoi_doan.setText("Th??m m???i");
        themmoi_doan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themmoi_doanActionPerformed(evt);
            }
        });

        xoa_doan.setText("X??a");
        xoa_doan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoa_doanActionPerformed(evt);
            }
        });

        sua_doan.setText("S???a");
        sua_doan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sua_doanActionPerformed(evt);
            }
        });

        timkiem_doan.setText("T??m ki???m");
        timkiem_doan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timkiem_doanActionPerformed(evt);
            }
        });

        doan_ndung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tham gia IT Festival", "Thsm gia ho???t ?????ng t??nh nguy???n" }));

        jLabel35.setText("M?? sinh vi??n");

        jLabel36.setText("T??n sinh vi??n");

        table_doan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "M?? sinh vi??n", "T??n sinh vi??n", "N???i dung ho???t ?????ng", "??i???m c???ng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_doan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_doanMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(table_doan);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        quaylai_doan.setText("Quay l???i");
        quaylai_doan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quaylai_doanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34))
                .addGap(38, 38, 38)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(doan_masv)
                    .addComponent(doan_tensv)
                    .addComponent(doan_diemcong)
                    .addComponent(doan_ndung, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(129, 129, 129)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(timkiem_doan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(themmoi_doan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sua_doan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(xoa_doan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(quaylai_doan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(doan_masv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timkiem_doan))
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(doan_tensv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(doan_ndung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(doan_diemcong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(quaylai_doan)))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(themmoi_doan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sua_doan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(xoa_doan)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 735, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel17Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 426, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel17Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        J.addTab("Ho???t ?????ng ??o??n - H???i nh?? tr?????ng", jPanel17);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(J, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 52, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(J, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(215, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ho???t ?????ng ??o??n th???", jPanel3);

        jLabel38.setText("Ng??nh");

        jLabel39.setText("L???p");

        miengiam_khoa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "C??ng ngh??? th??ng tin", "C?? kh??" }));

        miengiam_nganh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "C??ng ngh??? th??ng tin", "K??? thu???t ph???n m???m", "H??? th???ng th??ng tin" }));

        miengiam_lop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CNTT1", "CNTT2", "CNTT3", "CNTT4", "CNTT5", "CNTT6" }));

        table_miengiam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "M?? sinh vi??n", "T??n sinh vi??n", "?????i t?????ng ch??nh s??ch", "Khu v???c ???", "M???c mi???n gi???m"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_miengiam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_miengiamMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(table_miengiam);

        timkiem_miengiam.setText("T??m ki???m");
        timkiem_miengiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timkiem_miengiamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                        .addComponent(timkiem_miengiam, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(300, 300, 300))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 628, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(timkiem_miengiam)
                .addGap(38, 38, 38)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(294, Short.MAX_VALUE))
        );

        jLabel37.setText("Khoa");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel37)
                        .addGap(18, 18, 18)
                        .addComponent(miengiam_khoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(jLabel38)
                        .addGap(18, 18, 18)
                        .addComponent(miengiam_nganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(jLabel39)
                        .addGap(18, 18, 18)
                        .addComponent(miengiam_lop, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(miengiam_khoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)
                    .addComponent(jLabel38)
                    .addComponent(miengiam_nganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(miengiam_lop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Th???ng k?? mi???n gi???m h???c ph??", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 797, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void canhan_sdtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_canhan_sdtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_canhan_sdtActionPerformed

    private void canhan_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_canhan_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_canhan_emailActionPerformed

//---------------------
//S???a KhenThuong
    private void sua_khenthuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sua_khenthuongActionPerformed
        KhenThuong kt = new KhenThuong();
        kt.setMaSV(khenthuong_masv.getText());
        kt.setTenSV(khenthuong_tensv.getText());
        kt.setNDKhenThuong(khenthuong_ndkhen.getText());
        kt.setDiemCong(Float.parseFloat(khenthuong_diemcong.getText()));
        khenThuongController.updateKhenThuong(kt);
        loadTable();
        xoaTrangKhenThuong();
    }//GEN-LAST:event_sua_khenthuongActionPerformed

//---------------------
//S???a K??? Lu???t
    private void sua_kyluatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sua_kyluatActionPerformed
        // TODO add your handling code here:
        KyLuat kl = new KyLuat();
        kl.setMaSV(kyluat_masv.getText());
        kl.setTenSV(kyluat_tensv.getText());
        kl.setNDKyLuat(kyluat_noidung.getText());
        kl.setDiemTru(Float.parseFloat(kyluat_diemtru.getText()));
        kyLuatController.updateKyLuat(kl);
        loadTable();
        xoaTrangKyLuat();
    }//GEN-LAST:event_sua_kyluatActionPerformed

//---------------------
// Xo?? tr???ng HD CT_XH
    public void xoaTrangHoatDongCT_XH() {
        chinhtri_masv.setText("");
        chinhtri_tensv.setText("");
        chinhtri_ndung.setSelectedItem(null);
        chinhtri_diemcong.setText("");
        chinhtri_masv.requestFocus();
        chinhtri_masv.enable();
    }
//Th??m HoatDongCT_XH
    private void themmoi_chinhtriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themmoi_chinhtriActionPerformed
        // TODO add your handling code here:
        HoatDongCT_XH ctxh = new HoatDongCT_XH();
        ctxh.setMaSV(chinhtri_masv.getText());
        ctxh.setTenSV(chinhtri_tensv.getText());
        ctxh.setNDHoatDong(chinhtri_ndung.getSelectedItem().toString());
        ctxh.setDiemCong(Float.parseFloat(chinhtri_diemcong.getText()));
        hoatDongCT_XHController.addHoatDongCT_XH(ctxh);
        loadTable();
        xoaTrangHoatDongCT_XH();
    }//GEN-LAST:event_themmoi_chinhtriActionPerformed

//---------------------
//Xo?? tr???ng HoatDongDoan_Hoi
    public void xoaTrangHoatDongDoan_Hoi() {
        doan_masv.setText("");
        doan_tensv.setText("");
        doan_ndung.setSelectedItem(null);
        doan_diemcong.setText("");
        doan_masv.requestFocus();
        doan_masv.enable();
    }
//Th??m HoatDongDoan_Hoi
    private void themmoi_doanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themmoi_doanActionPerformed
        // TODO add your handling code here:
        HoatDongDoan_Hoi dh = new HoatDongDoan_Hoi();
        dh.setMaSV(doan_masv.getText());
        dh.setTenSV(doan_tensv.getText());
        dh.setNDHoatDong(doan_ndung.getSelectedItem().toString());
        dh.setDiemCong(Float.parseFloat(doan_diemcong.getText()));
        hoatDongDoan_HoiController.addHoatDongDoan_Hoi(dh);
        loadTable();
        xoaTrangHoatDongDoan_Hoi();
    }//GEN-LAST:event_themmoi_doanActionPerformed

//---------------------
//X??a KhenThuong
    public void xoaTrangKhenThuong() {
        khenthuong_masv.setText("");
        khenthuong_tensv.setText("");
        khenthuong_ndkhen.setText("");
        khenthuong_diemcong.setText("");
        khenthuong_masv.requestFocus();
        khenthuong_masv.enable();
    }
    private void xoa_khenthuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoa_khenthuongActionPerformed
        int ret = JOptionPane.showConfirmDialog(null,
                "b???n ch???c ch???n mu???n xo???", "Tho??t",
                JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            khenThuongController.xoaKhenThuong(khenthuong_masv.getText());
            loadTable();
            xoaTrangKhenThuong();
        }
    }//GEN-LAST:event_xoa_khenthuongActionPerformed

//Th??m,s???a,x??a,t??m ki???m TTCaNhan
    private void themmoi_canhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themmoi_canhanActionPerformed
        TTCaNhan ttcn = new TTCaNhan();
        ttcn.setKhoa(canhan_khoa_cbb.getSelectedItem().toString());
        ttcn.setNganh(canhan_nganh_cbb.getSelectedItem().toString());
        ttcn.setLop(canhan_lop_cbb.getSelectedItem().toString());
        ttcn.setMaSV(canhan_masv.getText());
        ttcn.setTenSV(canhan_tensv.getText());
        if (canhan_namradio.isSelected() == true) {
            ttcn.setGioiTinh("Nam");
        } else {
            ttcn.setGioiTinh("N???");
        }
        ttcn.setNgaySinh(canhan_ngaysinh.getText());
        ttcn.setQueQuan(canhan_quequan.getText());
        ttcn.setCMND(canhan_cmnd.getText());
        ttcn.setSDT(canhan_sdt.getText());
        ttcn.setEmail(canhan_email.getText());
        tTCaNhanController.addTTCaNhan(ttcn);
        loadTable();
        xoaTrangCaNhan();
    }//GEN-LAST:event_themmoi_canhanActionPerformed

    private void themmoi_gdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_themmoi_gdFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_themmoi_gdFocusLost

    private void timkiem_canhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timkiem_canhanActionPerformed
        if (tTCaNhanController.searchTTCaNhan(canhan_masv.getText()).size() > 0) {
            table_canhan.setModel(new TTCaNhanCustomTable(tTCaNhanController.searchTTCaNhan(canhan_masv.getText())));
        }
    }//GEN-LAST:event_timkiem_canhanActionPerformed
    public void xoaTrangCaNhan() {
        canhan_khoa_cbb.setSelectedItem(null);
        canhan_nganh_cbb.setSelectedItem(null);
        canhan_lop_cbb.setSelectedItem(null);
        canhan_masv.setText("");
        canhan_tensv.setText("");
        buttonGroup1.clearSelection();
        canhan_ngaysinh.setText("");
        canhan_quequan.setText("");
        canhan_cmnd.setText("");
        canhan_sdt.setText("");
        canhan_email.setText("");
        canhan_khoa_cbb.requestFocus();
        canhan_masv.enable();
    }
    private void xoa_canhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoa_canhanActionPerformed
        int ret = JOptionPane.showConfirmDialog(null,
                "b???n ch???c ch???n mu???n xo???", "Tho??t",
                JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            listcanhan.remove(selectedRow);
            tTCaNhanController.deleteTTCaNhan(canhan_masv.getText());
            loadTable();
            xoaTrangCaNhan();
        }
    }//GEN-LAST:event_xoa_canhanActionPerformed

    private void sua_canhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sua_canhanActionPerformed
        TTCaNhan ttcn = new TTCaNhan();
        ttcn.setKhoa(canhan_khoa_cbb.getSelectedItem().toString());
        ttcn.setNganh(canhan_nganh_cbb.getSelectedItem().toString());
        ttcn.setLop(canhan_lop_cbb.getSelectedItem().toString());
        ttcn.setMaSV(canhan_masv.getText());
        ttcn.setTenSV(canhan_tensv.getText());
        if (canhan_namradio.isSelected() == true) {
            ttcn.setGioiTinh("Nam");
        } else {
            ttcn.setGioiTinh("N???");
        }
        ttcn.setNgaySinh(canhan_ngaysinh.getText());
        ttcn.setQueQuan(canhan_quequan.getText());
        ttcn.setCMND(canhan_cmnd.getText());
        ttcn.setSDT(canhan_sdt.getText());
        ttcn.setEmail(canhan_email.getText());
        tTCaNhanController.updateTTCaNhan(ttcn);
        loadTable();
        xoaTrangCaNhan();
    }//GEN-LAST:event_sua_canhanActionPerformed

    private void table_canhanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_canhanMouseClicked
        selectedRow = table_canhan.getSelectedRow();
        TTCaNhan ttcn = listcanhan.get(selectedRow);
        canhan_khoa_cbb.setSelectedItem(ttcn.getKhoa() + "");
        canhan_nganh_cbb.setSelectedItem(ttcn.getNganh() + "");
        canhan_lop_cbb.setSelectedItem(ttcn.getLop() + "");
        canhan_masv.setText(ttcn.getMaSV() + "");
        canhan_tensv.setText(ttcn.getTenSV() + "");
        if (ttcn.getGioiTinh() == "Nam") {
            canhan_namradio.setSelected(true);
        } else {
            canhan_nuradio.setSelected(true);
        }
        canhan_ngaysinh.setText(ttcn.getNgaySinh() + "");
        canhan_quequan.setText(ttcn.getQueQuan() + "");
        canhan_cmnd.setText(ttcn.getCMND() + "");
        canhan_sdt.setText(ttcn.getSDT() + "");
        canhan_email.setText(ttcn.getEmail() + "");
    }//GEN-LAST:event_table_canhanMouseClicked

//---------------------
//Th??m,s???a,x??a,t??m ki???m TTGiaDinh
    private void timkiem_gdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timkiem_gdActionPerformed
        if (tTGiaDinhController.searchTTGiaDinh(gd_masv.getText()).size() > 0) {
            table_giadinh.setModel(new TTGiaDinhCustomtable(tTGiaDinhController.searchTTGiaDinh(gd_masv.getText())));
        }
    }//GEN-LAST:event_timkiem_gdActionPerformed

    private void themmoi_gdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themmoi_gdActionPerformed
        TTGiaDinh ttgd = new TTGiaDinh();
        ttgd.setMaSV(gd_masv.getText());
        ttgd.setTenSV(gd_tensv.getText());
        ttgd.setHoTenBo(gd_hotenbo.getText());
        ttgd.setHoTenMe(gd_hotenme.getText());
        ttgd.setSDTLienHe(gd_sdtlienhe.getText());
        ttgd.setAnhEmTrai(gd_anhemtrai.getText());
        ttgd.setChiEmGai(gd_chiemgai.getText());
        ttgd.setDoiTuongChinhSach(gd_doituongchinhsach.getSelectedItem().toString());
        ttgd.setKhuVucO(gd_khuvuco.getSelectedItem().toString());
        tTGiaDinhController.addTTGiaDinh(ttgd);
        loadTable();
        xoaTrangGiaDinh();
    }//GEN-LAST:event_themmoi_gdActionPerformed

    private void sua_gdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sua_gdActionPerformed
        TTGiaDinh ttgd = new TTGiaDinh();
        ttgd.setMaSV(gd_masv.getText());
        ttgd.setTenSV(gd_tensv.getText());
        ttgd.setHoTenBo(gd_hotenbo.getText());
        ttgd.setHoTenMe(gd_hotenme.getText());
        ttgd.setSDTLienHe(gd_sdtlienhe.getText());
        ttgd.setAnhEmTrai(gd_anhemtrai.getText());
        ttgd.setChiEmGai(gd_chiemgai.getText());
        ttgd.setDoiTuongChinhSach(gd_doituongchinhsach.getSelectedItem().toString());
        ttgd.setKhuVucO(gd_khuvuco.getSelectedItem().toString());
        tTGiaDinhController.updateTTGiaDinh(ttgd);
        loadTable();
        xoaTrangGiaDinh();
    }//GEN-LAST:event_sua_gdActionPerformed
    public void xoaTrangGiaDinh() {
        gd_masv.setText("");
        gd_tensv.setText("");
        gd_hotenbo.setText("");
        gd_hotenme.setText("");
        gd_sdtlienhe.setText("");
        gd_chiemgai.setText("");
        gd_anhemtrai.setText("");
        gd_doituongchinhsach.setSelectedItem(null);
        gd_khuvuco.setSelectedItem(null);
        gd_masv.requestFocus();
        gd_masv.enable();
    }
    private void xoa_gdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoa_gdActionPerformed
        int ret = JOptionPane.showConfirmDialog(null,
                "b???n ch???c ch???n mu???n xo???", "Tho??t",
                JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            listgiadinh.remove(selectedRow);
            tTGiaDinhController.deleteTTGiaDinh(gd_masv.getText());
            loadTable();
            xoaTrangGiaDinh();
        }
    }//GEN-LAST:event_xoa_gdActionPerformed

    private void table_giadinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_giadinhMouseClicked
        selectedRow = table_giadinh.getSelectedRow();
        TTGiaDinh ttgd = listgiadinh.get(selectedRow);
        gd_masv.setText(ttgd.getMaSV() + "");
        gd_tensv.setText(ttgd.getTenSV() + "");
        gd_hotenbo.setText(ttgd.getHoTenBo() + "");
        gd_hotenme.setText(ttgd.getHoTenMe() + "");
        gd_sdtlienhe.setText(ttgd.getSDTLienHe() + "");
        gd_anhemtrai.setText(ttgd.getAnhEmTrai() + "");
        gd_chiemgai.setText(ttgd.getChiEmGai() + "");
        gd_doituongchinhsach.setSelectedItem(ttgd.getDoiTuongChinhSach() + "");
        gd_khuvuco.setSelectedItem(ttgd.getKhuVucO() + "");
        gd_masv.disable();
    }//GEN-LAST:event_table_giadinhMouseClicked

//---------------------
//Th??m,t??m ki???m KhenThuong
    private void timkiem_khenthuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timkiem_khenthuongActionPerformed
        if (khenThuongController.searchKhenThuong(khenthuong_masv.getText()).size() > 0) {
            table_khenthuong.setModel(new KhenThuongCustomTable(khenThuongController.searchKhenThuong(khenthuong_masv.getText())));
        }
    }//GEN-LAST:event_timkiem_khenthuongActionPerformed

    private void themmoi_khenthuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themmoi_khenthuongActionPerformed
        try {
            KhenThuong kt = new KhenThuong();
            kt.setMaSV(khenthuong_masv.getText());
            kt.setTenSV(khenthuong_tensv.getText());
            kt.setNDKhenThuong(khenthuong_ndkhen.getText());
            kt.setDiemCong(Float.parseFloat(khenthuong_diemcong.getText()));
            khenThuongController.addKhenThuong(kt);
            loadTable();
            xoaTrangKhenThuong();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_themmoi_khenthuongActionPerformed

    private void table_khenthuongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_khenthuongMouseClicked
        selectedRow = table_khenthuong.getSelectedRow();// TODO add your handling code here:
        KhenThuong kt = listkhenthuong.get(selectedRow);
        khenthuong_masv.setText(kt.getMaSV());
        khenthuong_tensv.setText(kt.getTenSV());
        khenthuong_ndkhen.setText(kt.getNDKhenThuong());
        khenthuong_diemcong.setText(String.valueOf(kt.getDiemCong()));
        khenthuong_masv.disable();
    }//GEN-LAST:event_table_khenthuongMouseClicked

//---------------------
//Th??m,t??m ki???m,x??a KyLuat
    // xo?? tr???ng K??? lu???t
    public void xoaTrangKyLuat() {
        kyluat_masv.setText("");
        kyluat_tensv.setText("");
        kyluat_noidung.setText("");
        kyluat_diemtru.setText("");
        kyluat_masv.requestFocus();
        kyluat_masv.enable();
    }
    private void timkiem_kyluatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timkiem_kyluatActionPerformed
        // TODO add your handling code here:
        if (kyLuatController.searchKyLuat(kyluat_masv.getText()).size() > 0) {
            table_kyluat.setModel(new KyLuatCustomTable(kyLuatController.searchKyLuat(kyluat_masv.getText())));
        }
    }//GEN-LAST:event_timkiem_kyluatActionPerformed

    private void themmoi_kyluatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themmoi_kyluatActionPerformed
        // TODO add your handling code here:
        KyLuat kl = new KyLuat();
        kl.setMaSV(kyluat_masv.getText());
        kl.setTenSV(kyluat_tensv.getText());
        kl.setNDKyLuat(kyluat_noidung.getText());
        kl.setDiemTru(Float.parseFloat(kyluat_diemtru.getText()));
        kyLuatController.addKyLuat(kl);
        loadTable();
        xoaTrangKyLuat();
    }//GEN-LAST:event_themmoi_kyluatActionPerformed

    private void xoa_kyluatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoa_kyluatActionPerformed
        // TODO add your handling code here:
        int ret = JOptionPane.showConfirmDialog(null,
                "b???n ch???c ch???n mu???n xo???", "Tho??t",
                JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            kyLuatController.deleteKyLuat(kyluat_masv.getText());
            loadTable();
            xoaTrangKyLuat();
        }
    }//GEN-LAST:event_xoa_kyluatActionPerformed

    private void table_kyluatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_kyluatMouseClicked
        // TODO add your handling code here:
        selectedRow = table_kyluat.getSelectedRow();// TODO add your handling code here:
        KyLuat kl = listkyluat.get(selectedRow);
        kyluat_masv.setText(kl.getMaSV());
        kyluat_tensv.setText(kl.getTenSV());
        kyluat_noidung.setText(kl.getNDKyLuat());
        kyluat_diemtru.setText(String.valueOf(kl.getDiemTru()));
        kyluat_masv.disable();
    }//GEN-LAST:event_table_kyluatMouseClicked

//---------------------
//T??m ki???m,x??a HoatDongCT_XH
    private void timkiem_chinhtriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timkiem_chinhtriActionPerformed
        // TODO add your handling code here:
        if (hoatDongCT_XHController.searchHoatDongCT_XH(chinhtri_masv.getText()).size() > 0) {
            table_chinhtri.setModel(new HoatDongCT_XHCustomTable(hoatDongCT_XHController.searchHoatDongCT_XH(chinhtri_masv.getText())));
        }
    }//GEN-LAST:event_timkiem_chinhtriActionPerformed

    private void sua_chinhtriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sua_chinhtriActionPerformed
        // TODO add your handling code here:
        HoatDongCT_XH ctxh = new HoatDongCT_XH();
        ctxh.setMaSV(chinhtri_masv.getText());
        ctxh.setTenSV(chinhtri_tensv.getText());
        ctxh.setNDHoatDong(chinhtri_ndung.getSelectedItem().toString());
        ctxh.setDiemCong(Float.parseFloat(chinhtri_diemcong.getText()));
        hoatDongCT_XHController.updateHoatDongCT_XH(ctxh);
        loadTable();
        xoaTrangHoatDongCT_XH();
    }//GEN-LAST:event_sua_chinhtriActionPerformed

    private void xoa_chinhtriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoa_chinhtriActionPerformed
        // TODO add your handling code here:
        int ret = JOptionPane.showConfirmDialog(null,
                "b???n ch???c ch???n mu???n xo???", "Tho??t",
                JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            hoatDongCT_XHController.deleteHoatDongCT_XH(chinhtri_masv.getText());
            loadTable();
            xoaTrangHoatDongCT_XH();
        }
    }//GEN-LAST:event_xoa_chinhtriActionPerformed

    private void table_chinhtriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_chinhtriMouseClicked
        // TODO add your handling code here:
        selectedRow = table_chinhtri.getSelectedRow();// TODO add your handling code here:
        HoatDongCT_XH ctxh = listHoatDongCT_XH.get(selectedRow);
        chinhtri_masv.setText(ctxh.getMaSV());
        chinhtri_tensv.setText(ctxh.getTenSV());
        chinhtri_ndung.setSelectedItem(ctxh.getNDHoatDong() + "");
        chinhtri_diemcong.setText(String.valueOf(ctxh.getDiemCong()));
        chinhtri_masv.disable();
    }//GEN-LAST:event_table_chinhtriMouseClicked

//---------------------
//T??m ki???m,x??a HoatDongDoan_Hoi
    private void timkiem_doanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timkiem_doanActionPerformed
        // TODO add your handling code here:
        if (hoatDongDoan_HoiController.searchHoatDongDoan_Hoi(doan_masv.getText()).size() > 0) {
            table_doan.setModel(new HoatDongDoan_HoiCustomTable(hoatDongDoan_HoiController.searchHoatDongDoan_Hoi(doan_masv.getText())));
        }
    }//GEN-LAST:event_timkiem_doanActionPerformed

    private void sua_doanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sua_doanActionPerformed
        // TODO add your handling code here:
        HoatDongDoan_Hoi dh = new HoatDongDoan_Hoi();
        dh.setMaSV(doan_masv.getText());
        dh.setTenSV(doan_tensv.getText());
        dh.setNDHoatDong(doan_ndung.getSelectedItem().toString());
        dh.setDiemCong(Float.parseFloat(doan_diemcong.getText()));
        hoatDongDoan_HoiController.updateHoatDongDoan_Hoi(dh);
        loadTable();
        xoaTrangHoatDongDoan_Hoi();
    }//GEN-LAST:event_sua_doanActionPerformed

    private void xoa_doanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoa_doanActionPerformed
        // TODO add your handling code here:
        int ret = JOptionPane.showConfirmDialog(null,
                "b???n ch???c ch???n mu???n xo???", "Tho??t",
                JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            hoatDongDoan_HoiController.deleteHoatDongDoan_Hoi(doan_masv.getText());
            loadTable();
            xoaTrangHoatDongDoan_Hoi();
        }
    }//GEN-LAST:event_xoa_doanActionPerformed

    private void table_doanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_doanMouseClicked
        // TODO add your handling code here:
        selectedRow = table_doan.getSelectedRow();// TODO add your handling code here:
        HoatDongDoan_Hoi dh = listHoatDongDoan_Hoi.get(selectedRow);
        doan_masv.setText(dh.getMaSV());
        doan_tensv.setText(dh.getTenSV());
        doan_ndung.setSelectedItem(dh.getNDHoatDong() + "");
        doan_diemcong.setText(String.valueOf(dh.getDiemCong()));
        doan_masv.disable();
    }//GEN-LAST:event_table_doanMouseClicked

//---------------------
//T??m ki???m,x??a MienGiamHocPhi  
    public void xoaTrangMienGiam() {
        miengiam_khoa.setSelectedItem(null);
        miengiam_nganh.setSelectedItem(null);
        miengiam_lop.setSelectedItem(null);
    }
    private void timkiem_miengiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timkiem_miengiamActionPerformed
        // TODO add your handling code here:
        if (miengiam_khoa.getSelectedItem() == null || miengiam_nganh.getSelectedItem() == null || miengiam_lop.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Vui l??ng ch???n ????? Khoa, Ng??nh, L???p!");
        } else {
            String Khoa = miengiam_khoa.getSelectedItem().toString();
            String Nganh = miengiam_nganh.getSelectedItem().toString();
            String Lop = miengiam_lop.getSelectedItem().toString();
            table_miengiam.setModel(new MienGiamCustomTable(MienGiamController.searchMienGiam(Khoa, Nganh, Lop)));
        }
//        if (MienGiamController.searchMienGiam(miengiam_khoa.getSelectedItem().toString(),miengiam_nganh.getSelectedItem().toString(),miengiam_lop.getSelectedItem().toString()).size() > 0) {
//            table_miengiam.setModel(new MienGiamCustomTable(MienGiamController.searchMienGiam(miengiam_khoa.getSelectedItem().toString(),miengiam_nganh.getSelectedItem().toString(),miengiam_lop.getSelectedItem().toString())));
//        }
    }//GEN-LAST:event_timkiem_miengiamActionPerformed

    private void table_miengiamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_miengiamMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_table_miengiamMouseClicked
//Button Quay l???i
    private void quaylai_canhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quaylai_canhanActionPerformed
        xoaTrangCaNhan();
    }//GEN-LAST:event_quaylai_canhanActionPerformed

    private void quaylai_gdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quaylai_gdActionPerformed
        xoaTrangGiaDinh();
    }//GEN-LAST:event_quaylai_gdActionPerformed

    private void quaylai_khenthuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quaylai_khenthuongActionPerformed
        xoaTrangKhenThuong();
    }//GEN-LAST:event_quaylai_khenthuongActionPerformed

    private void quaylai_kyluatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quaylai_kyluatActionPerformed
        // TODO add your handling code here:
        xoaTrangKyLuat();
    }//GEN-LAST:event_quaylai_kyluatActionPerformed

    private void quaylai_chinhtriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quaylai_chinhtriActionPerformed
        // TODO add your handling code here:
        xoaTrangHoatDongCT_XH();
    }//GEN-LAST:event_quaylai_chinhtriActionPerformed

    private void quaylai_doanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quaylai_doanActionPerformed
        // TODO add your handling code here:
        xoaTrangHoatDongDoan_Hoi();
    }//GEN-LAST:event_quaylai_doanActionPerformed
//-----------------------------------------------------------

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane J;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField canhan_cmnd;
    private javax.swing.JTextField canhan_email;
    private javax.swing.JComboBox<String> canhan_khoa_cbb;
    private javax.swing.JComboBox<String> canhan_lop_cbb;
    private javax.swing.JTextField canhan_masv;
    private javax.swing.JRadioButton canhan_namradio;
    private javax.swing.JComboBox<String> canhan_nganh_cbb;
    private javax.swing.JTextField canhan_ngaysinh;
    private javax.swing.JRadioButton canhan_nuradio;
    private javax.swing.JTextField canhan_quequan;
    private javax.swing.JTextField canhan_sdt;
    private javax.swing.JTextField canhan_tensv;
    private javax.swing.JTextField chinhtri_diemcong;
    private javax.swing.JTextField chinhtri_masv;
    private javax.swing.JComboBox chinhtri_ndung;
    private javax.swing.JTextField chinhtri_tensv;
    private javax.swing.JTextField doan_diemcong;
    private javax.swing.JTextField doan_masv;
    private javax.swing.JComboBox doan_ndung;
    private javax.swing.JTextField doan_tensv;
    private javax.swing.JTextField gd_anhemtrai;
    private javax.swing.JTextField gd_chiemgai;
    private javax.swing.JComboBox<String> gd_doituongchinhsach;
    private javax.swing.JTextField gd_hotenbo;
    private javax.swing.JTextField gd_hotenme;
    private javax.swing.JComboBox<String> gd_khuvuco;
    private javax.swing.JTextField gd_masv;
    private javax.swing.JTextField gd_sdtlienhe;
    private javax.swing.JTextField gd_tensv;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextField khenthuong_diemcong;
    private javax.swing.JTextField khenthuong_masv;
    private javax.swing.JTextField khenthuong_ndkhen;
    private javax.swing.JTextField khenthuong_tensv;
    private javax.swing.JTextField kyluat_diemtru;
    private javax.swing.JTextField kyluat_masv;
    private javax.swing.JTextField kyluat_noidung;
    private javax.swing.JTextField kyluat_tensv;
    private javax.swing.JComboBox<String> miengiam_khoa;
    private javax.swing.JComboBox<String> miengiam_lop;
    private javax.swing.JComboBox<String> miengiam_nganh;
    private javax.swing.JButton quaylai_canhan;
    private javax.swing.JButton quaylai_chinhtri;
    private javax.swing.JButton quaylai_doan;
    private javax.swing.JButton quaylai_gd;
    private javax.swing.JButton quaylai_khenthuong;
    private javax.swing.JButton quaylai_kyluat;
    private javax.swing.JButton sua_canhan;
    private javax.swing.JButton sua_chinhtri;
    private javax.swing.JButton sua_doan;
    private javax.swing.JButton sua_gd;
    private javax.swing.JButton sua_khenthuong;
    private javax.swing.JButton sua_kyluat;
    private javax.swing.JTable table_canhan;
    private javax.swing.JTable table_chinhtri;
    private javax.swing.JTable table_doan;
    private javax.swing.JTable table_giadinh;
    private javax.swing.JTable table_khenthuong;
    private javax.swing.JTable table_kyluat;
    private javax.swing.JTable table_miengiam;
    private javax.swing.JButton themmoi_canhan;
    private javax.swing.JButton themmoi_chinhtri;
    private javax.swing.JButton themmoi_doan;
    private javax.swing.JButton themmoi_gd;
    private javax.swing.JButton themmoi_khenthuong;
    private javax.swing.JButton themmoi_kyluat;
    private javax.swing.JButton timkiem_canhan;
    private javax.swing.JButton timkiem_chinhtri;
    private javax.swing.JButton timkiem_doan;
    private javax.swing.JButton timkiem_gd;
    private javax.swing.JButton timkiem_khenthuong;
    private javax.swing.JButton timkiem_kyluat;
    private javax.swing.JButton timkiem_miengiam;
    private javax.swing.JButton xoa_canhan;
    private javax.swing.JButton xoa_chinhtri;
    private javax.swing.JButton xoa_doan;
    private javax.swing.JButton xoa_gd;
    private javax.swing.JButton xoa_khenthuong;
    private javax.swing.JButton xoa_kyluat;
    // End of variables declaration//GEN-END:variables
}
