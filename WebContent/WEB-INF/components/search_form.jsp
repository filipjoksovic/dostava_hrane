<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="container my-5">
	<form method="GET" action="restaurantSearch">
		<div class="d-flex w-100 mx-auto justify-content-around flex-wrap align-items-end">
			<div class="form-group flex-grow-1 p-2">
				<c:if test="${!restaurantSearch.isEmpty()}">
					<label for="restaurantSearch">Naziv restorana</label>
				</c:if>
				<input id="restaurant_search" placeholder="Naziv restorana"
					type="text" name="restaurantSearch" class="form-control"
					value="${restaurantSearch}">
			</div>
			<div class="form-group flex-grow-1 p-2">
				<c:if test="${!locationSearch.isEmpty()}">
					<label for="locationSearch">Lokacija restorana</label>
				</c:if>
				<input id = "locationSearch" placeholder="Lokacija restorana" type="text"
					name="locationSearch" class="form-control"
					value="${locationSearch}">
			</div>
			<div class="form-group flex-grow-1 p-2">
				<c:if test="${typeSelect != -1}">
					<label for="typeSelect">Tip restorana</label>
				</c:if>
				<select id = "typeSelect" name="typeSelect" class="form-control">
					<option disabled selected value=-1>Tip proizvoda</option>
					<c:forEach var="type_item" items="${restaurantTypes}">
						<option value="${type_item.getID()}" <c:if test = "${typeSelect == type_item.getID()}">selected</c:if>>${type_item.getTitle() }</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group flex-grow-1 p-2">
				<c:if test="${!minRate.toString().isEmpty()}">
					<label for="minRate">Minimalna ocena</label>
				</c:if>
				<input id = "minRate" name="minRate" class="form-control"
					placeholder="Minimalna ocena" value="${minRate}">
			</div>
			<div class="form-group flex-grow-1 p-2">
				<button type="submit" class="btn btn-success btn-block">
					<i class="fas fa-search"></i> Pretrazi
				</button>
			</div>
		</div>
	</form>
</div>
