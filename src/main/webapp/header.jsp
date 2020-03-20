<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>
<title>ToDoList App</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<spring:url value="/resources/css/common.css" var="coreCss" />
<spring:url value="/resources/css/bootstrap.min.css"
	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
<link rel='icon' href='../resources/images/icon.png' type='image/x-icon' />
</head>

<spring:url value="/user/user" var="urlHome" />
<spring:url value="/user/user" var="urlUser" />
<spring:url value="/logout" var="urlLogout" />
<spring:url value="/task/task" var="urlTask" />

<nav class="navbar navbar-inverse ">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${urlHome}">
				<img class="homePagePNG" alt="icon" src="/resources/images/icon.png"/>ToDo List App</a>
		</div>
		<div id="navbar">
			<ul class="nav navbar-nav navbar-right">
				<li class="logoutItem navBarItem">
					<a href="${urlLogout}">
						<span class="glyphicon glyphicon-log-out marLeft5"></span>
						Logout
					</a>
				</li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="userItem navBarItem">
					<a href="${urlUser}">
						<span class="glyphicon glyphicon-user marRight5"></span>User
					</a>
				</li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="taskItem navBarItem">
					<a href="${urlTask}">
					<span class="glyphicon glyphicon-tasks marRight5"></span>
						Task
					</a>
				</li>
			</ul>
		</div>
	</div>
</nav>