<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<c:if test="${pageContext.request.userPrincipal.name != null}">
	<form id="logoutForm" method="GET" action="${contextPath}/user/logout">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>

	<h2>
		Welcome ${pageContext.request.userPrincipal.name} | <a
			onclick="document.forms['logoutForm'].submit()">Logout</a>
	</h2>

</c:if>

<!-- Building table from model -->
<div class="table-responsive">
				<table  class="table table-striped table-bordered">
	<thead style="background: #4af">
		<tr>
			<th>Название</th>
			<th>Дата</th>
			<th>Стоимость</th>
			<th>Сложность</th>
			<th>Тип оплаты</th>
			<th>Взять проект</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${projectsSet}" var="project">

			<td><c:out value="${project.name}" /></td>
			<td><c:out value="${project.date}" /></td>
			<td><c:out value="${project.money}" /></td>
			<td><c:out value="${project.difficult}" /></td>
			<td><c:out value="${project.typeOfPayment}" /></td>

			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
