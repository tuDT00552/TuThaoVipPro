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
public class KyLuat extends SinhVien {
    String NDKyLuat;
    float DiemTru ;

    public KyLuat() {
    }

    public KyLuat(String NDKyLuat, float DiemTru) {
        this.NDKyLuat = NDKyLuat;
        this.DiemTru = DiemTru;
    }

    public KyLuat(String MaSV, String TenSV, String NDKyLuat, float DiemTru) {
        super(MaSV, TenSV);
        this.NDKyLuat = NDKyLuat;
        this.DiemTru = DiemTru;
    }

    public String getNDKyLuat() {
        return NDKyLuat;
    }

    public void setNDKyLuat(String NDKyLuat) {
        this.NDKyLuat = NDKyLuat;
    }

    public float getDiemTru() {
        return DiemTru;
    }

    public void setDiemTru(float DiemTru) {
        this.DiemTru = DiemTru;
    }

    public KyLuat(String MaSV) {
        super(MaSV);
    }
    
    @Override
    public String toString() {
        return "KyLuat{" + "NDKyLuat=" + NDKyLuat + ", DiemTru=" + DiemTru + '}';
    }
    
}
