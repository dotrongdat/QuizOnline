/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.question;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author TRONG DAT
 */
public class QuestionDTO implements Serializable{
    private String questID;
    private String subID;
    private String content;
    private Timestamp createDate;
    private boolean status;
    
    public QuestionDTO(){}


    public QuestionDTO(String questID, String subID, String content, Timestamp createDate, boolean status) {
        this.questID = questID;
        this.subID = subID;
        this.content = content;
        this.createDate = createDate;
        this.status = status;
    }
    
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    
    /**
     * @return the questID
     */
    public String getQuestID() {
        return questID;
    }

    /**
     * @param questID the questID to set
     */
    public void setQuestID(String questID) {
        this.questID = questID;
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
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the multipleSelection
     */


    /**
     * @return the totalAnswer
     */
   

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
    
    
}
