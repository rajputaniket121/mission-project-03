<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="in.co.rays.proj3.controller.GetMarksheetCtl"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Get Marksheet</title>
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

.marksheet-table {
	border: 2px solid #dee2e6;
	border-radius: 0.375rem;
	overflow: hidden;
}
</style>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css  " rel="stylesheet">
</head>
<body class="p4 d-flex flex-column">
	<div class="header">
		<%@ include file="Header.jsp"%>
	</div>

	<main class="container flex-grow-1 d-flex align-items-center justify-content-center">
		<div class="card grad-card shadow-sm w-100">
			<div class="card-body py-3">
				<h5 class="text-center text-success fw-bold mb-3">Get Marksheet</h5>

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

				<form action="<%=ORSView.GET_MARKSHEET_CTL%>" method="post">
					<input type="hidden" name="id" value="<%=DataUtility.getLong(request.getParameter("id"))%>">

					<div class="row justify-content-center mb-3">
						<div class="col-md-6">
							<div class="input-group input-group-sm">
								<span class="input-group-text"><i class="fa fa-id-card text-muted"></i></span>
								<input type="text" class="form-control" name="rollNo" placeholder="Enter Roll No." maxlength="5"
									value="<%=ServletUtility.getParameter("rollNo", request)%>">
								<button type="submit" name="operation" class="btn btn-primary btn-sm px-4"
									value="<%=GetMarksheetCtl.OP_GO%>">Go</button>
							</div>
							<div class="text-danger small mt-1">
								<%=ServletUtility.getErrorMessage("rollNo", request)%>
							</div>
						</div>
					</div>
					<jsp:useBean id="dto" class="in.co.rays.proj3.dto.MarksheetDTO" scope="request" />
					<jsp:useBean id="coursedto" class="in.co.rays.proj3.dto.CourseDTO" scope="request" />

					<%
						

						int physics = DataUtility.getInt(DataUtility.getStringData(dto.getPhysics()));
						int chemistry = DataUtility.getInt(DataUtility.getStringData(dto.getChemistry()));
						int maths = DataUtility.getInt(DataUtility.getStringData(dto.getMaths()));

						int total = physics + chemistry + maths;
						float percentage = (float) total / 3;
						percentage = Float.parseFloat(new DecimalFormat("##.##").format(percentage));

						if (dto.getRollNo() != null && dto.getRollNo().trim().length() > 0) {
					%>

					<div class="text-center mb-3">
						<h3 class="text-warning">Rays Technologies, Indore</h3>
					</div>

					<div class="row justify-content-center mb-3">
						<div class="col-md-8">
							<table class="table table-bordered table-sm">
								<tbody>
									<tr>
										<th class="text-center" style="width: 15%">Name</th>
										<td class="text-center text-capitalize" style="width: 35%"><%=DataUtility.getStringData(dto.getName())%></td>
										<th class="text-center" style="width: 15%">Roll No</th>
										<td class="text-center text-uppercase" style="width: 25%"><%=DataUtility.getStringData(dto.getRollNo())%></td>
									</tr>
									<tr>
										<th class="text-center">Status</th>
										<td class="text-center">Regular</td>
										<th class="text-center">Course</th>
										<td class="text-center text-uppercase"><%=DataUtility.getStringData(coursedto.getCourseName())%></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>

					<div class="row justify-content-center mb-3">
						<div class="col-md-8">
							<table class="table table-bordered table-sm">
								<thead class="table-light">
									<tr>
										<th class="text-center">Subject</th>
										<th class="text-center">Earned Credits</th>
										<th class="text-center">Total Credits</th>
										<th class="text-center">Grade</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="text-center">Physics</td>
										<td class="text-center">
											<%=physics%>
											<%
												if (physics < 33) {
											%><span class="text-danger">*</span><%
												}
											%>
										</td>
										<td class="text-center">100</td>
										<td class="text-center">
											<%
												if (physics > 90 && physics <= 100) { %>A+<%
												} else if (physics > 80 && physics <= 90) { %>A<%
												} else if (physics > 70 && physics <= 80) { %>B+<%
												} else if (physics > 60 && physics <= 70) { %>C+<%
												} else if (physics > 50 && physics <= 60) { %>C<%
												} else if (physics >= 33 && physics <= 50) { %>D<%
												} else if (physics >= 0 && physics < 33) { %><span class="text-danger">F</span><%
												}
											%>
										</td>
									</tr>
									<tr>
										<td class="text-center">Chemistry</td>
										<td class="text-center">
											<%=chemistry%>
											<%
												if (chemistry < 33) {
											%><span class="text-danger">*</span><%
												}
											%>
										</td>
										<td class="text-center">100</td>
										<td class="text-center">
											<%
												if (chemistry > 90 && chemistry <= 100) { %>A+<%
												} else if (chemistry > 80 && chemistry <= 90) { %>A<%
												} else if (chemistry > 70 && chemistry <= 80) { %>B+<%
												} else if (chemistry > 60 && chemistry <= 70) { %>C+<%
												} else if (chemistry > 50 && chemistry <= 60) { %>C<%
												} else if (chemistry >= 33 && chemistry <= 50) { %>D<%
												} else if (chemistry >= 0 && chemistry < 33) { %><span class="text-danger">F</span><%
												}
											%>
										</td>
									</tr>
									<tr>
										<td class="text-center">Maths</td>
										<td class="text-center">
											<%=maths%>
											<%
												if (maths < 33) {
											%><span class="text-danger">*</span><%
												}
											%>
										</td>
										<td class="text-center">100</td>
										<td class="text-center">
											<%
												if (maths > 90 && maths <= 100) { %>A+<%
												} else if (maths > 80 && maths <= 90) { %>A<%
												} else if (maths > 70 && maths <= 80) { %>B+<%
												} else if (maths > 60 && maths <= 70) { %>C+<%
												} else if (maths > 50 && maths <= 60) { %>C<%
												} else if (maths >= 33 && maths <= 50) { %>D<%
												} else if (maths >= 0 && maths < 33) { %><span class="text-danger">F</span><%
												}
											%>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>

					<div class="row justify-content-center mb-3">
						<div class="col-md-8">
							<table class="table table-bordered table-sm">
								<thead class="table-light">
									<tr>
										<th class="text-center">Total Marks</th>
										<th class="text-center">Percentage (%)</th>
										<th class="text-center">Division</th>
										<th class="text-center">Result</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<th class="text-center">
											<%=total%>
											<%
												if (total < 99 || physics < 33 || chemistry < 33 || maths < 33) {
											%><span class="text-danger">*</span><%
												}
											%>
										</th>
										<th class="text-center"><%=percentage%> %</th>
										<th class="text-center">
											<%
												if (percentage >= 60 && percentage <= 100) { %>1<sup>st</sup><%
												} else if (percentage >= 40 && percentage < 60) { %>2<sup>nd</sup><%
												} else if (percentage >= 0 && percentage < 40) { %>3<sup>rd</sup><%
												}
											%>
										</th>
										<th class="text-center">
											<%
												if (physics >= 33 && chemistry >= 33 && maths >= 33) {
											%><span class="text-success">Pass</span><%
												} else {
											%><span class="text-danger">Fail</span><%
												}
											%>
										</th>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<%
						}
					%>
				</form>
			</div>
		</div>
	</main>

	<div class="footer mt-auto py-2">
		<%@include file="FooterView.jsp"%>
	</div>

	<!-- Bootstrap JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js  "></script>
</body>
</html>