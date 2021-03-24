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
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

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
        
        String url="HomePage";
        ServletContext context=request.getServletContext();
        HashMap<String, String> authorizeMap = (HashMap<String, String>) context.getAttribute("AUTHORIZEMAP");
        url=authorizeMap.get(url);
        boolean existFault=false;
        try {
            String txtStatus=request.getParameter("status");
            String questSearch=request.getParameter("questSearch");
            String subSearch=request.getParameter("subSearch");
            String txtPage=request.getParameter("page");
            
            if(txtStatus==null||questSearch==null||subSearch==null||txtPage==null){
                existFault=true;
            }else{
                boolean status;
                if(txtStatus.equals("1")){
                    status=true;
                }else{
                    status=false;
                }
                questSearch=questSearch.trim();
                subSearch=subSearch.trim();
                int page=Integer.parseInt(txtPage);
                QuestionDAO questDAO=new QuestionDAO();
                int totalPage=questDAO.countTotal(questSearch, subSearch, status);
                if(totalPage<page && page>1){
                    page=totalPage;
                }
                int div=(page-1)/3;
                int first=div*3+1;
                int last=div*3+3;
                if(last>totalPage){
                    last=totalPage;
                }
                HashMap<SubjectDTO,HashMap<QuestionObject,List<AnswerDTO>>> data=new HashMap<SubjectDTO,HashMap<QuestionObject,List<AnswerDTO>>>();
                if(totalPage>0){
                
                
                
                questDAO.getQuestionsBySearching(questSearch, subSearch, status, page);
                
                HashMap<String, List<QuestionObject>> mapQuest=questDAO.getMapQuest();
                
                SubjectDAO subDAO=new SubjectDAO();
                subDAO.getSubjectsByID(mapQuest.keySet());
                List<SubjectDTO> listSub=subDAO.getSubList();
                for (SubjectDTO subjectDTO : listSub) {
                    List<QuestionObject> listQuest=mapQuest.get(subjectDTO.getSubID());
                    AnswerDAO ansDAO=new AnswerDAO();
                    ansDAO.getAnswersByListOfQuestions(listQuest,false);
                    HashMap<String,List<AnswerDTO>> mapAns=ansDAO.getMapAnswer();
                    
                    HashMap<QuestionObject,List<AnswerDTO>> mapData=new HashMap<QuestionObject,List<AnswerDTO>>();
                    for (QuestionObject questionDTO : listQuest) {
                        mapData.put(questionDTO, mapAns.get(questionDTO.getQuestID()));
                    }
                    data.put(subjectDTO, mapData);
                }
                }
                request.setAttribute("TOTALPAGE", totalPage);
                request.setAttribute("PAGE", page);
                request.setAttribute("FIRST", first);
                request.setAttribute("LAST", last);
                request.setAttribute("DATA", data);
                //get resource from Map
            }
        } catch (SQLException ex) {
            log("ERROR at SearchServlet_SQL: "+ex.getMessage());
            url="HomePage";
            existFault=true;
        } catch (NamingException ex) {
            log("ERROR at SearchServlet_Naming: "+ex.getMessage());
            url="HomePage";
            existFault=true;
        } catch (NumberFormatException ex){
            url="HomePage";
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
