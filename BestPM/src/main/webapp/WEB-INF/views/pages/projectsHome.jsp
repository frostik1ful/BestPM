<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page
	import="java.io.*,java.util.*,juke.controllers.ProjectsHomeController"%>
<!--  
<h4>Список будет обновлен через:</h4>
<body onload="startTimer()">
<p>
	<span id="timerr"
		style="color: #4af; font-size: 150%; font-weight: bold;"> <c:out
			value="${modelTimerMinutes} :" default="5" /> <c:out
			value="${modelTimerSeconds}" default="0" />
	</span>
</p>
-->
<h1>Доступные проекты</h1>
<c:if test="${not empty error}">
	<br>	
		<font color="red"><h2> Другой игрок уже взял этот проект </h2> </font>
	<br>
</c:if>
<!-- Building table from model -->
<div class="table-responsive">
	<table class="table table-striped table-bordered">
		<thead style="background: #4af">
			<tr>
				<th>Название</th>
				<th>Дней на выполнение</th>
				<th>Прибыль от выполнения</th>
				<th>Сложность</th>
				<th>Тип оплаты</th>
				<th>Взять проект</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${projectsList}" var="project">
				<tr>
					<c:url var="getProjectUrl"
						value="/projects/home?id=${project.id}" />
					<td><c:out value="${project.name}" /></td>
					<td><c:out value="${project.time}" /></td>
					<td><c:out value="${project.money}" /></td>
					<td><c:out value="${project.difficult}" /></td>
					<td><c:out value="${project.typeOfPayment}" /></td>
					<td><a href="${getProjectUrl}">Взять проект</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<!-- Giving add link if list is empty 
<c:if test="${empty projectsList}">
 There are currently no persons in the list. <a href="${addUrl}">Add</a> a person.
</c:if>
-->
<!-- Connecting JS for timer -->
<!-- 
<script type='text/javascript'>
	function startTimer() {
		var timer = document.getElementById("timerr");
		var time = timer.innerHTML;
		var arr = time.split(":");
		var mm = arr[0];
		var ss = arr[1];

		if (mm == 0 && ss < 1) {
			window.location.reload();
			return;
		}

		if (ss == 0) {
			mm--;
			ss = 59;
		} else
			ss--;
		if (ss < 10) {
			ss = "0" + ss;
		}
		document.getElementById("timerr").innerHTML = mm + " : " + ss;
	}
	setInterval(startTimer, 1000);
</script>
-->