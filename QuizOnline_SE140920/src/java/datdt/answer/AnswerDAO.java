/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.answer;

import datdt.question.QuestionObject;
import datdt.util.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author TRONG DAT
 */
public class AnswerDAO implements Serializable {

    private HashMap<String, List<AnswerDTO>> mapAnswer;

    public HashMap<String, List<AnswerDTO>> getMapAnswer() {
        return mapAnswer;
    }

    public boolean insertAnswers(List<AnswerDTO> listAns) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Insert Into Answer "
                        + "(AnsID,QuestID,Content,CorrectAnswer) "
                        + "Values ";
                int totalAnswer = listAns.size();
                for (int i = 0; i < totalAnswer; i++) {
                    if (i < totalAnswer - 1) {
                        sql = sql.concat(" (?,?,?,?), ");
                    } else {
                        sql = sql.concat(" (?,?,?,?) ");
                    }
                }
                ps = con.prepareStatement(sql);
                int countAnswer = 0;
                    for (int i = 0; i < totalAnswer; i++) {
                        AnswerDTO answer=listAns.get(i);
                        ps.setString(countAnswer * 4 + 1, answer.getAnsID());
                        ps.setString(countAnswer * 4 + 2, answer.getQuestID());
                        ps.setString(countAnswer * 4 + 3, answer.getContent());
                        ps.setBoolean(countAnswer * 4 + 4, answer.isCorrectAns());
                        countAnswer++;
                    }
                int result = ps.executeUpdate();
                if (result == totalAnswer) {
                    return true;
                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return false;
    }

    public boolean insertAnswersToQuestion(String questID, List<AnswerDTO> listNewAnswers, int totalExistedAnswers) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Insert Into Answer "
                        + "(AnsID,QuestID,Content,CorrectAnswer) "
                        + "Values ";
                int totalAnswer = listNewAnswers.size();
                for (int i = 0; i < totalAnswer; i++) {
                    if (i < totalAnswer - 1) {
                        sql = sql.concat(" (?,?,?,?), ");
                    } else {
                        sql = sql.concat(" (?,?,?,?) ");
                    }
                }
                ps = con.prepareStatement(sql);
                for (int i = 0; i < totalAnswer; i++) {
                    String ansID = questID + "_" + (totalExistedAnswers + i + 1);
                    AnswerDTO answer = listNewAnswers.get(i);
                    String content = answer.getContent();
                    boolean correctAnswer = answer.isCorrectAns();
                    ps.setString(i * 4 + 1, ansID);
                    ps.setString(i * 4 + 2, questID);
                    ps.setString(i * 4 + 3, content);
                    ps.setBoolean(i * 4 + 4, correctAnswer);
                }

                int result = ps.executeUpdate();
                if (result == totalAnswer) {
                    return true;
                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return false;
    }

    public void getAnswersByListOfQuestions(List<QuestionObject> listQuest, boolean random) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Select AnsID,QuestID,Content,CorrectAnswer "
                        + "From Answer "
                        + "Where QuestID in ( ";
                int totalQuest = listQuest.size();
                for (int i = 0; i < totalQuest; i++) {
                    if (i < totalQuest - 1) {
                        sql = sql.concat("?,");
                    } else {
                        sql = sql.concat("?)");
                    }
                }
                if(random){
                    sql=sql.concat(" Order By NewID() ");
                }
                ps = con.prepareStatement(sql);
                for (int i = 0; i < totalQuest; i++) {
                    ps.setString(i + 1, listQuest.get(i).getQuestID());
                }
                rs = ps.executeQuery();
                this.mapAnswer = new HashMap<String, List<AnswerDTO>>();
                while (rs.next()) {
                    String ansID = rs.getString("AnsID");
                    String questID = rs.getString("QuestID");
                    String content = rs.getString("Content");
                    boolean correctAnwser = rs.getBoolean("CorrectAnswer");
                    AnswerDTO answer = new AnswerDTO(ansID, questID, content, correctAnwser);
                    List<AnswerDTO> answerList;
                    if (this.mapAnswer.containsKey(questID)) {
                        answerList = this.mapAnswer.get(questID);
                    } else {
                        answerList = new ArrayList<AnswerDTO>();
                    }
                    answerList.add(answer);
                    this.mapAnswer.put(questID, answerList);
                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }
    public boolean deleteAnswer(String ansID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql="Delete From Answer "
                        + "Where AnsID=?";
                ps=con.prepareStatement(sql);
                ps.setString(1, ansID);
                int result=ps.executeUpdate();
                if(result==1){
                    return true;
                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return false;
    }
    public boolean updateAnswer(String ansID,boolean correctAnswer,String content) throws SQLException, NamingException{
        Connection con=null;
        PreparedStatement ps=null;
        try{
            con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Update Answer "
                        + "Set Content=?, CorrectAnswer=? "
                        + "Where AnsID= ?";
                ps=con.prepareStatement(sql);
                ps.setString(1, content);
                ps.setBoolean(2, correctAnswer);
                ps.setString(3, ansID);
                
                int result=ps.executeUpdate();
                if(result==1){
                    return true;
                }
            }
        }finally{
            if(con!=null){
                con.close();
            }
            if(ps!=null){
                ps.close();
            }
        }
         return false;  
    }
    public int getLastID(String questID) throws NamingException, SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        int lastID=0;
        try{
            con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Select Top(1) AnsID "
                        + "From Answer "
                        + "Where questID=? "
                        + "Order By AnsID DESC";
                ps=con.prepareStatement(sql);
                ps.setString(1, questID);
                
                rs=ps.executeQuery();
                if(rs.next()){
                    String txtLastID=rs.getString("AnsID");
                    int pos=txtLastID.lastIndexOf("_");
                    lastID=Integer.parseInt(txtLastID.substring(pos+1));
                }
            }
        }finally{
            if(con!=null){
                con.close();
            }
            if(ps!=null){
                ps.close();
            }
            if(rs!=null){
                rs.close();
            }
            return lastID;
        }    
    }
}
