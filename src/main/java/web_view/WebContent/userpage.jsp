<%@page import="bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="bean.SessionBean"%>
    
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
		<strong id="pagetitle">Account</strong>
		<br><br>
		<strong id="label">Name:</strong>
<%
		out.println(userBean.getName());
%>
		<br><br>
		<strong id="label">Surname:</strong>
<%
		out.println(userBean.getSurname());
%>
		<br><br>
		<strong id="label">Birthday:</strong>
<%
		out.println(userBean.getBirthday());
%>
		<br><br>
		<strong id="label">Sex:</strong>
<%
		out.println(userBean.getSex());
%>
		<br><br>
		<strong id="label">Email:</strong>
<%
		out.println(userBean.getEmailAddr());
%>
		<br><br>
		<strong id="label">Region:</strong>
<%
		out.println(userBean.getReg());
%>
		<br><br>
		<strong id="label">Province:</strong>
<%
		out.println(userBean.getProv());
%>
		<br><br>
		<strong id="label">City:</strong>
<%
		out.println(userBean.getCity());
%>
		<br><br>
		<strong id="label">Address:</strong>
<%
		out.println(userBean.getAddr());
%>
		<br><br>
		<strong id="label">Rating:</strong>
<%
		if(userBean.getRatingsNum() > 0) {
			out.println(userBean.getRatings() / userBean.getRatingsNum());
		}
		else {
			out.println(0.0);
		}
%>
		<br><br><br>
		<div class="footer">
			<input type="button" id="btn" value="Modify account" onclick="window.location.href='modify_account.jsp'">
			<input type="button" id="btn" value="Log out" onclick="window.location.href='logout.jsp'">
		</div>
	</div>

</body>
</html>