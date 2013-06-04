package com.omni.oesb.omh.notification.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "R09_Data")
public class R09Data {

	@Id
	@Column(name = "TRANSACTION_REFNO")
	private String transaction_refno;
	
	@Column(name = "STTLINDCT")
	private String settled_indicator;
	
	@Column(name = "REASON_CD")
	private String reason_code;
	
	@Column(name = "STTLTME")
	private String settelment_time;
	
	public R09Data() {}

	public String getTransaction_refno() {
		return transaction_refno;
	}

	public void setTransaction_refno(String transaction_refno) {
		this.transaction_refno = transaction_refno;
	}

	public String getSettled_indicator() {
		return settled_indicator;
	}

	public void setSettled_indicator(String settled_indicator) {
		this.settled_indicator = settled_indicator;
	}

	public String getReason_code() {
		return reason_code;
	}

	public void setReason_code(String reason_code) {
		this.reason_code = reason_code;
	}

	public String getSettelment_time() {
		return settelment_time;
	}

	public void setSettelment_time(String settelment_time) {
		this.settelment_time = settelment_time;
	}
	
	
}
