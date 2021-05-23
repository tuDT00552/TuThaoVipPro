/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.ConnectDB;
import Model.TTCaNhan;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author DT
 */
public class TTCaNhanController {
    ConnectDB conn = new ConnectDB();
    
    public ArrayList<TTCaNhan> getListTTCaNhan(){
        try{
            ArrayList<TTCaNhan> list = conn.GetDataTTCaNhan();
            return list;
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return null;
    }
    
    public void addTTCaNhan(TTCaNhan ttcn) {
        try {
            conn.addTTCaNhan(ttcn);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    } 
    
    public void updateTTCaNhan(TTCaNhan tTCaNhan) {
        try {
            conn.updateTTCaNhan(tTCaNhan);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    public void deleteTTCaNhan(String masvSearch) {
        try {
            conn.deleteTTCaNhan(masvSearch);
        } catch (Exception ex) {
            Logger.getLogger(TTCaNhanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<TTCaNhan> searchTTCaNhan(String masvSearch) {
        try {
            ArrayList<TTCaNhan> list = conn.SearchTTCaNhan(masvSearch);
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
