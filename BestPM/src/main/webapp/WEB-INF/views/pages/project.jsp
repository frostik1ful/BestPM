<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.formbox {
	width: 600px;
	height: 500px;
	border-radius: 10px;
}
</style>
<c:if test="${not empty information }">
	<div class="container">
		<div class="alert alert-info alert-dismissible fade show" role="alert">
			<strong>${information }</strong>
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</div>
</c:if>
<c:if test="${not empty editProject}">
	<div class="formbox">
		<form method="POST"
			action="${pageContext.request.contextPath}/project/saveChanges">
			<%-- CSRF for security, added by Sergey Christensen --%>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<%-- --%>

			<div class="form-group">
				<input type="hidden" name="projectId" value="${editProject}">
				<label for="name">Имя проекта:</label> <input type="text"
					required="required" name="name" value="${name}"
					class="form-control" placeholder="Введите имя для проекта"
					id="name" />
			</div>

			<div class="form-group">
				<label for="date">Срок проекта:</label> <input type="number"
					required="required" name="time" value="${time}"
					class="form-control" id="date" min="1.0" step="0.1" />
			</div>

			<div class="form-group">
				<label for="money">Финансирование:</label> <input type="number"
					required="required" name="money" value="${money}"
					class="form-control" placeholder="Сколько вы готовы инвестировать"
					id="money" />
			</div>

			<div class="form-group">
				<label for="difficult">Сложность:</label> <input type="number"
					required="required" placeholder="Выберите сложность проекта"
					name="difficult" value="${difficult}" class="form-control"
					id="difficult" min="1.0" step="0.1" />
			</div>

			<div class="form-group">
				<label for="type">Выберите тип оплаты:</label> <select
					class="form-control" name="type">
					<option value="${selectedPayType}" selected>${selectedPayType}</option>
					<c:forEach var="type" items="${payTypes}">
						<c:if test="${type ne selectedPayType }">
							<option value="${type}">${type}</option>
						</c:if>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-default">Сохранить</button>
				</div>
			</div>

		</form>
	</div>
</c:if>
<c:if test="${not empty projects and  empty editProject}">

	<%--<form action="${pageContext.request.contextPath}/project/all/projects">--%>
	<%-- CSRF for security, added by Sergey Christensen 
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />--%>
	<%-- --%>
	<%-- Add serch on table --%>
	<div class="form-group">
		<input type="text" class="form-control pull-right" id="search"
			placeholder="Поиск по проекта">
	</div>
	<%-- --%>
	<div class="table-responsive">
		<table id="escalation" class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>Имя</th>
					<th>Дата</th>
					<th>Бюджет</th>
					<th>Сложность</th>
					<th>Тип оплаты</th>
					<th colspan="2">Действие</th>


				</tr>
			</thead>
			<tbody>
				<c:forEach items="${projects}" var="project">
					<tr>
						<td>${project.name}</td>
						<td>${project.time}</td>
						<td>${project.money}</td>
						<td>${project.difficult}</td>
						<td>${project.typeOfPayment}</td>
						<td colspan="2" style="width: 50px;">
							<form action="${pageContext.request.contextPath}/project/delete"
								id="formDelete_${project.id }">
								<input type="hidden" name="projectId" value="${project.id}">
							</form>
							<form action="${pageContext.request.contextPath}/project/edit"
								id="formEdit_${project.id }">
								<input type="hidden" name="projectId" value="${project.id}">
							</form>
							<div class="input-group-append">
								<button form="formEdit_${project.id }" type="submit"
									class="btn btn-outline-secondary">Редактировать</button>
								<button type="button" class="btn btn-outline-danger"
									data-toggle="modal" data-target="#confirmDelete_${project.id }">Удалить</button>
							</div>
						</td>
					</tr>
					<!-- Modal -->
					<div class="modal fade" id="confirmDelete_${project.id }"
						tabindex="-1" role="dialog"
						aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
						<div class="modal-dialog modal-dialog-centered" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalCenterTitle">Подтвердить
										удаление</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									<span>Вы уверены что хотите удалить проект?</span><br>
									<p class="font-weight-bold text-danger">${project.name }</p>
								</div>
								<div class="modal-footer">
									<div class="container">
										<button type="button" class="btn btn-outline-secondary"
											data-dismiss="modal">Отменить</button>
										<button form="formDelete_${project.id }" type="submit"
											class="btn btn-outline-danger">Подтвердить</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<%--</form>--%>

</c:if>
<c:if test="${ empty projects and  empty editProject }">
	<div class="formbox">
		<form method="POST"
			action="${pageContext.request.contextPath}/project/add">
			<%-- CSRF for security, added by Sergey Christensen --%>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<%-- --%>
			<div class="form-group">
				<label for="name">Имя проекта:</label> <input type="text"
					required="required" name="name" class="form-control"
					placeholder="Введите имя для проекта" id="name" />
			</div>

			<div class="form-group">
				<label for="date">Срок проекта:</label> <input type="number"
					required="required" name="time" class="form-control" id="number"
					min="1.0" step="0.1" />
			</div>

			<div class="form-group">
				<label for="money">Финансирование:</label> <input type="number"
					required="required" name="money" class="form-control"
					placeholder="Сколько вы готовы инвестировать" id="money" />
			</div>

			<div class="form-group">
				<label for="difficult">Сложность:</label> <input type="number"
					required="required" name="difficult" class="form-control"
					placeholder="Выберите сложность проекта" id="difficult" min="1.0"
					step="0.1" />
			</div>

			<div class="form-group">
				<label for="type">Выберите тип оплаты:</label> <select
					class="form-control" name="type">
					<c:forEach var="type" items="${payTypes}">
						<option value="${type}">${type}</option>
					</c:forEach>
				</select>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-default">Создать
						новый проект</button>
				</div>
			</div>
		</form>
	</div>
</c:if>
<script>
	$(document).ready(function() {
		$('#escalation').DataTable();
	});
</script>
<script>
	$(document).ready(
			function() {
				$("#search").keyup(
						function() {
							_this = this;
							$.each($("#escalation tbody tr"), function() {
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