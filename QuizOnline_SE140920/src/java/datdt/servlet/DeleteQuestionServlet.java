/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.servlet;

import datdt.question.QuestionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author TRONG DAT
 */
@WebServlet(name = "DeleteQuestionServlet", urlPatterns = {"/DeleteQuestionServlet"})
public class DeleteQuestionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url="PrepareQuestionView";
        try {
           String questID=request.getParameter("questID");
           String subID=request.getParameter("subID");
           if(questID!=null && subID!=null){
               QuestionDAO questDAO=new QuestionDAO();
               boolean result= questDAO.updateStatusQuestion(questID, false);
               if(result){
                   url=url.concat("?subID="+subID+"&msg=Delete Sucessfully");
               }else{
                   url=url.concat("?subID="+subID+"&msg=Can't Delete");
               }
           }else{
               url="HomePage";
           }
        } catch (NamingException ex) {
            log("ERROR at DeleteQuestionServlet_Naming: "+ex.getMessage());
            url="ErrorPage?msg=Fail to connect to Server. Try again!";
        } catch (SQLException ex) {
            log("ERROR at DeleteQuestionServlet_Naming: "+ex.getMessage());
            url="ErrorPage?msg=Fail to connect to Server. Try again!";
        }finally{
            response.sendRedirect(url);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
