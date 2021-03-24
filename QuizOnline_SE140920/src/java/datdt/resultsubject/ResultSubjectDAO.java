/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.resultsubject;

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
public class ResultSubjectDAO implements Serializable{
    private List<ResultSubjectDTO> listDTO;

    public List<ResultSubjectDTO> getListDTO() {
        return listDTO;
    }
    
    public ResultSubjectDTO getResult(String email,String subID) throws NamingException, SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Select Mark, DateTakeQuiz, DurationTakeQuiz, Data "
                        + "From ResultSubject "
                        + "Where email=? And subID=? ";
                ps=con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, subID);
                rs=ps.executeQuery();
                if(rs.next()){
                    double mark=rs.getDouble("Mark");
                    Timestamp dateTakeQuiz=rs.getTimestamp("DateTakeQuiz");
                    long durationTakeQuiz=rs.getLong("DurationTakeQuiz");
                    String data=rs.getString("Data");
                    ResultSubjectDTO dto=new ResultSubjectDTO(subID, email, mark, dateTakeQuiz,durationTakeQuiz, data);
                    return dto;
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
        return null;
    }
    public boolean insertResult(String subID,String email,Timestamp dateTakeQuiz, String data) throws NamingException, SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        try{
             con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Insert Into ResultSubject "
                        + "(SubID,Email,Mark,DateTakeQuiz,DurationTakeQuiz,Data) "
                        + "Values (?,?,0.0,?,0,?)";
                ps=con.prepareStatement(sql);
                ps.setString(1, subID);
                ps.setString(2, email);
                ps.setTimestamp(3, dateTakeQuiz);
                ps.setString(4, data);
                
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
    public boolean updateResult(String subID,String email,double mark,long durationTakeQuiz, String data) throws NamingException, SQLException{
         Connection con=null;
        PreparedStatement ps=null;
        try{
            con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Update ResultSubject "
                        + "Set Mark=?, DurationTakeQuiz=?, Data=? "
                        + "Where SubID=? And email=?";
                ps=con.prepareStatement(sql);
                ps.setDouble(1, mark);
                ps.setLong(2, durationTakeQuiz);
                ps.setString(3, data);
                ps.setString(4, subID);
                ps.setString(5, email);
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
    public boolean checkCompleteQuiz(String email,String subID) throws NamingException, SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        
        try{
            con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Select SubID "
                        + "From ResultSubject "
                        + "Where email=? And subID=? ";
                ps=con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, subID);
                rs=ps.executeQuery();
                if(rs.next()){
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
            if(rs!=null){
                rs.close();
            }
        }
        return false;
    }
    public void getResultOfStudentsBySubId(String subId) throws NamingException, SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        
        try{
            con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Select Email, Mark, DateTakeQuiz, DurationTakeQuiz, Data "
                        + "From ResultSubject "
                        + "Where subID=? ";
                ps=con.prepareStatement(sql);
                ps.setString(1, subId);
                rs=ps.executeQuery();
                while(rs.next()){
                    String email=rs.getString("Email");
                    double mark=rs.getDouble("Mark");
                    Timestamp dateTakeQuiz=rs.getTimestamp("DateTakeQuiz");
                    long durationTakeQuiz=rs.getLong("DurationTakeQuiz");
                    String data=rs.getString("Data");
                    ResultSubjectDTO dto=new ResultSubjectDTO(subId, email, mark, dateTakeQuiz, durationTakeQuiz, data);
                    if(this.listDTO==null){
                        this.listDTO=new ArrayList<ResultSubjectDTO>();
                    }
                    this.listDTO.add(dto);
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
}
