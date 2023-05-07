<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title> Student Tracker App </title>
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>GPIG Latifabad, Hyderabad</h2>
		</div> 
	</div>

	<div id="container">
	
		<div id="content">
			
			<input type="button" value="Add Student" 
				onclick="window.location.href='add-student-form.jsp'"
				class="add-student-button"
			/>
			
			<table class="table">
				<thead>
				<tr> 
					<th> First Name </th>
					<th> Last Name </th>
					<th> Email </th>
				</tr>
				</thead>
				<c:forEach var="tempStudent" items="${STUDENT_LIST }">	
					<tbody>
					<tr> 
						<td> ${tempStudent.firstName}  </td>
						<td> ${tempStudent.lastName}  </td>
						<td> ${tempStudent.email}  </td>
					</tr>
					</tbody>
				</c:forEach>
			
			</table>
		
		</div>
	
	</div>
</body>


</html>