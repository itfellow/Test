package com.omni.oesb.fileparser.transformer.pac;

import java.io.File;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import com.omni.oesb.fileparser.transformer.TransformerUtil;
import com.omni.oesb.transformer.xml.pacs_008_001_03.AccountIdentification4Choice;
import com.omni.oesb.transformer.xml.pacs_008_001_03.ActiveCurrencyAndAmount;
import com.omni.oesb.transformer.xml.pacs_008_001_03.ActiveOrHistoricCurrencyCode;
import com.omni.oesb.transformer.xml.pacs_008_001_03.BranchAndFinancialInstitutionIdentification5;
import com.omni.oesb.transformer.xml.pacs_008_001_03.BranchAndFinancialInstitutionIdentification5CdtrAgt;
import com.omni.oesb.transformer.xml.pacs_008_001_03.BranchAndFinancialInstitutionIdentification5DbtrAgt;
import com.omni.oesb.transformer.xml.pacs_008_001_03.CashAccount24;
import com.omni.oesb.transformer.xml.pacs_008_001_03.CategoryPurpose1Choice;
import com.omni.oesb.transformer.xml.pacs_008_001_03.ChargeBearerType1Code;
import com.omni.oesb.transformer.xml.pacs_008_001_03.ClearingSystemMemberIdentification2;
import com.omni.oesb.transformer.xml.pacs_008_001_03.CreditTransferTransaction2;
import com.omni.oesb.transformer.xml.pacs_008_001_03.Document;
import com.omni.oesb.transformer.xml.pacs_008_001_03.ExternalCategoryPurpose1Code;
import com.omni.oesb.transformer.xml.pacs_008_001_03.FIToFICustomerCreditTransferV03;
import com.omni.oesb.transformer.xml.pacs_008_001_03.FinancialInstitutionIdentification8;
import com.omni.oesb.transformer.xml.pacs_008_001_03.FinancialInstitutionIdentification8CdtrAgt;
import com.omni.oesb.transformer.xml.pacs_008_001_03.FinancialInstitutionIdentification8DbtrAgt;
import com.omni.oesb.transformer.xml.pacs_008_001_03.GenericAccountIdentification1;
import com.omni.oesb.transformer.xml.pacs_008_001_03.GroupHeader49;
import com.omni.oesb.transformer.xml.pacs_008_001_03.LocalInstrument2Choice;
import com.omni.oesb.transformer.xml.pacs_008_001_03.ObjectFactory;
import com.omni.oesb.transformer.xml.pacs_008_001_03.PartyIdentification43;
import com.omni.oesb.transformer.xml.pacs_008_001_03.PaymentIdentification3;
import com.omni.oesb.transformer.xml.pacs_008_001_03.PaymentTypeInformation21;
import com.omni.oesb.transformer.xml.pacs_008_001_03.PostalAddress6;
import com.omni.oesb.transformer.xml.pacs_008_001_03.Priority2Code;
import com.omni.oesb.transformer.xml.pacs_008_001_03.RemittanceInformation7;
import com.omni.oesb.transformer.xml.pacs_008_001_03.ServiceLevel8Choice;
import com.omni.oesb.transformer.xml.pacs_008_001_03.SettlementInstruction1;
import com.omni.oesb.transformer.xml.pacs_008_001_03.SettlementMethod1Code;

public final class TransformerPac813 extends TransformerPacHeader implements Transformer{
	
	public void convertToNGRTGS(String fileName){
		
		String []mergeFile = new String[2];
		
		mergeFile[0] = CreadAppHeader("FIToFICustomerCredit");
		mergeFile[1] = createDocumentBody("FIToFICustomerCredit");
		
		mergePac(fileName,mergeFile);
		
	}
	
	private String createDocumentBody(String BusinessServiceRule){
		
		try{
			
			ObjectFactory factoryPac008 = new ObjectFactory();
			
			/******************** Group Header Starts here ***************/
			
			GroupHeader49 grpHdr = factoryPac008.createGroupHeader49();
			
			//Set transId here
			grpHdr.setMsgId("SBIC201310181000000301");						
			
			// greogrian calender example:- 2013-10-18T09:00:00
			// set Date and time here
			grpHdr.setCreDtTm(TransformerUtil.convertToXMLGregorianDateTime("20131018","1000"));
			grpHdr.setNbOfTxs("1");							// number given in String
			
			ActiveCurrencyAndAmount actvCurrencyAndAmt = new ActiveCurrencyAndAmount();
			
			// Set Currency Type for tot amt
			actvCurrencyAndAmt.setCcy("INR");
			
			// Set total amt here
			actvCurrencyAndAmt.setValue(new BigDecimal(5000000));
			
			
			grpHdr.setTtlIntrBkSttlmAmt(actvCurrencyAndAmt);	
			
			//set Settelment Date here
			grpHdr.setIntrBkSttlmDt(TransformerUtil.convertToXMLGregorianDate("20121018"));
			
			SettlementInstruction1 SttlmInf = new SettlementInstruction1();
			
			// set SettlementMethod here
			SttlmInf.setSttlmMtd(SettlementMethod1Code.CLRG);
			
			grpHdr.setSttlmInf(SttlmInf);
			
			/* Sets <FinInstnId> */
			BranchAndFinancialInstitutionIdentification5 branchFinInstnId = new BranchAndFinancialInstitutionIdentification5();
			/* Sets <ClrSysMmbId> */
			FinancialInstitutionIdentification8 FinInstnId = new FinancialInstitutionIdentification8();
			/* Sets <MmbId> */
			ClearingSystemMemberIdentification2 MmbId = new ClearingSystemMemberIdentification2();
			
			// Set Sender Ifsc here
			MmbId.setMmbId("SBIC0004080");
			
			FinInstnId.setClrSysMmbId(MmbId);
			
			branchFinInstnId.setFinInstnId(FinInstnId);
			
			grpHdr.setInstgAgt(branchFinInstnId);
			
			branchFinInstnId = new BranchAndFinancialInstitutionIdentification5();
			FinInstnId = new FinancialInstitutionIdentification8();
			MmbId = new ClearingSystemMemberIdentification2();
			
			// Set Receiver Ifsc here
			MmbId.setMmbId("ALLA0211214");
			
			FinInstnId.setClrSysMmbId(MmbId);
			
			branchFinInstnId.setFinInstnId(FinInstnId);
			grpHdr.setInstdAgt(branchFinInstnId);
			
			/********************  CreditTransferTransaction Starts here ***************/
			CreditTransferTransaction2 cdtTrfTxInf = new CreditTransferTransaction2();
			
			PaymentIdentification3 pmtId = new PaymentIdentification3();
			
			//	set transRef loop no
			pmtId.setInstrId("TRN0000000000001");
			// set utr no
			pmtId.setEndToEndId("/XUTR/TESTH11000000301");
			// set tranaction Id
			String transId =  "SBIC20131018R100000301";
			pmtId.setTxId(transId);
			
			cdtTrfTxInf.setPmtId(pmtId);
			
			PaymentTypeInformation21 paymentTypeInfo = new PaymentTypeInformation21();
			
			paymentTypeInfo.setInstrPrty(null);
			
			ServiceLevel8Choice SvcLvl = new ServiceLevel8Choice();
			
			paymentTypeInfo.setInstrPrty(Priority2Code.HIGH);
			
			//set prority here
			
			SvcLvl.setPrtry("99");
			
			paymentTypeInfo.setSvcLvl(SvcLvl);
			
			LocalInstrument2Choice LclInstrm = new LocalInstrument2Choice();
			
			// set proprietry 'RTGSFIToFICredit' or 'RTGSFIToFICustomerCredit’ or 'RTGSOwnAccTtransfer' or 'RTGSPaymentReturn' or 'RTGSNetSettlementXXzNN'
			LclInstrm.setPrtry(BusinessServiceRule);
			
			paymentTypeInfo.setLclInstrm(LclInstrm);
			
			CategoryPurpose1Choice CtgyPurp = new CategoryPurpose1Choice();
			
			// set Category Purpose
			CtgyPurp.setCd(ExternalCategoryPurpose1Code.CASH);
			
			paymentTypeInfo.setCtgyPurp(CtgyPurp);
			
			cdtTrfTxInf.setPmtTpInf(paymentTypeInfo);
			
			// set currency for amt
			actvCurrencyAndAmt.setCcy("INR");
			// set amt here
			actvCurrencyAndAmt.setValue(new BigDecimal(5000000));
			
			cdtTrfTxInf.setIntrBkSttlmAmt(actvCurrencyAndAmt);
			
			// set ChargeBearer Codes used are: CRED/DEBT/SHAR/SLEV
			cdtTrfTxInf.setChrgBr(ChargeBearerType1Code.DEBT);
			
			PartyIdentification43 partyId = new PartyIdentification43();
			// set Address Line 1 here
			partyId.setNm("Address Line 2");
	
			PostalAddress6 postalAdrs = new PostalAddress6();
			
			// set Address Line 2 here (List add items to add next Line)
			postalAdrs.getAdrLine().add("Address Line 2");
			
			partyId.setPstlAdr(postalAdrs);
			
			cdtTrfTxInf.setDbtr(partyId);
			
			CashAccount24 cashAcnt = new CashAccount24();
			
			AccountIdentification4Choice acntId = new AccountIdentification4Choice();
			
			GenericAccountIdentification1 genericAcntId = new GenericAccountIdentification1();
			
			// set order Customer (customer Account no) (Debtor's Account number)
			genericAcntId.setId("SBI7510000101007");
			acntId.setOthr(genericAcntId);
			
			cashAcnt.setId(acntId);
			cashAcnt.setCcy(ActiveOrHistoricCurrencyCode.INR);
			
			// below commented code set <tp> This message item is composed of the following BalanceType12element(s):
	//		CashAccountType2Choice cashAcntTyp = new CashAccountType2Choice();
	//		cashAcntTyp.setPrtry("sds");
	//		cashAcnt.setTp(cashAcntTyp);
			
			cdtTrfTxInf.setDbtrAcct(cashAcnt);
			
			BranchAndFinancialInstitutionIdentification5DbtrAgt branchFinInstnIdDbtrAgt = new BranchAndFinancialInstitutionIdentification5DbtrAgt();
			
			FinancialInstitutionIdentification8DbtrAgt finInstnIdDbtrAgt = new FinancialInstitutionIdentification8DbtrAgt();
			
			ClearingSystemMemberIdentification2 clrSysMmbId = new ClearingSystemMemberIdentification2();
			
			// set order institution / sender ifsc
			clrSysMmbId.setMmbId("SBIC0004080");
			
			finInstnIdDbtrAgt.setClrSysMmbId(clrSysMmbId);
			
			branchFinInstnIdDbtrAgt.setFinInstnId(finInstnIdDbtrAgt);
			
			
			cdtTrfTxInf.setDbtrAgt(branchFinInstnIdDbtrAgt);
			
			BranchAndFinancialInstitutionIdentification5CdtrAgt branchFitIdCdtrAgt = new BranchAndFinancialInstitutionIdentification5CdtrAgt();
			
			FinancialInstitutionIdentification8CdtrAgt fitIdCdtrAgt = new FinancialInstitutionIdentification8CdtrAgt();
			
			ClearingSystemMemberIdentification2 clrSysMmbId2 = new ClearingSystemMemberIdentification2();
			
			// Set Beneficiary IFSC or Receiver Ifsc here or Beneficiary Institution identification
			clrSysMmbId2.setMmbId("ALLA0211214");
			
			fitIdCdtrAgt.setClrSysMmbId(clrSysMmbId2);
			
			// commented code sets Bank Name (this setter found not working, XML not generating name of the bank in file)
			//	fitIdCdtrAgt.setNm("HDFC");
			
			branchFitIdCdtrAgt.setFinInstnId(fitIdCdtrAgt);
			
			cdtTrfTxInf.setCdtrAgt(branchFitIdCdtrAgt);
			
			partyId = new PartyIdentification43();
			
			// set Beneficiary Customer Name
			partyId.setNm("Karthik_Menon");
			
			// set Beneficary Customer Addres
			// partyId.setPstlAdr(null);
			
			cdtTrfTxInf.setCdtr(partyId);
			
			cashAcnt = new CashAccount24();
			
			acntId = new AccountIdentification4Choice();
			
			genericAcntId = new GenericAccountIdentification1();
			
			// set beneficary Acnt No here
			genericAcntId.setId("HDFC000001102015");
			
			acntId.setOthr(genericAcntId);
			cashAcnt.setId(acntId);
			
			// set currency type here
			cashAcnt.setCcy(ActiveOrHistoricCurrencyCode.INR);
			cdtTrfTxInf.setCdtrAcct(cashAcnt);
			
			RemittanceInformation7 remittanceInfo = new RemittanceInformation7();
			
			// set Sender to Receiver Information or Beneficiary Customer's Postal Address(add item)
			remittanceInfo.getUstrd().add("/URGENT/RECEIPT 1001");
			remittanceInfo.getUstrd().add("//PLEASE PROCESS QUICKLY");
			cdtTrfTxInf.setRmtInf(remittanceInfo);
			
			cdtTrfTxInf.setChrgsInf(null);
	
			FIToFICustomerCreditTransferV03 fIToFICustomerCredit = factoryPac008.createFIToFICustomerCreditTransferV03();
			
			fIToFICustomerCredit.setGrpHdr(grpHdr);
			fIToFICustomerCredit.setCdtTrfTxInf(cdtTrfTxInf);
			
			Document document = factoryPac008.createDocument();
			document.setFIToFICstmrCdtTrf(fIToFICustomerCredit);
			
			JAXBContext context = JAXBContext.newInstance("com.omni.oesb.transformer.xml.pacs_008_001_03");
	        JAXBElement<Document> element = factoryPac008.createDocument(document);
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        
	        
	        File path = new File(xmlCachePath);

	        
	        String docBodyAbsPath = path.getAbsolutePath()+"\\DocBody"+transId+".xml";
	        File createXml = new File(docBodyAbsPath);
	        
	        marshaller.marshal(element,createXml);

	        marshaller.marshal(element,System.out);
	        
	        return docBodyAbsPath;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	public static void main(String ar[]){
// 		new TransformerPac813().createPac813();
		String []as = new String[2];
		as[0] = "C:\\omh\\parserConf\\File Adapter\\xmlCache\\AppHeadSBIC201310181000000301.xml";
		as[1] = "C:\\omh\\parserConf\\File Adapter\\xmlCache\\DocBodySBIC20131018R100000301.xml";
		
		new TransformerPac813().mergePac("yeahh",as);
	}

}
