<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<form:form action="updatePassword" method="post" class="form-signin"
	style="width: 350px; margin-top:10%">
	<h2 class="form-signin-heading" style="text-align: center">Введите новый пароль:</h2>
			<br>
			 <input type="password" name="newPassword" class="form-control"
				placeholder="Новый пароль" autofocus="true">
			<br>
			 <input type="password" name="newPasswordConfirm" class="form-control"
				placeholder="Повторите пароль" autofocus="false">
			<br>
				<c:if test="${not empty error}">
					Длина пароля должна быть от 6 до 32 символов
					Пароли должны совпадать
					<br>
					<br>
				</c:if>
			<!-- hidden userToken from model-->
			<input type="hidden" name="userToken" value="${userToken}">
			
	<button class="btn btn-lg btn-primary btn-block" type="submit">Изменить пароль</button>
</form:form>

