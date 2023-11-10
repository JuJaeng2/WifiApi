<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link href="CSS/style.css" rel="stylesheet" type="text/css">
</head>
<script>
    function deleteBookmarkGroup(id){

        let xhr = new XMLHttpRequest();
        xhr.open('DELETE', 'deleteBookmarkGroup?id=' + id, true);
        xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        xhr.send();

        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    location.reload();
                    alert(xhr.response);
                } else {
                    alert(xhr.response);
                    console.error('Error:', xhr.status);
                    console.log(xhr.responseText);
                }
            }
        };
    }
</script>
<body>

<h1>북마크 그룹</h1>
<div>
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
<div>
    <button onclick="window.location.href='bookmark-group-add.jsp'">북마크 그룹 이름 추가</button>
</div>
<%
    String url = "jdbc:mariadb://localhost:3306/mission1db";
    String userId = "mission1";
    String password = "mission1";

    try{
        Class.forName("org.mariadb.jdbc.Driver");
    }catch(ClassNotFoundException e){
        e.printStackTrace();
    }

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;

    try{
        connection = DriverManager.getConnection(url, userId, password);
        statement = connection.createStatement();
        String sql = "select * from bookmark_list";
        rs = statement.executeQuery(sql);



%>
<table class="information">
    <tr>
        <th>ID</th>
        <th>북마크 이름</th>
        <th>순서</th>
        <th>등록일자</th>
        <th>수정일자</th>
        <th>비고</th>
    </tr>
    <%
        while(rs.next()){
            %>
    <tr>
        <td><%=rs.getString("id")%></td>
        <td><%=rs.getString("name")%></td>
        <td><%=rs.getString("order")%></td>
        <td><%=rs.getString("register_date")%></td>
        <td><%=rs.getString("update_date")%></td>
        <td>
            <a href="bookmark-group-edit.jsp?name=<%=rs.getString("name")%>&order=<%=rs.getString("order")%>&id=<%=rs.getString("id")%>">
                수정
            </a>
            <a href="#" onclick="deleteBookmarkGroup(<%=rs.getString("id")%>)">삭제</a>
        </td>
    </tr>
            <%
        }
    %>
</table>
<%
    }catch(SQLException e){
        e.printStackTrace();
    }finally{

        try{
            if(rs != null && rs.isClosed()){
                rs.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            if(statement != null && !statement.isClosed()){
                statement.close();
            }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }

        try{
            if(connection != null && connection.isClosed()){
                connection.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
%>
</body>
</html>
