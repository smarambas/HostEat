<%@page import="utils.FormatLocationString"%>
<%@page import="control.GetHostLocationController"%>
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
	<input type="button" id="btn" value="Back" onclick="window.location.href='guest_event_page.jsp'">
	<div class="container">
		<strong id="pagetitle">Menu</strong>
		<br><br>
	</div>
	<div class="map">
<%
		SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");
		EventBean eventBean = (EventBean) session.getAttribute("selectedEvent");
		
		String location = eventBean.getCityString() + ", " + eventBean.getAddressString();
		
		String formattedLocation = FormatLocationString.createLocString(location);
				
		out.println("<iframe width=\"600\" height=\"600\" frameborder=\"0\" style=\"border:0\" src=\"https://www.google.com/maps/embed/v1/place?key=" +
					GetHostLocationController.getApiKey() + 
					"&q=" + formattedLocation + "\"/></iframe>");
%>
	</div>
</body>
</html>