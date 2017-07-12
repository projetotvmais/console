package br.com.douglasfernandes.console;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

public class Testes {

	public static void main(String[] args) {
		try{
			String USER_AGENT = "Mozilla/5.0";
			String url = "http://rafildshdtv.com/live/5801255b39c38e1109ddfabd.m3u8";

			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);

			post.setHeader("Host", "rafildshdtv.com");
			post.setHeader("User-Agent", USER_AGENT);
			post.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			post.setHeader("Accept-Language", "en-US,en;q=0.5");
			post.setHeader("Connection", "keep-alive");
			post.setHeader("Referer", url);
			post.setHeader("Content-Type", "application/x-www-form-urlencoded");
			
			HttpResponse response = client.execute(post);

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
