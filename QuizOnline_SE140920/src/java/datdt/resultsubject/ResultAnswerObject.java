/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.resultsubject;

import java.io.Serializable;

/**
 *
 * @author TRONG DAT
 */
public class ResultAnswerObject implements Serializable{
    private String content;
    private boolean correctAnswer;
    private boolean choosen;

    public ResultAnswerObject() {
    }

    public ResultAnswerObject(String content, boolean correctAnswer, boolean choosen) {
        this.content = content;
        this.correctAnswer = correctAnswer;
        this.choosen = choosen;
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
     * @return the correctAnswer
     */
    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * @param correctAnswer the correctAnswer to set
     */
    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * @return the choosen
     */
    public boolean isChoosen() {
        return choosen;
    }

    /**
     * @param choosen the choosen to set
     */
    public void setChoosen(boolean choosen) {
        this.choosen = choosen;
    }
    
    
}
