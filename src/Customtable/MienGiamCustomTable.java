/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Customtable;

import Model.MienGiam;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Administrator
 */
public class MienGiamCustomTable  extends AbstractTableModel{
    private String name[]={"Mã sinh viên","Tên sinh viên","Đối tượng chính sách", "Khu vực ở","Mức miễn giảm"};
    private Class classes[]={String.class,String.class,String.class,String.class,String.class};
    ArrayList<MienGiam> dsmiengiam = new ArrayList<MienGiam>();

    public MienGiamCustomTable(ArrayList<MienGiam> listMienGiam) {
        this.dsmiengiam = listMienGiam;
    }
    @Override
    public int getRowCount() {
        return dsmiengiam.size();
    }

    @Override
    public int getColumnCount() {
        return name.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0: return dsmiengiam.get(rowIndex).getMaSV();
            case 1: return dsmiengiam.get(rowIndex).getTenSV();
            case 2: return dsmiengiam.get(rowIndex).getDoiTuongChinhSach();
            case 3: return dsmiengiam.get(rowIndex).getKhuVucO();
            case 4: return dsmiengiam.get(rowIndex).getMucMienGiam();
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
