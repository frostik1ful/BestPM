<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!--  <link rel="stylesheet"
	href="${pageContext.request.contextPath}/webjars/bootstrap/css/bootstrap.min.css"> 
<script type="text/javascript"
	src="${pageContext.request.contextPath}/webjars/bootstrap/js/bootstrap.min.js"></script>-->
	<link rel="shortcut icon" href='<c:url value="/resources/images/favicon.ico" />' type="image/x-icon">
	<link rel="icon" href='<c:url value="/resources/images/favicon.ico" />' type="image/x-icon">	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/webjars/jquery/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/webjars/popper.js/dist/popper.min.js"></script>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
	integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4"
	crossorigin="anonymous">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"
	integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm"
	crossorigin="anonymous"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/imageMapResizer.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/webjars/font-awesome/svg-with-js/js/fontawesome-all.min.js"></script>
</head>
<body>
	<div style="display: none;">
		<form id="request-form">
			<button type="submit" id="bth-submit">Send</button>
		</form>
	</div>
	<center>
		<!-- <h2 id="timer">До конца дня-</h2>
		 <button id="dateB">+day</button> -->
		<tiles:insertAttribute name="header" />
		<div class="container-fluid">
			<tiles:insertAttribute name="body" />
		</div>
	
	</center>
	<script type="text/javascript">
		$('map').imageMapResize();
	</script>
	<security:authorize url="/office/**">
		<script type="text/javascript">
			setTimer();
			setNotice();
			//Все переменые я создавал по id тегов
			var timerId;//ID таймера
			var hello = "Hello: ";
			var balance = "Your balance: ";
			jQuery(document).ready(function($) {
				$("#request-form").submit(function(event) {
					event.preventDefault();// Запретить отправку формы через браузер.
					secProgress();// Вызов функции.
					

				});
			});

			//Функция отправки запроса на сервер и прием данных
			function searchViaAjax() {
				$
						.ajax({
							type : "GET",
							url : "${pageContext.request.contextPath}/user/information",
							timeout : 100000,
							dataType : "json",
							success : function(data) {
								//console.log("SUCCESz: ", data);
								secProgress();
								//display(data);

							},
							error : function(e) {
								//console.log("ERROR: ", e);
								display(e);
							}
						});

			}
			//Преобразуем даные как нам надо
			function display(data) {
				$('#userDonate').html(" " + data['userDonate']);
				$('#userMoney').html(" " + data['userMoney']);
			}

			timerId = setInterval(function() {
				$("#bth-submit").click();
			}, 1000);

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			function setGameOver(){
				document.getElementById('gameOverButton').click();
			}
			function addNotification(text){
				var notifyBlock = document.querySelector('#noticeBlock');
				var noticeBlock = document.querySelector('#notices');
				var notices = noticeBlock.querySelectorAll('.notice');
				var notIcon = document.querySelector('#notIcon');
				if(notifyBlock.style.display=="none"){
					$(notIcon).css("color", "red");
				}
				else{
					$(notIcon).css("color", "grey");
				}
				if(notices.length<6){
					addNotice(noticeBlock)
				}
				else if(notices.length==7){
					noticeBlock.removeChild(notices[0]);
					notices[1].style.opacity=0;
					notices[1].style.transition = "all 1s";
					notices[1].innerHTML="";
					notices[1].style.height = "0px";
					addNotice(noticeBlock)
				}
				else{
					notices[0].style.opacity=0;
					notices[0].style.transition = "all 1s";
					notices[0].innerHTML="";
					notices[0].style.height = "0px";
					addNotice(noticeBlock)
				}
				function addNotice(noticeBlock){
					var notice = document.createElement('div');
					notice.setAttribute('class', 'notice');
					if(text=='gameOver'){
						setGameOver();
						
					}
					else{
					notice.innerHTML=text;
					if(screen.width<600){
						notice.style.height=30+"px";
						notice.style.fontSize = 5+"px";
						
					}
					else if(screen.width<990){
						notice.style.height=35+"px";
						notice.style.fontSize = 8+"px";
					}
					else{
						notice.style.height=100+"px";
						notice.style.fontSize = 120+"%";
					}
			    	noticeBlock.appendChild(notice);
					}
			    }
			}
			function setNotice(){
				$
				.ajax({
					url : "${pageContext.request.contextPath}/userProjects/getNotice",
					//timeout : 100000,
					dataType : "json",
					success : function(data) {
						data = data['notice'];
						var noticeBlock = document.querySelector('#notices');
						var noticeButton = document.querySelector('#nots');
						/* var nottext = document.querySelector('#nottext'); */
						var count;
						if(data.length>=6){
							count=data.length-6;
						}
						else{
							count=0;
						}
						for (var i = count; i < data.length; i++) {
							if(data[i]!='gameOver'){
								var notice = document.createElement('div');
								notice.setAttribute('class', 'notice');
								notice.innerHTML=data[i];
								if(screen.width<600){
									notice.style.height=30+"px";
									notice.style.fontSize = 5+"px";
									/* nottext.innerHTML = "Увед." */
									
								}
								else if(screen.width<990){
									notice.style.height=35+"px";
									notice.style.fontSize = 8+"px";
									/* nottext.innerHTML = "Уведомления" */
								}
								else{
									notice.style.height=100+"px";
									notice.style.fontSize = 120+"%";
									/* nottext.innerHTML = "Уведомления" */
								}
								
						    	noticeBlock.appendChild(notice);
								
							}
							
						}
					},
					error : function(e) {
						console.log("ERROR: ", e);
						display(e);
					}
				});
			}
			function setTimer() {
				$
						.ajax({
							url : "${pageContext.request.contextPath}/userProjects/getSeconds",
							//timeout : 100000,
							dataType : "json",
							success : function(data) {
								$('#userMoney').html(" " + data['userBalance']);
								$('#userDonate').html(" " + data['userDonate']);
								$('#clockTimer').html(data['secondsLeft']);
								/* if (data < 10) {
									$('#clockTimer').html(
											"До конца дня осталось " + "0"
													+ data['secondsLeft']);
								} else {
									$('#clockTimer').html(
											"До конца дня осталось " + data);
								} */
							},
							error : function(e) {
								console.log("ERROR: ", e);
								display(e);
							}
						});
			}
			function secProgress() {
				$
						.ajax({

							url : "${pageContext.request.contextPath}/userProjects/setSeconds",
							timeout : 100000,
							dataType : "json",
							success : function(data) {
								$('#userMoney').html(" " + data['userBalance']);
								$('#userDonate').html(" " + data['userDonate']);
								$('#clockTimer').html(data['secondsLeft']);
								/* if (data['secondsLeft'] < 10) {
									$('#clockTimer').html(
											"До конца дня осталось " + "0"
													+ data['secondsLeft']);
								} else {
									$('#clockTimer').html(
											"До конца дня осталось "
													+ data['secondsLeft']);
								} */
								if (data['secondsLeft'] == 0) {//если полученные секунды ==0 создаем запрос
									$
											.ajax({
												url : '${pageContext.request.contextPath}/userProjects/getInfo',
												success : function(data) {
													setProgress(data);
												}
											});
								}
							},
							error : function(e) {
								console.log("ERROR: ", e);
								display(e);
							}
						});
			}
			/* $(document).ready(function() {
				$("#request-form").submit(function(event) {
					event.preventDefault();
					startGame();

				});
			});  */

			$(document)
					.ready(
							function() {
								$("#dateB")
										.click(
												function() {
													$
															.ajax({
																url : "${pageContext.request.contextPath}/userProjects/getInfo",
																success : function(
																		data) {
																	setProgress(data);

																}

															});
												});
							});
			function dayProgress() {
				$.ajax({
					url : 'getInfo',
					success : function(data) {
						setProgress(data);

					}

				});
			}

			function setProgress(data) {
				var id = $("input.id");//создаем массивы элементов с данными 
				var name = $("td.name");
				var daysLeft = $("td.daysLeft");
				var money = $("td.money");
				var difficultLeft = $("td.difficultLeft");
				var progressPercent = $(".progressPercent");
				var typeOfPayment = $("td.typeOfPayment");
				var formAction = $("form.formAction");
				var info = $("td.info");
				var projectBars = $(".projectBar");
				var innerBars = $("td.progressTd");
				//здесь меняем значения
				for (var i = 0; i < data.length - 1; i++) {//-1 тут что бы не брать последний обьект где мы храним только деньги игрока
					for (var j = 0; j < id.length; j++) {
						if ($(id[j]).val() == data[i]['id']) {//если массив с id совпадает с id в дате меняем значения
							if (data[i]['daysLeft'] < 0) {
								$(daysLeft[j]).html(
										"Просроченно на: " + data[i]['daysLeft']*-1+" дней");
							} else {
								$(daysLeft[j]).html(data[i]['daysLeft']+' дней');

							}
							var str = data[i]['difficultLeft'];
							str = str.toString();
							if(str.indexOf('.')>0){
							$(difficultLeft[j]).html(str.substring(0, str.indexOf('.')+3));
							}
							
							$(money[j]).html(data[i]['money']);
							str = data[i]['progressPercent'];
							str = str.toString();
							if(str.indexOf('.')>0){
							$(progressPercent[j]).html(str.substring(0, str.indexOf('.')+3)+'%');
							}
							$(projectBars[j]).width(data[i]['progressPercent']+'%');

						}
					}
				}
				//здесь удаляем завершонные проекты со страницы
				if (id.length != data.length - 1) {
					for (var i = 0; i < id.length; i++) {
						var check = false;
						for (var j = 0; j < data.length - 1; j++) {
							if ($(id[i]).val() == data[j]['id']) {//если нашли совпадение чек - тру
								check = true;
								break;
							}
							if (check == false) {// если чек == фолс удаляем элементы без совпадений в дате
								$(id[i]).remove();
								$(name[i]).remove();
								$(daysLeft[i]).remove();
								$(money[i]).remove();
								$(difficultLeft[i]).remove();
								$(progressPercent[i]).remove();
								$(typeOfPayment[i]).remove();
								$(info[i]).remove();
								$(formAction[i]).remove();
								$(innerBars[i]).remove();
								$(projectBars[i]).remove();
							}
						}

					}

				}
				if (data.length == 1) {// если в дате один элемент(в котором мы передаем деньги) те- проектов у нас нет, удаляем все блоки с данными
					$(id).remove();
					$(name).remove();
					$(daysLeft).remove();
					$(money).remove();
					$(difficultLeft).remove();
					$(progressPercent).remove();
					$(typeOfPayment).remove();
					$(info).remove();
					$(formAction).remove();
					$(innerBars).remove();
					$(projectBars).remove();
				}

				// добавляем уведомления 
				for(var i = 0; i < data[data.length - 1]['notice'].length; i++){
					addNotification(data[data.length - 1]['notice'][i]);
				}
			}
		</script>
	</security:authorize>
</body>
</html>