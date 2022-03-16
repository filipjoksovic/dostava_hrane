<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.User"%>
<%@page import="bean.UserType"%>
<%@page import="dao.RoleDAO"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Prikaz korisnika</title>
<%@ include file="components/style.jsp"%>
</head>
<body>
	<%@ include file="components/navigation.jsp"%>

	<div class="container my-5">
		<h2 class="text-center mb-5">Pretrazite korisnike radi lakseg
			pregleda</h2>
		<form method="GET" action="listUsers">
			<div class="row align-items-end">
				<div class="col-md-9">
					<div class="form-group">
						<label for="search_text">Korisnicko ime, ime, prezime...</label> <input
							name="search_text" value="${search_text}" id="search_text"
							class="form-control">
					</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
						<button type="submit"
							class="btn btn-outline-primary mx-auto btn-block w-50">Pretrazi</button>
					</div>
				</div>
			</div>
				<div class="row align-items-center">
					<div class="col-md-3">
						<p class="text-center">Tipovi korisnika</p>
						<div
							class="d-flex w-100 flex-column justify-content-between flex-wrap">
							<c:forEach var="user_type" items="${user_types}">
								<div class="form-check form-check-inline p-2 border m-1 rounded">
									<input class="form-check-input" type="checkbox" name = "user_types"
										id="${user_type.getTitle()}" value="${user_type.getTitle()}" <c:if test = "${checkedTypes.contains(user_type.getTitle())}">checked</c:if>>
									<label class="form-check-label" for="${user_type.getTitle()}">${user_type.getTitle()}</label>
								</div>
							</c:forEach>
						</div>
					</div>
					<div class="col-md-3">
						<p class="text-center">Uloge korisnika</p>
						<div
							class="d-flex w-100 flex-column  justify-content-between flex-wrap">
							<c:forEach var="user_role" items="${user_roles}">
								<div class="form-check form-check-inline p-2 border m-1 rounded">
									<input class="form-check-input" type="checkbox" name = "user_roles"
										id="${user_role.getRoleID()}" value="${user_role.getRoleID()}" <c:if test = "${checkedRoles.contains(String.valueOf(user_role.getRoleID()))}">checked</c:if>>
									<label class="form-check-label" for="${user_role.getRoleID()}">${user_role.getRoleTitle()}</label>
								</div>
							</c:forEach>
						</div>
					</div>
					<div class="col-md-6">
						<div class="row">
							<div class="col-md-8">
								<div class="form-group">
									<label for="filterSort">Sortiraj po</label> <select
										id="filterSort" name="sort_filter" class="form-control">
										<option value = "fname" <c:if test = "${sFilter.equals('fname')}">selected</c:if>>Ime</option>
										<option value = "lname" <c:if test = "${sFilter.equals('lname')}">selected</c:if>>Prezime</option>
										<option value = "uname" <c:if test = "${sFilter.equals('uname')}">selected</c:if>>Korisnicko ime</option>
										<option value = "points" <c:if test = "${sFilter.equals('points')}">selected</c:if>>Broj bodova</option>
									</select>
								</div>
							</div>
							<div class = "col-md-4">
									<label for="filterSort">Redosled</label> <select
										id="filterSort" name="sort_order" class="form-control">
										<option value = "asc" <c:if test = "${sOrder.equals('asc')}">selected</c:if>>Rastuce</option>
										<option value = "desc" <c:if test = "${sOrder.equals('desc')}">selected</c:if>>Opadajuce</option>
									</select>
							</div>
						</div>
					</div>
				</div>
		</form>
	</div>

	<div class="container mt-3">
		<h2 class="text-center">Prikaz kreiranih korisnika</h2>
		<div class="row">
			<c:forEach var="user_item" items="${users}">
				<c:if
					test="${not user_item.getUsername().equals(user.getUsername())}">
					<div class="col-md-6">
						<div class="card my-2">
							<div class="card-header">${user_item.getUsername()}</div>
							<div class="card-body">
								<div class="row">
									<div class="col-md-6 text-center">
										<p>Ime: ${user_item.getFirstName()}</p>
										<p>Prezime: ${user_item.getLastName()}</p>
										<p>Datum rodjenja: ${user_item.getDateOfBirth()}</p>
									</div>
									<div class="col-md-6 text-center">
										<p>Pol: ${user_item.getGender()}</p>
										<p>Uloga: ${user_item.getRoleTitle()}</p>
										<p>Rank korisnika: ${user_item.getUserType()} -
											${userPointsDAO.getUserPoints(OrderPointsDAO.getUsername())}
											bodova</p>
									</div>
								</div>
							</div>
							<div
								class="card-footer d-flex w-100 justify-content-around flex-wrap ">
								<button class="btn btn-danger"
									onclick="location.href = 'deleteUser?username=${user_item.getUsername()}'">Ukloni
									korisnika</button>
								<c:if
									test="${not user_item.getBlocked() && user_item.getRoleID() != 1}">
									<button class="btn btn-warning"
										onclick="location.href = 'blockUser?username=${user_item.getUsername()}'">Blokiraj
										korisnika</button>
								</c:if>
								<c:if test="${user_item.getBlocked()}">
									<button class="btn btn-success"
										onclick="location.href = 'unblockUser?username=${user_item.getUsername()}'">Odblokiraj
										korisnika</button>
								</c:if>
							</div>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</div>
	</div>

</body>
</html>