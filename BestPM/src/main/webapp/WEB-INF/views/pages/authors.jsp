<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style type="text/css">
.big {
	font-size: 50px;
	margin-right: 3px;
}
</style>
<h1>Авторы</h1>
<div class="container text-center">
	<div class="row">
		<div class="col">
			<i class="fas fa-chess-king big"></i>
			<h3>
				<i class="fas fa-binoculars big" title="Контроль качества"></i><i
					class="far fa-comments big" title="Консультации"></i><i
					class="far fa-compass big" title="Наставление"></i>
			</h3>
			<h3>
				<a href="https://www.linkedin.com/in/magistrus74/"
					class="badge badge-warning">Виталий Унгурян</a>
			</h3>
		</div>
	</div>
	<div class="row">


		<div class="col">
			<i class="fas fa-graduation-cap big"></i>
			<h3>
				<i class="fas fa-database big" title="База данных"></i><i
					class="fas fa-box-open big" title="Смерживание"></i><i
					class="fas fa-cloud-upload-alt big" title="Загрузка на сервер"></i>
			</h3>
			<h3>
				<a href="https://www.linkedin.com/in/christensensergey/"
					class="badge badge-warning">Сергей Христензен</a>
			</h3>
		</div>
		<div class="col">
			<i class="fas fa-graduation-cap big"></i>
			<h3>
				<i class="fas fa-clipboard-list big" title="Распредиление задач"></i><i
					class="fas fa-bug big" title="Поиск багов"></i><i
					class="far fa-compass big" title="Наставление"></i><i
					class="fas fa-box-open big" title="Смерживание"></i>
			</h3>
			<h3>
				<a href="https://www.linkedin.com/in/anton-tkachenko-373476144/"
					class="badge badge-warning">Антон Ткаченко</a>
			</h3>
		</div>
	</div>
	<div class="row">
		<div class="col">
			<i class="fas fa-graduation-cap big"></i>
			<h3>
				<i class="far fa-clock big" title="Игровое время"></i><i
					class="fas fa-cogs big" title="Механика"></i><i
					class="fas fa-cubes big" title="Сущности"></i>
			</h3>
			<h3>
				<a href="https://www.linkedin.com/in/dmitriy-tishchenko-031857162/"
					class="badge badge-warning">Дмитрий Тищенко</a>
			</h3>
		</div>
		<div class="col">
			<i class="fas fa-graduation-cap big"></i>
			<h3>
				<i class="far fa-credit-card big" title="Кредиты"></i><i
					class="fas fa-cubes big" title="Сущности"></i><i
					class="far fa-bell big" title="Уведомления"></i>
			</h3>
			<h3>
				<a href="https://www.linkedin.com/in/vladimir-yanko-76577354/"
					class="badge badge-warning">Владимир Янко</a>
			</h3>
		</div>
		<div class="col">
			<i class="fas fa-graduation-cap big"></i>
			<h3>
				<i class="fab fa-css3-alt big" title="Дизайн"></i>
			</h3>
			<h3>
				<a href="https://www.facebook.com/vladimir.nickola"
					class="badge badge-warning">Владимир Никола</a>
			</h3>
		</div>
	</div>
</div>
<br>
<div class="container text-left">
<br>
<b>Цель проекта:</b>
<br>
Написать веб приложение (экономический симулятор), которое позволит пользователю (игроку) почувствовать себя менеджером проектов и побывать в условиях, приближенных к реалиям рынка IT сферы.
<br>
<b>Функциональное назначение проекта:</b>
<br>
Проект представляет собой многопользовательскую веб игру, содержащую набор модулей, сущностей и связей между ними, что позволяет пользователю управлять игровой версией проектного менеджера IT компании. 
<br>
<b>Описание модулей проекта:</b>
<br>
<b>Профили пользователей</b>
<br>
Каждый пользователь имеет возможность стать участником проекта пройдя процедуру регистрации. При регистрации пользователю необходимо ввести уникальное имя пользователя содержащее  от 6 до 32 символов, уникальный почтовый адрес в формате **@**.** и пароль содержащий от 6 до 32 символов, после успешной регистрации пользователя происходит автоматический вход в игру. Пользователи имеют доступ к странице своего профиля, на которой содержатся логин, email, фото пользователя. В игре есть возможность восстановить пароль при помощи письма, которое будет отправлено на почтовый адрес пользователя (срок действия ссылки восстановления пароля равен 24 часам). Если срок действия ссылки восстановления истек - пользователь будет перенаправлен на страницу ввода электронной почты для восстановления пароля.
<br>
<b>Офисы</b>
<br>
В игре присутствуют три вида офисов (офис уровня 1 на 5 программистов, офис уровня 2 на 20 программистов, офис уровня 3 на 50 программистов), от которых зависит максимальное количество программистов нанимаемых пользователем и другие игровые модули. При старте игры все пользователи получают “офис уровня 1”.
Все пользователи могут покупать офисы более высокого уровня за игровую валюту.
<br>
<b>Рынок проектов</b>
<br>
В игре присутствует рынок проектов на котором пользователь может брать проекты для игры. Проекты генерируются автоматически или создаются администратором, и хранятся в базе данных.
<br>
<b>Рынок программистов</b>
<br>
В игре присутствует рынок программистов на котором пользователь может нанимать программистов для игры. Программисты генерируются автоматически или создаются администратором, и хранятся в базе данных. На рынке программистов пользователи игры видят 10 программистов, выбранных из базы данных случайным образом (в списке программистов присутствуют: не менее одного программиста типа senior или teamlead; не менее двух программистов типа middle; не менее трех программистов типа junior). Программисты после найма привязываются к пользователю, на всех программистов действуют мотиваторы привязанные к данному пользователю.
<br>
<b>Кредиты</b>
<br>
В игре игроки могут брать кредиты в игровой валюте. 
Кредиты генерируются автоматически или создаются администратором. На странице кредитов пользователи игры видят 10 кредитов, сгенерированных случайным образом. 
<br>
<b>Рынок мотиваторов</b>
<br>
В игре присутствует рынок мотиваторов, где игрок может покупать их за игровую валюту. Мотиваторы бывают двух типов: постоянного действия и временные.
Мотиваторы привязываются к пользователю, влияют на всех программистов пользователя, кроме тех на которых повлияло случайное событие. Все мотиваторы положительно влияют на уровень мотивации программистов. При покупке мотиватора игрок получает прибавку к мотивации программистов которая снижается на протяжении игры, но всегда имеет значение не меньше нуля. Мотиваторы генерируются автоматически или создаются администратором, и хранятся в базе данных. 
<br>
<b>Игровая механика</b>
<br>
В игре присутствует игровое время (игровые дни и игровые месяцы, состоящие из 30 игровых дней). Один игровой день равен одной минуте реального времени. В игре используются две игровые валюты: валюта “Баксы” - используется для начисления денег игроку и вычитания денег у игрока; валюта “Монеты” - покупается игроком за реальные деньги, используется для покупки мотиваторов и офисов.
<br> 
<b>А) Случайные события</b>
<br>
Случайные события генерируются автоматически для каждого пользователя через случайные промежутки времени на протяжении всей игры. Вероятность возникновения случайных событий влияющих на мотивацию программистов отрицательно больше чем вероятность возникновения положительных случайных событий.
Все события которые случились с игроком, хранятся в истории событий игрока, отображаемой в виде всплывающих сообщений на протяжении игры. Случайные события делятся на три группы по характеру воздействия на игровой процесс:
<br>
<b>А.1) Действующие на всех программистов игрока</b>
<br>
Изменяют уровень мотивации всех программистов одинаково.
<br>
<b>А.2) Действующие на одного программиста игрока</b>
<br>
Изменяют уровень мотивации одного программиста, выбранного случайным образом.
<br>
<b>А.3) Действующие на баланс игрока</b>
<br>
Изменяют баланс игрока случайным образом, но не более 5% от общего баланса. Как положительно (инвесторы), так и отрицательно (налоговая и т. д.).
<br>
<b>Б) Начисление денег</b>
<br>
Игроку начисляются деньги за выполнение проектов: 
<br>
За проекты с типом оплаты “TIMEMATERIAL” в первый день каждого игрового месяца;
<br>
За проекты с типом оплаты “FIXPRICE” когда значение “сложность проекта” снижается до нуля;
<br>
При возникновении случайного события положительно влияющего на баланс игрока.
<br>
<b>В) Вычитание денег</b>
<br>
У игрока вычитаются деньги:
<br>
Для выплаты зарплат программистам в первый день каждого игрового месяца;
<br>
Для выплат по действующим кредитам в первый день каждого игрового месяца;
<br>
При возникновении случайного события отрицательно влияющего на баланс игрока;
<br>
Для выплаты зарплат программистам, уволенным в течении игры в момент их увольнения за все неоплаченные дни которые работал программист;
<br>
<b>Управление игровыми сессиями (стоп игра)</b>
<br>
При входе в игру для игрока начинается игровая сессия (начинается отсчет игровых дней, начинает работать игровая механика). Игрок может закончить игровую сессию если выйдет из игры. Если игрок не совершал никаких действий в игре на протяжении 30 игровых дней – игровая сессия будет прервана сервером игры автоматически.
<br>
<b>Конец игры</b>
<br>
Если в процессе игры баланс игрока в валюте “Баксы” падает ниже "-10.000" происходит конец игры, а именно: игрок тераяет все приобретенные/полученные в ходе игры офисы, проекты, програмимстов, кредиты, мотиваторы. Игрок получает “офис уровня 1” и 5000 “Баксов” на игровой счет. Баланс “Монет” остается неизменным.
<br>
</div>