/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.resultsubject;

import java.io.Serializable;
import java.sql.Timestamp;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author TRONG DAT
 */
public class ResultSubjectDTO implements Serializable{
    private String subID;
    private String email;
    private double mark;
    private Timestamp dateTakeQuiz;
    private long duraionTakeQuiz;
    private String data;

    public ResultSubjectDTO() {
    }

    public ResultSubjectDTO(String subID, String email, double mark, Timestamp dateTakeQuiz, long duraionTakeQuiz, String data) {
        this.subID = subID;
        this.email = email;
        this.mark = mark;
        this.dateTakeQuiz = dateTakeQuiz;
        this.duraionTakeQuiz = duraionTakeQuiz;
        this.data = data;
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the mark
     */
    public double getMark() {
        return mark;
    }

    /**
     * @param mark the mark to set
     */
    public void setMark(double mark) {
        this.mark = mark;
    }

    /**
     * @return the dateTakeQuiz
     */
    public Timestamp getDateTakeQuiz() {
        return dateTakeQuiz;
    }

    /**
     * @param dateTakeQuiz the dateTakeQuiz to set
     */
    public void setDateTakeQuiz(Timestamp dateTakeQuiz) {
        this.dateTakeQuiz = dateTakeQuiz;
    }

    /**
     * @return the duraionTakeQuiz
     */
    public long getDuraionTakeQuiz() {
        return duraionTakeQuiz;
    }

    /**
     * @param duraionTakeQuiz the duraionTakeQuiz to set
     */
    public void setDuraionTakeQuiz(long duraionTakeQuiz) {
        this.duraionTakeQuiz = duraionTakeQuiz;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }
    
    public String setFormatTimeBySecond(){
        long hours= duraionTakeQuiz/3600;
        duraionTakeQuiz=duraionTakeQuiz%3600;
        long minutes=duraionTakeQuiz/60;
        duraionTakeQuiz=duraionTakeQuiz%60;
        long seconds=duraionTakeQuiz;
                
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

    @Override
    public int hashCode() {
        return DigestUtils.sha256Hex(subID+email).hashCode();
    }
    
    
}
