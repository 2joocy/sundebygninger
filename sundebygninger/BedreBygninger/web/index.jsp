<%@page import="DbHandler.DBHandler"%>
<!DOCTYPE html>
<html >
    <head>
        <meta charset="UTF-8">
        <title>Login Form</title>
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
        <script src="script/scripts.js" type="text/javascript"></script>
    </head>

    <body>
    <center>
        <img src="pictures/logo-sunde-bygninger-property.png" alt=""/>
    </center>    
    <div class="login">


        <h2 class="login-header">Log in</h2>
        <form action="Front" method="POST" class="login-container" >
            <p><input type="text" name="email" placeholder="Email"></p>
            <p><input type="password" name="password" placeholder="Password"></p>
            <input name="methodForm" type="hidden" value="login" />
            <center><p><a href="register.jsp">Apply For Registry Here</a></p></center>
            <center></center>
            <center><p><a href="forgotPass.jsp">Forgot Password?</a></p></center>
            <%
            
            if (session.getAttribute("failure") == null ) {
                
            } else {
                out.print("<script>alert('"+ session.getAttribute("failure") +"')</script>");
            }

        %>
            <p><input type="submit" value="Log in"></p>
        </form>
        
    </div>
   
    <p style="padding-top: 100px;" />
    <center>Having issues? Contact us on 666-666-666-666</center>
</body>
</html>
