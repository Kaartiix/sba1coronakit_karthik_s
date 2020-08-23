<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-All Products(Admin)</title>
</head>
<body >
<div align="center"  >
	<jsp:include page="header.jsp" />
	<nav>
		<div><a href="admin?action=login"><b>Login ?</b></a></div><span></span><span></span>
		<div><a href="admin?action=newproduct"><b>Add New Product</b></a></div>
	</nav>
	<%-- Required View Template --%>
	<c:choose>
		<c:when test="${products == null || products.isEmpty() }">
			<p>
				No product Available. Want to add a Product? <a href="">Add a
					product</a>
			</p>
		</c:when>
		<c:otherwise>
			<table border="1" cellspacing="5px" cellpadding="5px">
				<tr>
					<th>Product ID</th>
					<th>Product Name</th>
					<th>Cost</th>
					<th>Product Details</th>
				</tr>
				<c:forEach items="${products}" var="product">
					<tr>
						<td>${product.id }</td>
						<td>${product.productName}</td>
						<td>${product.cost}</td>
						<td>${product.productDescription}</td>
						<td><a href="admin?action=deleteproduct&pid=${product.id }">Delete</a> 
						<span></span>
						<a href="admin?action=editproduct&pid=${product.id }">Edit</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
</div>
	<jsp:include page="footer.jsp" />
</body>
</html>