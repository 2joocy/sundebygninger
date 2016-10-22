<!DOCTYPE html>
<html >
    <head>
        <link rel="icon" href="http://sundebygninger.dk/wp-content/uploads/favicon.png" type="image/png">
        <meta charset="UTF-8">
        <title>Sunde Bygninger</title>
        <link href="style/styleLogin.css" rel="stylesheet" type="text/css"/>
        <style>
            .login button{
                width: 260px;
                height: 35px;
                background: #fff;
                border: 1px solid #fff;
                cursor: pointer;
                border-radius: 2px;
                color: #a18d6c;
                font-family: 'Exo', sans-serif;
                font-size: 16px;
                font-weight: 400;
                padding: 6px;
                margin-top: 10px;
            }

            .login button:hover{
                opacity: 0.8;
                transition: opacity .25s ease-in-out;
                -moz-transition: opacity .25s ease-in-out;
                -webkit-transition: opacity .25s ease-in-out;
                text-decoration: none;

            }

            .login button:active{
                opacity: 0.6;
            }

            .login button:focus{
                outline: none;
            }

            ::-webkit-input-placeholder{
                color: rgba(255,255,255,0.6);
            }

            ::-moz-input-placeholder{
                color: rgba(255,255,255,0.6);
            }
        </style>
    </head>


    <body onload="document.getElementById(body).style.opacity='1'">

        <div class="body" style="background-image: url(pictures/bghd.jpg);"></div>
        <div class="grad"></div>
        <div class="header">
            <img src="pictures/nylogo.png" alt=""/>
        </div>
        <br>
        <div class="login">
            <form action="Front" method="POST">
                <input type="text" placeholder="Email..." name="email"><br>
                <input type="password" placeholder="Password..." name="password"><br>
                <input name="methodForm" type="hidden" value="login" />

                <button type="submit">Login</button><br><br />
            </form>
            <a style="padding-left: 50px;" href="register.jsp">Apply For Membership</a><br><br>
            <a style="padding-left: 65px;" href="forgotPass.jsp">Forgot Password?</a><br>
            <%

                if (session.getAttribute("failure") == null) {

                } else {
                    out.print("<script>alert('" + session.getAttribute("failure") + "')</script>");
                    session.setAttribute("failure", null);
                }

            %>
<br><br />
            <a><h2>PS: Siden virker kun med en opløsning af 1920x1080!</h2></a>
        </div>
        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
    </body>
</html>
