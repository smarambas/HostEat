<%@page import="control.LogInController"%>
<%@page import="bean.SessionBean"%>
<%@ page import="bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:useBean id="loginBean" scope="request" class="bean.UserBean"/>
<jsp:setProperty property="*" name="loginBean"/>

<%@ page errorPage = "error_page.jsp" %>

<html lang="it">
<head>
	<meta charset="UTF-8">
	<title>HostEat</title>
	
	<link rel="stylesheet" type="text/css" href="stylesheet.css" media="screen"/>
</head>
<body>

<%
if(request.getParameter("login") != null) {	//if the button submit is pressed
	loginBean.setUsername(request.getParameter("username"));
	loginBean.setPassw(request.getParameter("password"));
	
	LogInController logInController = new LogInController();
	SessionBean sessionBean = logInController.logIn(loginBean);
		
	if(sessionBean != null) {
		session.setAttribute("sessionBean", sessionBean);
%>
		<jsp:forward page="homepage.jsp"></jsp:forward>
<%
	}
	else {
%>
		<h2>HostEat</h2>
		<p>
		<input type="button" value="Back" onclick="window.location.href='login.html'">
		<div class="container">
			<p id="error">Wrong username or password, please try again</p>
		</div>
<%
	}
}

%>
	
</body>
</html>