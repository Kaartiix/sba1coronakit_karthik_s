<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Edit Product(Admin)</title>
</head>
<body><div align="center">
	<jsp:include page="header.jsp" />
	<hr />
	
		<h3>Edit Product</h3>
		<div>
			<a href="admin?action=list"><b>Show the Product list</b></a>
		</div>
		<form action="admin?action=updateproduct" method="POST">

			<div>
				<label>Product Id :</label> <input type="number" name="productId"
					value="${product.id }" required />
			</div>
			<div>
				<label>Product Name :</label> <input type="text" name="productName"
					value="${product.productName }" required />
			</div>
			<div>
				<label>Cost :</label> <input type="text" name="cost"
					value="${product.cost }" required />
			</div>
			<div>
				<label>Product Details :</label> <input type="text"
					name="productDescription" value="${product.productDescription }"
					required />
			</div>
			<button>Update</button>
		</form>
	</div>


	<hr />
	<jsp:include page="footer.jsp" />
</body>
</html>