<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style type="text/css">
.pBlock {
	width: 800px;
	height: 500px;
	border-radius: 5px;
	border: solid 2px;
}
.center{
text-align: center;
}
.pName {
	width: 100%;
	height: 65px;
	font-size: 40px;
	text-align: center;
	background-color: rgb(50,215,250);
}

.infoBlock {
	width: 396px;
	height: calc(100% - 65px);
	text-align: center;
	font-size: 25px;
	background-color: grey;
	display: inline-block;
	vertical-align: top;
	border-top: solid 1px;
}

.fline {
	height: 48px;
	border-bottom: solid 1px;
	background-color: grey;
}

.sline {
	height: 48px;
	border-bottom: solid 1px;
	color: lightgrey;
	background-color: #252525;
}

.progrBlock {
	height: calc(100% - 65px);
	width: 200px;
	border: solid 1px;
	display: inline-block;
}

.progger {
	width: 100%;
	height: 25px;
	text-align: center;
	font-size: 20px;
}
}
</style>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<c:if test="${pageContext.request.userPrincipal.name != null}">
	<form id="logoutForm" method="GET" action="${contextPath}/user/logout">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<!-- <h2>Мои проекты</h2> -->
	<%--  <h2>Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a>
        </h2> --%>

</c:if>

<!-- Building table from model -->
<c:if test="${empty selectedProject}">
	<h2>Мои проекты</h2>
	<div class="table-responsive">
		<table class="table table-striped table-bordered">
			<thead style="background: #4af">
				<tr>
					<th class="center">Название</th>
					<th class="center">До дедлайна</th>
					<th class="center">Прибыль</th>
					<th class="center">Сложность</th>
					<th class="center">Прогресс</th>
					<th class="center">Тип оплаты</th>
					<th class="center">Посмотреть информацию</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${projectsSet}" var="project">
		<tbody>
			<input class="id" type="hidden" name="projectId" value="${project.id}">
				<td class="name center"><c:out value="${project.name}" /></td>
				<c:if test="${project.timeLeft gt 0}">
					<td class="daysLeft center"><c:out value="${project.timeLeft}" /> дней</td>
					<td class="money center"><c:out value="${project.money}" /></td>
				</c:if>
				<c:if test="${project.timeLeft le 0}">
					<td class="daysLeft center">Просроченно на: <c:out value="${project.timeLeft*-1}" /> дней</td>
					<td class="money center"><c:out value="${project.money*0.8}" /></td>
				</c:if>
				
				<td class="difficultLeft center"><c:out value="${project.stringDifficult}" /></td>
				<c:if test="${not empty project.programmers}">
					<td class="progressTd">
						<div class="progressPercent" style="text-align: center">${project.stringPercent}%</div>
						<div class="progress">
	  						<div class="projectBar progress-bar progress-bar-striped progress-bar-animated bg-success" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: ${project.progressPercent}%"></div>
						</div>
					</td>
				</c:if>
				<c:if test="${empty project.programmers}">
				<td class="progressTd">
				<div class="progressPercent" style="text-align: center">${project.stringPercent}%</div>
					<div class="progress">
  					<div class="projectBar progress-bar progress-bar-striped bg-danger" role="progressbar" style="width: ${project.progressPercent}%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
					</div>
					</td>
				</c:if>
				
				<%-- <td class="progressPercent"><c:out value="${project.progressPercent}"></c:out> --%>
				<td class="typeOfPayment center"><c:out value="${project.typeOfPayment}" /></td>
				<form class="formAction"
						action="${pageContext.request.contextPath}/userProjects/projectInfo">
						<input type="hidden" name="projectId" value="${project.id}">
						<td class="info" style="width: 90px;"><button type="submit"
								style="width: 100px; background-color: blue"
								class="btn btn-danger">Инфо</button></td>
					</form>

				</tbody>
			</c:forEach>
			</tbody>
		</table>
	</div>
</c:if>
<c:if test="${not empty selectedProject}">
	<h3>Информация по проекту:</h3>
	<div class="pBlock,container">
		<div class="pName">${selectedProject.name}</div>
		<%-- <div class="progrBlock">--%>
		<div class="table-responsive">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>Программисты</th>
						<th>Дней до зарплаты</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="programmer" items="${selectedProject.programmers}">
						<tr>
							<td>${programmer.name}</td>
							<c:if test="${30-programmer.dayInWork eq 0}">
								<td>Сегодня</td>
							</c:if>
							<c:if test="${30-programmer.dayInWork ne 0}">
								<td>${30-programmer.dayInWork}</td>
							</c:if>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<%-- </div>--%>
		<%--<div class="infoBlock">
			<div class="fline">Дата Окончания</div>
			<div class="sline">${selectedProject.date}</div>
			<div class="fline">Сложность</div>
			<div class="sline">${selectedProject.difficult}</div>
			<div class="fline">Тип Оплаты</div>
			<div class="sline">${selectedProject.typeOfPayment}</div>
			<div class="fline">Сумма</div>
			<div class="sline">${selectedProject.money}</div>
		</div> --%>
		<div class="table-responsive">
			<table class="table table-striped table-bordered">
				<thread>
				<tr>
					<th>До дедлайна</th>
					<th>Сложность</th>
					<th>Тип оплаты</th>
					<c:if test="${selectedProject.typeOfPayment eq 'TIMEMATERIAL'}">
					<th>Выплата через</th>
					</c:if>
					<th>Прибыль</th>
				</tr>
				</thread>
				<tbody>
					
					
					<c:if test="${selectedProject.timeLeft gt 0}">
						<td class="daysLeft"><c:out value="${selectedProject.timeLeft}" /> дней</td>
						
					</c:if>
					<c:if test="${selectedProject.timeLeft le 0}">
						<td class="daysLeft">Просроченно на: <c:out value="${selectedProject.timeLeft*-1}" /> дней</td>
						
					</c:if>
					
					
					<td>${selectedProject.difficult}</td>
					<td>${selectedProject.typeOfPayment}</td>
					<c:if test="${selectedProject.typeOfPayment eq 'TIMEMATERIAL'}">
						<td>${30-(selectedProject.time-selectedProject.timeLeft)%30} дней</td>
					</c:if>
					<c:if test="${selectedProject.timeLeft gt 0}">
						<td class="money"><c:out value="${selectedProject.money}" /></td>
					</c:if>
					<c:if test="${selectedProject.timeLeft le 0}">
						<td class="money"><c:out value="${selectedProject.money*0.8}" /></td>
					</c:if>
					


				</tbody>



			</table>
		</div>
		<%-- <div class="progrBlock">
	<div class="title">Программисты</div>
	<c:forEach var="programmer" items="${selectedProject.programmers}">
						<option value="${type}">${type}</option>
						<div class="progger"><c:out value="${programmer.name}" /></div>
	</c:forEach>
	</div> --%>
	</div>
</c:if>
<script type="text/javascript">
	var money = $("td.money");
	for(var i =0;i<money.length;i++){
		var str = $(money[i]).html();
		str = str.toString();
		if(str.includes('.')){
		$(money[i]).html(str.substring(0, str.indexOf('.')));
		}
		/* console.log($(money[i]).html()); */
	}
	/* var str = $(money).hmlt;
	console.log
	str = str.toString();
	$(difficultLeft[j]).html(str.substring(0, str.indexOf('.')+3)); */
</script>