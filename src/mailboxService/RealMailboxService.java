package mailboxService;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

import org.json.*;


public class RealMailboxService implements MailboxService {

	DatagramSocket socket;
	private String ServerIP;
	private int Port;
	private int mailboxID;
	HttpURLConnection connection = null;
	
	@Override
	public void registerRFID(String rfid, int mailboxID) {
		// TODO 
		//  rfid
		// lag json
		// send new rfid to server
	}

	@Override
	public ArrayList<String> getRFIDForMailbox(int mailboxID) {
		// TODO
		// Make http request
		// Parse json to arraylist
		
		
		
		
		return null;
	}

	@Override
	public MailboxData getMailboxData(int mailboxID) {
		// TODO 
		//Make http request
		//Parse json to mailboxdata
		return null;
	}

	@Override
	public int registerMailbox() {
		// TODO
		try{
			
			System.out.println("Registering mailbox");
			socket = new DatagramSocket(53005, InetAddress.getByName("0.0.0.0"));
			byte[] recvBuf = new byte[15000];
			DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
			socket.receive(packet);
			
			ServerIP = packet.getAddress().getHostAddress();
			Port = packet.getPort();
			
			System.out.println("Accepted broadcast!");
			System.out.println("Broadcast IP = " + packet.getAddress().getHostAddress());
			System.out.println("Broadcast Port = " + packet.getPort());
			
			
			URL url;
			url = new URL(ServerIP+"/mailbox");
			
			
			connection = (HttpURLConnection)url.openConnection();
			//URLConnection connection = new URL(ServerIP).openConnection();
			System.out.println("URLconnection works");
			connection.setRequestProperty("Accept-Charset", java.nio.charset.StandardCharsets.UTF_8.name());
			InputStream response = connection.getInputStream();
			
			
			
			StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String responseString = sb.toString();
            
            System.out.println(responseString);
            
            JSONObject jobj = new JSONObject();
            String mailboxIDString = jobj.getJSONObject(responseString).getString("id");
            
            mailboxID = Integer.parseInt(mailboxIDString);
		}
		catch (IOException ex){
			System.out.println("No url connection!");
		} catch (JSONException e) {
			System.out.println("Cannot get mailboxIP.");
			e.printStackTrace();
		}
		
		
		// accept broadcast
		// save IP to "serverAddress"
		return mailboxID;
	}

	@Override
	public void updateMailboxStatus(boolean hasMail) {
		// TODO 
		// has post
		// opprett json av has_post
		// send json til server
		
	}

}
