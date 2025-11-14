<!DOCTYPE html>
<%@page import="in.co.rays.proj3.controller.CollegeCtl"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>College View</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

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
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Font Awesome (for icons) -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="p4 d-flex flex-column">
	<div class="header">
		<%@ include file="Header.jsp" %>
	</div>

	<main class="container flex-grow-1 d-flex align-items-center justify-content-center">
		<form action="<%=ORSView.COLLEGE_CTL%>" method="POST" class="w-100" style="max-width: 800px;">
			<jsp:useBean id="dto" class="in.co.rays.proj3.dto.CollegeDTO" scope="request" />
				<% long id = DataUtility.getLong(request.getParameter("id")); %>
			<div class="card grad-card shadow-sm">
				<div class="card-body py-3">
					<h5 class="text-center text-success fw-bold mb-3">
						<%
							if (dto != null && id > 0) {
						%>Update<%
							} else {
						%>Add<%
							}
						%> College
					</h5>

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

					<!-- College Name -->
					<div class="row">
						<div class="col-md-6 mb-2">
							<label class="form-label"><strong>Name</strong> <span class="text-danger">*</span></label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i class="fa fa-university text-muted"></i></span>
								<input type="text" class="form-control" name="name" placeholder="Enter College Name"
									value="<%=DataUtility.getStringData(dto.getName())%>">
							</div>
							<div class="text-danger small mt-1">
								<%=ServletUtility.getErrorMessage("name", request)%>
							</div>
						</div>

						<!-- Address -->
						<div class="col-md-6 mb-2">
							<label class="form-label"><strong>Address</strong> <span class="text-danger">*</span></label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i class="fa fa-map-marker-alt text-muted"></i></span>
								<input type="text" class="form-control" name="address" placeholder="Enter Address"
									value="<%=DataUtility.getStringData(dto.getAddress())%>">
							</div>
							<div class="text-danger small mt-1">
								<%=ServletUtility.getErrorMessage("address", request)%>
							</div>
						</div>

						<!-- State -->
						<div class="col-md-6 mb-2">
							<label class="form-label"><strong>State</strong> <span class="text-danger">*</span></label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i class="fa fa-flag text-muted"></i></span>
								<input type="text" class="form-control" name="state" placeholder="Enter State"
									value="<%=DataUtility.getStringData(dto.getState())%>">
							</div>
							<div class="text-danger small mt-1">
								<%=ServletUtility.getErrorMessage("state", request)%>
							</div>
						</div>

						<!-- City -->
						<div class="col-md-6 mb-2">
							<label class="form-label"><strong>City</strong> <span class="text-danger">*</span></label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i class="fa fa-city text-muted"></i></span>
								<input type="text" class="form-control" name="city" placeholder="Enter City"
									value="<%=DataUtility.getStringData(dto.getCity())%>">
							</div>
							<div class="text-danger small mt-1">
								<%=ServletUtility.getErrorMessage("city", request)%>
							</div>
						</div>

						<!-- Phone No -->
						<div class="col-md-6 mb-2">
							<label class="form-label"><strong>Phone No</strong> <span class="text-danger">*</span></label>
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i class="fa fa-phone text-muted"></i></span>
								<input type="text" class="form-control" maxlength="10" name="phoneNo"
									placeholder="Enter Phone No." value="<%=DataUtility.getStringData(dto.getPhoneNo())%>">
							</div>
							<div class="text-danger small mt-1">
								<%=ServletUtility.getErrorMessage("phoneNo", request)%>
							</div>
						</div>
					</div>

					<!-- Buttons -->
					<div class="d-grid gap-2 d-sm-flex justify-content-sm-center mb-2">
						<%
							if (dto != null && id > 0) {
						%>
						<input type="submit" name="operation" class="btn btn-success btn-sm px-4"
							value="<%=CollegeCtl.OP_UPDATE%>">
						<input type="submit" name="operation" class="btn btn-secondary btn-sm px-4"
							value="<%=CollegeCtl.OP_CANCEL%>">
						<%
							} else {
						%>
						<input type="submit" name="operation" class="btn btn-success btn-sm px-4"
							value="<%=CollegeCtl.OP_SAVE%>">
						<input type="submit" name="operation" class="btn btn-secondary btn-sm px-4"
							value="<%=CollegeCtl.OP_RESET%>">
						<%
							}
						%>
					</div>
				</div>
			</div>
		</form>
	</main>

	<div class="footer mt-auto py-2">
		<%@include file="FooterView.jsp"%>
	</div>

	<!-- Bootstrap JS (for alerts, etc.) -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>