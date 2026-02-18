<%@page import="in.co.rays.proj3.controller.SupportCtl"%>
<%@page import="in.co.rays.proj3.utill.HTMLUtility"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj3.controller.ORSView"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Support View</title>

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

                        <form action="<%=ORSView.SUPPORT_CTL%>" method="post" class="w-100">

                            <jsp:useBean id="dto" class="in.co.rays.proj3.dto.SupportDTO"
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
                                        Support Ticket
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

                                    <%
                                    HashMap<String, String> issueTypeMap =
                                        (HashMap<String, String>) request.getAttribute("issueTypeMap");
                                    HashMap<String, String> ticketStatusMap =
                                        (HashMap<String, String>) request.getAttribute("ticketStatusMap");
                                %>

                                    <input type="hidden" name="id" value="<%=dto.getId()%>">
                                    <input type="hidden" name="createdBy"
                                        value="<%=dto.getCreatedBy()%>"> <input type="hidden"
                                        name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
                                        type="hidden" name="createdDatetime"
                                        value="<%=DataUtility.getTimestamp(dto.getCreatedDateTime())%>">
                                    <input type="hidden" name="modifiedDatetime"
                                        value="<%=DataUtility.getTimestamp(dto.getModifiedDateTime())%>">

                                    <!-- User Name -->
                                    <div class="form-group mb-3">
                                        <label><strong>User Name</strong> <span class="text-danger">*</span></label>
                                        <div class="input-group input-group-sm">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i
                                                    class="fa fa-user text-muted"></i></span>
                                            </div>
                                            <input type="text" name="userName" class="form-control"
                                                placeholder="Enter User Name"
                                                value="<%=DataUtility.getStringData(dto.getUserName())%>">
                                        </div>
                                        <small class="text-danger"> <%=ServletUtility.getErrorMessage("userName", request)%>
                                        </small>
                                    </div>

                                    <!-- Issue Type -->
                                    <div class="form-group mb-3">
                                        <label><strong>Issue Type</strong> <span class="text-danger">*</span></label>
                                        <div class="input-group input-group-sm">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i
                                                    class="fa fa-list text-muted"></i></span>
                                            </div>
                                            <%=HTMLUtility.getList("issueType", dto.getIssueType(), issueTypeMap)%>
                                        </div>
                                        <small class="text-danger"> <%=ServletUtility.getErrorMessage("issueType", request)%>
                                        </small>
                                    </div>

                                    <!-- Issue Description -->
                                    <div class="form-group mb-3">
                                        <label><strong>Issue Description</strong> <span class="text-danger">*</span></label>
                                        <div class="input-group input-group-sm">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i
                                                    class="fa fa-comment text-muted"></i></span>
                                            </div>
                                            <textarea name="issueDescription" class="form-control"
                                                placeholder="Enter Issue Description" rows="3"><%=DataUtility.getStringData(dto.getIssueDescription())%></textarea>
                                        </div>
                                        <small class="text-danger"> <%=ServletUtility.getErrorMessage("issueDescription", request)%>
                                        </small>
                                    </div>

                                    <!-- Ticket Status -->
                                    <div class="form-group mb-3">
                                        <label><strong>Ticket Status</strong> <span class="text-danger">*</span></label>
                                        <div class="input-group input-group-sm">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i
                                                    class="fa fa-flag text-muted"></i></span>
                                            </div>
                                            <%=HTMLUtility.getList("ticketStatus", dto.getTicketStatus(), ticketStatusMap)%>
                                        </div>
                                        <small class="text-danger"> <%=ServletUtility.getErrorMessage("ticketStatus", request)%>
                                        </small>
                                    </div>

                                    <!-- Buttons -->
                                    <div class="text-center mt-3">
                                        <%
                                        if (dto != null && id > 0) {
                                    %>
                                        <input type="submit" name="operation"
                                            class="btn btn-success btn-sm px-4"
                                            value="<%=SupportCtl.OP_UPDATE%>"> <input
                                            type="submit" name="operation"
                                            class="btn btn-secondary btn-sm px-4"
                                            value="<%=SupportCtl.OP_CANCEL%>">
                                        <%
                                        } else {
                                    %>
                                        <input type="submit" name="operation"
                                            class="btn btn-success btn-sm px-4"
                                            value="<%=SupportCtl.OP_SAVE%>"> <input type="submit"
                                            name="operation" class="btn btn-secondary btn-sm px-4"
                                            value="<%=SupportCtl.OP_RESET%>">
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