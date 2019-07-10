<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container">
	<h1 class="display-2">Ваши мотиваторы</h1>
	<c:if test="${not empty recomendation }">
		<div class="container">
			<div class="alert alert-info alert-dismissible fade show"
				role="alert">
				<strong>${recomendation }</strong>
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</div>
	</c:if>
	<c:if test="${not empty success }">
		<div class="container">
			<div class="alert alert-info alert-dismissible fade show"
				role="alert">
				<strong>${success }</strong>
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</div>
	</c:if>
</div>
<ul class="nav nav-tabs">
	<li class="nav-item"><a
		class="nav-link ${activeFirst } bg-primary text-white"
		href="${pageContext.request.contextPath}/user/myMotivators?active=1">Активные</a></li>
	<li class="nav-item"><a
		class="nav-link ${activeSecond } bg-primary text-white"
		href="${pageContext.request.contextPath}/user/myMotivators?active=0">Неактивные</a></li>
</ul>
<table class="table">
	<thead>
		<tr>
			<th scope="col">Имя</th>
			<th scope="col">Сила</th>
			<th scope="col">Оставшейся время</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="motivator" items="${motivators }">
			<tr>
				<td>${motivator.name }</td>
				<td>${motivator.power }</td>
				<c:if test="${motivator.type == type }">
					<c:if test="${motivator.timeAction  > 0}">
						<td>${motivator.timeAction }дн.</td>
					</c:if>
					<c:if test="${motivator.timeAction eq 0}">
						<td>Окончено</td>
					</c:if>
				</c:if>
				<c:if test="${motivator.type != type }">
					<td>Постоянного действия</td>
				</c:if>
			</tr>
		</c:forEach>
	</tbody>
</table>