<%@page import="bean.SessionBean"%>
<%@page import="exceptions.NoRecordFoundException"%>
<%@page import="bean.CourseBean"%>
<%@page import="bean.MenuBean"%>
<%@page import="control.GetMenuController"%>
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
<%
		SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");
		if(sessionBean.getUserBean().getUserType().equalsIgnoreCase("GUEST")) {
%>
			<input type="button" id="btn" value="Favorites" onclick="window.location.href='favorites.jsp'">
<%
		}
%>
	</div>
	<input type="button" id="btn" value="Back" onclick="history.back()">
	<div class="container">
		<strong id="pagetitle">Menu</strong>
		<br><br>
<%
		EventBean eventBean = (EventBean) session.getAttribute("selectedEvent");

		GetMenuController getMenuController = new GetMenuController();
		try {
			MenuBean menuBean = getMenuController.getMenu(eventBean);
			
			for(CourseBean cb : menuBean.getCoursesList()) {
				out.println("<strong id='label'>" + cb.getCourseName() + "</strong>");
				out.println("<br><br>");
				
				for(String dish : cb.getDishes()) {
					out.println(dish);
					out.println("<br>");
				}
				
				out.println("<br><br>");
			}
		} catch(NoRecordFoundException nrfe) {
			session.setAttribute("errormsg", "No menu set for the event");
%>
			<jsp:forward page="error_page.jsp"></jsp:forward>
<%
		} catch(Exception e) {
%>
			<jsp:forward page="error_page.jsp"></jsp:forward>
<%
		}
%>		
	</div>
</body>
</html>