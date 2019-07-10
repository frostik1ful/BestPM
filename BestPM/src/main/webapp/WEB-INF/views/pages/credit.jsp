<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container">
	<ul class="nav nav-tabs">
		<li class="nav-item"><a
			class="nav-link ${activeFirst } bg-primary text-white"
			href="${pageContext.request.contextPath}/credit/new?active=1">Взять
				кредит</a></li>
		<li class="nav-item"><a
			class="nav-link ${activeSecond } bg-primary text-white"
			href="${pageContext.request.contextPath}/credit/new?active=0">Мои
				кредиты</a></li>
	</ul>
	<div class="table-responsive">
		<table class="table table-striped table-bordered">

			<thead>
				<tr>

					<th scope="col">Сумма кредита</th>
					<th scope="col">Банк</th>
					<th scope="col">Кредитный рейтинг</th>
					<th scope="col">Кредитный рейтинг изменится на</th>
					<th scope="col">Ставка</th>
					<th scope="col">Время возврата</th>
					<th scope="col">Действие</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach var="credit" items="${credits}">
					<tr>
						<td>${credit.amount}</td>
						<td>${credit.bankName}</td>
						<td>${credit.creditRating}</td>
						<td>${credit.creditRatingChange}</td>
						<td>${credit.percent}</td>
						<td>${credit.timeToReturn}</td>
						<td><c:if test="${credit.id == 0}">
								<form action="${pageContext.request.contextPath}/credit/claim">

									<input type="hidden" name="amount" value="${credit.amount}">
									<input type="hidden" name="rating"
										value="${credit.creditRating}"> <input type="hidden"
										name="ratingChange" value="${credit.creditRatingChange}">
									<input type="hidden" name="time" value="${credit.timeToReturn}">
									<input type="hidden" name="percent" value="${credit.percent}">
									<input type="hidden" name="bankName" value="${credit.bankName}">
									<input type="hidden" name="userName"
										value="${pageContext.request.userPrincipal.name}">

									<button type="submit" style="width: 100px"
										class="btn btn-danger"
										<c:forEach var="creditsUser" items="${creditsUser}">
										<c:if test="${creditsUser.bankName eq credit.bankName}"> disabled="disabled"</c:if> 
										</c:forEach>>взять</button>

								</form>
							</c:if> <c:if test="${credit.id != 0}">
								<form action="${pageContext.request.contextPath}/credit/return">
									<input type="hidden" name="id" value="${credit.id}">
									<button type="submit" style="width: 100px"
										class="btn btn-danger">Вернуть</button>
								</form>
							</c:if></td>
					</tr>

				</c:forEach>
			</tbody>


		</table>
	</div>

</div>

