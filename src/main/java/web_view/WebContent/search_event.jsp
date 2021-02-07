<%@page import="java.util.ArrayList"%>
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
<%
	SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");

	sessionBean.setSearchedList(new ArrayList<>());
%>
	<h2>HostEat</h2>
	<p>
	<div class="header">
		<input type="button" id="btn" value="Homepage" onclick="window.location.href='homepage.jsp'">
		<input type="button" id="btn" value="Userpage" onclick="window.location.href='userpage.jsp'">
		<input type="button" id="btn" value="Notifications" onclick="window.location.href='notifications.jsp'">
		<input type="button" id="btn" value="Favorites" onclick="window.location.href='favorites.jsp'">
	</div>
	<input type="button" id="btn" value="Back" onclick="window.location.href='homepage.jsp'">
	<div class="container">
		<strong id="pagetitle">Search event</strong>
		<br><br>
		<form action="search_results.jsp">
			<strong id="label">Region:</strong>
			<input type="text" id="region" name="region">
			<br><br>
			<strong id="label">Province:</strong>
			<input type="text" id="province" name="province">
			<br><br>
			<strong id="label">City:</strong>
			<input type="text" id="city" name="city">
			<br><br>
			<strong id="label">Date:</strong>
			<input type="date" id="date" name="date">
			<br><br>
			<strong id="label">Hours:</strong>
			<select id="hours" name="hours">
				<option value="00">00</option>
				<option value="01">01</option>
				<option value="02">02</option>
				<option value="03">03</option>
				<option value="04">04</option>
				<option value="05">05</option>
				<option value="06">06</option>
				<option value="07">07</option>
				<option value="08">08</option>
				<option value="09">09</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
				<option value="13">13</option>
				<option value="14">14</option>
				<option value="15">15</option>
				<option value="16">16</option>
				<option value="17">17</option>
				<option value="18">18</option>
				<option value="19">19</option>
				<option value="20">20</option>
				<option value="21">21</option>
				<option value="22">22</option>
				<option value="23">23</option>
			</select>
			<br><br>
			<strong id="label">Minutes:</strong>
			<select id="minutes" name="minutes">
				<option value="00">00</option>
				<option value="05">05</option>
				<option value="10">10</option>
				<option value="15">15</option>
				<option value="20">20</option>
				<option value="25">25</option>
				<option value="30">30</option>
				<option value="35">35</option>
				<option value="40">40</option>
				<option value="45">45</option>
				<option value="50">50</option>
				<option value="55">55</option>
			</select>
			<br><br><br>
			<div class="footer">
				<input type="submit" id="submit" name="submit" value="Search">
			</div>
		</form>
	</div>
</body>
</html>