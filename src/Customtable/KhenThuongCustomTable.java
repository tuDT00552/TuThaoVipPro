/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Customtable;

import Model.KhenThuong;
import Model.TTCaNhan;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author H
 */
public class KhenThuongCustomTable extends AbstractTableModel{
    private String name[]={"Mã sinh viên","Tên sinh viên","Nội dung khen thưởng","Điểm cộng"};
    private Class classes[]={String.class,String.class,String.class,float.class};
    ArrayList<KhenThuong> dskhenthuong = new ArrayList<KhenThuong>();

    public KhenThuongCustomTable(ArrayList<KhenThuong> listkhenthuong) {
        this.dskhenthuong = listkhenthuong;
    }

    @Override
    public int getRowCount() {
        return dskhenthuong.size();
    }

    @Override
    public int getColumnCount() {
        return name.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0: return dskhenthuong.get(rowIndex).getMaSV();
            case 1: return dskhenthuong.get(rowIndex).getTenSV();
            case 2: return dskhenthuong.get(rowIndex).getNDKhenThuong();
            case 3: return dskhenthuong.get(rowIndex).getDiemCong();
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
