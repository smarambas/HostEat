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
		boolean end = false;
		
		while(!end) {
			if(request.getParameter("open" + i) != null) {
				end = true;
				break;
			}
			
			i++;
		}
		
		EventBean eventBean = (EventBean) session.getAttribute("result" + i);
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
		<br><br>
		
	</div>
</body>
</html>