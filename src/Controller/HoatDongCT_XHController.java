/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.ConnectDB;
import Model.HoatDongCT_XH;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author DT
 */
public class HoatDongCT_XHController {
    ConnectDB conn = new ConnectDB();
    public ArrayList<HoatDongCT_XH> getListHoatDongCT_XH() {
        try {
            ArrayList<HoatDongCT_XH> list = conn.GetDataHoatDongCT_XH();
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(HoatDongCT_XHController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void addHoatDongCT_XH(HoatDongCT_XH hoatDongCT_XH) {
        try {
            int count = conn.checkExistsTTCaNhan(hoatDongCT_XH.getMaSV());
            if(count == 1){
            conn.addHoatDongCT_XH(hoatDongCT_XH);
            }
            else JOptionPane.showMessageDialog(null, "Sinh viên không tồn tại");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void updateHoatDongCT_XH(HoatDongCT_XH hoatDongCT_XH) {
        try {
            conn.updateHoatDongCT_XH(hoatDongCT_XH);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    public ArrayList<HoatDongCT_XH> searchHoatDongCT_XH(String MaSV) {
        try {
            ArrayList<HoatDongCT_XH> list = conn.searchHoatDongCT_XH(MaSV);
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Không tồn tại sinh viên này");
            }
            return list;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return null;
    }
    public void deleteHoatDongCT_XH(String MaSV){
        try {
            conn.deleteHoatDongCT_XH(MaSV);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }    
}
