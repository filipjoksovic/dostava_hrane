<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Ocena restorana</title>
<%@ include file="components/style.jsp"%>
</head>
<body>
	<%@ include file="components/navigation.jsp"%>
	<c:if test="${not reviewExists}">
		<div class="container mt-5">
			<h3 class="text-center">Ostavljate ocenu za
				${restaurant.getName()}</h3>
			<h5 class="text-center">Ostavljenu ocenu menadzer ce morati da
				odobri kako bi bila vidljiva svim korisnicima.</h5>
		</div>
		<div class="container mt-5">
			<form action="rateRestaurant" method="POST">
				<input type="hidden" name="restaurant_id"
					value="${restaurant.getID()}" />
				<div class="d-flex w-75 mx-auto justify-content-around">
					<div class="form-check">
						<input class="form-check-input" type="radio" name="rate" id="pr1"
							value="1" checked>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="radio" name="rate" id="pr2"
							value="2" checked>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="radio" name="rate" id="pr3"
							value="3" checked>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="radio" name="rate" id="pr4"
							value="4" checked>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="radio" name="rate" id="pr5"
							value="5" checked>
					</div>
				</div>
				<div
					class="d-flex w-75 mx-auto mt-5 justify-content-around text-center">
					<label
						class="form-check-label btn-danger flex-grow-1 mx-1 p-1 py-2 rounded"
						for="pr1"> 1 </label> <label
						class="form-check-label btn-danger flex-grow-1 mx-1 p-1 py-2 rounded"
						for="pr2"> 2 </label> <label
						class="form-check-label btn-warning flex-grow-1 mx-1 p-1 py-2 rounded"
						for="pr3"> 3 </label> <label
						class="form-check-label btn-info flex-grow-1 mx-1 p-1 py-2 rounded"
						for="pr4"> 4 </label> <label
						class="form-check-label btn-success flex-grow-1 mx-1 p-1 py-2  rounded"
						for="pr5"> 5 </label>
				</div>
				<div class="form-group mt-3 w-50 mx-auto">
					<label for="review">Komentar</label>
					<textarea name="review" class="form-control" id="review"></textarea>
				</div>
				<button type="submit" class="btn btn-primary btn-block w-50 mx-auto">Oceni
					restoran</button>
			</form>
		</div>
	</c:if>
	<c:if test="${reviewExists}">
		<div class="container mt-5">
			<h3 class="text-center">Menjate ocenu za ${restaurant.getName()}</h3>
			<h5 class="text-center">Ostavljenu ocenu menadzer ce morati da
				odobri kako bi bila vidljiva svim korisnicima.</h5>
		</div>
		<div class="container mt-5">
			<form action="rateRestaurant" method="POST">
				<input type="hidden" name="restaurant_id"
					value="${restaurant.getID()}" />
				<div class="d-flex w-75 mx-auto justify-content-around">
					<div class="form-check">
						<input class="form-check-input" type="radio" name="rate" id="pr1"
							value="1" <c:if test = "${review.getGrade() eq 1}">checked</c:if>>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="radio" name="rate" id="pr2"
							value="2" <c:if test = "${review.getGrade() eq 2}">checked</c:if>>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="radio" name="rate" id="pr3"
							value="3" <c:if test = "${review.getGrade() eq 3}">checked</c:if>>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="radio" name="rate" id="pr4"
							value="4" <c:if test = "${review.getGrade() eq 4}">checked</c:if>>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="radio" name="rate" id="pr5"
							value="5" <c:if test = "${review.getGrade() eq 5}">checked</c:if>>
					</div>
				</div>
				<div
					class="d-flex w-75 mx-auto mt-5 justify-content-around text-center">
					<label
						class="form-check-label btn-danger flex-grow-1 mx-1 p-1 py-2 rounded"
						for="pr1"> 1 </label> <label
						class="form-check-label btn-danger flex-grow-1 mx-1 p-1 py-2 rounded"
						for="pr2"> 2 </label> <label
						class="form-check-label btn-warning flex-grow-1 mx-1 p-1 py-2 rounded"
						for="pr3"> 3 </label> <label
						class="form-check-label btn-info flex-grow-1 mx-1 p-1 py-2 rounded"
						for="pr4"> 4 </label> <label
						class="form-check-label btn-success flex-grow-1 mx-1 p-1 py-2  rounded"
						for="pr5"> 5 </label>
				</div>
				<div class="form-group mt-3 w-50 mx-auto">
					<label for="review">Komentar</label>
					<textarea name="review" class="form-control" id="review">${review.getComment()}</textarea>
				</div>
				<button type="submit" class="btn btn-primary btn-block w-50 mx-auto">Oceni
					restoran</button>
		</form>

		</div>
		<div class="container mt-3">
			<form method="POST" action="deleteReview">
				<input type="hidden" name="restaurant_id"
					value="${review.getRestaurantID()}">
				<button type="submit" class="btn btn-danger btn-block w-50 mx-auto">Ukloni
					ocenu</button>
			</form>
		</div>
	</c:if>
</body>
</html>