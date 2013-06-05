package com.omni.xmlbinding.test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.omni.oesb.xmlBinding.pacs_008_001_03.AccountIdentification4Choice;
import com.omni.oesb.xmlBinding.pacs_008_001_03.ActiveCurrencyAndAmount;
import com.omni.oesb.xmlBinding.pacs_008_001_03.ActiveOrHistoricCurrencyCode;
import com.omni.oesb.xmlBinding.pacs_008_001_03.BranchAndFinancialInstitutionIdentification5;
import com.omni.oesb.xmlBinding.pacs_008_001_03.BranchAndFinancialInstitutionIdentification5DbtrAgt;
import com.omni.oesb.xmlBinding.pacs_008_001_03.CashAccount24;
import com.omni.oesb.xmlBinding.pacs_008_001_03.CategoryPurpose1Choice;
import com.omni.oesb.xmlBinding.pacs_008_001_03.ChargeBearerType1Code;
import com.omni.oesb.xmlBinding.pacs_008_001_03.ClearingSystemMemberIdentification2;
import com.omni.oesb.xmlBinding.pacs_008_001_03.CreditTransferTransaction2;
import com.omni.oesb.xmlBinding.pacs_008_001_03.Document;
import com.omni.oesb.xmlBinding.pacs_008_001_03.ExternalCategoryPurpose1Code;
import com.omni.oesb.xmlBinding.pacs_008_001_03.FIToFICustomerCreditTransferV03;
import com.omni.oesb.xmlBinding.pacs_008_001_03.FinancialInstitutionIdentification8;
import com.omni.oesb.xmlBinding.pacs_008_001_03.FinancialInstitutionIdentification8DbtrAgt;
import com.omni.oesb.xmlBinding.pacs_008_001_03.GenericAccountIdentification1;
import com.omni.oesb.xmlBinding.pacs_008_001_03.GroupHeader49;
import com.omni.oesb.xmlBinding.pacs_008_001_03.LocalInstrument2Choice;
import com.omni.oesb.xmlBinding.pacs_008_001_03.ObjectFactory;
import com.omni.oesb.xmlBinding.pacs_008_001_03.PartyIdentification43;
import com.omni.oesb.xmlBinding.pacs_008_001_03.PaymentIdentification3;
import com.omni.oesb.xmlBinding.pacs_008_001_03.PaymentTypeInformation21;
import com.omni.oesb.xmlBinding.pacs_008_001_03.PostalAddress6;
import com.omni.oesb.xmlBinding.pacs_008_001_03.ServiceLevel8Choice;
import com.omni.oesb.xmlBinding.pacs_008_001_03.SettlementInstruction1;
import com.omni.oesb.xmlBinding.pacs_008_001_03.SettlementMethod1Code;

public class test {

	public static XMLGregorianCalendar convertToXMLGregorianDateTime(String Date,String time){
		
		XMLGregorianCalendar dateTime = null;
		if(Date!=null && time!=null){
			
			int day = Integer.parseInt(Date.substring(6));
			int month = Integer.parseInt(Date.substring(4, 6));
			int year = Integer.parseInt(Date.substring(0, 4));
			
			try{
				
				//Set Creation Date here
				GregorianCalendar gregorianDateTime = new GregorianCalendar(year,month,day);		
				
				String hrsStr = time.substring(0, 2);
				
				if(hrsStr.startsWith("0")){
					
					hrsStr = hrsStr.substring(1);
					
				}
				int hrs = Integer.parseInt(hrsStr);
				
				int min = Integer.parseInt(time.substring(2, 4));
				
				//Set Creation Time Starts here
				Calendar cal = Calendar.getInstance();
					// set Hours
					cal.set(Calendar.HOUR_OF_DAY,hrs);
					// set Minute
					cal.set(Calendar.MINUTE,min);
					// set Seconds
					cal.set(Calendar.SECOND,0);
		
				gregorianDateTime.setTime(cal.getTime());
			
				dateTime = DatatypeFactory.newInstance()
					    .newXMLGregorianCalendar(gregorianDateTime);
				dateTime.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
				dateTime.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return dateTime;
	}
	
	
	public static XMLGregorianCalendar convertToXMLGregorianDate(String Date){
		
		XMLGregorianCalendar dateTime = null;
		if(Date!=null){
			
			int day = Integer.parseInt(Date.substring(6));
			int month = Integer.parseInt(Date.substring(4, 6));
			int year = Integer.parseInt(Date.substring(0, 4));
			
			try{
				
				//Set Creation Date here
				GregorianCalendar gregorianDateTime = new GregorianCalendar(year,month,day);
				dateTime = DatatypeFactory.newInstance()
					    .newXMLGregorianCalendar(gregorianDateTime);
				dateTime.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
				dateTime.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
				dateTime.setHour(DatatypeConstants.FIELD_UNDEFINED);
				dateTime.setMinute(DatatypeConstants.FIELD_UNDEFINED);
				dateTime.setSecond(DatatypeConstants.FIELD_UNDEFINED);
			
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return dateTime;
	}
	
	
	public static void main(String[] args) throws JAXBException, DatatypeConfigurationException{
		
		ObjectFactory factoryPac008 = new ObjectFactory();
		
		/******************** Group Header Starts here ***************/
		
		GroupHeader49 grpHdr = factoryPac008.createGroupHeader49();
		
		//Set transId here
		grpHdr.setMsgId("SBIC201310181000000301");						
		
		// greogrian calender example:- 2013-10-18T09:00:00
		// set Date and time here
		grpHdr.setCreDtTm(convertToXMLGregorianDateTime("20131018","1000"));
		grpHdr.setNbOfTxs("1");							// number given in String
		
		ActiveCurrencyAndAmount actvCurrencyAndAmt = new ActiveCurrencyAndAmount();
		
		// Set Currency Type for tot amt
		actvCurrencyAndAmt.setCcy("INR");
		
		// Set total amt here
		actvCurrencyAndAmt.setValue(new BigDecimal(5000000));
		
		
		grpHdr.setTtlIntrBkSttlmAmt(actvCurrencyAndAmt);	
		
		//set Settelment Date here
		grpHdr.setIntrBkSttlmDt(convertToXMLGregorianDate("20121018"));
		
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
		
		// Set Receiver Ifsc here
		MmbId.setMmbId("ALLA0211214");
		
		grpHdr.setInstdAgt(branchFinInstnId);
		
		/********************  CreditTransferTransaction Starts here ***************/
		CreditTransferTransaction2 cdtTrfTxInf = new CreditTransferTransaction2();
		
		PaymentIdentification3 pmtId = new PaymentIdentification3();
		
		//	set transRef loop no
		pmtId.setInstrId("TRN0000000000001");
		// set utr no
		pmtId.setEndToEndId("/XUTR/TESTH11000000301");
		// set tranaction Id
		pmtId.setTxId("SBIC20131018R100000301");
		
		cdtTrfTxInf.setPmtId(pmtId);
		
		PaymentTypeInformation21 paymentTypeInfo = new PaymentTypeInformation21();
		
		paymentTypeInfo.setInstrPrty(null);
		
		ServiceLevel8Choice SvcLvl = new ServiceLevel8Choice();
		
		//set prority here
		
		SvcLvl.setPrtry("99");
		
		paymentTypeInfo.setSvcLvl(SvcLvl);
		
		LocalInstrument2Choice LclInstrm = new LocalInstrument2Choice();
		
		// set proprietry 'RTGSFIToFICredit' or 'RTGSFIToFICustomerCredit’ or 'RTGSOwnAccTtransfer' or 'RTGSPaymentReturn' or 'RTGSNetSettlementXXzNN'
		LclInstrm.setPrtry("FIToFICustomerCredit");
		
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
		
		PartyIdentification43 partId = new PartyIdentification43();
		// set Address Line 1 here
		partId.setNm("Address Line 2");
		
		PostalAddress6 postalAdrs = new PostalAddress6();
		
		// set Address Line 2 here (List add items to add next Line)
		postalAdrs.getAdrLine().add("Address Line 2");
		
		partId.setPstlAdr(postalAdrs);
		
		cdtTrfTxInf.setDbtr(partId);
		
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
		
		cdtTrfTxInf.setCdtr(null);
		cdtTrfTxInf.setCdtrAcct(null);
		cdtTrfTxInf.setCdtrAgt(null);
		cdtTrfTxInf.setChrgsInf(null);
		cdtTrfTxInf.setRmtInf(null);
		
		
		FIToFICustomerCreditTransferV03 fIToFICustomerCredit = factoryPac008.createFIToFICustomerCreditTransferV03();
		
		fIToFICustomerCredit.setGrpHdr(grpHdr);
		fIToFICustomerCredit.setCdtTrfTxInf(cdtTrfTxInf);
		
		Document document = factoryPac008.createDocument();
		document.setFIToFICstmrCdtTrf(fIToFICustomerCredit);
		
		JAXBContext context = JAXBContext.newInstance("com.omni.oesb.xmlBinding.pacs_008_001_03");
        JAXBElement<Document> element = factoryPac008.createDocument(document);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
   
        marshaller.marshal(element,System.out);
	}

}
