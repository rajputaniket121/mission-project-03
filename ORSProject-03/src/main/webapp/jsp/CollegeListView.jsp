<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@page import="in.co.rays.proj3.dto.CollegeDTO"%>
<%@page import="in.co.rays.proj3.utill.HTMLUtility"%>
<%@page import="in.co.rays.proj3.controller.CollegeListCtl"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
    <title>College List</title>
    <link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
    <!-- Bootstrap 4 -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <style>
.footer {
    left: 0 !important;
    right: 0 !important;
    margin-left: 0 !important;
    padding-left: 0 !important;
}
</style>
    
</head>

<body class="p-4"
    style="background-image: url('<%=ORSView.APP_CONTEXT%>/img/Linkme.jpg');
           background-size: cover;
           background-position: center;
           background-attachment: fixed;
           min-height: 100vh;">

    <%@include file="Header.jsp"%>

    <div class="container-fluid p-4">
        <jsp:useBean id="dto" class="in.co.rays.proj3.dto.CollegeDTO" scope="request"></jsp:useBean>
        <h2 class="text-center text-light font-weight-bold mt-4">College List</h2>

        <%
            // Retrieve messages from ServletUtility
            String errorMsg = ServletUtility.getErrorMessage(request);
            String successMsg = ServletUtility.getSuccessMessage(request);
        %>

        <% if (errorMsg != null && errorMsg.trim().length() > 0) { %>
            <div class="alert alert-danger text-center mt-3"><%=errorMsg%></div>
        <% } %>

        <% if (successMsg != null && successMsg.trim().length() > 0) { %>
            <div class="alert alert-success text-center mt-3"><%=successMsg%></div>
        <% } %>

        <form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="POST">

            <%
                int pageNo = ServletUtility.getPageNo(request);
                int pageSize = ServletUtility.getPageSize(request);
                int index = ((pageNo - 1) * pageSize) + 1;
                int nextPageSize = 0;

                if (request.getAttribute("nextListSize") != null) {
                    nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
                }

                List<CollegeDTO> collegeList = (List<CollegeDTO>) request.getAttribute("collegeList");
                List<CollegeDTO> list = (List<CollegeDTO>) ServletUtility.getList(request);
            %>

            <%
                if (list != null && list.size() > 0) {
            %>

            <input type="hidden" name="pageNo" value="<%=pageNo%>">
            <input type="hidden" name="pageSize" value="<%=pageSize%>">

            <!-- Search Panel -->
            <div class="table-responsive">
                <table class="table table-borderless w-100 text-center bg-transparent">
                    <tr>
                        <td>
                            <div class="d-flex justify-content-center align-items-center flex-wrap bg-light bg-opacity-75 p-3 rounded shadow-sm">
                                <div class="mx-2">
                                    <%=HTMLUtility.getList("collegeId", String.valueOf(dto.getId()), collegeList)%>
                                </div>
                                <div class="mx-2">
                                    <input type="text" class="form-control form-control-sm" name="city"
                                           placeholder="Enter College City"
                                           value="<%=ServletUtility.getParameter("city", request)%>"
                                           style="width: 180px;">
                                </div>
                                <div class="mx-2">
                                    <input type="submit" class="btn btn-sm btn-primary" name="operation"
                                           value="<%=CollegeListCtl.OP_SEARCH%>">
                                    <input type="submit" class="btn btn-sm btn-outline-secondary ml-1" name="operation"
                                           value="<%=CollegeListCtl.OP_RESET%>">
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <br>

            <!-- Data Table -->
            <div class="table-responsive">
                <table class="table table-bordered table-hover w-100 text-center bg-white shadow-sm">
                    <thead class="thead-light">
                        <tr>
                            <th><input type="checkbox" id="selectall" /> Select All</th>
                            <th>S.No</th>
                            <th>College Name</th>
                            <th>Address</th>
                            <th>State</th>
                            <th>City</th>
                            <th>Phone No</th>
                            <th>Edit</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            Iterator<CollegeDTO> it = list.iterator();
                            while (it.hasNext()) {
                                dto = it.next();
                        %>
                        <tr>
                            <td><input type="checkbox" class="case" name="ids" value="<%=dto.getId()%>"></td>
                            <td><%=index++%></td>
                            <td class="text-capitalize"><%=dto.getName()%></td>
                            <td class="text-capitalize"><%=dto.getAddress()%></td>
                            <td class="text-capitalize"><%=dto.getState()%></td>
                            <td class="text-capitalize"><%=dto.getCity()%></td>
                            <td><%=dto.getPhoneNo()%></td>
                            <td><a href="CollegeCtl?id=<%=dto.getId()%>" class="btn btn-link btn-sm p-0">Edit</a></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>

            <!-- Pagination and Action Buttons -->
            <table class="table w-100">
                <tr>
                    <td width="25%">
                        <input type="submit" class="btn btn-outline-primary"
                               name="operation" value="<%=CollegeListCtl.OP_PREVIOUS%>"
                               <%=pageNo > 1 ? "" : "disabled"%>>
                    </td>
                    <td width="25%" class="text-center">
                        <input type="submit" class="btn btn-outline-success"
                               name="operation" value="<%=CollegeListCtl.OP_NEW%>">
                    </td>
                    <td width="25%" class="text-center">
                        <input type="submit" class="btn btn-outline-danger"
                               name="operation" value="<%=CollegeListCtl.OP_DELETE%>">
                    </td>
                    <td width="25%" class="text-right">
                        <input type="submit" class="btn btn-outline-primary"
                               name="operation" value="<%=CollegeListCtl.OP_NEXT%>"
                               <%= (nextPageSize != 0) ? "" : "disabled" %>>
                    </td>
                </tr>
            </table>

            <%
                } else {
            %>
            <!-- Back Button -->
            <table class="table w-100 mt-3">
                <tr>
                    <td class="text-right">
                        <input type="submit" class="btn btn-warning btn-sm font-weight-bold"
                               name="operation" value="<%=CollegeListCtl.OP_BACK%>">
                    </td>
                </tr>
            </table>

            <%
                }
            %>
        </form>
    </div>

    <div class="footer">
    	<%@include file="FooterView.jsp"%>
    </div>

</body>
</html>
