<%@page import="in.co.rays.proj3.controller.UserCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj3.utill.HTMLUtility"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.dto.UserDTO"%>
<%@page import="in.co.rays.proj3.dto.RoleDTO"%>
<%@page import="in.co.rays.proj3.controller.LoginCtl"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>User View</title>

<!--  MINIMAL REQUIRED BOOTSTRAP -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<!--  JQUERY DATE PICKER -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>
$(function() {
    var today = new Date();
    var cutoff = new Date(today.getFullYear() - 18, today.getMonth(), today.getDate());
    $("#udate").datepicker({
        changeMonth: true,
        changeYear: true,
        yearRange: '1950:' + cutoff.getFullYear(),
        dateFormat: 'dd/mm/yy',
        maxDate: cutoff
    });
});
</script>

<style>
/*  BACKGROUND */
.page-bg {
    background-image: url('<%=ORSView.APP_CONTEXT%>/img/Linkme.jpg');
    background-size: cover;
    background-position: center;
    min-height: 100vh;
    padding-top: 80px;
    padding-bottom: 60px;
}

/*  CARD */
.grad-card {
    background: rgba(255,255,255,0.95);
}

/*  FIXED FOOTER */
.footer {
    position: fixed;
    bottom: 0;
    width: 100%;
    background: linear-gradient(to right, #f8f9fa, #6c757d);
    text-align: center;
    padding: 6px;
}
</style>
</head>

<body class="page-bg">

<%
UserDTO userDto = (UserDTO) session.getAttribute("user");
boolean userLoggedIn = userDto != null;
String role = userLoggedIn ? (String) session.getAttribute("role") : "";
String welcomeMsg = "Hi, " + (userLoggedIn ? userDto.getFirstName() + " (" + role + ")" : "Guest");
%>

<!--  HEADER (COMBINED) -->
<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top shadow-sm">
    <a class="navbar-brand" href="<%=ORSView.WELCOME_CTL%>">
        <img src="<%=ORSView.APP_CONTEXT%>/img/custom.png" height="40">
    </a>

    <button class="navbar-toggler" data-toggle="collapse" data-target="#nav">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div id="nav" class="collapse navbar-collapse">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" data-toggle="dropdown"><%=welcomeMsg%></a>
                <div class="dropdown-menu dropdown-menu-right">
                    <% if(userLoggedIn){ %>
                        <a class="dropdown-item" href="<%=ORSView.MY_PROFILE_CTL%>">Profile</a>
                        <a class="dropdown-item" href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change Password</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">Logout</a>
                    <% } else { %>
                        <a class="dropdown-item" href="<%=ORSView.LOGIN_CTL%>">Login</a>
                        <a class="dropdown-item" href="<%=ORSView.USER_REGISTRATION_CTL%>">Register</a>
                    <% } %>
                </div>
            </li>
        </ul>
    </div>
</nav>

<!--  MAIN USER FORM -->
<main class="container">
<div class="row justify-content-center align-items-center">
<form action="<%=ORSView.USER_CTL%>" method="post" class="w-100" style="max-width:800px;">
<jsp:useBean id="dto" class="in.co.rays.proj3.dto.UserDTO" scope="request"/>

<div class="card grad-card shadow-sm">
    <div class="card-body">

        <h5 class="text-center text-success mb-3">
        <%= (dto != null) ? "Update User" : "Add User" %>
        </h5>

        <input type="hidden" name="id" value="<%=dto.getId()%>">

        <div class="row">
            <div class="col-md-6 mb-2">
                <input class="form-control" name="firstName" placeholder="First Name"
                value="<%=DataUtility.getStringData(dto.getFirstName())%>">
            </div>

            <div class="col-md-6 mb-2">
                <input class="form-control" name="lastName" placeholder="Last Name"
                value="<%=DataUtility.getStringData(dto.getLastName())%>">
            </div>

            <div class="col-md-6 mb-2">
                <input class="form-control" name="login" placeholder="Email"
                value="<%=DataUtility.getStringData(dto.getLogin())%>">
            </div>

            <div class="col-md-6 mb-2">
                <input class="form-control" type="password" name="password" placeholder="Password">
            </div>

            <div class="col-md-6 mb-2">
                <input class="form-control" id="udate" name="dob" placeholder="DOB"
                value="<%=DataUtility.getDateString(dto.getDob())%>">
            </div>

            <div class="col-md-6 mb-2">
                <input class="form-control" name="mobileNo" placeholder="Mobile"
                value="<%=DataUtility.getStringData(dto.getMobileNo())%>">
            </div>

            <div class="col-12 text-center mt-3">
                <input type="submit" class="btn btn-success btn-sm px-4" value="Save">
                <input type="reset" class="btn btn-secondary btn-sm px-4">
            </div>
        </div>

    </div>
</div>
</form>
</div>
</main>

<!-- FOOTER (COMBINED) -->
<div class="footer">
    <h6 class="text-dark mb-0">&copy; RAYS Technologies</h6>
</div>

</body>
</html>
