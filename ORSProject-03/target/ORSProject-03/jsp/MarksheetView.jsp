<%@page import="in.co.rays.proj3.dto.StudentDTO"%>
<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.controller.MarksheetCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj3.utill.HTMLUtility"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Marksheet View</title>
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
		<form action="<%=ORSView.MARKSHEET_CTL%>" method="post" class="w-100" style="max-width: 600px;">
			<jsp:useBean id="dto" class="in.co.rays.proj3.dto.MarksheetDTO" scope="request" />
			<% long id = DataUtility.getLong(request.getParameter("id")); %>

			<%
				List<StudentDTO> studentList = (List<StudentDTO>) request.getAttribute("studentList");
			%>

			<div class="card grad-card shadow-sm">
				<div class="card-body py-3">
					<h5 class="text-center text-success fw-bold mb-3">
						<% if (dto != null && id > 0) { %>
						Update Marksheet
						<% } else { %>
						Add Marksheet
						<% } %>
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

					<!-- Roll No -->
					<div class="mb-3">
						<label class="form-label"><strong>Roll No</strong> <span class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i class="fa fa-id-card text-muted"></i></span>
							<input type="text" class="form-control" name="rollNo" maxlength="5" placeholder="Enter Roll No."
								value="<%=DataUtility.getStringData(dto.getRollNo())%>"
								<%= (id > 0) ? "readonly" : "" %>>
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("rollNo", request)%>
						</div>
					</div>

					<!-- Name -->
					<div class="mb-3">
						<label class="form-label"><strong>Name</strong> <span class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i class="fa fa-user text-muted"></i></span>
							<%=HTMLUtility.getList("studentId", String.valueOf(dto.getStudentId()), studentList)%>
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("studentId", request)%>
						</div>
					</div>

					<!-- Physics -->
					<div class="mb-3">
						<label class="form-label"><strong>Physics</strong> <span class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i class="fa fa-book text-muted"></i></span>
							<input type="text" class="form-control" name="physics" maxlength="3" placeholder="Enter Physics Marks"
								value="<%=DataUtility.getStringData(dto.getPhysics())%>">
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("physics", request)%>
						</div>
					</div>

					<!-- Chemistry -->
					<div class="mb-3">
						<label class="form-label"><strong>Chemistry</strong> <span class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i class="fa fa-flask text-muted"></i></span>
							<input type="text" class="form-control" name="chemistry" maxlength="3" placeholder="Enter Chemistry Marks"
								value="<%=DataUtility.getStringData(dto.getChemistry())%>">
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("chemistry", request)%>
						</div>
					</div>

					<!-- Maths -->
					<div class="mb-3">
						<label class="form-label"><strong>Maths</strong> <span class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i class="fa fa-calculator text-muted"></i></span>
							<input type="text" class="form-control" name="maths" maxlength="3" placeholder="Enter Maths Marks"
								value="<%=DataUtility.getStringData(dto.getMaths())%>">
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("maths", request)%>
						</div>
					</div>

					<!-- Buttons -->
					<div class="d-grid gap-2 d-sm-flex justify-content-sm-center mb-2">
						<%
							if (dto != null && id > 0) {
						%>
						<input type="submit" name="operation" class="btn btn-success btn-sm px-4"
							value="<%=MarksheetCtl.OP_UPDATE%>">
						<input type="submit" name="operation" class="btn btn-secondary btn-sm px-4"
							value="<%=MarksheetCtl.OP_CANCEL%>">
						<%
							} else {
						%>
						<input type="submit" name="operation" class="btn btn-success btn-sm px-4"
							value="<%=MarksheetCtl.OP_SAVE%>">
						<input type="submit" name="operation" class="btn btn-secondary btn-sm px-4"
							value="<%=MarksheetCtl.OP_RESET%>">
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
