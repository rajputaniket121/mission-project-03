<%@page import="in.co.rays.proj3.controller.TimetableCtl"%>
<%@page import="in.co.rays.proj3.dto.SubjectDTO"%>
<%@page import="in.co.rays.proj3.dto.CourseDTO"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.utill.HTMLUtility"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="in.co.rays.proj3.controller.CourseCtl"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Timetable View</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

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
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css  "
	rel="stylesheet">
</head>
<body class="p4 d-flex flex-column">
	<div class="header">
		<%@ include file="Header.jsp"%>
	</div>

	<main
		class="container flex-grow-1 d-flex align-items-center justify-content-center">
		<form action="<%=ORSView.TIMETABLE_CTL%>" method="post" class="w-100"
			style="max-width: 600px;">
			<jsp:useBean id="dto" class="in.co.rays.proj3.dto.TimetableDTO"
				scope="request" />
			<%
			long id = DataUtility.getLong(request.getParameter("id"));
			%>

			<%
			List<CourseDTO> courseList = (List<CourseDTO>) request.getAttribute("courseList");
			List<SubjectDTO> subjectList = (List<SubjectDTO>) request.getAttribute("subjectList");
			%>

			<div class="card grad-card shadow-sm">
				<div class="card-body py-3">
					<h5 class="text-center text-success fw-bold mb-3">
						<%=(dto != null && id > 0) ? "Update Timetable" : "Add Timetable"%>
					</h5>

					<%
					String successMsg = ServletUtility.getSuccessMessage(request);
					if (!successMsg.equals("")) {
					%>
					<div class="alert alert-success alert-dismissible fade show"
						role="alert">
						<%=successMsg%>
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"></button>
					</div>
					<%
					}
					String errorMsg = ServletUtility.getErrorMessage(request);
					if (!errorMsg.equals("")) {
					%>
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<%=errorMsg%>
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"></button>
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

					<!-- Course -->
					<div class="mb-3">
						<label class="form-label"><strong>Course</strong> <span
							class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i
								class="fa fa-graduation-cap text-muted"></i></span>
							<%=HTMLUtility.getList("courseId", String.valueOf(dto.getCourseId()), courseList)%>
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("courseId", request)%>
						</div>
					</div>

					<!-- Subject -->
					<div class="mb-3">
						<label class="form-label"><strong>Subject</strong> <span
							class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i
								class="fa fa-book text-muted"></i></span>
							<%=HTMLUtility.getList("subjectId", String.valueOf(dto.getSubjectId()), subjectList)%>
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("subjectId", request)%>
						</div>
					</div>

					<!-- Semester -->
					<div class="mb-3">
						<label class="form-label"><strong>Semester</strong> <span
							class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"> <i
								class="fa fa-calendar text-muted"></i>
							</span>
							<%
							LinkedHashMap<String, String> semesterMap = new LinkedHashMap<String, String>();
							semesterMap.put("1", "1");
							semesterMap.put("2", "2");
							semesterMap.put("3", "3");
							semesterMap.put("4", "4");
							semesterMap.put("5", "5");
							semesterMap.put("6", "6");
							semesterMap.put("7", "7");
							semesterMap.put("8", "8");

							String htmlList = HTMLUtility.getList("semester", String.valueOf(dto.getSemester()), semesterMap);
							out.print(htmlList);
							%>
						</div>


						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("semester", request)%>
						</div>
					</div>

					<!-- Exam Date -->
					<div class="mb-3">
						<label class="form-label"><strong>Exam Date</strong> <span
							class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i
								class="fa fa-calendar text-muted"></i></span> <input type="date"
								class="form-control" id="udate" name="examDate"
								value="<%=DataUtility.getDateString(dto.getExamDate())%>">
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("examDate", request)%>
						</div>
					</div>

					<!-- Exam Time -->
					<div class="mb-3">
						<label class="form-label"><strong>Exam Time</strong> <span
							class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"> <i
								class="fa fa-clock text-muted"></i>
							</span>
							<%
							LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
							map1.put("08:00 AM to 11:00 AM", "08:00 AM to 11:00 AM");
							map1.put("12:00 PM to 03:00 PM", "12:00 PM to 03:00 PM");
							map1.put("04:00 PM to 07:00 PM", "04:00 PM to 07:00 PM");

							String htmlList1 = HTMLUtility.getList("examTime", dto.getExamTime(), map1);
							out.print(htmlList1);
							%>
						</div>

						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("examTime", request)%>
						</div>
					</div>

					<!-- Description -->
					<div class="mb-3">
						<label class="form-label"><strong>Description</strong> <span
							class="text-danger">*</span></label>
						<div class="input-group input-group-sm">
							<span class="input-group-text"><i
								class="fa fa-align-left text-muted"></i></span>
							<textarea class="form-control" name="description" rows="3"
								placeholder="Enter Short description"><%=DataUtility.getStringData(dto.getDescription()).trim()%></textarea>
						</div>
						<div class="text-danger small mt-1">
							<%=ServletUtility.getErrorMessage("description", request)%>
						</div>
					</div>

					<!-- Buttons -->
					<div class="d-grid gap-2 d-sm-flex justify-content-sm-center mb-2">
						<%
						if (dto != null && id > 0) {
						%>
						<input type="submit" name="operation"
							class="btn btn-success btn-sm px-4"
							value="<%=TimetableCtl.OP_UPDATE%>"> <input type="submit"
							name="operation" class="btn btn-secondary btn-sm px-4"
							value="<%=TimetableCtl.OP_CANCEL%>">
						<%
						} else {
						%>
						<input type="submit" name="operation"
							class="btn btn-success btn-sm px-4"
							value="<%=TimetableCtl.OP_SAVE%>"> <input type="submit"
							name="operation" class="btn btn-secondary btn-sm px-4"
							value="<%=TimetableCtl.OP_RESET%>">
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