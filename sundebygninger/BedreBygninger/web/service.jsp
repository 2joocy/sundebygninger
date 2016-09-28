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
        

        
        <form action="POST" method="Front" class="login-container" >
            <p><input type="bName" placeholder="Building Name"></p>
            <p><input type="adress" placeholder="Address"></p>
            <p><input type="mNumber" placeholder="Matrikel Nr."></p>
            <p><input type="buildingM" placeholder="Building Space"></p>
            
            <input name="methodForm" type="hidden" value="login" />
            <p>Apply Plus Service<input type="checkbox" name="acuteService" value="Acute Service" placeholder="Acute Service Check"/></p>
            <p><input type="submit" value="Apply For Service"></p>
            
        </form>

    </div>
        </div>
    </body>
</html>

