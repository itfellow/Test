package com.omni.oesb.notification.TableInsert.vo;
/**
 * This class store value which are common in Outgoing msg table like N06,R41
 * and pass for inserting values in TransactionDtls Table
 *
 */
public class CommonTransactionDtlsVo {
	private String transId 	= null;
	private double amt 		= 0.0;
	private String account_no = null;
	private String benfAcnt = null;

	private String benfName = null;
	private String benfAcntTyp = null;
	private String sendingCustEmailMob = null;

	
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public double getAmt() {
		return amt;
	}
	public void setAmt(double amt) {
		this.amt = amt;
	}
	public String getAccount_no() {
		return account_no;
	}
	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}
	public String getBenfAcnt() {
		return benfAcnt;
	}
	public void setBenfAcnt(String benfAcnt) {
		this.benfAcnt = benfAcnt;
	}

	public String getBenfName() {
		return benfName;
	}
	public void setBenfName(String benfName) {
		this.benfName = benfName;
	}
	public String getBenfAcntTyp() {
		return benfAcntTyp;
	}
	public void setBenfAcntTyp(String benfAcntTyp) {
		this.benfAcntTyp = benfAcntTyp;
	}
	public String getSendingCustEmailMob() {
		return sendingCustEmailMob;
	}
	public void setSendingCustEmailMob(String sendingCustEmailMob) {
		this.sendingCustEmailMob = sendingCustEmailMob;
	}

	
	
}
