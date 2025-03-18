<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="headerPage/styles_header.css">
</head>
<body>
	<header class="header">
            <div class="header-content">
            	<div>
                    <a href="doctorShiftPage.jsp" class="logo">medi<span>son</span></a>
                </div>
                <div class="auth-buttons">
                <!-- Check for the session and display the relevant buttons -->
                    <% 	if (session.getAttribute("username") != null) { 
				    %>		
						    <form action="LogoutServlet" method="post">
					    		<button type="submit" id="logout" class="btn">logout</button>
			    			</form>
				    		<a href="doctorShiftPage.jsp" class="btn"><%= session.getAttribute("username") %></a>
				    <% 	} else { 
				    %>		<a href="login.jsp" class="btn">SignIn</a>
				       	 	<a href="register.jsp" class="btn">SignOut</a>
				    <% 	} 
				    %>
                </div>
            </div>
    </header>
</body>
</html>