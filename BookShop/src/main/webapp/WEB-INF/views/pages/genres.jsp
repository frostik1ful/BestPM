<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="container">
	
<c:if test="${empty genres && empty editGenreId}">
	<div class="container">
	<div class="formbox">
		<form method="POST"
			action="${pageContext.request.contextPath}/genre/add">
			<div class="form-group">
				<label for="name">Genre Name:</label> <input type="text"
					required="required" name="name" 
					class="form-control" placeholder="Enter genre name"
					id="name" />
			</div>
<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-default">Save</button>
				</div>
			</div>

		</form>
	</div>
	</div>
</c:if>
<c:if test="${not empty genres}">
	<div class="form-group">
		<input type="text" class="form-control pull-right" id="search"
			placeholder="Search">
	</div>
	<div class="table-responsive">
		<table id="escalation" class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>Name</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${genres}" var="genre">
					<tr>
						<td>${genre.name}</td>
						<td colspan="2" style="width: 50px;">
							<form action="${pageContext.request.contextPath}/genre/delete"
								id="formDelete_${genre.id }">
								<input type="hidden" name="genreId" value="${genre.id}">
							</form>
							<form action="${pageContext.request.contextPath}/genre/edit"
								id="formEdit_${genre.id }">
								<input type="hidden" name="genreId" value="${genre.id}">
							</form>
							<div class="input-group-append">
								<button form="formEdit_${genre.id }" type="submit"
									class="btn btn-outline-secondary">Edit</button>
								<button type="button" class="btn btn-outline-danger"
									data-toggle="modal" data-target="#confirmDelete_${genre.id }">Delete</button>
							</div>
						</td>
					</tr>
					<div class="modal fade" id="confirmDelete_${genre.id }"
						tabindex="-1" role="dialog"
						aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
						<div class="modal-dialog modal-dialog-centered" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalCenterTitle">Confirm Delete</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									<span>Delete this genre?</span><br>
									<p class="font-weight-bold text-danger">${genre.name }</p>
								</div>
								<div class="modal-footer">
									<div class="container">
										<button type="button" class="btn btn-outline-secondary"
											data-dismiss="modal">Cancel</button>
										<button form="formDelete_${genre.id }" type="submit"
											class="btn btn-outline-danger">Delete</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
</c:if>
<c:if test="${not empty editGenreId}">
	<div class="container">
	<div class="formbox">
		<form method="POST"
			action="${pageContext.request.contextPath}/genre/saveChanges">
			

			<div class="form-group">
				<input type="hidden" name="genreId" value="${editGenreId}">
				<label for="name">Genre Name:</label> <input type="text"
					required="required" name="name" value="${genreName}"
					class="form-control" placeholder="Enter genre name"
					id="name" />
			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-default">Save</button>
				</div>
			</div>

		</form>
	</div>
	</div>
</c:if>
</div>
</body>
</html>