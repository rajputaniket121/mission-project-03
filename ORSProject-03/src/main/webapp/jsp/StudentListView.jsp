<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.controller.StudentListCtl"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@page import="in.co.rays.proj3.dto.StudentDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
    <title>Student List</title>
    <link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
    <!-- Bootstrap 4 -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css    ">
</head>
<body class="p-4"
    style="background-image: url('<%=ORSView.APP_CONTEXT%>/img/Linkme.jpg'); 
           background-size: cover; 
           background-position: center; 
           background-attachment: fixed; 
           min-height: 100vh;">
    <%@include file="Header.jsp"%>
    <div class="container-fluid p-4">
        <h2 class="text-center text-light font-weight-bold mt-4">Student List</h2>

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

        <form action="<%=ORSView.STUDENT_LIST_CTL%>" method="post">
            <%
                int pageNo = ServletUtility.getPageNo(request);
                int pageSize = ServletUtility.getPageSize(request);
                int index = ((pageNo - 1) * pageSize) + 1;
                int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

                @SuppressWarnings("unchecked")
                List<StudentDTO> list = (List<StudentDTO>) ServletUtility.getList(request);
                Iterator<StudentDTO> it = list.iterator();

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
                                    <label><b>First Name :</b></label>
                                </div>
                                <div class="mx-2">
                                    <input type="text" class="form-control form-control-sm" name="firstName" placeholder="Enter First Name"
                                           value="<%=ServletUtility.getParameter("firstName", request)%>" style="width: 150px;">
                                </div>
                                <div class="mx-2">
                                    <label><b>Last Name :</b></label>
                                </div>
                                <div class="mx-2">
                                    <input type="text" class="form-control form-control-sm" name="lastName" placeholder="Enter Last Name"
                                           value="<%=ServletUtility.getParameter("lastName", request)%>" style="width: 150px;">
                                </div>
                                <div class="mx-2">
                                    <label><b>Email Id :</b></label>
                                </div>
                                <div class="mx-2">
                                    <input type="text" class="form-control form-control-sm" name="email" placeholder="Enter Email Id"
                                           value="<%=ServletUtility.getParameter("email", request)%>" style="width: 180px;">
                                </div>
                                <div class="mx-2">
                                    <input type="submit" class="btn btn-sm btn-primary" name="operation" value="<%=StudentListCtl.OP_SEARCH%>">
                                    <input type="submit" class="btn btn-sm btn-outline-secondary ml-1" name="operation" value="<%=StudentListCtl.OP_RESET%>">
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
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email Id</th>
                            <th>College Name</th>
                            <th>Gender</th>
                            <th>Mobile No</th>
                            <th>Date of Birth</th>
                            <th>Edit</th>
                        </tr>
                    </thead>

                    <tbody>
                        <%
                            while (it.hasNext()) {
                                StudentDTO dto = it.next();
                        %>
                        <tr>
                            <td><input type="checkbox" class="case" name="ids" value="<%=dto.getId()%>"></td>
                            <td><%=index++%></td>
                            <td class="text-capitalize"><%=dto.getFirstName()%></td>
                            <td class="text-capitalize"><%=dto.getLastName()%></td>
                            <td class="text-lowercase"><%=dto.getEmail()%></td>
                            <td class="text-capitalize"><%=dto.getCollegeName()%></td>
                            <td class="text-capitalize"><%=dto.getGender()%></td>
                            <td><%=dto.getMobileNo()%></td>
                            <%
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                String date = sdf.format(dto.getDob());
                            %>
                            <td><%=date%></td>
                            <td><a href="StudentCtl?id=<%=dto.getId()%>" class="btn btn-link btn-sm p-0">Edit</a></td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>

            <table class="table w-100">
                <tr>
                    <td width="25%"><input type="submit" class="btn btn-outline-primary" name="operation" value="<%=StudentListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>></td>
                    <td width="25%" class="text-center"><input type="submit" class="btn btn-outline-success" name="operation" value="<%=StudentListCtl.OP_NEW%>"></td>
                    <td width="25%" class="text-center"><input type="submit" class="btn btn-outline-danger" name="operation" value="<%=StudentListCtl.OP_DELETE%>"></td>
                    <td width="25%" class="text-right"><input type="submit" class="btn btn-outline-primary" name="operation" value="<%=StudentListCtl.OP_NEXT%>" <%= (nextPageSize != 0) ? "" : "disabled"%>></td>
                </tr>
            </table>

            <%
                }
                if (list.size() == 0) {
            %>
            <table class="table w-100">
                <tr>
                    <td class="text-right"><input type="submit"
						class="btn btn-warning btn-sm font-weight-bold" name="operation"
						value="<%=StudentListCtl.OP_BACK%>"></td>
                </tr>
            </table>
            <%
                }
            %>
        </form>
    </div>
    <%@include file="FooterView.jsp"%>
</body>
</html>