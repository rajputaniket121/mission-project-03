```jsp
<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.controller.MarksheetMeritListCtl"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@page import="in.co.rays.proj3.dto.MarksheetDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Marksheet Merit List</title>
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
				<h5 class="text-center text-success fw-bold mb-3">Marksheet Merit List</h5>

				<%
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

				<form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>" method="POST">
					<%
						int pageNo = ServletUtility.getPageNo(request);
						int pageSize = ServletUtility.getPageSize(request);
						int index = ((pageNo - 1) * pageSize) + 1;

						List<MarksheetDTO> list = (List<MarksheetDTO>) ServletUtility.getList(request);
						Iterator<MarksheetDTO> it = list.iterator();

						if (list.size() != 0) {
					%>
					<div class="table-responsive">
						<table class="table table-bordered table-striped table-hover">
							<thead class="table-light">
								<tr>
									<th scope="col" class="text-center">S.No</th>
									<th scope="col" class="text-center">Roll No</th>
									<th scope="col" class="text-center">Name</th>
									<th scope="col" class="text-center">Physics</th>
									<th scope="col" class="text-center">Chemistry</th>
									<th scope="col" class="text-center">Maths</th>
									<th scope="col" class="text-center">Total</th>
									<th scope="col" class="text-center">Percentage (%)</th>
								</tr>
							</thead>
							<%
								while (it.hasNext()) {
									MarksheetDTO bean = it.next();

									int physics = bean.getPhysics();
									int chemistry = bean.getChemistry();
									int maths = bean.getMaths();
									int total = physics + chemistry + maths;
									float percentage = (float) total / 3;
									percentage = Float.parseFloat(new DecimalFormat("##.##").format(percentage));
							%>
							<tbody>
								<tr>
									<td class="text-center"><%=index++%></td>
									<td class="text-center text-uppercase"><%=bean.getRollNo()%></td>
									<td class="text-capitalize text-center"><%=bean.getName()%></td>
									<td class="text-center"><%=bean.getPhysics()%></td>
									<td class="text-center"><%=bean.getChemistry()%></td>
									<td class="text-center"><%=bean.getMaths()%></td>
									<td class="text-center"><%=total%></td>
									<td class="text-center"><%=percentage%> %</td>
								</tr>
							</tbody>
							<%
								}
							%>
						</table>
					</div>
					<%
						} else {
					%>
					<div class="text-center">
						<h6>No records found</h6>
					</div>
					<%
						}
					%>
					<div class="d-flex justify-content-end mt-3">
						<input type="submit" name="operation" class="btn btn-primary btn-sm px-4"
							value="<%=MarksheetMeritListCtl.OP_BACK%>">
					</div>
					<input type="hidden" name="pageNo" value="<%=pageNo%>">
					<input type="hidden" name="pageSize" value="<%=pageSize%>">
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
```