<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  System.out.println(response.getWriter());
%>
<html>
<head>
    <title>와이파이 정보 구하기</title>
  <link href="CSS/style.css" rel="stylesheet" type="text/css">
  <script>
    function addBookMark(){
      let name = document.getElementById("name").value;
      let order = document.getElementById("order").value;
      let xhr = new XMLHttpRequest();
      xhr.open("POST", "addBookmarkGroup", true);
      xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
      xhr.send("name=" + name + "&order=" + order);

      xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
          if (xhr.status === 200) {
            alert(xhr.responseText);
            window.location.href = 'bookMark-group.jsp';
          } else {
            // 오류 처리
            alert("에러 발생 : ", xhr.responseText);
            console.error('Error:', xhr.status);
            console.log(xhr.responseText);
          }
        }
      };
    }
  </script>
</head>
<body>

<h1>북마크 그룹 추가</h1>
<div id="buttons">
  <a href="home.jsp">홈</a>
  <span> | </span>
  <a href="history.jsp">위치 히스토리 목록</a>
  <span> | </span>
  <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
  <span> | </span>
  <a href="bookmark-list.jsp">북마크 보기</a>
  <span> | </span>
  <a href="bookMark-group.jsp">북마크 그룹 관리</a>
</div>

<table id="detail">
  <tr>
    <td>북마크 이름</td>
    <td>
      <input id="name" name="name">
    </td>
  </tr>
  <tr>
    <td>순서</td>
    <td>
      <input id="order" name="order">
    </td>
  </tr>
  <tr>
    <td colspan="2" id="update_button">
      <button onclick="addBookMark()" >추가</button>
    </td>
  </tr>
</table>


</body>
</html>
