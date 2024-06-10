<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Service.LocationHistory" %>
<%@ page import="dao.LocationDao" %>
<%
    LocationHistory locationHistory = new LocationHistory();
    List<LocationDao> locationHistories = locationHistory.getLocationHistory();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>위치 히스토리</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>위치 히스토리</h1>
    <div class="header">
        <a href="index.jsp">홈</a>
        <a href="location_history.jsp">위치 히스토리 목록</a>
        <a href="WifiInfoServlet?action=fetchApiData">Open API 와이파이 정보 가져오기</a>
        <a href="bookmark-list.jsp">북마크 보기</a>
        <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    </div>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>위도</th>
                <th>경도</th>
                <th>검색 시간</th>
            </tr>
        </thead>
        <tbody>
        <%
            if (locationHistories != null) {
                for (LocationDao history : locationHistories) {
        %>
            <tr>
                <td><%= history.getId() %></td>
                <td><%= history.getLatitude() %></td>
                <td><%= history.getLongitude() %></td>
                <td><%= history.getSearchedAt() %></td>
            </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</body>
</html>
