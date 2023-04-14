/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nguye
 */
public class ContractDetailModel {
    private String cmndFK;
    private String idContractFK;
    private String startDate;
    private String endDate;
    private String note;
    private boolean statusDetail;
    public ContractDetailModel() {
    }

    public ContractDetailModel(String cmndFK, String idContractFK, String startDate, String endDate, String note, boolean statusDetail) {
        this.cmndFK = cmndFK;
        this.idContractFK = idContractFK;
        this.startDate = startDate;
        this.endDate = endDate;
        this.note = note;
        this.statusDetail = statusDetail;
    }

    

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public String getCmndFK() {
        return cmndFK;
    }

    public void setCmndFK(String cmndFK) {
        this.cmndFK = cmndFK;
    }

    public String getIdContractFK() {
        return idContractFK;
    }

    public void setIdContractFK(String idContractFK) {
        this.idContractFK = idContractFK;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(boolean statusDetail) {
        this.statusDetail = statusDetail;
    }
    
    
}
