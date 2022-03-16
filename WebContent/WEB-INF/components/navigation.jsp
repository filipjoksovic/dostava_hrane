<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Dodajem JSTL deklaraciju, kako bi koristili JSTL i EL --%>


<jsp:useBean id="user" type="bean.User" scope="session" />
<jsp:useBean id="cart" type="dao.CartDAO" scope="session" />

<nav
	class="navbar navbar-expand-lg navbar-light bg-light justify-content-between">
	<a class="navbar-brand" href="/dostava_hrane/">Dostava hrane</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarSupportedContent"
		aria-controls="navbarSupportedContent" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav mr-auto justify-content-between w-100">

			<c:if test="${not empty user.username}">
				<div class="d-flex">
					<li class="nav-item active"><a class="nav-link"
						href="/dostava_hrane/">Pocetna <span class="sr-only">(current)</span></a></li>
					<li class='nav-item'><a class="nav-link" href="account">Moj
							nalog</a></li>
					<c:if test="${user.roleID == 1}">
						<li class="nav-item"><a href="admin" class="nav-link">Kontrolna
								tabla</a></li>
					</c:if>
					<c:if test="${user.roleID == 2 }">
						<li class="nav-item"><a href="seller" class="nav-link">Kontrolna
								tabla</a></li>
					</c:if>
				</div>
				<div class=" d-flex">
					<li class='nav-item '><a class="nav-link" href="cart">Korpa(${cart.getCartCount()})</a></li>
					<li class='nav-item'><a class="nav-link" href="LogoutUser">${user.username}
							- Odjavi se</a></li>
				</div>
			</c:if>

			<c:if test="${empty user.username}">
				<div class="d-flex ml-auto">
					<li class="nav-item ml-auto"><a class="nav-link" href="login">Ulogujte
							se</a></li>
					<li class="nav-item ml-auto"><a class="nav-link"
						href="register">Registrujte se</a></li>
				</div>
			</c:if>
		</ul>

	</div>
</nav>

<div>

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
</div>
	<c:set var="req" value="${pageContext.request}" />
<c:set var="uri" value="${req.requestURI}" />
<c:if test= "${uri.contains('WEB-INF/index.jsp')}">
<jsp:include page="search_form.jsp"></jsp:include>
</c:if>
	