<%@page import="in.co.rays.proj3.controller.LoginCtl"%>
<%@page import="in.co.rays.proj3.dto.RoleDTO"%>
<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Header</title>
<!-- Bootstrap 4 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
<script>
	$(function() {
		$("#selectall").click(function() {
			$('.case').prop('checked', this.checked);
		});
		$(".case").click(
				function() {
					$("#selectall").prop("checked",
							$(".case").length === $(".case:checked").length);
				});
	});
</script>
<style type="text/css">
/* Keep your custom styling for dropdown toggle text and caret */
.nav-link.dropdown-toggle {
	color: black !important;
}

.nav-link.dropdown-toggle::after {
	border-top-color: #007bff !important;
}

/* More dropdown styling */
.more-dropdown .nav-link {
	min-width: 80px;
}
</style>
</head>
<body>
	<%
	UserDTO userDto = (UserDTO) session.getAttribute("user");
	boolean userLoggedIn = userDto != null;
	String role = userLoggedIn ? (String) session.getAttribute("role") : "";
	String welcomeMsg = "Hi, " + (userLoggedIn ? userDto.getFirstName() + " (" + role + ")" : "Guest");
	%>
	<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">

		<a class="navbar-brand" href="<%=ORSView.WELCOME_CTL%>"> <img
			src="<%=ORSView.APP_CONTEXT%>/img/custom.png" alt="Logo" height="42">
		</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav ml-auto">
				<%
				if (userLoggedIn) {
				%>
				<!-- ===== STUDENT MENUS ===== -->
				<%
				if (userDto.getRoleId() == RoleDTO.STUDENT) {
				%>
				<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Marksheet</a>
					<div class="dropdown-menu">
						<a class="dropdown-item"
							href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"> <i
							class="fa fa-award mr-2"></i>Marksheet Merit List
						</a>
					</div></li>
				<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">User</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.MY_PROFILE_CTL%>"><i
							class="fa fa-user mr-2"></i>My Profile</a> <a class="dropdown-item"
							href="<%=ORSView.CHANGE_PASSWORD_CTL%>"><i
							class="fa fa-key mr-2"></i>Change Password</a>
					</div></li>
				<!-- ===== ADMIN MENUS ===== -->
				<%
				} else if (userDto.getRoleId() == RoleDTO.ADMIN) {
				%>
				<!-- 1. User (Visible) -->
				<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">User</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.USER_CTL%>"><i
							class="fa fa-user-plus mr-2"></i>Add User</a> <a
							class="dropdown-item" href="<%=ORSView.USER_LIST_CTL%>"><i
							class="fa fa-users mr-2"></i>User List</a>
					</div></li>
				<!-- 2. Marksheet (Visible) -->
				<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Marksheet</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.MARKSHEET_CTL%>"><i
							class="fa fa-file mr-2"></i>Add Marksheet</a> <a
							class="dropdown-item" href="<%=ORSView.MARKSHEET_LIST_CTL%>"><i
							class="fa fa-paste mr-2"></i>Marksheet List</a> <a
							class="dropdown-item"
							href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><i
							class="fa fa-award mr-2"></i>Merit List</a> <a class="dropdown-item"
							href="<%=ORSView.GET_MARKSHEET_CTL%>"><i
							class="fa fa-copy mr-2"></i>Get Marksheet</a>
					</div></li>
				<!-- 3. Role (Visible) -->
				<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Role</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.ROLE_CTL%>"><i
							class="fa fa-user-tag mr-2"></i>Add Role</a> <a class="dropdown-item"
							href="<%=ORSView.ROLE_LIST_CTL%>"><i class="fa fa-list mr-2"></i>Role
							List</a>
					</div></li>
				<!-- 4. College (Visible) -->
				<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">College</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.COLLEGE_CTL%>"><i
							class="fa fa-university mr-2"></i>Add College</a> <a
							class="dropdown-item" href="<%=ORSView.COLLEGE_LIST_CTL%>"><i
							class="fa fa-building mr-2"></i>College List</a>
					</div></li>
				<!-- 5. Course (Visible) -->
				<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Course</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.COURSE_CTL%>"><i
							class="fa fa-book mr-2"></i>Add Course</a> <a class="dropdown-item"
							href="<%=ORSView.COURSE_LIST_CTL%>"><i
							class="fa fa-list mr-2"></i>Course List</a>
					</div></li>
				<!-- 6. Student (Visible) -->
				<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Student</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.STUDENT_CTL%>"><i
							class="fa fa-user-graduate mr-2"></i>Add Student</a> <a
							class="dropdown-item" href="<%=ORSView.STUDENT_LIST_CTL%>"><i
							class="fa fa-users mr-2"></i>Student List</a>
					</div></li>
				<!-- 7. Faculty (Visible) -->
				<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Faculty</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.FACULTY_CTL%>"><i
							class="fa fa-user-plus mr-2"></i>Add Faculty</a> <a
							class="dropdown-item" href="<%=ORSView.FACULTY_LIST_CTL%>"><i
							class="fa fa-users mr-2"></i>Faculty List</a>
					</div></li>
					
				<!-- 8. Time Table (Visible)-->
				<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Time Table</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.TIMETABLE_CTL%>"><i
								class="fa fa-plus mr-2"></i>Add TimeTable</a> <a
								class="dropdown-item" href="<%=ORSView.TIMETABLE_LIST_CTL%>"><i
								class="fa fa-list mr-2"></i>TimeTable List</a>
					</div></li>
					<!-- 9. Subject -->
					<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Subject</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.SUBJECT_CTL%>"><i
								class="fa fa-book-open mr-2"></i>Add Subject</a> <a class="dropdown-item"
								href="<%=ORSView.SUBJECT_LIST_CTL%>"><i
								class="fa fa-list mr-2"></i>Subject List</a>
					</div></li>
					
					<!-- 10. Doctor -->
					<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Doctor</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.DOCTOR_CTL%>"><i
								class="fa fa-user-md mr-2"></i>Add Doctor</a> <a class="dropdown-item"
								href="<%=ORSView.DOCTOR_LIST_CTL%>"><i
								class="fa fa-list mr-2"></i>Doctor List</a>
					</div></li>
					
				<!-- Use Case DROPDOWN - Items 10+ -->
				<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
						<font color="dark-green">Use Cases</font> <i class="fa fa-chevron-down ml-1"></i>
				</a>
					<div class="dropdown-menu">
						
						<!-- 11. Profile -->
						<a class="dropdown-item px-3 border-bottom" href="#"
							data-toggle="dropdown" data-target="#more-profile"> <i
							class="fa fa-user mr-2"></i>Profile
						</a>
						<div class="dropdown-submenu px-3 py-2">
							<a class="dropdown-item" href="<%=ORSView.PROFILE_CTL%>"><i
								class="fa fa-plus mr-2"></i>Add Profile</a> <a class="dropdown-item"
								href="<%=ORSView.PROFILE_LIST_CTL%>"><i
								class="fa fa-list mr-2"></i>Profile List</a>
						</div>
						<!-- 12. Contact -->
						<a class="dropdown-item px-3 border-bottom" href="#"
							data-toggle="dropdown" data-target="#more-contact"> <i
							class="fa fa-address-book mr-2"></i>Contact
						</a>
						<div class="dropdown-submenu px-3 py-2">
							<a class="dropdown-item" href="<%=ORSView.CONTACT_CTL%>"><i
								class="fa fa-plus mr-2"></i>Add Contact</a> <a class="dropdown-item"
								href="<%=ORSView.CONTACT_LIST_CTL%>"><i
								class="fa fa-list mr-2"></i>Contact List</a>
						</div>
						<!-- 13. Support -->
						<a class="dropdown-item px-3 border-bottom" href="#"
							data-toggle="dropdown" data-target="#more-support"> <i
							class="fa fa-life-ring mr-2"></i>Support
						</a>
						<div class="dropdown-submenu px-3 py-2">
							<a class="dropdown-item" href="<%=ORSView.SUPPORT_CTL%>"><i
								class="fa fa-plus mr-2"></i>Add Ticket</a> <a class="dropdown-item"
								href="<%=ORSView.SUPPORT_LIST_CTL%>"><i
								class="fa fa-list mr-2"></i>Ticket List</a>
						</div>
						<!-- 14. Alert -->
						<a class="dropdown-item px-3 border-bottom" href="#"
							data-toggle="dropdown" data-target="#more-alert"> <i
							class="fa fa-bell mr-2"></i>Alert
						</a>
						<div class="dropdown-submenu px-3 py-2">
							<a class="dropdown-item" href="<%=ORSView.ALERT_CTL%>"><i
								class="fa fa-plus mr-2"></i>Add Alert</a> <a class="dropdown-item"
								href="<%=ORSView.ALERT_LIST_CTL%>"><i
								class="fa fa-list mr-2"></i>Alert List</a>
						</div>
						<!-- 15. Feedback -->
						<a class="dropdown-item px-3 border-bottom" href="#"
							data-toggle="dropdown" data-target="#more-feedback"> <i
							class="fa fa-comment mr-2"></i>Feedback
						</a>
						<div class="dropdown-submenu px-3 py-2">
							<a class="dropdown-item" href="<%=ORSView.FEEDBACK_CTL%>"><i
								class="fa fa-plus mr-2"></i>Add Feedback</a> <a
								class="dropdown-item" href="<%=ORSView.FEEDBACK_LIST_CTL%>"><i
								class="fa fa-list mr-2"></i>Feedback List</a>
						</div>
						<!-- 16. Shift -->
						<a class="dropdown-item px-3" href="#" data-toggle="dropdown"
							data-target="#more-shift"> <i class="fa fa-clock mr-2"></i>Shift
						</a>
						<div class="dropdown-submenu px-3 py-2">
							<a class="dropdown-item" href="<%=ORSView.SHIFT_CTL%>"><i
								class="fa fa-plus mr-2"></i>Add Shift</a> <a class="dropdown-item"
								href="<%=ORSView.SHIFT_LIST_CTL%>"><i
								class="fa fa-list mr-2"></i>Shift List</a>
						</div>
						<!-- 17. Attendance -->
						<a class="dropdown-item px-3" href="#" data-toggle="dropdown"
							data-target="#more-attendance"> <i
							class="fa fa-calendar-check mr-2"></i>Attendance
						</a>
						<div class="dropdown-submenu px-3 py-2">
							<a class="dropdown-item" href="<%=ORSView.ATTENDANCE_CTL%>"><i
								class="fa fa-plus mr-2"></i>Add Attendance</a> <a
								class="dropdown-item" href="<%=ORSView.ATTENDANCE_LIST_CTL%>"><i
								class="fa fa-list mr-2"></i>Attendance List</a>
						</div>
					</div></li>
				<%
}
%>
				<%
}
%>
				<!-- ===== Profile / Auth Menu ===== -->
				<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"><%=welcomeMsg%></a>
					<div class="dropdown-menu dropdown-menu-right mr-2">
						<%
if (userLoggedIn) {
%>
						<a class="dropdown-item" href="<%=ORSView.MY_PROFILE_CTL%>"><i
							class="fa fa-user mr-2"></i>My Profile</a> <a class="dropdown-item"
							href="<%=ORSView.CHANGE_PASSWORD_CTL%>"><i
							class="fa fa-key mr-2"></i>Change Password</a> <a
							class="dropdown-item" target="_blank"
							href="<%=ORSView.JAVA_DOC_VIEW%>"><i class="fa fa-code mr-2"></i>Java
							Doc</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item"
							href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>"><i
							class="fa fa-sign-out-alt mr-2"></i>Logout</a>
						<%
} else {
%>
						<a class="dropdown-item" href="<%=ORSView.LOGIN_CTL%>"><i
							class="fa fa-sign-in-alt mr-2"></i>Login</a> <a class="dropdown-item"
							href="<%=ORSView.USER_REGISTRATION_CTL%>"><i
							class="fa fa-user-plus mr-2"></i>Register</a>
						<%
}
%>
					</div></li>
			</ul>
		</div>
	</nav>
</body>
</html>
