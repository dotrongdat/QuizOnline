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
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "MarkQuizServlet", urlPatterns = {"/MarkQuizServlet"})
public class MarkQuizServlet extends HttpServlet {

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
        String url="Result";
        ServletContext context=request.getServletContext();
        HashMap<String, String> authenMap = (HashMap<String, String>) context.getAttribute("AUTHENMAP");
        url=authenMap.get(url);
        boolean existFault=false;
        HttpSession session=request.getSession(true);
        try {
            AccountDTO account=(AccountDTO) session.getAttribute("ACCOUNT");
            String email=account.getEmail();
           String subID=request.getParameter("subID");           
           String txtDuration=request.getParameter("txtDuration");
           
            
            HashSet<String> answerSet=new HashSet<String>();
            Map<String,String[]> mapParam=request.getParameterMap();
           for (String paramName : mapParam.keySet()) {
                if(paramName.matches("Answer.+")){
                    answerSet.add(mapParam.get(paramName)[0]);
                }
            }
           if(subID!=null){
               long durationTakeQuiz=Long.parseLong(txtDuration);
               List<QuestionObject> questions=(List<QuestionObject>)session.getAttribute("LISTQUEST");               
               HashMap<String, List<AnswerDTO>> mapAnswers=(HashMap<String, List<AnswerDTO>>)session.getAttribute("MAPANSWER");
               if(questions!=null && mapAnswers!=null){
               double mark=0.0;
               int totalQuest=questions.size();
               
               
               ResultSubjectObject rsSub=new ResultSubjectObject();
               for (QuestionObject question : questions) {
                   ResultQuestionObject quest=new ResultQuestionObject();
                   String content=question.getContent();
                   quest.setContent(content);
                   List<ResultAnswerObject> listAns=new ArrayList<ResultAnswerObject>();
                   List<AnswerDTO> listRsAns=mapAnswers.get(question.getQuestID());
                   
                   boolean correct=false; 
                   for (AnswerDTO rsAns : listRsAns) {
                       String ansContent=rsAns.getContent();
                       boolean correctAnswer=false;
                       boolean choosen=false;
                       if(rsAns.isCorrectAns()){
                           correctAnswer=true;
                       }
                       if(answerSet.contains(rsAns.getAnsID())){
                           choosen=true;
                       }
                       if(correctAnswer && choosen){
                           correct=true;
                       }
                       ResultAnswerObject ans=new ResultAnswerObject(ansContent, correctAnswer, choosen);
                       quest.getAnswers().add(ans);                       
                   }
                   if(correct){
                      mark+=(double)10/totalQuest; 
                   }                   
                   rsSub.getQuestions().add(quest);
               }
               String data=rsSub.encode();               
               ResultSubjectDAO rsSubDAO=new ResultSubjectDAO();               
               boolean result= rsSubDAO.updateResult(subID, email, mark, durationTakeQuiz, data);
               if(result){
                   url=url.concat("?subID="+subID);
               }else{
                   url="HomePage?msg=Fail to connect to Server";
                    existFault=true;
               }
               }
               
           }else{
                url="HomePage";
                existFault=true;
           }
        }catch(IOException ex){
           log("ERROR at MarkQuizServlet_IO: "+ex.getMessage()); 
           url="HomePage?msg=Someting wrong happen";
            existFault=true;
        }
        catch(NumberFormatException ex){
            url="HomePage";
            existFault=true;
        } catch (NamingException ex) {
            log("ERROR at MarkQuizServlet_Naming: "+ex.getMessage());
             url="HomePage?msg=Fail to connect to Server";
            existFault=true;
        } catch (SQLException ex) {
            log("ERROR at MarkQuizServlet_SQL: "+ex.getMessage());
             url="HomePage?msg=Fail to connect to Server";
            existFault=true;
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
