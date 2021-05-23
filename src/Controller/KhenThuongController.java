/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.ConnectDB;
import Model.KhenThuong;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author DT
 */
public class KhenThuongController {

    ConnectDB conn = new ConnectDB();

    public ArrayList<KhenThuong> getListKhenThuong() {
        try {
            ArrayList<KhenThuong> list = conn.GetDataKhenThuong();
            return list;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return null;
    }

    public void addKhenThuong(KhenThuong khenthuong) {
        try {
            int count = conn.checkExistsTTCaNhan(khenthuong.getMaSV());
            if (count == 1) {
                conn.addKhenThuong(khenthuong);
            } else {
                JOptionPane.showMessageDialog(null, "Sinh viên không tồn tại");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void updateKhenThuong(KhenThuong khenthuong) {
        try {
            conn.updateKhenThuong(khenthuong);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public ArrayList<KhenThuong> searchKhenThuong(String MaSV) {
        try {
            ArrayList<KhenThuong> list = conn.timKhenThuong(MaSV);
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Không tồn tại sinh viên này");
            }
            return list;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return null;
    }

    public void xoaKhenThuong(String MaSV) {
        try {
            conn.xoaKhenThuong(MaSV);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
