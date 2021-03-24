/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.subject;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author TRONG DAT
 */
public class SubjectDTO implements Serializable{
    private String subID;
    private String subCodeName;
    private String subName;
    private Timestamp createDate;
    private long duraion;
    private boolean status;
    
    public SubjectDTO(){}

    public SubjectDTO(String subID, String subCodeName, String subName, Timestamp createDate, long duraion, boolean status) {
        this.subID = subID;
        this.subCodeName = subCodeName;
        this.subName = subName;
        this.createDate = createDate;
        this.duraion = duraion;
        this.status = status;
    }

    /**
     * @return the subID
     */
    public String getSubID() {
        return subID;
    }

    /**
     * @param subID the subID to set
     */
    public void setSubID(String subID) {
        this.subID = subID;
    }

    /**
     * @return the subCodeName
     */
    public String getSubCodeName() {
        return subCodeName;
    }

    /**
     * @param subCodeName the subCodeName to set
     */
    public void setSubCodeName(String subCodeName) {
        this.subCodeName = subCodeName;
    }

    /**
     * @return the subName
     */
    public String getSubName() {
        return subName;
    }

    /**
     * @param subName the subName to set
     */
    public void setSubName(String subName) {
        this.subName = subName;
    }

    /**
     * @return the createDate
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the duraion
     */
    public long getDuraion() {
        return duraion;
    }

    /**
     * @param duraion the duraion to set
     */
    public void setDuraion(long duraion) {
        this.duraion = duraion;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(this.subID); 
    }
    public String formatTimeBySecond(){
        long hours= duraion/3600;
        duraion=duraion%3600;
        long minutes=duraion/60;
        duraion=duraion%60;
        long seconds=duraion;
                
        String timeStr="";
        if(hours>=10){
                 timeStr=timeStr.concat(hours+" hour: ");
            }else{
                 timeStr=timeStr.concat("0"+hours+" hour: ");
            }          
        if(minutes>=10){
            timeStr=timeStr.concat(minutes+" minute: ");
        }else{
            timeStr=timeStr.concat("0"+minutes+" minute: ");
        }
        if(seconds>=10){
            timeStr=timeStr.concat(seconds+" second: ");
        }else{
            timeStr=timeStr.concat("0"+seconds+" second: ");
        }
        return timeStr;
    }
}
