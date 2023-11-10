<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>북마크 목록</title>
    <link href="CSS/style.css" rel="stylesheet" type="text/css">
</head>
<body>

<h1>북마크 목록</h1>
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
    try{
        connection = DriverManager.getConnection(url, userId, password);
        statement = connection.createStatement();
        String sql = "select bm.id as id, bl.name as bgName ,wi.X_SWIFI_MAIN_NM as wifiName, bm.register_date as registerDate" +
                " from bookmark as bm " +
                "     left join mission1db.bookmark_list bl " +
                "        on bl.id = bm.bl_id " +
                "     left join mission1db.wifi_info wi " +
                "        on bm.X_SWIFI_MGR_NO = wi.X_SWIFI_MGR_NO " +
                ";";
        rs = statement.executeQuery(sql);


%>
<table class="information">
    <tr>
        <th>ID</th>
        <th>북마크 이름</th>
        <th>와이파이 명</th>
        <th>등록일자</th>
        <th>비고</th>
    </tr>
    <%
        if (!rs.next()){
            %>
    <tr>
        <td colspan="5">등록된 북마크가 없습니다. 북마크를 등록해주세요</td>
    </tr>
    <%
        }else {
            rs.beforeFirst();
            while(rs.next()){
                String blId = rs.getString("id");
                String bgName = rs.getString("bgName");
                String wifiName = rs.getString("wifiName");
                String registerDate = rs.getString("registerDate");



            %>
        <tr>
            <td><%=blId%></td>
            <td><%=bgName%></td>
            <td><%=wifiName%></td>
            <td><%=registerDate%></td>
            <td><a href="bookmark-list-delete.jsp?blId=<%=blId%>&bgName=<%=bgName%>&wifiName=<%=wifiName%>&rgDate=<%=registerDate%>">삭제</a></td>
        </tr>
    <%
            }
        }
    %>

</table>
<%
    }catch (SQLException e){
    e.printStackTrace();
    }finally {
        try{
            if (rs != null && !rs.isClosed()){
                rs.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            if (statement != null && !statement.isClosed()){
                statement.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            if (connection != null && !connection.isClosed()){
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
%>
</body>
</html>
