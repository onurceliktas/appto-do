<%@ page session="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="../header.jsp" />
<head>
    <meta charset="utf-8">
    <title>Task Management</title>
	
	<script type="text/javascript">
		$('.navBarItem').removeClass('active');
		$('.taskItem').addClass('active');
	</script>	
    
    
    
</head>
<body>
  <div class="container">
  
  		<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" 
						class="close" 
						data-dismiss="alert" 
						aria-label="Close"
				>
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>


		<h1>All Tasks</h1>
		
		<spring:url value="/task/addTask" var="newUrl" />
		<button class="btn btn-primary newTask" 
				onclick="location.href='${newUrl}'"
		>
		<span class="glyphicon glyphicon-plus marRight5"></span>
				New Task
		</button>
		
		
		<spring:url value="/task/filter" var="filterUrl" />
		<form:form class="form-horizontal" 
				   method="get" 
				   modelAttribute="filterTaskAtt" 
				   action="${filterUrl}">
		<table class="table table-striped">
				<tr>
						<td >
							<label class="col-sm-3 control-label">Start Date</label>
								<div class="col-sm-9">
									<form:input type="date" path="startdate"
											    class="form-control" 
											    id="startdate" 
											    placeholder="startdate"
									/>
								</div>
						</td>
						<td>
							<label class="col-sm-3 control-label">End Date</label>
								<div class="col-sm-9">
									<form:input type="date" path="enddate"
											    class="form-control" 
											    id="enddate" 
											    placeholder="enddate"
									/>
								</div>
						</td>
						
						<td>
							<button class="btn btn-primary" onclick="location.href='${filterUrl}'"
							>
								<span class="glyphicon glyphicon-search marRight5"></span>
								Filter Task
							</button>
						</td>
				</tr>
			</table>
		</form:form>
		
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Descr</th>
					<th>Start Date</th>
					<th>End Date</th>
					<th>Task Status</th>
					<c:choose>
						<c:when test="${adminControl}">
							<th>Owner</th>
						</c:when>
					</c:choose>
					
					<th>Action</th>
							
					
				</tr>
			</thead>

			<c:forEach var="task" items="${tasks}">
				<tr>
					<td>${task.shortDescr}</td>
					<td>${task.startdate}</td>
					<td>${task.enddate}</td>
					<td>${task.viewTaskStatus}</td>
					<c:choose>
						<c:when test="${adminControl}">
							<td>${task.owner.firstname} ${task.owner.lastname}</td>
						</c:when>
					</c:choose>
						
					
					<td>
						<c:choose>
							<c:when test="${task.editable}">	
								
							
									<spring:url value="/task/${task.id}/update" var="updateUrl" />
									<button class="btn btn-primary" 
											onclick="location.href='${updateUrl}'"
									>
										<span class="glyphicon glyphicon-edit marRight5"></span>
										Update
									</button>
									
									<spring:url value="/task/${task.id}/delete" var="deleteUrl" /> 
									<button class="btn btn-danger" 
											onclick="this.disabled=true;location.href='${deleteUrl}'"
									>
										<span class="glyphicon glyphicon-remove marRight5"></span>
										Delete
									</button>
									
							 	
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${!task.editable}">	
									<spring:url value="/task/${task.id}/update" 
											 	var="updateUrl"
									/>
									<button disabled="disabled" 
											class="btn btn-primary" 
											onclick="location.href='${updateUrl}'"
									>
										<span class="glyphicon glyphicon-edit marRight5"></span>
										Update
									</button>
									
									<spring:url value="/task/${task.id}/delete" var="deleteUrl" /> 
									<button disabled="disabled" class="btn btn-danger" 
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
