/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.servlet;

import datdt.account.AccountDTO;
import datdt.resultsubject.ResultQuestionObject;
import datdt.resultsubject.ResultSubjectDAO;
import datdt.resultsubject.ResultSubjectDTO;
import datdt.resultsubject.ResultSubjectObject;
import datdt.subject.SubjectDAO;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author TRONG DAT
 */
@WebServlet(name = "PrepareResultViewServlet", urlPatterns = {"/PrepareResultViewServlet"})
public class PrepareResultViewServlet extends HttpServlet {
    private final int QUESTION_PER_PAGE=5;
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
        HttpSession session=request.getSession();
        boolean existFault=false;
        String url="ResultView"; // get resource from map
        ServletContext context=request.getServletContext();
        HashMap<String, String> authenMap = (HashMap<String, String>) context.getAttribute("AUTHENMAP");
        url=authenMap.get(url);
        try {
            AccountDTO account =(AccountDTO)session.getAttribute("ACCOUNT");
            
            String email=account.getEmail();
            
            String subID=request.getParameter("subID");
            int page=1;
            String txtPage=request.getParameter("page");
            if(txtPage!=null){
                page=Integer.parseInt(txtPage);
            }
            if(subID!=null){
                ResultSubjectDAO rsSubDAO=new ResultSubjectDAO();
                ResultSubjectDTO rsSub=rsSubDAO.getResult(email, subID);
                
                ResultSubjectObject rsSubObj=new ResultSubjectObject();
                rsSubObj.decode(rsSub.getData());
                
                SubjectDAO subDAO=new SubjectDAO();
                String subName=subDAO.getSubjectName(subID);
                
                List<ResultQuestionObject> quests=rsSubObj.getQuestions();
                int fromIndex=QUESTION_PER_PAGE*(page-1);
                int toIndex=fromIndex+QUESTION_PER_PAGE;
                int totalQuest=quests.size();
                toIndex=  toIndex>totalQuest? totalQuest:toIndex;
                
                int totalPage=(int)Math.ceil(totalQuest/(double)QUESTION_PER_PAGE);
                int div=(page-1)/3;
                int first=div*3+1;
                int last=div*3+3;
                if(last>totalPage){
                    last=totalPage;
                }
                
                request.setAttribute("DURATIONFORMAT", rsSub.setFormatTimeBySecond());
                request.setAttribute("SUBNAME", subName);
                request.setAttribute("DATASUB", rsSub);
                request.setAttribute("DATAQUEST", quests.subList(fromIndex, toIndex));
                request.setAttribute("TOTALPAGE", totalPage);
                request.setAttribute("FIRST", first);
                request.setAttribute("LAST", last);
                request.setAttribute("PAGE", page);
                request.setAttribute("QUANTITYPERPAGE", QUESTION_PER_PAGE);
                
            }
        } catch (NamingException ex) {
            log("ERROR at PrepareResultViewServlet_Naming: "+ex.getMessage());
            url="HomePage?msg=Someting wrong happen";
            existFault=true;
        } catch (SQLException ex) {
            log("ERROR at PrepareResultViewServlet_SQL: "+ex.getMessage());
            url="HomePage?msg=Someting wrong happen";
            existFault=true;
        } catch (ClassNotFoundException ex) {
            log("ERROR at PrepareResultViewServlet_ClassNotFound: "+ex.getMessage());
            url="HomePage?msg=Someting wrong happen";
            existFault=true;
        }catch(NumberFormatException ex){
            url="HomePage";
            existFault=true;
        }catch(IndexOutOfBoundsException ex){
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
