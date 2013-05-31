package com.omni.omh.notification.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "R90_DATA")
public class R90Data {

	@Id
	@Column(name = "TRANSACTION_REFNO")
	private String transaction_refno;
	
	@Column(name = "HEADER_ID")
	private Long header_id; 				//suppose to be fk to message header table
	
	@Column(name = "ACK_INDICATOR")
	private String ack_indicator;
	
	@Column(name = "REASON_CODE")
	private String reason_code;
	
	@Column(name = "EXPORT_STATUS")
	private String export_status;

	public R90Data() {}
	public String getTransaction_refno() {
		return transaction_refno;
	}

	public void setTransaction_refno(String transaction_refno) {
		this.transaction_refno = transaction_refno;
	}

	public Long getHeader_id() {
		return header_id;
	}

	public void setHeader_id(Long header_id) {
		this.header_id = header_id;
	}

	public String getAck_indicator() {
		return ack_indicator;
	}

	public void setAck_indicator(String ack_indicator) {
		this.ack_indicator = ack_indicator;
	}

	public String getReason_code() {
		return reason_code;
	}

	public void setReason_code(String reason_code) {
		this.reason_code = reason_code;
	}

	public String getExport_status() {
		return export_status;
	}

	public void setExport_status(String export_status) {
		this.export_status = export_status;
	}
	
	
}
