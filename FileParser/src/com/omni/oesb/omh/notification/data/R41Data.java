package com.omni.oesb.omh.notification.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "R41_DATA")
public class R41Data {
	
	@Id
	@Column(name = "TRANSACTION_REFNO")
	private String transaction_refno;
	
	@Column(name = "HEADER_ID")
	private Long header_id;					//suppose to be fk to message header table
	
	@Column(name = "VALUE_DATE")
	private Date value_date;
	
	@Column(name = "ORDER_CUSTOMER_ACCOUNT_NO")
	private String order_customer_account_no;

	@Column(name = "ORDER_CUSTOMER_NAME")
	private String order_customer_name;
	
	@Column(name = "ORDER_CUSTOMER_ADDRESS")
	private String order_customer_address;
	
	@Column(name = "BENEFICIARY_ACCOUNT_NO")
	private String beneficiary_account_no;
	
	@Column(name = "BENEFICIARY_CUSTOMER_NAME")
	private String beneficiary_customer_name;
	
	@Column(name = "BENEFICIARY_CUSTOMER_ADDRESS")
	private String beneficiary_customer_address;
	
	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "AMT")
	private Double amt;
	
	@Column(name = "SENDER_CORRESPONDENT")
	private String sender_correspondent;
	
	@Column(name = "RECEIVER_CORRESPONDENT")
	private String receiver_correspondent;
	
	@Column(name = "INTERMEDIARY")
	private String intermediary;
	
	@Column(name = "ACCOUT_WITH_INSTITUTION")
	private String accout_with_institution;
	
	@Column(name = "PAYMENT_DTLS")
	private String payment_dtls;
	
	@Column(name = "CHARGES_DTLS")
	private String charges_dtls;
	
	@Column(name = "SENDER_TO_RECEIVER_INFO")
	private String sender_to_receiver_info;
	
	@Column(name = "ORDERING_INSTITUTION")
	private String ordering_institution;
	
	@Column(name = "EXPORT_STATUS")
	private String export_status;

	public R41Data() {}
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

	public Date getValue_date() {
		return value_date;
	}

	public void setValue_date(Date value_date) {
		this.value_date = value_date;
	}

	public String getOrder_customer_name() {
		return order_customer_name;
	}

	
	public String getOrder_customer_account_no() {
		return order_customer_account_no;
	}
	
	public void setOrder_customer_account_no(String order_customer_account_no) {
		this.order_customer_account_no = order_customer_account_no;
	}
	
	public void setOrder_customer_name(String order_customer_name) {
		this.order_customer_name = order_customer_name;
	}

	public String getOrder_customer_address() {
		return order_customer_address;
	}

	public void setOrder_customer_address(String order_customer_address) {
		this.order_customer_address = order_customer_address;
	}

	public String getBeneficiary_account_no() {
		return beneficiary_account_no;
	}

	public void setBeneficiary_account_no(String beneficiary_account_no) {
		this.beneficiary_account_no = beneficiary_account_no;
	}

	public String getBeneficiary_customer_name() {
		return beneficiary_customer_name;
	}

	public void setBeneficiary_customer_name(String beneficiary_customer_name) {
		this.beneficiary_customer_name = beneficiary_customer_name;
	}

	public String getBeneficiary_customer_address() {
		return beneficiary_customer_address;
	}

	public void setBeneficiary_customer_address(String beneficiary_customer_address) {
		this.beneficiary_customer_address = beneficiary_customer_address;
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

	public String getPayment_dtls() {
		return payment_dtls;
	}

	public void setPayment_dtls(String payment_dtls) {
		this.payment_dtls = payment_dtls;
	}

	public String getCharges_dtls() {
		return charges_dtls;
	}

	public void setCharges_dtls(String charges_dtls) {
		this.charges_dtls = charges_dtls;
	}

	public String getSender_to_receiver_info() {
		return sender_to_receiver_info;
	}

	public void setSender_to_receiver_info(String sender_to_receiver_info) {
		this.sender_to_receiver_info = sender_to_receiver_info;
	}

	public String getOrdering_institution() {
		return ordering_institution;
	}

	public void setOrdering_institution(String ordering_institution) {
		this.ordering_institution = ordering_institution;
	}

	public String getExport_status() {
		return export_status;
	}

	public void setExport_status(String export_status) {
		this.export_status = export_status;
	}
	
	
	
	
	
}
