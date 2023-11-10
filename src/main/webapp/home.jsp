
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="API.GetWifiInfo" %>
<%@ page import="DTO.InfoDTO" %>
<%@ page import="java.util.List" %>
<%
  String curLat = request.getParameter("lat");
  String curLnt = request.getParameter("lnt");

  System.out.println("Lat : " + curLat);
  System.out.println("Lnt : " + curLnt);

  GetWifiInfo getWifiInfo = new GetWifiInfo();
%>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link href="CSS/style.css" rel="stylesheet" type="text/css">

  <script>
    function getLocation(){
      let lat;
      let lnt
      window.navigator.geolocation.getCurrentPosition(function (position){
        lat = position.coords.latitude;
        lnt = position.coords.longitude;

        document.getElementById('input_lat').value = lat;
        document.getElementById('input_lnt').value = lnt;

        //비동기식 처리
        let xhr = new XMLHttpRequest();
        xhr.open("POST", "saveHistory.jsp", true);
        xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        xhr.send("lnt=" + lnt + "&lat=" + lat);
      },
      function (error){
        switch (error.code){
          case error.PERMISSION_DENIED:
            alert("사용자 거부");
            break;
          case error.POSITION_UNAVAILABLE:
            alert("지리정보 없음");
            break;
          case error.TIMEOUT:
            alert("시간 초과");
            break;
        }
      })


    }

  </script>
</head>
<body>

<h1>와이파이 정보 구하기</h1>
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

<div id="location">
  <form action="home.jsp" method="post">
    <span>LAT : </span>
    <input type="text" value="<%=curLat%>" id="input_lat" name="lat">
    <span>, </span>
    <span>LNT : </span>
    <input type="text" value="<%=curLnt%>" id="input_lnt" name="lnt">
    <input type="button" onclick="getLocation()" value="내 위치 정보 가져오기">
    <button type="submit">근처 WIFI정보 갸져오기</button>
  </form>
</div>

<table class="information">
  <tr>
    <th>거리</th>
    <th>관리번호</th>
    <th>자치구</th>
    <th>와이파이명</th>
    <th>도로명주소</th>
    <th>상세주소</th>
    <th>설치위치(층)</th>
    <th>설치유형</th>
    <th>설치기관</th>
    <th>서비스구분</th>
    <th>망종류</th>
    <th>설치년도</th>
    <th>실내외 구분</th>
    <th>WIFI접속 환경</th>
    <th>X좌표</th>
    <th>Y좌표</th>
    <th>작업일자</th>
  </tr>
<%
  if (curLat == null) {

%>
  <tr>
    <td colspan="17">
      <span>위치 정보를 입력해 주세요.</span>
    </td>
  </tr>
  <%
  } else {

    List<InfoDTO> result = getWifiInfo.getWifiInfo(Double.parseDouble(curLat), Double.parseDouble(curLnt));
    System.out.println("result >> " + result);

    for (InfoDTO info : result) {
      %>
  <tr>
    <td><%=info.getDistance()%></td>
    <td><%=info.getX_SWIFI_MGR_NO()%></td>
    <td><%=info.getX_SWIFI_WRDOFC()%></td>
    <td><a href="detail.jsp?wifiId=<%=info.getX_SWIFI_MGR_NO()%>&distance=<%=info.getDistance()%>"><%=info.getX_SWIFI_MAIN_NM()%></a> </td>
    <td><%=info.getX_SWIFI_ADRES1()%></td>
    <td><%=info.getX_SWIFI_ADRES2()%></td>
    <td><%=info.getX_SWIFI_INSTL_FLOOR()%></td>
    <td><%=info.getX_SWIFI_INSTL_TY()%></td>
    <td><%=info.getX_SWIFI_INSTL_MBY()%></td>
    <td><%=info.getX_SWIFI_SVC_SE()%></td>
    <td><%=info.getX_SWIFI_CMCWR()%></td>
    <td><%=info.getX_SWIFI_CNSTC_YEAR()%></td>
    <td><%=info.getX_SWIFI_INOUT_DOOR()%></td>
    <td><%=info.getX_SWIFI_REMARS3()%></td>
    <td><%=info.getLAT()%></td>
    <td><%=info.getLNT()%></td>
    <td><%=info.getWORK_DTTM()%></td>
  </tr>
  <%
    }
  %>

  <%
    }
  %>


</table>
</body>
</html>
