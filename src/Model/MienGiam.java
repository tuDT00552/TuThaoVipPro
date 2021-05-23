/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Administrator
 */
public class MienGiam extends SinhVien{
    String DoiTuongChinhSach, KhuVucO, MucMienGiam;

    public MienGiam() {
    }

    public MienGiam(String DoiTuongChinhSach, String KhuVucO, String MucMienGiam) {
        this.DoiTuongChinhSach = DoiTuongChinhSach;
        this.KhuVucO = KhuVucO;
        this.MucMienGiam = MucMienGiam;
    }

    public MienGiam(String DoiTuongChinhSach, String KhuVucO, String MucMienGiam, String MaSV) {
        super(MaSV);
        this.DoiTuongChinhSach = DoiTuongChinhSach;
        this.KhuVucO = KhuVucO;
        this.MucMienGiam = MucMienGiam;
    }

    public MienGiam(String MaSV, String TenSV, String DoiTuongChinhSach, String KhuVucO, String MucMienGiam) {
        super(MaSV, TenSV);
        this.DoiTuongChinhSach = DoiTuongChinhSach;
        this.KhuVucO = KhuVucO;
        this.MucMienGiam = MucMienGiam;
    }

    public String getDoiTuongChinhSach() {
        return DoiTuongChinhSach;
    }

    public void setDoiTuongChinhSach(String DoiTuongChinhSach) {
        this.DoiTuongChinhSach = DoiTuongChinhSach;
    }

    public String getKhuVucO() {
        return KhuVucO;
    }

    public void setKhuVucO(String KhuVucO) {
        this.KhuVucO = KhuVucO;
    }

    public String getMucMienGiam() {
        return MucMienGiam;
    }

    public void setMucMienGiam(String MucMienGiam) {
        this.MucMienGiam = MucMienGiam;
    }

    @Override
    public String toString() {
        return "MienGiam{" + "DoiTuongChinhSach=" + DoiTuongChinhSach + ", KhuVucO=" + KhuVucO + ", MucMienGiam=" + MucMienGiam + '}';
    }
    
}
