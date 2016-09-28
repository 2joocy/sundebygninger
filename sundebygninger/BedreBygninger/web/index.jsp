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
        <form action="POST" method="Front" class="login-container" >
            <p><input type="email" placeholder="Email"></p>
            <p><input type="password" placeholder="Password"></p>
            <input name="methodForm" type="hidden" value="login" />
            <p><a href="register.jsp">Don't Have A Account? Apply For Registry Here!</a></p>
            <p><input type="submit" value="Log in"></p>
        </form>

    </div>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<p style="padding-top: 100px;" />
    <center>Having issues? Contact us on 666-666-666-666</center>
</body>
</html>
