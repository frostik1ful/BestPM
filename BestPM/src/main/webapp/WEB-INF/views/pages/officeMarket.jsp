<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="count" value="1"></c:set>
<div class="container">
	<div class="row">
		<c:forEach items="${offices }" var="office">
			<div class="col-sm mt-2">
				<div class="card" style="width: 18rem;">
					<img class="card-img-top"
						src="${pageContext.request.contextPath}${office.imageURL }"
						alt="OfficeImage">
					<div class="card-body">
						<h5 class="card-title">${office.officeName }</h5>
					</div>
					<ul class="list-group list-group-flush">
						<li class="list-group-item">Уровень: ${office.level }</li>
						<li class="list-group-item">Количество программистов:
							${office.countProgrammers }</li>
						<li class="list-group-item text-info"><i class="fas fa-dollar-sign" style="color:green; margin-right: 3px"></i>${office.price }</li>
						<li class="list-group-item text-info"><i class="fab fa-bitcoin" style="color:gold; margin-right: 3px;"></i>${office.priceDonate }</li>
					</ul>
					<div class="card-body">
						<form id="simpleFormOffice${count}" method="post"
							action="${pageContext.request.contextPath}/user/byOffice">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" /> <input type="hidden" name="officeId"
								value="${office.id}">
						</form>
						<form id="donateFormOffice${count}" method="post"
							action="${pageContext.request.contextPath}/user/byOffice/donate">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" /> <input type="hidden" name="officeId"
								value="${office.id}">
						</form>
						<div class="input-group-append card-link">
							<button form="simpleFormOffice${count}" type="submit"
								class="btn btn-outline-secondary">Купить за <i class="fas fa-dollar-sign" style="color:green; margin-right: 3px"></i></button>
							<button form="donateFormOffice${count}" type="submit"
								class="btn btn-outline-success">Купить за <i class="fab fa-bitcoin" style="color:gold; margin-right: 3px;"></i></button>
						</div>
					</div>
				</div>
			</div>
			<c:set var="count" value="${count + 1 }"></c:set>
		</c:forEach>
	</div>
</div>