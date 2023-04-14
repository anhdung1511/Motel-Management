/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import DBConnection.DBConnection;
import Model.RoomModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author nguye
 */
public class RoomController {
    private Connection conn;
    
    public RoomController() {
        conn = new DBConnection().getDBConnection();
    }
    
    public ArrayList<RoomModel> getListRoom() {
        ArrayList<RoomModel> roomList = new ArrayList<>();
        String sql = "SELECT * FROM tb_room";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                RoomModel room = new RoomModel();
                
                room.setIdRoom(rs.getString(1));
                room.setIdMotelFK(rs.getString(2));
                room.setPriceRoom(rs.getDouble(3));
                room.setMaxQuantity(rs.getInt(4));
                room.setCurrentQuantity(rs.getInt(5));
                room.setIdStatusFK(rs.getString(6));
                roomList.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }
    
    // Thêm mới 1 phòng trọ.
    public boolean insertRoom(RoomModel room) {
        String sql = "INSERT INTO tb_room(idRoom, idMotel, priceRoom, maxQuantity,currentQuantity,idStatus) VALUES(?,?,?,?,?,?)";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, room.getIdRoom()); 
            ps.setString(2, room.getIdMotelFK());
            ps.setDouble(3, room.getPriceRoom());
            ps.setInt(4, room.getMaxQuantity());
            ps.setInt(5, room.getCurrentQuantity());
            ps.setString(6, room.getIdStatusFK());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public ArrayList<RoomModel> getListRoomByMotel(String name) {
        ArrayList<RoomModel> roomList = new ArrayList<>();
        String sql = "SELECT tb_room.* FROM tb_room JOIN tb_motel ON tb_motel.idMotel = tb_room.idMotel AND tb_motel.nameMotel=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                RoomModel room = new RoomModel();
                
                room.setIdRoom(rs.getString(1));
                room.setIdMotelFK(rs.getString(2));
                room.setPriceRoom(rs.getDouble(3));
                room.setMaxQuantity(rs.getInt(4));
                room.setCurrentQuantity(rs.getInt(5));
                room.setIdStatusFK(rs.getString(6));
                roomList.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }
    
    public boolean deleteRoom(String id) {
        String sql = "DELETE FROM tb_room WHERE idRoom=?";
       
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean updateRoom(String idR, int max, double price, String idS) {
        String sql = "UPDATE tb_room SET priceRoom=?, maxQuantity=?, idStatus=? where idRoom=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, price);
            ps.setInt(2, max);
            ps.setString(3, idS);
            ps.setString(4, idR);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public ArrayList<RoomModel> listNoRentedRoom () {
        ArrayList<RoomModel> roomList = new ArrayList<>();
        String sql = "SELECT * FROM tb_room WHERE idStatus = 'TT1 or idStatus = 'TT4'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                RoomModel room = new RoomModel();
                
                room.setIdRoom(rs.getString(1));
                room.setIdMotelFK(rs.getString(2));
                room.setPriceRoom(rs.getDouble(3));
                room.setMaxQuantity(rs.getInt(4));
                room.setCurrentQuantity(rs.getInt(5));
                room.setIdStatusFK(rs.getString(6));
                roomList.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }
    
    public boolean updateStatusRooom(String id, int current, String status) {
        String sql = "UPDATE tb_room SET currentQuantity=?, idStatus=? WHERE idRoom=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, current);
            ps.setString(2, status);
            ps.setString(3, id);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public int checkCurrentQuantity(String id) {
        String sql = "SELECT COUNT(tb_customer.idCustomer) AS countCustomer FROM tb_customer JOIN tb_room ON tb_customer.idRoom=? AND tb_customer.idRoom = tb_room.idRoom";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            
            ResultSet rs = ps.executeQuery();
            
           if(rs.next()){
                int current = rs.getInt("countCustomer");
                return current;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    public boolean updateRoom(String id, int current, String status) {
        String sql = "UPDATE tb_room SET currentQuantity=? idStatus=? WHERE idRoom=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            
            ps.setInt(1, current);
            ps.setString(2, status);
            ps.setString(3, id);
            
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public RoomModel getRoom(String id) {
        String sql = "SELECT * FROM tb_room WHERE idRoom=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                RoomModel r = new RoomModel(
                        rs.getString("idRoom"),
                        rs.getString("idMotel"),
                        rs.getDouble("priceRoom"),
                        rs.getInt("maxQuantity"),
                        rs.getInt("currentQuantity"),
                        rs.getString("idStatus")
                );
                
                return r;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getCurrent(String id) {
        String sql = "SELECT currentQuantity FROM tb_room WHERE idRoom=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int current = rs.getInt(1);
                return current;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getMax(String id) {
        String sql = "SELECT maxQuantity FROM tb_room WHERE idRoom=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int max = rs.getInt(1);
                return max;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public String getStatus(String id) {
        String sql = "SELECT idStatus FROM tb_room WHERE idRoom=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String sts = rs.getString(1);
                return sts;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public String getIDMotel(String id) {
        String sql = "SELECT idMotel FROM tb_room WHERE idRoom=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String idM = rs.getString(1);
                return idM;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    
    public double getPriceRoom(String idR) {
        String sql = "SELECT priceRoom FROM tb_room WHERE idRoom=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idR);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                double price  = rs.getDouble(1);
                return price;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
   
    
    public int getTotalRoom() {
        String sql = "SELECT COUNT(*) FROM tb_room";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int result = rs.getInt(1);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    public int getTotalRoomRented() {
        String sql = "SELECT COUNT(*) FROM tb_room WHERE idStatus=\"TT2\" OR idStatus=\"TT3\"";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int result = rs.getInt(1);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    public int getTotalRoomUnrented() {
        String sql = "SELECT COUNT(*) FROM tb_room WHERE idStatus=\"TT1\" OR idStatus=\"TT4\"";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int result = rs.getInt(1);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    public int getTotalRoomRentedByMotel(String name) {
        String sql = "SELECT COUNT(*) FROM tb_room JOIN tb_motel ON tb_motel.idMotel = tb_room.idMotel AND tb_motel.nameMotel=? AND (idStatus=\"TT2\" OR idStatus=\"TT3\")";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int result = rs.getInt(1);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    public int getTotalRoomUnrentedByMotel(String name) {
        String sql = "SELECT COUNT(*) FROM tb_room JOIN tb_motel ON tb_motel.idMotel = tb_room.idMotel AND tb_motel.nameMotel=? AND (idStatus=\"TT1\" OR idStatus=\"TT4\")";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int result = rs.getInt(1);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getTotalRoomByMotel(String name) {
        String sql = "SELECT COUNT(*) FROM tb_room JOIN tb_motel ON tb_motel.idMotel = tb_room.idMotel AND tb_motel.nameMotel=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int result = rs.getInt(1);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    // Lấy idMax lớn nhất để thực hiện tự động thêm idRoom
    public String getMaxIdRoom(String idM) {
        String sql = "SELECT IFNULL(MAX(idRoom), \"null\") FROM phongtro.tb_room WHERE idMotel=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idM);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String result = rs.getString(1);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    
    public int getStatisticalByRoomMonthYear(int month, int year) {
        
        String sql = "select IFNULL(COUNT(*), 0) \n"
                + "from tb_contract join tb_room \n"
                + "on tb_contract.idRoom = tb_room.idRoom and (tb_room.idStatus = \"TT2\" or tb_room.idStatus = \"TT3\") and year(tb_contract.dateOfHire)=? and month(tb_contract.dateOfHire)=?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, year);
            ps.setInt(2, month);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
            ps.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    public boolean checkExistRoom(String idR) {
        String sql = "SELECT COUNT(*) FROM tb_contract WHERE idRoom=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idR);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public int totalRoomByMotel(String idM) {
        String sql = "SELECT IFNULL(COUNT(*), 0) FROM tb_room WHERE idMotel=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idM);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int result = rs.getInt(1);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    public  String convertUpper(String s) {
        String[] words = s.toLowerCase().split("\\s+");
        StringBuilder builder = new StringBuilder();
        for (var item : words) {
            char[] characters = item.toCharArray();
            characters[0] = Character.toUpperCase(characters[0]);
            builder.append(new String(characters));
            builder.append(" ");
        }
       
        return builder.toString().trim();
    }
    
//    public static void main(String[] args) {
//        System.out.println(new RoomController().convertUpper("dUNGGGG"));
//    }
}
