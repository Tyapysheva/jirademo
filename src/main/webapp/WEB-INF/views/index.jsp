<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Index page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link rel="stylesheet" href="/resources/css/style.css">
        <script type="text/javascript" src="/resources/js/app.js"></script>
    </head>
    <body>
        Hello! Issues loaded: ${issues.length}

        <div class="form">
            <form action="hello" method="post" onsubmit="return validate()">
                <table>
                    <tr>
                        <td>Enter Your name</td>
                        <td><input id="name" name="name"></td>
                        <td><input type="submit" value="Submit"></td>
                    </tr>
                </table>
            </form>
        </div>

    </body>
</html>