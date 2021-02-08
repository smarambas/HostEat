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

	CourseBean courseBean = (CourseBean) session.getAttribute("newCourse");
	
	if(courseBean != null) {
		if(request.getParameter("adddish") != null && request.getParameter("dish") != null && request.getParameter("dish").length() > 0) {
			String newDish = request.getParameter("dish");
			
			for(CourseBean cb : menuBean.getCoursesList()) {
				if(cb.getCourseName().equalsIgnoreCase(courseBean.getCourseName())) {
					cb.getDishes().add(newDish);
					
					session.setAttribute("newMenu", menuBean);
					
					break;
				}
			}
		}
		else {
			session.setAttribute("errormsg", "Please, specify a name for the dish");
%>
			<jsp:forward page="error_page.jsp"></jsp:forward>
<%
		}
%>
		<jsp:forward page="propose_menu.jsp"></jsp:forward>
<%
	}
	else {
		session.setAttribute("errormsg", "Please, specify first a course");
%>
		<jsp:forward page="error_page.jsp"></jsp:forward>
<%
	}	
%>
</body>
</html>