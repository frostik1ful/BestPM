<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<link href="https://fonts.googleapis.com/css?family=Bitter" rel="stylesheet">
	<style type="text/css">
@font-face {
    font-family:Digital;   
    src:url(${pageContext.request.contextPath}/resources/fonts/digital-7.ttf); 
}
.about{
color: grey;
}
.about:hover{
color:DimGray ;
}
.userMoney{
margin-right: 15px;
display: inline-block;
font-size: 23px;
font-family: 'Bitter', serif;
}
.timer{
/* position: absolute;
right:45%; */
position: relative;
top:4px;
left:14px;
margin-right:15px;
border-radius:3px;
border: 2px inset lightgrey;
margin-left:0;
padding:0;
height:40px;
width:45px;
font-family:Digital;
font-size: 35px;
/* background-color: grey; */
}
.le{
margin-right:0;
}
.left{
position: relative;
right:15%;
}
.not{
	width:16.3%;
 	margin-right: 5%;
	text-align: center;
	user-select: none;
	cursor: pointer;
	/* color: blue; */
	display: inline-block;
	 /* background-color: red; */ 
 }
.noticeBlock{
	position: fixed;
	/* left: 80%; */
	right:3%;
	width: 19%;
	height: 2%;
	font-size:20px;
	color:black;
	z-index:3;
	 /*  background-color: grey;
	 border: solid; */ */
}
.noticeHead{
	width: 100%;
	height: 30px;
	 background-color: red;
	 color: black;
	 text-align: center;
	user-select: none;
	cursor: pointer;
}
.notices{
	height: 100%;
	width:100%;
	
}
.hideB{
	margin-left: 60%;
	height: 100%;
	width: 35px;
	display: inline-block;
	background-color:grey;
	text-align: center;
	user-select: none;
	
}
.closeB{
	margin-left: 10px;
	height: 100%;
	width: 35px;
	display: inline-block;
	background-color:grey;
	text-align: center;
	user-select: none;
	
}
.notice{
	border: solid 1px;
	width: 100%;
	 height: 100px; 
	/*height:35px;*/
	margin-top: 5px;
	 font-size: 120%; 
	/*font-size:8px;*/
	background-color: white;
	text-align: center;
}

.block{
	width: 100%;
	height: 100px;
	margin-top: 5px;
	font-size: 20px;
	background-color: grey;
	 transition: all ease 0.8s;
	text-align: center;
}
}
</style>
<!-- 
<div class="card text-center">
	<div class="card-header">
		<ul class="nav nav-tabs card-header-tabs">
			<li class="nav-item"><sec:authentication property="authorities"
					var="roles" scope="page" /> <c:forEach var="role" items="${roles}">
					<c:choose>
						<c:when test="${role eq 'ADMIN'}">
							<li class="nav-item"><a class="nav-link" href="/">Админ</a></li>
							<c:set var="userType" scope="session" value="${'admin'}" />
						</c:when>

						<c:when test="${role eq 'USER'}">
							<c:if test="${userType != 'admin'}">
								<li class="nav-item"><a class="nav-link"
									href="/office/myOffice">Игрок</a></li>
								<c:set var="userType" scope="session" value="${'user'}" />
							</c:if>
						</c:when>

						<c:otherwise>
							<li class="nav-item"><a class="nav-link" href="/">Домой</a></li>
							<c:set var="userType" scope="session" value="${''}" />
						</c:otherwise>
					</c:choose>
				</c:forEach> <%-- User greetings --%> <c:if
					test="${pageContext.request.userPrincipal.name != null}">
					<form id="logoutForm" method="GET"
						action="${contextPath}/user/logout">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>
					<li class="nav-item" style="text-align: center"><a
						class="nav-link"
						href="${pageContext.request.contextPath}/user/profile">Здравствуйте,
							${pageContext.request.userPrincipal.name}</a></li>
					<li class="nav-item" style="text-align: center"><a
						class="nav-link"
						href="${pageContext.request.contextPath}/user/logout">Выйти</a></li>
				</c:if>
		</ul>
	</div>
</div>
-->
<div class="modal fade" id="gameOver" tabindex="-1"
				role="dialog" aria-labelledby="exampleModalCenterTitle"
				aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title" id="exampleModalCenterTitle" style="text-align: center">Игра окончена из за низкого баланса</h4>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							<img src="${pageContext.request.contextPath}/resources/images/sadFace.png"></img>
						</div>
						<div class="modal-footer">
							<div class="container">
								<button type="button" class="btn btn-outline-secondary"
									data-dismiss="modal">Начать заново</button>
								
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="input-group-append">
								<button id="gameOverButton" type="button" class="btn btn-outline-danger" style="display: none;"
									data-toggle="modal" data-target="#gameOver">gameOver</button>
							</div>
<div class="container-fluid ">
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		
		<sec:authentication property="authorities" var="roles" scope="page" /> <c:forEach var="role"
						items="${roles}">
						<c:choose>
							<c:when test="${role eq 'ADMIN'}">
								<a class="navbar-brand le" href="/"><img
								src="${pageContext.request.contextPath}/resources/images/logo.png"
								width="40" height="40">
								</a>
							</c:when>
								
							<c:when test="${role eq 'USER'}">
								<c:if test="${userType != 'admin'}">
									<a class="navbar-brand le" href="/office/myOffice"><img
										src="${pageContext.request.contextPath}/resources/images/logo.png"
										width="40" height="40">
									</a>
									<c:set var="userType" scope="session" value="${'user'}" />
								</c:if>
							</c:when>

							<c:otherwise>
								<a class="navbar-brand le" href="/"><img
										src="${pageContext.request.contextPath}/resources/images/logo.png"
										width="40" height="40">
								</a>
							</c:otherwise>
						</c:choose>
					</c:forEach> <%-- User greetings --%> 
		
		<p id="clockTimer" class="h5 timer"></p>
			
		<button class="navbar-toggler" type="button" style="font-size: 30px" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<i class="fas fa-list-ul"></i>
		</button>
		
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><%-- <sec:authentication property="authorities"
						var="roles" scope="page" /> <c:forEach var="role"
						items="${roles}">
						<c:choose>
							<c:when test="${role eq 'ADMIN'}">
								<li class="nav-item"><a class="nav-link" href="/">Админ</a></li>
								<c:set var="userType" scope="session" value="${'admin'}" />
							</c:when>

							<c:when test="${role eq 'USER'}">
								<c:if test="${userType != 'admin'}">
									<li class="nav-item"><a class="nav-link"
										href="/office/myOffice">Игрок</a></li>
									<c:set var="userType" scope="session" value="${'user'}" />
								</c:if>
							</c:when>

							<c:otherwise>
								<li class="nav-item"><a class="nav-link" href="/">Домой</a></li>
								<c:set var="userType" scope="session" value="${''}" />
							</c:otherwise>
						</c:choose>
					</c:forEach> User greetings  --%>
					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<form id="logoutForm" method="GET"
							action="${contextPath}/user/logout">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
						</form>
						<li class="nav-item" style="text-align: center"><a
							class="nav-link"
							href="${pageContext.request.contextPath}/user/profile"><i class="fas fa-user"></i> 
								${pageContext.request.userPrincipal.name}</a></li>
						<li class="nav-item" style="text-align: center">
						<a
							class="nav-link"
							href="${pageContext.request.contextPath}/user/logout"><i class="fas fa-sign-out-alt"></i> Выйти</a></li>
					</c:if>

					</li>
			</ul>
			<div class="nav-item" style="text-align: center"><a
							class="nav-link about"
							href="${pageContext.request.contextPath}/authors" ><i class="fas fa-users"></i> О проекте</a></div>
			<i class="fas fa-dollar-sign" style="color:green; font-size: 23px;margin-right: 3px"></i>
			<div  id="userMoney" class="userMoney" ></div>
			<i class="fab fa-bitcoin" style="color:gold; font-size: 23px;margin-right: 3px;"></i>
			<div id="userDonate" class="userMoney"></div>
			
			
		</div>
		
		<button id="notifyLB" class="navbar-toggler" onclick="hideNotify(true)" style="font-size: 30px;display: inline-block;width:60px" type="button" >
			<i id="notIcon" class="fas fa-bell" ></i>
			<!-- <span id="notifyCount" class="badge badge-pill badge-success" style="text-size: 5px"></span> -->
						<div id="noticeBlock"  class="noticeBlock">
					 		<div id="notices"  class="notices">
					 			<!--  <div class="notice">Проект здан</div> -->
					 		</div>
			 			</div>
		</button>
	 	 
	</nav>
</div>

<script type="text/javascript">
setNot();
jQuery(document).ready(function($) {
	setIcon();
});

	function setNot(){
		$.ajax({
			url : "${pageContext.request.contextPath}/userProjects/getNotice",
			//timeout : 100000,
			dataType : "json",
			success : function(data) {
				data = data['showNotify'];
				var noticeBlock = document.querySelector('#noticeBlock');
				
				
				if(data==true){
					noticeBlock.style.display="block";
					notifynumber=1;
				}
				else{
					noticeBlock.style.display="none"
				}
			},
			error : function(e) {
				console.log("ERROR: ", e);
				display(e);
			}
		});
	}
	function setIcon(){
		var notIcon = document.querySelector('#notIcon');
		var noticeBlock = document.querySelector('#noticeBlock');
		
		if(noticeBlock.style.display=="none"){
			notIcon.setAttribute('class', 'fas fa-bell-slash');
		}
		else{
			notIcon.setAttribute('class', 'fas fa-bell');
		}
	}
	function hideNotify(open){
		var notices = document.querySelector('#notices');
		var noticeBlock = document.querySelector('#noticeBlock');
		var notIcon = document.querySelector('#notIcon');
		if(noticeBlock.style.display=="none"){
			noticeBlock.style.display="block";
			$(notIcon).css("color", "grey");
			notIcon.setAttribute('class', 'fas fa-bell');
			notifynumber=1;
			$.ajax({
				type : "GET",
				url : "${pageContext.request.contextPath}/userProjects/showNotice",
				timeout : 100000,
				dataType : "json",
				
			});
		}
		else{
			noticeBlock.style.display="none"
			notIcon.setAttribute('class', 'fas fa-bell-slash');
				$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/userProjects/hideNotice",
					timeout : 100000,
					dataType : "json",
					
				});
		}
	}
	
	function setSize(){
			var noticeBlock = document.querySelector('.notices');
			var notices = noticeBlock.querySelectorAll('.notice');
			var notBlock = document.querySelector('.noticeBlock');
			/* var nottext = document.querySelector('#nottext'); */
			if(screen.width<600){
				$(notBlock).css("right",25);
				for(var i=0;i<notices.length;i++){
					notices[i].style.height=30+"px";
					notices[i].style.fontSize = 5+"px";
					/* nottext.innerHTML = "Увед." */
				}
			}
			else if(screen.width<990){
				$(notBlock).css("right",25);
				for(var i=0;i<notices.length;i++){
					notices[i].style.height=35+"px";
					notices[i].style.fontSize = 8+"px";
					/* nottext.innerHTML = "Уведомления" */
				}
			}
			else{
				for(var i=0;i<notices.length;i++){
					notices[i].style.height=100+"px";
					notices[i].style.fontSize = 120+"%";
					/* nottext.innerHTML = "Уведомления" */
				}
			}
		}
	$( window ).resize(function() {
		setSize();
    	});
	
	</script>