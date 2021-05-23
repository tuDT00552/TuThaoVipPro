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
public class TTGiaDinh extends SinhVien{
    String HoTenBo, HoTenMe, AnhEmTrai, ChiEmGai, DoiTuongChinhSach, KhuVucO, SDTLienHe;

    public TTGiaDinh() {
    }

    public TTGiaDinh(String MaSV) {
        super(MaSV);
    }
    
    public TTGiaDinh(String HoTenBo, String HoTenMe, String AnhEmTrai, String ChiEmGai, String DoiTuongChinhSach, String KhuVucO, String SDTLienHe) {
        this.HoTenBo = HoTenBo;
        this.HoTenMe = HoTenMe;
        this.AnhEmTrai = AnhEmTrai;
        this.ChiEmGai = ChiEmGai;
        this.DoiTuongChinhSach = DoiTuongChinhSach;
        this.KhuVucO = KhuVucO;
        this.SDTLienHe = SDTLienHe;
    }

    public TTGiaDinh(String MaSV, String TenSV, String HoTenBo, String HoTenMe, String AnhEmTrai, String ChiEmGai, String DoiTuongChinhSach, String KhuVucO, String SDTLienHe) {
        super(MaSV, TenSV);
        this.HoTenBo = HoTenBo;
        this.HoTenMe = HoTenMe;
        this.AnhEmTrai = AnhEmTrai;
        this.ChiEmGai = ChiEmGai;
        this.DoiTuongChinhSach = DoiTuongChinhSach;
        this.KhuVucO = KhuVucO;
        this.SDTLienHe = SDTLienHe;
    }

    public String getHoTenBo() {
        return HoTenBo;
    }

    public void setHoTenBo(String HoTenBo) {
        this.HoTenBo = HoTenBo;
    }

    public String getHoTenMe() {
        return HoTenMe;
    }

    public void setHoTenMe(String HoTenMe) {
        this.HoTenMe = HoTenMe;
    }

    public String getAnhEmTrai() {
        return AnhEmTrai;
    }

    public void setAnhEmTrai(String AnhEmTrai) {
        this.AnhEmTrai = AnhEmTrai;
    }

    public String getChiEmGai() {
        return ChiEmGai;
    }

    public void setChiEmGai(String ChiEmGai) {
        this.ChiEmGai = ChiEmGai;
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

    public String getSDTLienHe() {
        return SDTLienHe;
    }

    public void setSDTLienHe(String SDTLienHe) {
        this.SDTLienHe = SDTLienHe;
    }

    @Override
    public String toString() {
        return "TTGiaDinh{" + "HoTenBo=" + HoTenBo + ", HoTenMe=" + HoTenMe + ", AnhEmTrai=" + AnhEmTrai + ", ChiEmGai=" + ChiEmGai + ", DoiTuongChinhSach=" + DoiTuongChinhSach + ", KhuVucO=" + KhuVucO + ", SDTLienHe=" + SDTLienHe + '}';
    }
    
}
