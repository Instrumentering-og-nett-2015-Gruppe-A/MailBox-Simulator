package mailboxService;

import java.util.Date;

public class MailboxData {
	private String m_lcdText;
	private Date m_beginSleepAt;
	private Date m_wakeUpAt;
	
	public MailboxData(String lcdText, Date beginSleep, Date endSleep){
		this.m_beginSleepAt = beginSleep;
		this.m_wakeUpAt = endSleep;
		this.m_lcdText = lcdText;
		
	}
	public String getLCDText(){
		return this.m_lcdText;
	}
	public Date getBeginSleepAt(){
		return this.m_beginSleepAt;
	}
	public Date getEndSleepAt(){
		return this.m_wakeUpAt;
	}
}
