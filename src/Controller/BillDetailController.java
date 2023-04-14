/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DBConnection.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import Model.BillDetailModel;
/**
 *
 * @author nguye
 */
public class BillDetailController {
    private Connection conn;
    
    public BillDetailController() {
        conn = new DBConnection().getDBConnection();
    }
    
    public ArrayList<BillDetailModel> getListBillDetail() {
        ArrayList<BillDetailModel> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_bill_detail";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                list.add(new BillDetailModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getFloat(4),
                        rs.getFloat(5),
                        rs.getDouble(6),
                        rs.getString(7)
                ));
            }
            
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
    public boolean insertBillDetail(BillDetailModel detail) {
        String sql = "INSERT INTO tb_bill_detail(idBill, idService, price, oldIndex, newIndex, totalPrice, note) VALUES(?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, detail.getIdBillFK());
            ps.setString(2, detail.getIdServiceFK());
            ps.setDouble(3, detail.getPrice());
            ps.setFloat(4, detail.getOldIndex());
            ps.setFloat(5, detail.getNewIndex());
            ps.setDouble(6, detail.getTotalPrice());
            ps.setString(7, detail.getNote());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateBillDetail(BillDetailModel detail) {
        String sql = "UPDATE tb_bill_detail SET oldIndex=?, newIndex=?, totalPrice=?, note=? WHERE idBill=? AND idService =?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setFloat(1, detail.getOldIndex());
            ps.setFloat(2, detail.getNewIndex());
            ps.setDouble(3, detail.getTotalPrice());
            ps.setString(4, detail.getNote());
            ps.setString(5, detail.getIdBillFK());
            ps.setString(6, detail.getIdServiceFK());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public float getNewIndex(String idB, String idSer) {
        String sql = "SELECT newIndex FROM tb_bill_detail WHERE idBill=? AND idService=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idB);
            ps.setString(2, idSer);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                float newIndex = rs.getFloat(1);
                return newIndex;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public float getOldIndex(String idB, String idSer) {
        String sql = "SELECT oldIndex FROM tb_bill_detail WHERE idBill=? AND idService=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idB);
            ps.setString(2, idSer);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                float old = rs.getFloat(1);
                return old;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public double getTotalPrice(String idB, String idSer) {
        String sql = "SELECT totalPrice FROM tb_bill_detail WHERE idBill=? AND idService=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idB);
            ps.setString(2, idSer);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                double price = rs.getDouble(1);
                return price;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public double getPrice(String idB, String idSer) {
        String sql = "SELECT price FROM tb_bill_detail WHERE idBill=? AND idService=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idB);
            ps.setString(2, idSer);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
//    
//    public static void main(String[] args) {
//        System.out.println(new BillDetailController().getPrice("HOADON_01_HD_A_001_2022-05-30", "D"));
//    }
}
