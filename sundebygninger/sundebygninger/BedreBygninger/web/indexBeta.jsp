<!DOCTYPE html>
<html >
    <head>
        <meta charset="UTF-8">
        <title>Sundere Bygninger</title>
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
    </head>

    <body>
    <center style="padding-top: 2%;">
        <img src="pictures/logo-sunde-bygninger-property.png" alt=""/>
    </center>  
    <div class="inner-container">
        <div class="box">
            
            <h1>Login</h1>
            
            <input type="text" name="email" placeholder="Username"/>
            <input type="password" name="password" placeholder="Password"/>
            <button type="submit">Login</button>
            <input name="methodForm" type="hidden" value="login" />
            
            <center><p><a href="register.jsp">Apply For Registry Here</a></p></center>
            <center></center>
            <center><p><a href="forgotPass.jsp">Forgot Password?</a></p></center>
                        <%

                            if (session.getAttribute("failure") == null) {

                            } else {
                                out.print("<script>alert('" + session.getAttribute("failure") + "')</script>");
                                session.setAttribute("failure", null);
                            }

                        %>
            </form>
        </div>
    </div>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

    <script src="js/index.js"></script>



</body>
</html>
