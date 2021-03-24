/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.question;

import datdt.util.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author TRONG DAT
 */
public class QuestionDAO implements Serializable{
    private HashMap<String,List<QuestionObject>> mapQuest;
    private List<QuestionObject> listQuest;
    private final int QUESTION_PER_PAGE=5;

    public HashMap<String,List<QuestionObject>> getMapQuest() {
        return mapQuest;
    }

    public List<QuestionObject> getListQuest() {
        return listQuest;
    }

    
    
    public boolean insertQuestions(List<QuestionDTO> listQuesttion) throws NamingException, SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        
        try{
            con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Insert into Question "
                        + "(QuestID, SubID,Content,CreateDate, Status)"
                        + "Values ";
                int totalQuest=listQuesttion.size();
                for (int i = 0; i < totalQuest; i++) {
                    if(i!=totalQuest-1){
                        sql=sql.concat(" (?,?,?,?,1), ");
                    }else{
                        sql=sql.concat(" (?,?,?,?,1) ");
                    }
                    
                }
                ps=con.prepareStatement(sql);
                for (int i = 0; i < totalQuest; i++) {
                    QuestionDTO quest=listQuesttion.get(i);
                    
                    ps.setString(i*4 +1, quest.getQuestID());
                    ps.setString(i*4 +2, quest.getSubID());
                    ps.setString(i*4 +3, quest.getContent());
                    ps.setTimestamp(i*4+4, quest.getCreateDate());
                }
                int result=ps.executeUpdate();
                if(result==totalQuest){
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
    public boolean updateQuestion(String questID,String content, boolean status) throws NamingException, SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        
        try{
            con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Update Question "
                        + "Set Content=? , Status=? "
                        + "Where QuestID =  ?";
                
                ps=con.prepareStatement(sql);
                ps.setString(1, content);
                ps.setBoolean(2, status);
                ps.setString(3, questID);
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
    
    public boolean updateStatusQuestion(String questID, boolean status) throws NamingException, SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        
        try{
            con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Update Question "
                        + "Set Status=? "
                        + "Where QuestID =  ?";
                
                ps=con.prepareStatement(sql);
                ps.setBoolean(1, status);
                ps.setString(2, questID);
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
    public void getQuestionsBySubject(String subID,int page, boolean random, boolean paging) throws NamingException, SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Select QuestID, SubID, Content, CreateDate, Status "
                        + "From Question "
                        + "Where SubID=? And  Status = 1 ";
                if (random){
                    sql=sql.concat(" Order By NewID() ");
                }else{
                    sql=sql.concat(" Order By QuestID ");
                }
                if(paging){
                sql=sql.concat( "Offset ? Rows "
                              + "Fetch Next ? Rows Only ");
                }
                ps=con.prepareStatement(sql);
                ps.setString(1, subID);
                if(paging){
                    ps.setInt(3, this.QUESTION_PER_PAGE);
                    ps.setInt(2, QUESTION_PER_PAGE*(page-1));
                }     
                rs=ps.executeQuery(); 
                this.listQuest=new ArrayList<QuestionObject>();
                while(rs.next()){
                    String questID=rs.getString("QuestID");
                    String content=rs.getString("Content");
                    Timestamp createDate=rs.getTimestamp("CreateDate");
                    boolean status=rs.getBoolean("Status");
                    int numbercalOrder=rs.getRow();
                    QuestionObject quest=new QuestionObject(numbercalOrder,questID, subID, content, createDate, status);
                    this.listQuest.add(quest);
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
    }
     public void getQuestionsBySubject(String subID) throws NamingException, SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Select QuestID, SubID, Content, CreateDate, Status "
                        + "From Question "
                        + "Where SubID=? And  Status = 1 ";             
                ps=con.prepareStatement(sql);
                ps.setString(1, subID);
                rs=ps.executeQuery(); 
                this.listQuest=new ArrayList<QuestionObject>();
                while(rs.next()){
                    String questID=rs.getString("QuestID");
                    String content=rs.getString("Content");
                    Timestamp createDate=rs.getTimestamp("CreateDate");
                    int numbercalOrder=rs.getRow();
                    QuestionObject quest=new QuestionObject(numbercalOrder,questID, subID, content, createDate, true);

                    this.listQuest.add(quest);
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
    }
    public int countTotal(String content, String subName,boolean status) throws SQLException, NamingException{
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Select Count(q.QuestID) as quantity "
                        + "From Question q, Subject s "
                        + "Where q.SubID=s.SubID And q.Content like ? And s.SubName like ? And q.Status = ? ";
                
                ps=con.prepareStatement(sql);
                ps.setString(1, "%"+content+"%");
                ps.setString(2, "%"+subName+"%");
                ps.setBoolean(3, status);
                
                rs=ps.executeQuery();
                if(rs.next()){
                    int quantity=rs.getInt("quantity");
                    int totalPage=(int)Math.ceil((double)quantity/QUESTION_PER_PAGE);
                    return totalPage;
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
        return 0;
    }
    public void getQuestionsBySearching(String content, String subName,boolean status, int page) throws SQLException, NamingException{     
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        
        try{
            con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Select q.QuestID, q.SubID, q.Content, q.CreateDate, q.Status "
                        + "From Question q, Subject s "
                        + "Where q.SubID=s.SubID And q.Content like ? And s.SubName like ? And q.Status = ? "
                        + "Group By q.QuestID, q.SubID, q.Content, q.CreateDate, q.Status "
                        + "Order By q.QuestID, q.Content "
                        + "Offset ? Rows "
                        + "Fetch Next ? Rows Only ";
                
                ps=con.prepareStatement(sql);
                ps.setString(1, "%"+content+"%");
                ps.setString(2, "%"+subName+"%");
                ps.setBoolean(3, status);
                ps.setInt(4, QUESTION_PER_PAGE*(page-1));
                ps.setInt(5, this.QUESTION_PER_PAGE);
                
                rs=ps.executeQuery();
                this.mapQuest=new HashMap<String,List<QuestionObject>>();
                while(rs.next()){
                    String questID=rs.getString("QuestID");
                    String subID=rs.getString("SubID");
                    content=rs.getString("Content");
                    Timestamp createDate=rs.getTimestamp("CreateDate");
                    int numbercalOrder=rs.getRow();
                    QuestionObject quest=new QuestionObject(numbercalOrder,questID, subID, content,createDate, status);
                    List<QuestionObject> questions;
                    if(this.mapQuest.containsKey(subID)){
                        questions=this.mapQuest.get(subID);
                       
                    }else{
                        questions=new ArrayList<QuestionObject>();
                        
                    }
                     questions.add(quest);
                        this.mapQuest.put(subID, questions);
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
        }  
    }
    public int countExistedQuestionsBySubject(String subID) throws NamingException, SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null; 
        int quantity=0;
        try{
            con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Select Count(QuestID) as Quantity "
                        + "From Question "
                        + "Where SubID=?";
                
                ps=con.prepareStatement(sql);
                ps.setString(1, subID);
                rs=ps.executeQuery();
                if(rs.next()){
                    quantity=rs.getInt("Quantity");
                }
            }
        }finally{
            if(con!=null){
                con.close();
            }
            if(ps!=null){
                ps.close();
            }
            return quantity;
        }
    }
    
}
