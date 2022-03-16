<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dodavanje proizvoda</title>
<%@ include file="components/style.jsp"%>

</head>
<body>
	<jsp:include page="components/navigation.jsp"></jsp:include>
	<div class="container mt-3">
		<h3 class="text-center">Dodavanje proizvoda</h3>
		<form method="POST" action="addProduct" enctype = "multipart/form-data">
			<div class="row">
				<div class="col-md-6 form-group">
					<label for="productName">Naziv proizvoda</label> <input
						class="form-control" type="text" name="pname" id="productName">
				</div>
				<div class="col-md-6 form-group">
					<label for="productPrice">Cena proizvoda</label> <input
						class='form-control' class="form-control" id="productPrice"
						type="text" name="price">
				</div>
			</div>
			<div class="form-group">
				<label for="typeSelect">Tip proizvoda</label> <select
					class="form-control" name="type" id="typeSelect"
					class="form-control">
					<c:forEach var="productType" items="${productTypes}">
						<option value="${productType.getID()}">${productType.getName() }</option>
					</c:forEach>
				</select>
			</div>
			<div class="row">
				<div class="form group col-md-10">
					<label for="productQuantity">Kolicina proizvoda</label> <input
						type="text" name="quantity" class="form-control"
						id="productQuantity">
				</div>
				<div class="form-group col-md-2">
					<label for="unitType">Jedinica kolicina</label> <select
						class="form-control" name="unit" id="unitType">
						<option value="g" selected>grama</option>
						<option value="ml">mililitara</option>
					</select>
				</div>
			</div>
			<div class = "form-group">
				<label for = 'productDescription'>Opis proizvoda</label>
				<textarea class = "form-control" id = "productDescription" name = "description"></textarea>
			</div>
			<div class="input-group mb-3 mt-3">
				<div class="input-group-prepend">
					<span class="input-group-text">Slika proizvoda</span>
				</div>
				<div class="custom-file">
					<input type="file" class="custom-file-input" name="product_image" id="logo">
					<label class="custom-file-label" for="logo">Odaberite sliku</label>
				</div>
			</div>
			<button type = "submit" class = "btn btn-primary w-100">Kreiraj proizvod</button>
		</form>
	</div>
</body>
</html>