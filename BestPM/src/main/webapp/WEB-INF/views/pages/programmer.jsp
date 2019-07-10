<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="container">
<h2>Добавить нового программиста</h2>
<div class="form-group">
	<form action="add">
		<label class="control-label col-sm-2" for="name">Имя:</label>
		<div class="col-sm-10">
			<input type="text" class="form-control" placeholder="Введите имя"
				name="name">
		</div>
		<label class="control-label col-sm-2" for="number">Опыт:</label>
		<div class="col-sm-10">
			<input type="number" class="form-control"
				placeholder="Введите опыт программиста" name="exp">
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" value="Save" class="btn sucess">Создать</button>
			</div>
		</div>

	</form>
</div>
</div>
