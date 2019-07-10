<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
	<c:set var="programmer" value="${programmer}"></c:set>
	<div class="card" style="width: 18rem;">
		<div class="card-header">
			<span class="badge badge-secondary">${programmer.rating}</span>
		</div>
		<ul class="list-group list-group-flush">
			<li class="list-group-item">${programmer.name}</li>
			<li class="list-group-item">${programmer.money}</li>
		</ul>
	</div>
	<form action="hireCall" method="post">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="hidden" name="programmerId"
			value="${programmer.id}">
		<div class="form-group">
			<label for="money">Ваше предложение</label> <input
				required="required" type="number" name="price" class="form-control"
				id="money" placeholder="Money" min="0" step="1">
		</div>
		<button type="submit" class="btn btn-primary">Нанять</button>
	</form>
</div>