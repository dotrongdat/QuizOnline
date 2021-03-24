/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.servlet;

import datdt.answer.AnswerDAO;
import datdt.answer.AnswerDTO;
import datdt.question.QuestionDAO;
import datdt.question.QuestionObject;
import datdt.subject.SubjectDAO;
import datdt.subject.SubjectDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
 * @author TRONG DAT
 */
@WebServlet(name = "PrepareQuestionViewServlet", urlPatterns = {"/PrepareQuestionViewServlet"})
public class PrepareQuestionViewServlet extends HttpServlet {

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
        String url="SubjectDetailView";  //get from map ********
        ServletContext context=request.getServletContext();
        HashMap<String, String> authorizeMap = (HashMap<String, String>) context.getAttribute("AUTHORIZEMAP");
        url=authorizeMap.get(url);
        boolean existFault=false;
        try {
           String subID=request.getParameter("subID");
           if(subID!=null){
               SubjectDAO subDAO=new SubjectDAO();
               String subName=subDAO.getSubjectName(subID);
               HashMap<QuestionObject,List<AnswerDTO>> data=new HashMap<QuestionObject,List<AnswerDTO>>();
               QuestionDAO questDAO=new QuestionDAO();
               questDAO.getQuestionsBySubject(subID, 0, false,false);
               List<QuestionObject> listQuest=questDAO.getListQuest();
               if(listQuest.size()>0){
               AnswerDAO ansDAO=new AnswerDAO();
               ansDAO.getAnswersByListOfQuestions(listQuest,false);
               HashMap<String,List<AnswerDTO>> mapAns=ansDAO.getMapAnswer();
               for (QuestionObject question : listQuest) {
                   data.put(question, mapAns.get(question.getQuestID()));
               }
               }             
               request.setAttribute("SUBNAME", subName);
               request.setAttribute("DATA", data);
           }else{
                existFault=true;
                url="HomePage";
           }
        } catch (NamingException ex) {
            log("ERROR at PrepareQuestionViewServlet_Naming: "+ex.getMessage());
            url="ErrorPage?msg=Fail to connect to server";
            existFault=true;
        } catch (SQLException ex) {
            log("ERROR at PrepareQuestionViewServlet_SQL: "+ex.getMessage());
            url="ErrorPage?msg=Fail to connect to server";
            existFault=true;
        }catch(NumberFormatException ex){
            existFault=true;
            url="HomePage";
        }
        finally{
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
