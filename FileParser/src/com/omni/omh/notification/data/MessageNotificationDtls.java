package com.omni.omh.notification.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This POJO class is mapped with MESSAGE_NOTIFICATION_DTLS Table in
 * database. <br>
 * POJO/Data Class is a Hibernate POJO Table. 
 */
@Entity
@Table(name="MESSAGE_NOTIFICATION_DTLS")
public class MessageNotificationDtls {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	@Column (name="NOTIFY_ID")
	private Long notify_id;
	
	@Column (name="TRANSACTION_REFNO")
	private String transaction_refno;
	
	@Column (name="ACK_NACK_SUBTYP")
	private String ack_nack_subtyp;
	
	@Column (name="EMAIL_NOTIFY_STATUS")
	private String email_notify_status;
	
	@Column (name="SMS_NOTIFY_STATUS")
	private String sms_notify_status;
	
	@Column (name="FLAG")
	private Integer flag;
	
	@Column (name="TRANSACTION_DATE")
	private Date transaction_date;
	
	public MessageNotificationDtls() {}
	
	public Long getNotify_id() {
		return notify_id;
	}
	public void setNotify_id(Long notify_id) {
		this.notify_id = notify_id;
	}
	public String getTransaction_refno() {
		return transaction_refno;
	}
	public void setTransaction_refno(String transaction_refno) {
		this.transaction_refno = transaction_refno;
	}
	public String getAck_nack_subtyp() {
		return ack_nack_subtyp;
	}
	public void setAck_nack_subtyp(String ack_nack_subtyp) {
		this.ack_nack_subtyp = ack_nack_subtyp;
	}
	public String getEmail_notify_status() {
		return email_notify_status;
	}
	public void setEmail_notify_status(String email_notify_status) {
		this.email_notify_status = email_notify_status;
	}
	public String getSms_notify_status() {
		return sms_notify_status;
	}
	public void setSms_notify_status(String sms_notify_status) {
		this.sms_notify_status = sms_notify_status;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Date getTransaction_date() {
		return transaction_date;
	}
	public void setTransaction_date(Date transaction_date) {
		this.transaction_date = transaction_date;
	}
	
	
	
	
	
	
}
