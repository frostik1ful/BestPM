<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container">
	<h1 class="display-2">Рынок мотиваторов</h1>
	<c:if test="${not empty error }">
		<div class="container">
			<div class="alert alert-info" role="alert">${error }</div>
		</div>
	</c:if>
</div>
<c:set var="count" value="1"></c:set>
<div class="row">
	<c:forEach var="motivator" items="${motivators }">
		<div class="col mb-1 mt-1">
			<div class="card text-center w-100">
				<div class="card-body">
					<h5 class="card-title">${motivator.name }</h5>
					<p class="card-text">
						<textarea style="background-color: white; border: 0px;"
							class="form-control" readonly="readonly" wrap="hard" rows="3"
							cols="6">${motivator.discription }</textarea>
					</p>
				</div>
				<ul class="list-group list-group-flush">
					<li class="list-group-item">Сила: ${motivator.power }</li>
					<c:if test="${motivator.type == type }">
						<li class="list-group-item">Время действия:
							${motivator.timeOfAction } дн.</li>
					</c:if>
					<c:if test="${motivator.type != type }">
						<li class="list-group-item">Время действия: неограниченно</li>
					</c:if>
					<li class="list-group-item">Цена: ${motivator.price }<i class="fas fa-dollar-sign" style="color:green; margin-right: 3px"></i></li>
					<li class="list-group-item">Цена: ${motivator.priceDonate }<i class="fab fa-bitcoin" style="color:gold; margin-right: 3px;"></i></li>
				</ul>
				<div class="card-body">
					<form id="simpleForm${count }"
						action="${pageContext.request.contextPath}/user/buyMotivator"
						method="post">
						<input name="motivatorId" type="hidden" value="${motivator.id }">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>
					<form id="donateForm${count }"
						action="${pageContext.request.contextPath}/user/buyMotivator/donate"
						method="post">
						<input name="motivatorId" type="hidden" value="${motivator.id }">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>
					<div class="input-group-append card-link">
						<button form="simpleForm${count }" type="submit"
							class="btn btn-outline-secondary">Купить за <i class="fas fa-dollar-sign" style="color:green; margin-right: 3px"></i></button>
						<button form="donateForm${count }" type="submit"
							class="btn btn-outline-success">Купить за <i class="fab fa-bitcoin" style="color:gold; margin-right: 3px;"></i></button>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${(count % 3) == 0 }">
			<div class="w-100"></div>
		</c:if>
		<c:set var="count" value="${count + 1 }"></c:set>
	</c:forEach>
</div>
