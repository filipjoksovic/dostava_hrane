<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Detalji restorana</title>
<%@ include file="components/style.jsp"%>
<%@page import="dao.RestaurantProductDAO"%>
<%@page import="dao.ProductTypeDAO"%>

</head>
<body>
	<%@include file="components/navigation.jsp"%>

	<div class="container mt-5">
		<div class="row">
			<div class="col-md-3">
				<h3 class="text-center">${restaurant.name }</h3>
				<div>
					<h5 class="text-center">${restaurant.city},
						${restaurant.address},${restaurant.zipCode}</h5>
					<c:if test="${restaurant.getActive()}">
						<h6 class="btn btn-success btn-lg btn-block mt-5">Otvoren</h6>
					</c:if>
					<c:if test="${not restaurant.getActive()}">
						<h6 class="btn btn-success btn-lg btn-block mt-5">Zatvoren</h6>
					</c:if>
				</div>
				<c:if test="${avgReview == 0}">
					<h6 class="text-primary text-center">Nemamo ocena za ovaj
						restoran</h6>
				</c:if>
				<c:if test="${avgReview > 0}">
					<div class="text-primary text-center text-lg ">
						<c:forEach begin="1" end="${avgReview}" step="1" varStatus="loop">
							<i class="fas fa-star"></i>
						</c:forEach>
						<c:forEach begin="1" end="${5 - avgReview}" step="1"
							varStatus="loop">
							<i class="far fa-star"></i>
						</c:forEach>
					</div>
					<p class="text-success text-lg text-center">(${avgReview}/5.0)</p>
				</c:if>
			</div>
			<div class="col-md-1"></div>
			<div class="col-md-8">
				<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
					<li class="nav-item" role="presentation"><a
						class="nav-link active" id="pills-home-tab" data-toggle="pill"
						href="#products" role="tab" aria-controls="pills-home"
						aria-selected="true">Proizvodi</a></li>
					<li class="nav-item" role="presentation"><a class="nav-link"
						id="pills-profile-tab" data-toggle="pill" href="#reviews"
						role="tab" aria-controls="pills-profile" aria-selected="false">Ocene
							restorana</a></li>
				</ul>
				<div class="tab-content" id="pills-tabContent">
					<div class="tab-pane fade show active" id="products"
						role="tabpanel" aria-labelledby="home-tab">
						<h3 class="text-center">Proizvodi restorana</h3>
						<c:forEach var="product" items="${products}">
							<div class="card my-3">
								<div class="card-body">
									<div class="row">
										<div class="col-md-3 d-flex align-items-center">
											<img class="product-image"
												src="data:image/jpeg;base64,${RestaurantProductDAO.getProductImage(product)}">
										</div>
										<div class="col-md-7">
											<p>Naziv proizvoda: ${product.name}</p>
											<p>Cena proizvoda: ${product.price} rsd</p>
											<p>Tip proizvoda:
												${ProductTypeDAO.getTypeName(product.getTypeID())}</p>
											<p>Kolicina proizvoda: ${product.quantity}</p>
										</div>
									</div>
									<c:if test = "${user.getRoleID() == 0 && !user.getUsername().isBlank() }">
									<div class="">
										<form method="POST" action='addToCart'
											class=" row  align-items-center justify-content-center">
											<input type="hidden" name="product_id" value="${product.ID}">
											<input type="hidden" name="restaurant_id"
												value="${restaurant.ID}">
											<div class="col-md-2"></div>
											<div class="col-md-4">
												<div class="row align-items-center my-3">
													<div class="col-md-4">
														<label class="m-0 mr-1" for="quantity">Kolicina</label>
													</div>
													<div class="col-md-8">
														<input id="quantity" type="number" min=1 max=30
															name="quantity" value=1 class="form-control w-100">
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="h-100">
													<button type="submit"
														class="btn btn-primary btn-block w-100 my-auto"
														onclick="location.href = ">Dodaj u korpu</button>
												</div>
											</div>
											<div class="col-md-2"></div>
										</form>
									</div>
									</c:if>
									<c:if test = "${user.getRoleID() != 0}">
										<button class = "btn btn-warning btn-block w-75 mx-auto mt-3" disabled>Samo korisnici mogu da dodaju proizvode u korpu</button>
									</c:if>
								</div>
							</div>
						</c:forEach>



					</div>
					<div class="tab-pane fade" id="reviews" role="tabpanel"
						aria-labelledby="profile-tab">
						<c:forEach var="restaurantReview" items="${restaurantReviews}">
							<div class="card">
								<div
									class="card-header d-flex align-items-center w-100 justify-content-between">
									<div>
										<i class="fas fa-user text-lg mr-2 text-primary"></i><strong>${restaurantReview.getUsername()}</strong>
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
							</div>
						</c:forEach>

					</div>
				</div>


			</div>
		</div>
	</div>
</body>
</html>