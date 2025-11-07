<%@page import="in.co.rays.proj3.controller.UserRegistrationCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj3.utill.HTMLUtility"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>User Registration</title>

<!-- Bootstrap 5 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>

<!-- Font Awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />

<!-- jQuery UI -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>
	function phoneno() {
		$('#mobileNo').keypress(function(e) {
			if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
				e.preventDefault();
			}
		});
	}
	$(function() {
		$("#datepicker").datepicker({
			changeMonth: true,
			changeYear: true,
			yearRange: '1950:2025',
			dateFormat: 'dd/mm/yy'
		});
		phoneno();
	});
</script>

<style type="text/css">
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/Linkme.jpg');
	background-size: cover;
	background-position: center;
	background-attachment: fixed;
	min-height: 100vh;
	padding-top: 50px; 
	padding-bottom: 20px;
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

	<main class="container flex-grow-1 d-flex align-items-center justify-content-center">
		<form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post" class="w-100" style="max-width: 800px;">
			<div class="card grad-card shadow-sm">
				<div class="card-body py-3">

					<h5 class="text-center text-success fw-bold mb-3">User Registration</h5>

					<div class="row mb-2">
						<div class="col-md-12">
							<jsp:useBean id="dto" class="in.co.rays.proj3.dto.UserDTO" scope="request" />

							<%
								if (!ServletUtility.getSuccessMessage(request).equals("")) {
							%>
							<div class="alert alert-success alert-dismissible fade show p-2 mb-2" role="alert">
								<%=ServletUtility.getSuccessMessage(request)%>
								<button type="button" class="btn-close p-1" data-bs-dismiss="alert" aria-label="Close"></button>
							</div>
							<%
								}
							%>

							<%
								if (!ServletUtility.getErrorMessage(request).equals("")) {
							%>
							<div class="alert alert-danger alert-dismissible fade show p-2 mb-2" role="alert">
								<%=ServletUtility.getErrorMessage(request)%>
								<button type="button" class="btn-close p-1" data-bs-dismiss="alert" aria-label="Close"></button>
							</div>
							<%
								}
							%>

							<input type="hidden" name="id" value="<%=dto.getId()%>">
							<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
							<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
							<input type="hidden" name="createdDateTime"
								value="<%=DataUtility.getTimestamp(dto.getCreatedDateTime())%>">
							<input type="hidden" name="modifiedDateTime"
								value="<%=DataUtility.getTimestamp(dto.getModifiedDateTime())%>">
						</div>
					</div>

					<div class="row g-2">
						<!-- First Name -->
						<div class="col-md-6 mb-2">
							<label class="form-label">
								<strong>First Name</strong> <span class="text-danger">*</span>
							</label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i class="fa fa-user-alt text-muted"></i></span>
								<input type="text" class="form-control" name="firstName"
									value="<%=DataUtility.getStringData(dto.getFirstName())%>">
							</div>
							<div class="text-danger small"><%=ServletUtility.getErrorMessage("firstName", request)%></div>
						</div>

						<!-- Last Name -->
						<div class="col-md-6 mb-2">
							<label class="form-label">
								<strong>Last Name</strong> <span class="text-danger">*</span>
							</label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i class="fa fa-user-circle text-muted"></i></span>
								<input type="text" class="form-control" name="lastName"
									value="<%=DataUtility.getStringData(dto.getLastName())%>">
							</div>
							<div class="text-danger small"><%=ServletUtility.getErrorMessage("lastName", request)%></div>
						</div>

						<!-- Password -->
						<div class="col-md-6 mb-2">
							<label class="form-label">
								<strong>Password</strong> <span class="text-danger">*</span>
							</label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i class="fa fa-key text-muted"></i></span>
								<input type="password" class="form-control" name="password"
									value="<%=DataUtility.getStringData(dto.getPassword())%>">
							</div>
							<div class="text-danger small"><%=ServletUtility.getErrorMessage("password", request)%></div>
						</div>

						<!-- Confirm Password -->
						<div class="col-md-6 mb-2">
							<label class="form-label">
								<strong>Confirm Password</strong> <span class="text-danger">*</span>
							</label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i class="fa fa-key text-muted"></i></span>
								<input type="password" class="form-control" name="confirmPassword"
									value="<%=DataUtility.getStringData(dto.getConfirmPassword())%>">
							</div>
							<div class="text-danger small"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></div>
						</div>

						<!-- Email -->
						<div class="col-md-6 mb-2">
							<label class="form-label">
								<strong>Email Id</strong> <span class="text-danger">*</span>
							</label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i class="fa fa-envelope text-muted"></i></span>
								<input type="text" class="form-control" name="emailId"
									value="<%=DataUtility.getStringData(dto.getLogin())%>">
							</div>
							<div class="text-danger small"><%=ServletUtility.getErrorMessage("emailId", request)%></div>
						</div>

						<!-- Mobile No -->
						<div class="col-md-6 mb-2">
							<label class="form-label">
								<strong>Mobile No</strong> <span class="text-danger">*</span>
							</label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i class="fa fa-phone-square text-muted"></i></span>
								<input type="text" class="form-control" id="mobileNo" name="mobileNo"
									maxlength="10" value="<%=DataUtility.getStringData(dto.getMobileNo())%>">
							</div>
							<div class="text-danger small"><%=ServletUtility.getErrorMessage("mobileNo", request)%></div>
						</div>

						<!-- Gender -->
						<div class="col-md-6 mb-2">
							<label class="form-label">
								<strong>Gender</strong> <span class="text-danger">*</span>
							</label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i class="fa fa-venus-mars text-muted"></i></span>
								<%
									HashMap map = new HashMap();
									map.put("Male", "Male");
									map.put("Female", "Female");
									String htmlList = HTMLUtility.getList("gender", dto.getGender(), map);
									out.print(htmlList);
								%>
							</div>
							<div class="text-danger small"><%=ServletUtility.getErrorMessage("gender", request)%></div>
						</div>

						<!-- DOB -->
						<div class="col-md-6 mb-3">
							<label class="form-label">
								<strong>DOB</strong> <span class="text-danger">*</span>
							</label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i class="fa fa-calendar text-muted"></i></span>
								<input type="text" id="datepicker" name="dob"
									class="form-control" readonly
									value="<%=DataUtility.getDateString(dto.getDob())%>">
							</div>
							<div class="text-danger small"><%=ServletUtility.getErrorMessage("dob", request)%></div>
						</div>
					</div>

					<!-- Buttons -->
					<div class="d-grid gap-2 d-sm-flex justify-content-sm-center mb-2">
						<input type="submit" name="operation"
							class="btn btn-success btn-sm px-4"
							value="<%=UserRegistrationCtl.OP_SIGN_UP%>">
						<input type="submit" name="operation"
							class="btn btn-secondary btn-sm px-4"
							value="<%=UserRegistrationCtl.OP_RESET%>">
					</div>

				</div>
			</div>
		</form>
	</main>

	<div class="footer mt-auto py-2">
		<%@include file="FooterView.jsp"%>
	</div>
</body>
</html>