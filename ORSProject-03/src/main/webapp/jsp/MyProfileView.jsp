<%@page import="in.co.rays.proj3.controller.MyProfileCtl"%>
<%@page import="in.co.rays.proj3.utill.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj3.controller.BaseCtl"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.controller.ORSView"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>My Profile</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
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

<style type="text/css">
html, body {
    height: 100%;
    margin: 0;
}
.p4 {
    background-image: url('<%=ORSView.APP_CONTEXT%>/img/Linkme.jpg');
    background-size: cover;
    background-position: center;
    min-height: 100%;
    padding-top: 70px;
    padding-bottom: 60px;
}
.grad-card {
    background: rgba(255, 255, 255, 0.92);
    border-radius: 6px;
}
.page-wrapper {
    min-height: 100vh;
    position: relative;
    padding-bottom: 80px;
}
.footer {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
}
</style>
</head>
<body>
<div class="page-wrapper p4">

    <div class="header">
        <%@ include file="Header.jsp" %>
    </div>

    <main>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-5">

                    <form action="<%=ORSView.MY_PROFILE_CTL%>" method="post" class="w-100">
                        <jsp:useBean id="dto" class="in.co.rays.proj3.dto.UserDTO" scope="request" />

                        <div class="card grad-card shadow-sm">
                            <div class="card-body">

                                <h5 class="text-center text-success font-weight-bold mb-3">My Profile</h5>

                                <%
                                    String successMsg = ServletUtility.getSuccessMessage(request);
                                    if (successMsg != null && !successMsg.trim().equals("")) {
                                %>
                                <div class="alert alert-success alert-dismissible fade show" role="alert">
                                    <%= successMsg %>
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <%
                                    }
                                    String errorMsg = ServletUtility.getErrorMessage(request);
                                    if (errorMsg != null && !errorMsg.trim().equals("")) {
                                %>
                                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                    <%= errorMsg %>
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <%
                                    }
                                %>

                                <div class="form-group">
                                    <label><strong>Login Id</strong> <span class="text-danger">*</span></label>
                                    <div class="input-group input-group-sm">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fa fa-envelope text-muted"></i></span>
                                        </div>
                                        <input type="text" class="form-control" name="login" placeholder="Enter Email ID"
                                               value="<%=DataUtility.getStringData(dto.getLogin())%>">
                                    </div>
                                    <small class="text-danger"><%=ServletUtility.getErrorMessage("login", request)%></small>
                                </div>

                                <div class="form-group">
                                    <label><strong>First Name</strong> <span class="text-danger">*</span></label>
                                    <div class="input-group input-group-sm">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fa fa-user text-muted"></i></span>
                                        </div>
                                        <input type="text" class="form-control" name="firstName" placeholder="Enter First Name"
                                               value="<%=DataUtility.getStringData(dto.getFirstName())%>">
                                    </div>
                                    <small class="text-danger"><%=ServletUtility.getErrorMessage("firstName", request)%></small>
                                </div>

                                <div class="form-group">
                                    <label><strong>Last Name</strong> <span class="text-danger">*</span></label>
                                    <div class="input-group input-group-sm">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fa fa-user text-muted"></i></span>
                                        </div>
                                        <input type="text" class="form-control" name="lastName" placeholder="Enter Last Name"
                                               value="<%=DataUtility.getStringData(dto.getLastName())%>">
                                    </div>
                                    <small class="text-danger"><%=ServletUtility.getErrorMessage("lastName", request)%></small>
                                </div>

                                <div class="form-group">
                                    <label><strong>Date of Birth</strong> <span class="text-danger">*</span></label>
                                    <div class="input-group input-group-sm">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fa fa-calendar text-muted"></i></span>
                                        </div>
                                        <input type="text" id="udate" name="dob" class="form-control" placeholder="Enter Date Of Birth"
                                               value="<%=DataUtility.getDateString(dto.getDob())%>">
                                    </div>
                                    <small class="text-danger"><%=ServletUtility.getErrorMessage("dob", request)%></small>
                                </div>

                                <div class="form-group">
                                    <label><strong>Gender</strong> <span class="text-danger">*</span></label>
                                    <div class="input-group input-group-sm">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fa fa-venus-mars text-muted"></i></span>
                                        </div>
                                        <%
                                            HashMap<String, String> map = new HashMap<String, String>();
                                            map.put("Male", "Male");
                                            map.put("Female", "Female");
                                            String htmlList = HTMLUtility.getList("gender", dto.getGender(), map);
                                            out.print(htmlList);
                                        %>
                                    </div>
                                    <small class="text-danger"><%=ServletUtility.getErrorMessage("gender", request)%></small>
                                </div>

                                <div class="form-group">
                                    <label><strong>Mobile No</strong> <span class="text-danger">*</span></label>
                                    <div class="input-group input-group-sm">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fa fa-phone text-muted"></i></span>
                                        </div>
                                        <input type="text" class="form-control" name="mobileNo" maxlength="10" placeholder="Enter Mobile No."
                                               value="<%=DataUtility.getStringData(dto.getMobileNo())%>">
                                    </div>
                                    <small class="text-danger"><%=ServletUtility.getErrorMessage("mobileNo", request)%></small>
                                </div>

                                <div class="text-center">
                                    <input type="submit" name="operation" class="btn btn-success btn-sm px-4" value="<%=BaseCtl.OP_SAVE%>">
                                    <input type="submit" name="operation" class="btn btn-primary btn-sm px-4" value="<%=MyProfileCtl.OP_CHANGE_PASSWORD%>">
                                </div>

                                <div class="text-center mt-3">
                                    <a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forget my password?</b></a>
                                </div>

                            </div>
                        </div>

                    </form>

                </div>
            </div>
        </div>
    </main>

    <div class="footer">
        <%@ include file="FooterView.jsp" %>
    </div>

</div>
</body>
</html>
