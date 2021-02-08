<%@page import="bean.CourseBean"%>
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
<%
	MenuBean menuBean = (MenuBean) session.getAttribute("newMenu");

	if(request.getParameter("addcourse") != null && request.getParameter("course") != null && request.getParameter("course").length() > 0) {
		CourseBean newCourseBean = new CourseBean();
		newCourseBean.setCourseName(request.getParameter("course"));
		
		boolean contains = false;
		
		for(CourseBean cb : menuBean.getCoursesList()) {
			if(cb.getCourseName().equalsIgnoreCase(newCourseBean.getCourseName())) {
				contains = true;
				break;
			}
		}
		
		if(!contains) {
			menuBean.getCoursesList().add(newCourseBean);
			
			session.setAttribute("newCourse", newCourseBean);
			session.setAttribute("newMenu", menuBean);
		}
	}
	else {
		session.setAttribute("errormsg", "Please, specify a name for the course");
%>
		<jsp:forward page="error_page.jsp"></jsp:forward>
<%
	}
%>
	<jsp:forward page="propose_menu.jsp"></jsp:forward>
</body>
</html>