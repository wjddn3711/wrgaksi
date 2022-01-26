<%--
  Created by IntelliJ IDEA.
  User: jungwoo
  Date: 2022/01/26
  Time: 8:37 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="true" %>
<html>
<head>
  <title>알림창</title>
</head>
<body>
<script>
  alert('${msg}');
  location.href='<c:out value="${pageContext.request.contextPath}"/>${url}';
</script>
</body>
</html>