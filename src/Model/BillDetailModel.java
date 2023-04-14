/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nguye
 */
public class BillDetailModel {
    private String idBillFK;
    private String idServiceFK;
    private double price;
    private float oldIndex;
    private float newIndex;
    private double totalPrice;
    private String note;

    public BillDetailModel() {
    }

    public BillDetailModel(String idBillFK, String idServiceFK, double price, float oldIndex, float newIndex, double totalPrice, String note) {
        this.idBillFK = idBillFK;
        this.idServiceFK = idServiceFK;
        this.price = price;
        this.oldIndex = oldIndex;
        this.newIndex = newIndex;
        this.totalPrice = totalPrice;
        this.note = note;
    }

    public String getIdBillFK() {
        return idBillFK;
    }

    public void setIdBillFK(String idBillFK) {
        this.idBillFK = idBillFK;
    }

    public String getIdServiceFK() {
        return idServiceFK;
    }

    public void setIdServiceFK(String idServiceFK) {
        this.idServiceFK = idServiceFK;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getOldIndex() {
        return oldIndex;
    }

    public void setOldIndex(float oldIndex) {
        this.oldIndex = oldIndex;
    }

    public float getNewIndex() {
        return newIndex;
    }

    public void setNewIndex(float newIndex) {
        this.newIndex = newIndex;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

   
    
    
    
}
