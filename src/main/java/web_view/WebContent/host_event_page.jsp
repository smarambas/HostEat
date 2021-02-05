<%@page import="bean.EventBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page errorPage = "generic_error.jsp" %>
    
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
	<input type="button" value="Back" onclick="window.location.href='homepage.jsp'">
	<div class="header">
		<input type="button" id="btn" value="Homepage" onclick="window.location.href='homepage.jsp'">
		<input type="button" id="btn" value="Userpage" onclick="window.location.href='userpage.jsp'">
		<input type="button" id="btn" value="Notifications" onclick="window.location.href='notifications.jsp'">
		<p>
		<input type="button" id="btn" value="Refresh" onclick="">
	</div>
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
		<strong id="label">Address:</strong>
<%
		out.println(eventBean.getAddressString());
%>
		<br><br>
		<strong id="label">Bill:</strong>
<%
		out.println(eventBean.getBill());
%>
		<br><br><br>
		<div class="footer">
			<input type="button" id="btn" value="View guests" onclick="">
			<input type="button" id="btn" value="Open menu" onclick="">
			<input type="button" id="btn" value="Remove event" onclick="">
		</div>
	</div>
	
</body>
</html>