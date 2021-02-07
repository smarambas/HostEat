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
		<input type="button" id="btn" value="Favorites" onclick="window.location.href='favorites.jsp'">
	</div>
	<input type="button" id="btn" value="Back" onclick="window.location.href='homepage.jsp'">
	<p>
<%
	EventBean eventBean = (EventBean) session.getAttribute("selectedEvent");
%>
	<div class="container">
		<strong id="pagetitle">Event page</strong>
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
		<br><br>
<%
		boolean isAccepted = eventBean.getGuestStatus().equalsIgnoreCase("ACCEPTED");

		if(isAccepted) {
%>
			<strong id="label">Address:</strong>
<%
			out.println(eventBean.getAddressString());
%>
			<br><br>
<%
		}
%>
		<strong id="label">Status:</strong>
<%
		out.println(eventBean.getGuestStatus());
%>
		<br><br>
<%
		if(!(eventBean.getPayStatus().equalsIgnoreCase("NOSET"))) {
%>
			<strong id="label">Payment:</strong>
<%
			out.println(eventBean.getPayStatus());
%>
			<br><br>
			<strong id="label">Bill:</strong>
<%
			out.println(eventBean.getBill());
%>
			<br><br>
<%
		}

		boolean isPaymentRequired = !(eventBean.getPayStatus().equalsIgnoreCase("NOSET") || 
	  			  					  eventBean.getPayStatus().equalsIgnoreCase("PAID"));
%>
		<br><br><br>
	</div>
	<div class="footer">
<%
		if(isPaymentRequired && !isAccepted) {
%>
			<table class="footer">
				<caption></caption>
				<tr>
				<th id="dummy"></th>
				</tr>
				<tr>
				<td>
					<form action="pay_host.jsp">
						<input type="submit" id="btn" value="Pay host" name="pay">
					</form>				
				</td>
				<td>
					<form action="menu_page.jsp">
						<input type="submit" id="btn" value="Open menu" name="menu">
					</form>				
				</td>
				<td>
					<form action="guest_remove_event.jsp">
						<input type="submit" id="btn" value="Remove event" name="remove">
					</form>				
				</td>
				</tr>
			</table>
<%
		}
		else if(!isPaymentRequired && !isAccepted) {
%>
			<table class="footer">
				<caption></caption>
				<tr>
				<th id="dummy"></th>
				</tr>
				<tr>
				<td>
					<form action="menu_page.jsp">
						<input type="submit" id="btn" value="Open menu" name="menu">
					</form>				
				</td>
				<td>
					<form action="guest_remove_event.jsp">
						<input type="submit" id="btn" value="Remove event" name="remove">
					</form>				
				</td>
				</tr>
			</table>
<%
		}
		else {
%>
			<table class="footer">
				<caption></caption>
				<tr>
				<th id="dummy"></th>
				</tr>
				<tr>
				<td>
					<form action="view_host_location.jsp">
						<input type="submit" id="btn" value="View location" name="location">
					</form>				
				</td>
				<td>
					<form action="menu_page.jsp">
						<input type="submit" id="btn" value="Open menu" name="menu">
					</form>				
				</td>
				<td>
					<form action="guest_remove_event.jsp">
						<input type="submit" id="btn" value="Remove event" name="remove">
					</form>				
				</td>
				</tr>
			</table>
<%
		}
%>
	</div>
</body>
</html>