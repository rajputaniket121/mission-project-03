<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.controller.ChangePasswordCtl"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Change Password</title>
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
		<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post" class="w-100" style="max-width: 600px;">
			<jsp:useBean id="dto" class="in.co.rays.proj3.dto.UserDTO" scope="request" />
			<% long id = DataUtility.getLong(request.getParameter("id")); %>

			<div class="card grad-card shadow-sm">
				<div class="card-body py-3">
					<h5 class="text-center text-success fw-bold mb-3">Change Password</h5>

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

					<input type="hidden" name="id" value="<%=dto.getId()%>">
					<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
					<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
					<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDateTime())%>">
					<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDateTime())%>">

					<!-- Old Password -->
					<div class="mb-3">
						<label class="form-label"><strong>Old Password</strong> <span class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i class="fa fa-lock text-muted"></i></span>
							<input type="password" class="form-control" name="oldPassword" placeholder="Enter Old Password"
								value="<%=DataUtility.getString(request.getParameter("oldPassword") == null ? "" : DataUtility.getString(request.getParameter("oldPassword")))%>">
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("oldPassword", request)%>
						</div>
					</div>

					<!-- New Password -->
					<div class="mb-3">
						<label class="form-label"><strong>New Password</strong> <span class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i class="fa fa-lock text-muted"></i></span>
							<input type="password" class="form-control" name="newPassword" placeholder="Enter New Password"
								value="<%=DataUtility.getString(request.getParameter("newPassword") == null ? "" : DataUtility.getString(request.getParameter("newPassword")))%>">
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("newPassword", request)%>
						</div>
					</div>

					<!-- Confirm Password -->
					<div class="mb-3">
						<label class="form-label"><strong>Confirm Password</strong> <span class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i class="fa fa-lock text-muted"></i></span>
							<input type="password" class="form-control" name="confirmPassword" placeholder="Enter Confirm Password"
								value="<%=DataUtility.getString(request.getParameter("confirmPassword") == null ? "" : DataUtility.getString(request.getParameter("confirmPassword")))%>">
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("confirmPassword", request)%>
						</div>
					</div>

					<!-- Buttons -->
					<div class="d-grid gap-2 d-sm-flex justify-content-sm-center mb-2">
						<input type="submit" name="operation" class="btn btn-success btn-sm px-4"
							value="<%=ChangePasswordCtl.OP_SAVE%>">
						<input type="submit" name="operation" class="btn btn-secondary btn-sm px-4"
							value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE%>">
					</div>
				</div>
			</div>
		</form>
	</main>

	<div>
		<%@include file="FooterView.jsp"%>
	</div>

	<!-- Bootstrap JS (for dismissible alerts) -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js  "></script>
</body>
</html>