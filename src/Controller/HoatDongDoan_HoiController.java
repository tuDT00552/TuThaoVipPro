/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.ConnectDB;
import Model.HoatDongDoan_Hoi;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author DT
 */
public class HoatDongDoan_HoiController {
    ConnectDB conn = new ConnectDB();
    public ArrayList<HoatDongDoan_Hoi> getListHoatDongDoan_Hoi() {
        try {
            ArrayList<HoatDongDoan_Hoi> list = conn.GetDataHoatDongDoan_Hoi();
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(HoatDongDoan_HoiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void addHoatDongDoan_Hoi(HoatDongDoan_Hoi hoatDongDoan_Hoi) {
        try {
            int count = conn.checkExistsTTCaNhan(hoatDongDoan_Hoi.getMaSV());
            if(count == 1){
            conn.addHoatDongDoan_Hoi(hoatDongDoan_Hoi);
            }
            else JOptionPane.showMessageDialog(null, "Sinh viên không tồn tại");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void updateHoatDongDoan_Hoi(HoatDongDoan_Hoi hoatDongDoan_Hoi) {
        try {
            conn.updateHoatDongDoan_Hoi(hoatDongDoan_Hoi);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    public ArrayList<HoatDongDoan_Hoi> searchHoatDongDoan_Hoi(String MaSV) {
        try {
            ArrayList<HoatDongDoan_Hoi> list = conn.searchHoatDongDoan_Hoi(MaSV);
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Không tồn tại sinh viên này");
            }
            return list;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return null;
    }
    public void deleteHoatDongDoan_Hoi(String MaSV){
        try {
            conn.deleteHoatDongDoan_Hoi(MaSV);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }      
}
