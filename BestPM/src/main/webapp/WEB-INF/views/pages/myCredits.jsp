<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container">
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" href="#bank">Взять кредит</a></li>
		<li><a data-toggle="tab" href="#myCredits">Мои кредиты</a></li>

	</ul>
	<div class="tab-content">
		<div id="bank" class="tab-pane fade in active">
			<div class="table-responsive">
				<table class="table table-striped table-bordered">

					<thead>
						<tr>

							<th scope="col">Сумма кредита</th>
							<th scope="col">Кредитный рейтинг</th>
							<th scope="col">Кредитный рейтинг изменится на</th>
							<th scope="col">Процент</th>
							<th scope="col">Время возврата</th>
							<th scope="col">Действие</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="credit" items="${credits}">
							<tr>
								<td>${credit.amount}</td>
								<td>${credit.creditRating}</td>
								<td>${credit.creditRatingChange}</td>
								<td>${credit.percent}</td>
								<td>${credit.timeToReturn}</td>
								<td>
									<form action="${pageContext.request.contextPath}/credit/claim">
										<input type="hidden" name="amount" value="${credit.amount}">
										<input type="hidden" name="rating"
											value="${credit.creditRating}"> <input type="hidden"
											name="ratingChange" value="${credit.creditRatingChange}">
										<input type="hidden" name="time"
											value="${credit.timeToReturn}"> <input type="hidden"
											name="percent" value="${credit.percent}"> <input
											type="hidden" name="userName"
											value="${pageContext.request.userPrincipal.name}">
										<button type="submit" style="width: 100px"
											class="btn btn-danger">взять</button>
									</form>
								</td>
							</tr>

						</c:forEach>
					</tbody>


				</table>
			</div>
		</div>
	</div>
	<div class="tab-content">
		<div id="myCredits" class="tab-pane fade">
			<div class="table-responsive">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>

							<th scope="col">Сумма кредита</th>
							<th scope="col">Кредитный рейтинг</th>
							<th scope="col">Кредитный рейтинг изменится на</th>
							<th scope="col">Процент</th>
							<th scope="col">Время возврата</th>
							<th scope="col">Действие</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="creditTaken" items="${creditTakens}">
							<tr>
								<td>${creditTaken.amount}</td>
								<td>${creditTaken.creditRating}</td>
								<td>${creditTaken.creditRatingChange}</td>
								<td>${creditTaken.percent}</td>
								<td>${creditTaken.timeToReturn}</td>
								<td>
									<form action="${pageContext.request.contextPath}/credit/return">
										<input type="hidden" name="id" value="${creditTaken.id}">
										<button type="submit" style="width: 100px"
											class="btn btn-danger">Вернуть</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

</div>