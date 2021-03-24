<%-- 
    Document   : SubjectDetail
    Created on : Feb 5, 2021, 6:54:56 PM
    Author     : Win
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
                        <h1 class="mt-4">${requestScope.SUBNAME}</h1>                                               
                        <div style="height: auto">
                            <c:set value="${requestScope.DATA}" var="mapQuest"/>
                            <c:if test="${empty mapQuest}">
                                <p>Question is not exist</p>
                            </c:if>



                            <c:if test="${not empty mapQuest}">
                                <form action="Update" method="POST">
                                    <input type="hidden" name="subID" value="${param.subID}" />
                                    <div style="position: sticky; top: 0">
                                        <button type="submit" class="btn btn-dark">Save</button>
                                    </div>
                                    
                                    <div class="modal-dialog">
                                        <c:forEach items="${mapQuest}" var="data" varStatus="count">
                                            <c:set value="${data.key}" var="question"/>
                                            <div class="modal-content" style="margin:10px;">
                                                <div class="modal-header">
                                                    <a href="DeleteQuestion?questID=${question.questID}&subID=${param.subID}" style="position: absolute;top: 0px;right: 0px"><i class="fa fa-times-circle" aria-hidden="true"></i></a>
                                                    <span class="label label-warning" id="qid">${count.count}</span>
                                                    <p></p>
                                                    <textarea name="${question.questID}" class="form-control" required>${question.content}</textarea>
                                                    
                                                </div>
                                                <div class="modal-body">
                                                    <div class="quiz" id="quiz">
                                                        <c:forEach items="${data.value}" var="answer">
                                                            <input name="Answer_${question.questID}" type="radio" value="${answer.ansID}"
                                                                   <c:if test="${answer.correctAns==true}">
                                                                       checked
                                                                   </c:if>
                                                                   />
                                                            <textarea class="form-control" name="${answer.ansID}" required>${answer.content}</textarea>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                    
                                </form>
                            </c:if>
                 
                                <hr>
                            <form action="CreateQuestion" method="POST" onsubmit="return checkAdd();">
                                <input type="hidden" name="subID" value="${param.subID}" />
                                <div id="createQuest" >
                                        <button type="button" class="btn btn-dark" onclick="addFormQuestion()">New +</button>
                                </div>
                                <br/><input type="submit" class="btn btn-dark" value="Add" />
                            </form>

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
        <script>
                                    var countQuest = 0;
                                    var countExactQuest = 0;
                                    function addFormQuestion() {
                                        countQuest++;
                                        countExactQuest++;                                    
                                        document.querySelector('#createQuest').insertAdjacentHTML(
                                                'afterbegin',
                                                '<div class="modal-content" style="margin-top:15px">' +
                                                '<div class="modal-header">' +
                                                '<button type="button" class="btn btn-info" onclick="remove(this)">x</button>' +
                                                '<p></p>' +
                                                '<textarea name="' + countQuest + '" class="form-control" required></textarea>' +
                                                '</div>' +
                                                '<div class="modal-body">' +
                                                '<div class="quiz" id="quiz">' +
                                                '<input  name="Answer_' + countQuest + '" type="radio" value="' + countQuest + '_' + 1 + '" required/>' +
                                                '<textarea class="form-control" name="' + countQuest + '_' + 1 + '" required></textarea>' +
                                                '<input  name="Answer_' + countQuest + '" type="radio" value="' + countQuest + '_' + 2 + '"/>' +
                                                '<textarea class="form-control" name="' + countQuest + '_' + 2 + '" required></textarea>' +
                                                '<input  name="Answer_' + countQuest + '" type="radio" value="' + countQuest + '_' + 3 + '"/>' +
                                                '<textarea class="form-control" name="' + countQuest + '_' + 3 + '" required></textarea>' +
                                                '<input  name="Answer_' + countQuest + '" type="radio" value="' + countQuest + '_' + 4 + '"/>' +
                                                '<textarea class="form-control" name="' + countQuest + '_' + 4 + '" required></textarea>' +
                                                '</div>' +
                                                '</div>' +
                                                '</div>'
                                                
                                                )
                                    }
                                    function remove(input) {
                                      
                                        input.parentNode.parentNode.remove();                                    
                                        countExactQuest--;                                       
                                    }
                                    function checkAdd() {
                                        return countExactQuest > 0;
                                    }
        </script>
    </body>
</html>
