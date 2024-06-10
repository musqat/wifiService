<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.WifiDao" %>
<%@ page import="Service.DBService" %>
<%@ page import="Service.BookMarkGroupService" %>
<%@ page import="dao.BookMarkGroupDao" %>
<%@ page import="Service.BookMarkService" %>
<%@ page import="dao.BookMarkDao" %>
<%@ page import="java.util.List" %>
<%
    String managerNo = request.getParameter("X_SWIFI_MGR_NO");
    WifiDao wifiInfo = null;
    if (managerNo != null) {
        DBService dbService = new DBService();
        wifiInfo = dbService.getWifiInfoByManagerNo(managerNo);
    }

    BookMarkGroupService groupService = new BookMarkGroupService();
    List<BookMarkGroupDao> groups = groupService.getAllGroups();

    BookMarkService bookmarkService = new BookMarkService();
    List<BookMarkDao> bookmarks = null;
    if (wifiInfo != null) {
        bookmarks = bookmarkService.getBookmarksByWifiMainNm(wifiInfo.getX_SWIFI_MAIN_NM());
    }

    String action = request.getParameter("action");
    if ("addBookmark".equals(action)) {
        if (wifiInfo != null) {
            int groupId = Integer.parseInt(request.getParameter("group_id"));
            bookmarkService.addBookmark(wifiInfo.getX_SWIFI_MGR_NO(), groupId);
            response.sendRedirect("detail.jsp?X_SWIFI_MGR_NO=" + managerNo);
        } else {
            out.println("<p>Error: Wi-Fi information is not available.</p>");
        }
    } else if ("deleteBookmark".equals(action)) {
        int bookmarkId = Integer.parseInt(request.getParameter("bookmark_id"));
        bookmarkService.deleteBookmark(bookmarkId);
        response.sendRedirect("detail.jsp?X_SWIFI_MGR_NO=" + managerNo);
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 상세보기</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>와이파이 정보 상세보기</h1>
    <div class="header">
        <a href="index.jsp">홈</a>
        <a href="location_history.jsp">위치 히스토리 목록</a>
        <a href="WifiInfoServlet?action=fetchApiData">Open API 와이파이 정보 가져오기</a>
        <a href="bookmark-list.jsp">북마크 보기</a>
        <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    </div>
    <form action="detail.jsp" method="post">
        <input type="hidden" name="action" value="addBookmark">
        <input type="hidden" name="X_SWIFI_MGR_NO" value="<%= managerNo %>">
        <select name="group_id" required>
            <option value="" disabled selected>북마크 그룹 선택</option>
            <% for (BookMarkGroupDao group : groups) { %>
                <option value="<%= group.getId() %>"><%= group.getGroupName() %></option>
            <% } %>
        </select>
        <button type="submit">북마크 추가</button>
    </form>
    <div>
        <% if (wifiInfo != null) { %>
            <table class="detail-table">
                <tr><th>거리(Km)</th><td>0.0000</td></tr>
                <tr><th>관리번호</th><td><%= wifiInfo.getX_SWIFI_MGR_NO() %></td></tr>
                <tr><th>자치구</th><td><%= wifiInfo.getX_SWIFI_WRDOFC() %></td></tr>
                <tr><th>와이파이명</th><a href="detail.jsp?X_SWIFI_MGR_NO=<%= wifiInfo.getX_SWIFI_MGR_NO() %>"><td><%= wifiInfo.getX_SWIFI_MAIN_NM() %></td></a></tr>
                <tr><th>도로명주소</th><td><%= wifiInfo.getX_SWIFI_ADRES1() %></td></tr>
                <tr><th>상세주소</th><td><%= wifiInfo.getX_SWIFI_ADRES2() %></td></tr>
                <tr><th>설치위치 (층)</th><td><%= wifiInfo.getX_SWIFI_INSTL_FLOOR() %></td></tr>
                <tr><th>설치유형</th><td><%= wifiInfo.getX_SWIFI_INSTL_TY() %></td></tr>
                <tr><th>설치기관</th><td><%= wifiInfo.getX_SWIFI_INSTL_MBY() %></td></tr>
                <tr><th>서비스 구분</th><td><%= wifiInfo.getX_SWIFI_SVC_SE() %></td></tr>
                <tr><th>망종류</th><td><%= wifiInfo.getX_SWIFI_CMCWR() %></td></tr>
                <tr><th>설치년도</th><td><%= wifiInfo.getX_SWIFI_CNSTC_YEAR() %></td></tr>
                <tr><th>실내외 구분</th><td><%= wifiInfo.getX_SWIFI_INOUT_DOOR() %></td></tr>
                <tr><th>WIFI접속 환경</th><td><%= wifiInfo.getX_SWIFI_REMARS3() %></td></tr>
                <tr><th>X좌표</th><td><%= wifiInfo.getLAT() %></td></tr>
                <tr><th>Y좌표</th><td><%= wifiInfo.getLNT() %></td></tr>
                <tr><th>작업일자</th><td><%= wifiInfo.getWORK_DTTM() %></td></tr>
            </table>
        <% } else { %>
            <p>와이파이 정보를 찾을 수 없습니다.</p>
        <% } %>
    </div>
</body>
</html>
