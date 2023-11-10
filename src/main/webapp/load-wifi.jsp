
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="API.SaveApiData" %>
<%

  SaveApiData saveApiData = new SaveApiData();
  int result = saveApiData.getAPI();

%>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <div>
    <%
      if (result >= 1){
    %>
    <h1>와이파이 정보 <%=result%>개 가져오기 성공</h1>
    <%
      }else {
    %>
    <h1>Open API 와이파이 정보 가져오기 실패</h1>
    <%
      }
    %>
    <a href="home.jsp">홈으로 돌아가기</a>
  </div>
</body>
</html>
