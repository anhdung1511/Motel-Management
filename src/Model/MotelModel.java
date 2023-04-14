/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nguye
 */
public class MotelModel {
    private String idMotel;
    private String nameMotel;

    public MotelModel() {
    }

    public MotelModel(String idMotel, String nameMotel) {
        this.idMotel = idMotel;
        this.nameMotel = nameMotel;
    }

    public String getNameMotel() {
        return nameMotel;
    }

    public void setNameMotel(String nameMotel) {
        this.nameMotel = nameMotel;
    }

    public String getIdMotel() {
        return idMotel;
    }

    public void setIdMotel(String idMotel) {
        this.idMotel = idMotel;
    }
    
}
