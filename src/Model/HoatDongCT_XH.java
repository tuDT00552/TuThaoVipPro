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
public class HoatDongCT_XH extends SinhVien {
    String NDHoatDong ;
    float DiemCong; 

    public HoatDongCT_XH() {
    }

    public HoatDongCT_XH(String MaSV) {
        super(MaSV);
    }
    
    public HoatDongCT_XH(String NDHoatDong, float DiemCong) {
        this.NDHoatDong = NDHoatDong;
        this.DiemCong = DiemCong;
    }

    public HoatDongCT_XH(String MaSV, String TenSV, String NDHoatDong, float DiemCong) {
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

    @Override
    public String toString() {
        return "HoatDongCT_XH{" + "NDHoatDong=" + NDHoatDong + ", DiemCong=" + DiemCong + '}';
    }
    
}
