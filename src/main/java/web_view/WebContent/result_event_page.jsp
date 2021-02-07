<%@page import="bean.UserBean"%>
<%@page import="control.GetUserController"%>
<%@page import="bean.EventBean"%>
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
	<input type="button" id="btn" value="Back" onclick="window.location.href='search_results.jsp'">
	<div class="container">
		<strong id="pagetitle">Event page</strong>
		<br><br>
<%
		SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");
		
		int i = 0;

		if(session.getAttribute("index") != null) {
			i = (int) session.getAttribute("index");
		}
		else {
			boolean end = false;
			
			while(!end) {
				if(request.getParameter("open" + i) != null) {
					end = true;
					session.setAttribute("index", i);
					break;
				}
				
				i++;
			}
		}
		
		EventBean eventBean = (EventBean) session.getAttribute("result" + i);
		session.setAttribute("selectedEvent", eventBean);
%>
		<strong id="label">Owner:</strong>
<%
		out.println(eventBean.getEventOwner());
%>
		<br><br>
		<strong id="label">Date:</strong>
<%
		out.println(eventBean.getDateTime().substring(0, 10));
%>
		<br><br>
		<strong id="label">Time:</strong>
<%
		out.println(eventBean.getDateTime().substring(11));
%>
		<br><br>
		<strong id="label">Guests:</strong>
<%
		out.println(eventBean.getActualGuests());
%>
		<br><br>
		<strong id="label">Bill:</strong>
<%
		out.println(eventBean.getBill());
%>
		<br><br>
		<strong id="label">Region:</strong>
<%
		out.println(eventBean.getRegionString());
%>
		<br><br>
		<strong id="label">Province:</strong>
<%
		out.println(eventBean.getProvinceString());
%>
		<br><br>
		<strong id="label">City:</strong>
<%
		out.println(eventBean.getCityString());
%>
		<br><br><br>
	</div>
	<div class="footer">
		<table class="footer">
			<tr><td>
					<form action="join_event.jsp">
						<input type="submit" id="btn" value="Join event" name="join">
					</form>
				</td>
				<td>
					<form action="result_host_profile.jsp">
						<input type="submit" id="btn" value="Open host profile" name="open">
					</form>
				</td>
				<td>
					<form action="result_menu_page.jsp">
						<input type="submit" id="btn" value="Open menu" name="menu">
					</form>
			</td></tr>
		</table>
	</div>		
</body>
</html>