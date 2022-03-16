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
			<div onclick="location.href = 'listUsers'" class="action">
				<div class="action-icon">
					<i class="fas fa-users"></i>
				</div>
				<div class="action-text">Pregled korisnika</div>
			</div>
			<div onclick = "location.href = 'adminRegister'" class="action">
				<div class="action-icon">
					<i class="fas fa-plus"></i>
				</div>
				<div class="action-text">Dodavanje korisnika</div>
			</div>
			<div onclick = "location.href = 'createRestaurant'" class="action">
				<div class="action-icon">
					<i class="fas fa-plus"></i>
				</div>
				<div class="action-text">Dodavanje restorana</div>
			</div>
			<div onclick = "location.href = 'viewRestaurants'" class="action">
				<div class="action-icon">
					<i class="fas fa-list"></i>
				</div>
				<div class="action-text">Pregled restorana</div>
			</div>
			<div class = "action" onclick = "location.href = 'restaurant_reviews'">
				<div class = "action-icon">
					<i class="fas fa-eye"></i>
				</div>
				<div class = "action-text">Pregled ocena</div>
			</div>
		</div>
	</div>
</body>
</html>