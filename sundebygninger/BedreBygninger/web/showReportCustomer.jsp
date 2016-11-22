<%@page import="controller.DBController"%>
<%@page import="controller.DBController"%>
<%@page import="DbHandler.DBConnection"%>
<%@page import="java.sql.Connection"%>
<%@page import="entities.Report"%>
<%@page import="DbHandler.DBUserHandler"%>
<%@page import="entities.User"%>
<%@page import="DbHandler.DBBuildingHandler"%>
<!DOCTYPE html>
<%

    Connection conn = DBConnection.getConnection();
    DBController con = new DBController(conn);
    User user = (User) session.getAttribute("user");
    int idBuilding = Integer.parseInt(((String) session.getAttribute("idBuilding")).replaceAll(" ", ""));
    int idReport = Integer.parseInt(((String) session.getAttribute("idReport")).replaceAll(" ", ""));
    if (user == null) {
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
                    out.print(con.createMenu(user.getStatus()));
                %>
        </ul>
        <div class="edit" style="margin-top: 4%; padding-left: 10px;">
            <center>
                <div class='roomReport'>
                    <br />
                    <h2 style="color: white;">
                    Building Report:
                    </h2>
                        
                    <%
                        out.print(con.getReportOverviewWithReportID(idReport));
                    %>
                    <br />
                    <br />
                </div>
                <br />
                <br />
                <div class='roomReport'>
                    <%                        
                        out.print(con.showRoomReport(idReport));
                    %>
                </div>
            </center>
        </div>
    </body>
</html>

