package com.omni.oesb.omh.notification.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name="TRANSACTION_DTLS")
public class TransactionDtls {

	@Id
	@Column (name="TRANSACTION_REFID")
	private String transaction_refId;
	
	@Column (name="REL_REF_ID")
	private String rel_RefId;
	
	@Column (name="CUSTOMER_ID")
	private Long customer_id;
	
	@Column (name="MESSAGE_SUBJECT")
	private String message_subject;
	
	@Column (name="ACCOUNT_NO")
	private String account_no;
	
	@Column (name="MOBILE_NO")
	private String mobile_no;
	
	@Column (name="EMAIL")
	private String email;
	
	@Column (name="AMT")
	private Double amt;
	
	@Column (name="MESSAGE_TYPE")
	private String message_type;
	
	@Column (name="BRANCH_ID")	
	private String branch_id;
	
	@Column (name="TRANSACTION_DATE")
	private Date transaction_date;
	
	@Column (name="IFSC_CODE")
	private String ifsc_code;
	
	@Column (name="BENEFICIARY_NAME")
	private String beneficiary_name;
	
	@Column (name="BENEFICIARY_ACCOUNT")
	private String beneficiary_account;
	
	@Column (name="BENEFICIARY_BRANCH_IFSC")
	private String beneficiary_branch_ifsc;
	
	@Column (name="BENEFICIARY_ACCOUNT_TYP")
	private String beneficiary_account_typ;
	
	@Column (name="TRANSACTION_TIME")
	private String transaction_time;
	
	@Column (name="BENEFICIARY_AVAIL_BAL")
	private String beneficiary_avail_bal;
	
	@Column (name="AVAIL_BAL")
	private String avail_bal;
	
	@Column (name="BENEFICIARY_BANK")
	private String beneficiary_bank;
	
	@Column (name="BRANCH_NAME")
	private String branch_name;
	
	@Column (name="TRANSACTION_STATUS")
	private String transaction_status;
	
	@Column (name="TRANS_COMP_DATE")
	private Date transCompleteDate;
	
	@Column (name="TRANS_COMP_TIME")
	private String transCompTime;
	
	@Column (name="IS_BLOCKED")
	private String is_blocked;

	@Column (name="IN_OUT_MSG_FLAG")
	private String in_out_msg_flag;

	public TransactionDtls() {}
	public String getTransaction_refId() {
		return transaction_refId;
	}

	public void setTransaction_refId(String transaction_refId) {
		this.transaction_refId = transaction_refId;
	}

	public String getRel_RefId() {
		return rel_RefId;
	}

	public void setRel_RefId(String rel_RefId) {
		this.rel_RefId = rel_RefId;
	}

	public Long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	public String getMessage_subject() {
		return message_subject;
	}

	public void setMessage_subject(String message_subject) {
		this.message_subject = message_subject;
	}

	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getAmt() {
		return amt;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
	}

	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public Date getTransaction_date() {
		return transaction_date;
	}

	public void setTransaction_date(Date transaction_date) {
		this.transaction_date = transaction_date;
	}

	public String getIfsc_code() {
		return ifsc_code;
	}

	public void setIfsc_code(String ifsc_code) {
		this.ifsc_code = ifsc_code;
	}

	public String getBeneficiary_name() {
		return beneficiary_name;
	}

	public void setBeneficiary_name(String beneficiary_name) {
		this.beneficiary_name = beneficiary_name;
	}

	public String getBeneficiary_account() {
		return beneficiary_account;
	}

	public void setBeneficiary_account(String beneficiary_account) {
		this.beneficiary_account = beneficiary_account;
	}

	public String getBeneficiary_branch_ifsc() {
		return beneficiary_branch_ifsc;
	}

	public void setBeneficiary_branch_ifsc(String beneficiary_branch_ifsc) {
		this.beneficiary_branch_ifsc = beneficiary_branch_ifsc;
	}

	public String getBeneficiary_account_typ() {
		return beneficiary_account_typ;
	}

	public void setBeneficiary_account_typ(String beneficiary_account_typ) {
		this.beneficiary_account_typ = beneficiary_account_typ;
	}

	public String getTransaction_time() {
		return transaction_time;
	}

	public void setTransaction_time(String transaction_time) {
		this.transaction_time = transaction_time;
	}

	public String getBeneficiary_avail_bal() {
		return beneficiary_avail_bal;
	}

	public void setBeneficiary_avail_bal(String beneficiary_avail_bal) {
		this.beneficiary_avail_bal = beneficiary_avail_bal;
	}

	public String getAvail_bal() {
		return avail_bal;
	}

	public void setAvail_bal(String avail_bal) {
		this.avail_bal = avail_bal;
	}

	public String getBeneficiary_bank() {
		return beneficiary_bank;
	}

	public void setBeneficiary_bank(String beneficiary_bank) {
		this.beneficiary_bank = beneficiary_bank;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getTransaction_status() {
		return transaction_status;
	}

	public void setTransaction_status(String transaction_status) {
		this.transaction_status = transaction_status;
	}

	public Date getTransCompleteDate() {
		return transCompleteDate;
	}

	public void setTransCompleteDate(Date transCompleteDate) {
		this.transCompleteDate = transCompleteDate;
	}

	public String getTransCompTime() {
		return transCompTime;
	}

	public void setTransCompTime(String transCompTime) {
		this.transCompTime = transCompTime;
	}

	public String getIs_blocked() {
		return is_blocked;
	}

	public void setIs_blocked(String is_blocked) {
		this.is_blocked = is_blocked;
	}

	public String getIn_out_msg_flag() {
		return in_out_msg_flag;
	}

	public void setIn_out_msg_flag(String in_out_msg_flag) {
		this.in_out_msg_flag = in_out_msg_flag;
	}
	
	
	
}
