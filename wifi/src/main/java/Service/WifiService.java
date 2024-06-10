package Service;

import Connection.ApiConnection;
import Connection.DBConnection;
import dao.WifiDao;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/WifiInfoServlet")
public class WifiService extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("fetchApiData".equals(action)) {
            fetchApiData(request, response);
        }
    }

    private void fetchApiData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            JsonObject initialResponse = gson.fromJson(ApiConnection.getApiResponse(1, 1), JsonObject.class);
            JsonObject tbPublicWifiInfo = initialResponse.getAsJsonObject("TbPublicWifiInfo");
            int totalCount = tbPublicWifiInfo.get("list_total_count").getAsInt();

            for (int start = 1; start <= totalCount; start += 1000) {
                int end = Math.min(start + 999, totalCount);
                String jsonResponse = ApiConnection.getApiResponse(start, end);
                saveApiData(jsonResponse);
            }

            request.setAttribute("totalCount", totalCount);
            request.getRequestDispatcher("/wifiget.jsp").forward(request, response);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServletException("Error retrieving WiFi information", e);
        }
    }

    private void saveApiData(String jsonResponse) {
        JsonObject responseJson = gson.fromJson(jsonResponse, JsonObject.class);
        JsonObject tbPublicWifiInfo = responseJson.getAsJsonObject("TbPublicWifiInfo");
        JsonArray rows = tbPublicWifiInfo.getAsJsonArray("row");

        try (Connection connection = DBConnection.getConnection()) {
            for (int i = 0; i < rows.size(); i++) {
                JsonObject row = rows.get(i).getAsJsonObject();
                WifiDao wifiInfo = new WifiDao(
                        row.get("X_SWIFI_MGR_NO").getAsString(),
                        row.get("X_SWIFI_WRDOFC").getAsString(),
                        row.get("X_SWIFI_MAIN_NM").getAsString(),
                        row.get("X_SWIFI_ADRES1").getAsString(),
                        row.get("X_SWIFI_ADRES2").getAsString(),
                        row.get("X_SWIFI_INSTL_FLOOR").getAsString(),
                        row.get("X_SWIFI_INSTL_TY").getAsString(),
                        row.get("X_SWIFI_INSTL_MBY").getAsString(),
                        row.get("X_SWIFI_SVC_SE").getAsString(),
                        row.get("X_SWIFI_CMCWR").getAsString(),
                        row.get("X_SWIFI_CNSTC_YEAR").getAsString(),
                        row.get("X_SWIFI_INOUT_DOOR").getAsString(),
                        row.get("X_SWIFI_REMARS3").getAsString(),
                        row.get("LAT").getAsString(),
                        row.get("LNT").getAsString(),
                        row.get("WORK_DTTM").getAsString()
                );
                saveWifiInfo(wifiInfo, connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveWifiInfo(WifiDao wifiInfo, Connection connection) throws SQLException {
        String insertSQL = "INSERT INTO WifiInfo (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, "
                + "X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, "
                + "X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, wifiInfo.getX_SWIFI_MGR_NO());
            preparedStatement.setString(2, wifiInfo.getX_SWIFI_WRDOFC());
            preparedStatement.setString(3, wifiInfo.getX_SWIFI_MAIN_NM());
            preparedStatement.setString(4, wifiInfo.getX_SWIFI_ADRES1());
            preparedStatement.setString(5, wifiInfo.getX_SWIFI_ADRES2());
            preparedStatement.setString(6, wifiInfo.getX_SWIFI_INSTL_FLOOR());
            preparedStatement.setString(7, wifiInfo.getX_SWIFI_INSTL_TY());
            preparedStatement.setString(8, wifiInfo.getX_SWIFI_INSTL_MBY());
            preparedStatement.setString(9, wifiInfo.getX_SWIFI_SVC_SE());
            preparedStatement.setString(10, wifiInfo.getX_SWIFI_CMCWR());
            preparedStatement.setString(11, wifiInfo.getX_SWIFI_CNSTC_YEAR());
            preparedStatement.setString(12, wifiInfo.getX_SWIFI_INOUT_DOOR());
            preparedStatement.setString(13, wifiInfo.getX_SWIFI_REMARS3());
            preparedStatement.setString(14, wifiInfo.getLAT());
            preparedStatement.setString(15, wifiInfo.getLNT());
            preparedStatement.setString(16, wifiInfo.getWORK_DTTM());

            preparedStatement.executeUpdate();
        }
    }
}
