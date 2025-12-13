<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Error</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
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
		<div class="card grad-card shadow-sm text-center w-100">
			<div class="card-body py-4">
				<img src="<%=ORSView.APP_CONTEXT%>/img/500.jpg" class="img-fluid mb-4" style="max-width: 550px; height: auto;">

				<h2 class="text-danger fw-bold mb-3">Oops! Something went wrong</h2>
				<p class="text-danger fs-5">Requested resource is not available</p>
				
				<div class="col-md-6 mx-auto mt-4">
					<h5 class="fw-bold">Try:</h5>
					<ul class="text-start">
						<li>Check the network cables, modem, and router</li>
						<li>Reconnect to Wi-Fi</li>
					</ul>
				</div>
				
				<div class="mt-4">
					<a href="<%=ORSView.WELCOME_CTL%>" class="btn btn-primary btn-lg px-4">
						Please click here to Go Back
					</a>
				</div>
			</div>
		</div>
	</main>

	<div>
		<%@include file="FooterView.jsp"%>
	</div>

	<!-- Bootstrap JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js  "></script>
</body>
</html>