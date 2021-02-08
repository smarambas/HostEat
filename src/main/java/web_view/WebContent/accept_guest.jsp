<%@page import="bean.EventBean"%>
<%@page import="control.AcceptGuestController"%>
<%@page import="exceptions.DuplicateRecordException"%>
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
	<h2>HostEat</h2>
	<p>
<%
	SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");
	EventBean eventBean = (EventBean) session.getAttribute("selectedEvent");

	int i = 0;
	boolean end = false;
	
	while(!end) {
		if(request.getParameter("accept" + i) != null) {
			end = true;
			break;
		}
		
		i++;
	}
	
	UserBean guestBean = (UserBean) session.getAttribute("selectedGuest" + i);
	
	try {
		AcceptGuestController acceptGuestController = new AcceptGuestController();
		acceptGuestController.acceptGuest(guestBean, eventBean);
	} catch(DuplicateRecordException dre) {
		session.setAttribute("errormsg", "You already accepted that user");
%>
		<jsp:forward page="error_page.jsp"></jsp:forward>
<%
	} catch(Exception e) {
%>
		<jsp:forward page="error_page.jsp"></jsp:forward>
<%		
	}
%>
	<jsp:forward page="view_guests.jsp"></jsp:forward>
</body>
</html>