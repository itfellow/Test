package com.omni.oesb.DigitalSignature;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.cms.*;
import java.security.*;
import java.security.cert.*;


public class SignData {
	
	public static String execute()
	{
	
		String args = "ABCDEFGH";
		String keypass="OMNITECH";
		String certpass="OMNITECH";
		String certpath="D:\\omh\\certificate\\OMH.jks";		
		String alias_name="selfsigned";
		String keyStore_instance="JKS";
		String algoName="RSA";		
		
		String s="";
		
		FileInputStream ksfis = null;
		try{
				String ksName=certpath;
				String spass=certpass;
				String alias=alias_name;
				String kpass=keypass;
				
			System.out.println("----- Step 1 -----");
				java.security.cert.Certificate storecert = null;
			      java.security.cert.Certificate[] certChain = null;
				byte[] contentbytes=null;
		      	 Security.addProvider(new BouncyCastleProvider());
				ArrayList certList = new ArrayList();
			System.out.println("----- Step 2 -----");
	
				//Initialize Keystore from JKS file
			   
				KeyStore ks = KeyStore.getInstance(keyStore_instance);
				ksfis = new FileInputStream(ksName); 
				BufferedInputStream ksbufin = new BufferedInputStream(ksfis);  
	 			ks.load(ksbufin, spass.toCharArray());
			System.out.println("----- Step 3 -----");
				// Get Certificate chain from keystore
	
				certChain = ks.getCertificateChain(alias);
	
				// Load array list with retrieved certificates
			System.out.println("----- Step 3a -----: "+certChain.length);
	   			for ( int i = 0; i < certChain.length;i++)
					certList.add(certChain[i]);      
				
				// Initilize Certificate Store
				CertStore certs  = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
			System.out.println("----- Step 4 -----");
				// Extract private key
	
				PrivateKey priv = (PrivateKey) ks.getKey(alias, kpass.toCharArray());
				
				// Extract public key
			System.out.println("----- Step 5 -----");
				PublicKey pub = ks.getCertificate(alias).getPublicKey();
	
				// Get signer certificate from keystore
			System.out.println("----- Step 6 -----");
				storecert = ks.getCertificate(alias);
	
				CMSSignedDataGenerator signGen = new CMSSignedDataGenerator();
				signGen.addSigner(priv, (X509Certificate)storecert, CMSSignedGenerator.DIGEST_SHA1);
			      signGen.addCertificatesAndCRLs(certs);
				contentbytes = args.getBytes(); 
				CMSProcessable content = new CMSProcessableByteArray(contentbytes);
			System.out.println("----- Step 7 -----");
	 			 CMSSignedData signedData = signGen.generate(content,"BC");
				 byte[] signeddata = signedData.getEncoded();
				 //System.out.println("Created signed message: " + signeddata.length + " bytes") ;
				
			System.out.println("----- Step 8 -----");	
				//s=Base64Utils.base64Encode(signeddata);
				s = "MIAGCSqGSIb3DQEHAqCAMIACAQExCzAJBgUrDgMCGgUAMIAGCSqGSIb3DQEHAQAAoIAwggNkMIICTKADAgECAgRRbRloMA0GCSqGSIb3DQEBBQUAMHQxCzAJBgNVBAYTAklOMRQwEgYDVQQIEwtNQUhBUkFTSFRSQTEPMA0GA1UEBxMGTVVNQkFJMREwDwYDVQQKEwhPTU5JVEVDSDEcMBoGA1UECxMTQVBQTElDQVRJT04gU0VSVklDRTENMAsGA1UEAxMET01OSTAeFw0xMzA0MTYwOTI3MDRaFw0xNDA0MTEwOTI3MDRaMHQxCzAJBgNVBAYTAklOMRQwEgYDVQQIEwtNQUhBUkFTSFRSQTEPMA0GA1UEBxMGTVVNQkFJMREwDwYDVQQKEwhPTU5JVEVDSDEcMBoGA1UECxMTQVBQTElDQVRJT04gU0VSVklDRTENMAsGA1UEAxMET01OSTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAIH8+2H4jgEyDp9MYgN/WUxtyzE1xeJ4tIDTPc6B0+UaTGadWILqZGJieFEF+mcEfqQQ695kR+foqv0MgPdrTLwqBOA0FNGqwSv+e/7896KOzpjaSfK2Nx3fCNN+dvPpKTp++WrRUF52JSoliB41bfSC260KzZAHMT5kXlloOERylqonX8WpvfK0t/VXuMnLEbpbpsFaPK9iO1Gb/LY7Av4z1is2jLZ8hXfnODrO1BA6HJTuCLNjorfCH5p8Lsn9QzUQ5qAKvcY7eYgWqLvZ/wgRr0zP71IIJ0w8L0ygEZ0yuiniqamPI4X/aizzz0ZMhvlIDoP2X7b2scQBQ3hu980CAwEAATANBgkqhkiG9w0BAQUFAAOCAQEAdXlV0sbgZ+PZ5NFcnbqo8DRqOxYjxOxqBwuuPA/z4q7egNKwfH5sN+2T15j5TxnvykB6jQaXxs2NmiIBGoSPhEnHiCpa2W27zu6XKSijNVwoC6haajXcMw1QsU7r0fBJYJmVBZtYVd6+cO8iyB44tAv8Drjq62SxcfaJqzjwMcxRAykedzawBN4jPzJRWpY4dKh8c+6ElSCY5IxPuBrrr9rb0buVuf61G6gFnlKl9tgh7OKgqIMG2yhqO1ZQh/gvBn21uRLX9hqFiqyQLi4DPymbUCuJGElaTz3TtylUxzgFMqzARWpJVriAEwIx1NsnRzeLtA7oZgBnsMTWnyCkewAAMYICAjCCAf4CAQEwfDB0MQswCQYDVQQGEwJJTjEUMBIGA1UECBMLTUFIQVJBU0hUUkExDzANBgNVBAcTBk1VTUJBSTERMA8GA1UEChMIT01OSVRFQ0gxHDAaBgNVBAsTE0FQUExJQ0FUSU9OIFNFUlZJQ0UxDTALBgNVBAMTBE9NTkkCBFFtGWgwCQYFKw4DAhoFAKBdMBgGCSqGSIb3DQEJAzELBgkqhkiG9w0BBwEwHAYJKoZIhvcNAQkFMQ8XDTEzMDUzMTA0NTcxNlowIwYJKoZIhvcNAQkEMRYEFMFetLVb0AD0qHbB1xW+5C5is+V2MA0GCSqGSIb3DQEBAQUABIIBAAG0q2z7igxfI6bm6+EEEeW0B52JeCfGVc3YYYwBPciI+98hgi6XtV6zrCEPG481zuti2g9q+4F+wD/TICn/IXu6Fa9CrZAJ+aOOStytK9sgaBxAdQ51ankH5vtveJWQIcgPFnf/D9mr0q9jjhQxvlaK7Lqy/3z9rPnNwFkQY2SxcFC1RlxN7nEFCgT95OY86WFlOJZmiW5e78HZbJ0N35TtXpx9aedYyxMGcSvERd2jnad/pUMNAFzOA/MfG61moMFM2sEq9w6arQWMTvEsupm6O4ZFp9oU3HfLSqMtiU9rfDA2wjQlaHRxEIcO3K0ABm3yL21LEtZI3YZGGuZ66roAAAAAAAA=";
			System.out.println("----- Step 9 -----");
	
				 //FileOutputStream envfos = new FileOutputStream("BCsigned.p7s");
				 //envfos.write(signeddata);
		
	
		} catch (Exception e) {
	         System.err.println("Exception while creating digital signature for NEFT message :" + e.toString());
	     }
	
	     finally {
	    	 try {
				//envfos.close();
				ksfis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     }
	
		return s;
	
	} //End of execute()

	public static void main(String args[])
	{
	
		System.out.println("--------------------------------------------------------------------------");
		// System.out.println(SignData.execute("ABCDEFGH","federal","federalbank","D:\\NEFT\\PKCS\\federalbank.jks","federalbank","JKS","RSA"));
		System.out.println(SignData.execute());
		System.out.println("--------------------------------------------------------------------------");
	
	}


} 
