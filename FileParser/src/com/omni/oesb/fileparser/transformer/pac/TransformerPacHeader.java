package com.omni.oesb.fileparser.transformer.pac;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import com.omni.oesb.fileparser.Util.FileReaderUtil;
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



public abstract class TransformerPacHeader {
	
	protected FileReaderUtil fileReaderUtil = new FileReaderUtil();
	
	protected ResourceBundle bundle = PropAccess.getResourceBundle();
	
	String pathStr = bundle.getString("xmlCacheFolder").trim();
	
	String xmlCachePath = bundle.getString("xmlCacheFolder").trim();
	
	String xmlTransformPath = bundle.getString("xmlTransformPath").trim();
	
	protected String CreadAppHeader(String pacName,String BusinessServiceRule, String transId, HashMap<String, String> headerMap){
		
		try{
			
			ObjectFactory factoryHead001 = new ObjectFactory();
			
			BusinessApplicationHeaderV01 appHeadr = new BusinessApplicationHeaderV01();
			
			Party9Choice partyChoice = new Party9Choice();
			
			BranchAndFinancialInstitutionIdentification5 fIId = new BranchAndFinancialInstitutionIdentification5();
			
			FinancialInstitutionIdentification8 fiInstId = new FinancialInstitutionIdentification8();
			
			ClearingSystemMemberIdentification2 clrSysMembrId = new ClearingSystemMemberIdentification2();
			
			// set sender ifsc here 
			clrSysMembrId.setMmbId(headerMap.get("SNDR_IFSC"));
			
			fiInstId.setClrSysMmbId(clrSysMembrId);
			
			fIId.setFinInstnId(fiInstId);
			
			partyChoice.setFIId(fIId);
			
			appHeadr.setFr(partyChoice);
			
			partyChoice = new Party9Choice();
			fIId = new BranchAndFinancialInstitutionIdentification5();
			fiInstId = new FinancialInstitutionIdentification8();
			clrSysMembrId = new ClearingSystemMemberIdentification2();

			// set receiver ifsc here
			clrSysMembrId.setMmbId(headerMap.get("RCVR_IFSC"));
			
			fiInstId.setClrSysMmbId(clrSysMembrId);
			
			fIId.setFinInstnId(fiInstId);
			
			partyChoice.setFIId(fIId);
			
			appHeadr.setTo(partyChoice);
			
			// set business Message Id (accorndng to XML given by kumari transactionID)
			appHeadr.setBizMsgIdr(transId);
			
			appHeadr.setMsgDefIdr(pacName);
			
			// set business service Rule
			appHeadr.setBizSvc(BusinessServiceRule);
			
			String date = headerMap.get("ORGIN_DT");
			String time = headerMap.get("ORGIN_TIME");
			appHeadr.setCreDt(TransformerUtil.convertToXMLGregorianDateTime(date, time, true));
			
			// set possible duplicate emission flag here
			if(headerMap.get("POSBL_DUP_EMSN_FLAG").equals("1")){
				appHeadr.setCpyDplct(CopyDuplicate1Code.DUPL);
			}
			else{
				appHeadr.setCpyDplct(CopyDuplicate1Code.CODU);
			}
				
			
			
			SignatureEnvelope signEnvlop = new SignatureEnvelope();
			
//			 String sign = "hiThereFellow";
//			 XMLSignature xml_sign = new XMLSignature( null);
			
			signEnvlop.setAny(null);
			
			appHeadr.setSgntr(signEnvlop);
			
			/************* Combining all objects to form XMl *********/
			
			JAXBContext context = JAXBContext.newInstance("com.omni.oesb.transformer.xml.head_001_001_01");
	        JAXBElement<BusinessApplicationHeaderV01> element = factoryHead001.createAppHdr(appHeadr);
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	   
//	        marshaller.marshal(element,System.out);
			
	        
	        File path = new File(pathStr);
	        
	        String appHeaderAbsPath = path.getAbsolutePath()+"\\AppHead"+transId+".xml";
	        File createXml = new File(appHeaderAbsPath);
	        
	        marshaller.marshal(element,createXml);
			
	        return appHeaderAbsPath;
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		return null;
	}
	
	protected void mergePac(String fileName,String []mergeFile){
		
		StringBuffer fileData = new StringBuffer();
				
		for(String path : mergeFile){
			if(path!=null){
				FileReader fileReader = null;
				BufferedReader bufferedReader = null;
				
				try {
					
					fileReader = new FileReader(path);
					bufferedReader = new BufferedReader(fileReader);
					
					String thisLine = null;
					
					int lineCount = -1;
					
					while((thisLine = bufferedReader.readLine()) != null) {
						
						lineCount++;
						
						if(lineCount > 2){
							fileData.append(thisLine);
						}
						else if(lineCount==0){
							continue;
						}
						else if(lineCount==1){
							
							if(path.equals(mergeFile[0])){
								
								fileData.append("<RequestPayload>\n<AppHdr xmlns:xsi=\"urn:iso:std:iso:20022:tech:xsd:Header\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.01\">");
								
							}
							else if(path.equals(mergeFile[1])){
								
								fileData.append("<Document xmlns:xsi=\"urn:iso:std:iso:20022:tech:xsd:R41\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.03\">");
								
							}
						}
						
						
						
						fileData.append("\n");

					}
					if(fileReader != null)
						fileReader.close();
					
					if(bufferedReader != null)
						bufferedReader.close();
				} 
				catch(Exception e) 
				{
					 e.printStackTrace();	
					 System.exit(0);
				} 
			
			}
		}
		
		File fileDelete = new File(mergeFile[0]);
		if(fileDelete.exists())
			fileDelete.delete();					// delete AppXXX File from XML Cache Folder
		
		fileDelete = new File(mergeFile[1]);	
		if(fileDelete.exists())
			fileDelete.delete();					// delete DocBodyXXX File from XML Cache Folder
		
		fileData.append("</RequestPayload>");
		
		File dest = new File(xmlTransformPath+"\\"+fileName+".xml");
		
		fileReaderUtil.writeData(dest, fileData,false);
		
	}

	public static void main(String ar[]){
//		new TransformerPacHeader();
	}
}
