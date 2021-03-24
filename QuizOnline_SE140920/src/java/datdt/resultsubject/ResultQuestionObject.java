/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.resultsubject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TRONG DAT
 */
public class ResultQuestionObject implements Serializable{
    private String content;
    private List<ResultAnswerObject> answers;

    public ResultQuestionObject() {
        answers=new ArrayList<ResultAnswerObject>();
    }

    public ResultQuestionObject(String content, List<ResultAnswerObject> answers) {
        this.content = content;
        this.answers = answers;
    }

    

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ResultAnswerObject> getAnswers() {
        return answers;
    }

    public void setAnswers(List<ResultAnswerObject> answers) {
        this.answers = answers;
    }
    
}
