<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container">
	<c:if test="${not empty message }">
		<div class="alert alert-success" role="alert">${message }</div>
	</c:if>

	<c:if test="${not empty error }">
		<div class="alert alert-danger" role="alert">${error}</div>
	</c:if>
	<a class="btn btn-outline-secondary"
		href="${pageContext.request.contextPath}/programmer/market">На
		рынок программистов</a><a class="btn btn-outline-secondary"
		href="${pageContext.request.contextPath}/user/myProgrammers">Мои
		программисты</a>
</div>