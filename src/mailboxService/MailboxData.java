package mailboxService;


public class MailboxData {
	private String m_lcdText;
	private boolean m_isClosed;
	private long m_opens;
	
	public MailboxData(String lcdText, boolean isClosed, long opens){
		this.m_lcdText = lcdText;
		this.m_isClosed = isClosed;
		this.m_opens = opens;
	}
	public String getLCDText(){
		return this.m_lcdText;
	}
	
	public boolean getIsClosed(){
		return this.m_isClosed;
	}
	public long getOpens(){
		return this.m_opens;
	}
}
