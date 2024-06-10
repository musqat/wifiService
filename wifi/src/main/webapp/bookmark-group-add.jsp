<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 그룹 추가</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>북마크 그룹 추가</h1>
    <div class="header">
        <a href="index.jsp">홈</a>
        <a href="location_history.jsp">위치 히스토리 목록</a>
        <a href="WifiInfoServlet?action=fetchApiData">Open API 와이파이 정보 가져오기</a>
        <a href="bookmark-list.jsp">북마크 보기</a>
        <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    </div>

    <form action="bookmark-group.jsp" method="get">
        <input type="hidden" name="action" value="add">
        <table>
            <tr>
                <th><label for="group_name">북마크 이름:</label></th>
                <td><input type="text" id="group_name" name="group_name" required></td>
            </tr>
            <tr>
                <th><label for="display_order">순서:</label></th>
                <td><input type="number" id="display_order" name="display_order" required></td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center;">
                    <button type="submit">추가</button>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
