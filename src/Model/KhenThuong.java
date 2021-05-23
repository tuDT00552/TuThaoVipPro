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
public class KhenThuong extends SinhVien {
    String NDKhenThuong ;
    float DiemCong;

    public KhenThuong() {
    }

    public KhenThuong(String NDKhenThuong, Float DiemCong) {
        this.NDKhenThuong = NDKhenThuong;
        this.DiemCong = DiemCong;
    }

    public KhenThuong( String MaSV, String TenSV, String NDKhenThuong, Float DiemCong) {
        super(MaSV, TenSV);
        this.NDKhenThuong = NDKhenThuong;
        this.DiemCong = DiemCong;
    }

    public String getNDKhenThuong() {
        return NDKhenThuong;
    }

    public void setNDKhenThuong(String NDKhenThuong) {
        this.NDKhenThuong = NDKhenThuong;
    }

    public float getDiemCong() {
        return DiemCong;
    }

    public void setDiemCong(float DiemCong) {
        this.DiemCong = DiemCong;
    }

    public KhenThuong(String MaSV) {
        super(MaSV);
    }
    
    @Override
    public String toString() {
        return "KhenThuong{" + "NDKhenThuong=" + NDKhenThuong + ", DiemCong=" + DiemCong + '}';
    }
    
}
