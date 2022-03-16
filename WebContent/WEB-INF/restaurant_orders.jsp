<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Pregled porudzbina</title>
<%@ include file="components/style.jsp"%>
</head>
<body>
	<%@ include file="components/navigation.jsp"%>

	<div class="container">
		<h3 class="text-center">Pregled porudzbina za Vas restoran</h3>
		<h5 class="text-center">Pregled porudzbina</h5>
		<div class="order-list">
			<div class="row">
				<c:forEach var="order" items="${groupedOrders}">
					<div class="col-md-6">
						<div class="card order my-2">
							<div class="card-header">
								<div
									class="w-100 d-flex justify-content-between align-items-center">
									<div>
										<p>${order.getID()}-${order.getUsername()}</p>
										<p>${order.getTimestamp() }
									</div>
									<div
										class="d-flex flex-column justify-content-between align-items-center">
										<c:if test="${order.getStatusID() < 2}">
											<form method="post" action="orderProceed">
												<input type="hidden" name="order_id"
													value="${order.getID()}">
												<button class="btn btn-info">${order.getStatus()}
													<i class="fas fa-arrow-right"></i>
													${orderStatusDAO.getNextStatus(order.getID()) }
												</button>
											</form>
										</c:if>
										<c:if test="${order.getStatusID() >= 2 }">
											<c:if test = "${order.getStatusID() < 5}">
												<button disabled class="btn btn-info my-1">${order.getStatus()}
												</button>
											</c:if>
											<c:if test="${order.getStatusID() == 5}">
												<button disabled class="btn btn-danger my-1">${order.getStatus()}
												</button>
											</c:if>
											<c:set var="deliveryRequests"
												value="${deliveryRequestDAO.getDeliveryRequests(order)}" />
											<c:if
												test="${deliveryRequests.size() > 0 && not deliveryRequestDAO.isGranted(order)}">
												<button class="btn btn-warning my-1"
													onclick="location.href='getDeliveryRequests?id=${order.getID()}'">
													${deliveryRequests.size()} zahteva dostave</button>
											</c:if>
										</c:if>
									</div>
								</div>
							</div>
							<div class="card-body">
								<c:forEach var="order_item" items="${order.getOrderItems()}">
									<c:set var="product" scope="request"
										value="${productDAO.getProductByID(order_item.getProductID())}" />
									<div class="card order-item my-1">
										<div class="card-body">
											<div class="row">
												<div class="col-md-3">
													<div>
														<img class="product-image"
															src="data:image/jpeg;base64,${productDAO.getProductImage(product) }">
													</div>
												</div>
												<div class="col-md-7">
													<p>Artikal: ${product.getName()}</p>
													<p>Kolicina: ${order_item.getProductQuantity() }</p>
												</div>


											</div>
										</div>
									</div>
								</c:forEach>
							</div>
							<div class="card-footer">
								<div>
									<h5>
										Podaci o poruciocu
										</h6>
										<h6>Ime : ${order.getFirstName()} ${order.getLastName()}</h6>
										<h6>Lokcaija: ${order.getAddress()}, ${order.getCity()},
											${order.getCountry()}</h6>
								</div>
								<div>
									<c:if test="${deliveryRequestDAO.isGranted(order)}">
										<button disabled class="btn btn-success btn-block">Preuzima:
											${deliveryRequestDAO.getDeliverer(order)}</button>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
</body>
</html>