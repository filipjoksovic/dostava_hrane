<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<%@ include file="components/style.jsp"%>
</head>
<body>
	<%@ include file="components/navigation.jsp"%>
	<c:if test='${not empty sessionScope["error"]}'>
		<div
			class="alert alert-danger text-center alert-dismissible fade show"
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
	<div class="container mt-5">
		<h3 class="text-center">Popunite podatke u formi kako biste se
			kreirali Vas nalog.</h3>
		<form action="register" method="POST">
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
							name="pword" id="pword" class="form-control">
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


			<p class="text-center">
				Vec imate nalog? Prijavite se <a href="login.jsp">ovde</a>.
			</p>
			<button type="submit" class="btn btn-primary d-block w-50 mx-auto">Prijavi
				se</button>
		</form>
	</div>
</body>
</html>