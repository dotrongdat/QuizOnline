<%-- 
    Document   : Navbar
    Created on : Feb 5, 2021, 6:51:32 PM
    Author     : Win
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <link href="css/styles.css" rel="stylesheet" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/js/all.min.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<c:set value="${sessionScope.ACCOUNT}" var="account"/>
 <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <a class="navbar-brand" href="HomePage">Home</a>
            <button class="btn btn-link btn-sm order-1 order-lg-0" id="sidebarToggle" href="#"><i class="fas fa-bars"></i></button>
            <!-- Navbar Search-->
            <form class="d-none d-md-inline-block form-inline ml-auto mr-5 mr-md-5 my-2 my-md-5" action="Search" method="GET">
                <input type="hidden" name="page" value="1" />   
                <c:if test="${account.admin==true}">
                       <input class="form-control d-inline p-2" type="text" placeholder="Search..." aria-label="Search" aria-describedby="basic-addon2" name="questSearch" value="${param.questSearch}"/>
                       <select class="form-control d-inline p-2" aria-label="Default select example" name="status" title="Status">  

                        <option value="1"
                                <c:if test="${param.status==1}">
                                    selected
                                </c:if>
                                >True</option>
                        <option value="0"
                                <c:if test="${param.status==0}">
                                    selected
                                </c:if>
                                >False</option>
                    </select> 
                    </c:if>
                <div class="d-inline p-2">
                    <input type="hidden" name="search" value="search" />
                    <input class="form-control d-inline p-2" type="text" placeholder="Type course..." aria-label="Search" aria-describedby="basic-addon2" name="subSearch" value="${param.subSearch}"/>

                    
                    <div class="input-group-append d-inline p-2">
                        <button class="btn btn-primary" type="submit"><i class="fas fa-search"></i></button>
                    </div>
                </div>
            </form>
            
            <h4 style="color:white;">${account.name}</h4>
            <!-- Navbar-->
            <ul class="navbar-nav ml-auto ml-md-0">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="userDropdown" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
                        <a class="dropdown-item" href="#">Settings</a>
                        <a class="dropdown-item" href="#">Activity Log</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="LogOut">Logout</a>
                    </div>
                </li>
            </ul>
        </nav>

            
        <script src="js/scripts.js"></script>
