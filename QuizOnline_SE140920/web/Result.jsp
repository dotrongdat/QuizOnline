<%-- 
    Document   : Result
    Created on : Feb 5, 2021, 6:52:31 PM
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
        <title>Result - ${param.codeName}</title>
        <link href="css/styles.css" rel="stylesheet" />

        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/js/all.min.js" crossorigin="anonymous"></script>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <style>
            #qid {
                padding: 10px 15px;
                -moz-border-radius: 50px;
                -webkit-border-radius: 50px;
                border-radius: 20px;
            }
            label.btn {
                padding: 18px 60px;
                white-space: normal;
                -webkit-transform: scale(1.0);
                -moz-transform: scale(1.0);
                -o-transform: scale(1.0);
                -webkit-transition-duration: .3s;
                -moz-transition-duration: .3s;
                -o-transition-duration: .3s
            }

            label.btn:hover {
                text-shadow: 0 3px 2px rgba(0,0,0,0.4);
                -webkit-transform: scale(1.1);
                -moz-transform: scale(1.1);
                -o-transform: scale(1.1)
            }
            label.btn-block {
                text-align: left;
                position: relative
            }

            label .btn-label {
                position: absolute;
                left: 0;
                top: 0;
                display: inline-block;
                padding: 0 10px;
                background: rgba(0,0,0,.15);
                height: 100%
            }

            label .glyphicon {
                top: 34%
            }


            .modal-header {
                background-color: transparent;
                color: inherit
            }

            .modal-body {
                min-height: 205px
            }
            #loadbar {
                position: absolute;
                width: 62px;
                height: 77px;
                top: 2em
            }

        </style>
    </head>
    <body>
        <c:import url="Navbar.jsp"/>
        <div id="layoutSidenav">
            <c:import url="LeftNavbar.jsp"/>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                        <div class="card mb-4">
                            <div class="card-body">
                                <div id="ResultInfo">
                                    <p style="white-space: pre-line; font-size: 150%">Course: ${requestScope.SUBNAME}
                                    Date: ${requestScope.DATASUB.dateTakeQuiz}
                                    Total: ${requestScope.DATASUB.mark}/10.00
                                    Duration: ${requestScope.DURATIONFORMAT}
                                    </p>
                                    
                                </div>
                            </div>
                        </div>
                        <div style="height: auto">
                            <c:set value="${requestScope.FIRST}" var="first"/>
                            <c:set value="${requestScope.LAST}" var="last"/>
                            <c:set value="${requestScope.TOTALPAGE}" var="totalPage"/>
                            <c:set value="${requestScope.PAGE}" var="page"/>
                            <c:set value="${requestScope.QUANTITYPERPAGE}" var="questPerPage"/>

                            <div id="ResultDetail">
                                <div class="modal-dialog">
                                    <c:forEach items="${requestScope.DATAQUEST}" var="question" varStatus="count">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h3><span class="label label-warning" id="qid">${count.count+(page-1)*questPerPage}</span>${question.content}</h3>
                                            </div>
                                            <div class="modal-body"> 
                                                <div class="quiz" id="quiz" data-toggle="buttons">
                                                    <c:forEach items="${question.answers}" var="answer">
                                                        <label  class="element-animation btn btn-lg btn-primary btn-block" 
                                                                <c:if test="${answer.correctAnswer and answer.choosen}">
                                                                    style="color: greenyellow"                                                                    
                                                                </c:if>
                                                                <c:if test="${not answer.correctAnswer and answer.choosen}">
                                                                    style="color: #ff0000"
                                                                </c:if>

                                                                ><span  class="btn-label"><i class="glyphicon glyphicon-chevron-right"></i></span>
                                                            <input type="radio">
                                                            <p style="white-space: pre-line">
                                                                ${answer.content}
                                                            </p>
                                                        </label>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                        <hr>
                                    </c:forEach>

                                </div>
                            </div>
                            <div>

                                <nav aria-label="Page navigation example">
                                    <ul class="pagination justify-content-end">
                                        <c:if test="${first>1}">
                                            <li class="page-item">
                                                <a class="page-link" href="Result?subID=${param.subID}&page=${first-1}" tabindex="-1" aria-disabled="true">Previous</a>
                                            </li>
                                        </c:if>
                                        <c:forEach begin="${first}" end="${last}" varStatus="count">
                                            <li class="page-item
                                                <c:if test="${count.index==page}">
                                                    active   
                                                </c:if>
                                                "
                                                >
                                                <a class="page-link" href="Result?subID=${param.subID}&page=${count.index}">${count.index}</a>
                                            </li>
                                        </c:forEach>                                    
                                        <c:if test="${totalPage>last}">
                                            <li class="page-item">
                                                <a class="page-link" href="Result?subID=${param.subID}&page=${last+1}">Next</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </nav>
                            </div>
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

        <script src="js/scripts.js"></script>
    </body>
</html>
