<%-- 
    Document   : editBuilding
    Created on : 25-10-2016, 16:16:06
    Author     : William-PC
--%>

<%@page import="entities.Building"%>
<%@page import="DbHandler.DBUserHandler"%>
<%@page import="entities.User"%>
<%@page import="DbHandler.DBBuildingHandler"%>
<!DOCTYPE html>
<%
    DBBuildingHandler db = new DBBuildingHandler();
    DBUserHandler dbb = new DBUserHandler();
    User user = (User) session.getAttribute("user");
    Building build = (Building) session.getAttribute("building");
    if (user == null) {
        response.sendRedirect("index.jsp");
    }
%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <link rel="icon" href="http://sundebygninger.dk/wp-content/uploads/favicon.png" type="image/png">
        <meta charset="UTF-8">
        <script src="script/scripts.js" type="text/javascript"></script>
        <title>Sunde Bygninger - Start Side</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body style="height: 92%;">
        <script>

            document.getElementById("lockInput").addEventListener("click", function () {
                alert("Disabled.");
                document.getElementById("address").setAttribute("readonly", true);
            });


        </script>    
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
        <div class="container">
            <center>
                <h2>Edit Building <button id="lockInput" onclick="lockUnlock();">&#128274;</button></h2>
                <form action="Front" method="POST" >
                    <div class="edit">

                        <p>Adress</p>
                        <p><input type="text" id="address" name="address" value="<% out.print(build.getAddress()); %>" placeholder="Address" ></p>
                        <br />
                        <p>Cadastral</p>
                        <p><input type="text" id="cadastral" name="cadastral" value="<% out.print(build.getCadastral()); %>" placeholder="Cadastral"></p>
                        <br />
                        <p>Building Year</p>
                        <p><input type="text" id="builtYear" name="builtYear" value="<% out.print(build.getBuiltYear()); %>" placeholder="Year Built"></p>
                        <br />
                        <p>Area</p>
                        <p><input type="text" id="area" name="area" value="<% out.print(build.getArea()); %>" placeholder="Area"></p>
                        <br />
                        <p>Zipcode</p>
                        <p><input type="text" id="zipcode" name="zipcode" value="<% out.print(build.getZipcode()); %>" placeholder="Zipcode"></p>
                        <br />
                        <p>City</p>
                        <p><input type="text" id="city" name="city" value="<% out.print(build.getCity()); %>" placeholder="City"></p>
                        <br />
                        <p>Condition</p>
                        <p><input type="text" id="condition" name="condition" value="<% out.print(build.getCondition()); %>" placeholder="Condition"></p>
                        <br />
                        <p>Extra Text</p>
                        <p><input type="text" id="extraText" name="extraText" value="<% out.print(build.getExtraText()); %>" placeholder="Extra Text"></p>

                        <%
                            out.print("<p><input type='hidden' name='buildingID' value='" + build.getIdBuilding() + "'></p>");
                        %>
                        <input name="methodForm" type="hidden" value="editBuildingFinal" />
                        <p><input type="submit" value="Apply"></p>
                    </div>
                </form>
            </center>
        </div>

    </body>
</html>


