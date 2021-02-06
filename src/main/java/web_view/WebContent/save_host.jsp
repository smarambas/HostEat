<%@page import="control.SaveHostController"%>
<%@page import="bean.SessionBean"%>
<%@page import="bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page errorPage = "generic_error.jsp" %>

<!DOCTYPE html>
<html lang="">
<head>
	<meta charset="UTF-8">
	<title>HostEat</title>
</head>
<body>
<%
	SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");
	UserBean hostBean = (UserBean) session.getAttribute("selectedHost");
	
	SaveHostController saveHostController = new SaveHostController();
	SessionBean newSessionBean = saveHostController.saveHost(sessionBean, sessionBean.getUserBean(), hostBean);
	
	session.setAttribute("sessionBean", newSessionBean);
%>
	<jsp:forward page="host_profile.jsp"></jsp:forward>
</body>
</html>