<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Service.BookMarkGroupService" %>
<%@ page import="dao.BookMarkGroupDao" %>
<%

	request.setCharacterEncoding("UTF-8");
    BookMarkGroupService service = new BookMarkGroupService();
    int id = Integer.parseInt(request.getParameter("id"));
    BookMarkGroupDao group = service.getGroupById(id);
    if (request.getMethod().equalsIgnoreCase("POST")) {
        String groupName = request.getParameter("group_name");
        int displayOrder = Integer.parseInt(request.getParameter("display_order"));
        service.updateGroup(id, groupName, displayOrder);
        response.sendRedirect("bookmark-group.jsp");
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 그룹 수정</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>북마크 그룹 수정</h1>
    <div class="header">
        <a href="index.jsp">홈</a>
        <a href="location_history.jsp">위치 히스토리 목록</a>
        <a href="WifiInfoServlet?action=fetchApiData">Open API 와이파이 정보 가져오기</a>
        <a href="bookmark-list.jsp">북마크 보기</a>
        <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    </div>
    <form action="bookmark-group-update.jsp?id=<%= id %>" method="post">
        <table>
            <tr>
                <th>북마크 이름</th>
                <td><input type="text" name="group_name" value="<%= group.getGroupName() %>" required></td>
            </tr>
            <tr>
                <th>순서</th>
                <td><input type="number" name="display_order" value="<%= group.getDisplayOrder() %>" required></td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center;">
                    <button type="submit">수정</button>
                    <a href="bookmark-group.jsp">돌아가기</a>
                </td>
            </tr>
      	</table>
            
    </form>
</body>
</html>
