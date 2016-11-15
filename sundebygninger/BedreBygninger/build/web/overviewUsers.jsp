<%@page import="java.sql.Connection"%>
<%@page import="controller.DBController"%>
<%@page import="DbHandler.*"%>
<%@page import="entities.User"%>
<%@page import="DbHandler.DBBuildingHandler"%>
<!DOCTYPE html>
<%
    Connection conn = DBConnection.getConnection();
    DBController con = new DBController(conn);
    DBBuildingHandler dbb = new DBBuildingHandler();
    DBUserHandler db = new DBUserHandler();
    User user = (User) session.getAttribute("user");

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
                    out.print(con.createMenu(dbb, db, user.getStatus()));
                %>
        </ul>

        <div id="edit" style="margin-top: 4%;">
            
            <%
            out.print(con.getUnConfirmed());
            %>
            
        </div>

    </body>
</html>







