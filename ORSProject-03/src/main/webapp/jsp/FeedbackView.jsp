<%@page import="in.co.rays.proj3.controller.FeedbackCtl"%>
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
<title>Feedback View</title>

<link rel="icon" type="image/png"
    href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

<!-- jQuery UI -->
<link rel="stylesheet"
    href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
        $(function() {
            $("#udate").datepicker({
                changeMonth : true,
                changeYear : true,
                yearRange : '1950:2030',
                dateFormat : 'dd/mm/yy'
            });
        });
    </script>

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
            <%@include file="calendar.jsp" %>
        </div>

        <main>
            <div class="container">
                <div class="row justify-content-center mt-4">

                    <div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-5">

                        <form action="<%=ORSView.FEEDBACK_CTL%>" method="post" class="w-100">

                            <jsp:useBean id="dto" class="in.co.rays.proj3.dto.FeedbackDTO"
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
                                        Feedback
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
                                    HashMap<String, String> ratingMap =
                                        (HashMap<String, String>) request.getAttribute("ratingMap");
                                %>

                                    <input type="hidden" name="id" value="<%=dto.getId()%>">
                                    <input type="hidden" name="createdBy"
                                        value="<%=dto.getCreatedBy()%>"> <input type="hidden"
                                        name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
                                        type="hidden" name="createdDatetime"
                                        value="<%=DataUtility.getTimestamp(dto.getCreatedDateTime())%>">
                                    <input type="hidden" name="modifiedDatetime"
                                        value="<%=DataUtility.getTimestamp(dto.getModifiedDateTime())%>">

                                    <!-- Feedback Code -->
                                    <div class="form-group mb-3">
                                        <label><strong>Feedback Code</strong> <span class="text-danger">*</span></label>
                                        <div class="input-group input-group-sm">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i
                                                    class="fa fa-code text-muted"></i></span>
                                            </div>
                                            <input type="text" name="feedbackCode" class="form-control"
                                                placeholder="Enter Feedback Code"
                                                value="<%=DataUtility.getStringData(dto.getFeedbackCode())%>">
                                        </div>
                                        <small class="text-danger"> <%=ServletUtility.getErrorMessage("feedbackCode", request)%>
                                        </small>
                                    </div>

                                    <!-- Customer Name -->
                                    <div class="form-group mb-3">
                                        <label><strong>Customer Name</strong> <span class="text-danger">*</span></label>
                                        <div class="input-group input-group-sm">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i
                                                    class="fa fa-user text-muted"></i></span>
                                            </div>
                                            <input type="text" name="customerName" class="form-control"
                                                placeholder="Enter Customer Name"
                                                value="<%=DataUtility.getStringData(dto.getCustomerName())%>">
                                        </div>
                                        <small class="text-danger"> <%=ServletUtility.getErrorMessage("customerName", request)%>
                                        </small>
                                    </div>

                                    <!-- Rating -->
                                    <div class="form-group mb-3">
                                        <label><strong>Rating</strong> <span class="text-danger">*</span></label>
                                        <div class="input-group input-group-sm">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i
                                                    class="fa fa-star text-muted"></i></span>
                                            </div>
                                            <%=HTMLUtility.getList("rating", dto.getRating(), ratingMap)%>
                                        </div>
                                        <small class="text-danger"> <%=ServletUtility.getErrorMessage("rating", request)%>
                                        </small>
                                    </div>

                                    <!-- Comments -->
                                    <div class="form-group mb-3">
                                        <label><strong>Comments</strong> <span class="text-danger">*</span></label>
                                        <div class="input-group input-group-sm">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i
                                                    class="fa fa-comment text-muted"></i></span>
                                            </div>
                                            <textarea name="comments" class="form-control"
                                                placeholder="Enter Comments" rows="3"><%=DataUtility.getStringData(dto.getComments())%></textarea>
                                        </div>
                                        <small class="text-danger"> <%=ServletUtility.getErrorMessage("comments", request)%>
                                        </small>
                                    </div>

                                    <!-- Feedback Date -->
                                    <div class="form-group mb-3">
                                        <label><strong>Feedback Date</strong> <span class="text-danger">*</span></label>
                                        <div class="input-group input-group-sm">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i
                                                    class="fa fa-calendar text-muted"></i></span>
                                            </div>
                                            <input type="text" id="udate" name="feedbackDate" class="form-control"
                                                placeholder="Select Feedback Date" readonly
                                                value="<%=DataUtility.getDateString(dto.getFeedbackDate())%>">
                                        </div>
                                        <small class="text-danger"> <%=ServletUtility.getErrorMessage("feedbackDate", request)%>
                                        </small>
                                    </div>

                                    <!-- Buttons -->
                                    <div class="text-center mt-3">
                                        <%
                                        if (dto != null && id > 0) {
                                    %>
                                        <input type="submit" name="operation"
                                            class="btn btn-success btn-sm px-4"
                                            value="<%=FeedbackCtl.OP_UPDATE%>"> <input
                                            type="submit" name="operation"
                                            class="btn btn-secondary btn-sm px-4"
                                            value="<%=FeedbackCtl.OP_CANCEL%>">
                                        <%
                                        } else {
                                    %>
                                        <input type="submit" name="operation"
                                            class="btn btn-success btn-sm px-4"
                                            value="<%=FeedbackCtl.OP_SAVE%>"> <input type="submit"
                                            name="operation" class="btn btn-secondary btn-sm px-4"
                                            value="<%=FeedbackCtl.OP_RESET%>">
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