<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin registracija</title>
<%@ include file="components/style.jsp"%>
<%@ page import="bean.UserType"%>
<%@ page import="bean.Role"%>
<%@ page import="java.util.ArrayList"%>
</head>
<body>
	<%@ include file="components/navigation.jsp"%>
	<%
	ArrayList<UserType> userTypes = (ArrayList<UserType>) request.getAttribute("userTypes");
	ArrayList<Role> roleTypes = (ArrayList<Role>) request.getAttribute("roleTypes");
	%>

	<div class="container mt-3">
		<h2 class="text-center">Kreiranje novog korisnika</h2>
		<form action="adminRegister" method="POST">
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
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="roleSelect">Uloga korisnika</label> <select
							class="form-control" name="userRole" id="roleSelect">
							<c:forEach var="roleType" items="${roleTypes}">
								<option value="${roleType.getRoleID()}">${roleType.getRoleTitle()}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="rankSelect">Nivo korisnika</label> <select
							class="form-control" name="userRank" id="rankSelect">
							<c:forEach var="userType" items="${userTypes}">
								<option value="${userType.getTitle()}">${userType.getTitle()}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			<button type="submit" class="btn btn-primary d-block w-50 mx-auto">Prijavi
				se</button>
		</form>
	</div>


</body>
</html>