<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Service.BookMarkService" %>
<%@ page import="dao.BookMarkDao" %>

<%
    String bookmarkIdParam = request.getParameter("bookmark_id");
    int bookmarkId = -1;
    BookMarkDao bookmark = null;

    if (bookmarkIdParam != null && !bookmarkIdParam.isEmpty()) {
        bookmarkId = Integer.parseInt(bookmarkIdParam);
        
        BookMarkService bookmarkService = new BookMarkService();
        bookmark = bookmarkService.getBookmarkById(bookmarkId);
    }

    String confirm = request.getParameter("confirm");

    if (bookmarkId != -1 && "yes".equals(confirm)) {
        BookMarkService bookmarkService = new BookMarkService();
        bookmarkService.deleteBookmark(bookmarkId);
        response.sendRedirect("bookmark-list.jsp");
    } 
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>북마크 삭제</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
        <h1>북마크 삭제</h1>
        <div class="header">
            <a href="index.jsp">홈</a>
            <a href="location_history.jsp">위치 히스토리 목록</a>
            <a href="WifiInfoServlet?action=fetchApiData">Open API 와이파이 정보 가져오기</a>
            <a href="bookmark-list.jsp">북마크 보기</a>
            <a href="bookmark-group.jsp">북마크 그룹 관리</a>
        </div>
    <form action="bookmark-delete.jsp" method="post">
        <input type="hidden" name="bookmark_id" value="<%= bookmarkId %>">
        <table>
            <tr>
                <th>북마크 이름</th>
                <td><%= bookmark != null ? bookmark.getGroupName() : "" %></td>
            </tr>
            <tr>
                <th>와이파이명</th>
                <td><%= bookmark != null ? bookmark.getWifiId() : "" %></td>
            </tr>
            <tr>
                <th>등록일자</th>
                <td><%= bookmark != null ? bookmark.getRegDate() : "" %></td>
            </tr>
        <tr>
            <td colspan="2" style="text-align: center;">
        		<button type="submit" name="confirm" value="yes">삭제</button>
                <a href="bookmark-list.jsp">돌아가기</a>
        	</td>
        	
        </tr>
         </table>
        
    </form>
</body>
</html>
