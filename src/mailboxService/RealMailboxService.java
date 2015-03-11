package mailboxService;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

import org.json.*;

public class RealMailboxService implements MailboxService {

	DatagramSocket socket;
	private String ServerIP;
	private String protocol = "http://";
	
	@Override
	public void registerRFID(String rfid, int mailboxID) {
		String host = ":5000";
		String method = "PUT";
		String body = "{\"rfid\":\""+rfid+"\"}";	
		getJSON(protocol+ServerIP+host+"/api/mailbox/"+mailboxID, method, body);
	}

	@Override
	public ArrayList<String> getRFIDForMailbox(int mailboxID) {
		
		String host = ":5000";
		String method = "GET";
		String url = protocol+ServerIP+host+"/api/mailbox/"+mailboxID;
		JSONObject jobj = getJSON(url, method);
		
		ArrayList<String> m_rfids = new ArrayList<String>();
		JSONArray rfids = null;
		try {
			rfids = jobj.getJSONArray("keys");
			for(int i = 0; i < rfids.length(); i++ ){
				m_rfids.add(rfids.getJSONObject(i).getString("rfid"));
			}
		} catch (JSONException e) {
			System.out.println("Cannot get updated rfidkeys. Using old keys.");
			e.printStackTrace();
		}
		return m_rfids;
	}

	public void setServerIP(String serverIPstr){
		ServerIP = serverIPstr;
	}

	@Override
	public int registerMailbox() {
	
		int mailboxId = -1;
		String host = ":5000/api/mailbox";
		
		try{	
			System.out.println("Registering mailbox");
			socket = new DatagramSocket(53005, InetAddress.getByName("0.0.0.0"));
			byte[] recvBuf = new byte[15000];
			DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
			socket.receive(packet);
			
			ServerIP = packet.getAddress().getHostAddress();
			
			System.out.println("Accepted broadcast!");
			System.out.println("Broadcast IP = " + ServerIP);
			
			String url = protocol+ServerIP+host;
			String method = "POST"; 
			
			JSONObject jobj = getJSON(url, method);
            String mailboxIdString = jobj.getString("id");
            
            mailboxId = Integer.parseInt(mailboxIdString);
            System.out.println("Got new mailboxId. It is:"+mailboxId);
		}
		catch (IOException ex){
			System.out.println("No url connection!");
		} catch (JSONException e) {
			System.out.println("Cannot get mailboxIP.");
			e.printStackTrace();
		}
		if (mailboxId == -1){
			throw new RuntimeException("failed to get new id");
		}
		return mailboxId;
	}

	@Override
	public String getLCDText(int mailboxID){
		String host = ":5000";
		String method = "GET";
		String url = protocol+ServerIP+host+"/api/mailbox/"+mailboxID;
		
		System.out.println("Url for å hente lcdtekst"+url);
		JSONObject jobj = getJSON(url, method);
		String lcdText = ""; 
		
		try{
			lcdText = jobj.getString("display_text");
		} catch (JSONException e) {
			System.out.println("Cannot get new lcdText.");
			e.printStackTrace();
		}
		return lcdText;
	}
	
	@Override
	public void updateMailboxStatus(boolean hasMail, int mailboxID) {
		
		String host = ":5000";
		String method = "PUT";
		String body = "{\"has_mail\":"+hasMail+"}";	
		System.out.println(body);
		getJSON(protocol+ServerIP+host+"/api/mailbox/"+mailboxID, method, body);	
	}
	
	public JSONObject getJSON(String url, String method){
		return getJSON(url, method, null);
	}
	
	public JSONObject getJSON(String url, String method, String body){
		
		JSONObject jobj = null;
		try{
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod(method);
			connection.setRequestProperty("Accept-Charset", java.nio.charset.StandardCharsets.UTF_8.name());
			System.out.println("Made connection and set to "+method);
			
			if (body !=null){
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-type", "application/json");
				connection.setRequestProperty("Accept", "application/json");
				OutputStream output = connection.getOutputStream();
				byte[] data = body.getBytes("UTF-8");
				output.write(data);
			}	
			InputStream response = connection.getInputStream();
			StringBuilder sb = new StringBuilder();
	        String line;
	        BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	        }
	        String responseString = sb.toString();
	        System.out.println("Makes a string from response:" +responseString);
	        
	        jobj = new JSONObject(responseString);
		}
        catch (IOException ex){
			System.out.println("No url connection!");
		} catch (JSONException e) {
			System.out.println("Cannot get mailboxIP.");
			e.printStackTrace();
		}
		return jobj;
	}
}
