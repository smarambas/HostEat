<%@page import="okhttp3.internal.ws.RealWebSocket.Message"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page isErrorPage = "true" %>   
    
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
	<input type="button" id="btn" value="Back" onclick="history.back()">
	<div class="container">
<%
	if(session.getAttribute("errormsg") != null) {
		out.println("<p id='error'>" + session.getAttribute("errormsg"));
		session.removeAttribute("errormsg");
	}
	else {
%>
		<p id="error">Ops, something went wrong, please try again</p>
		<p>Exception is: <%= exception %>  
<%
	}
%>
	</div>
</body>
</html>