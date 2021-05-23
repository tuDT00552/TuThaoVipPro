/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.ConnectDB;
import Model.TTGiaDinh;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author DT
 */
public class TTGiaDinhController {
    ConnectDB conn = new ConnectDB();
    
    public ArrayList<TTGiaDinh> getListTTGiaDinh(){
        try{
            ArrayList<TTGiaDinh> list = conn.GetDataTTGiaDinh();
            return list;
        }catch(SQLException ex){
            Logger.getLogger(TTGiaDinhController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void addTTGiaDinh(TTGiaDinh ttgd) {
        try {
            int count = conn.checkExistsTTCaNhan(ttgd.getMaSV());
            if (count == 1) {
                conn.addTTGiaDinh(ttgd);
            }
            else JOptionPane.showMessageDialog(null, "Sinh viên không tồn tại");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    } 
    
    public void updateTTGiaDinh(TTGiaDinh ttgd) {
        try {
            conn.updateTTGiaDinh(ttgd);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    public void deleteTTGiaDinh(String MaSV) {
        try {
            conn.deleteTTGiaDinh(MaSV);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    public ArrayList<TTGiaDinh> searchTTGiaDinh(String MaSV) {
        try {
            ArrayList<TTGiaDinh> list = conn.SearchTTGiaDinh(MaSV);
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Không tồn tại sinh viên này");
            }
            return list;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return null;
    }    
}
