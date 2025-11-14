<%@page import="in.co.rays.proj3.controller.UserRegistrationCtl"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@page import="in.co.rays.proj3.controller.LoginCtl"%>
<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Login View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- jQuery + jQuery UI -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- Bootstrap + FontAwesome -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>

<!-- Background + Card Theme (same as UserView.jsp) -->
<style>
.p4 {
    background-image: url('<%=ORSView.APP_CONTEXT%>/img/Linkme.jpg');
    background-size: cover;
    background-position: center;
    background-attachment: fixed;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
    padding-top: 70px;
    padding-bottom: 40px;
}
.grad-card {
    background: rgba(255, 255, 255, 0.92);
}
</style>
</head>

<body class="p4 d-flex flex-column">
    <div class="header">
        <%@include file="Header.jsp"%>
    </div>

    <main class="container flex-grow-1 d-flex align-items-center justify-content-center">
        <form action="<%=ORSView.LOGIN_CTL%>" method="post" class="w-100" style="max-width: 450px;">
            <jsp:useBean id="dto" class="in.co.rays.proj3.dto.UserDTO" scope="request" />

            <div class="card grad-card shadow-sm">
                <div class="card-body p-4">
                    <h4 class="text-center text-success fw-bold mb-3">Login</h4>

                    <!-- Success Message -->
                    <%
                        if (!ServletUtility.getSuccessMessage(request).equals("")) {
                    %>
                    <div class="alert alert-success alert-dismissible fade show p-2 mb-2" role="alert">
                        <%=ServletUtility.getSuccessMessage(request)%>
                        <button type="button" class="btn-close p-1" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <% } %>

                    <!-- Error Message -->
                    <%
                        if (!ServletUtility.getErrorMessage(request).equals("")) {
                    %>
                    <div class="alert alert-danger alert-dismissible fade show p-2 mb-2" role="alert">
                        <%=ServletUtility.getErrorMessage(request)%>
                        <button type="button" class="btn-close p-1" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <% } %>

                    <!-- Email -->
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Email ID <span class="text-danger">*</span></label>
                        <div class="input-group input-group-sm">
                            <span class="input-group-text"><i class="fa fa-envelope text-muted"></i></span>
                            <input type="text" class="form-control" name="login" placeholder="Enter Email"
                                   value="<%=DataUtility.getStringData(dto.getLogin())%>">
                        </div>
                        <div class="text-danger small"><%=ServletUtility.getErrorMessage("login", request)%></div>
                    </div>

                    <!-- Password -->
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Password <span class="text-danger">*</span></label>
                        <div class="input-group input-group-sm">
                            <span class="input-group-text"><i class="fa fa-lock text-muted"></i></span>
                            <input type="password" class="form-control" name="password" placeholder="Enter Password"
                                   value="<%=DataUtility.getStringData(dto.getPassword())%>">
                        </div>
                        <div class="text-danger small"><%=ServletUtility.getErrorMessage("password", request)%></div>
                    </div>

                    <!-- Buttons -->
                    <div class="d-grid gap-2 d-sm-flex justify-content-sm-center mb-2">
                        <input type="submit" name="operation" class="btn btn-success btn-sm px-4" 
                               value="<%=LoginCtl.OP_SIGN_IN%>">
                        <input type="submit" name="operation" class="btn btn-primary btn-sm px-4" 
                               value="<%=UserRegistrationCtl.OP_SIGN_UP%>">
                    </div>

                    <!-- Forgot Password -->
                    <div class="text-center mt-3">
                        <a href="<%=ORSView.FORGET_PASSWORD_CTL%>" class="btn btn-outline-secondary btn-sm">
                            <i class="fa fa-key me-1"></i>Forgot Password?
                        </a>
                    </div>

                    <input type="hidden" name="uri" value="<%=request.getAttribute("uri")%>">
                </div>
            </div>
        </form>
    </main>

    <div class="footer mt-auto py-2">
        <%@include file="FooterView.jsp"%>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
