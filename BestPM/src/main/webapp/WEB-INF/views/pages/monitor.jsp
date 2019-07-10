<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<img
	src="${pageContext.request.contextPath}/resources/images/Monitor.jpg"
	class="img-fluid" alt="Monitor image" usemap="#monitor">
<map name="monitor">
	<area title="Мои Пограммисты"
		href="${pageContext.request.contextPath}/user/myProgrammers"
		coords="168,279,324,277,324,192,269,184,279,169,299,128,314,116,292,99,281,67,258,55,209,50,214,77,201,99,189,109,202,135,210,155,221,165,226,182,170,194"
		shape="poly" shape="poly">
	<area title="Мои Мотиваторы"
		href="${pageContext.request.contextPath}/user/myMotivators"
		coords="246,465,95" shape="circle">
	<area title="Мои Кредиты"
		href="${pageContext.request.contextPath}/credit/new"
		coords="357,914,139,723" shape="rect">
	<area title="Новый Офис"
		href="${pageContext.request.contextPath}/office/market"
		coords="1559,742,1750,934" shape="rect">
	<area title="Рынок проектов"
		href="${pageContext.request.contextPath}/projects/home"
		coords="1527,246,1757,90" shape="rect">
</map>