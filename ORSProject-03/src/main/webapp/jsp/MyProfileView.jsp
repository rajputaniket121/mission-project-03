<%@page import="in.co.rays.proj3.controller.MyProfileCtl"%>
<%@page import="in.co.rays.proj3.utill.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj3.controller.BaseCtl"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.controller.ORSView"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>My Profile</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

<!-- Same background & card style as User View -->
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
	overflow: auto;
}

.grad-card {
	background: rgba(255, 255, 255, 0.92);
}
</style>
<!-- Bootstrap CSS (assumed already included via Header.jsp or globally) -->
<!-- If not, add Bootstrap 5 CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css  " rel="stylesheet">
</head>
<body class="p4 d-flex flex-column">
	<div class="header">
		<%@ include file="Header.jsp"%>
	</div>

	<main class="container flex-grow-1 d-flex align-items-center justify-content-center">
		<form action="<%=ORSView.MY_PROFILE_CTL%>" method="post" class="w-100" style="max-width: 600px;">
			<jsp:useBean id="dto" class="in.co.rays.proj3.dto.UserDTO" scope="request" />
			<% long id = DataUtility.getLong(request.getParameter("id")); %>

			<div class="card grad-card shadow-sm">
				<div class="card-body py-3">
					<h5 class="text-center text-success fw-bold mb-3">My Profile</h5>

					<%
                        String successMsg = ServletUtility.getSuccessMessage(request);
                        if (!successMsg.equals("")) {
                    %>
					<div class="alert alert-success alert-dismissible fade show" role="alert">
						<%=successMsg%>
						<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
					</div>
					<%
                        }
                        String errorMsg = ServletUtility.getErrorMessage(request);
                        if (!errorMsg.equals("")) {
                    %>
					<div class="alert alert-danger alert-dismissible fade show" role="alert">
						<%=errorMsg%>
						<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
					</div>
					<%
                        }
                    %>

					<!-- Login Id -->
					<div class="mb-3">
						<label class="form-label"><strong>Login Id</strong> <span class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i class="fa fa-envelope text-muted"></i></span>
							<input type="text" class="form-control" name="login" placeholder="Enter Email ID"
								value="<%=DataUtility.getStringData(dto.getLogin())%>">
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("login", request)%>
						</div>
					</div>

					<!-- First Name -->
					<div class="mb-3">
						<label class="form-label"><strong>First Name</strong> <span class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i class="fa fa-user text-muted"></i></span>
							<input type="text" class="form-control" name="firstName" placeholder="Enter First Name"
								value="<%=DataUtility.getStringData(dto.getFirstName())%>">
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("firstName", request)%>
						</div>
					</div>

					<!-- Last Name -->
					<div class="mb-3">
						<label class="form-label"><strong>Last Name</strong> <span class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i class="fa fa-user text-muted"></i></span>
							<input type="text" class="form-control" name="lastName" placeholder="Enter Last Name"
								value="<%=DataUtility.getStringData(dto.getLastName())%>">
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("lastName", request)%>
						</div>
					</div>

					<!-- Date of Birth -->
					<div class="mb-3">
						<label class="form-label"><strong>Date of Birth</strong> <span class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i class="fa fa-calendar text-muted"></i></span>
							<input type="date" class="form-control" name="dob" id="udate" placeholder="Select Date of Birth"
								value="<%=DataUtility.getDateString(dto.getDob())%>">
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("dob", request)%>
						</div>
					</div>

					<!-- Gender -->
					<div class="mb-3">
						<label class="form-label"><strong>Gender</strong> <span class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i class="fa fa-venus-mars text-muted"></i></span>
							<%
								HashMap<String, String> map = new HashMap<String, String>();
								map.put("Female", "Female");
								map.put("Male", "Male");

								String htmlList = HTMLUtility.getList("gender", dto.getGender(), map);
							%>
							<select class="form-select" name="gender">
								<option value="">Select Gender</option>
								<%=htmlList%>
							</select>
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("gender", request)%>
						</div>
					</div>

					<!-- Mobile No -->
					<div class="mb-3">
						<label class="form-label"><strong>Mobile No</strong> <span class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i class="fa fa-phone text-muted"></i></span>
							<input type="text" class="form-control" name="mobileNo" maxlength="10" placeholder="Enter Mobile No."
								value="<%=DataUtility.getStringData(dto.getMobileNo())%>">
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("mobileNo", request)%>
						</div>
					</div>

					<!-- Buttons -->
					<div class="d-grid gap-2 d-sm-flex justify-content-sm-center mb-2">
						<input type="submit" name="operation" class="btn btn-success btn-sm px-4"
							value="<%=BaseCtl.OP_SAVE%>">
						<input type="submit" name="operation" class="btn btn-primary btn-sm px-4"
							value="<%=MyProfileCtl.OP_CHANGE_PASSWORD%>">
					</div>

					<!-- Forget Password Link -->
					<div class="text-center mt-3">
						<a href="<%=ORSView.FORGET_PASSWORD_CTL%>" class="text-decoration-none"><b>Forget my password?</b></a>
					</div>
				</div>
			</div>
		</form>
	</main>

	<div class="footer mt-auto py-2">
		<%@include file="FooterView.jsp"%>
	</div>

	<!-- Bootstrap JS (for dismissible alerts) -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js  "></script>
</body>
</html>