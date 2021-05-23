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
public class SinhVien {
    private String MaSV;
    private String TenSV;

    public SinhVien() {
    }

    public SinhVien(String MaSV) {
        this.MaSV = MaSV;
    }
    
    
    public SinhVien(String MaSV, String TenSV) {
        this.MaSV = MaSV;
        this.TenSV = TenSV;
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String MaSV) {
        this.MaSV = MaSV;
    }

    public String getTenSV() {
        return TenSV;
    }

    public void setTenSV(String TenSV) {
        this.TenSV = TenSV;
    }

    
    
}
