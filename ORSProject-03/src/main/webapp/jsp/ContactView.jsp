<%@page import="in.co.rays.proj3.controller.ContactCtl"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@page import="in.co.rays.proj3.controller.ORSView"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Contact View</title>

<link rel="icon" type="image/png"
    href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

<style>
html, body {
    height: 100%;
    margin: 0;
}

.page-wrapper {
    min-height: 100vh;
    position: relative;
    padding-bottom: 80px;
}

.p4 {
    background-image: url('<%=ORSView.APP_CONTEXT%>/img/Linkme.jpg');
    background-size: cover;
    background-position: center;
    background-attachment: fixed;
    min-height: 100%;
    padding-top: 70px;
    padding-bottom: 40px;
}

.grad-card {
    background: rgba(255, 255, 255, 0.92);
    border-radius: 6px;
}

.footer {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
}

.input-group-text .fa {
    width: 16px;
    text-align: center;
}
</style>
</head>

<body>
    <div class="page-wrapper p4">

        <div class="header">
            <%@include file="Header.jsp"%>
        </div>

        <main>
            <div class="container">
                <div class="row justify-content-center mt-4">

                    <div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-5">

                        <form action="<%=ORSView.CONTACT_CTL%>" method="post" class="w-100">

                            <jsp:useBean id="dto" class="in.co.rays.proj3.dto.ContactDTO"
                                scope="request"></jsp:useBean>

                            <div class="card grad-card shadow-sm">
                                <div class="card-body">

                                    <h5 class="text-center text-success font-weight-bold mb-3">
                                        <%
                                        long id = DataUtility.getLong(request.getParameter("id"));
                                            if (dto != null && id > 0) {
                                        %>Update<%
                                            } else {
                                        %>Add<%
                                        }
                                        %>
                                        Contact
                                    </h5>

                                    <%
                                    String errorMsg = ServletUtility.getErrorMessage(request);
                                    if (errorMsg != null && !errorMsg.trim().equals("")) {
                                %>
                                    <div class="alert alert-danger alert-dismissible fade show"
                                        role="alert">
                                        <%=errorMsg%>
                                        <button type="button" class="close" data-dismiss="alert">
                                            <span>&times;</span>
                                        </button>
                                    </div>
                                    <%
                                    }
                                    String successMsg = ServletUtility.getSuccessMessage(request);
                                    if (successMsg != null && !successMsg.trim().equals("")) {
                                %>
                                    <div class="alert alert-success alert-dismissible fade show"
                                        role="alert">
                                        <%=successMsg%>
                                        <button type="button" class="close" data-dismiss="alert">
                                            <span>&times;</span>
                                        </button>
                                    </div>
                                    <%
                                    }
                                %>

                                    <input type="hidden" name="id" value="<%=dto.getId()%>">
                                    <input type="hidden" name="createdBy"
                                        value="<%=dto.getCreatedBy()%>"> <input type="hidden"
                                        name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
                                        type="hidden" name="createdDatetime"
                                        value="<%=DataUtility.getTimestamp(dto.getCreatedDateTime())%>">
                                    <input type="hidden" name="modifiedDatetime"
                                        value="<%=DataUtility.getTimestamp(dto.getModifiedDateTime())%>">

                                    <!-- Name -->
                                    <div class="form-group mb-3">
                                        <label><strong>Name</strong> <span class="text-danger">*</span></label>
                                        <div class="input-group input-group-sm">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i
                                                    class="fa fa-user text-muted"></i></span>
                                            </div>
                                            <input type="text" name="name" class="form-control"
                                                placeholder="Enter Name"
                                                value="<%=DataUtility.getStringData(dto.getName())%>">
                                        </div>
                                        <small class="text-danger"> <%=ServletUtility.getErrorMessage("name", request)%>
                                        </small>
                                    </div>

                                    <!-- Email -->
                                    <div class="form-group mb-3">
                                        <label><strong>Email</strong> <span class="text-danger">*</span></label>
                                        <div class="input-group input-group-sm">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i
                                                    class="fa fa-envelope text-muted"></i></span>
                                            </div>
                                            <input type="text" name="email" class="form-control"
                                                placeholder="Enter Email"
                                                value="<%=DataUtility.getStringData(dto.getEmail())%>">
                                        </div>
                                        <small class="text-danger"> <%=ServletUtility.getErrorMessage("email", request)%>
                                        </small>
                                    </div>

                                    <!-- Mobile No -->
                                    <div class="form-group mb-3">
                                        <label><strong>Mobile No</strong> <span class="text-danger">*</span></label>
                                        <div class="input-group input-group-sm">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i
                                                    class="fa fa-phone text-muted"></i></span>
                                            </div>
                                            <input type="text" name="mobileNo" class="form-control"
                                                placeholder="Enter Mobile Number"
                                                value="<%=DataUtility.getStringData(dto.getMobileNo())%>">
                                        </div>
                                        <small class="text-danger"> <%=ServletUtility.getErrorMessage("mobileNo", request)%>
                                        </small>
                                    </div>

                                    <!-- Message -->
                                    <div class="form-group mb-3">
                                        <label><strong>Message</strong> <span class="text-danger">*</span></label>
                                        <div class="input-group input-group-sm">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i
                                                    class="fa fa-comment text-muted"></i></span>
                                            </div>
                                            <textarea name="message" class="form-control"
                                                placeholder="Enter Message" rows="3"><%=DataUtility.getStringData(dto.getMessage())%></textarea>
                                        </div>
                                        <small class="text-danger"> <%=ServletUtility.getErrorMessage("message", request)%>
                                        </small>
                                    </div>

                                    <!-- Buttons -->
                                    <div class="text-center mt-3">
                                        <%
                                        if (dto != null && id > 0) {
                                    %>
                                        <input type="submit" name="operation"
                                            class="btn btn-success btn-sm px-4"
                                            value="<%=ContactCtl.OP_UPDATE%>"> <input
                                            type="submit" name="operation"
                                            class="btn btn-secondary btn-sm px-4"
                                            value="<%=ContactCtl.OP_CANCEL%>">
                                        <%
                                        } else {
                                    %>
                                        <input type="submit" name="operation"
                                            class="btn btn-success btn-sm px-4"
                                            value="<%=ContactCtl.OP_SAVE%>"> <input type="submit"
                                            name="operation" class="btn btn-secondary btn-sm px-4"
                                            value="<%=ContactCtl.OP_RESET%>">
                                        <%
                                        }
                                    %>
                                    </div>

                                </div>
                            </div>

                        </form>

                    </div>
                </div>
            </div>
        </main>

        <div class="footer">
            <%@include file="FooterView.jsp"%>
        </div>

    </div>
</body>
</html>