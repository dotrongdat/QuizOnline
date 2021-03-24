/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.subject;

import datdt.account.AccountDTO;
import datdt.answer.AnswerDTO;
import datdt.util.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.naming.NamingException;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author TRONG DAT
 */
public class SubjectDAO implements Serializable{
    private List<SubjectDTO> subList;

    public List<SubjectDTO> getSubList() {
        return subList;
    }
    
    public boolean insertSubject(String subID, String subCodeName,String subName,Timestamp createDate,long duration) throws NamingException, SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        try{
            //1. make connection
            con=DBHelper.makeConnection();
            if(con!=null){
                //2. create SQL string
                String sql="Insert Into Subject "
                        + "(subID,subCodeName,subName,createDate,duration,status) "
                        + "Values (?,?,?,?,?,?)";
                //3. create stament
                ps=con.prepareStatement(sql);
                ps.setString(1, subID);
                ps.setString(2, subCodeName);
                ps.setString(3,subName);
                ps.setTimestamp(4, createDate);
                ps.setLong(5, duration);
                ps.setBoolean(6, true);
                
                //4. excute query
                int row=ps.executeUpdate();
                
                //5. check result
                if(row==1){
                   return true;
                }//end if 1 row is affected
                    
            }//end if connection connected
        }finally{
            if(ps!=null){
                ps.close();
            }//end if prepared statement not null
            if(con!=null){
                con.close();
            }//end if connnection not null
        }
        return false;
    }
    public boolean checkDuplicate(String subCodeName) throws NamingException, SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            //1. make connection
            con=DBHelper.makeConnection();
            if(con!=null){
                //2. create SQL string
                String sql="Select subCodeName "
                        + "From Subject "
                        + "Where SubCodeName=?";
                //3. create stament
                ps=con.prepareStatement(sql);
                ps.setString(1, subCodeName);
                
                //4. excute query
                rs=ps.executeQuery();
                
                //5. check result
                if(rs.next()){
                    return false;
                }//end if result return not empty
                    
            }//end if connection connected
        }finally{
            if(rs!=null){
                rs.close();
            }//end if result set not null
            if(ps!=null){
                ps.close();
            }//end if prepared statement not null
            if(con!=null){
                con.close();
            }//end if connnection not null
        }
        return true; 
    }
    public void getSubjectsByName(String subNameSearch,String email) throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Select rs.SubID,s.SubCodeName,s.SubName,s.CreateDate,s.Duration,s.Status "
                        + "From Subject s, ResultSubject rs "
                        + "Where s.SubID=rs.SubID And s.SubName  like ? And rs.Email=? And s.Status=1 "
                        + "Group By rs.SubID,s.SubCodeName,s.SubName,s.CreateDate,s.Duration,s.Status "
                        + "Order By s.SubCodeName";
                ps=con.prepareStatement(sql);
                ps.setString(1, "%"+subNameSearch+"%");
                ps.setString(2, email);
                rs=ps.executeQuery();
                this.subList=new ArrayList<SubjectDTO>();
                if(rs.next()){
                    String subID=rs.getString("SubID");
                    String subCodeName = rs.getString("SubCodeName");
                    String subName = rs.getString("SubName");
                    Timestamp createDate=rs.getTimestamp("CreateDate");
                    long duration=rs.getLong("Duration");
                    boolean status=rs.getBoolean("Status");
                    SubjectDTO subject=new SubjectDTO(subID, subCodeName, subName, createDate, duration, status);
                    this.subList.add(subject);
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
    public SubjectDTO getSubjectByID(String subID) throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Select SubID,SubCodeName,SubName,CreateDate,Duration,Status "
                        + "From Subject "
                        + "Where SubID=? And Status=1";                
                ps=con.prepareStatement(sql);
                ps.setString(1, subID);
                rs=ps.executeQuery();
                if(rs.next()){
                    String subCodeName = rs.getString("SubCodeName");
                    String subName = rs.getString("SubName");
                    Timestamp createDate=rs.getTimestamp("CreateDate");
                    long duration=rs.getLong("Duration");
                    boolean status=rs.getBoolean("Status");
                    SubjectDTO subject=new SubjectDTO(subID, subCodeName, subName, createDate, duration, status);
                    return subject;
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
        return null;
    }
    
    public void getSubjectsByID(Set<String> subIDList) throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Select SubID,SubCodeName,SubName,CreateDate,Duration,Status "
                        + "From Subject "
                        + "Where SubID in ( ";
                int totalSub = subIDList.size();
                for (int i = 0; i < totalSub; i++) {
                    if (i < totalSub - 1) {
                        sql = sql.concat("?,");
                    } else {
                        sql = sql.concat("?)");
                    }
                }
                ps = con.prepareStatement(sql);
                int temp=1;
                for (String subID : subIDList) {
                    ps.setString(temp++, subID);
                }
//                for (int i = 0; i < totalSub; i++) {
//                    ps.setString(i + 1, subIDList.get(i));
//                }
                rs = ps.executeQuery();
                this.subList=new ArrayList<SubjectDTO>();
                while (rs.next()) {
                    String subID = rs.getString("SubID");
                    String subCodeName = rs.getString("SubCodeName");
                    String subName = rs.getString("SubName");
                    Timestamp createDate=rs.getTimestamp("CreateDate");
                    long duration=rs.getLong("Duration");
                    boolean status=rs.getBoolean("Status");
                    SubjectDTO subject=new SubjectDTO(subID, subCodeName, subName, createDate, duration, status);
                    this.subList.add(subject);                    
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
    public long getDuration(String subID) throws NamingException, SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Select Duration "
                        + "From Subject "
                        + "Where SubID= ? And Status=1";
                ps=con.prepareStatement(sql);
                ps.setString(1, subID);
                rs=ps.executeQuery();
                if(rs.next()){
                    long duration=rs.getLong("Duration");
                    return duration;
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
        return 0;
    }
    public String getSubjectName(String subID) throws NamingException, SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Select SubName "
                        + "From Subject "
                        + "Where SubID= ? And Status=1";
                ps=con.prepareStatement(sql);
                ps.setString(1, subID);
                rs=ps.executeQuery();
                if(rs.next()){
                    String subName=rs.getString("SubName");
                    return subName;
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
    public void getAllSubjects() throws NamingException, SQLException{
        Connection con=null;
        Statement stm=null;
        ResultSet rs=null;
        try{
            //1. make connection
            con=DBHelper.makeConnection();
            if(con!=null){
                //2. create SQL string
                String sql="Select subID,subCodeName,subName,createDate,duration,status "
                        + "From Subject "
                        + "Where status=1";
                //3. create stament
                    stm=con.createStatement();
                //4. excute query
                rs=stm.executeQuery(sql);
                
                //5. check result
                this.subList=new ArrayList<SubjectDTO>();
                while(rs.next()){
                    String subID=rs.getString("subID");
                    String subCodeName=rs.getString("subCodeName");
                    String subName=rs.getString("subName");
                    Timestamp createDate=rs.getTimestamp("createDate");
                    long duration=rs.getLong("duration");
                    boolean status=rs.getBoolean("status");
                    SubjectDTO dto=new SubjectDTO(subID,subCodeName, subName, createDate, duration, status);
                    subList.add(dto);
                }//end if result return not empty
                    
            }//end if connection connected
        }finally{
            if(rs!=null){
                rs.close();
            }//end if result set not null
            if(stm!=null){
                stm.close();
            }//end if prepared statement not null
            if(con!=null){
                con.close();
            }//end if connnection not null
        }
    }
    public boolean updateSubject(String subID,String subName, long duration, boolean status) throws NamingException, SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        try{
            //1. make connection
            con=DBHelper.makeConnection();
            if(con!=null){
                //2. create SQL string
                String sql="Update  Subject "
                        + "Set subName=?,  duration=?, status=? "
                        + "Where subID=?";
                //3. create stament
                ps=con.prepareStatement(sql);
                ps.setString(1, subName);
                ps.setLong(2,duration);
                ps.setBoolean(3, status);
                ps.setString(4, subID);
                
                //4. excute query
                int row=ps.executeUpdate();
                
                //5. check result
                if(row==1){
                   return true;
                }//end if 1 row is affected
                    
            }//end if connection connected
        }finally{
            if(ps!=null){
                ps.close();
            }//end if prepared statement not null
            if(con!=null){
                con.close();
            }//end if connnection not null
        }
        return false;
    }
    public int countExistedSubject() throws NamingException, SQLException{
        Connection con=null;
        Statement stm=null;
        ResultSet rs=null;
        int count=0;
        try{
            con=DBHelper.makeConnection();
            if(con!=null){
                String sql="Select Count(SubID) as quantity "
                        + "From Subject ";
                stm=con.createStatement();
                rs=stm.executeQuery(sql);
                if(rs.next()){
                    count=rs.getInt("quantity");
                }
            }
        }finally{
            if(con!=null){
                con.close();
            }
            if(stm!=null){
                stm.close();
            }
            if(rs!=null){
                rs.close();
            }
            return count;
        }
    }
}
