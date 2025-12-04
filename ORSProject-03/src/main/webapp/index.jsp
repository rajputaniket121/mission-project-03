<%@ page import="in.co.rays.proj3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {
    background-image: url('<%=ORSView.APP_CONTEXT%>/img/Linkme.jpg');
    background-size: cover;
    background-repeat: no-repeat;

    /* Center content using flexbox */
    display: flex;
    flex-direction: column;
    justify-content: center; /* vertical center */
    align-items: center;     /* horizontal center */
    height: 100vh;           /* full height */
    margin: 0;
}
</style>
<body>

    <div>
        <h1 style="padding-left: 15%;">
            <img src="img/custom.png" width="318" height="120" border="0">
        </h1>

        <h1>
            <a href="<%=ORSView.WELCOME_CTL%>" style="color: white;">
                <font size="8px">Online Result System</font>
            </a>
        </h1>
    </div>

</body>
</html>
