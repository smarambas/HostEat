<%@page import="bean.SessionBean"%>
<%@page import="control.SignUpController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<jsp:useBean id="userBean" scope="request" class="bean.UserBean"/>
<jsp:setProperty property="*" name="userBean"/>

<%@ page errorPage = "error_page.jsp" %>

<!DOCTYPE html>
<html lang="">
<head>
	<meta charset="UTF-8">
	<title>HostEat</title>
	
	<link rel="stylesheet" type="text/css" href="stylesheet.css" media="screen"/> 
</head>
<body>
<%
boolean checkinput = false;
boolean checktype = false;

if(request.getParameter("signup") != null) {	
	if(userBean.setName(request.getParameter("name")) &&
	   userBean.setSurname(request.getParameter("surname")) &&
	   userBean.setEmailAddr(request.getParameter("email")) &&
	   userBean.setBirthday(request.getParameter("birthday") + " 00:00") &&
	   userBean.setReg(request.getParameter("region")) &&
	   userBean.setProv(request.getParameter("province")) &&
	   userBean.setCity(request.getParameter("city"))) {
				   
		checkinput = true;
		userBean.setUsername(request.getParameter("username"));
		userBean.setPassw(request.getParameter("password"));
		userBean.setSex(request.getParameter("sex"));
		userBean.setAddr(request.getParameter("address"));
	}
		
	if(request.getParameter("type") != null && request.getParameter("type").equalsIgnoreCase("host")) {
		userBean.setUserType("HOST");
		checktype = true;
	}
	else if(request.getParameter("type") != null && request.getParameter("type").equalsIgnoreCase("guest")) {
		userBean.setUserType("GUEST");
		checktype = true;
	}
	else {
		checktype = false;
	}
	
	if(checkinput) {
		if(checktype) {
			SignUpController signUpController = new SignUpController();
			SessionBean sessionBean = signUpController.signUp(userBean);
			
			if(sessionBean != null) {
				session.setAttribute("sessionBean", sessionBean);
%>
				<jsp:forward page="homepage.jsp"></jsp:forward>
<%
			}
			else {
%>
				<jsp:forward page="error_page.jsp"></jsp:forward>
<%
			}
		}
		else {
			session.setAttribute("errormsg", "Please, select the type of user (Host/Guest)");
%>
			<jsp:forward page="error_page.jsp"></jsp:forward>
<%
		}
	}
	else {
%>
		<h2>HostEat</h2>
		<p>
		<input type="button" value="Back" onclick="window.location.href='signup.html'">
		<div class="container">
			<p id="error">Please, make sure that:</p>
			<ul>
				<li id="error">your name contains only characters
				<li id="error">your surname contains only characters
				<li id="error">your email address has the correct format (abc@dfg.something)
				<li id="error">your region contains only characters
				<li id="error">your province contains only characters
				<li id="error">your city contains only characters
			</ul>
		</div>
<%
	}
}

%>
</body>
</html>