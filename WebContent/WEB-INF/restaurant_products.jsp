<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Prikaz proizvoda</title>
<%@ include file="components/style.jsp"%>
<%@page  import = "dao.RestaurantProductDAO" %>
<%@page import = "dao.ProductTypeDAO" %>


</head>
<body>
	<%@include file = "components/navigation.jsp"%>
	<c:if test='${not empty sessionScope["message"]}'>
		<div
			class="alert alert-success text-center alert-dismissible fade show"
			role="alert">
			${sessionScope['message']}
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
		<%
		session.setAttribute("message", null);
		%>
	</c:if>
	<c:if test='${not empty sessionScope["error"]}'>
		<div
			class="alert alert-success text-center alert-dismissible fade show"
			role="alert">
			${sessionScope['error']}
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
		<%
		session.setAttribute("error", null);
		%>
	</c:if>
	<div class = "container my-3">
		<h2 class = "text-center">Prikaz proizvoda za Vas restoran</h2>
		<c:forEach var = "product" items = "${products}" >
			<div class = "card my-3">
				<div class = "card-body row">
					<div class = "col-md-3">
						<img class = "product-image" src = "data:image/jpeg;base64,${RestaurantProductDAO.getProductImage(product)}">
					</div>
					<div class = "col-md-7">
						<p>Naziv proizvoda: ${product.name}</p>
						<p>Cena proizvoda: ${product.price} rsd</p>
						<p>Tip proizvoda: ${ProductTypeDAO.getTypeName(product.getTypeID())}</p>
						<p>Kolicina proizvoda: ${product.quantity}</p>
					</div>
					<div class = "col-md-2 d-flex flex-column flex-grow-1 my-auto justify-content-around">
						<button class = "btn btn-danger my-3" onclick = "location.href = 'removeProduct?id=${product.getID()}'">Ukloni proizvod</button>
						<button class = "btn btn-warning my-3" onclick = "location.href = 'editProduct?id=${product.getID()}'">Izmeni proizvod</button>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>