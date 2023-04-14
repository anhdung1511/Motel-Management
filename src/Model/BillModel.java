/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nguye
 */
public class BillModel {
    private String idBill;
    private String idContractFK;
    private String collectionDate;
    private double totalMoney;
    private boolean statusBill;
    private int stt;
    public BillModel() {
    }

    public BillModel(String idBill, String idContractFK, String collectionDate, double totalMoney, boolean statusBill, int stt) {
        this.idBill = idBill;
        this.idContractFK = idContractFK;
        this.collectionDate = collectionDate;
        this.totalMoney = totalMoney;
        this.statusBill = statusBill;
        this.stt = stt;
    }

    public BillModel(String idBill, String idContractFK, String collectionDate, double totalMoney, boolean statusBill) {
        this.idBill = idBill;
        this.idContractFK = idContractFK;
        this.collectionDate = collectionDate;
        this.totalMoney = totalMoney;
        this.statusBill = statusBill;
    }

    
   
    public String getIdContractFK() {
        return idContractFK;
    }

    public void setIdContractFK(String idContractFK) {
        this.idContractFK = idContractFK;
    }

    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public boolean isStatusBill() {
        return statusBill;
    }

    public void setStatusBill(boolean statusBill) {
        this.statusBill = statusBill;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }
    
    
}
