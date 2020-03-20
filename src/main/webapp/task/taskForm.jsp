<%@ page session="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../header.jsp" />

<head>
	<script type="text/javascript">
			$('.navBarItem').removeClass('active');
			$('.taskItem').addClass('active');
	</script>
</head>


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
	<c:choose>
		<c:when test="${taskForm['new']}">
			<h1>Add Task</h1>
		</c:when>
		<c:otherwise>
			<h1>Update Task</h1>
		</c:otherwise>
	</c:choose>
	<br />

	<spring:url value="/task/task" var="taskActionUrl" />

	<form:form class="form-horizontal" 
			   method="post" 
			   modelAttribute="taskForm" 
			   action="${taskActionUrl}">
		
		<form:hidden path="id" />
		
		<spring:bind path="descr">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Descr</label>
				<div class="col-sm-10">
					<form:input path="descr" 
							 	type="text" 
							 	class="form-control " 
							 	id="descr" 
							 	placeholder="descr"
					/>
					<form:errors path="descr" class="control-label" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="startdate">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Start Date</label>
				<div class="col-sm-10">
					<form:input type="date" 
							    path="startdate" 
							    class="form-control wid20" 
							    id="startdate" 
							    placeholder="startdate"
					/>
					<form:errors path="startdate" class="control-label" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="enddate">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">End Date</label>
				<div class="col-sm-10">
				
					<form:input type="date" 
								path="enddate" 
								class="form-control wid20" 
								id="enddate" 
								placeholder="enddate" 
					/>
					<form:errors path="enddate" class="control-label" /> 
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="taskstatus">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Status</label>
				<div class="col-sm-5">
					<form:select path="taskstatus" 
								 class="form-control" 
								 items="${taskStatusList}" 
								 itemValue="value">
					</form:select>
					<form:errors path="taskstatus" class="control-label" />
				</div>
				<div class="col-sm-5"></div>
			</div>
		</spring:bind>
		


		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${taskForm['new']}">
						<button type="submit" class="btn btn-primary pull-right"
						>
							<span class="glyphicon glyphicon-plus marRight5"></span>
							Add
						</button>
					</c:when>
					<c:otherwise>
						<button type="submit" class="btn btn-primary pull-right"
						>
							<span class="glyphicon glyphicon-edit marRight5"></span>
							Update
						</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>

</div>

<jsp:include page="../footer.jsp" />

</body>
</html>