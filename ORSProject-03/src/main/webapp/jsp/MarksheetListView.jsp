<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="java.util.Collections"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="in.co.rays.proj3.utill.DataUtility"%>
<%@page import="in.co.rays.proj3.controller.MarksheetListCtl"%>
<%@page import="in.co.rays.proj3.utill.ServletUtility"%>
<%@page import="in.co.rays.proj3.dto.MarksheetDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
<title>Marksheet List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
<!-- Bootstrap 4 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<style>
.footer {
	left: 0 !important;
	right: 0 !important;
	margin-left: 0 !important;
	padding-left: 0 !important;
}
</style>

</head>
<body class="p-4"
	style="background-image: url('<%=ORSView.APP_CONTEXT%>/img/Linkme.jpg'); 
           background-size: cover; 
           background-position: center; 
           background-attachment: fixed; 
           min-height: 100vh;">
	<%@include file="Header.jsp"%>
	<div class="container-fluid p-4">
		<h2 class="text-center text-light font-weight-bold mt-4">Marksheet
			List</h2>

		<%
            if (!ServletUtility.getErrorMessage(request).equals("")) {
        %>
		<div class="alert alert-danger"><%=ServletUtility.getErrorMessage(request)%></div>
		<%
        }
        if (!ServletUtility.getSuccessMessage(request).equals("")) {
        %>
		<div class="alert alert-success"><%=ServletUtility.getSuccessMessage(request)%></div>
		<%
        }
        %>

		<form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="POST">
			<%
                int pageNo = ServletUtility.getPageNo(request);
                int pageSize = ServletUtility.getPageSize(request);
                int index = ((pageNo - 1) * pageSize) + 1;
                int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

                List<MarksheetDTO> list = (List<MarksheetDTO>) ServletUtility.getList(request);
                Iterator<MarksheetDTO> it = list.iterator();

                if (list.size() != 0) {
            %>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<!-- Floating-style search form -->
			<div class="table-responsive">
				<table
					class="table table-borderless w-100 text-center bg-transparent">
					<tr>
						<td>
							<div
								class="d-flex justify-content-center align-items-center flex-wrap bg-light bg-opacity-75 p-3 rounded shadow-sm">
								<div class="mx-2">
									<label><b>Name :</b></label>
								</div>
								<div class="mx-2">
									<input type="text" class="form-control form-control-sm"
										name="name" placeholder="Enter Student Name"
										value="<%=ServletUtility.getParameter("name", request)%>"
										style="width: 150px;">
								</div>
								<div class="mx-2">
									<label><b>Roll No :</b></label>
								</div>
								<div class="mx-2">
									<input type="text" class="form-control form-control-sm"
										name="rollNo" placeholder="Enter Roll No."
										value="<%=ServletUtility.getParameter("rollNo", request)%>"
										style="width: 120px;">
								</div>
								<div class="mx-2">
									<input type="submit" class="btn btn-sm btn-primary"
										name="operation" value="<%=MarksheetListCtl.OP_SEARCH%>">
									<input type="submit"
										class="btn btn-sm btn-outline-secondary ml-1" name="operation"
										value="<%=MarksheetListCtl.OP_RESET%>">
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<br>

			<!-- Data Table -->
			<div class="table-responsive">
				<table
					class="table table-bordered table-hover w-100 text-center bg-white shadow-sm">
					<thead class="thead-light">
						<tr>
							<th><input type="checkbox" id="selectall" /> Select All</th>
							<th>S.No</th>
							<th>Roll No</th>
							<th>Name</th>
							<th>Physics</th>
							<th>Chemistry</th>
							<th>Math's</th>
							<th>Total</th>
							<th>Percentage (%)</th>
							<th>Edit</th>
						</tr>
					</thead>

					<tbody>
						<%
                            while (it.hasNext()) {
                                MarksheetDTO dto = it.next();
                                int physics = dto.getPhysics();
                                int chemistry = dto.getChemistry();
                                int maths = dto.getMaths();
                                int total = physics + chemistry + maths;
                                float percentage = (float) total / 3;
                                percentage = Float.parseFloat(new DecimalFormat("##.##").format(percentage));
                        %>
						<tr>
							<td><input type="checkbox" class="case" name="ids"
								value="<%=dto.getId()%>"></td>
							<td><%=index++%></td>
							<td class="text-uppercase"><%=dto.getRollNo()%></td>
							<td class="text-capitalize"><%=dto.getName()%></td>
							<td><%=dto.getPhysics()%></td>
							<td><%=dto.getChemistry()%></td>
							<td><%=dto.getMaths()%></td>
							<td><%=total%></td>
							<td><%=percentage%> %</td>
							<td><a href="MarksheetCtl?id=<%=dto.getId()%>"
								class="btn btn-link btn-sm p-0">Edit</a></td>
						</tr>
						<%
                            }
                        %>
					</tbody>
				</table>
			</div>

			<table class="table w-100">
				<tr>
					<td width="25%"><input type="submit"
						class="btn btn-outline-primary" name="operation"
						value="<%=MarksheetListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td width="25%" class="text-center"><input type="submit"
						class="btn btn-outline-success" name="operation"
						value="<%=MarksheetListCtl.OP_NEW%>"></td>
					<td width="25%" class="text-center"><input type="submit"
						class="btn btn-outline-danger" name="operation"
						value="<%=MarksheetListCtl.OP_DELETE%>"></td>
					<td width="25%" class="text-right"><input type="submit"
						class="btn btn-outline-primary" name="operation"
						value="<%=MarksheetListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
                }
                if (list.size() == 0) {
            %>
			<table class="table w-100">
				<tr>
					<td class="text-right"><input type="submit"
						class="btn btn-warning btn-sm font-weight-bold" name="operation"
						value="<%=MarksheetListCtl.OP_BACK%>"></td>
				</tr>
			</table>
			<%
                }
            %>
		</form>
	</div>
	<div class="footer">
		<%@include file="FooterView.jsp"%>
	</div>
</body>
</html>