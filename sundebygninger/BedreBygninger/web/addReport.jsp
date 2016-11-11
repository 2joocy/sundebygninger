<%@page import="entities.Report"%>
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
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="script/scripts.js" type="text/javascript"></script>
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body style="height: 92%;">
        <%            if (user.getStatus().equalsIgnoreCase("worker")) {

            }

            session.setAttribute("userID", user.getIdUser());
        %>
        <ul class="topnav">
            <a href="firstPage.jsp" style="float:left; padding-right: 25px; padding-left: 10px;"><img src="pictures/menu-logo.png" alt=""/></a>
                <%
                    out.print(db.createMenu(user.getStatus()));
                %>
        </ul>
        <div class="edit" style="margin-top: 4%; padding-left: 10px;">
            <div class="modal fade" id="myModal" role="dialog">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <center><h4>Register Room Report</h4></center>
                                <%
                                    if (session.getAttribute("failure") == null) {
                                        //out.print("<script>alert('No failure')</script>");
                                    } else {
                                        out.print("<script>alert('" + session.getAttribute("failure") + "')</script>");
                                        session.setAttribute("failure", null);
                                    }

                                %>
                        </div>
                        <div class="modal-body">
                            <center>
                                <form action="Front" method="POST" class="login-container" >
                                    <center>
                                        <input type="checkbox" name="remarks">Remarks<br>
                                        <input type="checkbox" name="damage">Damage<br>
                                        <br />
                                        <p>Origin Date Of Damage (Format: DD:MM:YYYY: mm:ss)</p>
                                        <p><input type="text"  name="dateOfDamage"></p>
                                        <br />
                                        <p>Placement of damage</p>
                                        <p><input type="text" name="placementOfDmg"  ></p>
                                        <br />
                                        <p>Describe Damage</p>
                                        <p><input type="text" name="descDmg"></p>
                                        <br />
                                        <p>Reason Of Damage</p>
                                        <p><input type="text" name="reasonDmg" ></p>
                                        <br />
                                        <p>Type Of Damage</p>
                                        <select name="typeDmg">
                                            <option value="Water Damage">Water Damage</option>
                                            <option value="Rot Damage">Rot Damage</option>
                                            <option value="Mold Damage">Mold Damage</option>
                                            <option value="Fire Damage">Fire Damage</option>
                                            <option value="Other Damage">Other Remark</option>
                                        </select>
                                        <br>
                                        <br>
                                        <br>
                                        <input type="checkbox" name="hasWallRemarks">Wall Remarks
                                        <br><br />
                                        <p>Wall Remark</p>
                                        <p><input type="text" name="wallRemark"   ></p>
                                        <br>
                                        <input type="checkbox" name="hasRoofRemarks" >Roof Remarks
                                        <br><br />
                                        <p>Roof Remark</p>
                                        <p><input type="text" name="roofRemarks"   ></p>
                                        <br />
                                        <input type="checkbox" name="hasFloorRemark">Floor Remarks
                                        <br><br />
                                        <p>Floor Remarks</p>
                                        <p><input type="text" name="floorRemarks"></p>
                                        <br />
                                        <input type="checkbox" name="hasMoistureRemark">Moisture
                                        <br><br />
                                        <p>Moisture Description</p>
                                        <p><input type="text"  name="moistureDesc"  ></p>
                                        <br />
                                        <p>Moisture Measure</p>
                                        <p><input type="text" name="moistureMeasure"  ></p>
                                        <br /><p>Conclusion</p>
                                        <p><input type="text" name="conclusion" ></p>

                                        <input type="hidden" name="methodForm" value="submitRoom"/>
                                        <input type="hidden" name="idBuilding" value="<%  %>"/>

                                        <input type="submit" value="Submit Room" />

                                    </center>
                                </form>
                            </center>
                            <div class="modal-footer">
                                <center>
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                </center>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="roomSideBar">
                <center><h2 style="color: white;">Report Review:</h2></center>
                               
                    <br />
                    <center>
                        <%
                            int idReport = (Integer) session.getAttribute("idReport");
                            out.print("<h3 style='color: white;'>Room Reports:<br /><br /> " + db.countRooms(idReport) + "</h3>");
                        %>
                        <br />
                        <center>
                            <input type="submit" data-toggle="modal" data-target="#myModal" style="width: 200px;" value="Add Room Report">
                            <br />
                            <br />
                            <form action="Front" method="POST" > 
                            <input type="submit" style="width: 200px;" value="View Room Report">
                            <input type="hidden" name="idReport" value="<% out.print(idReport); %>">
                            <input type="hidden" name="methodForm" value="showRoomReport">
                            </form>
                        </center>
                        <br> <br>
                    </center>

            </div>
                        <form action="Front" method="POST" > 
            <center>
                <br>
                <h3>Submit Report</h3>
                <br />
                <form action="Front" method="POST" >
                    <input type="text" name="buildingUsage" placeholder="Building Usage..." />
                    <br />
                    Roof Remarks<input type="checkbox" name="roofRemarks" value="1"  />
                    <br />
                    <input type="text" name="roofText" placeholder="Roof Remarks..."  />
                    <br />
                    Outer Wall Remarks<input type="checkbox" name="outerWallRemarks" value="1"  />
                    <br />
                    <input type="text" name="outerWallText" placeholder="Outer Wall Text..."  />
                    <input type="text" name="buildingUsage" placeholder="Building Usage..."  />
                    <br />
                    <input type="submit" value="Submit Report">
                    <input type="hidden" name="idUser" value="<% session.getAttribute("userID");%>" />
                    <input type="hidden" name="idBuilding" value="<% session.getAttribute("idBuilding");%>"  />
                    <input type="hidden" name="methodForm" value="finalAddReport"  />
                </form>
            </center>
            <br />
            <center>
                Or
                <form action="Front" method="POST" >
                    <input type="hidden" name="methodForm" value="closeReport">
                    <br>
                    <input type="submit" value="Save Report">
                </form>
            </center>
        </div>
    </body>
</html>

