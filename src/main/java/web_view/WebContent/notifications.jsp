<%@page import="control.DeleteAllNotificationsController"%>
<%@page import="control.DeleteNotificationController"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="bean.NotificationBean"%>
<%@page import="control.GetNotificationsController"%>
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
			<input type="button" id="btn" value="Refresh" onclick="window.location.href='notifications.jsp'">
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
			<input type="button" id="btn" value="Refresh" onclick="window.location.href='notifications.jsp'">
		</div>
		<p>
<%
	}
%>
	<div class="container">
		<strong id="pagetitle">Notifications</strong>
		<br><br>
<%
		List<NotificationBean> notificationBeans = new ArrayList<>();
		
		GetNotificationsController getNotificationsController = new GetNotificationsController();
		try {
			notificationBeans = getNotificationsController.getNotifications(userBean);
			
			if(notificationBeans != null && !notificationBeans.isEmpty()) {
				int i = 0;
				for(NotificationBean nb : notificationBeans) {
					out.println(nb.getDate());
					out.println("<br>");
					out.println(nb.getText());
					session.setAttribute("notification" + i, nb);
%>
					<form action="delete_notification.jsp">
						<br>
						<input type="submit" id="btn" value="Delete" name="del<%=i%>">
					</form>
					<hr>
<%
					i++;
				}
			}
		} catch(Exception e) {
%>
			<jsp:forward page="generic_error.jsp"></jsp:forward>
<%
		}
%>
		<br><br><br>
		<div class="footer">
			<form action="notifications.jsp">
				<input type="submit" id="btn" value="Delete all" name="deleteall">
<%
				if(request.getParameter("deleteall") != null) {
					DeleteAllNotificationsController deleteAllNotificationsController = new DeleteAllNotificationsController();
					deleteAllNotificationsController.deleteAllNotifications(userBean);
%>
					<jsp:forward page="homepage.jsp"></jsp:forward> 
<%
				}
%>
			</form>
		</div>
	</div>
</body>
</html>