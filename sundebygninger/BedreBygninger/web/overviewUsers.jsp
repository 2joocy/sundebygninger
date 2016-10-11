<%-- 
    Document   : firstPage
    Created on : 28-09-2016, 19:22:11
    Author     : William-PC
--%>
<%@page import="DbHandler.DBBuildingHandler"%>
<%@page import="entities.User"        %>
<%@page import = "DbHandler.DBUserHandler"%>
<%
    DBUserHandler db = new DBUserHandler();
    User user = (User) session.getAttribute("user");
    DBBuildingHandler dbB = new DBBuildingHandler();
    if (!user.getStatus().equalsIgnoreCase("worker")) {
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
    <body >
        <%      if (db.countUnConfirmed() < 0) {
                out.print("<script>alert('You have new unconfirmed accounts to review!(" + db.countUnConfirmed() + ")');</script>");
            }

        %>
        <div id="main" onclick="closeNav()">
            <img src="pictures/logo-sunde-bygninger-property.png" alt=""/>
        </div>
        <div id="mySidenav" class="sidenav">

            <center><a href="#">Welcome <br /> <%out.print(user.getBusinessName());%></a></center>
            
            <%out.print(dbB.createMenu(user.getStatus())); %>
        </div>
        <span onclick="openNav()"><h3>&#9776; Menu</h3></span>


        <div id="main2" onclick="closeNav()">
            <center>
            <%
                out.print(db.getUnConfirmed());
            %>
            </center>
        </div>
    </body>
</html>
