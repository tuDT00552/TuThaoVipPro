/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Customtable;

import Model.HoatDongDoan_Hoi;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author DT
 */
public class HoatDongDoan_HoiCustomTable extends AbstractTableModel{
    private String name[]={"Mã sinh viên","Tên sinh viên","Nội dung hoạt động","Điểm cộng"};
    private Class classes[]={String.class,String.class,String.class,float.class};
    ArrayList<HoatDongDoan_Hoi> dsHoatDongDoan_Hoi = new ArrayList<HoatDongDoan_Hoi>();

    public HoatDongDoan_HoiCustomTable(ArrayList<HoatDongDoan_Hoi> listHoatDongDoan_Hoi) {
        this.dsHoatDongDoan_Hoi = listHoatDongDoan_Hoi;
    }

    @Override
    public int getRowCount() {
        return dsHoatDongDoan_Hoi.size();
    }

    @Override
    public int getColumnCount() {
        return name.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0: return dsHoatDongDoan_Hoi.get(rowIndex).getMaSV();
            case 1: return dsHoatDongDoan_Hoi.get(rowIndex).getTenSV();
            case 2: return dsHoatDongDoan_Hoi.get(rowIndex).getNDHoatDong();
            case 3: return dsHoatDongDoan_Hoi.get(rowIndex).getDiemCong();
            default:return null;
        }
    }
    @Override
    public Class getColumnClass(int columnIndex) {
        return classes[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return name[column];
    }        
}
