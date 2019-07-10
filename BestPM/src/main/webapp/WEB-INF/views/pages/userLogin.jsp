<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<form method="POST" action="${contextPath}/user/login"
	class="form-signin" style="width: 350px; margin-top:10%">
	<h2 class="form-heading" style="text-align: center; margin-top:20%;">Вход</h2>

	<div class="form-group ${error != null ? 'has-error' : ''}" style="margin:auto;">
		<span>${message}</span> <input name="username" type="text"
			class="form-control" placeholder="Имя пользователя" autofocus="true"/> <br> <input
			name="password" type="password" class="form-control"
			placeholder="Пароль" /> <span>${error}</span> <br> <input
			type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

		<button class="btn btn-lg btn-primary btn-block" type="submit">Вход</button>
		<br>
		<h4 class="text-center">
			<a href="${contextPath}/user/forgotPassword">Забыли пароль?</a>
		</h4>
  		<br>
		<h4 class="text-center">
			<a href="${contextPath}/user/add">Создать пользователя</a>
		</h4>
	</div>
</form>
