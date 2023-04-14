/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nguye
 */
public class ContractModel {
    private String idContract;
    private String idRoomFK;
    private String dateOfHire;
    private double depositMoney;
    private String checkOutDate;
    private String note;
    private int statusContract;
    

    public ContractModel() {
    }

    public ContractModel(String idContract, String idRoomFK, String dateOfHire, double depositMoney, String checkOutDate, String note, int statusContract) {
        this.idContract = idContract;
        this.idRoomFK = idRoomFK;
        this.dateOfHire = dateOfHire;
        this.depositMoney = depositMoney;
        this.checkOutDate = checkOutDate;
        this.note = note;
        this.statusContract = statusContract;
    }

    public String getIdContract() {
        return idContract;
    }

    public void setIdContract(String idContract) {
        this.idContract = idContract;
    }

    public String getIdRoomFK() {
        return idRoomFK;
    }

    public void setIdRoomFK(String idRoomFK) {
        this.idRoomFK = idRoomFK;
    }

    public String getDateOfHire() {
        return dateOfHire;
    }

    public void setDateOfHire(String dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    public double getDepositMoney() {
        return depositMoney;
    }

    public void setDepositMoney(double depositMoney) {
        this.depositMoney = depositMoney;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStatusContract() {
        return statusContract;
    }

    public void setStatusContract(int statusContract) {
        this.statusContract = statusContract;
    }

    
    
}
