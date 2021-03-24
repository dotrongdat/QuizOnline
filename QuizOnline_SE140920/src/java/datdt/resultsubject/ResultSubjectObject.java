/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.resultsubject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author TRONG DAT
 */
public class ResultSubjectObject implements Serializable{
    private List<ResultQuestionObject> questions;

    public ResultSubjectObject() {
        questions=new ArrayList<ResultQuestionObject>();
    }
    public ResultSubjectObject(List<ResultQuestionObject> questions) {
        this.questions = questions;
    }

    public List<ResultQuestionObject> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ResultQuestionObject> questions) {
        this.questions = questions;
    }
    public String encode() throws IOException{
        ByteArrayOutputStream baos=null;
        ObjectOutputStream oos=null;
        try{
            baos=new ByteArrayOutputStream();
            oos=new ObjectOutputStream(baos);
            oos.writeObject(this);
            String data=Base64.encodeBase64String(baos.toByteArray());
            return data;
        }finally{
            if(baos!=null){
                baos.close();
            }
            if(oos!=null){
                oos.close();
            }
        }
    }
    public void decode(String data) throws IOException, ClassNotFoundException{
        ByteArrayInputStream bais=null;
        ObjectInputStream ois=null;
        try{
            byte[] byteData=Base64.decodeBase64(data);
            bais=new ByteArrayInputStream(byteData);
            ois=new ObjectInputStream(bais);
            
            ResultSubjectObject obj=(ResultSubjectObject)ois.readObject();
            this.questions=obj.getQuestions();
        }finally{
            if(bais!=null){
                bais.close();
            }
            if(ois!=null){
                ois.close();
            }
        }
    }
}
