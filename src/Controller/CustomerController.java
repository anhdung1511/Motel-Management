/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DBConnection.DBConnection;
import Model.CustomerModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class CustomerController {
    private Connection conn;

    public CustomerController() {
        conn = new DBConnection().getDBConnection();
    }
    
    public ArrayList<CustomerModel> getListCustomer(){
        ArrayList<CustomerModel> customerList = new ArrayList<>();
        String sql = "select * from tb_customer";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                CustomerModel kh = new CustomerModel();
                
                kh.setCMND(rs.getString(1));
                kh.setFirstName(rs.getString(2));
                kh.setLastName(rs.getString(3));
                kh.setSex(rs.getBoolean(4));
                kh.setDateOfBirth(rs.getString(5));
                kh.setPhoneNumber(rs.getString(6));
                kh.setAddress(rs.getString(7));
                kh.setJob(rs.getString(8));
                kh.setRentalStatus(rs.getBoolean(9));
                
                customerList.add(kh);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerList;
    }
    
    public boolean insertCustomer(CustomerModel cus) {
        String sql = "INSERT INTO "
                + "tb_customer(CMND, firstName, lastName, sex, dateOfBirth, phoneNumber, address, job, rentalStatus) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, cus.getCMND());
            ps.setString(2, cus.getFirstName());
            ps.setString(3, cus.getLastName());
            ps.setBoolean(4, cus.isSex());
            ps.setString(5, cus.getDateOfBirth());
            ps.setString(6, cus.getPhoneNumber());
            ps.setString(7, cus.getAddress());
            ps.setString(8, cus.getJob());
            ps.setBoolean(9, cus.isRentalStatus());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateCustomer(CustomerModel cus) {
        String sql = "UPDATE tb_customer SET firstName=?, lastName=?, sex=?, dateOfBirth=?, phoneNumber=?, address=?, job=?, rentalStatus=? WHERE CMND=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            
            ps.setString(1, cus.getFirstName());
            ps.setString(2, cus.getLastName());
            ps.setBoolean(3, cus.isSex());
            ps.setString(4, cus.getDateOfBirth());
            ps.setString(5, cus.getPhoneNumber());
            ps.setString(6, cus.getAddress());
            ps.setString(7, cus.getJob());
            ps.setBoolean(8, cus.isRentalStatus());
            ps.setString(9, cus.getCMND());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean deleteCustomer(String id) {
        String sql = "DELETE FROM tb_customer WHERE CMND=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean checkCustomer(String cmnd) {
        String sql = "SELECT COUNT(*) as ck FROM tb_customer WHERE CMND=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cmnd);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                int ck = rs.getInt("ck");
                return ck > 0 ? false : true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
   public ArrayList<CustomerModel> getInfo(String idR, String idC) {
        ArrayList<CustomerModel> list = new ArrayList<>();
        String sql = "select tb_customer.* "
                + "from tb_customer, tb_contract_detail, tb_contract, tb_room "
                + "where tb_room.idRoom=? and tb_contract.idContract=? "
                + "and tb_customer.CMND = tb_contract_detail.CMND "
                + "and tb_contract_detail.idContract = tb_contract.idContract "
                + "and tb_contract.idRoom = tb_room.idRoom "
                + "and tb_contract_detail.statusContractDetail=1";
        try {
           PreparedStatement ps = conn.prepareStatement(sql);
           ps.setString(1, idR);
           ps.setString(2, idC);
           ResultSet rs = ps.executeQuery();
           
           while(rs.next()) {
                list.add(new CustomerModel(
                       rs.getString(1),
                       rs.getString(2),
                       rs.getString(3),
                       rs.getBoolean(4), 
                       rs.getString(5),
                       rs.getString(6),
                       rs.getString(7),
                       rs.getString(8),
                       true  
               ));
           }
           ps.close();
       } catch (Exception e) {
           e.printStackTrace();
       }
       return list;
   }
    
    public String getFirstName(String cmnd) {
        String sql = "SELECT firstName FROM tb_customer WHERE CMND=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cmnd);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                String first = rs.getString(1);
                return first;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
   
    
    public String getLastName(String cmnd) {
        String sql = "SELECT lastName FROM tb_customer WHERE CMND=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cmnd);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                String last = rs.getString(1);
                return last;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    
    public ArrayList<CustomerModel> getFindCustomerByName(String name){
        ArrayList<CustomerModel> customerList = new ArrayList<>();
        String sql = "select *  from tb_customer where lastName like ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                CustomerModel kh = new CustomerModel();
                
                kh.setCMND(rs.getString(1));
                kh.setFirstName(rs.getString(2));
                kh.setLastName(rs.getString(3));
                kh.setSex(rs.getBoolean(4));
                kh.setDateOfBirth(rs.getString(5));
                kh.setPhoneNumber(rs.getString(6));
                kh.setAddress(rs.getString(7));
                kh.setJob(rs.getString(8));
                kh.setRentalStatus(rs.getBoolean(9));
                
                customerList.add(kh);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerList;
    }
    
    
    // Kiểm tra xem khách đã từng thuê ở đây chưa
    public boolean checkExistCustomer(String cmnd) {
        String sql = "SELECT COUNT(*) FROM phongtro.tb_contract_detail WHERE CMND=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cmnd);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public CustomerModel getCustomerWithCMND(String cmnd) {
        String sql = "SELECT * FROM tb_customer WHERE CMND=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cmnd);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return new CustomerModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getBoolean(9));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean updateRentalStatus(String cmnd, boolean sts) {
        String sql = "UPDATE tb_customer SET rentalStatus=? WHERE CMND=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, sts);
            ps.setString(2, cmnd);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean getRentalStatus(String cmnd) {
        String sql = "SELECT rentalStatus FROM tb_customer WHERE CMND=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cmnd);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (Exception e) {
        }
        return false;
    }
    
    
    public String getFullNameCustomer(String cmnd) {
        String sql = "SELECT CONCAT(firstName , ' ' , lastName) AS FULLNAME FROM tb_customer WHERE CMND=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cmnd);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
//    public static void main(String[] args) {
//        System.out.println(new CustomerController().getFullNameCustomer("241883009"));
//    }
}
