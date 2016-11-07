
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>File Uploading Test</title>
    </head>
    <body>
        <form method="POST" action="Front" enctype="multipart/form-data">
            <td><input type="file" accept=".jpg, .jpeg, .png" name="picture"/></td>
            <input type="hidden" name="methodForm" value="uploadPicture"/>
            <input type="submit" value="Save">
        </form>
    </body>
</html>
