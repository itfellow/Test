//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.06.04 at 11:33:25 PM IST 
//


package com.omni.oesb.transformer.xml.head_001_001_01;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CopyDuplicate1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CopyDuplicate1Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CODU"/>
 *     &lt;enumeration value="COPY"/>
 *     &lt;enumeration value="DUPL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CopyDuplicate1Code")
@XmlEnum
public enum CopyDuplicate1Code {

    CODU,
    COPY,
    DUPL;

    public String value() {
        return name();
    }

    public static CopyDuplicate1Code fromValue(String v) {
        return valueOf(v);
    }

}
