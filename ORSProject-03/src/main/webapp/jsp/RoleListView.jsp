<%@page import="in.co.rays.proj3.utill.HTMLUtility"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@page import="in.co.rays.proj3.controller.RoleListCtl"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
    <title>Role List</title>
    <link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
    <!-- Bootstrap 4 -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
</head>
<body class="p-4"
    style="background-image: url('<%=ORSView.APP_CONTEXT%>/img/Linkme.jpg'); 
           background-size: cover; 
           background-position: center; 
           background-attachment: fixed; 
           min-height: 100vh;">
    <%@include file="Header.jsp"%>

    <jsp:useBean id="dto" class="in.co.rays.proj3.dto.RoleDTO" scope="request"></jsp:useBean>

    <div class="container-fluid p-4">
        <h2 class="text-center text-light font-weight-bold mt-4">Role List</h2>

        <form action="<%=ORSView.ROLE_LIST_CTL%>" method="post">
            <%
                int pageNo = ServletUtility.getPageNo(request);
                int pageSize = ServletUtility.getPageSize(request);
                int index = ((pageNo - 1) * pageSize) + 1;
                int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

                List<RoleDTO> roleList = (List<RoleDTO>) request.getAttribute("roleList");
                List<RoleDTO> list = (List<RoleDTO>) ServletUtility.getList(request);
                Iterator<RoleDTO> it = list.iterator();
                
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
                                    <label><b>Role Name :</b></label>
                                </div>
                                <div class="mx-2">
                                    <%=HTMLUtility.getList("id", String.valueOf(dto.getId()), roleList)%>
                                </div>
                                <div class="mx-2">
                                    <input type="submit" class="btn btn-sm btn-primary" name="operation" value="<%=RoleListCtl.OP_SEARCH%>">
                                    <input type="submit" class="btn btn-sm btn-outline-secondary ml-1" name="operation" value="<%=RoleListCtl.OP_RESET%>">
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
                            <th>Role Name</th>
                            <th>Description</th>
                            <th>Edit</th>
                        </tr>
                    </thead>

                    <tbody>
                        <%
                            while (it.hasNext()) {
                                dto = (RoleDTO) it.next();
                        %>

                        <tr>
                            <td><input type="checkbox" class="case" name="ids" value="<%=dto.getId()%>"></td>
                            <td><%=index++%></td>                
                            <td class="text-capitalize"><%=dto.getName()%></td>
                            <td class="text-capitalize"><%=dto.getDescription()%></td>
                            <td><a href="RoleCtl?id=<%=dto.getId()%>" class="btn btn-link btn-sm p-0">Edit</a></td>
                        </tr>

                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>

            <table class="table w-100">
                <tr>
                    <td width="25%"><input type="submit" class="btn btn-outline-primary" name="operation" value="<%=RoleListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>></td>
                    <td width="25%" class="text-center"><input type="submit" class="btn btn-outline-success" name="operation" value="<%=RoleListCtl.OP_NEW%>"></td>
                    <td width="25%" class="text-center"><input type="submit" class="btn btn-outline-danger" name="operation" value="<%=RoleListCtl.OP_DELETE%>"></td>
                    <td width="25%" class="text-right"><input type="submit" class="btn btn-outline-primary" name="operation" value="<%=RoleListCtl.OP_NEXT%>" <%=nextListSize != 0 ? "" : "disabled"%>></td>
                </tr>
            </table>

            <%
                } else {
            %>

            <table class="table w-100">
                <tr>
                    <td class="text-right"><input type="submit" class="btn btn-outline-secondary" name="operation" value="<%=RoleListCtl.OP_BACK%>"></td>
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