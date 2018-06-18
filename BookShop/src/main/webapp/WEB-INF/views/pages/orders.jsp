<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="spring"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="container" style="margin-top: 15px">
<div class="row">
	<div class="table-responsive">
		<table id="escalation" class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>Id</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Address</th>
					<th>Book Name</th>
					<th>Book Id</th>
					<th>Quantity</th>
					<th>Action</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orders}" var="order">
					<tr>
						<td>${order.id}</td>
						<td>${order.firstName}</td>
						<td>${order.lastName}</td>
						<td>${order.address}</td>
						<td>${order.bookName}</td>
						<td>${order.bookId}</td>
						<td>${order.quantity}</td>
						<td colspan="2" style="width: 50px;">
							<form action="${pageContext.request.contextPath}/order/delete"
								id="formDelete_${order.id }">
								<input type="hidden" name="orderId" value="${order.id}">
							</form>
							
							<div class="input-group-append">
								<button type="button" class="btn btn-outline-danger"
									data-toggle="modal" data-target="#confirmDelete_${order.id }">Delete</button>
							</div>
						</td>
						</tr>
						<div class="modal fade" id="confirmDelete_${order.id }"
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
									<span>Delete this order?</span><br>
									<p class="font-weight-bold text-danger">${order.id }</p>
								</div>
								<div class="modal-footer">
									<div class="container">
										<button type="button" class="btn btn-outline-secondary"
											data-dismiss="modal">Cancel</button>
										<button form="formDelete_${order.id }" type="submit"
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
</div>
</div>
</body>
</html>