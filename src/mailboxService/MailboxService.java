package mailboxService;

import java.util.ArrayList;

public interface MailboxService {
	public void registerRFID(String rfid, int mailboxID);
	public ArrayList<String> getRFIDForMailbox(int mailboxID);
	public int registerMailbox();
	public void updateMailboxStatus(boolean hasMail, int mailboxID);
	public String getLCDText(int mailboxID);
	public void setServerIP(String serverIPstr);
}

