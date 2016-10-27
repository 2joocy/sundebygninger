<%-- 
    Document   : editRoom
    Created on : 27-10-2016, 05:43:41
    Author     : William-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Room ID#:</h1>
        <%
        out.print(session.getAttribute("idRoom"));
        %>
    </body>
</html>
