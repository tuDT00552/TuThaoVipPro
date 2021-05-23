/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Customtable;

import Model.TTCaNhan;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author DT
 */
public class TTCaNhanCustomTable extends AbstractTableModel{
    private String name[]={"Khoa","Ngành","Lớp","Mã sinh viên","Tên sinh viên","Giới tính","Ngày sinh","Quê quán","Số CMND/CCCD","SDT","Email"};
    private Class classes[]={String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class};
    ArrayList<TTCaNhan> dscanhan = new ArrayList<TTCaNhan>();

    public TTCaNhanCustomTable(ArrayList<TTCaNhan> listcanhan) {
        this.dscanhan = listcanhan;
    }
    
    
    @Override
    public int getRowCount() {
            return dscanhan.size();
    }

    @Override
    public int getColumnCount() {
         return name.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0: return dscanhan.get(rowIndex).getKhoa();
            case 1: return dscanhan.get(rowIndex).getNganh();
            case 2: return dscanhan.get(rowIndex).getLop();
            case 3: return dscanhan.get(rowIndex).getMaSV();
            case 4: return dscanhan.get(rowIndex).getTenSV();
            case 5: return dscanhan.get(rowIndex).getGioiTinh();
            case 6: return dscanhan.get(rowIndex).getNgaySinh();
            case 7: return dscanhan.get(rowIndex).getQueQuan();
            case 8: return dscanhan.get(rowIndex).getCMND();
            case 9: return dscanhan.get(rowIndex).getSDT();
            case 10: return dscanhan.get(rowIndex).getEmail();
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
