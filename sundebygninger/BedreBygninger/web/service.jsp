<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="entities.Building"%>
<%@page import="entities.User"%>
<%@page import="DbHandler.DBBuildingHandler"%>
<%@page import="DbHandler.DBUserHandler"%>

<%-- 
    Document   : firstPage
    Created on : 28-09-2016, 19:22:11
    Author     : William-PC
--%>
<%

    DBBuildingHandler db = new DBBuildingHandler();
    User user = (User) session.getAttribute("user");
    if (session.getAttribute("user") == null) {
        response.sendRedirect("index.jsp");
    }

%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="script/scripts.js" type="text/javascript"></script>
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%            out.print(db.createMenu(user.getStatus()));
        %>
        <div id="main">
            <img src="pictures/logo-sunde-bygninger-property.png" alt=""/>
        </div>
        
        <span onclick="openNav()"><h3>&#9776; Menu</h3></span>


        <div id="main2">

        </div>
        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

</body>
</html>

