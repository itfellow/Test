package com.omni.oesb.fileparser.transformer.pac;

import java.math.BigDecimal;

import com.omni.oesb.fileparser.transformer.TransformerUtil;
import com.omni.oesb.transformer.xml.pacs009_001_03.ActiveCurrencyAndAmount;
import com.omni.oesb.transformer.xml.pacs009_001_03.BranchAndFinancialInstitutionIdentification5GrpHdr;
import com.omni.oesb.transformer.xml.pacs009_001_03.ClearingSystemMemberIdentification2;
import com.omni.oesb.transformer.xml.pacs009_001_03.FinancialInstitutionIdentification8GrpHdr;
import com.omni.oesb.transformer.xml.pacs009_001_03.GroupHeader51;
import com.omni.oesb.transformer.xml.pacs009_001_03.ObjectFactory;
import com.omni.oesb.transformer.xml.pacs009_001_03.SettlementInstruction1;
import com.omni.oesb.transformer.xml.pacs009_001_03.SettlementMethod1Code;

public class TransformerPac913 {
	
	public void createPac913() {
		ObjectFactory factory913 = new ObjectFactory();
		
		GroupHeader51 grpHdr = new GroupHeader51();
		grpHdr.setMsgId("SBIC201310181000000301");
		grpHdr.setCreDtTm(TransformerUtil.convertToXMLGregorianDateTime("20131018","1000"));
		grpHdr.setNbOfTxs("1");

		/*settlmntInf*/
		ActiveCurrencyAndAmount activeCrncyAndAmt = new ActiveCurrencyAndAmount();
		activeCrncyAndAmt.setCcy("INR");
		activeCrncyAndAmt.setValue(new BigDecimal(3453453));
		grpHdr.setTtlIntrBkSttlmAmt(activeCrncyAndAmt);
		grpHdr.setIntrBkSttlmDt(TransformerUtil.convertToXMLGregorianDateTime("20131018","1000"));

		SettlementInstruction1 settlmntInf = new SettlementInstruction1();
		settlmntInf.setSttlmMtd(SettlementMethod1Code.CLRG);
		grpHdr.setSttlmInf(settlmntInf);
		
		/*InstgAgt*/
		ClearingSystemMemberIdentification2 sndrClrSysMmbId = new ClearingSystemMemberIdentification2();
		sndrClrSysMmbId.setMmbId("TEST0000001");
		
		FinancialInstitutionIdentification8GrpHdr sndrFinInstnId = new FinancialInstitutionIdentification8GrpHdr();
		sndrFinInstnId.setClrSysMmbId(sndrClrSysMmbId);
		
		BranchAndFinancialInstitutionIdentification5GrpHdr sndrInstgAgt = new BranchAndFinancialInstitutionIdentification5GrpHdr();
		sndrInstgAgt.setFinInstnId(sndrFinInstnId);
		
		grpHdr.setInstgAgt(sndrInstgAgt);
		
		/*InstdAdt*/
		
	}
	public static void main(String[] args) {
		new TransformerPac913().createPac913();
	}

}