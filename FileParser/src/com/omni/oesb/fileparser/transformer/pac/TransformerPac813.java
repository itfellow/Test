package com.omni.oesb.fileparser.transformer.pac;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.ResourceBundle;

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
import com.omni.oesb.transformer.xml.pacs_008_001_03.Instruction3Code;
import com.omni.oesb.transformer.xml.pacs_008_001_03.InstructionForCreditorAgent1;
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
import com.omni.util.common.PropAccess;

public final class TransformerPac813 extends TransformerPacHeader implements Transformer{
	
	private ResourceBundle bundle 		= PropAccess.getResourceBundle();
	
	private String transIdGen = bundle.getString("TransIdGenFlag").trim();
	
	public void convertToNGRTGS(String pacName,
								String businessRule, 
								HashMap<String, String> headerMap, 
								HashMap<String, String>  msgBodyMap){
		
		String []mergeFile = new String[2];
		
		String msgSubTyp = headerMap.get("MSG_SUBTYPE");
		
		String transId = generateNgrtgsTransId(msgSubTyp.charAt(0), headerMap);
		
		mergeFile[0] = CreadAppHeader(pacName, businessRule, transId, headerMap);
		
		//DataFormatting/filtering for NEFT & RTGS
		msgBodyMap = filterData(transId,msgSubTyp,headerMap,msgBodyMap);
				
		mergeFile[1] = createDocumentBody(businessRule, transId, msgBodyMap);
		
		String fileName = msgSubTyp+"_"+transId;
		
		mergePac(fileName, mergeFile);
		
	}
	
	//
	private HashMap<String, String>  filterData(String transId,String type, HashMap<String, String> headerMap, HashMap<String, String>  msgBodyMap){
		
		HashMap<String ,String> filteredData = new HashMap<String, String>();
		
		try{
			
			String ORGIN_DATE = headerMap.get("ORGIN_DT").replace("/", "");
			String ORGIN_TIME = headerMap.get("ORGIN_TIME");
			String TOTAL_AMT = null;
			String CURRENCY = null;
			String SENDER_IFSC = null;
			String RECIEVER_IFSC = null;
			String TRANS_REF_LOOP_NO = null;
			String END_TO_END_ID = null;
			String TRANS_ID = null;
			String AMT = null;
			String PRIORITY_FLAG = null;
			String CUST_ACNT_NO = null;
			String CUST_ADRS = null;
			String CUST_NAME = null;
			String BENF_ACNT_NO = null;
			String SENDER_TO_REMITTANCE_INFO = null;
			
			
			
			SENDER_IFSC = headerMap.get("SNDR_IFSC");;
			RECIEVER_IFSC = headerMap.get("RCVR_IFSC");
			
			String utrNo = headerMap.get("UTR");
			if(utrNo != null){
			
				END_TO_END_ID = "/XUTR/" + utrNo;
			
			}
			else{
				
				END_TO_END_ID = "/XUTR/";
			}
			
			PRIORITY_FLAG = headerMap.get("PRIORITY_FLAG");
			
			CURRENCY = msgBodyMap.get("CURRENCY");
			
			TRANS_ID = transId.substring(0,12);
			
			
			if(type.charAt(0)=='R' || type.charAt(0)=='r'){
				
				TRANS_ID = TRANS_ID + "R" + transId.substring(12,22);
				
				TOTAL_AMT = msgBodyMap.get("AMT");
				
				TRANS_REF_LOOP_NO = msgBodyMap.get("TRANS_REF_ID");
				AMT = msgBodyMap.get("AMT");
				
				String order_cust = msgBodyMap.get("ORDRNG_CUST");
				String[] order_cust_dtls = null;
				
				if(order_cust != null)
					order_cust_dtls = order_cust.split("\\n");
				
				CUST_ACNT_NO = order_cust_dtls[0];
				CUST_NAME = order_cust_dtls[1];
				CUST_ADRS = msgBodyMap.get("ORDRNG_CUST");
				
				
				String benf_cust = msgBodyMap.get("BENF_CUST");
				String[] benf_dtls = null;
				
				if(benf_cust != null)
					benf_dtls = benf_cust.split("\\n");
					
				String BENF_ADRS = msgBodyMap.get("BENF_CUST");
				filteredData.put("BENF_ADRS", BENF_ADRS);
				
				BENF_ACNT_NO = benf_dtls[0].replace("/", "");
				filteredData.put("BENF_NAME", benf_dtls[1]);
				
				SENDER_TO_REMITTANCE_INFO = msgBodyMap.get("SNDR_TO_RCVR_INFO").replace("//", "");
				filteredData.put("SENDER_TO_REMITTANCE_INFO", SENDER_TO_REMITTANCE_INFO);
				
			}
			else if(type.charAt(0)=='N' || type.charAt(0)=='n'){
				
				TRANS_ID = TRANS_ID + "N" + transId.substring(12,22);
				
				TRANS_REF_LOOP_NO = msgBodyMap.get("TRANS_LOOP_REF_ID");
				
				AMT = msgBodyMap.get("AMT");
				
				CUST_ACNT_NO = msgBodyMap.get("CUST_ACNT_NO");
				CUST_NAME = msgBodyMap.get("CUST_ACNT_NAME");
				CUST_ADRS = msgBodyMap.get("ORGIN_REMIT");
				
				BENF_ACNT_NO = msgBodyMap.get("BENF_ACNT_NO");
				
				String BENF_NAME = msgBodyMap.get("BENF_ACNT_NAME");
				filteredData.put("BENF_NAME", BENF_NAME);
				
				String BENF_ADRS = msgBodyMap.get("BENF_ADRS");
				filteredData.put("BENF_ADRS", BENF_ADRS);
			}
			else{
				System.out.println("Warning: Message Type Not Identified, Check the Message");
				System.exit(0);
			}
			
			filteredData.put("MSG_SUBTYPE", headerMap.get("MSG_SUBTYPE"));
			filteredData.put("ORGIN_DATE", ORGIN_DATE);
			filteredData.put("ORGIN_TIME", ORGIN_TIME);
			filteredData.put("TOTAL_AMT", TOTAL_AMT);
			filteredData.put("CURRENCY", CURRENCY);
			filteredData.put("SENDER_IFSC", SENDER_IFSC);
			filteredData.put("RECIEVER_IFSC", RECIEVER_IFSC);
			filteredData.put("TRANS_REF_LOOP_NO", TRANS_REF_LOOP_NO);
			filteredData.put("END_TO_END_ID", END_TO_END_ID);
			filteredData.put("TRANS_ID", TRANS_ID);
			filteredData.put("AMT", AMT);
			filteredData.put("PRIORITY_FLAG", PRIORITY_FLAG);
			filteredData.put("CUST_ACNT_NO", CUST_ACNT_NO);
			filteredData.put("CUST_ADRS", CUST_ADRS);
			filteredData.put("CUST_NAME", CUST_NAME);
			filteredData.put("BENF_ACNT_NO", BENF_ACNT_NO);
			
		}
		catch(Exception e){
			
			e.printStackTrace();
			
		}
		
		
		return filteredData;
	}
	
	
	private String createDocumentBody(String BusinessServiceRule, String transId, HashMap<String, String>  msgBodyMap){
		
		try{
			
			String msg_sub_typ = msgBodyMap.get("MSG_SUBTYPE");
			
			ObjectFactory factoryPac008 = new ObjectFactory();
			
			/******************** Group Header Starts here ***************/
			
			GroupHeader49 grpHdr = factoryPac008.createGroupHeader49();
			
			//Set transId here
			grpHdr.setMsgId(transId);						
			
			// greogrian calender example:- 2013-10-18T09:00:00
			// set Date and time here
			grpHdr.setCreDtTm(TransformerUtil.convertToXMLGregorianDateTime(msgBodyMap.get("ORGIN_DATE"),msgBodyMap.get("ORGIN_TIME"), false));
			grpHdr.setNbOfTxs("1");					// number given in String
			
			ActiveCurrencyAndAmount actvCurrencyAndAmt = new ActiveCurrencyAndAmount();
			
			// Set Currency Type for tot amt
			actvCurrencyAndAmt.setCcy(msgBodyMap.get("CURRENCY"));
			
			// Set total amt here
			actvCurrencyAndAmt.setValue(new BigDecimal(Double.parseDouble(msgBodyMap.get("AMT"))));
			
			
			grpHdr.setTtlIntrBkSttlmAmt(actvCurrencyAndAmt);	
			
			//set Settelment Date here
			grpHdr.setIntrBkSttlmDt(TransformerUtil.convertToXMLGregorianDate(msgBodyMap.get("ORGIN_DATE")));
			
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
			MmbId.setMmbId(msgBodyMap.get("SENDER_IFSC"));
			
			FinInstnId.setClrSysMmbId(MmbId);
			
			branchFinInstnId.setFinInstnId(FinInstnId);
			
			grpHdr.setInstgAgt(branchFinInstnId);
			
			branchFinInstnId = new BranchAndFinancialInstitutionIdentification5();
			FinInstnId = new FinancialInstitutionIdentification8();
			MmbId = new ClearingSystemMemberIdentification2();
			
			// Set Receiver Ifsc here
			MmbId.setMmbId(msgBodyMap.get("RECIEVER_IFSC"));
			
			FinInstnId.setClrSysMmbId(MmbId);
			
			branchFinInstnId.setFinInstnId(FinInstnId);
			grpHdr.setInstdAgt(branchFinInstnId);
			
			/********************  CreditTransferTransaction Starts here ***************/
			CreditTransferTransaction2 cdtTrfTxInf = new CreditTransferTransaction2();
			
			PaymentIdentification3 pmtId = new PaymentIdentification3();
			
			//	set transRef loop no
			pmtId.setInstrId(msgBodyMap.get("TRANS_REF_LOOP_NO"));
			
			// set utr no
			pmtId.setEndToEndId(msgBodyMap.get("END_TO_END_ID"));
			
			// set tranaction Id
			pmtId.setTxId(transId);
			
			cdtTrfTxInf.setPmtId(pmtId);
			
			PaymentTypeInformation21 paymentTypeInfo = new PaymentTypeInformation21();
			
			paymentTypeInfo.setInstrPrty(null);
			
			ServiceLevel8Choice SvcLvl = new ServiceLevel8Choice();
			
			// indicator of urgency, Default value high
			paymentTypeInfo.setInstrPrty(Priority2Code.HIGH);
			
			//set prority here
			
			SvcLvl.setPrtry(msgBodyMap.get("PRIORITY_FLAG"));
			
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
			actvCurrencyAndAmt.setCcy(msgBodyMap.get("CURRENCY"));
			
			// set amt here
			actvCurrencyAndAmt.setValue(new BigDecimal(getAmount(msgBodyMap.get("AMT"))));
			
			cdtTrfTxInf.setIntrBkSttlmAmt(actvCurrencyAndAmt);
			
			//	WARNING HARD-CODED Debit enum used here for making tag <ChrgBr>DEBT</ChrgBr>
			// set ChargeBearer Codes used are: CRED/DEBT/SHAR/SLEV
			cdtTrfTxInf.setChrgBr(ChargeBearerType1Code.DEBT);
			
			if(msg_sub_typ.charAt(0)=='R' || msg_sub_typ.charAt(0)=='r'){
				
				PartyIdentification43 partyId = new PartyIdentification43();
				
				String cust_name = msgBodyMap.get("CUST_NAME");
				
				// set cust name here for RTGS
				if(cust_name!=null)
					partyId.setNm(cust_name);
		
				PostalAddress6 postalAdrs = new PostalAddress6();
				
				// set cust adrs here (List add items to add next Line) for RTGS
				String custAdrs = msgBodyMap.get("CUST_ADRS");
				String[] cust_adrs_Dtls  = null;
				
				if(custAdrs!=null)
					cust_adrs_Dtls = custAdrs.split("\\n");
				
				if(cust_adrs_Dtls!=null){
					int cust_adrs_Dtls_len = cust_adrs_Dtls.length;
					if(cust_adrs_Dtls_len > 0){
						for(int i=2 ; i < cust_adrs_Dtls_len ; i++){
							postalAdrs.getAdrLine().add(cust_adrs_Dtls[i]);
						}
						
						partyId.setPstlAdr(postalAdrs);
					}
				}
				
				cdtTrfTxInf.setDbtr(partyId);
			}
			else{
				PartyIdentification43 partyId = new PartyIdentification43();
				
				String cust_name = msgBodyMap.get("CUST_NAME");
				
				// set cust name here for NEFT
				if(cust_name!=null)
					partyId.setNm(cust_name);
		
				PostalAddress6 postalAdrs = new PostalAddress6();
				
				// set cust adrs here (List add items to add next Line) for NEFT
				String custAdrs = msgBodyMap.get("CUST_ADRS");
				String[] cust_adrs_Dtls  = null;
				
				if(custAdrs!=null)
					cust_adrs_Dtls = custAdrs.split("\\n");
				
				if(cust_adrs_Dtls!=null){
					int cust_adrs_Dtls_len = cust_adrs_Dtls.length;
					if(cust_adrs_Dtls_len > 0){
						for(int i=0 ; i < cust_adrs_Dtls_len ; i++){
							postalAdrs.getAdrLine().add(cust_adrs_Dtls[i]);
						}
						
						partyId.setPstlAdr(postalAdrs);
					}
				}
				
				cdtTrfTxInf.setDbtr(partyId);
			}
			
			
		
			
		
			
			CashAccount24 cashAcnt = new CashAccount24();
			
			AccountIdentification4Choice acntId = new AccountIdentification4Choice();
			
			GenericAccountIdentification1 genericAcntId = new GenericAccountIdentification1();
			
			// set order Customer (customer Account no) (Debtor's Account number)
			genericAcntId.setId(msgBodyMap.get("CUST_ACNT_NO"));
			acntId.setOthr(genericAcntId);
			
			cashAcnt.setId(acntId);
			
			// Set default to INR
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
			clrSysMmbId.setMmbId(msgBodyMap.get("SENDER_IFSC"));
			
			finInstnIdDbtrAgt.setClrSysMmbId(clrSysMmbId);
			
			branchFinInstnIdDbtrAgt.setFinInstnId(finInstnIdDbtrAgt);
			
			
			cdtTrfTxInf.setDbtrAgt(branchFinInstnIdDbtrAgt);
			
			BranchAndFinancialInstitutionIdentification5CdtrAgt branchFitIdCdtrAgt = new BranchAndFinancialInstitutionIdentification5CdtrAgt();
			
			FinancialInstitutionIdentification8CdtrAgt fitIdCdtrAgt = new FinancialInstitutionIdentification8CdtrAgt();
			
			ClearingSystemMemberIdentification2 clrSysMmbId2 = new ClearingSystemMemberIdentification2();
			
			// Set Beneficiary IFSC or Receiver Ifsc here or Beneficiary Institution identification
			clrSysMmbId2.setMmbId(msgBodyMap.get("RECIEVER_IFSC"));
			
			fitIdCdtrAgt.setClrSysMmbId(clrSysMmbId2);
			
			// commented code sets Bank Name (this setter found not working, XML not generating name of the bank in file)
			//	fitIdCdtrAgt.setNm("HDFC");
			
			branchFitIdCdtrAgt.setFinInstnId(fitIdCdtrAgt);
			
			cdtTrfTxInf.setCdtrAgt(branchFitIdCdtrAgt);
			
			if(msg_sub_typ.charAt(0)=='R' || msg_sub_typ.charAt(0)=='r'){
				
				PartyIdentification43 partyId = new PartyIdentification43();
				
				String benf_name = msgBodyMap.get("BENF_NAME").toUpperCase();
				// set Benf Customer Name for RTGS
				if(benf_name!=null)
					partyId.setNm(benf_name);
				
				PostalAddress6 postalAdrs = new PostalAddress6();
				
				// set Benf Customer Adrs for RTGS
				String benfAdrs = msgBodyMap.get("BENF_ADRS");
				String[] benfAdrsDtls = null;
				
				if(benfAdrs!=null)
					benfAdrsDtls = msgBodyMap.get("BENF_ADRS").split("\\n");
				
				if(benfAdrsDtls!=null){
					int benf_adrs_len = benfAdrsDtls.length;
					if(benf_adrs_len > 0){
						for(int i=2 ; i < benf_adrs_len ; i++){
							postalAdrs.getAdrLine().add(benfAdrsDtls[i]);
						}
					}
				}
				partyId.setPstlAdr(postalAdrs);
				
				cdtTrfTxInf.setCdtr(partyId);
				
			}
			else{
				
				PartyIdentification43 partyId = new PartyIdentification43();
				
				String benf_name = msgBodyMap.get("BENF_NAME").toUpperCase();
				// set Benf Customer Name for NEFT
				if(benf_name!=null)
					partyId.setNm(benf_name);
				
				PostalAddress6 postalAdrs = new PostalAddress6();
				
				// set Benf Customer Adrs for NEFT
				String benfAdrs = msgBodyMap.get("BENF_ADRS");
				String[] benfAdrsDtls = null;
				
				if(benfAdrs!=null)
					benfAdrsDtls = msgBodyMap.get("BENF_ADRS").split("\\n");
				
				if(benfAdrsDtls!=null){
					int benf_adrs_len = benfAdrsDtls.length;
					if(benf_adrs_len > 0){
						for(int i=1 ; i < benf_adrs_len ; i++){
							postalAdrs.getAdrLine().add(benfAdrsDtls[i]);
						}
					}
				}
				partyId.setPstlAdr(postalAdrs);
				
				cdtTrfTxInf.setCdtr(partyId);
				
			}
			
			cashAcnt = new CashAccount24();
			
			acntId = new AccountIdentification4Choice();
			
			genericAcntId = new GenericAccountIdentification1();
			
			// set beneficary Acnt No here
			genericAcntId.setId(msgBodyMap.get("BENF_ACNT_NO"));
			
			acntId.setOthr(genericAcntId);
			cashAcnt.setId(acntId);
			
			// set currency type here
			cashAcnt.setCcy(ActiveOrHistoricCurrencyCode.INR);
			cdtTrfTxInf.setCdtrAcct(cashAcnt);
			
			RemittanceInformation7 remittanceInfo = new RemittanceInformation7();
			
			if(msg_sub_typ.charAt(0) == 'R' || msg_sub_typ.charAt(0) == 'r'){
				
				String[] sender_to_remit_dtls = msgBodyMap.get("SENDER_TO_REMITTANCE_INFO").split("\\n");
				
				// set Sender to Receiver Information or Beneficiary Customer's Postal Address(add item)
				int len = sender_to_remit_dtls.length;
				for(int i = 0 ; i < len; i++){
					remittanceInfo.getUstrd().add(sender_to_remit_dtls[i]);
				}
			}
			else{
				
				remittanceInfo.getUstrd().add(null);
				
			}
			
			
			
			cdtTrfTxInf.setRmtInf(remittanceInfo);
			
			
			cdtTrfTxInf.setChrgsInf(null);
			
//			if(msg_sub_typ.charAt(0) == 'N' || msg_sub_typ.charAt(0) == 'n'){
				InstructionForCreditorAgent1 instructionCode = new InstructionForCreditorAgent1();
				instructionCode.setCd(Instruction3Code.PHOB);
				cdtTrfTxInf.getInstrForCdtrAgt().add(instructionCode);
//			}
			
			
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
			System.exit(0);
		}
		
		return null;
	}
	
	
	
	// utility methods
	
	private String generateNgrtgsTransId(char msgTyp,HashMap<String, String> headerMap){
		
		// 	transId Format Used to generate in HDFC Bank: sender_IFSC(4) + date(8) + channel default set to 1 + utr(11 to 16)
		//	ex: BNKA20130306006000000
		
		// transId Format according to RBI Documentation: 
		
		try {
			
			String senderIfsc = headerMap.get("SNDR_IFSC");
			
			senderIfsc = senderIfsc.substring(0, 4);
			
			String creationDate = headerMap.get("ORGIN_DT").replace("/", "");
			
			char channel = '1';
			
			if(msgTyp == 'R' || msgTyp == 'r'){
				
				
				String utr = headerMap.get("UTR");
				
				utr = utr.substring(10) + "000";
				
				String transId = senderIfsc + creationDate + channel + utr ;
				
				System.out.println("New NGRTGS TransId Generated: "+transId);
				
				return transId;
			}
			else if(msgTyp == 'N' || msgTyp == 'n'){
				String str = headerMap.get("UTR");
				
				if(str!=null){
					str = str.substring(10) + "000";
				}
				else{
					str = headerMap.get("SEQ_NUM");
				}
				String transId = senderIfsc + creationDate + channel + str;
				
				System.out.println("New NGRTGS TransId Generated: "+transId);
				
				return transId;
				
			}
			else{
				
				throw new Exception("msgTyp Not Recognised");
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private Double getAmount(String amtStr){
		
		Double amt = 0.0;
		if(amtStr!=null){
			amt = Double.parseDouble(amtStr);
		}
		
		return amt;
	}
	

}
