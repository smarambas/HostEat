<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="bean.UserBean"%>
<%@page import="bean.EventBean"%>
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
	</div>
	<input type="button" id="btn" value="Back" onclick="window.location.href='view_guests.jsp'">
	<p>
<%
	EventBean eventBean = (EventBean) session.getAttribute("selectedEvent");

	int i = 0;
	boolean end = false;
	
	while(!end) {
		if(request.getParameter("profile" + i) != null) {
			end = true;
			break;
		}
		
		i++;
	}
	
	UserBean guestBean = (UserBean) session.getAttribute("selectedGuest" + i);
%>
	<div class="container">
		<strong id="pagetitle">Guest profile</strong>
		<br><br>
		<strong id="label">Username:</strong>
<%
		out.println(guestBean.getUsername());
%>
		<br><br>
		<strong id="label">Name:</strong>
<%
		out.println(guestBean.getName());
%>
		<br><br>
		<strong id="label">Surname:</strong>
<%
		out.println(guestBean.getSurname());
%>
		<br><br>
		<strong id="label">Sex:</strong>
<%
		out.println(guestBean.getSex());
%>
		<br><br>
		<strong id="label">Birthday:</strong>
<%
		out.println(guestBean.getBirthday().substring(0, 10));
%>
		<br><br>
		<strong id="label">Email:</strong>
<%
		out.println(guestBean.getEmailAddr());
%>
		<br><br>
		<strong id="label">Region:</strong>
<%
		out.println(guestBean.getReg());
%>
		<br><br>
		<strong id="label">Province:</strong>
<%
		out.println(guestBean.getProv());
%>
		<br><br>
		<strong id="label">City:</strong>
<%
		out.println(guestBean.getCity());
%>
		<br><br>
		<strong id="label">Rating:</strong>
<%
		if(guestBean.getRatingsNum() > 0) {
			out.println((double) guestBean.getRatings() / guestBean.getRatingsNum());
		}
		else {
			out.println(0.0);
		}
%>
		<br><br>
	</div>
	<div class="footer">
<%
		if(guestBean.getGuestStatus().equalsIgnoreCase("ACCEPTED")) {
			String format = "yyyy-MM-dd HH:mm";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			
			GregorianCalendar now = new GregorianCalendar();
			GregorianCalendar dateCal = new GregorianCalendar();
			dateCal.setTime(sdf.parse(eventBean.getDateTime()));
			
			long dateInMillis = dateCal.getTimeInMillis();
			long nowInMillis = now.getTimeInMillis();
			
			if(dateInMillis - nowInMillis < 0) {
				session.setAttribute("userToRate", guestBean.getUsername());
%>
				<form action="rate_user.jsp">
					<input type="submit" id="btn" value="Rate guest" name="rate">
				</form>	
<%
			}
		}
%>
	</div>
</body>
</html>