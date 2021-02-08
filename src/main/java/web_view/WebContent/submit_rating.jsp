<%@page import="control.RateUserController"%>
<%@page import="exceptions.AlreadyRatedException"%>
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
<%
	SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");
	EventBean eventBean = (EventBean) session.getAttribute("selectedEvent");
	String username = (String) session.getAttribute("userToRate");
	
	UserBean userBean = new UserBean();
	userBean.setUsername(username);
	
	int rating = 0;
	
	if(request.getParameter("vote") != null) {
		rating = Integer.parseInt(request.getParameter("vote"));
	}
	
	if(rating > 0) {
		RateUserController rateUserController = new RateUserController();
		try {
			rateUserController.rateUser(sessionBean, userBean, eventBean, rating);
			
			if(sessionBean.getUserBean().getUserType().equalsIgnoreCase("HOST")) {
%>
				<jsp:forward page="guest_profile.jsp"></jsp:forward>
<%
			}
			else {
%>
				<jsp:forward page="guest_event_page.jsp"></jsp:forward>
<%	
			}
		} catch(AlreadyRatedException are) {
			session.setAttribute("errormsg", "You already rated that user");
%>
			<jsp:forward page="error_page.jsp"></jsp:forward>
<%
		} catch(Exception e) {
%>
			<jsp:forward page="error_page.jsp"></jsp:forward>
<%
		}
	}
	else {
		session.setAttribute("errormsg", "You must select a vote first");
%>
		<jsp:forward page="error_page.jsp"></jsp:forward>
<%
	}
%>
</body>
</html>