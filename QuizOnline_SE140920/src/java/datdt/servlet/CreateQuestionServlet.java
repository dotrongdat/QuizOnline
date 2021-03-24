/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.servlet;

import datdt.answer.AnswerDAO;
import datdt.answer.AnswerDTO;
import datdt.question.QuestionDAO;
import datdt.question.QuestionDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author TRONG DAT
 */
@WebServlet(name = "CreateQuestionServlet", urlPatterns = {"/CreateQuestionServlet"})
public class CreateQuestionServlet extends HttpServlet {

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
        try{
           Map<String,String[]> mapParam=request.getParameterMap();
            int orderOfQuestion=0;
            String subID=request.getParameter("subID");
            if(subID!=null){
                
            HashMap<String,String> mapQuestionGetter=new HashMap<String,String>(); //questOrder - questContent
            
            HashMap<String,HashMap<String,String>> mapAnswerGetter=new HashMap<String,HashMap<String,String>>(); //questOrder - (AnswerOrder-AnswerContent)
                           
            HashSet<String> setCorrectAnswer=new HashSet<String>();
            for (String  paramName: mapParam.keySet()) {
                if(paramName.matches("[0-9]+")){
                    mapQuestionGetter.put(paramName, mapParam.get(paramName)[0]);
                }else if(paramName.matches("[0-9]+[_][0-9]+")){
                    String[] sqlitStr=paramName.split("_");
                    String questOrder=sqlitStr[0];
                    HashMap<String,String> answer;
                    if(mapAnswerGetter.containsKey(questOrder)){
                        answer=mapAnswerGetter.get(questOrder);                                                                        
                    }else{
                        answer=new HashMap<String,String>(); 
                    }
                    answer.put(paramName,mapParam.get(paramName)[0]);
                    mapAnswerGetter.put(questOrder, answer);
                }else if(paramName.matches("Answer.+")){
                    setCorrectAnswer.add(mapParam.get(paramName)[0]);
                }
            }
            HashMap<QuestionDTO,List<AnswerDTO>> mapData=new HashMap<QuestionDTO,List<AnswerDTO>>();
            
            List<QuestionDTO> listQuestToDataBase=new ArrayList<QuestionDTO>();
            List<AnswerDTO> listAnswerToDataBase=new ArrayList<AnswerDTO>();
            QuestionDAO questDAO=new QuestionDAO();
            int quantityOfExistQuest=questDAO.countExistedQuestionsBySubject(subID);
            
            AnswerDAO ansDAO=new AnswerDAO();
            
            Date date=new Date();
            Timestamp createDate=new Timestamp(date.getTime());
            for (String questOrder : mapQuestionGetter.keySet()) {
                 String questID=subID+"_"+(++quantityOfExistQuest);
                 String content=mapQuestionGetter.get(questOrder);
                 boolean status = true;
                 QuestionDTO newQuestion=new QuestionDTO(questID, subID, content, createDate, status);
                 
                 HashMap<String,String> answer=mapAnswerGetter.get(questOrder);
                 
                 int lastID=ansDAO.getLastID(questID);
                 
                 for (String ansOrder : answer.keySet()) {
                    String ansID=questID+"_"+(++lastID);
                    String ansContent=answer.get(ansOrder);
                    boolean ansStatus=true;
                    boolean correctAnswer=false;
                    if(setCorrectAnswer.contains(ansOrder)){
                        correctAnswer=true;
                    }
                    AnswerDTO newAnswer=new AnswerDTO(ansID, questID, ansContent, correctAnswer);
                    listAnswerToDataBase.add(newAnswer);
                 }                 
                  listQuestToDataBase.add(newQuestion);
            }
            
            questDAO.insertQuestions(listQuestToDataBase);
            ansDAO.insertAnswers(listAnswerToDataBase);

                url=url.concat("?subID="+subID+"&msg=Success");
            }else{
                url="HomePage";
            }
        } catch (NamingException ex) {
            log("ERROR at CreateQuestionServlet_Naming: "+ex.getMessage());
            url="ErrorPage?msg=Fail to connect to Server. Try again!";
        } catch (SQLException ex) {
           log("ERROR at CreateQuestionServlet_SQL: "+ex.getMessage());
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
