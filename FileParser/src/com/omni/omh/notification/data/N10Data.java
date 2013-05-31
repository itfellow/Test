package com.omni.omh.notification.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "N10_DATA")
public class N10Data {
	
//	2020
	@Id
	@Column(name = "TRANSACTION_REFID")
	private String transaction_refId;
	
//	2020
	@Column(name = "TRANSACTION_REFNO")
	private String transaction_Loop_RefNo;
	
//	5518
	@Column(name = "ORIGINATOR_IFSC_CODE")
	private String originator_Ifsc_Code;
	
//	2006
	@Column(name = "RELATE_REF")
	private String relate_ref;
	
//	3501
	@Column(name = "AMT_CREDITED_DATE")
	private String amt_credited_date;
	
	@Column(name = "AMT_CREDITED_TIME")
	private String amt_credited_time;
	
	@Column(name = "EXPORT_STATUS")
	private String export_status;
	
	@Column(name = "HEADER_ID")
	private Long header_id;

	public N10Data() {}
	public String getTransaction_refId() {
		return transaction_refId;
	}

	public void setTransaction_refId(String transaction_refId) {
		this.transaction_refId = transaction_refId;
	}

	public String getTransaction_Loop_RefNo() {
		return transaction_Loop_RefNo;
	}

	public void setTransaction_Loop_RefNo(String transaction_Loop_RefNo) {
		this.transaction_Loop_RefNo = transaction_Loop_RefNo;
	}

	public String getOriginator_Ifsc_Code() {
		return originator_Ifsc_Code;
	}

	public void setOriginator_Ifsc_Code(String originator_Ifsc_Code) {
		this.originator_Ifsc_Code = originator_Ifsc_Code;
	}

	public String getRelate_ref() {
		return relate_ref;
	}

	public void setRelate_ref(String relate_ref) {
		this.relate_ref = relate_ref;
	}

	public String getAmt_credited_date() {
		return amt_credited_date;
	}

	public void setAmt_credited_date(String amt_credited_date) {
		this.amt_credited_date = amt_credited_date;
	}

	public String getAmt_credited_time() {
		return amt_credited_time;
	}

	public void setAmt_credited_time(String amt_credited_time) {
		this.amt_credited_time = amt_credited_time;
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
