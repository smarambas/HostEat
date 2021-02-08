<%@page import="control.CreateEventController"%>
<%@page import="exceptions.WrongDateException"%>
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
	EventBean eventBean = new EventBean();

	if(request.getParameter("date") != null) {
		eventBean.setDateTime(request.getParameter("date") + " " + request.getParameter("hours") + ":" + request.getParameter("minutes"));
	}
	else {
		session.setAttribute("errormsg", "Please, specify a date in the future");
%>
		<jsp:forward page="error_page.jsp"></jsp:forward>
<%
	}
	
	if(request.getParameter("guests") != null) {
		eventBean.setMaxGuestsNumber(Integer.parseInt(request.getParameter("guests")));
	}
	else {
		session.setAttribute("errormsg", "Please, specify the maximum number of guests");
%>
		<jsp:forward page="error_page.jsp"></jsp:forward>
<%
	}
	
	if(request.getParameter("bill") != null) {
		eventBean.setBill(Double.parseDouble(request.getParameter("bill")));
	}
	else {
		eventBean.setBill(0);
	}
	
	CreateEventController createEventController = new CreateEventController();
	
	try {
		SessionBean newSessionBean = createEventController.createEvent(sessionBean, eventBean);
		
		session.setAttribute("sessionBean", newSessionBean);
		
%>
		<jsp:forward page="propose_menu.jsp"></jsp:forward>
<%
	} catch (WrongDateException wde) {
		session.setAttribute("errormsg", "Please, specify a date in the future");
%>
		<jsp:forward page="error_page.jsp"></jsp:forward>
<%
	} catch (NumberFormatException nfe) {
		session.setAttribute("errormsg", "Please, specify a valid date");
%>
		<jsp:forward page="error_page.jsp"></jsp:forward>
<%
	} catch (Exception e) {
%>
		<jsp:forward page="error_page.jsp"></jsp:forward>
<%
	}
%>
</body>
</html>