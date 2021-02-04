<%@page import="bean.EventBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="">
<head>
	<meta charset="UTF-8">
	<title>HostEat</title>
</head>
<body>

<%
EventBean eventBean = (EventBean) session.getAttribute("selectedEvent");
out.println(eventBean.getDateTime());
%>
	
</body>
</html>