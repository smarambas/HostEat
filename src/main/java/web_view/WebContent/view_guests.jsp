<%@page import="control.GetJoinedGuestsController"%>
<%@page import="bean.UserBean"%>
<%@page import="java.util.List"%>
<%@page import="bean.EventBean"%>
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
	</div>
	<input type="button" id="btn" value="Back" onclick="window.location.href='host_event_page.jsp'">
	<p>
<%
	EventBean eventBean = (EventBean) session.getAttribute("selectedEvent");
	List<UserBean> guestsList;
%>
	
	<div class="container">
		<strong id="pagetitle">Joined guests list</strong>
		<br><br>
<%
		GetJoinedGuestsController getJoinedGuestsController = new GetJoinedGuestsController();
		guestsList = getJoinedGuestsController.getJoinedGuests(eventBean);
		
		int i = 0;
		for(UserBean ub : guestsList) {
			out.println(ub.getName() + " " + ub.getSurname());
			out.println("<br>");
			out.println(ub.getGuestStatus());
			out.println("<br>");
			
			if(!(ub.getPayStatus().equalsIgnoreCase("NOSET"))) {
				out.println(ub.getPayStatus());
				out.println("<br>");
			}
			
			session.setAttribute("selectedGuest" + i, ub);
%>
			<br>
			<div class="footer">
				<table class="footer">
					<caption></caption>
					<tr>
					<th id="dummy"></th>
					</tr>
					<tr>
					<td>
						<form action="accept_guest.jsp">
							<input type="submit" id="btn" value="Accept guest" name="accept<%=i%>">
						</form>				
					</td>
					<td>
						<form action="deny_guest.jsp">
							<input type="submit" id="btn" value="Deny guest" name="deny<%=i%>">
						</form>				
					</td>
					<td>
						<form action="guest_profile.jsp">
							<input type="submit" id="btn" value="View profile" name="profile<%=i%>">
						</form>				
					</td>
					</tr>
				</table>
			</div>
			<hr>
<%
			i++;
		}
%>
	</div>
</body>
</html>