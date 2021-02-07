<%@page import="control.JoinEventController"%>
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
	
	for(EventBean eb : sessionBean.getEventBeanList()) {
		if(eb.getEventOwner().equals(eventBean.getEventOwner()) && eb.getDateTime().equals(eventBean.getDateTime())) {
			session.setAttribute("errormsg", "You already joined that event");
%>
			<jsp:forward page="error_page.jsp"></jsp:forward>
<%
		}
	}
	
	if(eventBean.getGuestsNumber() < eventBean.getMaxGuestsNumber()) {
		JoinEventController joinEventController = new JoinEventController();
		SessionBean newSessionBean = joinEventController.joinEvent(sessionBean, eventBean);
		
		session.setAttribute("sessionBean", newSessionBean);
		session.removeAttribute("selectedEvent");
%>
		<jsp:forward page="homepage.jsp"></jsp:forward>
<%
	}
	else {
		session.setAttribute("errormsg", "You can't join a full event, sorry");
%>
		<jsp:forward page="error_page.jsp"></jsp:forward>
<%
	}


%>
</body>
</html>