/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.servlet;

import datdt.account.AccountDAO;
import datdt.resultsubject.ResultSubjectDAO;
import datdt.resultsubject.ResultSubjectDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Win
 */
@WebServlet(name = "SearchHistoryTakeQuizServlet", urlPatterns = {"/SearchHistoryTakeQuizServlet"})
public class SearchHistoryTakeQuizServlet extends HttpServlet {

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
        String url="Subject";
        ServletContext context=request.getServletContext();
        HashMap<String, String> authorizeMap = (HashMap<String, String>) context.getAttribute("AUTHORIZEMAP");
        url=authorizeMap.get(url);
        boolean existFault=false;
        try {
           String subId=request.getParameter("subID");
           if(subId!=null){
               ResultSubjectDAO rsDAO=new ResultSubjectDAO();
               rsDAO.getResultOfStudentsBySubId(subId);
               List<ResultSubjectDTO> rsList=rsDAO.getListDTO();
               AccountDAO accountDAO=new AccountDAO();
               
               HashMap<ResultSubjectDTO,String> dataMap=new HashMap<ResultSubjectDTO,String>();
               for (ResultSubjectDTO resultSubjectDTO : rsList) {
                   String email=resultSubjectDTO.getEmail();
                   String name=accountDAO.getName(email);
                   dataMap.put(resultSubjectDTO, name);
               }
               
               request.setAttribute("DATA", dataMap);
           }else{
                url="HomePage";
                existFault=true;
           }
        } catch (NamingException ex) {
            log("ERROR at PrepareHistoryTakeQuizServlet_Naming: "+ex.getMessage());
            url="HomePage";
            existFault=true;
        } catch (SQLException ex) {
            log("ERROR at PrepareHistoryTakeQuizServlet_SQLException: "+ex.getMessage());
            url="HomePage";
            existFault=true;
        }finally{
            if(existFault){
                response.sendRedirect(url);
            }else{
                RequestDispatcher rd=request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
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
