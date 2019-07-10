<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page
	import="java.io.*,java.util.*,juke.controllers.ProjectsHomeController"%>

<h1>Список пользователей проекта</h1>

<div class="form-group">
	<input type="text" class="form-control pull-right" id="search"
		placeholder="Поиск по таблице">
</div>

<!-- Building table from model -->
<div class="table-responsive">
	<table class="table table-striped table-bordered" id="mytable">
		<thead>
			<tr>
				<th>Имя пользователя</th>
				<th>email</th>
				<th>Баланс</th>
				<th>Донат</th>
				<th>Забанен</th>
				<th>Забанить/Разбанить</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userList}" var="user">
				<tr>
					<c:url var="getBannedUrl"
						value="/user/banned?username=${user.username}" />

					<td><c:out value="${user.username}" /></td>
					<td><c:out value="${user.email}" /></td>
					<td><c:out value="${user.balance}" /></td>
					<td><c:out value="${user.donate}" /></td>
					<td><c:out value="${user.banned}" /></td>
					<td>
						<!-- <a href="${getBannedUrl}" class="btn btn-primary btn-sm"
						role="button">ЗАБАНИТЬ/РАЗБАНИТЬ</a>-->
						<form id="formBun_${user.id }"
							action="${pageContext.request.contextPath}/user/banned"
							method="get">
							<input name="username" type="hidden" value="${user.username }">
							<button type="button" class="btn btn-outline-danger"
								data-toggle="modal" data-target="#confirmBun_${user.id }">ЗАБАНИТЬ/РАЗБАНИТЬ</button>
						</form>
					</td>
				</tr>
				<div class="modal fade" id="confirmBun_${user.id }" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalCenterTitle"
					aria-hidden="true">
					<div class="modal-dialog modal-dialog-centered" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalCenterTitle">Подтвердить
									действие</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<span>Вы уверены в своем решении?</span><br>
								<p class="font-weight-bold text-danger">${user.username }</p>
							</div>
							<div class="modal-footer">
								<div class="container">
									<button type="button" class="btn btn-outline-secondary"
										data-dismiss="modal">Отменить</button>
									<button form="formBun_${user.id }" type="submit"
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

<%-- search --%>
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
