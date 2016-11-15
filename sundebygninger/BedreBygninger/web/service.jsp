<%@page import="entities.User"%>
<%@page import="DbHandler.DBBuildingHandler"%>
<%@page import="DbHandler.DBUserHandler"%>
<!DOCTYPE html>
<%
    DBUserHandler db = new DBUserHandler();
    DBBuildingHandler dbB = new DBBuildingHandler();
    User user = (User) session.getAttribute("user");
    String searchParameter = (String) session.getAttribute("searchParameter");
    if (searchParameter == null) {
        searchParameter = "awaiting";
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

        <div class="edit" style="margin-top: 4%;">
            <center>
                <br />
                <p>
                <form action="Front" method="POST">
                    <select name="typesearch">
                        <option value="awaiting">Awaiting Service</option>
                        <option value="reviewed">Reviewed</option>
                        <option value="denied">Denied</option>
                        <option value="finished">Finished</option>
                        <option value="other">Other</option>
                    </select>
                    <input type="hidden" name="methodForm" value="filterServiceCustomer">
                    <br />
                    <br />
                    <input type="submit" value="Search" style="width: 125px;"/><br><br />
                </form>
                </p> 

                <%  
                    out.print(dbB.getAwaitingServiceCustomer(searchParameter, user.getIdUser(), user.getStatus()));
                %>
            </center>
        </div>
    </body>
</html>







