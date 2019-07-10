<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">
	<form:form method="post" modelAttribute="motivator" action="save">
		<div class="form-group">
			<label>Наименование мотиватора</label>
			<form:input type="text" path="name" class="form-control"
				placeholder="Наименование мотиватора" autofocus="true"
				required="required"></form:input>
		</div>
		<div class="form-group">
			<label>Сила мотиватора</label>
			<form:input type="number" path="power" class="form-control"
				placeholder="Сила мотиватора" step="0.1" max="5" min="0.2"
				required="required"></form:input>
		</div>
		<div class="form-group">
			<label>Цена мотиватора</label>
			<form:input type="number" path="price" class="form-control"
				placeholder="Цена мотиватора" step="1" min="10" required="required"></form:input>
		</div>
		<div class="form-group">
			<label>Цена мотиватора в монетах</label>
			<form:input type="number" path="priceDonate" class="form-control"
				placeholder="Цена мотиватора" step="1" min="10" required="required"></form:input>
		</div>
		<div class="form-group">
			<label>Описание мотиватора</label>
			<form:textarea path="discription" class="form-control"
				placeholder="Описание" required="required" wrap="hard" rows="3"
				cols="20" maxlength="500"></form:textarea>
		</div>
		<div class="form-group ">
			<label>Тип мотиватора</label>
			<form:select id="motivatorType" path="type" class="form-control">
				<c:forEach var="type" items="${typeMotivators }">
					<form:option value="${type }"></form:option>
				</c:forEach>
			</form:select>
		</div>
		<div id="hidenInput" class="form-group " style="display: none;">
			<label>Время действия</label>
			<form:input id="inputTimeOfAction" value="0" type="number"
				path="timeOfAction" class="form-control"
				placeholder="Время действия" step="1" min="1" max="30"></form:input>
		</div>
		<button class="btn btn-lg btn-primary btn-block" type="submit">Добавить</button>
	</form:form>
	<c:if test="${not empty succsess }">
		<div class="alert alert-success" role="alert">${succsess }</div>
	</c:if>
	<c:if test="${not empty error }">
		<div class="alert alert-danger" role="alert">${error}</div>
	</c:if>
</div>
<script>
	var count = 0;
	var divInputHiden = document.querySelector("#hidenInput");
	var selectMotivatorType = document.querySelector("#motivatorType");

	function setToZero(event) {
		count = 0;
	}

	function motivatorListener(event) {
		count++;
		if (count === 2) {
			check(event);
		}
	}

	function check(event) {
		if (selectMotivatorType.value == "TEMPORARY") {
			divInputHiden.style.display = "block";
			document.querySelector("#inputTimeOfAction").min = 1;
		} else {
			divInputHiden.style.display = "none";
			document.querySelector("#inputTimeOfAction").min = 0;
		}
	}
	selectMotivatorType.addEventListener("mouseout", setToZero, false);
	selectMotivatorType.addEventListener("click", motivatorListener, false);
	document.addEventListener("DOMContentLoaded", check);
</script>