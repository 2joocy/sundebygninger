<%-- 
    Document   : editBuilding
    Created on : 25-10-2016, 16:16:06
    Author     : William-PC
--%>


<%@page import="DbHandler.DBConnection"%>
<%@page import="controller.DBController"%>
<%@page import="java.sql.Connection"%>
<%@page import="DbHandler.DBUserHandler"%>
<%@page import="DbHandler.DBBuildingHandler"%>
<%@page import="DbHandler.ImageHandler"%>
<%@page import="entities.Building"%>
<%@page import="entities.User"%>
<!DOCTYPE html>
<%
    Connection conn = DBConnection.getConnection();
    DBController con = new DBController(conn);
    DBBuildingHandler dbb = new DBBuildingHandler();
    DBUserHandler db = new DBUserHandler();
    User user = (User) session.getAttribute("user");
    int idBuilding = Integer.parseInt(((String) session.getAttribute("idBuilding")).replaceAll(" ", ""));
    Building build = dbb.getBuilding(idBuilding);
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
        meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="script/scripts.js" type="text/javascript"></script>
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body style="height: 92%;">
        <script>
            $().ready(function () {
                $('#clicker').click(function () {
                    $('input').each(function () {
                        if ($(this).attr('readonly')) {
                            
                            $(this).removeAttr('readonly');
                        } else {
                            $(this).attr({
                                'readonly': 'readonly'
                            });
                        }
                    });
                });
            });

        </script>    
      
        <ul class="topnav">
            <a href="firstPage.jsp" style="float:left; padding-right: 25px; padding-left: 10px;"><img src="pictures/menu-logo.png" alt=""/></a>
                <%
                    out.print(con.createMenu(dbb, db, user.getStatus()));
                %>
        </ul>

        <div class="edit" style="margin-top: 4%;">
            <center>
                <div class="roomSideBar">
                    <h2 style="color: white;">Report Review:</h2>
                    <form action="Front" method="POST" >                
                        <br />
                        <center>
                            
                        

                        <%
                            out.print(con.getReportOverview(idBuilding));
                        %>
                        <br> <br>
                        
                        </center>
                        <!-- Modal -->



                </div>
                </form>
                
                <div class="pictureBox">
                    <h2>Picture</h2>
                    <%= con.getImageHTML(build.getFk_idMainPicture(), 350, 370) %>
                    <form action="Front" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="methodForm" value="newMainImage"/>
                        <input type="hidden" name="idBuilding" value="<%=build.getIdBuilding()%>"/>
                        <input type="hidden" name="fk_idMainPicture" value="<%=build.getFk_idMainPicture()%>"/>
                        <input type="hidden" name="redirect" value="reviewBuildingDetail.jsp"/>
                        <td><input style="length: 300px; padding: 15px; border: 0px solid black; border-radius: 3px;" type="file" accept=".jpg, .jpeg, .png" name="picture"/></td>
                        <button style="padding: 15px; width: 150px; border: 0px solid black; border-radius: 3px;" type="submit">Upload New Main Image</button>
                    </form>
                </div>
                        
                <h2 style="padding-top: 15px;">Edit Building <button id="clicker" onclick="lockUnlock();">&#128274;</button></h2>
                <form action="Front" method="POST" >


                    <p>Address</p>
                    <p><input type="text" id="address" name="address" value="<% out.print(build.getAddress()); %>" placeholder="Address" readonly></p>
                    <br />
                    <p>Cadastral</p>
                    <p><input type="text" id="cadastral" name="cadastral" value="<% out.print(build.getCadastral()); %>" placeholder="Cadastral" readonly></p>
                    <br />
                    <p>Building Year</p>
                    <p><input type="text" id="builtYear" name="builtYear" value="<% out.print(build.getBuiltYear()); %>" placeholder="Year Built" readonly></p>
                    <br />
                    <p>Area</p>
                    <p><input type="text" id="area" name="area" value="<% out.print(build.getArea()); %>" placeholder="Area" readonly></p>
                    <br />
                    <p>Zipcode</p>
                    <p><input type="text" id="zipcode" name="zipcode" value="<% out.print(build.getZipcode()); %>" placeholder="Zipcode" readonly></p>
                    <br />
                    <p>City</p>
                    <p><input type="text" id="city" name="city" value="<% out.print(build.getCity()); %>" placeholder="City" readonly></p>
                    <br />
                    <p>Condition</p>
                    <p><input type="text" id="condition" name="condition" value="<% out.print(build.getCondition()); %>" placeholder="Condition" readonly></p>
                    <br />
                    <p>Extra Text</p>
                    <p><input type="text" id="extraText" name="extraText" value="<% out.print(build.getExtraText()); %>" placeholder="Extra Text" readonly></p>
                    
                    <%
                        out.print("<p><input type='hidden' name='buildingID' value='" + build.getIdBuilding() + "'></p>");
                    %>
                    <input name="methodForm" type="hidden" value="editBuildingFinal" />
                      <br />
                    <button style="padding: 15px; width: 150px; border: 0px solid black; border-radius: 3px;" type="submit">Apply</button>
                    <br />
                    <br />
                    
                </form>
            </center>

        </div>
    </body>
</html>


