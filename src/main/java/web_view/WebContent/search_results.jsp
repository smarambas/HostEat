<%@page import="exceptions.NoRecordFoundException"%>
<%@page import="exceptions.WrongDateException"%>
<%@page import="control.SearchEventController"%>
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
	<div class="header">
		<input type="button" id="btn" value="Homepage" onclick="window.location.href='homepage.jsp'">
		<input type="button" id="btn" value="Userpage" onclick="window.location.href='userpage.jsp'">
		<input type="button" id="btn" value="Notifications" onclick="window.location.href='notifications.jsp'">
		<input type="button" id="btn" value="Favorites" onclick="window.location.href='favorites.jsp'">
	</div>
	<input type="button" id="btn" value="Back" onclick="window.location.href='search_event.jsp'">
	<div class="container">
		<strong id="pagetitle">Search results</strong>
		<br><br>
<%
		SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");
		EventBean eventBean = new EventBean();
		
		if(sessionBean.getSearchedList().isEmpty()) {
			if(request.getParameter("region") != null) {
				eventBean.setRegionString(request.getParameter("region"));
			}
			else {
				eventBean.setRegionString("");
			}
			
			if(request.getParameter("province") != null) {
				eventBean.setProvinceString(request.getParameter("province"));
			}
			else {
				eventBean.setProvinceString("");
			}
			
			if(request.getParameter("city") != null) {
				eventBean.setCityString(request.getParameter("city"));
			}
			else {
				eventBean.setCityString("");
			}
			
			if(request.getParameter("date") != null) {
				eventBean.setDateTime(request.getParameter("date") + " " + request.getParameter("hours") + ":" + request.getParameter("minutes"));
			}
			else {
				session.setAttribute("errormsg", "Please, specify a date in the future");
%>
				<jsp:forward page="error_page.jsp"></jsp:forward>
<%
			}
		
			SearchEventController searchEventController = new SearchEventController();
			try {
				SessionBean newSessionBean = searchEventController.searchEvent(sessionBean, eventBean);
				session.setAttribute("sessionBean", newSessionBean);
				
				int i = 0;
				for(EventBean eb : newSessionBean.getSearchedList()) {
					out.println(eb.getDateTime());
					out.println("<br>");
					out.println(eb.getRegionString() + "(" + eb.getProvinceString() + ")");
					out.println("<br>");
					out.println(eb.getCityString());
					out.println("<br><br>");
					session.setAttribute("result" + i, eb);
%>
					<form action="result_event_page.jsp">
						<input type="submit" id="btn" value="Open event" name="open<%=i%>">
					</form>
					<hr>
<%
					i++;
				}
				
			} catch(IllegalArgumentException iae) {
				session.setAttribute("errormsg", "Please, insert at least one research field");
%>
				<jsp:forward page="error_page.jsp"></jsp:forward>
<%
			} catch (WrongDateException wde) {
				session.setAttribute("errormsg", "The date and time picked is in the past, try again");
%>
				<jsp:forward page="error_page.jsp"></jsp:forward>
<%
			} catch (NoRecordFoundException nrfe) {
				session.setAttribute("errormsg", "No events found, please try again");
%>
				<jsp:forward page="error_page.jsp"></jsp:forward>
<%
			} catch (Exception e) {
%>
				<jsp:forward page="error_page.jsp"></jsp:forward>
<%
			}
		}
		else {
			int i = 0;
			for(EventBean eb : sessionBean.getSearchedList()) {
				out.println(eb.getDateTime());
				out.println("<br>");
				out.println(eb.getRegionString() + "(" + eb.getProvinceString() + ")");
				out.println("<br>");
				out.println(eb.getCityString());
				out.println("<br><br>");
				session.setAttribute("result" + i, eb);
%>
				<form action="result_event_page.jsp">
					<input type="submit" id="btn" value="Open event" name="open<%=i%>">
				</form>
				<hr>
<%
				i++;
			}
		}

%>		
	</div>
</body>
</html>