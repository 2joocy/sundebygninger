<!DOCTYPE html>
<html >
    <head>
        <meta charset="UTF-8">
        <title>Sundere Bygninger</title>
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
    </head>

    <body>
    <center style="padding-top: 45px;">
        <img src="pictures/logo-sunde-bygninger-property.png" alt=""/>
    </center>  
    <div class="inner-container">
        <div class="box">
            <form action="Front" method="POST">
            <h1>Login</h1>
            <input type="text" name="username" placeholder="Username"/>
            <input type="text" name="password" placeholder="Password"/>
            <button type="submit">Login</button>
            <input name="methodForm" type="hidden" value="login" />
            <center><p><a href="register.jsp">Apply For Registry Here</a></p></center>
            <center></center>
            <center><p><a href="forgotPass.jsp">Forgot Password?</a></p></center>
                        <%

                            if (session.getAttribute("failure") == null) {

                            } else {
                                out.print("<script>alert('" + session.getAttribute("failure") + "')</script>");
                            }

                        %>
            </form>
        </div>
    </div>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

    <script src="js/index.js"></script>



</body>
</html>
