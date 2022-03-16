<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Ocene resotrana</title>
<%@ include file="components/style.jsp"%>
</head>
<body>
	<%@ include file="components/navigation.jsp"%>
	<div class="container my-5">
		<h3 class="text-center">U listi ispod se nalaze sve ocene koje su
			Vasi kupci ostavili.</h3>
		<h5 class="text-center">Ocene mogu biti odobrene i odbijene.
			Odobrene ocene ulaze u prosecnu ocenu restorana.</h5>
	</div>
	<div class="container my-3">
		<h5 class="text-center">Ocene restorana</h5>
		<div class="row">
			<c:forEach var="restaurantReview" items="${restaurantReviews}">
				<div class="col-md-6">
					<div class="card">
						<div
							class="card-header d-flex align-items-center w-100 justify-content-between">
							<div>
								<i class="fas fa-user text-lg mr-2 text-primary"></i><strong>${restaurantReview.getUsername()} za ${restaurantDAO.getRestaurantByID(restaurantReview.getRestaurantID()).getName() }</strong>
							</div>
							<div class="text-primary ">
								<c:forEach begin="1" end="${restaurantReview.getGrade()}"
									step="1" varStatus="loop">
									<i class="fas fa-star"></i>
								</c:forEach>
								<c:forEach begin="1" end="${5 - restaurantReview.getGrade()}"
									step="1" varStatus="loop">
									<i class="far fa-star"></i>
								</c:forEach>
							</div>
						</div>
						<div class="card-body">
							Komentar: ${restaurantReview.getComment() } <small
								class="text-muted"><i class="d-block text-right">${restaurantReview.getTimestamp()}</i></small>
						</div>

						<div class="card-footer">
							<c:if test="${not restaurantReview.getAllowed()}">
								<form method="POST" action="restaurant_reviews">
									<input type = "hidden" name = "username" value = "${restaurantReview.getUsername()}">
									<input type = "hidden" name = "restaurant_id" value = "${restaurantReview.getRestaurantID()}">
									<button type="submit"
										class="btn btn-success btn-block mx-auto w-50">Dozvoli
										ocenu</button>
								</form>
							</c:if>
							<c:if test="${restaurantReview.getAllowed()}">
								<form method="POST" action="restaurant_reviews">
									<input type = "hidden" name = "username" value = "${restaurantReview.getUsername()}">
									<input type = "hidden" name = "restaurant_id" value = "${restaurantReview.getRestaurantID()}">
									<button type="submit"
										class="btn btn-danger btn-block mx-auto w-50">Odbij ocenu</button>
								</form>
							</c:if>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>