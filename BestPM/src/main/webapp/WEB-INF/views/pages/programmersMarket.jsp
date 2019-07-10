<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="count" value="1"></c:set>
<div class="container">
	<div class="table-responsive">
		<table class="table table-striped table-bordered">

			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">Имя</th>
					<th scope="col">Рейтинг</th>
					<th scope="col">Желаемый Оклад</th>
					<th scope="col">Действие</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach var="programmer" items="${programmers }">
					<tr>
						<th scope="row">${count }</th>
						<td>${programmer.name }</td>
						<td>${programmer.rating }</td>
						<td>${programmer.money }</td>
						<td><form method="post"
								action="${pageContext.request.contextPath}/programmer/hire">
								<input type="hidden" name="programmerId"
									value="${programmer.id }"> <input type="hidden"
									name="${_csrf.parameterName}" value="${_csrf.token}" />
								<button type="submit" class="btn btn-primary">Нанять</button>
							</form></td>
					</tr>
					<c:set var="count" value="${count + 1 }"></c:set>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>