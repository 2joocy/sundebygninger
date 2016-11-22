
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
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body style="height: 92%;">
        <form action="Front" method="POST" enctype="multipart/form-data">
        <ul class="topnav">
            <a href="firstPage.jsp" style="float:left; padding-right: 25px; padding-left: 10px;"><img src="pictures/menu-logo.png" alt=""/></a>
            <%
            out.print(con.createMenu(user.getStatus()));
            %>
        </ul>

        <div class="container">
            <center>
            <input type="checkbox" name="remarks">Remarks<br>
        <input type="checkbox" name="damage">Damage<br>
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
        <input type="checkbox" name="hasWallRemarks">Wall Remarks<br>
        <p>Wall Remark</p>
        <p><input type="text" name="wallRemark"   ></p>
        <br>
        <input type="checkbox" name="hasRoofRemarks" >Roof Remarks<br>
        <p>Roof Remark</p>
        <p><input type="text" name="roofRemarks"   ></p>
        <br />
        <input type="checkbox" value="hasFloorRemarks">Floor Remarks<br>
        <p>Floor Remarks</p>
        <p><input type="text" name="floorRemarks"></p>
        <br />
        <input type="checkbox" value="hasMoistureRemark">Moisture<br>
        <p>Moisture Description</p>
        <p><input type="text"  name="moistureDesc"  ></p>
        <br />
        <p>Moisture Measure</p>
        <p><input type="text" name="moistureMeasure"  ></p>
        <br /><p>Conclusion</p>
        <p><input type="text" name="address" ></p>
        
        <td>Upload Picture<input type="file" accept=".jpg, .jpeg, .png" name="picture"/></td>
        <%
        out.print("<input type='hidden' name='idBuilding' value='" + session.getAttribute("idBuilding") + "'/>");
        %>
        <input type="hidden" name="methodForm" value="submitReport"/>
        
        <input type="submit" />
        </form>
        </center>
        </div>

    </body>
</html>

