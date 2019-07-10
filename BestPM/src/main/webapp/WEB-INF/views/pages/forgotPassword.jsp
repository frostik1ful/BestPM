<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<form:form action="forgotPassword" method="post" class="form-signin"
	style="width: 350px; margin-top:10%">
	<h2 class="form-signin-heading" style="text-align: center">Введите
		адрес электронной почты:</h2>
	<br>
	<input type="text" name="email" class="form-control"
		placeholder="Электронная почта" autofocus="true">
	<c:if test="${not empty error}">
		<c:choose>

			<c:when test="${error == 'mailWasNotFound'}">
				<br>	
				Почта введена не верно или не найдена
				<br>
			</c:when>
			<c:when test="${error == 'outdatedToken'}">
				<br>	
				Время действия ссылки восстановления пароля истекло
				<br>
			</c:when>
			<c:when test="${error == 'wrongToken'}">
				<br>	
				Неверная ссылка восстановления пароля
				<br>
			</c:when>
		</c:choose>
	</c:if>

	<br>
	<button class="btn btn-lg btn-primary btn-block" type="submit">Выслать
		письмо восстановления</button>
</form:form>




