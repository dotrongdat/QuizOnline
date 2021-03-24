/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.answer;

import java.io.Serializable;

/**
 *
 * @author TRONG DAT
 */
public class AnswerDTO implements Serializable{
    private String ansID;
    private String questID;
    private String content;
    private boolean correctAns;

    public AnswerDTO() {
    }

    public AnswerDTO(String ansID, String questID, String content, boolean correctAns) {
        this.ansID = ansID;
        this.questID = questID;
        this.content = content;
        this.correctAns = correctAns;
    }
    public AnswerDTO(String content,boolean correctAns){
        this.ansID=null;
        this.questID=null;
    }
    /**
     * @return the ansID
     */
    public String getAnsID() {
        return ansID;
    }

    /**
     * @param ansID the ansID to set
     */
    public void setAnsID(String ansID) {
        this.ansID = ansID;
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
     * @return the correctAns
     */
    public boolean isCorrectAns() {
        return correctAns;
    }

    /**
     * @param correctAns the correctAns to set
     */
    public void setCorrectAns(boolean correctAns) {
        this.correctAns = correctAns;
    }
    
    
}
