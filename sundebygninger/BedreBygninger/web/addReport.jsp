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
                    out.print(con.createMenu(user.getStatus()));
                %>
        </ul>
        <div class="addReport" style="margin-top: 4%; padding-left: 10px;">
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
                            <form action="Front" method="POST" class="login-container" >
                                <div class="addReportInput">
                                     <center>
                                    <table style="width:50%">
                                        <tr>
                                            <td><input type="checkbox" name="remarks">Remarks<br>
                                    <input type="checkbox" name="damage">Damage<br>
                                    <input type="checkbox" name="hasWallRemarks">Wall Remarks<br></td>
                                            <td><input type="checkbox" name="hasFloorRemark">Floor Remarks<br>
                                    <input type="checkbox" name="hasRoofRemarks" >Roof Remarks   <br>
                                    <input type="checkbox" name="hasMoistureRemark">Moisture<br></td> 
                                        </tr>
                                        <tr>
                                            <td> Date Damage(DD:MM:YYYY: mm:ss)<br />
                                                <input type="text"  name="dateOfDamage"></td>
                                            <td>Placement of damage<br />
                                                <br />
                                                <input type="text" name="placementOfDmg"></td>
                                        </tr>
                                        <tr>
                                            <td>Describe Damage<br />
                                    <input type="text" name="descDmg"></td>
                                            <td> Reason Of Damage <br />
                                    <input type="text" name="reasonDmg" ></td> 
                                        </tr>
                                        <tr>
                                            <td> Type Of Damage<br>
                                    <select name="typeDmg">
                                        <option value="Water Damage">Water Damage</option>
                                        <option value="Rot Damage">Rot Damage</option>
                                        <option value="Mold Damage">Mold Damage</option>
                                        <option value="Fire Damage">Fire Damage</option>
                                        <option value="Other Damage">Other Remark</option>
                                    </select></td>
                                            <td>Wall Remark<br />
                                    <input type="text" name="wallRemark"   ></td> 
                                        </tr>
                                        <tr>
                                            <td>Roof Remark<br />
                                    <input type="text" name="roofRemarks"></td>
                                            <td> Floor Remarks<br />
                                    <input type="text" name="floorRemarks"></td> 
                                        </tr>
                                        <tr>
                                            <td>Moisture Description<br />
                                    <input type="text"  name="moistureDesc"  ></td>
                                            <td>Moisture Measure<br />
                                    <input type="text" name="moistureMeasure"  ></td> 
                                        </tr>
                                    </table>
                                   
                                        <br />
                                        <br />
                                        Conclusion<br />
                                    <input type="text" name="conclusion" >
                                    <input type="hidden" name="methodForm" value="submitRoom"/>

                                    <br /><br />
                                    <button type="submit" class="btn btn-default" />Submit</button>
                                </center>
                                    <br />
                                </div>     
                            </form>

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
                    <%                            int idReport = (Integer) session.getAttribute("idReport");
                        out.print("<h3 style='color: white;'>Room Reports:<br /><br /> " + con.countRooms(idReport) + "</h3>");
                    %>
                    <br />
                    <center>
                        <div class="addReportReport">
                        <input type="submit" data-toggle="modal" data-target="#myModal" style="width: 200px;" value="Add Room Report">
                        </div>
                        <br />
                        <br />
                        <form action="Front" method="POST" > 
                            <div class="addReportReport">
                            <input type="submit" style="width: 200px;" value="View Room Report">
                            <input type="hidden" name="idReport" value="<% out.print(idReport); %>">
                            <input type="hidden" name="methodForm" value="showRoomReport">
                            </div>
                        </form>
                    </center>
                    <br> <br>
                </center>

            </div>
            <form action="Front" method="POST" > 
                <div class="addReportReport">
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
                        </div>
                    </form>
                </center>
                <br />
        </div>
    </body>
</html>

