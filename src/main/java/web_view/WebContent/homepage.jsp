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
	</div>
<%
}

%>
	
</body>
</html>