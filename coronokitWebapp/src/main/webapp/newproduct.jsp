<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Add New Product(Admin)</title>
</head>
<body>
	<div align="center">
		<jsp:include page="header.jsp" />
		<hr />
		<h3>Add New Product</h3>
		<nav>
			<div>
				<a href="admin?action=list"><b>Show the Product list</b></a>
			</div>
		</nav>
		<form action="admin?action=insertproduct" method="POST">
			<table border="1">
				<tr>
					<td><label>Product Id</label></td>
					<td><input type="number" name="productId"
						value="${product.id }" required /></td>
				</tr>
				<tr>
					<td><label>Product Name</label></td>
					<td><input type="text" name="productName"
						value="${product.productName }" required /></td>
				</tr>

				<tr>
					<td><label>Cost</label></td>
					<td><input type="text" name="cost" value="${product.cost }"
						required /></td>
				</tr>

				<tr>
					<td><label>Product Details</label></td>
					<td><input type="text" name="productDescription"
						value="${product.productDescription }" required /></td>
				</tr>

			</table>
			<button>ADD</button>
		</form>


		<hr />
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>