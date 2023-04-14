/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nguye
 */
public class RoomModel {
    private String idRoom;
    private String idMotelFK;
    private double priceRoom;
    private int MaxQuantity;
    private int CurrentQuantity;
    private String idStatusFK;
    
    public RoomModel() {
    }

    public RoomModel(String idRoom, String idMotelFK, double priceRoom, int MaxQuantity, int CurrentQuantity, String idStatusFK) {
        this.idRoom = idRoom;
        this.idMotelFK = idMotelFK;
        this.priceRoom = priceRoom;
        this.MaxQuantity = MaxQuantity;
        this.CurrentQuantity = CurrentQuantity;
        this.idStatusFK = idStatusFK;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    public String getIdMotelFK() {
        return idMotelFK;
    }

    public void setIdMotelFK(String idMotelFK) {
        this.idMotelFK = idMotelFK;
    }

    public double getPriceRoom() {
        return priceRoom;
    }

    public void setPriceRoom(double priceRoom) {
        this.priceRoom = priceRoom;
    }

    public int getMaxQuantity() {
        return MaxQuantity;
    }

    public void setMaxQuantity(int MaxQuantity) {
        this.MaxQuantity = MaxQuantity;
    }

    public int getCurrentQuantity() {
        return CurrentQuantity;
    }

    public void setCurrentQuantity(int CurrentQuantity) {
        this.CurrentQuantity = CurrentQuantity;
    }

    public String getIdStatusFK() {
        return idStatusFK;
    }

    public void setIdStatusFK(String idStatusFK) {
        this.idStatusFK = idStatusFK;
    }

    
    
    
}
