<%@page import="bean.UserBean"%>
<%@page import="bean.EventBean"%>
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
	<div class="header">
		<input type="button" id="btn" value="Homepage" onclick="window.location.href='homepage.jsp'">
		<input type="button" id="btn" value="Userpage" onclick="window.location.href='userpage.jsp'">
		<input type="button" id="btn" value="Notifications" onclick="window.location.href='notifications.jsp'">
<%
	SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");

	if(sessionBean.getUserBean().getUserType().equalsIgnoreCase("GUEST")) {
%>
		<input type="button" id="btn" value="Favorites" onclick="window.location.href='favorites.jsp'">
<%
	}
%>
	</div>
	<input type="button" id="btn" value="Back" onclick="history.back()">
	<p>
	<div class="container">
		<strong id="pagetitle">Rate user</strong>
		<br><br>
	</div>
	<div class="footer">
		<strong id="label">Vote:</strong>
		<form action="submit_rating.jsp">	
			<div class="col-lg-4 form-group">
				<input type="radio" id="1" value="1" name="vote">
				<label for="1">1&nbsp</label>
				<input type="radio" id="2" value="2" name="vote">
				<label for="2">2&nbsp</label>
				<input type="radio" id="3" value="3" name="vote">
				<label for="3">3&nbsp</label>
				<input type="radio" id="4" value="4" name="vote">
				<label for="4">4&nbsp</label>
				<input type="radio" id="5" value="5" name="vote">
				<label for="5">5&nbsp</label>
			</div>
			<br><br>
			<input type="submit" value="Submit rating" name="submit">
		</form>
	</div>
</body>
</html>