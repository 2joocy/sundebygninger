<%@page import="entities.Report"%>
<%@page import="DbHandler.DBUserHandler"%>
<%@page import="entities.User"%>
<%@page import="DbHandler.DBBuildingHandler"%>
<!DOCTYPE html>
<%
    
    
DBBuildingHandler db = new DBBuildingHandler();
DBUserHandler dbb = new DBUserHandler();
User user = (User) session.getAttribute("user");
Report report = (Report) session.getAttribute("report");
int idReport = report.getIdReport();
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
            out.print(db.createMenu(user.getStatus()));
            %>
        </ul>
        <div class="edit" style="margin-top: 4%; padding-left: 10px;">
            <center>
            <%
                out.print(db.showRoomReport(idReport));
            %>
            </center>
        </div>
    </body>
</html>

