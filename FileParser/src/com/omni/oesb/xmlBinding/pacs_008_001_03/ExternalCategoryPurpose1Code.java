//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.06.04 at 11:31:28 PM IST 
//


package com.omni.oesb.xmlBinding.pacs_008_001_03;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExternalCategoryPurpose1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ExternalCategoryPurpose1Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CASH"/>
 *     &lt;enumeration value="CORT"/>
 *     &lt;enumeration value="DIVI"/>
 *     &lt;enumeration value="GOVT"/>
 *     &lt;enumeration value="HEDG"/>
 *     &lt;enumeration value="INTC"/>
 *     &lt;enumeration value="INTE"/>
 *     &lt;enumeration value="LOAN"/>
 *     &lt;enumeration value="PENS"/>
 *     &lt;enumeration value="SALA"/>
 *     &lt;enumeration value="SECU"/>
 *     &lt;enumeration value="SSBE"/>
 *     &lt;enumeration value="SUPP"/>
 *     &lt;enumeration value="TAXS"/>
 *     &lt;enumeration value="TRAD"/>
 *     &lt;enumeration value="TREA"/>
 *     &lt;enumeration value="VATX"/>
 *     &lt;enumeration value="WHLD"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ExternalCategoryPurpose1Code")
@XmlEnum
public enum ExternalCategoryPurpose1Code {

    CASH,
    CORT,
    DIVI,
    GOVT,
    HEDG,
    INTC,
    INTE,
    LOAN,
    PENS,
    SALA,
    SECU,
    SSBE,
    SUPP,
    TAXS,
    TRAD,
    TREA,
    VATX,
    WHLD;

    public String value() {
        return name();
    }

    public static ExternalCategoryPurpose1Code fromValue(String v) {
        return valueOf(v);
    }

}
