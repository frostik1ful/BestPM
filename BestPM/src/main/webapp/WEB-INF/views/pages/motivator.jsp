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
<c:if test="${not empty editMotivator}">
	<div class="formbox">
		<form method="POST"
			action="${pageContext.request.contextPath}/motivator/saveChanges">
			<%-- CSRF for security, added by Sergey Christensen --%>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<%-- --%>

			<div class="form-group">
				<input type="hidden" name="motivatorId" value="${editMotivator}">
				<label for="name">Наименование мотиватора:</label> <input type="text"
					required="required" name="name" value="${name}"
					class="form-control" placeholder="Введите имя для мотиватора"
					id="name" />
			</div>

			<div class="form-group">
				<label for="power">Сила мотиватора:</label> <input type="number"
					required="required" name="power" value="${motivatorPower}"
					class="form-control" placeholder="Сила мотиватора" id="power" step="0.1" max="5" min="0.2" />
			</div>

			<div class="form-group">
				<label for="price">Цена мотиватора:</label> <input type="number"
					required="required" min="10" name="price" value="${motivatorPrice}"
					class="form-control" placeholder="Цена мотиватора"
					id="price" />
			</div>
			
			<div class="form-group">
				<label for="donatePrice">Цена мотиватора в монетах:</label> <input type="number"
					required="required" min="10" name="donatePrice" value="${motivatorDonatePrice}"
					class="form-control" placeholder="Цена мотиватора в монетах"
					id="donatePrice" />
			</div>

			<div class="form-group">
				<label for="discription">Описание мотиватора:</label> <input type="text"
					required="required" placeholder="Описание мотиватора"
					name="discription" value="${discription}" class="form-control"
					id="discription" min="1.0" step="0.1" />
			</div>
			
			<div class="form-group" id="actionTime" >
					<label for="timeOfAction">Время действия:</label> <input type="number"
						required="required" name="timeOfAction" value="${timeOfAction}"
						class="form-control" placeholder="Время действия"
						id="timeOfAction" />
			</div>
			
			<div class="form-group">
				<label for="type">Выберите тип мотиватора:</label> <select
					id="motivatorType" class="form-control" name="type" onchange=showActionTime();>
					<option  value="${selectedMotivatorType}" selected>${selectedMotivatorType}</option>
					<c:forEach var="type" items="${MotivatorTypes}">
						<c:if test="${type ne selectedMotivatorType }">
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
<c:if test="${not empty motivators and  empty editMotivator}">

	<div class="form-group">
		<input type="text" class="form-control pull-right" id="search"
			placeholder="Поиск по мотиваторам">
	</div>
	<%-- --%>
	<div class="table-responsive">
		<table id="escalation" class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>Название</th>
					<th>Тип</th>
					<th>Сила</th>
					<th>Время действия</th>
					<th>Цена</th>
					<th>Цена в монетах</th>
					<th>Описание</th>
					<th>Действие</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${motivators}" var="motivator">
					<tr>
						<td>${motivator.name}</td>
						<td>${motivator.type}</td>
						<td>${motivator.power}</td>
						<td>${motivator.timeOfAction}</td>
						<td>${motivator.price}</td>
						<td>${motivator.priceDonate}</td>
						<td>${motivator.discription}</td>
						<td colspan="2" style="width: 50px;">
							<form action="${pageContext.request.contextPath}/motivator/delete"
								id="formDelete_${motivator.id }">
								<input type="hidden" name="motivatorId" value="${motivator.id}">
							</form>
							<form action="${pageContext.request.contextPath}/motivator/edit"
								id="formEdit_${motivator.id }">
								<input type="hidden" name="motivatorId" value="${motivator.id}">
							</form>
							<div class="input-group-append">
								<button form="formEdit_${motivator.id }" type="submit"
									class="btn btn-outline-secondary">Редактировать</button>
								<button type="button" class="btn btn-outline-danger"
									data-toggle="modal" data-target="#confirmDelete_${motivator.id }">Удалить</button>
							</div>
						</td>
					</tr>
					<!-- Modal -->
					<div class="modal fade" id="confirmDelete_${motivator.id }"
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
									<span>Вы уверены что хотите удалить мотиватор?</span><br>
									<p class="font-weight-bold text-danger">${motivator.name }</p>
								</div>
								<div class="modal-footer">
									<div class="container">
										<button type="button" class="btn btn-outline-secondary"
											data-dismiss="modal">Отменить</button>
										<button form="formDelete_${motivator.id }" type="submit"
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
<c:if test="${ empty motivators and  empty editMotivator }">
	<h1>Мотиваторы отсутствуют</h1>
</c:if>


<script>
function showActionTime(){
	var selectMotivatorType = document.querySelector("#motivatorType");
	var actionTime = document.querySelector("#actionTime");
	
	if (selectMotivatorType.value == "TEMPORARY") {
		actionTime.style.display = "block";
	}
	else{
		actionTime.style.display = "none";
	}
}
</script>