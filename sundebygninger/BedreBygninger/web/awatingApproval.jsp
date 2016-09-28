<%@page import="DbHandler.DBHandler"%>
<%-- 
    Document   : firstPage
    Created on : 28-09-2016, 19:22:11
    Author     : William-PC
--%>
<%

if(session.getAttribute("user") == null){
    response.sendRedirect("index.jsp");
}

%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
        <script src="script/scripts.js" type="text/javascript"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="main">
            <img src="pictures/logo-sunde-bygninger-property.png" alt=""/>
        </div>
        <div id="mySidenav" class="sidenav">
            <p>Display Usernane</p>
            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
            <a href="#">Estates</a>
            <a href="service.jsp">Apply Service</a>
            <a href="#">Order History</a>
            <a href="#">Account Management</a>
            <a href="#">Help</a>
        </div>
        <span onclick="openNav()"><h3>&#9776; Menu</h3></span>


        <div id="main2">
            <div class="login">
        

        <h2 class="login-header">Log in</h2>
        <form action="POST" method="Front" class="login-container" >
            <p>
            Your account has been registered, however, it will not be functional untill a admin has reviewed your application! If your problem is urgent, we recommend calling our guideline: 666-666-666-666!    
            </p>
        </form>

    </div>
        </div>
    </body>
</html>


