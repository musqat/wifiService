<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="dao.BookMarkDao" %>
<%@ page import="Service.BookMarkService" %>
<%
    BookMarkService bookmarkService = new BookMarkService();
    List<BookMarkDao> bookmarks = bookmarkService.getAllBookmarks();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>북마크 목록</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>북마크 목록</h1>
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
                <th>북마크 그룹</th>
                <th>와이파이 이름</th>
                <th>등록일자</th>
                <th>비고</th>
            </tr>
        </thead>
        <tbody>
        <%
        if (bookmarks != null && !bookmarks.isEmpty()) {
            for (BookMarkDao bookmark : bookmarks) {
        %>
            <tr>
                <td><%= bookmark.getId() %></td>
                <td><%= bookmark.getGroupName() %></td>
                <td><%= bookmark.getWifiId() %></td>
                <td><%= bookmark.getRegDate() %></td>
                <td>
                    <form action="bookmark-delete.jsp" method="post" style="display:inline;">
                        <input type="hidden" name="bookmark_id" value="<%= bookmark.getId() %>">
                        <button type="submit">삭제</button>
                    </form>
                </td>
            </tr>
        <%
            }
        } else {
        %>
            <tr>
                <td colspan="5">북마크가 없습니다.</td>
            </tr>
        <%
        }
        %>
        </tbody>
    </table>
</body>
</html>
