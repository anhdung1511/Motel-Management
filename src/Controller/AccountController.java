/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DBConnection.DBConnection;
import Model.AccountModel;
import java.sql.*;
/**
 *
 * @author nguye
 */
public class AccountController {
    private Connection conn;
    
    public AccountController() {
        conn = new DBConnection().getDBConnection();
    }
    
    public AccountModel getAccount(String userName) {
        String sql = "select * from tb_user where userName=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                AccountModel acc = new AccountModel(rs.getString(1), rs.getString(2), rs.getString(3));
                return acc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean changePasswd(String name, String passwd){
        String sql = "update tb_user set passwd=? where userName=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, passwd);
            ps.setString(2, name);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean insertAccount(AccountModel acc) {
        String sql = "INSERT INTO tb_user(userName, passwd, displayName) VALUES(?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, acc.getUserName());
            ps.setString(2, acc.getPasswd());
            ps.setString(3, acc.getDisplayName());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
//    
//    public static void main(String[] args) {
//        
//        System.out.println(new AccountController().getAccount("admin"));
//        
//    }
}
