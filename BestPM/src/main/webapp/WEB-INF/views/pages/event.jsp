<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container">
<c:if test="${ not empty editEvent }">
	<div class="formbox">
		<form method="POST"
			action="${pageContext.request.contextPath}/event/saveChanges">
			<%-- CSRF for security, added by Sergey Christensen --%>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<%-- --%>

			<div class="form-group">
				<input type="hidden" name="eventId" value="${editEvent}">
				<label for="name">Наименование события:</label> <input type="text"
					required="required" name="name" value="${name}"
					class="form-control" placeholder="Введите имя для события"
					id="name" />
			</div>

			<div class="form-group">
				<label for="eventPowerF">Сила события (с запятой):</label> <input type="number"
					required="required" name="eventPowerF" value="${eventPowerF}"
					class="form-control" placeholder="Сила события (с запятой)" id="eventPowerF"  />
			</div>

			<div class="form-group">
				<label for="eventPowerI">Сила события (целочисленная):</label> <input type="number"
					required="required"  name="eventPowerI" value="${eventPowerI}"
					class="form-control" placeholder="Сила события (целочисленная)"
					id="eventPowerI" />
			</div>
			
			<div class="form-group">
				<label for="eventTime">Время действия:</label> <input type="number"
					required="required"  name="eventTime" value="${eventTime}"
					class="form-control" placeholder="Время действия"
					id="eventTime" />
			</div>

			<div class="form-group">
				<label for="isHappy">Счастливое?</label>
				<c:if test="${eventIsHappy eq true}">
					<input type="checkbox" style="height: 30px;width: 30px" class="form-control" value="true" name="isHappy" checked/>
				</c:if>
				<c:if test="${eventIsHappy ne true or empty eventIsHappy}">
					<input type="checkbox" style="height: 30px;width: 30px" class="form-control" value="true" name="isHappy" />
				</c:if>
				 
			</div>
			
			
			<div class="form-group">
				<label for="type">Тип:</label> <select
					id="motivatorType" class="form-control" name="type" onchange=showActionTime();>
					<option  value="${selectedEventType}" selected>${selectedEventType}</option>
					<c:forEach var="type" items="${EventTypes}">
						<c:if test="${type ne selectedEventType }">
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
	<c:if test="${empty editEvent }">
	<h2>Добавить новое событие</h2>
	<div class="form-group">
		<form action="addNewEvent">
			<label class="control-label col-sm-2" for="name">Описание</label>
			<div class="col-sm-10">
				<input type="text" class="form-control"
					placeholder="Введите описание" name="name">
			</div>
			<label class="control-label col-sm-2" for="type">Тип</label> 
			<select	name="type">
				<option value="DISEASE">Болезнь</option>
				<option value="ACCIDENT">Происшествие</option>
				<option value="FINANCIAL">Финансы</option>
			</select>
			<label class="control-label col-sm-2" for="type">is Happy?</label> 
			<input type="checkbox" name="isHappy" value="true"/>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" value="Save" class="btn sucess">Создать</button>
				</div>
			</div>

		</form>
	</div>
	<div class="table-responsive">
		<table id="escalation" class="table table-striped table-bordered">
			<thead>
				<tr>
					<th scope="col">Описание события</th>
					<th scope="col">Сила события (с запятой)</th>
					<th scope="col">Сила события (целочисленная)</th>
					<th scope="col">Время действия</th>
					<th scope="col">Тип</th>
					<th scope="col">Счастливое?</th>
					<th scope="col">Действия</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="events" items="${events}">
					<tr>
						<td>${events.name}</td>
						<td>${events.powerInFloat}</td>
						<td>${events.powerInInteger}</td>
						<td>${events.eventTime}</td>
						<td>${events.eventType}</td>
						<td>${events.happy}</td>
						<td colspan="2" style="width: 50px;">
							<form action="${pageContext.request.contextPath}/event/delete"
								id="formDelete_${events.id }">
								<input type="hidden" name="eventsId" value="${events.id}">
							</form>
							<form action="${pageContext.request.contextPath}/event/edit"
								id="formEdit_${events.id }">
								<input type="hidden" name="eventsId" value="${events.id}">
							</form>
							<div class="input-group-append">
								<button form="formEdit_${events.id }" type="submit"
									class="btn btn-outline-secondary">Редактировать</button>
								<button type="button" class="btn btn-outline-danger"
									data-toggle="modal" data-target="#confirmDelete_${events.id }">Удалить</button>
							</div>
						</td>
					</tr>
					<div class="modal fade" id="confirmDelete_${events.id }"
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
									<span>Вы уверены что хотите удалить событие?</span><br>
									<p class="font-weight-bold text-danger">${events.name }</p>
								</div>
								<div class="modal-footer">
									<div class="container">
										<button type="button" class="btn btn-outline-secondary"
											data-dismiss="modal">Отменить</button>
										<button form="formDelete_${events.id }" type="submit"
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
	</c:if>
</div>
