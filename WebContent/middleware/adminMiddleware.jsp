<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id = "user" type = "bean.User" scope = "session"/>
<c:if test = '${user.getRoleID() != 1 }'>
		<%
			response.sendRedirect(request.getContextPath() + "/");
			request.getSession().setAttribute("error","Morate biti administrator kako biste pristupili ovom delu sajta");
		%>
</c:if>