<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.Restaurant"%>
<%@page import="bean.RestaurantType"%>
<%@page import="dao.RestaurantDAO"%>
<%@page import="dao.RestaurantTypeDAO"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Prikaz korisnika</title>
<%@ include file="components/style.jsp"%>
</head>
<body>
	<%@ include file="components/navigation.jsp"%>

	<div class="container mt-3">
		<h2 class="text-center">Prikaz kreiranih restorana</h2>
		<div class="row">
			<c:forEach var="restaurant" items="${restaurants}">
				<div class="col-md-6">
					<div class="card my-2">
						<div class="card-header">
							<div class="d-flex justify-content-between">
								<img class="restaurantLogo"
									src="data:image/jpeg;base64,${restaurantDAO.getRestaurantLogo(restaurant)}" />
								<div class="ml-3 text-right">
									<div>${restaurant.getName()},${restaurant.getAddress()},
										${restaurant.getZipCode()}</div>
										<div>Ocena: ${restaurantDAO.getAverageReview(restaurant.getID())} / 5</div>
								</div>
							</div>
						</div>


						<div class="card-body">
							<p>Aktivan: ${restaurant.getActive()}</p>
							<p>Menadzeri:
								${restaurantDAO.getRestaurantManagers(restaurant.getID())}</p>
						</div>
						<div class="card-footer d-flex justify-content-around">
							<c:if test="${restaurant.getActive() == false}">
								<button class="btn btn-primary"
									onclick="location.href = 'activateRestaurant?id=${restaurant.getID()}'">Aktiviraj
									restoran</button>
							</c:if>
							<c:if test="${restaurant.getActive() == true}">
								<button class="btn btn-danger"
									onclick="location.href = 'deactivateRestaurant?id=${restaurant.getID()}'">Deaktiviraj
									restoran</button>
							</c:if>
							<button class="btn btn-danger"
								onclick="location.href = 'deleteRestaurant?id=${restaurant.ID}'">Ukloni
								restoran</button>

						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

</body>
</html>