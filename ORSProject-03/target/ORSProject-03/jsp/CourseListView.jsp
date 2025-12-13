<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.utill.HTMLUtility"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.controller.CourseListCtl"%>
<%@page import="in.co.rays.proj3.controller.BaseCtl"%>
<%@page import="in.co.rays.proj3.dto.CourseDTO"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
    <title>Course List</title>
    <link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
    <!-- Bootstrap 4 -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css    ">
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
        <jsp:useBean id="dto" class="in.co.rays.proj3.dto.CourseDTO" scope="request"></jsp:useBean>

        <h2 class="text-center text-light font-weight-bold mt-4">Course List</h2>

        <%
            if (!ServletUtility.getErrorMessage(request).equals("")) {
        %>
        <div class="alert alert-danger"><%=ServletUtility.getErrorMessage(request)%></div>
        <%
        }
        if (!ServletUtility.getSuccessMessage(request).equals("")) {
        %>
        <div class="alert alert-success"><%=ServletUtility.getSuccessMessage(request)%></div>
        <%
        }
        %>

        <form action="<%=ORSView.COURSE_LIST_CTL%>" method="post">
            <%
                int pageNo = ServletUtility.getPageNo(request);
                int pageSize = ServletUtility.getPageSize(request);
                int index = ((pageNo - 1) * pageSize) + 1;
                int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
                List<CourseDTO> courseList = (List<CourseDTO>) request.getAttribute("courseList");
                List<CourseDTO> list = (List<CourseDTO>) ServletUtility.getList(request);
                Iterator<CourseDTO> it = list.iterator();

                if (list.size() != 0) {
            %>

            <input type="hidden" name="pageNo" value="<%=pageNo%>">
            <input type="hidden" name="pageSize" value="<%=pageSize%>">

            <!-- Floating-style search form -->
            <div class="table-responsive">
                <table class="table table-borderless w-100 text-center bg-transparent">
                    <tr>
                        <td>
                            <div class="d-flex justify-content-center align-items-center flex-wrap bg-light bg-opacity-75 p-3 rounded shadow-sm">
                                <div class="mx-2">
                                    <label><b>Course Name :</b></label>
                                </div>
                                <div class="mx-2">
                                   <%=HTMLUtility.getList("courseId", String.valueOf(dto.getId()), courseList)%>
                                </div>
                                <div class="mx-2">
                                    <input type="submit" class="btn btn-sm btn-primary" name="operation" value="<%=CourseListCtl.OP_SEARCH%>">
                                    <input type="submit" class="btn btn-sm btn-outline-secondary ml-1" name="operation" value="<%=CourseListCtl.OP_RESET%>">
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
                            <th>Name</th>
                            <th>Duration</th>
                            <th>Description</th>
                            <th>Edit</th>
                        </tr>
                    </thead>

                    <tbody>
                        <%
                            while (it.hasNext()) {
                                dto = (CourseDTO) it.next();
                        %>
                        <tr>
                            <td><input type="checkbox" class="case" name="ids" value="<%=dto.getId()%>"></td>
                            <td><%=index++%></td>
                            <td class="text-capitalize"><%=dto.getCourseName()%></td>
                            <td class="text-capitalize"><%=dto.getDuration()%></td>
                            <td class="text-capitalize"><%=dto.getDescription()%></td>
                            <td><a href="CourseCtl?id=<%=dto.getId()%>" class="btn btn-link btn-sm p-0">Edit</a></td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>

            <table class="table w-100">
                <tr>
                    <td width="25%"><input type="submit" class="btn btn-outline-primary" name="operation" value="<%=CourseListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>></td>
                    <td width="25%" class="text-center"><input type="submit" class="btn btn-outline-success" name="operation" value="<%=CourseListCtl.OP_NEW%>"></td>
                    <td width="25%" class="text-center"><input type="submit" class="btn btn-outline-danger" name="operation" value="<%=CourseListCtl.OP_DELETE%>"></td>
                    <td width="25%" class="text-right"><input type="submit" class="btn btn-outline-primary" name="operation" value="<%=CourseListCtl.OP_NEXT%>" <%=nextPageSize != 0 ? "" : "disabled"%>></td>
                </tr>
            </table>

            <%
                }
                if (list.size() == 0) {
            %>
            <table class="table w-100">
                <tr>
                    <td class="text-right"><input type="submit" class="btn btn-outline-secondary" name="operation" value="<%=CourseListCtl.OP_BACK%>"></td>
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