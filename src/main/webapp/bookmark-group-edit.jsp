<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link href="CSS/style.css" rel="stylesheet" type="text/css">
    <script>
        function updateBookmarkGroup(name, order, id){

            let xhr = new XMLHttpRequest();
            xhr.open("POST", "updateBookmarkGroup", true);
            xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            xhr.send("name=" + name + "&order=" + order + "&id=" + id);

            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        alert(xhr.response);
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

<h1>북마크 그룹 수정</h1>
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
<%
    String name = request.getParameter("name");
    String order = request.getParameter("order");
    String id = request.getParameter("id");
    System.out.println("name : " + name);
    System.out.println("order : " + order);
    System.out.println("id : " + id);
%>
    <table id="detail">
        <tr>
            <td>북마크 이름</td>
            <td>
                <input value="<%=name%>" name="name" required id="name">
            </td>
        </tr>
        <tr>
            <td>순서</td>
            <td>
                <input value="<%=order%>" name="order" required id="order">
            </td>
        </tr>
        <tr>
            <td colspan="2" id="update_button">
                <a href="bookMark-group.jsp">돌아가기</a>
                <span> | </span>
                <button onclick="updateBookmarkGroup(document.getElementById('name').value, document.getElementById('order').value, <%=id%>)">수정</button>
            </td>

        </tr>
    </table>


</body>
</html>
