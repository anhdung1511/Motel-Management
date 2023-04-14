/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DBConnection.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import Model.ContractDetailModel;

/**
 *
 * @author nguye
 */
public class ContractDetailController {

    private Connection conn;

    public ContractDetailController() {
        conn = new DBConnection().getDBConnection();
    }

    public ArrayList<ContractDetailModel> getListContractDetail() {
        ArrayList<ContractDetailModel> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_contract_detail";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ContractDetailModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6)
                ));
            }

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<ContractDetailModel> getListContractDetailByStatus(boolean sts) {
        ArrayList<ContractDetailModel> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_contract_detail WHERE statusContractDetail=?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, sts);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ContractDetailModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6)
                ));
            }

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertContractDetail(ContractDetailModel detail) {
        String sql = "INSERT INTO "
                + "tb_contract_detail(CMND, idContract, startDate, endDate, note, statusContractDetail) "
                + "VALUES(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, detail.getCmndFK());
            ps.setString(2, detail.getIdContractFK());
            ps.setString(3, detail.getStartDate());
            ps.setString(4, detail.getEndDate());
            ps.setString(5, detail.getNote());
            ps.setBoolean(6, detail.isStatusDetail());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getStartDate(String cmnd, String idC) {
        String sql = "SELECT startDate FROM tb_contract_detail WHERE CMND=? AND idContract=? AND statusContractDetail=1";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, cmnd);
            ps.setString(2, idC);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String date = rs.getString(1);
                return date;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

//    public int getMonthStartDate(String cmnd, String idC) {
//        String sql = "SELECT MONTH(startDate) FROM tb_contract_detail WHERE CMND=? AND idContract=? AND statusContractDetail=1";
//
//        try {
//            PreparedStatement ps = conn.prepareStatement(sql);
//
//            ps.setString(1, cmnd);
//            ps.setString(2, idC);
//
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                int date = rs.getInt(1);
//                return date;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    public int getYearStartDate(String cmnd, String idC) {
//        String sql = "SELECT YEAR(startDate) FROM tb_contract_detail WHERE CMND=? AND idContract=? AND statusContractDetail=1";
//
//        try {
//            PreparedStatement ps = conn.prepareStatement(sql);
//
//            ps.setString(1, cmnd);
//            ps.setString(2, idC);
//
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                int date = rs.getInt(1);
//                return date;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    public int getDayStartDate(String cmnd, String idC) {
//        String sql = "SELECT DAY(startDate) FROM tb_contract_detail WHERE CMND=? AND idContract=? AND statusContractDetail=1";
//
//        try {
//            PreparedStatement ps = conn.prepareStatement(sql);
//
//            ps.setString(1, cmnd);
//            ps.setString(2, idC);
//
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                int date = rs.getInt(1);
//                return date;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

    public String getEndDate(String cmnd, String idC) {
        String sql = "SELECT endDate FROM tb_contract_detail WHERE CMND=? AND idContract=?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, cmnd);
            ps.setString(2, idC);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String date = rs.getString(1);
                return date;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public int countContractDetail(String cmnd) {
        String sql = "SELECT COUNT(*) FROM tb_contract_detail WHERE CMND=? AND statusContractDetail=1";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cmnd);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int sum = rs.getInt(1);
                return sum;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updateEndDate(String cmnd, String idC) {
        String sql = "UPDATE tb_contract_detail SET endDate = DATE_ADD(startDate, INTERVAL 6 MONTH) WHERE CMND=? AND idContract=?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cmnd);
            ps.setString(2, idC);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<ContractDetailModel> getListContractDetailByRoomStatus(String idR, boolean sts) {
        ArrayList<ContractDetailModel> list = new ArrayList<>();
        String sql = "SELECT tb_contract_detail.*  FROM tb_contract JOIN tb_contract_detail ON tb_contract.idRoom=? AND tb_contract_detail.statusContractDetail=? AND tb_contract.idContract = tb_contract_detail.idContract";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idR);
            ps.setBoolean(2, sts);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new ContractDetailModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6)
                ));
            }

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ArrayList<ContractDetailModel> getListContractDetailRoom(String idR) {
        ArrayList<ContractDetailModel> list = new ArrayList<>();
        String sql = "SELECT tb_contract_detail.*  FROM tb_contract JOIN tb_contract_detail ON tb_contract.idRoom=? AND tb_contract.idContract = tb_contract_detail.idContract";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idR);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new ContractDetailModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6)
                ));
            }

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int getCountContractCurrent(String idC) {
        String sql = "SELECT COUNT(*) FROM tb_contract_detail WHERE idContract=? AND statusContractDetail=1";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idC);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean getStatusContractDetail(String cmnd, String idC) {
        String sql = "SELECT statusContractDetail FROM tb_contract_detail WHERE CMND=? AND idContract=?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cmnd);
            ps.setString(2, idC);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                boolean sts = rs.getBoolean(1);
                return sts;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateEndDayCurrent(String cmnd, String idC, String date) {
        String sql = "UPDATE tb_contract_detail SET endDate=?, statusContractDetail=0 WHERE CMND=? AND idContract=?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, date);
            ps.setString(2, cmnd);
            ps.setString(3, idC);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStatusContractDetail(String cmnd, String idC) {
        String sql = "UPDATE tb_contract_detail SET statusContractDetail=0 WHERE CMND=? AND idContract=?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cmnd);
            ps.setString(2, idC);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public int getCountStatusExprixeContractDetail(String idC) {
        String sql = "SELECT IFNULL(COUNT(*), 0) FROM tb_contract_detail WHERE idContract =? AND statusContractDetail=0";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idC);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getCountStatusUnExprixeContractDetail(String idC) {
        String sql = "SELECT IFNULL(COUNT(*), 0) FROM tb_contract_detail WHERE idContract =? AND statusContractDetail=1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idC);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getTotalStatusContractDetail(String idC) {
        String sql = "SELECT IFNULL(COUNT(*), 0) FROM tb_contract_detail WHERE idContract=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idC);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public String getMaxEndDate(String idC) {
        String sql = "SELECT MAX(endDate) FROM tb_contract_detail WHERE idContract=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idC);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
        }
        return null;
    }
    
    
    public ArrayList<ContractDetailModel> getListContractDetailWithRoom(String idC) {
        ArrayList<ContractDetailModel> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_contract_detail WHERE idContract=? and statusContractDetail=1";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idC);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new ContractDetailModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6)
                ));
            }

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ArrayList<ContractDetailModel> getContractExtend() {
        ArrayList<ContractDetailModel> list = new ArrayList<>();
        String sql = "select * from tb_contract_detail where datediff(endDate, curdate()) <= 7 and datediff(endDate, curdate()) >= 0 and statusContractDetail=1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new ContractDetailModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6)
                ));
            }

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
   
    
    public int getDistanceDate(String cmnd, String idC) {
        String sql = "select datediff(endDate, curdate()) from tb_contract_detail where cmnd=? and idContract=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cmnd);
            ps.setString(2, idC);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }
     
    public Date getExtendFromDate(String cmnd, String idC) {
        String sql = "select date_add(endDate, interval 1 day) from tb_contract_detail where CMND=? and idContract=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cmnd);
            ps.setString(2, idC);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getDate(1);
            }
        } catch (Exception e) {
        }
        return null;
    }
    
    public Date getExtendToDate(String cmnd, String idC) {
        String sql = "select endDate + interval 1 day + interval 6 month from tb_contract_detail where CMND=? and idContract=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cmnd);
            ps.setString(2, idC);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getDate(1);
            }
        } catch (Exception e) {
        }
        return null;
    }
    
//    public static void main(String[] args) {
//        System.out.println(new ContractDetailController().getMaxEndDate("HD_A_001_2022-05-30"));
//    }
}
