package Connection;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ApiConnection {
	private static final String API_URL = "http://openapi.seoul.go.kr:8088/6e4f4344566d757335365041694461/json/TbPublicWifiInfo/";
	private static final OkHttpClient okHttp = new OkHttpClient();

	public static String getApiResponse(int start, int end) throws IOException {
		String url = API_URL + start + "/" + end;
		Request request = new Request.Builder().url(url).build();

		try (Response response = okHttp.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				throw new IOException("Unexpected code " + response);
			}

			ResponseBody responseBody = response.body();
			if (responseBody != null) {
				return responseBody.string();
			} else {
				throw new IOException("Response body is null");
			}
		}
	}
}
