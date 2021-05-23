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
public class HoatDongDoan_Hoi extends SinhVien {
    String NDHoatDong;
    float DiemCong;

    public HoatDongDoan_Hoi() {
    }

    public HoatDongDoan_Hoi(String NDHoatDong, float DiemCong) {
        this.NDHoatDong = NDHoatDong;
        this.DiemCong = DiemCong;
    }

    public HoatDongDoan_Hoi(String MaSV, String TenSV, String NDHoatDong, float DiemCong) {
        super(MaSV, TenSV);
        this.NDHoatDong = NDHoatDong;
        this.DiemCong = DiemCong;
    }

    public String getNDHoatDong() {
        return NDHoatDong;
    }

    public void setNDHoatDong(String NDHoatDong) {
        this.NDHoatDong = NDHoatDong;
    }

    public float getDiemCong() {
        return DiemCong;
    }

    public void setDiemCong(float DiemCong) {
        this.DiemCong = DiemCong;
    }

    public HoatDongDoan_Hoi(String MaSV) {
        super(MaSV);
    }
    
    @Override
    public String toString() {
        return "HoatDongDoan_Hoi{" + "NDHoatDong=" + NDHoatDong + ", DiemCong=" + DiemCong + '}';
    }
    
}
