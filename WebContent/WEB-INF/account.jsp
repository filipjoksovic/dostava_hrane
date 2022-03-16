<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Moj nalog</title>
<jsp:useBean id="userDAO" type="dao.UserDAO" scope="request" />
<%@ include file="components/style.jsp"%>
</head>
<body>
	<%@ include file="components/navigation.jsp"%>
	<div class="container-fluid px-5 mt-3">
		<h3 class="text-center">Dobrodosli, ${user.username}.</h3>
		<c:choose>
			<c:when test="${user.getRoleID() == 0}">
				<h5 class="text-center">Ispod se nalaze podaci o Vasem nalogu,
					kao i porudzbinama koje ste kreirali.</h5>
				<div class="row mt-5">
					<div class="col-md-3">
						<h5>Podaci o nalogu</h5>
						<form method="POST" action="editAccount">
							<div class="form-group">
								<label for="fname">Ime</label> <input name="fname"
									class="form-control" id="fname" value="${user.getFirstName()}">
							</div>
							<div class="form-group">
								<label for="lname">Prezime</label> <input name="lname"
									id="lname" class="form-control" value="${user.getLastName()}">
							</div>
							<div class="form-group">
								<label for="gender">Pol</label> <select name="gender"
									class="form-control">
									<c:forEach var="gender" items="${genderSelect}">
										<option value="${gender.getGenderID() }">${gender.getGenderName() }</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label for="dob">Datum rodjenja</label> <input type="date"
									value="${user.getDateOfBirth()}" name="dob"
									class="form-control">
							</div>
							<p>Broj poena: ${pointsCount}</p>
							<button
								<c:choose>
															<c:when test="${user.getUserType() == 'Zlatni'}">
																disabled class="btn btn-warning btn-block">Rank: Zlatni</button>
															</c:when>
															<c:when test="${user.getUserType() == 'Srebrni'}">
																disabled class="btn btn-secondary btn-block">Rank: Srebrni</button>
															</c:when>
															<c:when test="${user.getUserType() == 'Bronzani'}">
																disabled class="btn btn-info btn-block">Rank: Bronzani</button>
															</c:when>
															<c:otherwise>
																disabled class="btn btn-success btn-block">Rank: Standardni</button>
															</c:otherwise>
														</c:choose>
								<input type = "hidden" name = "username" value = "${user.getUsername()}">
						<button type="submit" class="btn btn-primary btn-block w-100">Izmeni
							informacije o nalogu</button>
				</form>
			</div>
			<div class="col-md-6">
				<c:if test="${user.getRoleID() == 0 }">
					<h5 class="text-center">Pregled porudzbina</h5>
					<div class="order-list">
						<c:forEach var="order" items="${groupedOrders}">
							<div class="card order my-2">
								<div class="card-header">
									<div
										class="w-100 d-flex justify-content-between align-items-center">
										<div>
											<p>${order.key}</p>
											<p>${order.value.get(0).getTimestamp() }
										</div>
										<c:if test="${orderDAO.canCancel(order.value)}">
											<button class="btn btn-danger" onclick = "location.href = 'cancelOrder?id=${order.key}'">Otkazi porudzbinu</button>
										</c:if>
									</div>
								</div>
								<div class="card-body">
									<c:forEach var="order_item" items="${order.value}">
										<c:set var="product" scope="request"
											value="${productDAO.getProductByID(order_item.getProductID())}" />
										<div class="card order-item">
											<div class="card-body">
												<div class="row">
													<div class="col-md-3">
														<div>
															<img class="product-image"
																src="data:image/jpeg;base64,${productDAO.getProductImage(product) }">
														</div>
													</div>
													<div class="col-md-6">
														<p>Artikal: ${product.getName()}</p>
														<p>Kolicina: ${order_item.getProductQuantity() }</p>
													</div>
													<div class="col-md-3">
														<c:choose>
															<c:when test="${order_item.getStatus() == 1 }">
																<button disabled class="btn btn-info btn-block w-100">${orderStatusDAO.getStatusFromID(order_item.getStatus()) }</button>
															</c:when>
															<c:when test="${order_item.getStatus() == 2 }">
																<button disabled class="btn btn-warning btn-block w-100">${orderStatusDAO.getStatusFromID(order_item.getStatus()) }</button>
															</c:when>
															<c:when test="${order_item.getStatus() == 3 }">
																<button disabled class="btn btn-primary btn-block w-100">${orderStatusDAO.getStatusFromID(order_item.getStatus()) }</button>
															</c:when>
															<c:when test="${order_item.getStatus() == 4 }">
																<button disabled class="btn btn-success btn-block w-100">${orderStatusDAO.getStatusFromID(order_item.getStatus()) }</button>
															</c:when>
															<c:when test="${order_item.getStatus() == 5 }">
																<button disabled class="btn btn-danger">${orderStatusDAO.getStatusFromID(order_item.getStatus()) }</button>
															</c:when>
															<c:otherwise>
																<button disabled class="btn btn-secondary">${orderStatusDAO.getStatusFromID(order_item.getStatus()) }</button>
															</c:otherwise>

														</c:choose>
													</div>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
						</c:forEach>
					</div>
				</c:if>
			</div>
			<div class="col-md-3">
				<div class="my-3">
					<h5 class="text-right">Ocenite restorane/ Izmenite ocenu</h5>
					<c:forEach var="restaurant" items="${reviewableRestaurants}">
						<button class="btn btn-warning mt-3 btn-block w-75 ml-auto"
							onclick="location.href = 'rateRestaurant?id=${restaurant.getID()}'">Oceni
							${restaurant.getName() }</button>
					</c:forEach>
				</div>
				<div class="my-3">
					<c:forEach var="restaurant" items="${reviewedRestaurants}">
						<button class="btn btn-warning mt-3 btn-block w-75 ml-auto"
							onclick="location.href = 'rateRestaurant?id=${restaurant.getID()}'">Izmenite ocenu za
							${restaurant.getName() }</button>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	</c:when>
	<c:otherwise>
		<div class = "container">
			<h5>Podaci o nalogu</h5>
				<form method="POST" action="editAccount">
					<div class="form-group">
						<label for="fname">Ime</label> <input name="fname"
							class="form-control" id="fname" value="${user.getFirstName()}">
					</div>
					<div class="form-group">
						<label for="lname">Prezime</label> <input name="lname" id="lname"
							class="form-control" value="${user.getLastName()}">
					</div>
					<div class="form-group">
						<label for="gender">Pol</label> <select name="gender"
							class="form-control">
							<c:forEach var="gender" items="${genderSelect}">
								<option value="${gender.getGenderID() }">${gender.getGenderName() }</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label for="dob">Datum rodjenja</label> <input type="date"
							value="${user.getDateOfBirth()}" name = "dob" class="form-control">
					</div>
					
						<input type = "hidden" name = "username" value = "${user.getUsername()}">
						<button type="submit" class="btn btn-primary btn-block w-100">Izmeni
							informacije o nalogu</button>
				</form>
		</div>
	</c:otherwise>
	</c:choose>
</body>
</html>