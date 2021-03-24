<%-- 
    Document   : Quiz
    Created on : Feb 5, 2021, 6:52:01 PM
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
        <title>Quiz - ${param.codeName}</title>
        <link href="css/styles.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous"> 
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.6.0/dist/umd/popper.min.js" integrity="sha384-KsvD1yqQ1/1+IA7gi3P0tyJcT3vR+NdBTt13hSJ2lnve8agRGXTTyNaBYmCR/Nwi" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.min.js" integrity="sha384-nsg8ua9HAw1y0W1btsyWgBklPnCUAFLuTMS2G72MMONqmOymq585AcH49TLBQObG" crossorigin="anonymous"></script>
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

            .base-timer {
                position: absolute;
                width: 300px;
                height: 300px;
            }

            .base-timer__svg {
                transform: scaleX(-1);
            }

            .base-timer__circle {
                fill: none;
                stroke: none;
            }

            .base-timer__path-elapsed {
                stroke-width: 7px;
                stroke: grey;
            }

            .base-timer__path-remaining {
                stroke-width: 7px;
                stroke-linecap: round;
                transform: rotate(90deg);
                transform-origin: center;
                transition: 1s linear all;
                fill-rule: nonzero;
                stroke: currentColor;
            }

            .base-timer__path-remaining.green {
                color: rgb(65, 184, 131);
            }

            .base-timer__path-remaining.orange {
                color: orange;
            }

            .base-timer__path-remaining.red {
                color: red;
            }

            .base-timer__label {
                position: absolute;
                width: 300px;
                height: 300px;
                top: 0;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 48px;
            }
        </style>
    </head>
    <body>
        <c:import url="Navbar.jsp"/>
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Submit</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        Do you want to submit ?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <input type="submit" class="btn btn-success" value="Submit"/>
                    </div>
                </div>
            </div>
        </div>
        <div id="layoutSidenav">
            <div id="layoutSidenav_content">
                <main>


                    <div class="container-fluid">                        

                        <div style="height: 100vh">
                            <c:if test="${requestScope.DURATION>0}">
                                <div id="app"></div>
                            </c:if>
                            <form id="formQuiz" action="MarkQuiz" method="POST" >
                                <input type="hidden" name="subID" value="${param.subID}" />
                                <div class="modal-dialog">
                                    <c:set value="${fn:length(requestScope.DATA)}" var="quantity"/>
                                    <c:forEach items="${requestScope.DATA}" var="data" varStatus="count">


                                        <div class="modal-content" id="${count.count}" 
                                             <c:if test="${count.count!=1}">
                                                 hidden
                                             </c:if>     
                                             >
                                            <div class="modal-header">
                                                <h3><span class="label label-warning" id="qid">${count.count}</span>${data.key.content}</h3>
                                            </div>
                                            <div class="modal-body">           
                                                <div class="quiz" id="quiz" data-toggle="buttons">
                                                    <c:forEach items="${data.value}" var="answer">
                                                        <label  class="element-animation${count.count} btn btn-lg btn-primary btn-block" onclick="choose(this)"><span  class="btn-label"><i class="glyphicon glyphicon-chevron-right"></i></span>
                                                            <input type="radio" name="Answer_${data.key.questID}" value="${answer.ansID}">
                                                            <p style="white-space: pre-line">${answer.content}</p>
                                                        </label>
                                                    </c:forEach>
                                                    <button class="btn btn-outline-secondary" type="button"
                                                            <c:if test="${count.count==1}">
                                                                hidden
                                                            </c:if>
                                                            onclick="show(${count.count-1})"
                                                            >
                                                        Back
                                                    </button>
                                                    <button class="btn btn-outline-secondary" type="button"
                                                            <c:if test="${count.count==quantity}">
                                                                hidden
                                                            </c:if>
                                                            onclick="show(${count.count+1})"
                                                            >
                                                        Next
                                                    </button>


                                                    <input id="duration" type="hidden" name="txtDuration"/>
                                                </div>

                                            </div>
                                        </div>
                                    </c:forEach>
                                    <div class="modal-content"> <button type="submit" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                            Submit
                                        </button> 
                                    </div>

                                    <%--                                    <div class="modal-content" id="${quantity+1}" hidden>
                                                                            <div class="modal-header">
                                                                                <h3><span class="label label-warning" id="qid">SUBMIT</span></h3>
                                                                            </div>
                                                                            <div class="modal-body">           
                                                                                <button type="submit">Submit</button>
                                                                            </div>
                                                                        </div> --%>  
                                </div>

                            </form>
                        </div>
                        <!-- Modal -->

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
        <script>

                                                                var currentQuest = 1;
                                                                function show(id) {
                                                                    document.getElementById(id).removeAttribute('hidden');
                                                                    document.getElementById(currentQuest).setAttribute('hidden', 'true');
                                                                    currentQuest = id;
                                                                }
                                                                function choose(ele) {
                                                                    var className = document.getElementsByClassName(ele.className);

                                                                    for (var i = className.length - 1; i >= 0; i--) {
                                                                        className[i].style.removeProperty('color');
                                                                    }
                                                                    ele.style.color = "red";
                                                                }
        </script>     
        <c:if test="${requestScope.DURATION>0}">
            <script>
                const FULL_DASH_ARRAY = 283;
                const WARNING_THRESHOLD = 10;
                const ALERT_THRESHOLD = 5;

                const COLOR_CODES = {
                    info: {
                        color: "green"
                    },
                    warning: {
                        color: "orange",
                        threshold: WARNING_THRESHOLD
                    },
                    alert: {
                        color: "red",
                        threshold: ALERT_THRESHOLD
                    }
                };

                const TIME_LIMIT = ${requestScope.DURATION};
                let timePassed = 0;
                let timeLeft = TIME_LIMIT;
                let timerInterval = null;
                let remainingPathColor = COLOR_CODES.info.color;

                document.getElementById("app").innerHTML = `
<div class="base-timer">
  <svg class="base-timer__svg" viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg">
    <g class="base-timer__circle">
      <circle class="base-timer__path-elapsed" cx="50" cy="50" r="45"></circle>
      <path
        id="base-timer-path-remaining"
        stroke-dasharray="283"
        class="base-timer__path-remaining $` + `{remainingPathColor}"
        d="
          M 50, 50
          m -45, 0
          a 45,45 0 1,0 90,0
          a 45,45 0 1,0 -90,0
        "
      ></path>
    </g>
  </svg>
  <span id="base-timer-label" class="base-timer__label"></span>
</div>
`;

                startTimer();

                function onTimesUp() {
                    clearInterval(timerInterval);

                    document.getElementById("formQuiz").submit();
                }

                function startTimer() {
                    timerInterval = setInterval(() => {
                        timePassed += 1;
                        timeLeft = TIME_LIMIT - timePassed;
                        document.getElementById("base-timer-label").innerHTML = formatTime(
                                timeLeft
                                );
                        setCircleDasharray();
                        setRemainingPathColor(timeLeft);
                        document.getElementById("duration").value = timePassed;
                        if (timeLeft === 0) {
                            onTimesUp();
                        }
                    }, 1000);
                }

                function formatTime(time) {
                    const minutes = Math.floor(time / 60);
                    let seconds = time % 60;

                    if (seconds < 10) {
                        seconds = `0` + seconds;
                    }

                    return minutes + `:` + seconds;
                }

                function setRemainingPathColor(timeLeft) {
                    const {alert, warning, info} = COLOR_CODES;
                    if (timeLeft <= alert.threshold) {
                        document
                                .getElementById("base-timer-path-remaining")
                                .classList.remove(warning.color);
                        document
                                .getElementById("base-timer-path-remaining")
                                .classList.add(alert.color);
                    } else if (timeLeft <= warning.threshold) {
                        document
                                .getElementById("base-timer-path-remaining")
                                .classList.remove(info.color);
                        document
                                .getElementById("base-timer-path-remaining")
                                .classList.add(warning.color);
                    }
                }

                function calculateTimeFraction() {
                    const rawTimeFraction = timeLeft / TIME_LIMIT;
                    return rawTimeFraction - (1 / TIME_LIMIT) * (1 - rawTimeFraction);
                }

                function setCircleDasharray() {
                    const circleDasharray = `$` + `{(
                                           calculateTimeFraction() * FULL_DASH_ARRAY
                                           ).toFixed(0)} 283`;
                    document
                            .getElementById("base-timer-path-remaining")
                            .setAttribute("stroke-dasharray", circleDasharray);
                }
            </script>
        </c:if>
    </body>
</html>
