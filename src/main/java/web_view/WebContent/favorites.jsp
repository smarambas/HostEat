<%@page import="bean.UserBean"%>
<%@page import="bean.SessionBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page errorPage = "error_page.jsp" %>

<!DOCTYPE html>
<html lang="">
<head>
	<meta charset="UTF-8">
	<title>HostEat</title>
	
	<link rel="stylesheet" type="text/css" href="stylesheet.css" media="screen"/>
</head>
<body>
	<h2>HostEat</h2>
	<p>
	<div class="header">
		<input type="button" id="btn" value="Homepage" onclick="window.location.href='homepage.jsp'">
		<input type="button" id="btn" value="Userpage" onclick="window.location.href='userpage.jsp'">
		<input type="button" id="btn" value="Notifications" onclick="window.location.href='notifications.jsp'">
		<input type="button" id="btn" value="Favorites" onclick="window.location.href='favorites.jsp'">
	</div>
	<p>
	<div class="container">
		<strong id="pagetitle">Favorites</strong>
		<br><br>
<%
		SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");
		
		int i = 0;
		for(UserBean ub : sessionBean.getSavedHosts()) {
			out.println("<p class=\"hostname\">" + ub.getName() + " " + ub.getSurname());
// 			out.println("<br>");
			out.println("<p>" + ub.getReg() + " (" + ub.getProv() + ")");
			out.println("<br>");
%>
			<table class="footer">
				<caption></caption>
				<tr>
				<th id="dummy"></th>
				</tr>
				<tr>
				<td>
					<form action="favorites.jsp">
						<input type="submit" id="btn" value="View profile" name="profile<%=i%>">
<%
						if(request.getParameter("profile" + i) != null) {
							session.setAttribute("selectedHost", ub);
%>
							<jsp:forward page="host_profile.jsp"></jsp:forward>
<%
						}
%>
					</form>
				</td>
				<td>
					<form action="favorites.jsp">
						<input type="submit" id="btn" value="View events" name="events<%=i%>">
<%						
						if(request.getParameter("events" + i) != null) {
							session.setAttribute("selectedHost", ub);
%>
							<jsp:forward page="host_events.jsp"></jsp:forward>
<%
						}
%>
					</form>
				</td>
				</tr>
			</table>
			<hr>
<%
			i++;
		}
%>
	</div>
</body>
</html>