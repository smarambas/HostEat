<%@page import="bean.SessionBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<jsp:useBean id="sessionBean" scope="session" class="bean.SessionBean"/>
<jsp:setProperty property="*" name="sessionBean"/>

<%
sessionBean = new SessionBean();
session.setAttribute("sessionBean", sessionBean);
%>
    
<!DOCTYPE html>
<html lang="it">
<head>
	<meta charset="UTF-8">
	<title>HostEat</title>
	
	<link rel="stylesheet" type="text/css" href="stylesheet.css" media="screen"/>
</head>
<body>
	<h2>HostEat</h2>
	<div class="container">
		<p>Welcome to HostEat!</p>
		<p>The platform designed to share good home cooking</p>
		<input type="button" id="btn" value="Sign Up" onclick="window.location.href='signup.html'">
		<p>
		<input type="button" id="btn" value="Log In" onclick="window.location.href='login.html'">
	</div>
</body>
</html>