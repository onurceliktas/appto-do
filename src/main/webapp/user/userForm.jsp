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
    	$('.userItem').addClass('active');
    </script>
</head>

<div class="container">

	<c:choose>
		<c:when test="${userForm['new']}">
			<h1>Add User</h1>
		</c:when>
		<c:otherwise>
			<h1>Update User</h1>
		</c:otherwise>
	</c:choose>
	<br />
	
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

	<spring:url value="/user/user" var="userActionUrl" />

	<form:form class="form-horizontal" 
			   method="post" 
			   modelAttribute="userForm" 
			   action="${userActionUrl}">
		
		<form:hidden path="id" />
		
		<spring:bind path="firstname">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">First Name</label>
				<div class="col-sm-10">
					<form:input path="firstname" 
								type="text" 
								class="form-control " 
								id="firstname" 
								placeholder="First Name" 
					/>
					<form:errors path="firstname" class="control-label" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="lastname">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Last Name</label>
				<div class="col-sm-10">
					<form:input path="lastname" 
								type="text" 
								class="form-control " 
								id="lastname"
								placeholder="Last Name" 
					/>
					<form:errors path="lastname" class="control-label" />
				</div>
			</div>
		</spring:bind>
		
		
		<c:choose>
			<c:when test="${adminControl}">
				<spring:bind path="username">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">User Name</label>
						<div class="col-sm-10">
							<form:input path="username" 
										type="text" 
										class="form-control" 
										id="username" 
										placeholder="User Name" 
							/>
							<form:errors path="username" class="control-label" />
						</div>
					</div>
				</spring:bind>
			</c:when>
		</c:choose>
		
		
		
		<c:choose>
			<c:when test="${userForm['admin']}">
			</c:when>
			<c:otherwise>
				<spring:bind path="password">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Password</label>
						<div class="col-sm-10">
							<form:password path="password" 
										   class="form-control" 
										   id="password" 
										   placeholder="Password" 
							/>
							<form:errors path="password" class="control-label" />
						</div>
					</div>
				</spring:bind>
			</c:otherwise>
		</c:choose>
		
		
		<c:choose>
			<c:when test="${adminControl}">
				<spring:bind path="status">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Status</label>
						<div class="col-sm-5">
							<form:select path="status" 
										 class="form-control" 
										 items="${statusList}" 
										 itemValue="value"
							>
							</form:select>
							<form:errors path="status" class="control-label" />
						</div>
						<div class="col-sm-5">
							<label class="control-label">(Passive users can not be login!)</label>
						</div>
					</div>
				</spring:bind>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
			
		
		<c:choose>
			<c:when test="${adminControl}">
				<spring:bind path="role">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Role</label>
						<div class="col-sm-5">
							<form:select path="role" 
										 class="form-control" 
										 items="${roleList}"  
										 itemLabel="name" 
							>
							</form:select>
							<form:errors path="role" class="control-label" />
						</div>
						<div class="col-sm-5"></div>
					</div>
				</spring:bind>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>


		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${userForm['new']}">
						<button type="submit" 
								class="btn btn-primary pull-right"
						>
						<span class="glyphicon glyphicon-plus marRight5"></span>
							Add
						</button>
					</c:when>
					<c:otherwise>
						<button type="submit" 
								class="btn btn-primary pull-right"
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