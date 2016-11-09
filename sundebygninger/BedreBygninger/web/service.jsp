<%@page import="DbHandler.ImageHandler"%>
<%@page import="entities.User"%>
<%@page import="DbHandler.DBBuildingHandler"%>
<%@page import="DbHandler.DBUserHandler"%>
<!DOCTYPE html>
<%
    DBUserHandler db = new DBUserHandler();
    DBBuildingHandler dbB = new DBBuildingHandler();
    User user = (User) session.getAttribute("user");
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
            <center style="padding-top: 3%;">
                <p>
                Currently No Service Available!
                <%= ImageHandler.getImageHTML(26, 1400, 600) %>
                </p>    
            </center>
        </div>

    </body>
</html>