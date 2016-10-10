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
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
        <script src="script/scripts.js" type="text/javascript"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="main">
            <img src="pictures/logo-sunde-bygninger-property.png" alt=""/>
        </div>
        <%            out.print(db.createMenu(user.getStatus()));
        %>
        <span onclick="openNav()"><h3>&#9776; Menu</h3></span>


        <div id="main2">
            <div class="login">

                <%
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    
                %>
                <h2 class="login-header">Register Building</h2>
                <form action="Front" method="POST" class="login-container" >
                    <p><input type="text" name="address" placeholder="Address"></p>
                    <br />
                    <p><input type="text" name="cadastral" placeholder="Cadastral"></p>
                    <br />
                    <p><input type="text" name="builtYear" placeholder="Year Built"></p>
                    <br />
                    <p><input type="text" name="area" placeholder="Area"></p>
                    <br />
                    <p><input type="text" name="zipcode" placeholder="Zipcode"></p>
                    <br />
                    <p><input type="text" name="city" placeholder="City"></p>
                    <br />
                    <p><input type="text" name="condition" placeholder="Condition"></p>
                    <br />
                    <p><input type="text" name="extraText" placeholder="Extra Text"></p>
                    <br />
                    <%
                    int userID = user.getIdUser();
                    
                    out.print("<p><input type='hidden' name='userID' value='"+userID+"'></p>");
                    %>
                    <input name="methodForm" type="hidden" value="registerBuilding" />
                    <center><p><a href="overviewBuilding.jsp">Go Back</a></p></center>
                    <p><input type="submit" value="Apply"></p>
                </form>

            </div>
            <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

        </div>
    </body>
</html>

