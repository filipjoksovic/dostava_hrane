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
	<div class="container mt-5">
		<h3 class="text-center">Zahtevi za dostavu za porudzbinu
			${orderID}</h3>
		<h5 class="text-center">Odobrenjem jednog zahteva za dostavu
			brisu se svi ostali zahtevi</h5>

		<div class="order-requests row mt-5">
			<c:forEach var="request" items="${requests}">
				<div class="col-md-6">
					<div class="card text-center">
						<div class="card-header">${request.getUsername()}</div>
						<div class="card-footer">
							<form method="POST" action="getDeliveryRequests">
								<input type="hidden" name="order_id" value="${orderID}">
								<input type="hidden" name="username"
									value="${request.getUsername()}">
								<button class="btn btn-success btn-block" type="submit">Odobri
									porudzbinu</button>
							</form>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>