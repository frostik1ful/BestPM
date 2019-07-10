<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container text-info">
	<div class="row">
		<div class="col">
			<h1 class="display-1 text-danger">ERROR</h1>
		</div>
		<div class="w-100"></div>
		<div class="col">
			<h1 class="display-3">${excuses }</h1>
		</div>
		<div class="w-100"></div>
		<div class="col">${url }</div>
		<div class="col">${status }</div>
		<div class="w-100"></div>
	</div>
	<center>
		<p class="h1">
			<a href="${pageContext.request.contextPath}/office/myOffice"
				class="text-primary">Домой</a>
		</p>
	</center>
</div>