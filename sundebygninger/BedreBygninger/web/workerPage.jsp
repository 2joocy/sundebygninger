
<%@page import="DbHandler.DBUserHandler"%>
<%-- 
    Document   : firstPage
    Created on : 28-09-2016, 19:22:11
    Author     : William-PC
--%>
<%@page import="entities.User"        %>
<%
    DBUserHandler db = new DBUserHandler();
    User user = (User) session.getAttribute("user");

    if (!user.getStatus().equalsIgnoreCase("worker")) {
        response.sendRedirect("index.jsp");
    }


%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entities.User"%>
<%@page import="DbHandler.DBBuildingHandler"%>
<!DOCTYPE html>
<%
    
    
DBBuildingHandler dbB = new DBBuildingHandler();

if(user == null){
    response.sendRedirect("index.jsp");
    }
%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <link rel="icon" href="http://sundebygninger.dk/wp-content/uploads/favicon.png" type="image/png">
        <meta charset="UTF-8">
        <title>Sunde Bygninger - Start Side</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body style="height: 92%;">

        <ul class="topnav">
            <a href="firstPage.jsp" style="float:left; padding-right: 25px; padding-left: 10px;"><img src="pictures/menu-logo.png" alt=""/></a>
            <%
            out.print(dbB.createMenu(user.getStatus()));
            %>
        </ul>

        <div class="container">
            <p>harr</P>
        </div>

    </body>
</html>

