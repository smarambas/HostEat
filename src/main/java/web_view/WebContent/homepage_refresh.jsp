<%@page import="control.RefreshController"%>
<%@page import="bean.UserBean"%>
<%@page import="bean.SessionBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page errorPage = "generic_error.jsp" %>

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
	
	RefreshController refreshController = new RefreshController();
	SessionBean newSessionBean = refreshController.refresh(userBean);
	
	session.setAttribute("sessionBean", newSessionBean);
%>
	<jsp:forward page="homepage.jsp"></jsp:forward>
</body>
</html>