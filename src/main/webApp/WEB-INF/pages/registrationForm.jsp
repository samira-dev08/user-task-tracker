<%--
  Created by IntelliJ IDEA.
  User: SamNar
  Date: 2.06.2023
  Time: 23:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
    <link rel="stylesheet"  type="text/css" href="/webApp/css/main.css">
</head>
<body>
<div class="container">
    <h1>
        User Registration
    </h1>
    <form action="api/v1/auth/sign-up" method="post">

        <label><b>Username:</b></label>
        <input type="text" placeholder="username" name="userName"/>
        <br>

        <label><b>User Email:</b></label>
        <input type="text" placeholder="Email" name="userEmail"/>
        <br>
        <label><b>Password:</b></label>
        <input type="password" placeholder="password" name="password"/>
        <br>

        <button type="submit" class="registerbtn">Sign Up</button>
    </form>
</div>
</body>
</html>
