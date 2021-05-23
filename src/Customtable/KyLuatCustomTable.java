/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Customtable;

import Model.KyLuat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author DT
 */
public class KyLuatCustomTable extends AbstractTableModel{
    private String name[]={"Mã sinh viên","Tên sinh viên","Nội dung kỷ luật","Điểm trừ"};
    private Class classes[]={String.class,String.class,String.class,float.class};
    ArrayList<KyLuat> dskyluat = new ArrayList<KyLuat>();

    public KyLuatCustomTable(ArrayList<KyLuat> listkyluat) {
        this.dskyluat = listkyluat;
    }

    @Override
    public int getRowCount() {
        return dskyluat.size();
    }

    @Override
    public int getColumnCount() {
        return name.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0: return dskyluat.get(rowIndex).getMaSV();
            case 1: return dskyluat.get(rowIndex).getTenSV();
            case 2: return dskyluat.get(rowIndex).getNDKyLuat();
            case 3: return dskyluat.get(rowIndex).getDiemTru();
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
