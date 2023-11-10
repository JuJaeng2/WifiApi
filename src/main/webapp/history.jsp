<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>위치 히스토리</title>
    <link href="CSS/style.css" rel="stylesheet" type="text/css">
    <script>
        function deleteHistory(id){
            let xhr = new XMLHttpRequest();
            xhr.open("DELETE", "deleteHistory?id=" + id, true);
            xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            xhr.send();

            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        location.reload();
                        console.log(xhr.responseText);
                    } else {
                        // 오류 처리
                        console.error('Error:', xhr.status);
                        console.log(xhr.responseText);
                    }
                }
            };
        }
    </script>
</head>
<body>
<%
    String url = "jdbc:mariadb://localhost:3306/mission1db";
    String userId = "mission1";
    String password = "mission1";

    try {
        Class.forName("org.mariadb.jdbc.Driver");
    }catch (ClassNotFoundException e){
        e.printStackTrace();
    }

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;

    try {
        connection = DriverManager.getConnection(url, userId, password);
        statement = connection.createStatement();
        String sql = "select * from location_history order by id desc";
        rs = statement.executeQuery(sql);



%>
<h1>위치 히스토리 페이지</h1>
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

<table class="information">
    <tr>
        <th>ID</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>조회일자</th>
        <th>비고</th>
    </tr>
    <%

        while(rs.next()){


            %>
    <tr>
        <td><%=rs.getString("id")%></td>
        <td><%=rs.getString("X_coordinate")%></td>
        <td><%=rs.getString("Y_coordinate")%></td>
        <td><%=rs.getString("search_date")%></td>
        <td>
            <button onclick="deleteHistory(<%=rs.getString("id")%>)">삭제</button>
<%--            <a href="deleteHistory?name=test">삭제</a>--%>
        </td>
    </tr>
    <%
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if (rs != null && !rs.isClosed()){
                    rs.close();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }

            try{
                if(statement != null && !statement.isClosed()){
                    statement.close();
                }
            }catch(SQLException e ){
                e.printStackTrace();
            }

            try{
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }

        }
    %>

</table>
</body>
</html>

