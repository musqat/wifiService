<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Service.BookMarkGroupService" %>
<%@ page import="dao.BookMarkGroupDao" %>
<%@ page import="java.util.List" %>
<%
	request.setCharacterEncoding("UTF-8");
	BookMarkGroupService service = new BookMarkGroupService();
	List<BookMarkGroupDao> groups = service.getAllGroups();
	String action = request.getParameter("action");
	if ("add".equals(action)) {
	    String groupName = request.getParameter("group_name");
	    int displayOrder = Integer.parseInt(request.getParameter("display_order"));
	    service.addGroup(groupName, displayOrder);
	    response.sendRedirect("bookmark-group.jsp");
	} else if ("delete".equals(action)) {
	    int id = Integer.parseInt(request.getParameter("id"));
	    service.deleteGroup(id);
	    response.sendRedirect("bookmark-group.jsp");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 그룹 관리</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<script type="text/javascript">
    function confirmDelete(id) {
        if (confirm("정말 삭제하시겠습니까?")) {
            document.getElementById("deleteForm" + id).submit();
        }
    }
</script>
</head>
<body>
    <h1>북마크 그룹 관리</h1>
    <div class="header">
        <a href="index.jsp">홈</a>
        <a href="location_history.jsp">위치 히스토리 목록</a>
        <a href="WifiInfoServlet?action=fetchApiData">Open API 와이파이 정보 가져오기</a>
        <a href="bookmark-list.jsp">북마크 보기</a>
        <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    </div>
    <a href="bookmark-group-add.jsp">북마크 그룹 이름 추가</a>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>북마크 이름</th>
                <th>순서</th>
                <th>등록일자</th>
                <th>수정일자</th>
                <th>비고</th>
            </tr>
        </thead>
        <tbody>
        <%
        if (groups != null && !groups.isEmpty()) {
            for (BookMarkGroupDao group : groups) {
        %>
            <tr>
                <td><%= group.getId() %></td>
                <td><%= group.getGroupName() %></td>
                <td><%= group.getDisplayOrder() %></td>
                <td><%= group.getRegDate() %></td>
                <td><%= group.getModDate() != null ? group.getModDate() : "" %></td>
                <td>
                    <a href="bookmark-group-update.jsp?id=<%= group.getId() %>">수정</a>
                    <form id="deleteForm<%= group.getId() %>" action="bookmark-group.jsp" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="<%= group.getId() %>">
                        <a href="javascript:void(0);" onclick="confirmDelete(<%= group.getId() %>)">삭제</a>
                    </form>
                </td>
            </tr>
        <%
            }
        } else {
        %>
            <tr>
                <td colspan="6">정보가 존재하지 않습니다.</td>
            </tr>
        <%
        }
        %>
        </tbody>
    </table>
</body>
</html>
