<%@page import="control.ModifyAccountController"%>
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
	<h2>HostEat</h2>
	<p>
	<input type="button" id="btn" value="Back" onclick="window.location.href='userpage.jsp'">
<%
	SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");
	UserBean userBean = sessionBean.getUserBean();
	
	if(userBean.getUserType().equalsIgnoreCase("HOST")) {
%>
	<div class="header">
		<input type="button" id="btn" value="Homepage" onclick="window.location.href='homepage.jsp'">
		<input type="button" id="btn" value="Userpage" onclick="window.location.href='userpage.jsp'">
		<input type="button" id="btn" value="Notifications" onclick="window.location.href='notifications.jsp'">
		<p>
	</div>
	<p>
<%
	}
	else {
%>
	<div class="header">
		<input type="button" id="btn" value="Homepage" onclick="window.location.href='homepage.jsp'">
		<input type="button" id="btn" value="Userpage" onclick="window.location.href='userpage.jsp'">
		<input type="button" id="btn" value="Notifications" onclick="window.location.href='notifications.jsp'">
		<input type="button" id="btn" value="Favorites" onclick="">
		<p>
	</div>
	<p>
<%
	}
%>

	<div class="container">
		<strong id="pagetitle">Modify account</strong>
		<br><br>
		<form action="modify_account.jsp">
			<strong id="label">New email:</strong>
			<input type="text" id="emailField" name="emailField">
			<br><br>
			<strong id="label">New password:</strong>
			<input type="text" id="passwField" name="passwField">
			<br><br>
			<strong id="label">New region:</strong>
			<input type="text" id="regionField" name="regionField">
			<br><br>
			<strong id="label">New province:</strong>
			<input type="text" id="provField" name="provField">
			<br><br>
			<strong id="label">New city:</strong>
			<input type="text" id="cityField" name="cityField">
			<br><br>
			<strong id="label">New address:</strong>
			<input type="text" id="addrField" name="addrField">
			<br><br><br>
			<input type="submit" id="submit" name="submit" value="Submit">
<%
			if(request.getParameter("submit") != null) {
				UserBean tempBean = new UserBean();
				
				tempBean.setEmailAddr(request.getParameter("emailField"));
				tempBean.setPassw(request.getParameter("passwField"));
				tempBean.setReg(request.getParameter("regionField"));
				tempBean.setProv(request.getParameter("provField"));
				tempBean.setCity(request.getParameter("cityField"));
				tempBean.setAddr(request.getParameter("addrField"));
				
				ModifyAccountController modifyAccountController = new ModifyAccountController();
				try {
					SessionBean newSessionBean = modifyAccountController.modifyAccount(sessionBean, tempBean);
					session.setAttribute("sessionBean", newSessionBean);
				} catch(Exception e) {
%>
					<jsp:forward page="generic_error.jsp"></jsp:forward>
<%
				}
%>
				<jsp:forward page="userpage.jsp"></jsp:forward>
<%
			}
%>
		</form>
	</div>
</body>
</html>