
<%@page import="controller.DBController"%>
<%@page import="java.sql.Connection"%>
<%@page import="DbHandler.DBBuildingHandler"%>
<%@page import="java.awt.Image"%>
<%@page import="DbHandler.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>File Check</title>
    </head>
    <body>
        <%
            String s1 = (String) request.getSession().getAttribute("imageMessage");
            String s2 = (String) request.getSession().getAttribute("imageId");
            DBBuildingHandler handler = new DBBuildingHandler();
            Connection conn = DBConnection.getConnection();
            DBController con = new DBController(conn);
        %>
        <p><%=s1%></p>
        <p><%=s2%></p>
        <%=con.getImageHTML(Integer.parseInt(s2))%>
    </body>
</html>
