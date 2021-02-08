<%@page import="control.ProposeMenuController"%>
<%@page import="bean.EventBean"%>
<%@page import="bean.SessionBean"%>
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
	SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");
	int eventsNum = sessionBean.getEventBeanList().size();
	EventBean eventBean = sessionBean.getEventBeanList().get(eventsNum - 1);
	MenuBean menuBean = (MenuBean) session.getAttribute("newMenu");

	ProposeMenuController proposeMenuController = new ProposeMenuController();
	proposeMenuController.proposeMenu(eventBean, menuBean);
	
	session.removeAttribute("newMenu");
	session.removeAttribute("newCourse");
%>
	<jsp:forward page="homepage.jsp"></jsp:forward>
</body>
</html>