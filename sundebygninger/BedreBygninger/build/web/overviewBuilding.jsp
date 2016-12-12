<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.sql.Connection"%>
<%@page import="controller.DBController"%>
<%@page import="DbHandler.*"%>
<%@page import="entities.User"%>
<%@page import="DbHandler.DBBuildingHandler"%>
<!DOCTYPE html>
<%
    Connection conn = DBConnection.getConnection();
    DBController con = new DBController(conn);
    User user = (User) session.getAttribute("user");

    if (user == null) {
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
            if (user.getStatus().equalsIgnoreCase("worker")) {

                if (con.countUnConfirmed() > 0) {
                    out.print("<script>alert('You have new unconfirmed accounts to review!(" + con.countUnConfirmed() + ")');</script>");
                }

            }
        %>

        <ul class="topnav">
            <a href="firstPage.jsp" style="float:left; padding-right: 25px; padding-left: 10px;"><img src="pictures/menu-logo.png" alt=""/></a>
                <%
                    out.print(con.createMenu(user.getStatus()));
                %>
        </ul>

        <div id="edit" style="margin-top: 4%;">
            <%
                session.setAttribute("isThumbnail", true);
                out.print(con.getBuildings(user.getIdUser(), user.getStatus()));
            %>
            <br />
            <center>
                <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Add Building</button>
            </center>
            <!-- Modal -->
            <div class="modal fade" id="myModal" role="dialog">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <center><h4>Register Building</h4></center>
                        </div>
                        <div class="modal-body">

                            <%
                                if (session.getAttribute("failure") != null) {
                                    out.print("<script>alert('" + session.getAttribute("failure") + "')</script>");
                                    session.setAttribute("failure", null);
                                }

                            %>
                            <center>
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

                                    <%                                        int userID = user.getIdUser();
                                        out.print("<p><input type='hidden' name='userID' value='" + userID + "'></p>");
                                    %>
                                    <input name="methodForm" type="hidden" value="registerBuilding" />
                                    <p><input style="border: 1px solid silver; border-radius: 3px; background-color: white; padding: 10px;" type="submit" value="Apply"></p>
                                </form>
                            </center>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>


            </div></P>
    </div>

</body>
</html>


