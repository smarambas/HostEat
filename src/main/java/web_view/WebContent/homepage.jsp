<%@page import="bean.EventBean"%>
<%@page import="java.util.List"%>
<%@page import="bean.SessionBean"%>
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
<%
SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");

if(sessionBean.getUserBean().getUserType().equalsIgnoreCase("HOST")) {
%>
	<div class="header">
		<input type="button" id="btn" value="Homepage" onclick="window.location.href='homepage.jsp'">
		<input type="button" id="btn" value="Userpage" onclick="">
		<input type="button" id="btn" value="Notifications" onclick="">
		<p>
		<input type="button" id="btn" value="Refresh" onclick="">
	</div>
	<p>
	<div class="container">
		<strong id="pagetitle">Homepage</strong>
		<p>
		<input type="button" id="btn" value="New event" onclick="">
		<p>
		<strong id="pagetitle">Events created</strong>
		<p>
<%
		List<EventBean> eventBeanList = sessionBean.getEventBeanList();
		
		if(eventBeanList != null && !(eventBeanList.isEmpty())) {
			for(EventBean eb : eventBeanList) {
				out.println(eb.getDateTime());
				out.println("<p>Guests: " + eb.getActualGuests());
				out.println("<p>");
				
				session.setAttribute("selectedEvent", eb);
%>
				<input type="button" id="btn" value="Open event" onclick="window.location.href='host_event_page.jsp'">
				<hr>
<%
			}
		}
%>
	</div>
<%
}
else {
%>
	<div class="header">
		<input type="button" id="btn" value="Homepage" onclick="window.location.href='homepage.jsp'">
		<input type="button" id="btn" value="Userpage" onclick="">
		<input type="button" id="btn" value="Notifications" onclick="">
		<input type="button" id="btn" value="Favorites" onclick="">
		<p>
		<input type="button" id="btn" value="Refresh" onclick="">
	</div>
	<p>
	<div class="container">
		<strong id="pagetitle">Homepage</strong>
		<p>
		<input type="button" id="btn" value="Search event" onclick="">
		<p>
		<strong id="pagetitle">Events joined</strong>
		<p>
	</div>
<%
}
%>
	
</body>
</html>