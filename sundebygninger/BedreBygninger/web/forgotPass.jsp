
<%@page import="entities.Building"%>
<%@page import="entities.User"%>
<%@page import="DbHandler.DBBuildingHandler"%>
<%@page import="DbHandler.DBUserHandler"%>

<!DOCTYPE html>
<html >
    <head>
        <link rel="icon" href="http://sundebygninger.dk/wp-content/uploads/favicon.png" type="image/png">
        <meta charset="UTF-8">
        <title>Sunde Bygninger</title>
        <link href="style/styleLogin.css" rel="stylesheet" type="text/css"/>
        <style>
            .login button{
                width: 260px;
                height: 35px;
                background: #fff;
                border: 1px solid #fff;
                cursor: pointer;
                border-radius: 2px;
                color: #a18d6c;
                font-family: 'Exo', sans-serif;
                font-size: 16px;
                font-weight: 400;
                padding: 6px;
                margin-top: 10px;
            }

            .login button:hover{
                opacity: 0.8;
                transition: opacity .25s ease-in-out;
                -moz-transition: opacity .25s ease-in-out;
                -webkit-transition: opacity .25s ease-in-out;
                text-decoration: none;

            }

            .login button:active{
                opacity: 0.6;
            }

            .login button:focus{
                outline: none;
            }

            ::-webkit-input-placeholder{
                color: rgba(255,255,255,0.6);
            }

            ::-moz-input-placeholder{
                color: rgba(255,255,255,0.6);
            }
        </style>
    </head>


    <body onload="document.getElementById(body).style.opacity='1'">

        <div class="body" style="background-image: url(pictures/bghd.jpg);"></div>
        <div class="grad"></div>
        <br>
        <center>
            <div class="backgroundRegister">
        <div class="register">
            <form action="Front" method="POST" class="login-container" >
            <img src="pictures/forgotpass.png" alt="" style="float: left;"/>
            <input type="text" name="email" placeholder="Email">
            <input name="methodForm" type="hidden" value="forgotPass" />
            <center><p><a href="index.jsp">Didn't forget your password anyway?</a></p></center>
            <%
            
            if (session.getAttribute("failure") == null ) {
                
            } else {
                out.print("<script>alert('"+ session.getAttribute("failure") +"')</script>");
            }

        %>
            <p><input type="submit" value="Send Request"></p>
        </form>
        </form>


        </div>
                </div>
            </center>
        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
    </body>
</html>

