<%@page import="in.co.rays.proj3.controller.FeedbackListCtl"%>
<%@page import="in.co.rays.proj3.utill.HTMLUtility"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.dto.FeedbackDTO"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj3.controller.ORSView"%>

<html>
<head>
<title>Feedback List</title>

<link rel="icon" type="image/png"
    href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

<style>
.p4 {
    background-image: url('<%=ORSView.APP_CONTEXT%>/img/Linkme.jpg');
    background-size: cover;
    background-position: center;
    background-attachment: fixed;
    min-height: 100vh;
    padding-top: 70px;
    padding-bottom: 80px;
}
</style>

</head>

<body class="p4">

    <%@include file="Header.jsp"%>

    <div class="container-fluid">

        <jsp:useBean id="dto" class="in.co.rays.proj3.dto.FeedbackDTO"
            scope="request"></jsp:useBean>

        <h2 class="text-center text-light font-weight-bold">
            Feedback List
        </h2>

        <!-- Error & Success Messages -->
        <%
        if (!ServletUtility.getErrorMessage(request).equals("")) {
    %>
        <div class="alert alert-danger">
            <%=ServletUtility.getErrorMessage(request)%>
        </div>
        <%
        }
        if (!ServletUtility.getSuccessMessage(request).equals("")) {
    %>
        <div class="alert alert-success">
            <%=ServletUtility.getSuccessMessage(request)%>
        </div>
        <%
        }
    %>

        <form action="<%=ORSView.FEEDBACK_LIST_CTL%>" method="post">

            <%
            int pageNo = ServletUtility.getPageNo(request);
            int pageSize = ServletUtility.getPageSize(request);
            int index = ((pageNo - 1) * pageSize) + 1;
            int nextPageSize =
                DataUtility.getInt(request.getAttribute("nextListSize").toString());

            HashMap<String, String> ratingMap =
                (HashMap<String, String>) request.getAttribute("ratingMap");

            List<FeedbackDTO> list =
                (List<FeedbackDTO>) ServletUtility.getList(request);

            Iterator<FeedbackDTO> it = list.iterator();

            if (list.size() != 0) {
        %>

            <input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
                type="hidden" name="pageSize" value="<%=pageSize%>">

            <div class="table-responsive">
                <table
                    class="table table-borderless w-100 text-center bg-transparent">
                    <tr>
                        <td>
                            <div
                                class="d-flex justify-content-center align-items-center flex-wrap bg-light p-3 rounded shadow-sm">
                                <div class="mx-2">
                                    <label><b>Feedback Code :</b></label>
                                </div>
                                <div class="mx-2">
                                    <input type="text" class="form-control form-control-sm"
                                        name="feedbackCode" placeholder="Enter Feedback Code"
                                        value="<%=ServletUtility.getParameter("feedbackCode", request)%>">
                                </div>
                                <div class="mx-2">
                                    <label><b>Customer Name :</b></label>
                                </div>
                                <div class="mx-2">
                                    <input type="text" class="form-control form-control-sm"
                                        name="customerName" placeholder="Enter Customer Name"
                                        value="<%=ServletUtility.getParameter("customerName", request)%>">
                                </div>
                                <div class="mx-2">
                                    <label><b>Rating :</b></label>
                                </div>
                                <div class="mx-2">
                                    <%=HTMLUtility.getList("rating", String.valueOf(dto.getRating()), ratingMap)%>
                                </div>
                                <div class="mx-2">
                                    <input type="submit" class="btn btn-sm btn-primary"
                                        name="operation" value="<%=FeedbackListCtl.OP_SEARCH%>">
                                    <input type="submit"
                                        class="btn btn-sm btn-outline-secondary ml-1" name="operation"
                                        value="<%=FeedbackListCtl.OP_RESET%>">
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>

            <br>

            <!-- DATA TABLE -->
            <div class="table-responsive">
                <table
                    class="table table-bordered table-hover w-100 text-center bg-white shadow-sm">

                    <thead class="thead-light">
                        <tr>
                            <th><input type="checkbox" id="selectall"> Select
                                All</th>
                            <th>S.No</th>
                            <th>Feedback Code</th>
                            <th>Customer Name</th>
                            <th>Rating</th>
                            <th>Comments</th>
                            <th>Feedback Date</th>
                            <th>Edit</th>
                        </tr>
                    </thead>

                    <tbody>
                        <%
                    while (it.hasNext()) {
                        dto = (FeedbackDTO) it.next();
                %>
                        <tr>
                            <td><input type="checkbox" class="case" name="ids"
                                value="<%=dto.getId()%>"></td>
                            <td><%=index++%></td>
                            <td><%=dto.getFeedbackCode()%></td>
                            <td class="text-capitalize"><%=dto.getCustomerName()%></td>
                            <td><%=dto.getRating()%></td>
                            <td><%=dto.getComments()%></td>
                            <td><%=DataUtility.getDateString(dto.getFeedbackDate())%></td>
                            <td><a href="FeedbackCtl?id=<%=dto.getId()%>"
                                class="btn btn-link btn-sm p-0">
                                    Edit
                            </a></td>
                        </tr>
                        <%
                    }
                %>
                    </tbody>

                </table>
            </div>

            <!-- PAGINATION & ACTIONS -->
            <table class="table w-100">
                <tr>
                    <td width="25%"><input type="submit"
                        class="btn btn-outline-primary" name="operation"
                        value="<%=FeedbackListCtl.OP_PREVIOUS%>"
                        <%=pageNo > 1 ? "" : "disabled"%>></td>

                    <td width="25%" class="text-center"><input type="submit"
                        class="btn btn-outline-success" name="operation"
                        value="<%=FeedbackListCtl.OP_NEW%>"></td>

                    <td width="25%" class="text-center"><input type="submit"
                        class="btn btn-outline-danger" name="operation"
                        value="<%=FeedbackListCtl.OP_DELETE%>"></td>

                    <td width="25%" class="text-right"><input type="submit"
                        class="btn btn-outline-primary" name="operation"
                        value="<%=FeedbackListCtl.OP_NEXT%>"
                        <%=nextPageSize != 0 ? "" : "disabled"%>></td>
                </tr>
            </table>

            <%
            }
            if (list.size() == 0) {
        %>

            <table class="table w-100">
                <tr>
                    <td class="text-right"><input type="submit"
                        class="btn btn-warning" name="operation"
                        value="<%=FeedbackListCtl.OP_BACK%>"></td>
                </tr>
            </table>

            <%
            }
        %>

        </form>
    </div>

    <div>
        <%@include file="FooterView.jsp"%>
    </div>

</body>
</html>