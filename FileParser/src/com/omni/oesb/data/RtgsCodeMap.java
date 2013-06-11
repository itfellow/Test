package com.omni.oesb.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "rtgs_code_map")
public class RtgsCodeMap {

	@Id
	@Column(name = "MSG_CODE")
	private String msg_code = null;
	
	@Column(name = "CODE_VALUE")
	private String code_value = null;
	
	public RtgsCodeMap() {}
	
	@Fetch(FetchMode.SELECT)
	public String getMsg_code() {
		return msg_code;
	}

	public void setMsg_code(String msg_code) {
		this.msg_code = msg_code;
	}
	
	@Fetch(FetchMode.SELECT)
	public String getCode_value() {
		return code_value;
	}

	public void setCode_value(String code_value) {
		this.code_value = code_value;
	}
	
	
}
