<%@page import="controller.DBController"%>
<%@page import="entities.User"%>
<%@page import="DbHandler.*"%>
<!DOCTYPE html>
<%
    DBController con = new DBController(DBConnection.getConnection());
    DBBuildingHandler db = new DBBuildingHandler();
    User user = (User) session.getAttribute("user");

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
            out.print(con.createMenu(user.getStatus()));
            %>
        </ul>

        <div class="edit" style="margin-top: 4%; padding-left: 10px;">
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <center>
            <p>If you experience any issues regarding the site and it's functionality, or whether you have questions for our support group, you're welcome to contact us at:<br></P>
            <p>666-666-666-666</p>
            </center>
        </div>

    </body>
</html>

