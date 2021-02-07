<%@page import="bean.SessionBean"%>
<%@page import="bean.EventBean"%>
<%@page import="bean.UserBean"%>
<%@page import="control.GetUserController"%>
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
		<input type="button" id="btn" value="Favorites" onclick="window.location.href='favorites.jsp'">
	</div>
	<input type="button" id="btn" value="Back" onclick="window.location.href='result_event_page.jsp'">
	<p>
	<div class="container">
		<strong id="pagetitle">Host profile</strong>
		<br><br>
<%
	SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");

	EventBean eventBean = (EventBean) session.getAttribute("selectedEvent");

	GetUserController getUserController = new GetUserController();
	UserBean tempUserBean = new UserBean();
	tempUserBean.setUsername(eventBean.getEventOwner());
	UserBean hostBean = getUserController.getUser(tempUserBean);
	
	session.setAttribute("selectedHost", hostBean);
%>	
		<strong id="label">Username:</strong>
<%
		out.println(hostBean.getUsername());
%>
		<br><br>
		<strong id="label">Name:</strong>
<%
		out.println(hostBean.getName());
%>
		<br><br>
		<strong id="label">Surname:</strong>
<%
		out.println(hostBean.getSurname());
%>
		<br><br>
		<strong id="label">Sex:</strong>
<%
		out.println(hostBean.getSex());
%>
		<br><br>
		<strong id="label">Birthday:</strong>
<%
		out.println(hostBean.getBirthday().substring(0, 10));
%>
		<br><br>
		<strong id="label">Email:</strong>
<%
		out.println(hostBean.getEmailAddr());
%>
		<br><br>
		<strong id="label">Region:</strong>
<%
		out.println(hostBean.getReg());
%>
		<br><br>
		<strong id="label">Province:</strong>
<%
		out.println(hostBean.getProv());
%>
		<br><br>
		<strong id="label">City:</strong>
<%
		out.println(hostBean.getCity());
%>
		<br><br>
		<strong id="label">Rating:</strong>
<%
		if(hostBean.getRatingsNum() > 0) {
			out.println(hostBean.getRatings() / hostBean.getRatingsNum());
		}
		else {
			out.println(0.0);
		}
%>
		<br><br><br>
		<div class="footer">
<%
			boolean found = false;
			for(int i = 0; i < sessionBean.getSavedHosts().size(); i++) {
				if(sessionBean.getSavedHosts().get(i).getUsername().equals(hostBean.getUsername())) {
					found = true;
					break;
				}
			}
			
			if(found) {
%>
				<form action="remove_host.jsp">
					<input type="submit" id="btn" value="Remove host">
				</form>
<%
			}
			else {
%>
				<form action="save_host.jsp">
					<input type="submit" id="btn" value="Save host">
				</form>
<%
			}
%>
		</div>
	</div>

</body>
</html>