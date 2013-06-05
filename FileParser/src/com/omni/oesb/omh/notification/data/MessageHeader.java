package com.omni.oesb.omh.notification.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MESSAGE_HEADER")
public class MessageHeader {

	@Id
	@Column(name = "HEADER_ID")
	private Long header_id;
	
	@Column(name = "BANK_APP_IDENTIFIER")
	private String bank_app_identifier;
	
	@Column(name = "MESSAGE_IDENTIFIER")
	private String message_identifier;
	
	@Column(name = "IOIDENTIFIER")
	private String IOIdentifier;
	
	@Column(name = "MESSAGE_TYPE")
	private String message_type;
	
	@Column(name = "MESSAGE_SUB_TYPE")
	private String message_sub_type;
	
	@Column(name = "SENDER_ADDRESS")
	private String sender_address;
	
	@Column(name = "RECEIVER_ADDRESS")
	private String receiver_address;
	
	@Column(name = "DELIVERY_FLAG")
	private Integer delivery_flag;
	
	@Column(name = "OPEN_NOTIFICATION_FLAG")
	private Integer open_notification_flag;
	
	@Column(name = "NON_DELIVERY_WARNING_FLAG")
	private Integer non_delivery_warning_flag;
	
	@Column(name = "OBSOLESCENCE_PERIOD")
	private Integer obsolescence_period;
	
	@Column(name = "MUR")
	private String MUR;
	
	@Column(name = "POSSIBLE_DUPLICATE_FLAG")
	private Integer possible_duplicate_flag;
	
	@Column(name = "SERVICE_IDENTIFIER")
	private String service_identifier;
	
	@Column(name = "ORIGINATING_DATE")
	private Date originating_date;
	
	@Column(name = "ORGINATING_TIME")
	private String orginating_time;
	
	@Column(name = "TESTING_TRAINING_FLAG")
	private Integer testing_training_flag;
	
	@Column(name = "SEQUENCE_NO")
	private String sequence_no;
	
	@Column(name = "FILLER")
	private String filler;
	
	@Column(name = "UNIQUE_TRANSACTION_REFERENCE")
	private String unique_transaction_reference;
	
	@Column(name = "RTGS_PRIORITY")
	private Integer RTGS_priority;
	
	@Column(name = "TRANSACTION_REFNO")
	private String transaction_refno;

	public MessageHeader() {}
	
	public Long getHeader_id() {
		return header_id;
	}

	public void setHeader_id(Long header_id) {
		this.header_id = header_id;
	}

	public String getBank_app_identifier() {
		return bank_app_identifier;
	}

	public void setBank_app_identifier(String bank_app_identifier) {
		this.bank_app_identifier = bank_app_identifier;
	}

	public String getMessage_identifier() {
		return message_identifier;
	}

	public void setMessage_identifier(String message_identifier) {
		this.message_identifier = message_identifier;
	}

	public String getIOIdentifier() {
		return IOIdentifier;
	}

	public void setIOIdentifier(String iOIdentifier) {
		IOIdentifier = iOIdentifier;
	}

	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public String getMessage_sub_type() {
		return message_sub_type;
	}

	public void setMessage_sub_type(String message_sub_type) {
		this.message_sub_type = message_sub_type;
	}

	public String getSender_address() {
		return sender_address;
	}

	public void setSender_address(String sender_address) {
		this.sender_address = sender_address;
	}

	public String getReceiver_address() {
		return receiver_address;
	}

	public void setReceiver_address(String receiver_address) {
		this.receiver_address = receiver_address;
	}

	public Integer getDelivery_flag() {
		return delivery_flag;
	}

	public void setDelivery_flag(Integer delivery_flag) {
		this.delivery_flag = delivery_flag;
	}

	public Integer getOpen_notification_flag() {
		return open_notification_flag;
	}

	public void setOpen_notification_flag(Integer open_notification_flag) {
		this.open_notification_flag = open_notification_flag;
	}

	public Integer getNon_delivery_warning_flag() {
		return non_delivery_warning_flag;
	}

	public void setNon_delivery_warning_flag(Integer non_delivery_warning_flag) {
		this.non_delivery_warning_flag = non_delivery_warning_flag;
	}

	public Integer getObsolescence_period() {
		return obsolescence_period;
	}

	public void setObsolescence_period(Integer obsolescence_period) {
		this.obsolescence_period = obsolescence_period;
	}

	public String getMUR() {
		return MUR;
	}

	public void setMUR(String mUR) {
		MUR = mUR;
	}

	public Integer getPossible_duplicate_flag() {
		return possible_duplicate_flag;
	}

	public void setPossible_duplicate_flag(Integer possible_duplicate_flag) {
		this.possible_duplicate_flag = possible_duplicate_flag;
	}

	public String getService_identifier() {
		return service_identifier;
	}

	public void setService_identifier(String service_identifier) {
		this.service_identifier = service_identifier;
	}

	public Date getOriginating_date() {
		return originating_date;
	}

	public void setOriginating_date(Date originating_date) {
		this.originating_date = originating_date;
	}

	public String getOrginating_time() {
		return orginating_time;
	}

	public void setOrginating_time(String orginating_time) {
		this.orginating_time = orginating_time;
	}

	public Integer getTesting_training_flag() {
		return testing_training_flag;
	}

	public void setTesting_training_flag(Integer testing_training_flag) {
		this.testing_training_flag = testing_training_flag;
	}

	public String getSequence_no() {
		return sequence_no;
	}

	public void setSequence_no(String sequence_no) {
		this.sequence_no = sequence_no;
	}

	public String getFiller() {
		return filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public String getUnique_transaction_reference() {
		return unique_transaction_reference;
	}

	public void setUnique_transaction_reference(String unique_transaction_reference) {
		this.unique_transaction_reference = unique_transaction_reference;
	}

	public Integer getRTGS_priority() {
		return RTGS_priority;
	}

	public void setRTGS_priority(Integer rTGS_priority) {
		RTGS_priority = rTGS_priority;
	}

	public String getTransaction_refno() {
		return transaction_refno;
	}

	public void setTransaction_refno(String transaction_refno) {
		this.transaction_refno = transaction_refno;
	}
	
	

	
}
