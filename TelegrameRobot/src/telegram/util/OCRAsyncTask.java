package telegram.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

/**
 * Created by suhasbachewar on 10/5/16.
 */
public class OCRAsyncTask {

	public static void main(String[] args) throws Exception {
		sendPost(true,
				"https://api.telegram.org/file/bot889507584:AAHsoTN22rdIznIVre6MZI05cPT47AuoZEs/photos/file_16.jpg",
				"eng");
	}

	private static final String apiUrl = "https://api.ocr.space/parse/image"; // OCR API Endpoints
	private static final String apiKey = "20d778984188957"; // API KEY

	public static String sendPost(boolean isOverlayRequired, String imageUrl, String language) throws Exception {

		System.out.println("開始辨別圖片");
		URL obj = new URL(apiUrl); // OCR API Endpoints
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		JSONObject postDataParams = new JSONObject();

		postDataParams.put("apikey", apiKey);// TODO Add your Registered API key
		postDataParams.put("isOverlayRequired", isOverlayRequired);
		postDataParams.put("url", imageUrl);
		postDataParams.put("language", language);
		postDataParams.put("filetype", "JPG");
		postDataParams.put("isSearchablePdfHideTextLayer", true);
		postDataParams.put("scale", true);

		postDataParams.put("isTable", false);

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(getPostDataString(postDataParams));
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// return result
		return String.valueOf(response);
	}

	public static String getPostDataString(JSONObject params) throws Exception {

		StringBuilder result = new StringBuilder();
		boolean first = true;

		Iterator<String> itr = params.keys();

		while (itr.hasNext()) {

			String key = itr.next();
			Object value = params.get(key);

			if (first)
				first = false;
			else
				result.append("&");

			result.append(URLEncoder.encode(key, "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(value.toString(), "UTF-8"));

		}
		return result.toString();
	}
}
