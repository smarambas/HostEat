<%@page import="control.DenyGuestController"%>
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
<%
	SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");
	EventBean eventBean = (EventBean) session.getAttribute("selectedEvent");

	int i = 0;
	boolean end = false;
	
	while(!end) {
		if(request.getParameter("deny" + i) != null) {
			end = true;
			break;
		}
		
		i++;
	}
	
	UserBean guestBean = (UserBean) session.getAttribute("selectedGuest" + i);
	
	try {
		DenyGuestController denyGuestController = new DenyGuestController();
		SessionBean newSessionBean = denyGuestController.denyGuest(sessionBean, guestBean, eventBean);
		
		session.setAttribute("sessionBean", newSessionBean);
	} catch(Exception e) {
%>
		<jsp:forward page="error_page.jsp"></jsp:forward>
<%		
	}
%>
	<jsp:forward page="view_guests.jsp"></jsp:forward>	
</body>
</html>