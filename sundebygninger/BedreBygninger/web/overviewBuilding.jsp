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
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="script/scripts.js" type="text/javascript"></script>
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
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
            <%
            out.print(db.getBuildings(user.getIdUser()));
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
                            <h4>Register Building</h4>
                        </div>
                        <div class="modal-body">

                            <%
                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                Date date = new Date();

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
                                    <p><input type="submit" value="Apply"></p>
                                </form>
                            </center>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
        </div>
        
        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

    </div>
</body>
</html>

