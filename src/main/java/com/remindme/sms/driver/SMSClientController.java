package com.remindme.sms.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jboss.resteasy.util.Base64;



public class SMSClientController {
	
	public boolean sendMessage(){
		
		//Validate SMS
		
		//Send SMS
		processSMS();
		return true;
	}
	
	private boolean processSMS(){
		
		try {

			URL url = new URL("https://api.catapult.inetwork.com/v1/users/u-bkgwz6x7ltl3g4nxdgu3uzy/messages");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			String authStr = "t-vxqon7gpovoz3u4c26r52vq" + ":" + "wwlk5m5lrakwqkth4ii2cvcebsdgeizisayzpji";
	        String authEncoded = Base64.encodeBytes(authStr.getBytes());
		
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Basic " + authEncoded);

			String input = "{\n  \"from\": \"4703091343\",\n  \"to\": \"123456789\",\n  \"text\": \"Good morning, this is a test message\"\n  \n}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {

				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();

		}
		return true;
	}

	
}