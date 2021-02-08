<%@page import="bean.CourseBean"%>
<%@page import="bean.SessionBean"%>
<%@page import="bean.EventBean"%>
<%@page import="bean.MenuBean"%>
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
	<br>
<%
	MenuBean newMenuBean;
	
	if(session.getAttribute("newMenu") != null) {
		newMenuBean = (MenuBean) session.getAttribute("newMenu");
	}
	else {
		newMenuBean = new MenuBean();
		session.setAttribute("newMenu", newMenuBean);
	}
%>
	<div class="container">
		<strong id="pagetitle">Menu</strong>
		<br><br>
<%
		for(CourseBean cb : newMenuBean.getCoursesList()) {
			out.println("<strong id='label'>" + cb.getCourseName() + "</strong>");
			out.println("<br><br>");
			
			for(String dish : cb.getDishes()) {
				out.println(dish);
				out.println("<br>");
			}
			
			out.println("<br><br>");
		}
%>
	</div>
	<div class="footer">
		<form action="new_course.jsp">
			<input type="submit" id="btn" value="Add course" name="addcourse">
			<input type="text" id="course" name="course">
		</form>		
		<br>
		<form action="new_dish.jsp">
			<input type="submit" id="btn" value="Add dish" name="adddish">
			<input type="text" id="dish" name="dish">
		</form>		
		<br><br>
		<form action="confirm_menu.jsp">
			<input type="submit" id="btn" value="Confirm menu" name="confirm">
		</form>		
	</div>
</body>
</html>