<%@page import="control.DeleteJoinedEventController"%>
<%@page import="bean.SessionBean"%>
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
<%
	SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");
	EventBean eventBean = (EventBean) session.getAttribute("selectedEvent");
	
	DeleteJoinedEventController deleteJoinedEventController = new DeleteJoinedEventController();
	SessionBean newSessionBean = deleteJoinedEventController.deleteEvent(sessionBean, eventBean);
	
	session.setAttribute("sessionBean", newSessionBean);
%>
	<jsp:forward page="homepage.jsp"></jsp:forward>
</body>
</html>