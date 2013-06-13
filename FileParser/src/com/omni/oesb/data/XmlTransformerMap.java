package com.omni.oesb.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.omni.oesb.omh.notification.data.MessageTypeMst;

@Entity
@Table(name = "xml_transformer_map")
public class XmlTransformerMap {
	
	@Id
	@Column(name = "id")
	private int id = 0;
	
	@Column(name = "PAC_NAME")
	private String pac_name = null;
	
	@Column(name = "TRANSFORMER_CLASS_NAME")
	private String tranformer_class_name = null;
	
	@Column(name = "BUSINESS_SERVICE_RULE")
	private String business_service_rule = null;

	public XmlTransformerMap(){}
	
	@Fetch(FetchMode.SELECT)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Fetch(FetchMode.SELECT)
	public String getPac_name() {
		return pac_name;
	}
	
	
	public void setPac_name(String pac_name) {
		this.pac_name = pac_name;
	}
	
	@Fetch(FetchMode.SELECT)
	public String getTranformer_class_name() {
		return tranformer_class_name;
	}
	
	public void setTranformer_class_name(String tranformer_class_name) {
		this.tranformer_class_name = tranformer_class_name;
	}
	
	@Fetch(FetchMode.SELECT)
	public String getBusiness_service_rule() {
		return business_service_rule;
	}

	public void setBusiness_service_rule(String business_service_rule) {
		this.business_service_rule = business_service_rule;
	}
}
