<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Korpa</title>
<%@ include file="components/style.jsp"%>

<jsp:useBean id="restaurantProductDAO" type="dao.RestaurantProductDAO"
	scope="request" />
<jsp:useBean id="cartDAO" type="dao.CartDAO" scope="request" />
</head>
<body>
	<%@ include file="components/navigation.jsp"%>
	<div class="container mt-5">
		<div class="row">
			<div class="col-md-8">
				<h3 class="text-center">Proizvodi u korpi</h3>
				<c:if test="${cartDAO.getCartCount() == 0}">
					<div class="mt-5">
						<h5 class="text-center">Ups...nemate ni jedan proizvod u
							korpi. Vratite se na pocetnu i dodajte neke proizvode iz naseg
							asortimana.</h5>
						<a href="/dostava_hrane/" class="btn btn-primary btn-block w-100">Pocetna</a>
					</div>
				</c:if>
				<c:forEach var="product" items="${cartProducts}">
					<div class="card my-3">
						<div class="card-body">
							<div class="row">
								<div class="col-md-4 d-flex align-items-center">
									<img class="product-image"
										src="data:image/jpeg;base64,${restaurantProductDAO.getProductImage(product)}">
								</div>
								<div class="col-md-6">
									<p>Naziv proizvoda: ${product.name}</p>
									<p>Cena proizvoda: ${product.price} rsd</p>
									<p>Tip proizvoda:
										${ProductTypeDAO.getTypeName(product.getTypeID())}</p>
									<p>Kolicina proizvoda:
										${cartDAO.getCartItem(product.getID()).getProductQuantity()}</p>
								</div>
								<div class="col-md-2 ">
									<div
										class="h-100 d-flex flex-column align-items-center justify-content-around">
										<form method="POST" action='removeFromCart' class="w-100 my-1">
											<input type="hidden" name="product_id" value="${product.ID}">
											<input type="hidden" name="restaurant_id"
												value="${restaurant.ID}">
											<button type="submit"
												class="btn btn-danger btn-block w-100 mx-auto"
												onclick="location.href = ">
												<i class="fas fa-times"></i>
											</button>
										</form>
										<form method="POST" action='addToCart' class="w-100 my-1">
											<input type="hidden" name="product_id" value="${product.ID}">
											<input type="hidden" name="restaurant_id"
												value="${restaurant.ID}"> <input type="hidden"
												name="quantity" value="1">
											<button type="submit"
												class="btn btn-success btn-block w-100 mx-auto "
												onclick="location.href = ">
												<i class="fas fa-plus mr-3"></i><strong>1</strong>
											</button>
										</form>
										<form method="POST" action='removeFromCart'
											class="w-100  my-1">
											<input type="hidden" name="product_id" value="${product.ID}">
											<input type="hidden" name="restaurant_id"
												value="${restaurant.ID}"> <input type="hidden"
												name="quantity" value="1">
											<button type="submit"
												class="btn btn-warning btn-block w-100 mx-auto"
												onclick="location.href = ">
												<i class="fas fa-minus mr-3"></i><strong>1</strong>
											</button>
										</form>
									</div>
								</div>
							</div>

						</div>
					</div>
				</c:forEach>
			</div>
			<div class="col-md-1"></div>
			<div class="col-md-3">
				<form method="POST" action="createOrder">
					<h3 class="text-center">Podaci o porudzbini</h3>
					<h5 class="text-center my-3">Podaci o kupcu</h5>
					<div class="orderer-data mb-3">
						<p>
							Ime: <input type="text" name="fname"
								value="${user.getFirstName()}" class="form-control">
						</p>
						<p>
							Prezime: <input type="text" name="lname"
								value="${user.getLastName()}" class="form-control">
						</p>
						<p>
							Adresa: <input type="text" class="form-control form-control-sm"
								name="address" required>
						</p>
						<p>
							Grad: <input type="text" class="form-control form-control-sm"
								name="city" required>
						</p>
						<p>
							Drzava: <input type="text" class="form-control form-control-sm"
								name="country" required>
						</p>
						<p>Trenutan broj bodova: ${accountPoints}</p>
					</div>
					<h5 class="text-center mb-3">Podaci o porudzbini</h5>
					<div class="order-data">
						<p>
							Ukupna cena: <strong>${cartDAO.getCartPrice()}.00 rsd</strong>
						</p>
						<c:if test="${discount > 0 }">
							<p>Cena sa uracunatim popustom (${discount}%): ${discountedPrice}din</p>
						</c:if>
						<p>
							Ostvaren broj bodova: <strong>${cartDAO.getCartPoints()}</strong>
						</p>
					</div>
					<button type="submit" class="btn btn-warning btn-block w-100">
						Kreiraj porudzbinu <i class="fas fa-arrow-right"></i>
					</button>
			</div>
			</form>
		</div>
	</div>

</body>
</html>