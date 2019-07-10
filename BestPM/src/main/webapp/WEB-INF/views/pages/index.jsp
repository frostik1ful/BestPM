<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<div class="container">
	<c:if test="${pageContext.request.userPrincipal.name == null}">
		<div class="jumbotron" style="text-align: center; margin-top:10%">
			<h1 style="text-align: center">Добро пожаловать</h1>
			<img src="/resources/images/logo.png" class="rounded"
				style="height: 260px; width: 200px;"> <br> <br>
			<p class="lead" style="text-align: center">Войдите или
				зарегистрируйтесь.</p>
			<div style="text-align: center">
				<a class="btn btn-lg btn-success"
					href="<c:url value="/user/login" />" role="button">Войти</a> <a
					class="btn btn-lg btn-danger" href="<c:url value="/user/add" />"
					role="button">Регистрация</a>
			</div>
			<br>
			<h4 class="text-center">
			<a href="${contextPath}/authors">О проекте</a>
			</h4>
		</div>
	</c:if>

	<c:if test="${userType eq 'admin'}">
		<div class="card-body" style="padding-left: 0;">
			<a class="nav-link"
				href="${pageContext.request.contextPath}/user/listOfUsers">Список
				пользователей</a> <a class="nav-link"
				href="${pageContext.request.contextPath}/programmer/new">Добавить
				программиста</a> <a class="nav-link"
				href="${pageContext.request.contextPath}/project/new">Добавить
				проект</a> <a class="nav-link"
				href="${pageContext.request.contextPath}/motivator/new">Добавить
				мотиватор </a> <a class="nav-link"
				href="${pageContext.request.contextPath}/motivator/all">Все
				мотиваторы</a> <a class="nav-link"
				href="${pageContext.request.contextPath}/event/new">Добавить
				событие</a> <a class="nav-link"
				href="${pageContext.request.contextPath}/project/all">Все
				проекты</a> <a class="nav-link"
				href="${pageContext.request.contextPath}/programmer/all/programmers">Все
				программисты</a> <a class="nav-link"
				href="${pageContext.request.contextPath}/credit/new">Кредиты </a> <a
				class="nav-link"
				href="${pageContext.request.contextPath}/programmer/market">Маркет
				программистов</a> <a class="nav-link"
				href="${pageContext.request.contextPath}/motivator/market">Рынок
				мотиваторов</a> <a class="nav-link"
				href="${pageContext.request.contextPath}/user/myMotivators">Мои
				мотиваторы</a> <a class="nav-link"
				href="${pageContext.request.contextPath}/projects/home">Рынок
				проектов</a> <a class="nav-link"
				href="${pageContext.request.contextPath}/userProjects/">Мои
				проекты</a> <a class="nav-link"
				href="${pageContext.request.contextPath}/user/myProgrammers">Мои
				программисты</a> <a class="nav-link"
				href="${pageContext.request.contextPath}/office/myOffice">Мой
				офис</a>
		</div>
	</c:if>
</div>





