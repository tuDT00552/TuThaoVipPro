/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Customtable;

import Model.HoatDongCT_XH;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author DT
 */
public class HoatDongCT_XHCustomTable extends AbstractTableModel{
    private String name[]={"Mã sinh viên","Tên sinh viên","Nội dung hoạt động","Điểm cộng"};
    private Class classes[]={String.class,String.class,String.class,float.class};
    ArrayList<HoatDongCT_XH> dsHoatDongCT_XH = new ArrayList<HoatDongCT_XH>();

    public HoatDongCT_XHCustomTable(ArrayList<HoatDongCT_XH> listHoatDongCT_XH) {
        this.dsHoatDongCT_XH = listHoatDongCT_XH;
    }

    @Override
    public int getRowCount() {
        return dsHoatDongCT_XH.size();
    }

    @Override
    public int getColumnCount() {
        return name.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0: return dsHoatDongCT_XH.get(rowIndex).getMaSV();
            case 1: return dsHoatDongCT_XH.get(rowIndex).getTenSV();
            case 2: return dsHoatDongCT_XH.get(rowIndex).getNDHoatDong();
            case 3: return dsHoatDongCT_XH.get(rowIndex).getDiemCong();
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
