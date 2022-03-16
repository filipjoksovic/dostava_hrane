<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Kreiranje restorana</title>
<%@ include file="components/style.jsp"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.RestaurantType"%>
<%@page import="dao.RestaurantTypeDAO"%>
</head>
<body>
	<%
	ArrayList<RestaurantType> restaurants = (ArrayList<RestaurantType>) request.getAttribute("restaurantTypes");
	%>
	<%@ include file="components/navigation.jsp"%>
	<c:if test='${not empty sessionScope["error"]}'>
		<div
			class="alert alert-success text-center alert-dismissible fade show"
			role="alert">
			${sessionScope['error']}
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
		<%
		session.setAttribute("error", null);
		%>
	</c:if>
	<div class="container mt-3">
		<h2 class="text-center">Kreirajte novi restoran</h2>
		<form method="POST" action="createRestaurant"
			enctype="multipart/form-data">
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="resName">Naziv restorana</label> <input
							class="form-control" id="resName" name="name">
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="resType">Tip restorana</label> <select
							class="form-control" id="resType" name="type">
							<c:forEach var="type" items="${restaurantTypes}">
								<option value="${type.getID()}">${type.getTitle()}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class = "col-md-4">
					<label for = "city">Grad</label>
					<input class = "form-control" name = "city" id = "city">
				</div>
				<div class="col-md-4">
					<label for="address">Adresa</label> <input class="form-control"
						name="address" id="address">
				</div>
				<div class="col-md-4">
					<label for="zip">Postanski broj</label> <input class="form-control"
						name="zip" id="zip">
				</div>
			</div>

			<div class="input-group mb-3 mt-3">
				<div class="input-group-prepend">
					<span class="input-group-text">Logo restorana</span>
				</div>
				<div class="custom-file">
					<input type="file" class="custom-file-input" name="logo" id="logo">
					<label class="custom-file-label" for="logo">Odaberite sliku</label>
				</div>
			</div>
			<c:if test="${not empty availableManagers}">
				<div class="form-group">
					<label for="managerSelect">Odabir menadzera</label> <select
						id="managerSelect" name="manager" class="form-control">
						<c:forEach var="manager" items="${availableManagers}">
							<option value="${manager.getUsername()}">${manager.getUsername()}</option>
						</c:forEach>
					</select>
				</div>
			</c:if>
			<c:if test="${empty availableManagers}">
				<h6 class="text-center">Ni jedan slobodan menadzer nije
					dostupan. Kreirajte menadzera za ovaj restoran popunjavanjem
					podataka forme.</h6>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label for="fname">Ime</label> <input type="text" name="fname"
								id="fname" class="form-control">
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label for="lname">Prezime</label> <input type="text"
								name="lname" id="lname" class="form-control">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label for="uname">Korisnicko ime</label> <input type="text"
								name="uname" id="uname" class="form-control">
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label for="pword">Lozinka</label> <input type="password"
								name="password" id="pword" class="form-control">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label for="gender">Pol</label> <select name="gender" id="gender"
								class="form-control">
								<option value="m">Muski</option>
								<option value="f">Zenski</option>
								<option value="o">Drugo</option>
								<option value="n">Radije se ne bih izjasnio/la</option>
							</select>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label for="dob">Datum rodjenja</label> <input
								class="form-control" type="date" name="dob" id="dob">
						</div>
					</div>
				</div>
				<input name="roleSelect" value="2" type="hidden">
				<input name="rankSelect" value="Standardni" type="hidden">
				<input name = "createManager" value = "1" type = "hidden">
			</c:if>
			<button type="submit" class="btn btn-primary">Posalji
				podatke</button>
		</form>
	</div>
</body>
</html>