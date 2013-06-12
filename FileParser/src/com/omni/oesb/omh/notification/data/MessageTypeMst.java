package com.omni.oesb.omh.notification.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.omni.oesb.data.XmlTransformerMap;

@Entity
@Table(name="MESSAGE_TYPE_MST")

public class MessageTypeMst {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MESSAGE_TYPE_ID")
	private Long message_type_id;
		
	@Column(name = "MESSAGE_TYPE")
	private String message_type;
		
	@Column(name = "IO_IDENTIFIER")
	private String io_identifier;
		
	@Column(name = "MSG_TYP_FLAG")
	private String msg_typ_flag;
	
	@Column(name = "SERIES")
	private String series;
	
	@ManyToOne
	@JoinColumn(name = "xml_id")
	private XmlTransformerMap xml_id;

	public MessageTypeMst() {}

	@Fetch(FetchMode.SELECT)
	public Long getMessage_type_id() {
		return message_type_id;
	}


	public void setMessage_type_id(Long message_type_id) {
		this.message_type_id = message_type_id;
	}

	@Fetch(FetchMode.SELECT)
	public String getMessage_type() {
		return message_type;
	}


	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	@Fetch(FetchMode.SELECT)
	public String getIo_identifier() {
		return io_identifier;
	}


	public void setIo_identifier(String io_identifier) {
		this.io_identifier = io_identifier;
	}

	@Fetch(FetchMode.SELECT)
	public String getMsg_typ_flag() {
		return msg_typ_flag;
	}

	@Fetch(FetchMode.SELECT)
	public void setMsg_typ_flag(String msg_typ_flag) {
		this.msg_typ_flag = msg_typ_flag;
	}

	@Fetch(FetchMode.SELECT)
	public String getSeries() {
		return series;
	}


	public void setSeries(String series) {
		this.series = series;
	}
	
	public XmlTransformerMap getXml_id() {
		return xml_id;
	}

	public void setXml_id(XmlTransformerMap xml_id) {
		this.xml_id = xml_id;
	}



	
}
