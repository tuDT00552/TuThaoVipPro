/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.ConnectDB;
import Model.KyLuat;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author DT
 */
public class KyLuatController {
    ConnectDB conn = new ConnectDB();
    public ArrayList<KyLuat> getListKyLuat() {
        try {
            ArrayList<KyLuat> list = conn.GetDataKyLuat();
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(KyLuatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void addKyLuat(KyLuat kyluat) {
        try {
            int count = conn.checkExistsTTCaNhan(kyluat.getMaSV());
            if(count == 1){
            conn.addKyLuat(kyluat);
            }
            else JOptionPane.showMessageDialog(null, "Sinh viên không tồn tại");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void updateKyLuat(KyLuat kyLuat) {
        try {
            conn.updateKyLuat(kyLuat);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    public ArrayList<KyLuat> searchKyLuat(String MaSV) {
        try {
            ArrayList<KyLuat> list = conn.searchKyLuat(MaSV);
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Không tồn tại sinh viên này");
            }
            return list;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return null;
    }
    public void deleteKyLuat(String MaSV){
        try {
            conn.deleteKyLuat(MaSV);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }    
}
