<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<form:form method="post" modelAttribute="userForm" class="form-signin"
	style="width: 350px; margin-top:10%">
	<h2 class="form-signin-heading" style="text-align: center;">Регистрация</h2>
	<spring:bind path="username">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<form:input type="text" path="username" class="form-control"
				placeholder="Имя пользователя" autofocus="true"></form:input>
			<form:errors path="username"></form:errors>
		</div>
	</spring:bind>
	
	<spring:bind path="email">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<form:input type="text" path="email" class="form-control"
				placeholder="Электронная почта" autofocus="true"></form:input>
			<form:errors path="email"></form:errors>
		</div>
	</spring:bind>
	
	<spring:bind path="password">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<form:input type="password" path="password" class="form-control"
				placeholder="Пароль"></form:input>
			<form:errors path="password"></form:errors>
		</div>
	</spring:bind>

	<spring:bind path="confirmPassword">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<form:input type="password" path="confirmPassword"
				class="form-control" placeholder="Подтвердите пароль"></form:input>
			<form:errors path="confirmPassword"></form:errors>
		</div>
	</spring:bind>

	<button class="btn btn-lg btn-primary btn-block" type="submit">Зарегистрировать</button>

</form:form>

