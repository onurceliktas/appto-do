<%@ page session="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="../header.jsp" />
<head>
    <meta charset="utf-8">
    <title>User List</title>
    <script type="text/javascript">
    	$('.navBarItem').removeClass('active');
    	$('.userItem').addClass('active');
    </script>
</head>
<body>
  <div class="container">
		<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" 
						class="close" 
						data-dismiss="alert" 
						aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>

		<h1>All Users</h1>
		<c:choose>
			<c:when test="${adminControl}">
				<spring:url value="/user/addUser" var="newUrl" />
				<button class="btn btn-primary" 
						onclick="location.href='${newUrl}'"
				>
				<span class="glyphicon glyphicon-plus marRight5"></span>
					New User
				</button>
			</c:when>
		</c:choose>
		

		<table class="table table-striped">
			<thead>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>User Name</th>
					<th>Status</th>
					<th>Action</th>
				</tr>
			</thead>

			<c:forEach var="user" items="${users}">
				<tr>
					<td>${user.firstname}</td>
					<td>${user.lastname}</td>
					<td>${user.username}</td>
					<td>${user.viewStatus}</td>
					<td>
						
						<spring:url value="/user/${user.id}/update" var="updateUrl" />
						<button class="btn btn-primary" 
								onclick="location.href='${updateUrl}'"
						>
						<span class="glyphicon glyphicon-edit marRight5"></span>
							Update
						</button>
						
						<c:choose>
							<c:when test="${adminControl}">
								<spring:url value="/user/${user.id}/delete" var="deleteUrl" /> 
								<button class="btn btn-danger" 
										onclick="this.disabled=true;location.href='${deleteUrl}'"
								>
								<span class="glyphicon glyphicon-remove marRight5"></span>
									Delete
								</button>
							</c:when>
						</c:choose>
						
				 	</td>
				</tr>
			</c:forEach>
		</table>

	</div>
  <jsp:include page="../footer.jsp" />
</body>
</html>
