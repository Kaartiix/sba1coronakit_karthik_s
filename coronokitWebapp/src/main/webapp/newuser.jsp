<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-New User(user)</title>
</head>
<body><div align="center">
<jsp:include page="header.jsp"/>
<hr/>
<nav>
		<div><a href="admin?action=login"><b>Login ?</b></a></div><span></span><span></span>
	</nav>
<h3 align="center" style="font: normal; font-size: 20px; color: blue;">User Details</h3>
	<div align="center">
		<div align="left" style="display: inline-block; border: thin solid black; padding: 10px;">
			<form action=user?action=insertuser method="post">
				<label style="display: table-cell;">Name:</label>
				<input type="text" maxlength="50" required name="username" type="text" />					
				<br> <br> 
				<label style="display: table-cell;">Email:</label>
				<input type="email" name="emailid" required/><br> <br> 
				<label style="display: table-cell;">Mobile:</label> 
				<input type="text" name="contactnum"  required /><br> <br> 
				<!--input type="text" name="usrMobile" pattern="^((\+)?(\d{2}[-])?(\d{10}){1})?(\d{11}){0,1}?$" oninvalid="setCustomValidity('Enter valid Mobile. Ex: +91-123456789 or 123456789')" required /--><br> <br>
				<button>Add to Shop</button>
				<br>
			</form>
		</div>
	</div>

<hr/>	
	<jsp:include page="footer.jsp"/></div>
</body>
</html>