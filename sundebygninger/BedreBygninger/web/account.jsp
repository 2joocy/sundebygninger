<%@page import="DbHandler.DBUserHandler"%>
<%@page import="entities.User"%>
<%@page import="DbHandler.DBBuildingHandler"%>
<!DOCTYPE html>
<%

    DBBuildingHandler db = new DBBuildingHandler();
    DBUserHandler dbb = new DBUserHandler();
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
        <%
            if (user.getStatus().equalsIgnoreCase("worker")) {

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

        <div class="edit" style="margin-top: 4%; padding-left: 10px;">
            <br><br><br>
            <center>
                 <%
                
                if (session.getAttribute("failure") == null) {
                    //out.print("<script>alert('No failure')</script>");
                } else {
                    out.print("<script>alert('" + session.getAttribute("failure") + "')</script>");
                    session.setAttribute("failure", null);
                }

                %>
                <form action="Front" method="POST" >
                <p><h3>Change Password</h3></P>
                <p><input type="password" name="password" value="" placeholder="Current Password..." ></p>
                <p><input type="password" name="newPass" value="" placeholder="New Password..." ></p>
                <p><input type="password" name="confirmNewPass" value="" placeholder="Confirm Password..." ></p>
                <input type="hidden" name="methodForm" value="changePass" />
                <input type="hidden" name="email" value="<%out.print(user.getEmail());%>" />
                <input type='submit' value='Submit' />
                </form>
                
                <br><br><br>
                <br><br>
                <form action="Front" method="POST" >
                <p><h3>Change Email</h3></P>
                <p><input type="text" name="oldEmail" value="" placeholder="Current Email..." ></p>
                <p><input type="text" name="newEmail" value="" placeholder="New Email..." ></p>
                <p><input type="text" name="confirmNewemail" value="" placeholder="Confirm Email..." ></p>
                <input type="hidden" name="methodForm" value="changeEmail" />
                <input type="hidden" name="userID" value="<%out.print(user.getIdUser());%>" />
                <input type='submit' value='Submit' />
                </form>
            </center>
            
        </div>

    </body>
</html>

