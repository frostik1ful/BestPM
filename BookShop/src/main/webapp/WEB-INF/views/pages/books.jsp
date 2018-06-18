<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>

<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<style type="text/css">
.back{
width: 100%;
height: 100%;
background-color: grey;
}
.bookBox{
	background-color: lightgrey;
	border: solid 2px;
	border-radius: 5px;
	width: 100%;
	height: 290px;
	
}
.myCol{
width:390px;
height: 320px;
border-radius: 8%
}
.bookImage {
	float: left;
	width:225px;
	height:100%;
	margin-left: 2px;
	background-image: url(images/book.png);
	background-repeat: no-repeat;
    display: inline-block;
	
}
</style>

</head>
<body>
<div class="container" style="margin-top: 15px">
<div class="row">
<c:if test="${not empty authorBooks || not empty genreBooks}">
	<c:if test="${not empty authorBooks}">
	<c:forEach items="${authorBooks}" var="book">
		<div class="col-sm-4 myCol" >
			<div class="bookBox">
				<div class="bookImage" >
					<td>Authors</td>
					<div class="input-group mb-3">
					  <div class="input-group-prepend">
					   
					  </div>
					  <select class="custom-select" size="4" id="inputGroupSelect01">
					  	<c:forEach items="${book.authors}" var="author">
					    	<option >${author.name}<option>
						</c:forEach>
					  </select>
					</div>
					<td>Genres</td>
					<div class="input-group mb-3">
					  <div class="input-group-prepend">
					   
					  </div>
					  <select class="custom-select" size="4" id="inputGroupSelect01">
					    <c:forEach items="${book.genres}" var="genre">
					    	<option >${genre.name}<option>
						</c:forEach>
					  </select>
					</div>
				</div>
				<button type="button" class="btn btn-outline-danger"
				data-toggle="modal" data-target="#confirmBuyA_${book.id }">Buy</button>
			</div>
		</div>
		
		<div class="modal fade" id="confirmBuyA_${book.id }"
						tabindex="-1" role="dialog"
						aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
						<div class="modal-dialog modal-dialog-centered" role="document">
							<div class="modal-content">
								<div class="modal-header container">
									<h5 class="modal-title" id="exampleModalCenterTitle">Create order</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<form method="POST" action="${pageContext.request.contextPath}/order/add">
								<div class="modal-body">
								
									<div class="form-group">
									<span>Enter your first name</span><br>
									<input type="hidden" name="bookId" value="${book.id}">
									<input type="text" required="required" name="firstName" class="form-control"
									placeholder="first name" id="name" />
									</div>
									<div class="form-group">
									<span>Enter your last name</span><br>
									<input type="text" required="required" name="lastName" class="form-control"
									placeholder="last name" id="lastname" />
									</div>
									<div class="form-group">
									<span>Enter your address</span><br>
									<input type="text" required="required" name="address" class="form-control"
									placeholder="address" id="address" />
									</div>
									<div class="form-group">
									<span>Enter quantity of books</span><br>
									<input type="number" required="required" name="quantity" class="form-control" id="quantity"
									min="1.0" step="1.0" />
									</div>
									
								</div>
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<button type="submit" class="btn btn-default">Buy</button>
									</div>
								</div>
								<div class="modal-footer">
									<div class="container">
										<button type="button" class="btn btn-outline-secondary"
											data-dismiss="modal">Cancel</button>
										
									</div>
								</div>
								</form>
							</div>
						</div>
					</div>
		
	</c:forEach>
	</c:if>
	<c:if test="${not empty genreBooks}">
	<c:forEach items="${genreBooks}" var="book">
		<div class="col-sm-4 myCol" >
			<div class="bookBox">
				<div class="bookImage" >
					<td>Authors</td>
					<div class="input-group mb-3">
					  <div class="input-group-prepend">
					   
					  </div>
					  <select class="custom-select" size="4" id="inputGroupSelect01">
					  	<c:forEach items="${book.authors}" var="author">
					    	<option >${author.name}<option>
						</c:forEach>
					  </select>
					</div>
					<td>Genres</td>
					<div class="input-group mb-3">
					  <div class="input-group-prepend">
					   
					  </div>
					  <select class="custom-select" size="4" id="inputGroupSelect01">
					    <c:forEach items="${book.genres}" var="genre">
					    	<option >${genre.name}<option>
						</c:forEach>
					  </select>
					</div>
				</div>
				<button type="button" class="btn btn-outline-danger"
				data-toggle="modal" data-target="#confirmBuyG_${book.id }">Buy</button>
			</div>
		</div>
		
		<div class="modal fade" id="confirmBuyG_${book.id }"
						tabindex="-1" role="dialog"
						aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
						<div class="modal-dialog modal-dialog-centered" role="document">
							<div class="modal-content">
								<div class="modal-header container">
									<h5 class="modal-title" id="exampleModalCenterTitle">Create order</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<form method="POST" action="${pageContext.request.contextPath}/order/add">
								<div class="modal-body">
								
									<div class="form-group">
									<span>Enter your first name</span><br>
									<input type="hidden" name="bookId" value="${book.id}">
									<input type="text" required="required" name="firstName" class="form-control"
									placeholder="first name" id="name" />
									</div>
									<div class="form-group">
									<span>Enter your last name</span><br>
									<input type="text" required="required" name="lastName" class="form-control"
									placeholder="last name" id="lastname" />
									</div>
									<div class="form-group">
									<span>Enter your address</span><br>
									<input type="text" required="required" name="address" class="form-control"
									placeholder="address" id="address" />
									</div>
									<div class="form-group">
									<span>Enter quantity of books</span><br>
									<input type="number" required="required" name="quantity" class="form-control" id="quantity"
									min="1.0" step="1.0" />
									</div>
									
								</div>
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<button type="submit" class="btn btn-default">Buy</button>
									</div>
								</div>
								<div class="modal-footer">
									<div class="container">
										<button type="button" class="btn btn-outline-secondary"
											data-dismiss="modal">Cancel</button>
										
									</div>
								</div>
								</form>
							</div>
						</div>
					</div>
		
	</c:forEach>
	</c:if>
</c:if>
<c:if test="${empty adminBooks}">



	<c:forEach items="${books}" var="book">
		<div class="col-sm-4 myCol" >
			<div class="bookBox">
				<div class="bookImage"  >
					
					<td>Authors</td>
					<div class="input-group mb-3">
					  <div class="input-group-prepend">
					   
					  </div>
					  <select class="custom-select" size="4" id="inputGroupSelect01">
					  	<c:forEach items="${book.authors}" var="author">
					    	<option >${author.name}<option>
						</c:forEach>
					  </select>
					</div>
					<td>Genres</td>
					<div class="input-group mb-3">
					  <div class="input-group-prepend">
					   
					  </div>
					  <select class="custom-select" size="4" id="inputGroupSelect01">
					    <c:forEach items="${book.genres}" var="genre">
					    	<option >${genre.name}<option>
						</c:forEach>
					  </select>
					</div>
				</div>
				<h5><span class="badge badge-dark">${book.name}</span></h5>
				<h5><span class="badge badge-dark">${book.price}$</span></h5>
				<button type="button" class="btn btn-outline-danger"
				data-toggle="modal" data-target="#confirmBuy_${book.id }">Buy</button>
				
			</div>
		</div>
		
		<div class="modal fade" id="confirmBuy_${book.id }"
						tabindex="-1" role="dialog"
						aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
						<div class="modal-dialog modal-dialog-centered" role="document">
							<div class="modal-content">
								<div class="modal-header container">
									<h5 class="modal-title" id="exampleModalCenterTitle">Create order</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<form method="POST" action="${pageContext.request.contextPath}/order/add">
								<div class="modal-body">
									
									<div class="form-group">
									<span>Enter your first name</span><br>
									<input type="hidden" name="bookId" value="${book.id}">
									<input type="text" required="required" name="firstName" class="form-control"
									placeholder="first name" id="name" />
									</div>
									<div class="form-group">
									<span>Enter your last name</span><br>
									<input type="text" required="required" name="lastName" class="form-control"
									placeholder="last name" id="lastname" />
									</div>
									<div class="form-group">
									<span>Enter your address</span><br>
									<input type="text" required="required" name="address" class="form-control"
									placeholder="address" id="address" />
									</div>
									<div class="form-group">
									<span>Enter quantity of books</span><br>
									<input type="number" required="required" name="quantity" class="form-control" id="quantity"
									min="1" step="1" />
									</div>
									
								</div>
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<button type="submit" class="btn btn-default">Buy</button>
									</div>
								</div>
								<div class="modal-footer">
									<div class="container">
										<button type="button" class="btn btn-outline-secondary"
											data-dismiss="modal">Cancel</button>
										
									</div>
								</div>
								</form>
							</div>
						</div>
					</div>
		
	</c:forEach>
	

</c:if>
<c:if test="${not empty adminBooks}">
	<div class="form-group">
		<input type="text" class="form-control pull-right" id="search"
			placeholder="Search">
	</div>
	<%-- --%>
	<div class="table-responsive">
		<table id="escalation" class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>Name</th>
					<th>Price</th>
					<th>Description</th>
					<th>Authors</th>
					<th>Genres</th>
					<th>Action</th>


				</tr>
			</thead>
			<tbody>
				<c:forEach items="${adminBooks}" var="book">
					<tr>
						<td>${book.name}</td>
						<td>${book.price}</td>
						<td>${book.description}</td>
						<td>
							<c:forEach items="${book.authors}" var="authorz">
								${authorz.name},
							</c:forEach>	
						</td>
						<td>
						<c:forEach items="${book.genres}" var="genrez">
								${genrez.name},
							</c:forEach>
						</td>
						<td colspan="2" style="width: 50px;">
							<form action="${pageContext.request.contextPath}/deleteBook"
								id="formDelete_${book.id }">
								<input type="hidden" name="bookId" value="${book.id}">
							</form>
							<form action="${pageContext.request.contextPath}/editBook"
								id="formEdit_${book.id }">
								<input type="hidden" name="editBookId" value="${book.id}">
							</form>
							<div class="input-group-append">
								<button form="formEdit_${book.id }" type="submit"
									class="btn btn-outline-secondary">Edit</button>
								<button type="button" class="btn btn-outline-danger"
									data-toggle="modal" data-target="#confirmDelete_${book.id }">Delete</button>
							</div>
						</td>
					</tr>
					<!-- Modal -->
					<div class="modal fade" id="confirmDelete_${book.id }"
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
									<span>Delete this book?</span><br>
									<p class="font-weight-bold text-danger">${book.name }</p>
								</div>
								<div class="modal-footer">
									<div class="container">
										<button type="button" class="btn btn-outline-secondary"
											data-dismiss="modal">Cancel</button>
										<button form="formDelete_${book.id }" type="submit"
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
<c:if test="${not empty editBookId}">
	<div class="container">
	<h1><span class="badge badge-dark">Edit Book</span></h1>
	<div class="formbox">
		<form method="POST"
			action="${pageContext.request.contextPath}/saveChangesBook">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<%-- --%>

			<div class="form-group">
				<input type="hidden" name="editBookId" value="${editBookId}">
				<label for="name">BookName:</label> <input type="text"
					required="required" name="name" value="${name}"
					class="form-control" placeholder="Enter book name"
					id="name" />
			</div>

			<div class="form-group">
				<label for="price">BookPrice:</label> <input type="number"
					required="required" name="price" value="${price}"
					class="form-control" id="price" min="1.0" step="1" />
			</div>

			<div class="form-group">
				<label for="money">Description:</label> <input type="text"
					required="required" name="description" value="${description}"
					class="form-control" placeholder="Enter description"
					id="description" />
			</div>
			
			<h2><span class="badge badge-dark">Authors</span></h2>
			
			<div class="form-group">
			<input type="checkbox" value="-1" checked="checked" style="display: none" name="authorsId" class="form-check-input" id="authors" />
				<c:forEach items="${authors}" var="author">
					<div class="form-check">
						<input type="checkbox" value="${author.id}" name="authorsId" class="form-check-input" id="authors" />
						<label class="form-check-label">${author.name}</label> 
					</div>
				</c:forEach>
				<c:forEach items="${bookAuthors}" var="bookAuthor">
					<div class="form-check">
						<input type="checkbox" value="${bookAuthor.id}" checked="checked" name="authorsId" class="form-check-input" id="authors" />
						<label class="form-check-label">${bookAuthor.name}</label> 
					</div>
				</c:forEach>
			</div>
			
			<h2><span class="badge badge-dark">Genres</span></h2>
			<div class="form-group">
			<input type="checkbox" value="-1" checked="checked" style="display: none" name="genresId" class="form-check-input" id="genres" />
				<c:forEach items="${genres}" var="genre">
					<div class="form-check">
						<input type="checkbox" value="${genre.id}" name="genresId" class="form-check-input" id="genres" />
						<label class="form-check-label">${genre.name}</label> 
					</div>
				</c:forEach>
				<c:forEach items="${bookGenres}" var="bookGenre">
					<div class="form-check">
						<input type="checkbox" value="${bookGenre.id}" checked="checked" name="genresId" class="form-check-input" id="genres" />
						<label class="form-check-label">${bookGenre.name}</label> 
					</div>
				</c:forEach>
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
<c:if test="${not empty newBook}">
	<div class="container">
	<h1><span class="badge badge-warning">New Book</span></h1>
	<div class="formbox">
		<form method="POST"
			action="${pageContext.request.contextPath}/addBook">
			
			<div class="form-group">
				<label for="name">BookName:</label> <input type="text"
					required="required" name="name" 
					class="form-control" placeholder="Enter book name"
					id="name" />
			</div>

			<div class="form-group">
				<label for="price">BookPrice:</label> <input type="number"
					required="required" name="price" 
					class="form-control" placeholder="Enter price" id="price" min="1.0" step="1" />
			</div>

			<div class="form-group">
				<label for="money">Description:</label> <input type="text"
					required="required" name="description" 
					class="form-control" placeholder="Enter description"
					id="description" />
			</div>
			
			<h2><span class="badge badge-dark">Authors</span></h2>
			
			
			<div class="form-group">
			
				<input type="checkbox" value="-1" checked="checked" style="display: none" name="authorsId" class="form-check-input" id="authors" />
				<c:forEach items="${authors}" var="author">
				<div class="form-check">
					<input type="checkbox" value="${author.id}" name="authorsId" class="form-check-input" id="authors" />
					<label class="form-check-label">${author.name}</label> 
				</div>
				</c:forEach>
			</div>
			
			<h2><span class="badge badge-dark">Genres</span></h2>
			<div class="form-group">
			
				<input type="checkbox" value="-1" checked="checked" style="display: none" name="genresId" class="form-check-input" id="genres" />
				<c:forEach items="${genres}" var="genre">
				<div class="form-check">
				<input type="checkbox" value="${genre.id}" name="genresId" class="form-check-input" id="genres" />
				<label class="form-check-label">${genre.name}</label> 
				</div>
				</c:forEach>
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
	
</div>
</body>
</html>
<script>
	$(document).ready(function() {
		$('#escalation').DataTable();
	});
</script>
<script>
	$(document).ready(
			function() {
				$("#search").keyup(
						function() {
							_this = this;
							$.each($("#escalation tbody tr"), function() {
								if ($(this).text().toLowerCase().indexOf(
										$(_this).val().toLowerCase()) === -1) {
									$(this).hide();
								} else {
									$(this).show();
								}
							});
						});
			});
</script>