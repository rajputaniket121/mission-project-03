<%@page import="in.co.rays.proj3.controller.UserCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj3.utill.HTMLUtility"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj3.controller.ORSView"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>User View</title>
<!-- jQuery UI -->
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		// Calculate today minus 18 years
		var today = new Date();
		var cutoff = new Date(today.getFullYear() - 18, today.getMonth(), today
				.getDate());

		$("#udate").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1950:' + cutoff.getFullYear(), // dynamic upper limit
			dateFormat : 'dd/mm/yy',
			maxDate : cutoff
		// users can't pick a date after this
		});
	});
</script>

<style type="text/css">
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/Linkme.jpg');
	background-size: cover;
	background-position: center;
	background-attachment: fixed;
	min-height: 100vh;
	display: flex;
	flex-direction: column;
	justify-content: center;
	padding-top: 70px;
	padding-bottom: 40px;
	overflow: visible !important;
}
.ui-datepicker {
    z-index: 999999 !important;
    font-size: 14px !important;
}

.grad-card {
	background: rgba(255, 255, 255, 0.92);
}

</style>
</head>
<body class="p4 d-flex flex-column">
	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>
	<main
		class="container flex-grow-1 d-flex align-items-center justify-content-center">
		<form action="<%=ORSView.USER_CTL%>" method="post" class="w-100"
			style="max-width: 800px;">
			<jsp:useBean id="dto" class="in.co.rays.proj3.dto.UserDTO"
				scope="request" />
			<%
			List<RoleDTO> roleList = (List<RoleDTO>) request.getAttribute("roleList");
			%>
			<div class="card grad-card shadow-sm">
				<div class="card-body py-3">
					<%
					long id = DataUtility.getLong(request.getParameter("id"));
					%>
					<h5 class="text-center text-success fw-bold mb-3">
						<%
						if (dto != null && id > 0) {
						%>
						Update User
						<%
						} else {
						%>
						Add User
						<%
						}
						%>
					</h5>
					<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
					%>
					<div class="alert alert-success alert-dismissible">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<%=ServletUtility.getSuccessMessage(request)%>
					</div>
					<%
					}
					if (!ServletUtility.getErrorMessage(request).equals("")) {
					%>
					<div class="alert alert-danger alert-dismissible">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<%=ServletUtility.getErrorMessage(request)%>
					</div>
					<%
					}
					%>
					<input type="hidden" name="id" value="<%=dto.getId()%>"> <input
						type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
					<input type="hidden" name="modifiedBy"
						value="<%=dto.getModifiedBy()%>"> <input type="hidden"
						name="createdDatetime"
						value="<%=DataUtility.getTimestamp(dto.getCreatedDateTime())%>">
					<input type="hidden" name="modifiedDatetime"
						value="<%=DataUtility.getTimestamp(dto.getModifiedDateTime())%>">
					<div class="row">
						<!-- First Name -->
						<div class="col-md-6 mb-2">
							<label class="form-label"><strong>First Name</strong> <span
								class="text-danger">*</span></label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i
									class="fa fa-user-alt text-muted"></i></span> <input type="text"
									class="form-control" name="firstName"
									placeholder="Enter First Name"
									value="<%=DataUtility.getStringData(dto.getFirstName())%>">
							</div>
							<div class="text-danger small"><%=ServletUtility.getErrorMessage("firstName", request)%></div>
						</div>
						<!-- Last Name -->
						<div class="col-md-6 mb-2">
							<label class="form-label"><strong>Last Name</strong> <span
								class="text-danger">*</span></label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i
									class="fa fa-user-circle text-muted"></i></span> <input type="text"
									class="form-control" name="lastName"
									placeholder="Enter Last Name"
									value="<%=DataUtility.getStringData(dto.getLastName())%>">
							</div>
							<div class="text-danger small"><%=ServletUtility.getErrorMessage("lastName", request)%></div>
						</div>
						<!-- Login Id -->
						<div class="col-md-6 mb-2">
							<label class="form-label"><strong>Login Id</strong> <span
								class="text-danger">*</span></label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i
									class="fa fa-envelope text-muted"></i></span> <input type="text"
									class="form-control" name="login" placeholder="Enter Email ID"
									value="<%=DataUtility.getStringData(dto.getLogin())%>">
							</div>
							<div class="text-danger small"><%=ServletUtility.getErrorMessage("login", request)%></div>
						</div>
						<!-- Password -->
						<div class="col-md-6 mb-2">
							<label class="form-label"><strong>Password</strong> <span
								class="text-danger">*</span></label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i
									class="fa fa-key text-muted"></i></span> <input type="password"
									class="form-control" name="password"
									placeholder="Enter Password"
									value="<%=DataUtility.getStringData(dto.getPassword())%>">
							</div>
							<div class="text-danger small"><%=ServletUtility.getErrorMessage("password", request)%></div>
						</div>
						<!-- Confirm Password -->
						<div class="col-md-6 mb-2">
							<label class="form-label"><strong>Confirm
									Password</strong> <span class="text-danger">*</span></label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i
									class="fa fa-key text-muted"></i></span> <input type="password"
									class="form-control" name="confirmPassword"
									placeholder="Enter Confirm Password"
									value="<%=DataUtility.getStringData(dto.getPassword())%>">
							</div>
							<div class="text-danger small"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></div>
						</div>
						<!-- DOB -->
						<div class="col-md-6 mb-2">
							<label class="form-label"><strong>Date of Birth</strong>
								<span class="text-danger">*</span></label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i
									class="fa fa-calendar text-muted"></i></span> <input type="text"
									id="udate" name="dob" class="form-control"
									placeholder="Enter Date Of Birth"
									value="<%=DataUtility.getDateString(dto.getDob())%>">
							</div>
							<div class="text-danger small"><%=ServletUtility.getErrorMessage("dob", request)%></div>
						</div>
						<!-- Gender -->
						<div class="col-md-6 mb-2">
							<label class="form-label"><strong>Gender</strong> <span
								class="text-danger">*</span></label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i
									class="fa fa-venus-mars text-muted"></i></span>
								<%
								HashMap<String, String> map = new HashMap<String, String>();
								map.put("Male", "Male");
								map.put("Female", "Female");
								String htmlList = HTMLUtility.getList("gender", dto.getGender(), map);
								out.print(htmlList);
								%>
							</div>
							<div class="text-danger small"><%=ServletUtility.getErrorMessage("gender", request)%></div>
						</div>
						<!-- Role -->
						<div class="col-md-6 mb-2">
							<label class="form-label"><strong>Role</strong> <span
								class="text-danger">*</span></label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i
									class="fa fa-user text-muted"></i></span>
								<%=HTMLUtility.getList("roleId", String.valueOf(dto.getRoleId()), roleList)%>
							</div>
							<div class="text-danger small"><%=ServletUtility.getErrorMessage("roleId", request)%></div>
						</div>
						<!-- Mobile No -->
						<div class="col-md-6 mb-2">
							<label class="form-label"><strong>Mobile No</strong> <span
								class="text-danger">*</span></label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i
									class="fa fa-phone text-muted"></i></span> <input type="text"
									class="form-control" maxlength="10" name="mobileNo"
									placeholder="Enter Mobile No."
									value="<%=DataUtility.getStringData(dto.getMobileNo())%>">
							</div>
							<div class="text-danger small"><%=ServletUtility.getErrorMessage("mobileNo", request)%></div>
						</div>
					</div>
					<!-- Buttons -->
					<div class="d-grid gap-2 d-sm-flex justify-content-sm-center mb-2">
						<%
						if (dto != null && id > 0) {
						%>
						<input type="submit" name="operation"
							class="btn btn-success btn-sm px-4"
							value="<%=UserCtl.OP_UPDATE%>"> &nbsp; <input
							type="submit" name="operation"
							class="btn btn-secondary btn-sm px-4"
							value="<%=UserCtl.OP_CANCEL%>">
						<%
						} else {
						%>
						<input type="submit" name="operation"
							class="btn btn-success btn-sm px-4" value="<%=UserCtl.OP_SAVE%>">
						&nbsp; <input type="submit" name="operation"
							class="btn btn-secondary btn-sm px-4"
							value="<%=UserCtl.OP_RESET%>">
						<%
						}
						%>
					</div>
				</div>
			</div>
		</form>
	</main>
	<div>
		<%@include file="FooterView.jsp"%>
	</div>
</body>
</html>