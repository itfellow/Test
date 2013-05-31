package com.omni.omh.notification.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "N09_DATA")
public class N09Data  {


//	2020
	@Id
	@Column(name = "TRANSACTION_REFID")
	private String transaction_refId;
	
//	3535
	@Column(name = "BATCH_TIME")
	private String batch_time;
	
//	5185
	@Column(name = "TOTAL_LOOPS")
	private String ttl_Trans_No;
	
//  4115
	@Column(name = "SUMOFAMT_CURRENT")
	private String ttl_Amt;
	
//	2020
	@Column(name = "TRANSACTION_LOOP_REFNO")
	private String transaction_Loop_No;
	
//	5756
	@Column(name = "SEND_BRANCH_IFSC")
	private String sender_Branch_Ifsc;
	
//	2006
	@Column(name = "RELATED_REF")
	private String related_ref;
	
//	5569
	@Column(name = "BENEFICIARY_BRANCH_IFSC")
	private String beneficiary_Branch_Ifsc;
	
//	6310
	@Column(name = "BENEFICIARY_CUSTOMER_ACCOUNT_TYPE")
	private String beneficiary_customer_account_type;
	
//	6061
	@Column(name = "BENEFICIARY_CUSTOMER_ACCOUNT_NO")
	private String beneficiary_customer_account_no;
	
//	6081
	@Column(name = "BENEFICIARY_CUSTOEMER_ACCOUNT_NAME")
	private String beneficiary_customer_account_name;
	

	//	5565
	@Column(name = "BENEFICIARY_CUSTOEMER_ADDRESS")
	private String beneficiary_Cust_Address;
	
//	4038
	@Column(name = "AMT")
	private Double amt;
	
//	3380
	@Column(name = "VALUE_DATE")
	private Date value_Date;
	
//	3375
	@Column(name = "REMMITANCE_DATE")
	private String remittance_date;
	
//	3381
	@Column(name = "ORIGINAL_VALUE_DATE")
	private Date original_Value_Date;
	
//	6346
	@Column(name = "REASON_CODE")
	private String reason_Code;
	
//	6366
	@Column(name = "REJECTION_REASON")
	private String rejection_Reason;
	
//	6366
	@Column(name = "SENDER_REC_INFO")
	private String sender_To_Rec_Info;
	
	@Column(name = "EXPORT_STATUS")
	private String export_status;
	
	@Column(name = "HEADER_ID")
	private Long header_id; // suppose to be foreign key

	public N09Data() {}
	public String getTransaction_refId() {
		return transaction_refId;
	}

	public void setTransaction_refId(String transaction_refId) {
		this.transaction_refId = transaction_refId;
	}

	public String getBatch_time() {
		return batch_time;
	}

	public void setBatch_time(String batch_time) {
		this.batch_time = batch_time;
	}

	public String getTtl_Trans_No() {
		return ttl_Trans_No;
	}

	public void setTtl_Trans_No(String ttl_Trans_No) {
		this.ttl_Trans_No = ttl_Trans_No;
	}

	public String getTtl_Amt() {
		return ttl_Amt;
	}

	public void setTtl_Amt(String ttl_Amt) {
		this.ttl_Amt = ttl_Amt;
	}

	public String getTransaction_Loop_No() {
		return transaction_Loop_No;
	}

	public void setTransaction_Loop_No(String transaction_Loop_No) {
		this.transaction_Loop_No = transaction_Loop_No;
	}

	public String getSender_Branch_Ifsc() {
		return sender_Branch_Ifsc;
	}

	public void setSender_Branch_Ifsc(String sender_Branch_Ifsc) {
		this.sender_Branch_Ifsc = sender_Branch_Ifsc;
	}

	public String getRelated_ref() {
		return related_ref;
	}

	public void setRelated_ref(String related_ref) {
		this.related_ref = related_ref;
	}

	public String getBeneficiary_Branch_Ifsc() {
		return beneficiary_Branch_Ifsc;
	}

	public void setBeneficiary_Branch_Ifsc(String beneficiary_Branch_Ifsc) {
		this.beneficiary_Branch_Ifsc = beneficiary_Branch_Ifsc;
	}

	public String getBeneficiary_customer_account_type() {
		return beneficiary_customer_account_type;
	}

	public void setBeneficiary_customer_account_type(
			String beneficiary_customer_account_type) {
		this.beneficiary_customer_account_type = beneficiary_customer_account_type;
	}

	public String getBeneficiary_customer_account_no() {
		return beneficiary_customer_account_no;
	}

	public void setBeneficiary_customer_account_no(
			String beneficiary_customer_account_no) {
		this.beneficiary_customer_account_no = beneficiary_customer_account_no;
	}



	public String getBeneficiary_Cust_Address() {
		return beneficiary_Cust_Address;
	}

	public void setBeneficiary_Cust_Address(String beneficiary_Cust_Address) {
		this.beneficiary_Cust_Address = beneficiary_Cust_Address;
	}

	public Double getAmt() {
		return amt;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
	}

	public Date getValue_Date() {
		return value_Date;
	}

	public void setValue_Date(Date value_Date) {
		this.value_Date = value_Date;
	}

	public String getRemittance_date() {
		return remittance_date;
	}

	public void setRemittance_date(String remittance_date) {
		this.remittance_date = remittance_date;
	}

	public Date getOriginal_Value_Date() {
		return original_Value_Date;
	}

	public void setOriginal_Value_Date(Date original_Value_Date) {
		this.original_Value_Date = original_Value_Date;
	}

	public String getReason_Code() {
		return reason_Code;
	}

	public void setReason_Code(String reason_Code) {
		this.reason_Code = reason_Code;
	}

	public String getRejection_Reason() {
		return rejection_Reason;
	}

	public void setRejection_Reason(String rejection_Reason) {
		this.rejection_Reason = rejection_Reason;
	}

	public String getSender_To_Rec_Info() {
		return sender_To_Rec_Info;
	}

	public void setSender_To_Rec_Info(String sender_To_Rec_Info) {
		this.sender_To_Rec_Info = sender_To_Rec_Info;
	}

	public String getExport_status() {
		return export_status;
	}

	public void setExport_status(String export_status) {
		this.export_status = export_status;
	}

	public Long getHeader_id() {
		return header_id;
	}

	public void setHeader_id(Long header_id) {
		this.header_id = header_id;
	}
	
	public String getBeneficiary_customer_account_name() {
		return beneficiary_customer_account_name;
	}
	public void setBeneficiary_customer_account_name(
			String beneficiary_customer_account_name) {
		this.beneficiary_customer_account_name = beneficiary_customer_account_name;
	}
	
}
