<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="bean.SessionBean"%>
<%@page import="bean.UserBean"%>
    
<%@ page errorPage = "generic_error.jsp" %>

<!DOCTYPE html>
<html lang="">
<head>
	<meta charset="UTF-8">
	<title>HostEat</title>
	
	<link rel="stylesheet" type="text/css" href="stylesheet.css" media="screen"/>
</head>
<body>
<%
	session.invalidate();
%>
	<jsp:forward page="index.jsp"></jsp:forward>
</body>
</html>