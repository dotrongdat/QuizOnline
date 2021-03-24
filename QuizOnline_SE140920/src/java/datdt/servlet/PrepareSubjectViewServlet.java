/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.servlet;

import datdt.account.AccountDTO;
import datdt.question.QuestionDAO;
import datdt.resultsubject.ResultSubjectDAO;
import datdt.subject.SubjectDAO;
import datdt.subject.SubjectDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author TRONG DAT
 */
@WebServlet(name = "PrepareSubjectViewServlet", urlPatterns = {"/PrepareSubjectViewServlet"})
public class PrepareSubjectViewServlet extends HttpServlet {

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
        String url="SubjectView"; // get resource from map
        ServletContext context=request.getServletContext();
        HashMap<String, String> authorizeMap = (HashMap<String, String>) context.getAttribute("AUTHORIZEMAP");
        url=authorizeMap.get(url);
        boolean existFault=false;
        HttpSession session=request.getSession();
        try {
            AccountDTO account=(AccountDTO)session.getAttribute("ACCOUNT");
           String subID=request.getParameter("subID");
           if(subID!=null){
               SubjectDAO subDAO=new SubjectDAO();
               SubjectDTO subject=subDAO.getSubjectByID(subID);
               long duration=subject.getDuraion();
               long hour=duration/(60*60*1000);
               long minute=(duration-hour*(60*60*1000))/(60*1000);
               request.setAttribute("HOUR", hour);
               request.setAttribute("MIN", minute);
               request.setAttribute("SUBJECT", subject);
               if(!account.isAdmin()){
                   String email=account.getEmail();
                   ResultSubjectDAO rsSubDAO=new ResultSubjectDAO();
                   QuestionDAO questDAO=new QuestionDAO();
                   boolean checkCompleteQuiz=rsSubDAO.checkCompleteQuiz(email, subID);
                   boolean checkAllowQuiz=questDAO.countExistedQuestionsBySubject(subID)>0;
                   request.setAttribute("CHECKCOMPLETEQUIZ", checkCompleteQuiz);
                   request.setAttribute("CHECKALLOWQUIZ", checkAllowQuiz);
               }
           }else{
               url="HomePage";
               existFault=true;
           }
        } catch (SQLException ex) {
            log("ERROR at PrepareQuizDetailViewServlet_SQL: "+ex.getMessage());
            url="ErrorPage?msg=Fail to connect to server";
            existFault=true;
        } catch (NamingException ex) {
            log("ERROR at PrepareQuizDetailViewServlet_Naming: "+ex.getMessage());
            url="ErrorPage?msg=Fail to connect to server";
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
