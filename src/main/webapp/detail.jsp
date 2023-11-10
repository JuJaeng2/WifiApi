<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String wifiId = request.getParameter("wifiId");
    String distance = request.getParameter("distance");

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
        String sql = "select * from bookmark_list";
        rs = statement.executeQuery(sql);

%>
<html>
<head>
    <title>상세정보</title>
    <link href="CSS/style.css" rel="stylesheet" type="text/css">
    <script>
        function addToBookmark(wifiId){
            let bgId = document.getElementById('bgId').value;

            let xhr = new XMLHttpRequest();
            xhr.open('POST', 'addToBookmark', true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.send("bgId=" + bgId + "&wifiId=" + wifiId);

            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        alert(xhr.responseText);
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
<h1>와이파이 정보 구하기</h1>
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
<div>
    <select name="BGName" id="bgId">
        <option value="" disabled>--북마크 그룹 이름 선택--</option>
        <%
            while(rs.next()){
        %>
        <option value="<%=rs.getString("id")%>"><%=rs.getString("name")%></option>
        <%
            }
        %>
    </select>
    <button onclick="addToBookmark('<%=wifiId%>')">북마크 추가하기</button>
</div>
<div id="table_div">
<table id="detail">
<%
    sql = "select * from wifi_info where X_SWIFI_MGR_NO='" + wifiId +"'";
    rs = statement.executeQuery(sql);
    while(rs.next()){
        %>
    <tr>
        <td>거리</td>
        <td><%=distance%></td>
    </tr>
    <tr>
        <td>관리번호</td>
        <td><%=rs.getString("X_SWIFI_MGR_NO")%></td>
    </tr>
    <tr>
        <td>자치구</td>
        <td><%=rs.getString("X_SWIFI_WRDOFC")%></td>
    </tr>
    <tr>
        <td>와이파이명</td>
        <td><%=rs.getString("X_SWIFI_MAIN_NM")%></td>
    </tr>
    <tr>
        <td>도로명주소</td>
        <td><%=rs.getString("X_SWIFI_ADRES1")%></td>
    </tr>
    <tr>
        <td>상세주소</td>
        <td><%=rs.getString("X_SWIFI_ADRES2")%></td>
    </tr>
    <tr>
        <td>설치위치(층)</td>
        <td><%=rs.getString("X_SWIFI_INSTL_FLOOR")%></td>
    </tr>
    <tr>
        <td>설치유형</td>
        <td><%=rs.getString("X_SWIFI_INSTL_TY")%></td>
    </tr>
    <tr>
        <td>설치기관</td>
        <td><%=rs.getString("X_SWIFI_INSTL_MBY")%></td>
    </tr>
    <tr>
        <td>서비스구분</td>
        <td><%=rs.getString("X_SWIFI_SVC_SE")%></td>
    </tr>
    <tr>
        <td>망종류</td>
        <td><%=rs.getString("X_SWIFI_CMCWR")%></td>
    </tr>
    <tr>
        <td>설치년도</td>
        <td><%=rs.getString("X_SWIFI_CNSTC_YEAR")%></td>
    </tr>
    <tr>
        <td>실내외 구분</td>
        <td><%=rs.getString("X_SWIFI_INOUT_DOOR")%></td>
    </tr>
    <tr>
        <td>WIFI접속 환경</td>
        <td><%=rs.getString("X_SWIFI_REMARS3")%></td>
    </tr>
    <tr>
        <td>X좌표</td>
        <td><%=rs.getString("LAT")%></td>
    </tr>
    <tr>
        <td>Y좌표</td>
        <td><%=rs.getString("LNT")%></td>
    </tr>
    <tr>
        <td>작업일자</td>
        <td><%=rs.getString("WORK_DTTM")%></td>
    </tr>
    <%
    }
%>
</table>
</div>
</body>
</html>
<%
    }catch(SQLException e){
        e.printStackTrace();
    }finally {
        try{
            if(rs != null && !rs.isClosed()){
                rs.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            if(statement != null && !statement.isClosed()){
                statement.isClosed();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
%>