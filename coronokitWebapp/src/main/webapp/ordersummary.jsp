<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Order Summary(user)</title>
</head>
<body>
<div align="center">
	<jsp:include page="header.jsp" />
	<hr />
	<div><a href="admin?action=login"><b>Login as Admin?</b></a></div><span></span><span></span>
	<div><a href="user?action=newuser"><b>Order as new user?</b></a></div>
	<h1 align="center" style="font: normal; font-size: 20px; color: blue;">Order Summary</h1>
	<c:choose>
		<c:when test="${coronaKit!=null }">
			<table border="1">
				<tr><td>Order Number: </td><td>${coronaKit.getId() }</td></tr>
				<tr><td>Name: </td><td>${coronaKit.getPersonName() }</td></tr>
				<tr><td>Email: </td><td>${coronaKit.getEmail() }</td></tr>
				<tr><td>Mobile: </td><td>${coronaKit.getContactNumber() }</td></tr>
				<tr><td>Billing Address: </td><td>${coronaKit.getDeliveryAddress() }</td></tr>
				<tr><td>Total Amount: </td><td>${coronaKit.getTotalAmount() }</td></tr>
			</table>
		</c:when>
	</c:choose>

	<p></p>
	
	<hr />
	<jsp:include page="footer.jsp" /></div>
</body>
</html>