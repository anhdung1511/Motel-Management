/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author nguye
 */
public class DBConnection {
    private Connection conn = null;
    
    public DBConnection(){
    }
    
    public Connection getDBConnection(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/phongtro", "root", "241883009");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
