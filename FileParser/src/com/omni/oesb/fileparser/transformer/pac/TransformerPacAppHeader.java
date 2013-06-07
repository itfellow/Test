package com.omni.oesb.fileparser.transformer.pac;

import java.io.File;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import com.omni.oesb.fileparser.transformer.TransformerUtil;
import com.omni.oesb.transformer.xml.head_001_001_01.BranchAndFinancialInstitutionIdentification5;
import com.omni.oesb.transformer.xml.head_001_001_01.BusinessApplicationHeaderV01;
import com.omni.oesb.transformer.xml.head_001_001_01.ClearingSystemMemberIdentification2;
import com.omni.oesb.transformer.xml.head_001_001_01.CopyDuplicate1Code;
import com.omni.oesb.transformer.xml.head_001_001_01.FinancialInstitutionIdentification8;
import com.omni.oesb.transformer.xml.head_001_001_01.ObjectFactory;
import com.omni.oesb.transformer.xml.head_001_001_01.Party9Choice;
import com.omni.oesb.transformer.xml.head_001_001_01.SignatureEnvelope;
import com.omni.util.common.PropAccess;



public  class TransformerPacAppHeader {
	
	protected ResourceBundle bundle = PropAccess.getResourceBundle();
	
	public void CreadAppHeader(String pacTyp,String BusinessServiceRule){
		try{
			ObjectFactory factoryHead001 = new ObjectFactory();
	
			BusinessApplicationHeaderV01 appHeadr = new BusinessApplicationHeaderV01();
			
			Party9Choice partyChoice = new Party9Choice();
			
			BranchAndFinancialInstitutionIdentification5 fIId = new BranchAndFinancialInstitutionIdentification5();
			
			FinancialInstitutionIdentification8 fiInstId = new FinancialInstitutionIdentification8();
			
			ClearingSystemMemberIdentification2 clrSysMembrId = new ClearingSystemMemberIdentification2();
			
			// set sender ifsc here 
			clrSysMembrId.setMmbId("SBIC0004080");
			
			fiInstId.setClrSysMmbId(clrSysMembrId);
			
			fIId.setFinInstnId(fiInstId);
			
			partyChoice.setFIId(fIId);
			
			appHeadr.setFr(partyChoice);
			
			partyChoice = new Party9Choice();
			fIId = new BranchAndFinancialInstitutionIdentification5();
			fiInstId = new FinancialInstitutionIdentification8();
			clrSysMembrId = new ClearingSystemMemberIdentification2();
			
			// set receiver ifsc here
			clrSysMembrId.setMmbId("ALLA0211214");
			
			fiInstId.setClrSysMmbId(clrSysMembrId);
			
			fIId.setFinInstnId(fiInstId);
			
			partyChoice.setFIId(fIId);
			
			appHeadr.setTo(partyChoice);
			
			// set business Message Id (accorndng to XML given by kumari transactionID)
			String transId = "SBIC201310181000000301";
			appHeadr.setBizMsgIdr(transId);
			
			appHeadr.setMsgDefIdr(pacTyp);
			
			// set business service Rule
			appHeadr.setBizSvc(BusinessServiceRule);
			
			appHeadr.setCreDt(TransformerUtil.convertToXMLGregorianDateTime("20131018", "0900"));
			
			appHeadr.setCpyDplct(CopyDuplicate1Code.CODU);
			
			SignatureEnvelope signEnvlop = new SignatureEnvelope();
			
			
			
			signEnvlop.setAny(null);
			
			appHeadr.setSgntr(signEnvlop);
			
			/************* Combining all objects to form XMl *********/
			
			JAXBContext context = JAXBContext.newInstance("com.omni.oesb.transformer.xml.head_001_001_01");
	        JAXBElement<BusinessApplicationHeaderV01> element = factoryHead001.createAppHdr(appHeadr);
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	   
	        marshaller.marshal(element,System.out);
			
	        String pathStr = bundle.getString("xmlCacheFolder").trim();
	        File path = new File(pathStr);
	        File createXml = new File(path.getAbsolutePath()+"\\AppHead"+transId+".xml");
	        marshaller.marshal(element,createXml);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String ar[]){
		new TransformerPacAppHeader().CreadAppHeader("pacs.008.001.03","FIToFICustomerCredit");
	}
}
