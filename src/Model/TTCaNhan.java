/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author H
 */
public class TTCaNhan extends SinhVien{
    String Khoa, GioiTinh, Nganh, Lop, NgaySinh, QueQuan, CMND, SDT, Email;

    public TTCaNhan() {
    }

    public TTCaNhan(String MaSV) {
        super(MaSV);
    }
    
    
    public TTCaNhan(String Khoa, String GioiTinh, String Nganh, String Lop, String NgaySinh, String QueQuan, String CMND, String SDT, String Email) {
        this.Khoa = Khoa;
        this.GioiTinh = GioiTinh;
        this.Nganh = Nganh;
        this.Lop = Lop;
        this.NgaySinh = NgaySinh;
        this.QueQuan = QueQuan;
        this.CMND = CMND;
        this.SDT = SDT;
        this.Email = Email;
    }

    public TTCaNhan(String MaSV, String TenSV, String Khoa, String GioiTinh, String Nganh, String Lop, String NgaySinh, String QueQuan, String CMND, String SDT, String Email) {
        super(MaSV, TenSV);
        this.Khoa = Khoa;
        this.GioiTinh = GioiTinh;
        this.Nganh = Nganh;
        this.Lop = Lop;
        this.NgaySinh = NgaySinh;
        this.QueQuan = QueQuan;
        this.CMND = CMND;
        this.SDT = SDT;
        this.Email = Email;
    }

    

    public String getKhoa() {
        return Khoa;
    }

    public void setKhoa(String Khoa) {
        this.Khoa = Khoa;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getNganh() {
        return Nganh;
    }

    public void setNganh(String Nganh) {
        this.Nganh = Nganh;
    }

    public String getLop() {
        return Lop;
    }

    public void setLop(String Lop) {
        this.Lop = Lop;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getQueQuan() {
        return QueQuan;
    }

    public void setQueQuan(String QueQuan) {
        this.QueQuan = QueQuan;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    @Override
    public String toString() {
        return "TTCaNhan{" + "Khoa=" + Khoa + ", GioiTinh=" + GioiTinh + ", Nganh=" + Nganh + ", Lop=" + Lop + ", NgaySinh=" + NgaySinh + ", QueQuan=" + QueQuan + ", CMND=" + CMND + ", SDT=" + SDT + ", Email=" + Email + '}';
    }
    
}
