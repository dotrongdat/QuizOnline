/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.account;

import datdt.util.DBHelper;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Win
 */
public class AccountDAO implements Serializable{
    public AccountDTO authenticate(String email,String password) throws NamingException, SQLException, NoSuchAlgorithmException{
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            //1. make connection
            con=DBHelper.makeConnection();
            if(con!=null){
                //2. create SQL string
                String sql="Select name, admin "
                        + "From Account "
                        + "Where email=? and password=? and status=1";
                //3. create stament
                ps=con.prepareStatement(sql);
                ps.setString(1, email);
//                MessageDigest digest=MessageDigest.getInstance("SHA-256");
                String encryptPassword=DigestUtils.sha256Hex(password);
                ps.setString(2, encryptPassword);
                
                //4. excute query
                rs=ps.executeQuery();
                
                //5. check result
                if(rs.next()){
                    String name=rs.getString("name");
                    boolean admin=rs.getBoolean("admin");
                    AccountDTO dto=new AccountDTO(email, name,null, admin, true);                    
                    return dto;
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
        return null; 
    }
    public boolean insertAccount(String email, String password, String name) throws NamingException, SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        try{
            //1. make connection
            con=DBHelper.makeConnection();
            if(con!=null){
                //2. create SQL string
                String sql="Insert Into account "
                        + "(email,password,name,admin,status) "
                        + "Values (?,?,?,?,?)";
                //3. create stament
                ps=con.prepareStatement(sql);
                ps.setString(1, email);
                String encryptPassword=DigestUtils.sha256Hex(password);
                ps.setString(2,encryptPassword);
                ps.setString(3, name);
                ps.setBoolean(4, false);
                ps.setBoolean(5, true);
                
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
    public String getName(String email) throws SQLException, NamingException{
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            //1. make connection
            con=DBHelper.makeConnection();
            if(con!=null){
                //2. create SQL string
                String sql="Select name "
                        + "From Account "
                        + "Where email=? And status=1";
                //3. create stament
                ps=con.prepareStatement(sql);
                ps.setString(1, email);
                
                //4. excute query
                rs=ps.executeQuery();
                
                //5. check result
                if(rs.next()){
                    String name=rs.getString("name");                 
                    return name;
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
        return null; 
    }
}
