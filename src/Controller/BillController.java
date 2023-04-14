/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import DBConnection.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import Model.BillModel;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author nguye
 */
public class BillController {
    private Connection conn;
    
    public BillController() {
        conn = new DBConnection().getDBConnection();
    }
    
    public ArrayList<BillModel> getListBill() {
        ArrayList<BillModel> list =  new ArrayList<>();
        String sql = "SELECT * FROM tb_bill";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                list.add(new BillModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getBoolean(5),
                        rs.getInt(6)
                ));
            }
            
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean insertBill(BillModel bill) {
        String sql = "INSERT INTO tb_bill(idBill, idContract, collectionDate, totalMoney, statusBill) VALUES(?,?,?,?,?)";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, bill.getIdBill());
            ps.setString(2, bill.getIdContractFK());
            ps.setString(3, bill.getCollectionDate());
            ps.setDouble(4, bill.getTotalMoney());
            ps.setBoolean(5, bill.isStatusBill());
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateBill(BillModel bill) {
        String sql = "UPDATE tb_bill SET totalMoney=?, statusBill=? WHERE idBill=? AND idContract=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, bill.getTotalMoney());
            ps.setBoolean(2, bill.isStatusBill());
            ps.setString(3, bill.getIdBill());
            ps.setString(4, bill.getIdContractFK());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public int getMaxSTTBill(String idC) {
        String sql = "SELECT MAX(stt) FROM tb_bill WHERE idContract=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idC);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int stt = rs.getInt(1);
                return stt;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    public int getMonthCollectionDate(String idC, int stt) {
        String sql = "SELECT MONTH(collectionDate) FROM tb_bill WHERE idContract =? AND stt=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idC);
            ps.setInt(2, stt);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                int month = rs.getInt(1);
                return month;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    public int getYearCollectionDate(String idC, int stt) {
        String sql = "SELECT YEAR(collectionDate) FROM tb_bill WHERE idContract =? AND stt=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idC);
            ps.setInt(2, stt);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                int year = rs.getInt(1);
                return year;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public String getIDBill(int stt) {
        String sql = "SELECT idBill FROM tb_bill WHERE stt=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, stt);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                String id = rs.getString(1);
                return id;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getMinSTTBill(String idC) {
        String sql = "SELECT MIN(stt) FROM tb_bill WHERE idContract=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idC);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int stt = rs.getInt(1);
                return stt;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    public ArrayList<BillModel> getListBillAtRoom(String idC) {
        ArrayList<BillModel> list =  new ArrayList<>();
        String sql = "SELECT * FROM tb_bill WHERE idContract=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idC);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                list.add(new BillModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getBoolean(5),
                        rs.getInt(6)
                ));
            }
            
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
    public boolean updateTotalMoney(String idB, double total) {
        String sql = "UPDATE tb_bill SET totalMoney=? WHERE idBill=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, total);
            ps.setString(2, idB);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean getStatusBill (String idB) {
        String sql = "SELECT statusBill FROM tb_bill WHERE idBill=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idB);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                boolean sts = rs.getBoolean(1);
                return sts;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStatusBill(String idB, boolean ck) {
        String sql = "UPDATE tb_bill SET statusBill=? WHERE idBill=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, ck);
            ps.setString(2, idB);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public ArrayList<BillModel> getListBillWithMonthYear(int mon, int year) {
        ArrayList<BillModel> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_bill WHERE MONTH(collectionDate)=? AND YEAR(collectionDate)=?";
        
        try {
            PreparedStatement  ps = conn.prepareStatement(sql);
            ps.setInt(1, mon);
            ps.setInt(2, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new BillModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getBoolean(5),
                        rs.getInt(6)
                ));
            }
            
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
        
    
    public ArrayList<BillModel> getListBillWithMonthYearStatus(int mon, int year, boolean status) {
        ArrayList<BillModel> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_bill WHERE MONTH(collectionDate)=? AND YEAR(collectionDate)=? AND statusBill=?";
        
        try {
            PreparedStatement  ps = conn.prepareStatement(sql);
            ps.setInt(1, mon);
            ps.setInt(2, year);
            ps.setBoolean(3, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new BillModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getBoolean(5),
                        rs.getInt(6)
                ));
            }
            
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
    public int getCountBillStatus(boolean sts, int mon, int year) {
        String sql = "SELECT COUNT(statusBill) FROM tb_bill WHERE statusBill=? AND MONTH(collectionDate)=? AND YEAR(collectionDate)=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, sts);
            ps.setInt(2, mon);
            ps.setInt(3, year);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int tmp  = rs.getInt(1);
                return tmp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    } 
    
    
    public double getStatisticalByYear(int year) {
        String sql = "SELECT IFNULL(SUM(totalMoney), 0) FROM tb_bill WHERE YEAR(collectionDate)=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, year);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                double total = rs.getDouble(1);
                return total;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public double getStatisticalByMonthYear(int mon, int year) {
        
        String sql = "select IFNULL(sum(totalMoney), 0) \n"
                + "FROM tb_bill \n"
                + "WHERE YEAR(collectionDate)=? AND MONTH(collectionDate)=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, year);
            ps.setInt(2, mon);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getDouble(1);
            }
            ps.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    

    public boolean checkBillPaidOrUnPaid(int stt) {
        String sql = "SELECT statusBill FROM tb_bill WHERE stt=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, stt);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
//    public static void main(String[] args) {
//        System.out.println(new BillController().getMaxSTTBill("HD_A_007_2022-06-02"));
//    }
}
