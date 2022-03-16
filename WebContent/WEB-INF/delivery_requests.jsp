<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Zahtevi za dostavu</title>
<%@ include file="components/style.jsp"%>

</head>
<body>
	<%@ include file="components/navigation.jsp"%>
	<div class="container my-5">
		<h3 class="text-center">Zahtevi za dostavu</h3>
		<h5 class="text-center">U listi ispod se nalaze sve dostupne
			porudzbine. Kada menadzer odobri Vas zahtev, mozete doci po nju.</h5>
	</div>
	<div class="container my-5">
		<c:forEach var="order" items="${groupedOrders}">
			<c:if test="${order.getStatusID() >= 2 }">
				<div class="card my-3">
					<div
						class="card-header d-flex justify-content-between align-items-center">
						<div>
							<span>${order.getID()}</span><br> <span>${order.getTimestamp()}</span>
						</div>
						<div>
							<c:if
								test="${not deliveryRequestDAO.requestExists(order.getID(), user.getUsername())}">
								<form action="sendDeliveryRequest" method="POST">
									<input type="hidden" name="order_id" value="${order.getID()}">
									<button type="submit" class="btn btn-info">Posalji
										zahtev za preuzimanje</button>
								</form>
							</c:if>
							<c:if
								test="${deliveryRequestDAO.requestExists(order.getID(), user.getUsername())}">
								<c:if
									test="${deliveryRequestDAO.canPickup(order.getID(), user.getUsername()) }">
									<c:if test = "${order.getStatusID() != 4 }">
									<form method="post" action="orderProceed">
												<input type="hidden" name="order_id"
													value="${order.getID()}">
												<button class="btn btn-info">${order.getStatus()}
													<i class="fas fa-arrow-right"></i>
													${orderStatusDAO.getNextStatus(order.getID()) }
												</button>
											</form>
											</c:if>
											<c:if test = "${order.getStatusID() == 4 }">
												<button disabled class = "btn btn-success">${order.getStatus()}</button>
											</c:if>
								</c:if>
								<c:if
									test="${!deliveryRequestDAO.canPickup(order.getID(), user.getUsername()) }">
									<button class="btn btn-danger" disabled>Cekanje
										menadzera</button>
								</c:if>
							</c:if>
						</div>
					</div>
					<div class="card-body order-items">
						<c:forEach var="order_item" items="${order.getOrderItems()}">
							<div class="card my-2">
								<div class="card-body">
									<c:set var="product"
										value="${productDAO.getProductByID(order_item.getProductID())}"></c:set>
									<div class="row">
										<div class="col-md-3">
											<img class="product-image"
												src="data:image/jpeg;base64,${productDAO.getProductImage(product) }">
										</div>
										<div class="col-md-7">
											<p>${product.getName() }</p>
											<p>Kolicina: ${order_item.getProductQuantity() }</p>
										</div>
										<div class="col-md-2"></div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>

					<div class="card-footer">
						<div class="row text-center">
							<div class="col-md-6">
								<span>${order.getFirstName()} ${order.getLastName() }</span>
							</div>
							<div class="col-md-6">
								<span>${order.getAddress()}, ${order.getCity()},
									${order.getCountry()}</span>
							</div>
						</div>


					</div>
				</div>
			</c:if>
		</c:forEach>
	</div>
</body>
</html>