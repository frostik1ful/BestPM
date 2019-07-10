<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="userForm" value="${userForm}" />

<!-- image check on client side, size check also exists on server side -->
<script>
function readURL(input) {
    var type   = ['image/bmp','image/gif','image/jpg','image/jpeg','image/png'];
    var size   = 3 * 1024 * 1024; // bytes
    var file   = input.files[0];
    var prev   = document.getElementById('img_prev');
    function errMsg(x) {
        alert('Внимание! ' + x);
        prev.src = '';
        input.value = '';
    }
    if (type.indexOf(file.type) == -1) {
        errMsg('Неверный тип файла. Допустимые типы: bmp, gif, jpg, jpeg, png.');
        return false;
    } else if (file.size > size) {
        errMsg('Неверный размер файла. Максимальный размер: 3Мб');
        return false;
    } 
}
</script>


<br>
<div class="container" >
  <div class=".center-block" >
    <div class="col-md-9">
      <div class="panel panel-default">
        <div class="panel-body">
          <div class="row">
            <div class="col-md-12 lead">Профиль пользвателя<hr></div>
          </div>
          <div class="row">
			<div class="col-md-4 text-center">
              <img src="${appUrl}${userForm.userImage}" class="rounded" style="height: 220px; width: 220px;">
              Максимальный размер: 3Мб
              	<form method="POST" enctype="multipart/form-data" action="/user/imageUpload">
 					<br><input id="file2" type="file" name="file" accept="image/*" onchange="readURL(this)"/><br>
 					<br><input type="submit" value="Загрузить" onclick="return confirm('Вы хотите обновить картинку профиля?') ? true : false;"/>         
 					<input type="submit" value="Удалить" onclick="return confirm('Вы хотите удалить картинку профиля?') ? true : false;"/>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
				</form>
		
            </div>
            <div class="col-md-8">
              <div class="row">
                <div class="col-md-6">
                  <h1 class="only-bottom-margin"><c:out value="${userForm.username}" /></h1>
                </div>
              </div>
              <div class="row">
                <div class="col-md-6">
                  <span class="text-muted">Электронный адрес:</span> <c:out value="${userForm.email}"/><br>
                </div>
              </div>
              <div class="row">
                <div class="col-md-6">
                  <span class="text-muted">Баланс:</span> <c:out value="${userForm.balance}"/><br>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-12">
              <hr><form action="/user/updateProfile">
    				<button type="submit" class="btn btn-default pull-right"><i class="glyphicon glyphicon-pencil"></i> Редактировать профиль</button>
				  </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>