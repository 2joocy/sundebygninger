<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="entities.Building"%>
<%@page import="entities.User"%>
<%@page import="DbHandler.DBBuildingHandler"%>
<%@page import="DbHandler.DBUserHandler"%>
<%@page import="DbHandler.DBUserHandler"%>
<%@page import="entities.User"%>
<%@page import="DbHandler.DBBuildingHandler"%>
<!DOCTYPE html>
<%
    
    
DBBuildingHandler db = new DBBuildingHandler();
DBUserHandler dbb = new DBUserHandler();
User user = (User) session.getAttribute("user");

if(user == null){
    response.sendRedirect("index.jsp");
    }
%>
<html lang="en">
    <head>
          <meta charset="utf-8">
          <link href="style/style.css" rel="stylesheet" type="text/css"/>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="script/scripts.js" type="text/javascript"></script>
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
        <meta charset="utf-8">
        <link rel="icon" href="http://sundebygninger.dk/wp-content/uploads/favicon.png" type="image/png">
        <meta charset="UTF-8">
        <title>Sunde Bygninger - Start Side</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body style="height: 92%;">
        <%
        if(user.getStatus().equalsIgnoreCase("worker")){
    
            if (dbb.countUnConfirmed() > 0) {
                out.print("<script>alert('You have new unconfirmed accounts to review!(" + dbb.countUnConfirmed() + ")');</script>");
            }
       
        }
        %>

        <ul class="topnav">
            <a href="firstPage.jsp" style="float:left; padding-right: 25px; padding-left: 10px;"><img src="pictures/menu-logo.png" alt=""/></a>
            <%
            out.print(db.createMenu(user.getStatus()));
            %>
        </ul>

        <div id="edit" style="margin-top: 4%;">
          <%
          out.print(db.printAwaitingService());
          %>
        </div>

    </body>
</html>


