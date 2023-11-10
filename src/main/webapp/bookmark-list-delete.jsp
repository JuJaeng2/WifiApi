
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
  <link href="CSS/style.css" rel="stylesheet" type="text/css">
  <script>
    function deleteBookmarkList(blId){

      let xhr = new XMLHttpRequest();
      xhr.open('DELETE', 'deleteBookmarkList?blId=' + blId, true);
      xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
      xhr.send();

      xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
          if (xhr.status === 200) {
            alert(xhr.responseText);
            window.location.href ='bookmark-list.jsp';
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
<h1>북마크 리스트 삭제</h1>
<div id = "buttons">
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
<%
  String blId = request.getParameter("blId");
  String bgName = request.getParameter("bgName");
  String wifiName = request.getParameter("wifiName");
  String rgDate = request.getParameter("rgDate");
%>
<table id="detail">
  <tr>
    <td>북마크명</td>
    <td><%=bgName%></td>
  </tr>
  <tr>
    <td>와이파이명</td>
    <td><%=wifiName%></td>
  </tr>
  <tr>
    <td>등록일자</td>
    <td><%=rgDate%></td>
  </tr>
  <tr>
    <td colspan="2" id="update_button">
      <a href="bookmark-list.jsp">돌아가기</a>
      <button onclick="deleteBookmarkList('<%=blId%>')">삭제</button>
    </td>
  </tr>
</table>
</body>
</html>
