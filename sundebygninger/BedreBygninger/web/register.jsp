
<%@page import="entities.Building"%>
<%@page import="entities.User"%>
<%@page import="DbHandler.DBBuildingHandler"%>
<%@page import="DbHandler.DBUserHandler"%>
<!DOCTYPE html>
<html >
    <head>
        <meta charset="UTF-8">
        <title>Login Form</title>
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
        <script src="script/scripts.js" type="text/javascript"></script>
    </head>

    <body>
    <center style="padding-top: 45px;">
        <img src="pictures/logo-sunde-bygninger-property.png" alt=""/>
    </center>    
    <div class="inner-container-register">
        <div class="box-register">
        <form action="Front" method="POST">
            <h1>Register</h1>
            <p><input type="text" name="businessName" placeholder="Business Name"></p>
            
            <p><input type="email" name="email" placeholder="Email"></p>
            
            <p><input type="password" name="password" placeholder="Password"></p>
            
            <p><input type="text" name="phone" placeholder="Phone Number"></p>
            
            <p><input type="text" name="fullName" placeholder="Full Name"></p>
            <input name="methodForm" type="hidden" value="register" />
            <center><p><a href="index.jsp">Already Have A Account?</a></p></center>
            <p><button type="submit">Register</button></p>
        </form>
        </div>
    </div>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

</body>
</html>
