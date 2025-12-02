<%@page import="in.co.rays.proj3.controller.DoctorCtl"%>
<%@page import="in.co.rays.proj3.utill.HTMLUtility"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj3.controller.ORSView"%>

<!DOCTYPE html>
<html>
<head>
<title>Doctor View</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- jQuery UI -->
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>
	$(function() {
		var today = new Date();
		var cutoff = new Date(today.getFullYear() - 18, today.getMonth(), today.getDate());

		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1950:' + cutoff.getFullYear(),
			dateFormat : 'dd/mm/yy',
			maxDate : cutoff
		});
	});
</script>

<style>
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
		<form action="<%=ORSView.DOCTOR_CTL%>" method="post" class="w-100"
			style="max-width: 600px;">

			<jsp:useBean id="dto" class="in.co.rays.proj3.dto.DoctorDTO"
				scope="request"></jsp:useBean>

			<div class="card grad-card shadow-sm">
				<div class="card-body">

					<h5 class="text-center text-success fw-bold mb-3">
						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>Update<%
							} else {
						%>Add<%
							}
						%>
						Doctor
					</h5>

					<!-- Success & Error Messages -->
					<%
						String errorMsg = ServletUtility.getErrorMessage(request);
						if (!errorMsg.equals("")) {
					%>
					<div class="alert alert-danger alert-dismissible fade show" role="alert">
						<%=errorMsg%>
						<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
					</div>
					<%
						}
						String successMsg = ServletUtility.getSuccessMessage(request);
						if (!successMsg.equals("")) {
					%>
					<div class="alert alert-success alert-dismissible fade show" role="alert">
						<%=successMsg%>
						<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
					</div>
					<%
						}
					%>

					<%
						HashMap<String, String> expertiesMap =
						(HashMap<String, String>) request.getAttribute("expertiesMap");
					%>

					<input type="hidden" name="id" value="<%=dto.getId()%>">
					<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
					<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
					<input type="hidden" name="createdDatetime"
						value="<%=DataUtility.getTimestamp(dto.getCreatedDateTime())%>">
					<input type="hidden" name="modifiedDatetime"
						value="<%=DataUtility.getTimestamp(dto.getModifiedDateTime())%>">

					<!-- Name -->
					<div class="mb-3">
						<label class="form-label"><strong>Name</strong> <span
							class="text-danger">*</span></label>
						<input type="text" name="name" class="form-control"
							placeholder="Enter Name"
							value="<%=DataUtility.getStringData(dto.getName())%>">
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("name", request)%>
						</div>
					</div>

					<!-- DOB -->
					<div class="mb-3">
						<label class="form-label"><strong>Date of Birth</strong> <span
							class="text-danger">*</span></label>
						<input type="text" id="datepicker" name="dob"
							class="form-control" placeholder="Select your date of birth"
							readonly value="<%=DataUtility.getDateString(dto.getDob())%>">
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("dob", request)%>
						</div>
					</div>

					<!-- Mobile -->
					<div class="mb-3">
						<label class="form-label"><strong>Mobile No</strong> <span
							class="text-danger">*</span></label>
						<input type="text" name="mobile" maxlength="10"
							class="form-control" placeholder="Enter Mobile No."
							value="<%=DataUtility.getStringData(dto.getMobile())%>">
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("mobile", request)%>
						</div>
					</div>

					<!-- Experties -->
					<div class="mb-3">
						<label class="form-label"><strong>Experties</strong> <span
							class="text-danger">*</span></label>
						<%=HTMLUtility.getList("experties", dto.getExperties(), expertiesMap)%>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("experties", request)%>
						</div>
					</div>

					<!-- Buttons -->
					<div class="d-grid gap-2 d-sm-flex justify-content-sm-center mt-3">
						<%
						if (dto.getId() != null && dto.getId() > 0) {
						%>
						<input type="submit" name="operation"
							class="btn btn-success btn-sm px-4"
							value="<%=DoctorCtl.OP_UPDATE%>">
						<input type="submit" name="operation"
							class="btn btn-secondary btn-sm px-4"
							value="<%=DoctorCtl.OP_CANCEL%>">
						<%
							} else {
						%>
						<input type="submit" name="operation"
							class="btn btn-success btn-sm px-4"
							value="<%=DoctorCtl.OP_SAVE%>">
						<input type="submit" name="operation"
							class="btn btn-secondary btn-sm px-4"
							value="<%=DoctorCtl.OP_RESET%>">
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

	<!-- Bootstrap JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
