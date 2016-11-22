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
        <br>
        <center>
            <div class="backgroundRegister">
        <div class="register">
            <form action="Front" method="POST">
            <center> 
                <img src="pictures/nylogo - Kopi.png" alt=""/>
            </center>
            <p><input type="text" name="businessName" placeholder="Business Name"></p>
            
            <p><input type="text" name="email" placeholder="Email"></p>
            
            <p><input type="password" name="password" placeholder="Password"></p>
            
            <p><input type="text" name="phone" placeholder="Phone Number"></p>
            
            <p><input type="text" name="fullName" placeholder="Full Name"></p>
            <input name="methodForm" type="hidden" value="register" />
            <center><br><p><a href="index.jsp">Already Have A Account?</a></p></center>
            <p><button type="submit">Register</button></p>
            <br>
        </form>


        </div>
                </div>
            </center>
        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
    </body>
</html>

