///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package datdt.servlet;
//
//import datdt.account.AccountDTO;
//import datdt.subject.SubjectDAO;
//import datdt.subject.SubjectDTO;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.naming.NamingException;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
///**
// *
// * @author TRONG DAT
// */
//@WebServlet(name = "SearchSubjectServlet", urlPatterns = {"/SearchSubjectServlet"})
//public class SearchSubjectServlet extends HttpServlet {
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        String url="HistoryTakeQuizPage"; // get resource from map;
//        boolean existFault=false;
//        HttpSession session=request.getSession();
//        try {
//            AccountDTO account=(AccountDTO)session.getAttribute("ACCOUNT");
//           String email=account.getEmail();
//           String subNameSearch=request.getParameter("subNameSearch");
//           if(subNameSearch!=null){
//               SubjectDAO subDAO=new SubjectDAO();
//               subDAO.getSubjectsByName(subNameSearch,email);
//               List<SubjectDTO> subList=subDAO.getSubList();
//               request.setAttribute("DATA", subList);
//           }else{
//               existFault=true;
//           }
//        } catch (NamingException ex) {
//            log("ERROR at SearchSubjectServlet_Naming: "+ex.getMessage());
//            url="ErrorPage?msg=Fail to connect to server";
//            existFault=true;
//        } catch (SQLException ex) {
//            log("ERROR at SearchSubjectServlet_SQL: "+ex.getMessage());
//            url="ErrorPage?msg=Fail to connect to server";
//            existFault=true;
//        }finally{
//            if(existFault){
//                response.sendRedirect(url);
//            }else{
//                RequestDispatcher rd=request.getRequestDispatcher(url);
//                rd.forward(request, response);
//            }
//            out.close();
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}