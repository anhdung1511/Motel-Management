/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DBConnection.DBConnection;
import Model.MotelModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author nguye
 */
public class MotelController {
    private Connection conn;
    
    public MotelController() {
        this.conn = new DBConnection().getDBConnection();
    }
    
    public ArrayList<MotelModel> getListMotel() {
        ArrayList<MotelModel> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_motel";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                list.add(new MotelModel(
                        rs.getString(1),
                        rs.getString(2)
                ));
            }
            
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public String getIdMotel(String name) {
        String sql = "SELECT idMotel FROM tb_motel WHERE nameMotel=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String s = rs.getString(1);
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public boolean updateMotel(String name, String idM) {
        String sql = "UPDATE tb_motel SET nameMotel=? WHERE idMotel=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, idM);
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean insertMotel(MotelModel m) {
        String sql = "INSERT INTO tb_motel(idMotel, nameMotel) VALUES(?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, m.getIdMotel());
            ps.setString(2, m.getNameMotel());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean checkIDMotel(String idM) {
        String sql = "SELECT idMotel FROM tb_motel WHERE idMotel=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idM);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String result = rs.getString(1);
                return !result.equals("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
//    public static void main(String[] args) {
//        System.out.println(new MotelController().checkIDMotel("C"));
//    }
}
