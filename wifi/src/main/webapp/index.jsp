<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="dao.WifiDao" %>
<%@ page import="Service.DBService" %>
<%@ page import="Service.LocationHistory" %>
<%
    String latStr = request.getParameter("lat");
    String lngStr = request.getParameter("lnt");
    List<WifiDao> wifiInfos = null;
    String errorMessage = null;
    DBService dbService = new DBService();
    LocationHistory locationHistory = new LocationHistory();  // Ensure this instantiation matches your class structure

    if (latStr != null && lngStr != null) {
        try {
            double lat = Double.parseDouble(latStr);
            double lng = Double.parseDouble(lngStr);
            String searchedAt = new java.sql.Timestamp(System.currentTimeMillis()).toString();
            locationHistory.saveLocationHistory(lat, lng, searchedAt);
            wifiInfos = dbService.getNearbyWifi(lat, lng, 20);
        } catch (Exception e) {
            errorMessage = e.getMessage();
            e.printStackTrace();
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<script src="location.js"></script>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>와이파이 정보 구하기</h1>
    <div class="header">
        <a href="index.jsp">홈</a>
        <a href="location_history.jsp">위치 히스토리 목록</a>
        <a href="WifiInfoServlet?action=fetchApiData">Open API 와이파이 정보 가져오기</a>
        <a href="bookmark-list.jsp">북마크 보기</a>
        <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    </div>
    <div>
        <form action="index.jsp" method="post">
            <p>
                LAT: <input type="text" id="lat" name="lat" value="">
                LNT: <input type="text" id="lnt" name="lnt" value="">
                <input type="button" value="내 위치 가져오기" onclick="getLocation()">
                <input type="submit" value="근처 찾기">
            </p>
        </form>
    </div>
    <%
    if (errorMessage != null) {
    %>
        <p style="color:red;">Error: <%= errorMessage %></p>
    <%
    }
    %>
    <table>
        <thead>
            <tr>
                <th>거리 (Km)</th>
                <th>관리번호</th>
                <th>자치구</th>
                <th>와이파이명</th>
                <th>도로명주소</th>
                <th>상세주소</th>
                <th>설치위치 (층)</th>
                <th>설치유형</th>
                <th>설치기관</th>
                <th>서비스 구분</th>
                <th>망종류</th>
                <th>설치년도</th>
                <th>실내외 구분</th>
                <th>WIFI접속 환경</th>
                <th>X좌표</th>
                <th>Y좌표</th>
                <th>작업일자</th>
            </tr>
        </thead>
        <tbody>
        <%
        if (wifiInfos != null) {
            for (WifiDao info : wifiInfos) {
        %>
            <tr>
                <td><%= String.format("%.2f", info.getDistance()) %></td>
                <td><%= info.getX_SWIFI_MGR_NO() %></td>
                <td><%= info.getX_SWIFI_WRDOFC() %></td>
                <td><a href="detail.jsp?X_SWIFI_MGR_NO=<%= info.getX_SWIFI_MGR_NO() %>"><%= info.getX_SWIFI_MAIN_NM() %></a></td>
                <td><%= info.getX_SWIFI_ADRES1() %></td>
                <td><%= info.getX_SWIFI_ADRES2() %></td>
                <td><%= info.getX_SWIFI_INSTL_FLOOR() %></td>
                <td><%= info.getX_SWIFI_INSTL_TY() %></td>
                <td><%= info.getX_SWIFI_INSTL_MBY() %></td>
                <td><%= info.getX_SWIFI_SVC_SE() %></td>
                <td><%= info.getX_SWIFI_CMCWR() %></td>
                <td><%= info.getX_SWIFI_CNSTC_YEAR() %></td>
                <td><%= info.getX_SWIFI_INOUT_DOOR() %></td>
                <td><%= info.getX_SWIFI_REMARS3() %></td>
                <td><%= info.getLAT() %></td>
                <td><%= info.getLNT() %></td>
                <td><%= info.getWORK_DTTM() %></td>
            </tr>
        <%
            }
        }
        %>
        </tbody>
    </table>
</body>
</html>
