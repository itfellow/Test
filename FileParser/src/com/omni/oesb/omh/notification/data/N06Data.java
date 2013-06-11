package com.omni.oesb.omh.notification.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "N06_DATA")
public class N06Data{
	
//	2020
	@Id
	@Column(name = "TRANSACTION_REFID")
	private String transaction_RefNo;
	
//	3535
	@Column(name = "BATCH_TIME")
	private String batch_Time;
	
//	1106
	@Column(name = "TOTAL_LOOPS")
	private Integer total_Transactions;
	
//	4063
	@Column(name = "SUMOFAMT_CURRENT")
	private Double ttl_amt;
	
//	2020
	@Column(name = "TRANSACTION_LOOP_REFNO")
	private String transaction_Loop_RefNo;
	
//	4038
	@Column(name = "AMT")
	private Double	 amt;
	
//	3380
	@Column(name = "VALUE_DATE")
	private Date valueDate;
	
//	5756
	@Column(name = "SEND_BRANCH_IFSC")
	private String sending_Branch_IFSC;
	
//	6305
	@Column(name = "SENDING_CUSTOMER_ACCOUNT_TYPE")
	private String sending_Cust_Acc_Type;
	
//	6021
	@Column(name = "SENDING_CUST_ACCOUNT_NO")
	private String sending_Cust_Acc_No;
	
//	6091
	@Column(name = "SENDING_CUSTOMER_ACCOUNT_NAME")
	private String sender_Acc_Name;
	
//	5629
	@Column(name = "SENDING_CUST_EMAIL")
	private String sending_Cust_EmailMob;
	
//	7002
	@Column(name = "ORGINATOR_REMITTANCE")
	private String originator_Of_Remittance;
	
//	5569
	@Column(name = "BENEFICIARY_BRANCH_IFSC")
	private String ben_Branch_IFSC;
	
//	6310
	@Column(name = "BENEFICIARY_CUSTOMER_ACCOUNT_TYPE")
	private String ben_Cust_Acc_Type;
	
//	6061
	@Column(name = "BENEFICIARY_CUSTOMER_ACCOUNT_NO")
	private String ben_Cust_Acc_No;
	
//	6081
	@Column(name = "BENEFICIARY_CUSTOEMER_ACCOUNT_NAME")
	private String ben_Cust_Acc_Name;
	
//	5565
	@Column(name = "BENEFICIARY_CUSTOEMER_ADDRESS")
	private String ben_Cust_Acc_Address;
	
//	3375
	@Column(name = "REMMITANCE_DATE")
	private String remittance_date;
	
//	7495
	@Column(name = "REMMITANCE_INFO")
	private String remittance_info;
	
	@Column(name = "EXPORT_STATUS")
	private String export_status;
	
	@Column(name = "HEADER_ID")
	private Long header_id;

	public N06Data() {}

	public String getTransaction_RefNo() {
		return transaction_RefNo;
	}

	public void setTransaction_RefNo(String transaction_RefNo) {
		this.transaction_RefNo = transaction_RefNo;
	}

	public String getBatch_Time() {
		return batch_Time;
	}

	public void setBatch_Time(String batch_Time) {
		this.batch_Time = batch_Time;
	}

	public Integer getTotal_Transactions() {
		return total_Transactions;
	}

	public void setTotal_Transactions(Integer total_Transactions) {
		this.total_Transactions = total_Transactions;
	}

	public Double getTtl_amt() {
		return ttl_amt;
	}

	public void setTtl_amt(Double ttl_amt) {
		this.ttl_amt = ttl_amt;
	}

	public String getTransaction_Loop_RefNo() {
		return transaction_Loop_RefNo;
	}

	public void setTransaction_Loop_RefNo(String transaction_Loop_RefNo) {
		this.transaction_Loop_RefNo = transaction_Loop_RefNo;
	}

	public Double getAmt() {
		return amt;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public String getSending_Branch_IFSC() {
		return sending_Branch_IFSC;
	}

	public void setSending_Branch_IFSC(String sending_Branch_IFSC) {
		this.sending_Branch_IFSC = sending_Branch_IFSC;
	}

	public String getSending_Cust_Acc_Type() {
		return sending_Cust_Acc_Type;
	}

	public void setSending_Cust_Acc_Type(String sending_Cust_Acc_Type) {
		this.sending_Cust_Acc_Type = sending_Cust_Acc_Type;
	}

	public String getSending_Cust_Acc_No() {
		return sending_Cust_Acc_No;
	}

	public void setSending_Cust_Acc_No(String sending_Cust_Acc_No) {
		this.sending_Cust_Acc_No = sending_Cust_Acc_No;
	}

	public String getSender_Acc_Name() {
		return sender_Acc_Name;
	}

	public void setSender_Acc_Name(String sender_Acc_Name) {
		this.sender_Acc_Name = sender_Acc_Name;
	}

	public String getSending_Cust_EmailMob() {
		return sending_Cust_EmailMob;
	}

	public void setSending_Cust_EmailMob(String sending_Cust_EmailMob) {
		this.sending_Cust_EmailMob = sending_Cust_EmailMob;
	}

	public String getOriginator_Of_Remittance() {
		return originator_Of_Remittance;
	}

	public void setOriginator_Of_Remittance(String originator_Of_Remittance) {
		this.originator_Of_Remittance = originator_Of_Remittance;
	}

	public String getBen_Branch_IFSC() {
		return ben_Branch_IFSC;
	}

	public void setBen_Branch_IFSC(String ben_Branch_IFSC) {
		this.ben_Branch_IFSC = ben_Branch_IFSC;
	}

	public String getBen_Cust_Acc_Type() {
		return ben_Cust_Acc_Type;
	}

	public void setBen_Cust_Acc_Type(String ben_Cust_Acc_Type) {
		this.ben_Cust_Acc_Type = ben_Cust_Acc_Type;
	}

	public String getBen_Cust_Acc_No() {
		return ben_Cust_Acc_No;
	}

	public void setBen_Cust_Acc_No(String ben_Cust_Acc_No) {
		this.ben_Cust_Acc_No = ben_Cust_Acc_No;
	}

	public String getBen_Cust_Acc_Name() {
		return ben_Cust_Acc_Name;
	}

	public void setBen_Cust_Acc_Name(String ben_Cust_Acc_Name) {
		this.ben_Cust_Acc_Name = ben_Cust_Acc_Name;
	}

	public String getBen_Cust_Acc_Address() {
		return ben_Cust_Acc_Address;
	}

	public void setBen_Cust_Acc_Address(String ben_Cust_Acc_Address) {
		this.ben_Cust_Acc_Address = ben_Cust_Acc_Address;
	}

	public String getRemittance_date() {
		return remittance_date;
	}

	public void setRemittance_date(String remittance_date) {
		this.remittance_date = remittance_date;
	}

	public String getRemittance_info() {
		return remittance_info;
	}

	public void setRemittance_info(String remittance_info) {
		this.remittance_info = remittance_info;
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
	

}
