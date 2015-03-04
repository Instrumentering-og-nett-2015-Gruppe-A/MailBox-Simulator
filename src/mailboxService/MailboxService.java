package mailboxService;

import java.util.ArrayList;

public interface MailboxService {
	public void registerRFID(String rfid, int mailboxID);
	public ArrayList<String> getRFIDForMailbox(int mailboxID);
	public int registerMailbox();
	public void updateMailboxStatus(boolean hasMail, int mailboxID);
}

