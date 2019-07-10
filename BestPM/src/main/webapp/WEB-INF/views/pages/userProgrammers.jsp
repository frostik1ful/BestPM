<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="count" value="0"></c:set>
<c:set var="totalSume" value="0"></c:set>
<c:if test="${not empty succsess }">
	<div class="alert alert-success alert-dismissible fade show"
		role="alert">
		<span>${succsess }</span>
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>
</c:if>

<c:if test="${not empty error }">
	<div class="alert alert-danger alert-dismissible fade show"
		role="alert">
		<span>${error}</span>
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>
</c:if>
<div class="form-group">
	<input type="text" class="form-control pull-right" id="search"
		placeholder="Поиск по таблице">
</div>
<div class="table-responsive">
	<table id="mytable" class="table table-hover">
		<thead>
			<tr>
				<th scope="col">Имя</th>
				<th scope="col">Зарплата</th>
				<th scope="col">Рейтинг</th>
				<th scope="col">Мотивация</th>
				<th scope="col">Продуктивность</th>
				<th scope="col">До повышения</th>
				<th scope="col">Проект</th>
				<c:if test="${not empty projects}">
					<th scope="col">Ваши проекты</th>
					<th scope="col"></th>
				</c:if>
				<c:if test="${empty projects}">
					<th scope="col"></th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="programmer" items="${programmers }">
				<tr>
					<th scope="row">${programmer.name }</th>
					<td>${programmer.money }</td>
					<td>${programmer.rating }</td>
					<td>${programmer.motivation }</td>
					<td>${programmer.productivity }</td>
					<td><c:choose>
							<c:when test="${programmer.rating == 'JUNIOR'}">
								<c:set var="percent"
									value="${(programmer.experience * 100)/1000 }"></c:set>
								<div class="progress">
									<div class="progress-bar bg-success text-dark"
										role="progressbar" style="width: ${percent }%"
										aria-valuenow="${percent }" aria-valuemin="0"
										aria-valuemax="100">
										<fmt:formatNumber type="number" minFractionDigits="2"
											value="${percent }" />
										%
									</div>
								</div>
							</c:when>
							<c:when test="${programmer.rating == 'MIDDLE'}">
								<c:set var="percent"
									value="${(programmer.experience * 100)/2000 }"></c:set>
								<div class="progress">
									<div class="progress-bar bg-success text-dark"
										role="progressbar" style="width: ${percent }%"
										aria-valuenow="${percent }" aria-valuemin="0"
										aria-valuemax="100">
										<fmt:formatNumber type="number" minFractionDigits="2"
											value="${percent }" />
										%
									</div>
								</div>
							</c:when>
							<c:when test="${programmer.rating == 'SENIOR'}">
								<c:set var="percent"
									value="${(programmer.experience * 100)/3000 }"></c:set>
								<div class="progress">
									<div class="progress-bar bg-success text-dark"
										role="progressbar" style="width: ${percent }%"
										aria-valuenow="${percent }" aria-valuemin="0"
										aria-valuemax="100">
										<fmt:formatNumber type="number" minFractionDigits="2"
											value="${percent }" />
										%
									</div>
								</div>
							</c:when>
							<c:when test="${programmer.rating == 'TEAMLEAD'}">
								<div class="progress">
									<div class="progress-bar bg-success text-dark"
										role="progressbar" style="width: 100%" aria-valuenow="100"
										aria-valuemin="0" aria-valuemax="100">100%</div>
								</div>
							</c:when>
						</c:choose></td>
					<c:if test="${not empty programmer.project.name }">
						<td>${programmer.project.name }</td>
					</c:if>
					<c:if test="${empty programmer.project.name }">
						<td>Не работает</td>
					</c:if>
					<c:if test="${not empty projects}">
						<td><form id="formAppoint_${count }" method="post"
								action="${pageContext.request.contextPath}/programmer/appoint">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" /> <input type="hidden"
									value="${programmer.id}" name="programmerId">
							</form>
							<form id="formFire_${count }" method="post"
								action="${pageContext.request.contextPath}/programmer/fire">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" /> <input type="hidden"
									value="${programmer.id}" name="programmerId">
							</form>
							<div class="input-group">
								<select form="formAppoint_${count }" class="custom-select"
									name="projectId">
									<option value="0">Без работы</option>
									<c:forEach var="project" items="${projects }">
										<c:choose>
											<c:when test="${project.name  eq programmer.project.name}">
												<option value="${project.id }" selected="selected">${project.name }</option>
											</c:when>
											<c:otherwise>
												<option value="${project.id }">${project.name }</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
								<div class="input-group-append">
									<button form="formAppoint_${count }" type="submit"
										class="btn btn-outline-secondary">Назначить</button>
									<button type="button" class="btn btn-outline-danger"
										data-toggle="modal" data-target="#confirmFire_${count }">Уволить</button>
								</div>
							</div></td>
					</c:if>
					<c:if test="${empty projects }">
						<td>
							<form id="formFire_${count }" method="post"
								action="${pageContext.request.contextPath}/programmer/fire">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" /> <input type="hidden"
									value="${programmer.id}" name="programmerId">
							</form>
							<button type="button" class="btn btn-outline-danger"
								data-toggle="modal" data-target="#confirmFire_${count }">Уволить</button>
						</td>
					</c:if>
				</tr>
				<!-- Modal -->
				<div class="modal fade" id="confirmFire_${count }" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalCenterTitle"
					aria-hidden="true">
					<div class="modal-dialog modal-dialog-centered" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalCenterTitle">Подтвердить
									увольнение</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<span>Вы уверены что хотите уволить программиста</span><br>
								<p class="font-weight-bold text-danger">${programmer.name }</p>
							</div>
							<div class="modal-footer">
								<div class="container">
									<button type="button" class="btn btn-outline-secondary"
										data-dismiss="modal">Отменить</button>
									<button form="formFire_${count }" type="submit"
										class="btn btn-outline-danger">Подтвердить</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<c:set var="totalSume" value="${totalSume + programmer.money}"></c:set>
				<c:set var="count" value="${count + 1 }"></c:set>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td class="font-weight-bold" scope="row">Итого:</td>
				<td class="font-weight-bold">${totalSume }</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<c:if test="${not empty projects}">
					<td></td>
					<td></td>
				</c:if>
				<c:if test="${empty projects}">
					<td></td>
				</c:if>
			</tr>
		</tfoot>
	</table>
</div>
<script>
	$(document).ready(
			function() {
				$("#search").keyup(
						function() {
							_this = this;
							$.each($("#mytable tbody tr"), function() {
								if ($(this).text().toLowerCase().indexOf(
										$(_this).val().toLowerCase()) === -1) {
									$(this).hide();
								} else {
									$(this).show();
								}
							});
						});
			});
</script>