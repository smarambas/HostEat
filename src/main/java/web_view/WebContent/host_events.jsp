<%@page import="java.util.ArrayList"%>
<%@page import="control.GetHostEventsController"%>
<%@page import="bean.UserBean"%>
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
<%
	SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");
	sessionBean.setSearchedList(new ArrayList<>());

	UserBean hostBean = (UserBean) session.getAttribute("selectedHost");
	
	GetHostEventsController getHostEventsController = new GetHostEventsController();
	
	SessionBean newSessionBean = getHostEventsController.getHostEvents(sessionBean, hostBean);
	
	session.setAttribute("sessionBean", newSessionBean);
%>
	<jsp:forward page="search_results.jsp"></jsp:forward>		
</body>
</html>