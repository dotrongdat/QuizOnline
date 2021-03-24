<%-- 
    Document   : SignUp
    Created on : Feb 5, 2021, 6:53:59 PM
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
        <title>Sign Up</title>
        <link href="css/styles.css" rel="stylesheet" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/js/all.min.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    </head>
    <body class="bg-primary">
        <div id="layoutAuthentication">
            <div id="layoutAuthentication_content">
                <main>
                    <div class="container">
                        <div class="row justify-content-center">
                            <div class="col-lg-7">
                                <div class="card shadow-lg border-0 rounded-lg mt-5">
                                    <div class="card-header"><h3 class="text-center font-weight-light my-4">Create Account</h3></div>
                                    <div class="card-body">
                                        <form action="SignUp" method="POST" onsubmit="return validate();">


                                                    <div class="form-group">
                                                        <label class="small mb-1" for="inputName">Name</label>
                                                        <input class="form-control py-4" id="inputName" type="text" placeholder="Enter name" name="txtName" minlength="2" maxlength="50" required />
                                                    </div>
                                            

                                               

                                            <div class="form-group">
                                                <label class="small mb-1" for="inputEmailAddress">Email</label>
                                                <input class="form-control py-4" id="inputEmailAddress" type="email" aria-describedby="emailHelp" placeholder="Enter email address" name="txtEmail" required/>
                                                <c:if test="${not empty param.msg}">
                                                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                                    <p> ${param.msg}</p>
                                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                    </c:if>
                                            </div>
                                            <div class="form-row">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="small mb-1" for="inputPassword">Password</label>
                                                        <input class="form-control py-4" id="inputPassword" type="password" placeholder="Enter password" name="txtPassword" minlength="6" maxlength="30" pattern=".+[A-Za-z0-9]+.+" oninput="validateConfirm()" required/>
                                                    </div>
                                                </div>
                                                <div class="col-md-5">
                                                    <div class="form-group">
                                                        <label class="small mb-1" for="inputConfirmPassword">Confirm Password</label>
                                                        <input class="form-control py-4" id="inputConfirmPassword" type="password" placeholder="Confirm password" name="txtConfirm" minlength="6" maxlength="30" pattern=".+[A-Za-z0-9]+.+" oninput="validateConfirm()" required />
                                                        
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div  id="inValidLogo">
                                                        <i class="fa fa-window-close" aria-hidden="true"></i>
                                                    </div>
                                                    <div id="validLogo" hidden>
                                                        <i  class="fa fa-check-square" aria-hidden="true" style="color: green"></i>
                                                    </div>
                                                        
                                                        
                                            
                                                </div>
                                            </div>
                                            <div class="form-group mt-4 mb-0"><button type="submit" class="btn btn-primary btn-block">Create Account</button></div>
                                        </form>
                                    </div>
                                    <div class="card-footer text-center">
                                        <div class="small"><a href="LoginPage">Have an account? Go to login</a></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
            <div id="layoutAuthentication_footer">
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
            var check=true;
            function validateConfirm(){                
                check=document.getElementById("inputPassword").value==document.getElementById("inputConfirmPassword").value;
                if(check){
                    document.getElementById("inValidLogo").setAttribute('hidden','true');
                    document.getElementById("validLogo").removeAttribute('hidden');
                }else{
                    document.getElementById("inValidLogo").removeAttribute('hidden');
                    document.getElementById("validLogo").setAttribute('hidden','true');
                }
            }
            
            function validate(){
                if(!check){
                    return false;
                }
                return true;
            }
        </script>
        
    </body>
</html>
