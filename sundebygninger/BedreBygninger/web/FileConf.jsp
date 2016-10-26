
<%@page import="DbHandler.DBBuildingHandler"%>
<%@page import="java.awt.Image"%>
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
        %>
        <p><%=s1%></p>
        <p><%=s2%></p>
        <%=handler.getImageHTML(32)%>
    </body>
</html>
