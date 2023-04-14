/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import DBConnection.DBConnection;
import Model.ContractModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author nguye
 */
public class ContractController {
    private Connection conn;
    
    public ContractController() {
        conn = new DBConnection().getDBConnection();
    }
    
    public ArrayList<ContractModel> getListContract() {
        ArrayList<ContractModel> contract = new ArrayList<>();
        String sql = "SELECT * FROM tb_contract";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                ContractModel cm = new ContractModel();
                
                cm.setIdContract(rs.getString(1));
                cm.setIdRoomFK(rs.getString(2));
                cm.setDateOfHire(rs.getString(3));
                cm.setDepositMoney(rs.getDouble(4));
                cm.setCheckOutDate(rs.getString(5));
                cm.setNote(rs.getString(6));
                cm.setStatusContract(rs.getInt(7));
                
                contract.add(cm);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contract;
    }
    
    public boolean insertContract(ContractModel contract) {
        String sql = "INSERT INTO tb_contract(idContract, idRoom, dateOfHire, depositMoney, checkOutDate, note, statusContract) VALUES(?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, contract.getIdContract());
            ps.setString(2, contract.getIdRoomFK());
            ps.setString(3, contract.getDateOfHire());
            ps.setDouble(4, contract.getDepositMoney());
            ps.setString(5, contract.getCheckOutDate());
            ps.setString(6, contract.getNote());
            ps.setInt(7, contract.getStatusContract());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateContract(ContractModel contract) {
        String sql = "UPDATE tb_contract SET idRoom=?, dateOfHire=?, depositMoney=?, checkOutDate=?, note=?, statusContract=? WHERE idContract=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, contract.getIdRoomFK());
            ps.setString(2, contract.getDateOfHire());
            ps.setDouble(3, contract.getDepositMoney());
            ps.setString(4, contract.getCheckOutDate());
            ps.setString(5, contract.getNote());
            ps.setInt(6, contract.getStatusContract());
            ps.setString(7, contract.getIdContract());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteContract(String id) {
        String sql = "DELETE FROM tb_contract WHERE idContract=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public boolean checkExist(String idR) {
        String sql = "SELECT COUNT(*) FROM tb_contract WHERE idRoom=? AND statusContract=1";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idR);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getInt(1) > 0 ? false : true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public String getDateHire(String idR) {
        String sql = "SELECT dateOfHire FROM tb_contract WHERE idRoom=? AND statusContract=1";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idR);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String date = rs.getString("tb_contract.dateOfHire");
                return date;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public String getIDContract(String idR) {
        String sql = "SELECT idContract FROM tb_contract WHERE idRoom=? AND statusContract=1";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idR);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String ID = rs.getString(1);
                return ID;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    
    public int getMonthContractBill(String idC) {
        String sql = "SELECT MONTH(dateOfHire) FROM tb_contract WHERE idContract=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idC);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int date = rs.getInt(1);
                return date;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getYearContractBill(String idC) {
        String sql = "SELECT YEAR(dateOfHire) FROM tb_contract WHERE idContract=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idC);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int date = rs.getInt(1);
                return date;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getDayContractBill(String idC) {
        String sql = "SELECT DAY(dateOfHire) FROM tb_contract WHERE idContract=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idC);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int date = rs.getInt(1);
                return date;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public String getIDRoom(String idC) {
        String sql = "SELECT idRoom FROM tb_contract WHERE idContract=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idC);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                String idR = rs.getString(1);
                return idR;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    
    public boolean updateCheckOutDate(String idC, String date) {
        String sql = "UPDATE tb_contract SET checkOutDate=?, statusContract=0 WHERE idContract=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, date);
            ps.setString(2, idC);
            
            return  ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
//    
//    public static void main(String[] args) {
//        System.out.println(new ContractController().getIDRoom("HD_Q9_001_2022-05-15"));
//    }
}
