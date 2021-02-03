<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page isErrorPage = "true" %>   
    
<!DOCTYPE html>
<html lang="">
<head>
	<meta charset="UTF-8">
	<title>HostEat</title>
	
	<style>  
		.container {  
			text-align: center;  
			font-size: 16px;  
			padding-top: 10px;  
		}  
		
		#btn {  
			font-size: 14px;  
			margin: auto;	
		}  
		
		#pagetitle {
			text-align: center;  
			font-size: 14px;
		}
		
		#error {
			color: red
			font-size: 14px;
		}
	</style>  
</head>
<body>
	<strong>HostEat</strong>
	<p>
	<input type="button" value="Back" onclick="history.back()">
	<div class="container">
		<p id="error">Ops, something went wrong, please try again</p>
	</div>
</body>
</html>