<%@page import="in.co.rays.proj3.controller.ORSView"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome Page</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/resources/demos/style.css">


<style>
.p4 {
	background-image:
		url('<%=ORSView.APP_CONTEXT%>/img/Linkme.jpg');
	background-size: cover;
}

.cl {
	font-family: Lucida Calligraphy;
	font-family: Monotype Corsiva;
	font-family: Footlight MT Light;
}
</style>

</head>
<body class="p4">
	<div class="header">
		<%@include file="Header.jsp"%>
	</div>
	<div class="align-items-center justify-content-center">
		<center >
			<h1 style="padding-top: 20%; color: white" >
				<b class="cl">" Welcome To Online Result System "</b>
			</h1>
		</center>
	</div>
	<div class="footer">
		<%@include file="FooterView.jsp"%>
	</div>
</body>

</html>