/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.ConnectDB;
import Model.MienGiam;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class MienGiamController {

    ConnectDB conn = new ConnectDB();

    public ArrayList<MienGiam> getListMienGiam() {
        try {
            ArrayList<MienGiam> list = conn.GetDataMienGiam();
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(KyLuatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<MienGiam> searchMienGiam(String Khoa, String Nganh, String Lop) {
        try {
            ArrayList<MienGiam> list = conn.searchMienGiam(Khoa, Nganh, Lop);
            return list;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return null;
    }
}
