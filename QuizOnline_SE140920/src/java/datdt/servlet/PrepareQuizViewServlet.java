/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.servlet;

import datdt.account.AccountDTO;
import datdt.answer.AnswerDAO;
import datdt.answer.AnswerDTO;
import datdt.question.QuestionDAO;
import datdt.question.QuestionObject;
import datdt.resultsubject.ResultAnswerObject;
import datdt.resultsubject.ResultQuestionObject;
import datdt.resultsubject.ResultSubjectDAO;
import datdt.resultsubject.ResultSubjectObject;
import datdt.subject.SubjectDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author TRONG DAT
 */
@WebServlet(name = "PrepareQuizViewServlet", urlPatterns = {"/PrepareQuizViewServlet"})
public class PrepareQuizViewServlet extends HttpServlet {

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
        String url="QuizView"; // get resource from map *********
        ServletContext context=request.getServletContext();
        HashMap<String, String> authenMap = (HashMap<String, String>) context.getAttribute("AUTHENMAP");
        url=authenMap.get(url);
        boolean existFault=false;
        HttpSession session=request.getSession();
        try {
            String subID=request.getParameter("subID");
            if(subID!=null){
                AccountDTO account=(AccountDTO)session.getAttribute("ACCOUNT");
                String email=account.getEmail();
                ResultSubjectDAO rsSubDAO=new ResultSubjectDAO();
                if(!rsSubDAO.checkCompleteQuiz(email, subID)){
                SubjectDAO subDAO=new SubjectDAO();
                long duration=subDAO.getDuration(subID);
                QuestionDAO questDAO=new QuestionDAO();
                questDAO.getQuestionsBySubject(subID, 1, true,false);
                List<QuestionObject> listQuest=questDAO.getListQuest();
                
                AnswerDAO ansDAO=new AnswerDAO();
                ansDAO.getAnswersByListOfQuestions(listQuest,true);
                HashMap<String,List<AnswerDTO>> mapAns= ansDAO.getMapAnswer();
                
                HashMap<QuestionObject,List<AnswerDTO>> data=new HashMap<QuestionObject,List<AnswerDTO>>();
                for (QuestionObject question : listQuest) {
                   data.put(question, mapAns.get(question.getQuestID()));
                }
                ResultSubjectObject subObj=new ResultSubjectObject();
                List<ResultQuestionObject> listQuestObj=new ArrayList<ResultQuestionObject>();
                for (QuestionObject questionObject : listQuest) {
                    ResultQuestionObject questObj=new ResultQuestionObject();
                    questObj.setContent(questionObject.getContent());
                    List<AnswerDTO> listAns=mapAns.get(questionObject.getQuestID());
                    List<ResultAnswerObject> listRsAnsObj=new ArrayList<ResultAnswerObject>();
                    for (AnswerDTO listAn : listAns) {
                        ResultAnswerObject rsAnsObj=new ResultAnswerObject(listAn.getContent(), listAn.isCorrectAns(), false);
                        listRsAnsObj.add(rsAnsObj);
                    }
                    questObj.setAnswers(listRsAnsObj);
                    listQuestObj.add(questObj);
                }
                subObj.setQuestions(listQuestObj);
                String rsData=subObj.encode();
                
                Date date=new Date();
                Timestamp dateTakeQuiz=new Timestamp(date.getTime());
                rsSubDAO.insertResult(subID, email,dateTakeQuiz,rsData);
                session.setAttribute("LISTQUEST", listQuest);
                session.setAttribute("MAPANSWER", mapAns);
                long maxInteractiveInterval=duration/1000+60*5;
                request.setAttribute("DURATION", duration/1000);
                session.setMaxInactiveInterval((int)maxInteractiveInterval);
                request.setAttribute("DATA", data);
                }else{
                    existFault=true;
                    url="HomePage";
                }
            }else{
                existFault=true;
                url="HomePage";
            }
        } catch (NamingException ex) {
            log("ERROR at PrepareQuizViewServlet_Naming: "+ex.getMessage());
            url="ErrorPage?msg=Fail to connect to server";
            existFault=true;
        } catch (SQLException ex) {
            log("ERROR at PrepareQuizViewServlet_SQL: "+ex.getMessage());
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
