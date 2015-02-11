package mailboxService;

import java.util.ArrayList;
import java.util.HashMap;

public class FakeMailboxService implements MailboxService{

	private ArrayList<Integer> m_mailboxes;
	private HashMap<Integer, ArrayList<String>> m_rfids;
	private HashMap<Integer, MailboxData> m_mailboxDatas;
	private int m_currentId;
	public FakeMailboxService(){
		m_mailboxes = new ArrayList<Integer>();
		m_rfids = new HashMap<Integer, ArrayList<String>>();
		m_mailboxDatas = new HashMap<Integer, MailboxData>();
		m_currentId = 0;
	}
	@Override
	public void registerRFID(String rfid, int mailboxID) {
		ArrayList<String> mailboxRFIDs = m_rfids.get(mailboxID);
		
		mailboxRFIDs.add(rfid);
		
	}

	@Override
	public ArrayList<String> getRFIDForMailbox(int mailboxID) {
		ArrayList<String> mailboxRFIDs = m_rfids.get(mailboxID);
		
		return mailboxRFIDs;
	}

	@Override
	public MailboxData getMailboxData(int mailboxID) {
		return m_mailboxDatas.get(mailboxID);
	}

	@Override
	public int registerMailbox() {
		// TODO Auto-generated method stub
		m_rfids.put(m_currentId, new ArrayList<String>());
		m_mailboxDatas.put(m_currentId, new MailboxData("Statustext", true, 0));
		m_mailboxes.add(m_currentId);
		return m_currentId++;
	}

	@Override
	public void updateMailboxStatus(boolean hasMail) {
		
		
	}

}
