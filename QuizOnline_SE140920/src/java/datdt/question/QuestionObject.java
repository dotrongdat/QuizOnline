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
public class QuestionObject extends QuestionDTO implements Serializable{
    private int numericalOrder;

    public QuestionObject() {
    }

    public QuestionObject(int numericalOrder, String questID, String subID, String content, Timestamp createDate, boolean status) {
        super(questID, subID, content, createDate, status);
        this.numericalOrder = numericalOrder;
    }

    public int getNumericalOrder() {
        return numericalOrder;
    }

    public void setNumericalOrder(int numericalOrder) {
        this.numericalOrder = numericalOrder;
    }

    @Override
    public int hashCode() {
        return numericalOrder;
    }
        
}
