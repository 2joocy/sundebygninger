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
        <%
            out.print(db.createMenu(user.getStatus()));
        %>
        <span onclick="openNav()"><h3>&#9776; Menu</h3></span>


        <div id="main2">
        
        <%
        out.print(db.getBuildings(user.getID()));
        %>
        <a href="addBuilding.jsp">Add Building</a>
        </div>
    </body>
</html>

