/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Customtable;

import Model.TTGiaDinh;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author DT
 */
public class TTGiaDinhCustomtable extends AbstractTableModel{
    private String name[]={"Mã sinh viên","Tên sinh viên","Họ tên bố","Họ tên mẹ","SĐT liên hệ","Anh/Em trai","Chị/Em gái","Đối tượng chính sách","Khu vực ở"};
    private Class classes[]={String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class};    
    ArrayList<TTGiaDinh> dsgiadinh = new ArrayList<TTGiaDinh>();
    
    public TTGiaDinhCustomtable(ArrayList<TTGiaDinh> listgiadinh){
        this.dsgiadinh = listgiadinh;
    }
    
    @Override
    public int getRowCount() {
         return dsgiadinh.size();
    }

    @Override
    public int getColumnCount() {
        return name.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
         switch(columnIndex){
            case 0: return dsgiadinh.get(rowIndex).getMaSV();
            case 1: return dsgiadinh.get(rowIndex).getTenSV();
            case 2: return dsgiadinh.get(rowIndex).getHoTenBo();
            case 3: return dsgiadinh.get(rowIndex).getHoTenMe();
            case 4: return dsgiadinh.get(rowIndex).getSDTLienHe();
            case 5: return dsgiadinh.get(rowIndex).getAnhEmTrai();
            case 6: return dsgiadinh.get(rowIndex).getChiEmGai();
            case 7: return dsgiadinh.get(rowIndex).getDoiTuongChinhSach();
            case 8: return dsgiadinh.get(rowIndex).getKhuVucO();
            default:return null;
        }      
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return classes[columnIndex]; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getColumnName(int column) {
        return name[column]; //To change body of generated methods, choose Tools | Templates.
    }
        
    
}
