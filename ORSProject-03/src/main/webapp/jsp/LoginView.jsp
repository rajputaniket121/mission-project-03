<%@page import="in.co.rays.proj3.controller.UserRegistrationCtl"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Login view</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

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

<style type="text/css">
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/Linkme.jpg');
	background-size: cover;
	background-position: center;
	background-attachment: fixed;
	min-height: 100vh;
	padding-top: 30px;
	padding-bottom: 20px;
}
.grad-card {
	background: rgba(255, 255, 255, 0.92);
}
</style>

</head>
<body class="p4 d-flex flex-column">
	<div>
		<%@include file="Header.jsp"%>
	</div>

	<main class="container flex-grow-1 d-flex align-items-center justify-content-center">
		<form action="<%=ORSView.LOGIN_CTL%>" method="post" class="w-100" style="max-width: 2000px;">
			<div class="row mt-5">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<div class="card grad shadow-sm">
						<div class="card-body">

							<h3 class="text-center text-dark fw-bold">Login</h3>

							<div>
								<jsp:useBean id="dto" class="in.co.rays.proj3.dto.UserDTO" scope="request" />

								<%
									if (!ServletUtility.getSuccessMessage(request).equals("")) {
								%>
								<div class="alert alert-success alert-dismissible fade show" role="alert">
									<%=ServletUtility.getSuccessMessage(request)%>
									<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
								</div>
								<%
									}
								%>

								<%
									if (!ServletUtility.getErrorMessage(request).equals("")) {
								%>
								<div class="alert alert-danger alert-dismissible fade show" role="alert">
									<%=ServletUtility.getErrorMessage(request)%>
									<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
								</div>
								<%
									}
								%>

								<%
									String uri = (String) request.getAttribute("uri");
								%>

								<input type="hidden" name="id" value="<%=dto.getId()%>">
								<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
								<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
								<input type="hidden" name="createdDateTime"
									value="<%=DataUtility.getTimestamp(dto.getCreatedDateTime())%>">
								<input type="hidden" name="modifiedDateTime"
									value="<%=DataUtility.getTimestamp(dto.getModifiedDateTime())%>">
							</div>

							<!-- Email Field -->
							<div class="mt-3">
								<label class="form-label">
									<strong>Email Id</strong> <span class="text-danger">*</span>
								</label>
								<div class="input-group">
									<span class="input-group-text">
										<i class="fa fa-envelope text-muted"></i>
									</span>
									<input type="text" class="form-control"
										name="login" placeholder="Enter email"
										value="<%=DataUtility.getStringData(dto.getLogin())%>">
								</div>
								<div class="text-danger mt-1">
									<%=ServletUtility.getErrorMessage("login", request)%>
								</div>
							</div>

							<!-- Password Field -->
							<div class="mt-3">
								<label class="form-label">
									<strong>Password</strong> <span class="text-danger">*</span>
								</label>
								<div class="input-group">
									<span class="input-group-text">
										<i class="fa fa-lock text-muted"></i>
									</span>
									<input type="password" class="form-control"
										name="password" placeholder="Enter password"
										value="<%=DataUtility.getStringData(dto.getPassword())%>">
								</div>
								<div class="text-danger mt-1">
									<%=ServletUtility.getErrorMessage("password", request)%>
								</div>
							</div>

							<!-- Buttons -->
							<div class="text-center mt-4">
								<input type="submit" name="operation"
									class="btn btn-success btn-md me-2"
									value="<%=LoginCtl.OP_SIGN_IN%>">
								<input type="submit" name="operation"
									class="btn btn-primary btn-md"
									value="<%=UserRegistrationCtl.OP_SIGN_UP%>">
							</div>

							<!-- Forgot Password Link -->
							<div class="text-center mt-3">
								<a href="<%=ORSView.FORGET_PASSWORD_CTL%>"
									class="btn btn-outline-secondary btn-sm"> <i
									class="fa fa-key me-1"></i>Forgot Password?
								</a>
							</div>

							<input type="hidden" name="uri" value="<%=uri%>">
						</div>
					</div>
				</div>
				<div class="col-md-4"></div>
			</div>
		</form>
	</main>

	<%@include file="FooterView.jsp"%>
</body>
</html>