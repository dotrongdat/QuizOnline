<%-- 
    Document   : Subject
    Created on : Feb 5, 2021, 6:54:29 PM
    Author     : Win
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Static Navigation - SB Admin</title>
        <link href="css/styles.css" rel="stylesheet" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/js/all.min.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
    </head>
    <body>
        <c:import url="Navbar.jsp"/>
        <div id="layoutSidenav">
            <c:import url="LeftNavbar.jsp"/>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                        

                        <div style="height: auto">
                            <c:set value="${requestScope.SUBJECT}" var="subject"/>
                            <c:set value="${sessionScope.ACCOUNT}" var="account"/>
                            <c:if test="${account.admin==true}">
                                                            <form action="UpdateSubject" method="GET">
                                <div style="border-radius: 1px; border: solid green; box-shadow: 2px 5px #888888;">
                                    <input type="hidden" name="subID" value="${subject.subID}" />
                                    <label for="subName">Course</label>
                                    <input type="text" id="subName" class="form-control" name="subName" value="${subject.subName}" required/>                                           
                                    <label for="duration">Duration</label>
                                        <select name="hour">
                                            <c:forEach begin="0" end="24" varStatus="count">
                                                <option
                                                    <c:if test="${count.index==requestScope.HOUR}">
                                                        selected
                                                    </c:if>    
                                                    >${count.index}</option>
                                            </c:forEach>
                                        </select> Hour
                                        <select name="minute">
                                            <c:forEach begin="0" end="59" varStatus="count">
                                                <option
                                                    <c:if test="${count.index==requestScope.MIN}">
                                                        selected
                                                    </c:if>
                                                    >${count.index}</option>
                                            </c:forEach>
                                        </select>  Minute                                    
                                </div>
                                           <br/>
                                           <button type="submit" class="btn btn-dark">Save</button>
                                           <a style="padding: 10px" href="SearchHistoryTakeQuiz?subId=${subject.subID}"><button type="button" class="btn btn-dark">View Attempt</button></a>
                                           <br/>
                                           <a href="PrepareQuestionView?subID=${subject.subID}">Edit question</a>
                            </form>
                            
                                           <c:if test="${not empty param.showHistory}">
                                               <c:if test="${not empty requestScope.DATA}">
                                                   <table class="table table-dark table-hover">
                                                       <tr>
                                                           <th>Name</th>
                                                           <th>Mark</th>
                                                           <th>Date take quiz</th>
                                                           <th>Duration</th>
                                                       </tr>
                                                       <c:forEach var="dto" items="${requestScope.DATA}">
                                                           <tr>
                                                               <td>${dto.value}</td>
                                                               <td>${dto.key.mark}</td>
                                                               <td>${dto.key.dateTakeQuiz}</td>
                                                               <td>${dto.key.formatTimeBySecond}</td>
                                                           </tr>
                                                       </c:forEach>
                                                    </table>
                                               </c:if>
                                               <c:if test="${empty requestScope.DATA}">
                                                   Empty
                                               </c:if>
                                           </c:if>
                            <table class="table table-dark table-hover">
                            </table>
                            </c:if>
                            <c:if test="${account.admin==false}">
                                <form action="Quiz" method="GET">
                                <div style="border-radius: 1px; border: solid green; box-shadow: 2px 5px #888888;">
                                    <input type="hidden" name="subID" value="${subject.subID}" />
                                    <label for="subName">Course</label>
                                    <input type="text" id="subName" class="form-control" name="subName" value="${subject.subName}" readonly/>
                                    <label for="duration">Duration</label>
                                    <input type="text" id="duration" value="${requestScope.HOUR} h : ${requestScope.MIN} m" readonly />
                                </div>
                                    <c:if test="${requestScope.CHECKALLOWQUIZ==true}">
                                        <c:if test="${requestScope.CHECKCOMPLETEQUIZ==false}">
                                        <input type="submit" value="Take Quiz" />
                                    </c:if>
                                    <c:if test="${requestScope.CHECKCOMPLETEQUIZ==true}">
                                        <a href="Result?subID=${subject.subID}"><button type="button">View Result</button></a>
                                    </c:if>
                                    </c:if>
                                    
                                </form>   
                            </c:if>
                                       


                        </div>
                        
                    </div>
                </main>
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright &copy; Your Website 2020</div>
                            <div>
                                <a href="#">Privacy Policy</a>
                                &middot;
                                <a href="#">Terms &amp; Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
    </body>
</html>
