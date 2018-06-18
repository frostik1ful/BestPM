<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="jumbotron" style="margin-bottom: 0">
  <div class="container text-center">
    <h1>BookShop</h1>      
    <p>Books,Books & Books</p>
  </div>
</div>
<nav class="navbar navbar-expand-lg navbar navbar-dark bg-dark container">
	<a class="navbar-brand" href="${pageContext.request.contextPath}/">Home</a>
	
	<div class="dropdown">
		  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    Genre
		  </button>
		  
		  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		  	
				<c:forEach items="${genres}" var="genre">
					<a class="dropdown-item" href="/BookShop/genreSort?genreId=${genre.id}"  >${genre.name}</a>
				  	
			    </c:forEach>
		
		  </div>
	</div>
	
	<div class="dropdown">
		  <button class="btn btn-secondary dropdown-toggle" style="margin-left: 15px" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    Author
		  </button>
		  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		  <c:forEach items="${authors}" var="author">
		  	<input type="hidden" name="authorId" value="${author.id}">
		  	<a class="dropdown-item" href="/BookShop/authorSort?authorId=${author.id}">${author.name}</a>
		  </c:forEach>
		  </div>
	</div>
	<a class="navbar-brand" style="margin-left: 60%" href="${pageContext.request.contextPath}/admin/">Admin</a>
	
</nav>