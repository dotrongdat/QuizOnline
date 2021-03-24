/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.servlet;

import datdt.answer.AnswerDAO;
import datdt.question.QuestionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
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
@WebServlet(name = "UpdateQuestionAnswerServlet", urlPatterns = {"/UpdateQuestionAnswerServlet"})
public class UpdateQuestionAnswerServlet extends HttpServlet {

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
            String subID=request.getParameter("subID");
            Map<String,String[]> paramMap=request.getParameterMap();
            HashSet<String>correctAnswerSet=new HashSet<String>();           
            QuestionDAO questDAO=new QuestionDAO();
            AnswerDAO ansDAO=new AnswerDAO();
            boolean result=true;
            for (String paramName : paramMap.keySet()) {
                if(paramName.matches("Answer.+")){
                    correctAnswerSet.add(paramMap.get(paramName)[0]);
                }
            }
            for (String paramName : paramMap.keySet()) {
                if(paramName.matches(subID+"[_][0-9]+")){
                    String questID=paramName;
                    String content=paramMap.get(paramName)[0];
                    result= questDAO.updateQuestion(questID, content, true);
                }else if(paramName.matches(subID+"[_][0-9]+[_][0-9]+")){
                    String ansID=paramName;
                    String content=paramMap.get(paramName)[0];
                    boolean correct=correctAnswerSet.contains(ansID);
                    result= ansDAO.updateAnswer(ansID, correct, content);
                }
            }
            url=url.concat("?subID="+subID);
            if(result){
                url=url.concat("&msg=Update success");
            }
            else{
                url=url.concat("&msg=Fail to Update");
            }                
        } catch (NamingException ex) {
            log("ERROR at UpdateQuestionAnswerServlet_Naming: "+ex.getMessage());
            url="ErrorPage?msg=Fail to connect to server";
        } catch (SQLException ex) {
            log("ERROR at UpdateQuestionAnswerServlet_SQL: "+ex.getMessage());
            url="ErrorPage?msg=Fail to connect to server";
        }
        finally{
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
