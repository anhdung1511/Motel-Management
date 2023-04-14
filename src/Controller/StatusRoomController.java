/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DBConnection.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import Model.StatusRoomModel;
/**
 *
 * @author nguye
 */
public class StatusRoomController {
    private Connection conn;
    
    public StatusRoomController() {
        conn = new DBConnection().getDBConnection();
    }
    
    public ArrayList<StatusRoomModel> getListStatus() {
        ArrayList<StatusRoomModel> listStatus = new ArrayList<>();
        String sql = "select * from tb_status_room";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                StatusRoomModel status = new StatusRoomModel();
                status.setIdStatus(rs.getString(1));
                status.setStatusName(rs.getString(2));
                
                listStatus.add(status);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listStatus;
    }
    
    public String getNameStatus(String id) {
        String sql = "SELECT statusName FROM tb_status_room WHERE idStatus=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String name = rs.getString("statusName");
                return name;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "";
    }
    
    public String getID(String name) {
        String sql = "SELECT idStatus FROM tb_status_room WHERE statusName=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                String idS = rs.getString(1);
                return idS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
//    public static void main(String[] args) {
//        System.out.println(new StatusRoomController().getID("8"));
//    }
}
