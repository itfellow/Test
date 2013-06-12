package com.omni.oesb.fileparser.transformer.pac;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import com.omni.oesb.fileparser.transformer.TransformerUtil;
import com.omni.oesb.transformer.xml.pacs009_001_03.AccountIdentification4Choice;
import com.omni.oesb.transformer.xml.pacs009_001_03.ActiveCurrencyAndAmount;
import com.omni.oesb.transformer.xml.pacs009_001_03.BranchAndFinancialInstitutionIdentification5;
import com.omni.oesb.transformer.xml.pacs009_001_03.BranchAndFinancialInstitutionIdentification5Dbtr;
import com.omni.oesb.transformer.xml.pacs009_001_03.BranchAndFinancialInstitutionIdentification5GrpHdr;
import com.omni.oesb.transformer.xml.pacs009_001_03.CashAccount24;
import com.omni.oesb.transformer.xml.pacs009_001_03.CategoryPurpose1Choice;
import com.omni.oesb.transformer.xml.pacs009_001_03.ClearingSystemMemberIdentification2;
import com.omni.oesb.transformer.xml.pacs009_001_03.CreditTransferTransaction4;
import com.omni.oesb.transformer.xml.pacs009_001_03.Document;
import com.omni.oesb.transformer.xml.pacs009_001_03.ExternalCategoryPurpose1Code;
import com.omni.oesb.transformer.xml.pacs009_001_03.FinancialInstitutionCreditTransferV03;
import com.omni.oesb.transformer.xml.pacs009_001_03.FinancialInstitutionIdentification8;
import com.omni.oesb.transformer.xml.pacs009_001_03.FinancialInstitutionIdentification8Dbtr;
import com.omni.oesb.transformer.xml.pacs009_001_03.FinancialInstitutionIdentification8GrpHdr;
import com.omni.oesb.transformer.xml.pacs009_001_03.GenericAccountIdentification1;
import com.omni.oesb.transformer.xml.pacs009_001_03.GenericFinancialIdentification1;
import com.omni.oesb.transformer.xml.pacs009_001_03.GroupHeader51;
import com.omni.oesb.transformer.xml.pacs009_001_03.LocalInstrument2Choice;
import com.omni.oesb.transformer.xml.pacs009_001_03.ObjectFactory;
import com.omni.oesb.transformer.xml.pacs009_001_03.PaymentIdentification3;
import com.omni.oesb.transformer.xml.pacs009_001_03.PaymentTypeInformation21;
import com.omni.oesb.transformer.xml.pacs009_001_03.PostalAddress6;
import com.omni.oesb.transformer.xml.pacs009_001_03.Priority2Code;
import com.omni.oesb.transformer.xml.pacs009_001_03.RemittanceInformation2;
import com.omni.oesb.transformer.xml.pacs009_001_03.ServiceLevel8Choice;
import com.omni.oesb.transformer.xml.pacs009_001_03.SettlementInstruction1;
import com.omni.oesb.transformer.xml.pacs009_001_03.SettlementMethod1Code;

public class TransformerPac913 extends TransformerPacHeader implements Transformer{
	
	public void convertToNGRTGS(HashMap<String, String> headerMap, HashMap<String, String>  msgBodyMap){
		
		try {
			
			ObjectFactory factory913 = new ObjectFactory();
			
			/*GrpHdr*/
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
			
			/*GrpHdr* - InstgAgt*/
			ClearingSystemMemberIdentification2 sndrClrSysMmbId = new ClearingSystemMemberIdentification2();
			sndrClrSysMmbId.setMmbId("TEST0000001");
			
			FinancialInstitutionIdentification8GrpHdr sndrFinInstnId = new FinancialInstitutionIdentification8GrpHdr();
			sndrFinInstnId.setClrSysMmbId(sndrClrSysMmbId);
			
			BranchAndFinancialInstitutionIdentification5GrpHdr sndrInstgAgt = new BranchAndFinancialInstitutionIdentification5GrpHdr();
			sndrInstgAgt.setFinInstnId(sndrFinInstnId);
			
			grpHdr.setInstgAgt(sndrInstgAgt);
			
			/*GrpHdr* - InstdAgt*/
			ClearingSystemMemberIdentification2 rcvrClrSysMmbId = new ClearingSystemMemberIdentification2();
			rcvrClrSysMmbId.setMmbId("TEST0000001");
			
			FinancialInstitutionIdentification8GrpHdr rcvrFinInstnId = new FinancialInstitutionIdentification8GrpHdr();
			rcvrFinInstnId.setClrSysMmbId(rcvrClrSysMmbId);
			
			BranchAndFinancialInstitutionIdentification5GrpHdr rcvrInstgAgt = new BranchAndFinancialInstitutionIdentification5GrpHdr();
			rcvrInstgAgt.setFinInstnId(rcvrFinInstnId);
			
			grpHdr.setInstdAgt(rcvrInstgAgt);
			
			
			/*CdtTrfTxInf*/
			CreditTransferTransaction4 cdtTrfTxInf = new CreditTransferTransaction4();
			
			/*CdtTrfTxInf - PmtId*/
			PaymentIdentification3 pmtId = new PaymentIdentification3();
			pmtId.setEndToEndId("/XUTR/TESTH13057010572");
			pmtId.setTxId("TESTR52013022600010572");
			cdtTrfTxInf.setPmtId(pmtId);
			
			/*CdtTrfTxInf - PmtTpInf*/
			PaymentTypeInformation21 pmtTpInf = new PaymentTypeInformation21();
			pmtTpInf.setInstrPrty(Priority2Code.HIGH);
			
			/*CdtTrfTxInf - PmtTpInf - SvcLvl*/
			ServiceLevel8Choice svcLvl = new ServiceLevel8Choice();
			svcLvl.setPrtry("TTC=4001,PRI=80");
			pmtTpInf.setSvcLvl(svcLvl);
			
			/*CdtTrfTxInf - PmtTpInf - LclInstrm*/
			LocalInstrument2Choice lclInstrm = new LocalInstrument2Choice();
			lclInstrm.setPrtry("RTGSFIToFICredit");
			pmtTpInf.setLclInstrm(lclInstrm);
			
			/*CdtTrfTxInf - PmtTpInf - CtgyPurp*/
			CategoryPurpose1Choice ctgyPurp = new CategoryPurpose1Choice();
			ctgyPurp.setCd(ExternalCategoryPurpose1Code.CASH);
			pmtTpInf.setCtgyPurp(ctgyPurp);
			
			cdtTrfTxInf.setPmtTpInf(pmtTpInf);
			
			/*CdtTrfTxInf - IntrBkSttlmAmt*/
			ActiveCurrencyAndAmount cActiveCrncyAndAmt = new ActiveCurrencyAndAmount();
			cActiveCrncyAndAmt.setCcy("INR");
			cActiveCrncyAndAmt.setValue(new BigDecimal(34440444.00));
			
			cdtTrfTxInf.setIntrBkSttlmAmt(cActiveCrncyAndAmt);
			
			/*CdtTrfTxInf - Dbtr*/
			BranchAndFinancialInstitutionIdentification5Dbtr dbtr = new BranchAndFinancialInstitutionIdentification5Dbtr();
			
			/*CdtTrfTxInf - Dbtr - FinInstnId*/
			FinancialInstitutionIdentification8Dbtr dbtrFinInstnId = new FinancialInstitutionIdentification8Dbtr();
			
			/*CdtTrfTxInf - Dbtr - FinInstnId - ClrSysMmbId*/
			ClearingSystemMemberIdentification2 dbtrClrSysMmbId = new ClearingSystemMemberIdentification2();
			dbtrClrSysMmbId.setMmbId("TEST0000001");
			dbtrFinInstnId.setClrSysMmbId(dbtrClrSysMmbId);
			
			dbtrFinInstnId.setNm("TEST Bank");
			dbtr.setFinInstnId(dbtrFinInstnId);
			
			cdtTrfTxInf.setDbtr(dbtr);
			
			/*CdtTrfTxInf - Cdtr*/
			BranchAndFinancialInstitutionIdentification5 cdtr = new BranchAndFinancialInstitutionIdentification5();;
			
			/*CdtTrfTxInf - Cdtr - FinInstnId*/
			FinancialInstitutionIdentification8 cdtrFinInstnId = new FinancialInstitutionIdentification8();
			
			/*CdtTrfTxInf - Cdtr - FinInstnId - ClrSysMmbId*/
			ClearingSystemMemberIdentification2 cdtrClrSysMmbId = new ClearingSystemMemberIdentification2();
			cdtrClrSysMmbId.setMmbId("HDFC0000002");
			cdtrFinInstnId.setClrSysMmbId(cdtrClrSysMmbId);
			
			cdtrFinInstnId.setNm("HDFC Bank");
			
			/*CdtTrfTxInf - Cdtr - FinInstnId - PstlAdr*/
			PostalAddress6 pstladr = new PostalAddress6();
			pstladr.getAdrLine().add("HDFC Postal Address Line 1");
			pstladr.getAdrLine().add("HDFC Postal Address Line 2");
			pstladr.getAdrLine().add("HDFC Postal Address Line 3");
			cdtrFinInstnId.setPstlAdr(pstladr);
			
			/*CdtTrfTxInf - Cdtr - FinInstnId - Id*/
			GenericFinancialIdentification1 othr = new GenericFinancialIdentification1();
			othr.setId("FinancialIdentification");
			cdtrFinInstnId.setOthr(othr);
			
			cdtr.setFinInstnId(cdtrFinInstnId);
			
			cdtTrfTxInf.setCdtr(cdtr);
			
			/*CdtTrfTxInf - CdtrAcct*/
			CashAccount24 cdtrAcct = new CashAccount24();
			
			/*CdtTrfTxInf - CdtrAcct - Id*/
			AccountIdentification4Choice id = new AccountIdentification4Choice();
			
			/*CdtTrfTxInf - CdtrAcct - Id - othr*/
			GenericAccountIdentification1 cOthr = new GenericAccountIdentification1();
			cOthr.setId("C33333333333C");
			id.setOthr(cOthr);

			cdtrAcct.setId(id);
			
			cdtTrfTxInf.setCdtrAcct(cdtrAcct);
			
			/*CdtTrfTxInf - RmtInf*/
			RemittanceInformation2 rmtInf = new RemittanceInformation2();
			rmtInf.getUstrd().add("Remittince Info Line 1");
			rmtInf.getUstrd().add("Remittince Info Line 2");
			cdtTrfTxInf.setRmtInf(rmtInf);
			
			/*FinInstnCdtTrf*/
			FinancialInstitutionCreditTransferV03 finInstnCdtTrf = new FinancialInstitutionCreditTransferV03();
			finInstnCdtTrf.setGrpHdr(grpHdr);
			finInstnCdtTrf.getCdtTrfTxInf().add(cdtTrfTxInf);
			
			
			Document document = factory913.createDocument();
			document.setFinInstnCdtTrf(finInstnCdtTrf);
			
			JAXBContext context = JAXBContext.newInstance("com.omni.oesb.transformer.xml.pacs009_001_03");
			JAXBElement<Document> element = factory913.createDocument(document);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			marshaller.marshal(element,System.out);
		} catch (PropertyException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}
	

	
	public static void main(String[] args) {
//		new TransformerPac913().convertToNGRTGS("asd");
	}


}
