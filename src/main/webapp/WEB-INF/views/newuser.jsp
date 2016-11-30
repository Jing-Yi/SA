<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="en">
<head>
 	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    

    <title>DVD</title>
</head>


<body>
	<%@include file="navbar.jspf" %>
	<div class="container theme-showcase" role="main">
    
      <div class="jumbotron" >    
        <h1>管理員註冊介面</h1>
        <p class="lead">輸入資料 </p>
      </div>
  
<form action="newuser" method ="post">
	帳號:<input type="text" name ="name"/></br>
	電話:<input type="text" name ="phone"/></br>
	地址:<input type="text" name ="address"/></br>
	密碼:<input type="password" name ="password"/></br>

	<button type="submit" class="btn btn-primary">登入</button>
</form>
</body>
</html>


