<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<%@ include file = "components/style.jsp" %>
</head>
<body>
	<%@ include file = "components/navigation.jsp" %>
	
	<c:if test='${not empty sessionScope["error"]}'>
		<div class="alert alert-danger text-center alert-dismissible fade show" role="alert">
  			${sessionScope['error']}
  			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  			</button>
		</div>
		<% session.setAttribute("error",null); %>
	</c:if>
	
	<div class = "container mt-5">
		<h3 class = "text-center">Popunite podatke u formi kako biste se prijavili na Vas nalog.</h3>
		<form action = "login" method = "POST">
		  <div class="form-group">
		    <label for="username">Korisnicko ime</label>
		    <input type="username" class="form-control" name = "username" id="email" aria-describedby="emailHelp">
		  </div>
		  <div class="form-group">
		    <label for="password">Lozinka</label>
		    <input type="password" name = "password" class="form-control" id="password">
		  </div>
		  <p class = "text-center">Nemate nalog? Registrujte se <a href = "register">ovde</a>.</p>
		  <button type="submit" class="btn btn-primary d-block w-50 mx-auto">Prijavi se</button>
	</form>
	</div>
</body>
</html>