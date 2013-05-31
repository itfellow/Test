package com.omni.omh.notification.data;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "R42_DATA")
public class R42Data{
	
	@Id
	@Column(name = "TRANSACTION_REFNO")
	private String transaction_refno;
	
	
	@Column(name = "HEADER_ID")
	private Long  header_id;				//suppose to be fk to message header table
	
	
	@Column(name = "RELATED_REF")
	private String related_ref;
	
	@Column(name = "VALUE_DATE")
	private Date Value_date;
	
	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "AMT")
	private Double amt;
	
	@Column(name = "ORDER_INSTITUTION")
	private String order_institution;
	
	@Column(name = "SENDER_CORRESPONDENT")
	private String sender_correspondent;
	
	@Column(name = "RECEIVER_CORRESPONDENT")
	private String receiver_correspondent;
	
	@Column(name = "INTERMEDIARY")
	private String intermediary;
	
	@Column(name = "ACCOUT_WITH_INSTITUTION")
	private String accout_with_institution;
	
	@Column(name = "BENEFICIARY_INSTITUTION")
	private String beneficiary_institution;
	
	@Column(name = "SENDER_TO_RECEIVER_INFO")
	private String sender_to_receiver_info;
	
	@Column(name = "export_status")
	private String export_status;

	public R42Data() {}
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

	public String getRelated_ref() {
		return related_ref;
	}

	public void setRelated_ref(String related_ref) {
		this.related_ref = related_ref;
	}

	public Date getValue_date() {
		return Value_date;
	}

	public void setValue_date(Date value_date) {
		Value_date = value_date;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAmt() {
		return amt;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
	}

	public String getOrder_institution() {
		return order_institution;
	}

	public void setOrder_institution(String order_institution) {
		this.order_institution = order_institution;
	}

	public String getSender_correspondent() {
		return sender_correspondent;
	}

	public void setSender_correspondent(String sender_correspondent) {
		this.sender_correspondent = sender_correspondent;
	}

	public String getReceiver_correspondent() {
		return receiver_correspondent;
	}

	public void setReceiver_correspondent(String receiver_correspondent) {
		this.receiver_correspondent = receiver_correspondent;
	}

	public String getIntermediary() {
		return intermediary;
	}

	public void setIntermediary(String intermediary) {
		this.intermediary = intermediary;
	}

	public String getAccout_with_institution() {
		return accout_with_institution;
	}

	public void setAccout_with_institution(String accout_with_institution) {
		this.accout_with_institution = accout_with_institution;
	}

	public String getBeneficiary_institution() {
		return beneficiary_institution;
	}

	public void setBeneficiary_institution(String beneficiary_institution) {
		this.beneficiary_institution = beneficiary_institution;
	}

	public String getSender_to_receiver_info() {
		return sender_to_receiver_info;
	}

	public void setSender_to_receiver_info(String sender_to_receiver_info) {
		this.sender_to_receiver_info = sender_to_receiver_info;
	}

	public String getExport_status() {
		return export_status;
	}

	public void setExport_status(String export_status) {
		this.export_status = export_status;
	}
	

	
	
	
	
}
