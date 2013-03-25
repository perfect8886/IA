package cn.gucas.ia.matrix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Crawler4JWKS {
	private static final String WATCHED_LEFT = "¿´¹ý£¨";
	private static final String WATCHED_RIGHT = "£©";

	public void excute() {
		this.parse("http://kansha.baidu.com/user/15");
	}

	private void parse(String url) {
		String doc = this.getHtmlDocument(url);
		System.out.println(doc);

		int index1 = doc.indexOf(WATCHED_LEFT);
		String subDoc = doc.substring(index1);
		int index2 = subDoc.indexOf(WATCHED_RIGHT);
		String watchedNum = subDoc.substring(0, index2);
	}

	private String getHtmlDocument(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);

		try {
			HttpResponse response = httpClient.execute(httpGet);

			HttpEntity entity = response.getEntity();
			if (entity == null) {
				throw new ClientProtocolException(
						"Response contains no content");
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(
					entity.getContent(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			return buffer.toString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		Crawler4JWKS crawler = new Crawler4JWKS();
		crawler.excute();
	}
}
