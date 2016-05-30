
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h3>Login Successful!</h3>
        <div>
            <%--   <ul>
               <%
                   ArrayList<String> liste =(ArrayList<String>) request.getAttribute("menu");                   
                   for(String item : liste)
                   {
                       %>
                       <li><%= item %> </li>
                       <%
                   }
               %>
           </ul>
            --%>
            <ul>
                <c:forEach var="item" items="${requestScope.menu}">
                    <li>${item}</li>
                </c:forEach>
            </ul>
            <ul>
                <li><a href="/FormEx/login.jsp">Login</a></li>
                <li><a href="/FormEx/index.jsp">Index</a></li>
                <li><a href="/FormEx/contact.jsp">Contact</a></li>
                <li><a href="/FormEx/logout">LogOut</a></li>
            </ul>
        </div>
    </body>
</html>
