<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Pocetna</title>

<%@ include file="components/style.jsp"%>
</head>
<body>
	<jsp:include page="components/navigation.jsp"></jsp:include>
	
	
	<div class="container">
		<h2 class="text-center">Prikaz restorana</h2>
		<div class="restaurants">
			<c:forEach var="restaurant" items="${restaurants}">
				<div class="card mt-3 restaurant" onclick = "location.href = 'restaurant?id=${restaurant.ID}'">
					<div class="card-body">
						<div class="row">
							<div class="col-md-3">
								<img
									src="data:image/jpeg;base64,${restaurantDAO.getRestaurantLogo(restaurant)}"
									class="restaurant-logo d-block mx-auto">
							</div>
							<div class="col-md-9 d-flex justify-content-around my-auto">
								<div>
									<h3 class="text-center">${restaurant.name} - ${restaurantTypeDAO.getTypeFromID(restaurant)}</h3>
									<p>${restaurant.city }, ${restaurant.address}, 
										${restaurant.zipCode}</p>
								</div>
								<div class = "restaurant-status d-flex align-items-center <c:if test="${!restaurant.active }">text-danger</c:if> <c:if test="${restaurant.active }">text-success</c:if>">
								<h4 class="text-center">
									<c:if test="${!restaurant.active }">Zatvoren</c:if>
									<c:if test="${restaurant.active }">Otvoren</c:if>
								</h4>
								</div>
							</div>
						</div>

					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>