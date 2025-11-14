<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.model.RoleModelInt"%>
<%@page import="in.co.rays.proj3.model.ModelFactory"%>
<%@page import="in.co.rays.proj3.utill.HTMLUtility"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@page import="in.co.rays.proj3.controller.UserListCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj3.dto.RoleDTO"%>
<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
<title>User List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
<!-- Bootstrap 4 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
</head>

<body class="p-4"
	style="background-image: url('<%=ORSView.APP_CONTEXT%>/img/Linkme.jpg'); 
           background-size: cover; 
           background-position: center; 
           background-attachment: fixed; 
           min-height: 100vh;">
	<%@include file="Header.jsp"%>
	<jsp:useBean id="dto" class="in.co.rays.proj3.dto.UserDTO"
		scope="request"></jsp:useBean>

	<div class="container-fluid p-4">
		<h2 class="text-center text-light font-weight-bold mt-4">User
			List</h2>

		<form action="<%=ORSView.USER_LIST_CTL%>" method="post">
			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
			List<RoleDTO> roleList = (List<RoleDTO>) request.getAttribute("roleList");
			List<UserDTO> list = (List<UserDTO>) ServletUtility.getList(request);
			Iterator<UserDTO> it = list.iterator();
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

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<!-- Floating-style search form -->
			<div class="table-responsive">
				<table
					class="table table-borderless w-100 text-center bg-transparent">
					<tr>
						<td>
							<div
								class="d-flex justify-content-center align-items-center flex-wrap bg-light bg-opacity-75 p-3 rounded shadow-sm">
								<div class="mx-2">
									<input type="text" class="form-control form-control-sm"
										name="firstName" placeholder="Enter First Name"
										value="<%=ServletUtility.getParameter("firstName", request)%>"
										style="width: 180px;">
								</div>
								<div class="mx-2">
									<input type="text" class="form-control form-control-sm"
										name="login" placeholder="Enter Login ID"
										value="<%=ServletUtility.getParameter("login", request)%>"
										style="width: 180px;">
								</div>
								<div class="mx-2">
									<%=HTMLUtility.getList("roleId", String.valueOf(dto.getRoleId()), roleList)%>
								</div>
								<div class="mx-2">
									<input type="submit" class="btn btn-sm btn-primary btn-sm font-weight-bold"
										name="operation" value="<%=UserListCtl.OP_SEARCH%>"> <input
										type="submit" class="btn btn-sm btn-outline-secondary btn-sm font-weight-bold ml-1"
										name="operation" value="<%=UserListCtl.OP_RESET%>">
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>

			<!-- Data Table -->
			<div class="table-responsive">
				<table
					class="table table-bordered table-hover w-100 text-center bg-white shadow-sm">
					<thead class="thead-light">
						<tr>
							<th><input type="checkbox" id="selectall" /> Select All</th>
							<th>S.No</th>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Login Id</th>
							<th>Mobile No</th>
							<th>Gender</th>
							<th>DOB</th>
							<th>Role</th>
							<th>Edit</th>
						</tr>
					</thead>
					<tbody>
						<%
						while (it.hasNext()) {
							dto = (UserDTO) it.next();
							RoleModelInt model = ModelFactory.getInstance().getRoleModel();
							RoleDTO roleBean = model.findByPK(dto.getRoleId());
							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
							String date = sdf.format(dto.getDob());
						%>
						<tr>
							<td><input type="checkbox" class="case" name="ids"
								value="<%=dto.getId()%>"
								<%=(userDto.getId() == dto.getId() || dto.getRoleId() == RoleDTO.ADMIN) ? "disabled" : ""%>></td>
							<td><%=index++%></td>
							<td class="text-capitalize"><%=dto.getFirstName()%></td>
							<td class="text-capitalize"><%=dto.getLastName()%></td>
							<td class="text-lowercase"><%=dto.getLogin()%></td>
							<td><%=dto.getMobileNo()%></td>
							<td class="text-capitalize"><%=dto.getGender()%></td>
							<td><%=date%></td>
							<td class="text-capitalize"><%=roleBean.getName()%></td>
							<td><a href="<%=ORSView.USER_CTL%>?id=<%=dto.getId()%>"
								class="btn btn-link btn-sm p-0"
								<%=(userDto.getId() == dto.getId() || dto.getRoleId() == RoleDTO.ADMIN) ? "onclick='return false;'" : ""%>>Edit</a></td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
			</div>

			<table class="table w-100">
				<tr>
					<td width="25%"><input type="submit"
						class="btn btn-outline-primary btn-sm font-weight-bold" name="operation"
						value="<%=UserListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td width="25%" class="text-center"><input type="submit"
						class="btn btn-outline-success btn-sm font-weight-bold" name="operation"
						value="<%=UserListCtl.OP_NEW%>"></td>
					<td width="25%" class="text-center"><input type="submit"
						class="btn btn-outline-danger btn-sm font-weight-bold" name="operation"
						value="<%=UserListCtl.OP_DELETE%>"></td>
					<td width="25%" class="text-right"><input type="submit"
						class="btn btn-outline-primary btn-sm font-weight-bold" name="operation"
						value="<%=UserListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>
				</tr>
			</table>
			<%
			} else {
			%>
			<table class="table w-100">
				<tr>
					<td class="text-right"><input type="submit"
						class="btn btn-warning btn-sm font-weight-bold" name="operation"
						value="<%=UserListCtl.OP_BACK%>"></td>
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
