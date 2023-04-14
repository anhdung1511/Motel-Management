/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DBConnection.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import Model.ServiceModel;
/**
 *
 * @author nguye
 */
public class ServiceController {
    private Connection conn;
    
    public ServiceController() {
        conn = new DBConnection().getDBConnection();
    }
    
    public ArrayList<ServiceModel> getListService () {
        ArrayList<ServiceModel> model = new ArrayList<>();
        String sql = "SELECT * FROM tb_service";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                model.add(new ServiceModel (
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4)
                ));
            }
            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public double getPrice(String id) {
        String sql = "SELECT priceService FROM tb_service WHERE idService=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                double price = rs.getDouble("priceService");
                return price;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    public boolean insertService(ServiceModel service) {
        String sql = "INSERT INTO tb_service(idService, nameService, priceService, unit) VALUES(?,?,?,?)";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, service.getIdService());
            ps.setString(2, service.getNameService());
            ps.setDouble(3, service.getPrice());
            ps.setString(4, service.getUnit());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateService(String id, double price, String unit) {
        String sql = "UPDATE tb_service SET priceService=?, unit=? WHERE idService=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
           
            ps.setDouble(1, price);
            ps.setString(2, unit);
            ps.setString(3, id);
            
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean checkID(String id) {
        String sql = "SELECT COUNT(*) FROM tb_service WHERE idService=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                int result = rs.getInt(1);
                return result > 0 ? false : true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public String getUnit(String id) {
        String sql = "SELECT unit FROM tb_service WHERE idService=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
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
    
//    public static void main(String[] args) {
//        System.out.println(new ServiceController().checkID("D"));
//    }
}
