<%@page import="control.DeleteNotificationController"%>
<%@page import="bean.NotificationBean"%>
<%@page import="bean.UserBean"%>
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
	UserBean userBean = sessionBean.getUserBean();
	
	int i = 0;
	boolean end = false;
	
	while(!end) {
		if(request.getParameter("del" + i) != null) {
			end = true;
			break;
		}
		
		i++;
	}
	
	NotificationBean nb = (NotificationBean) session.getAttribute("notification" + i);
	
	DeleteNotificationController deleteNotificationController = new DeleteNotificationController();
	deleteNotificationController.deleteNotification(userBean, nb);
	
	session.removeAttribute("notification" + i);
%>
	<jsp:forward page="notifications.jsp"></jsp:forward>
</body>
</html>