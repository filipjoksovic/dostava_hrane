<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Admin</title>

<%@ include file="components/style.jsp"%>
</head>
<body>
	<jsp:include page="components/navigation.jsp"></jsp:include>
	<jsp:useBean id="user" type="bean.User" scope="session" />

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
	<div class="container mt-3">
		<h2 class="text-center">Dobrodosli, ${user.getUsername()}</h2>
		<h5 class="text-center">Ispod se nalaze neke od akcija koje su
			Vam dostupne.</h5>
	</div>
	<div class="container mt-3">
		<div class="actions">
			<div onclick="location.href = 'addProduct'" class="action">
				<div class="action-icon">
					<i class="fas fa-users"></i>
				</div>
				<div class="action-text">Unos proizvoda</div>
			</div>
			<div class="action" onclick="location.href = 'viewProducts'">
				<div class="action-icon">
					<i class="fas fa-hamburger"></i>
				</div>
				<div class="action-text">Pregled proizvoda</div>
			</div>
			<div class="action"
				onclick="location.href = 'viewOrders?id=${user.getRestaurantID()}'">
				<div class="action-icon">
					<i class="fas fa-clipboard"></i>
				</div>
				<div class="action-text">Pregled porudzbina</div>
			</div>
			<div class="action" onclick="location.href = 'restaurant_reviews'">
				<div class="action-icon">
					<i class="fas fa-eye"></i>
				</div>
				<div class="action-text">Pregled ocena</div>
			</div>
		</div>
	</div>
	<div class="container mt-3">
		<h2 class="text-center">Podaci o restoranu</h2>
		<h5 class="text-center">Ispod se nalaze podaci o restoranu, koje
			mozete menjati</h5>
	</div>
	<div class="container mt-5">
		<div class="row mt-5">
			<div
				class="col-md-3 d-flex align-items-center justify-content-center">
				<img class="restaurant-logo"
					src="data:image/jpeg;base64,${restaurantLogo}" />
			</div>
			<div class="col-md-9">
				<form method="POST" action="editRestaurant">
					<div class="form-group">
						<label for="restaurant-name">Naziv restorana</label> <input
							type="text" class="form-control" name="restaurant_name"
							value="${restaurant.getName() }">
					</div>
					<div class="row mt-3">
						<div class="col-md-4">
							<label for="restaurantAddress">Adresa</label> <input type="text"
								name="address" id="restaurantAddress" class="form-control"
								value="${restaurant.getAddress() }">
						</div>
						<div class="col-md-4">
							<label for="restaurantCity">Grad</label> <input type="text"
								name="city" id="restaurantCity" class="form-control"
								value="${restaurant.getCity() }">
						</div>
						<div class="col-md-4">
							<label for="restaurantZipCode">Postanski broj</label> <input
								type="text" name="zip_code" id="restaurantZipCode"
								class="form-control" value="${restaurant.getZipCode() }">
						</div>
					</div>
					<div class="form-group">
						<label for id="restaurantType">Tip restorana</label> <select
							name="restaurant_type" class="form-control">
							<c:forEach var="restaurantType"
								items="${restaurantTypeDAO.getAllTypes()}">
								<option value="${restaurantType.getID()}"
									<c:if test = "${restaurant.getType() == restaurantType.getID()}">selected</c:if>>${restaurantType.getTitle()}</option>
							</c:forEach>
						</select>
					</div>
					<div class="mt-3">
						<c:if test="${not restaurant.getActive() }">
							<button disabled
								class="btn-block w-50 mx-auto btn btn-outline-danger btn-lg">Zatvoren</button>
						</c:if>
						<c:if test="${restaurant.getActive() }">
							<button disabled
								class="btn-block w-50 mx-auto  btn btn-outline-success btn-lg">Otvoren</button>
						</c:if>
					</div>
					<input name = "restaurant_id" type = "hidden" value = "${restaurant.getID()}">
					<button type="submit"
						class="btn-block w-100 mt-3 btn btn-warning btn-lg">Izmeni</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>