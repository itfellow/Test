package com.omni.oesb.omh.notification.TableInsert;

import java.util.Date;
import java.util.HashMap;

import com.omni.oesb.fileparser.Util.ParserUtil;
import com.omni.oesb.fileparser.dao.FileParserDao;
import com.omni.oesb.notification.TableInsert.vo.CommonAckStatusVo;
import com.omni.oesb.notification.TableInsert.vo.CommonTransactionDtlsVo;
import com.omni.oesb.omh.notification.data.MessageHeader;
import com.omni.oesb.omh.notification.data.MessageNotificationDtls;
import com.omni.oesb.omh.notification.data.N06Data;
import com.omni.oesb.omh.notification.data.N09Data;
import com.omni.oesb.omh.notification.data.N10Data;
import com.omni.oesb.omh.notification.data.R09Data;
import com.omni.oesb.omh.notification.data.R41Data;
import com.omni.oesb.omh.notification.data.R42Data;
import com.omni.oesb.omh.notification.data.R90Data;
import com.omni.oesb.omh.notification.data.TransactionDtls;
import com.omni.util.common.CommonClass;

/**
 * 
 * This class contain Methods which intialize table pojo of Hibernate, to
 * Perform Insert Operation
 * @author shinoj
 *
 */
public class TableData {
	
	protected final FileParserDao nfDao = new FileParserDao();
	
	protected ParserUtil parserUtil = new ParserUtil();
	
	protected final CommonClass commonClass = new CommonClass();
		
		// check key are mapped from database neft_code_map table
		//Key AMT & SUM_AMT is hardcoded for String Formation in class:MessageDataFilter Method: filterNeftData() 
		@SuppressWarnings("static-access")
		protected Object[] setN06(HashMap<String,String> headerMap,HashMap<String,String> msgBodyMap){
			
			Object tablePojo[] = new Object[2];
			
			CommonTransactionDtlsVo common = new CommonTransactionDtlsVo();
			N06Data n06_data = new N06Data();
			
		
			n06_data.setAmt(Double.parseDouble(msgBodyMap.get("AMT")));
			
			double amt = 0.0;
			amt = Double.parseDouble(msgBodyMap.get("SUM_AMT"));
			n06_data.setTtl_amt(amt);
			
			n06_data.setBatch_Time(msgBodyMap.get("BATCH_TIME"));
			
			String benf_branch_ifsc = headerMap.get("RCVR_IFSC");
			n06_data.setBen_Branch_IFSC(benf_branch_ifsc);
			
			String benf_Cust_Acc_adrs = msgBodyMap.get("BENF_ADRS");
			n06_data.setBen_Cust_Acc_Address(benf_Cust_Acc_adrs);
			
			String benf_Acc_Name = msgBodyMap.get("BENF_ACNT_NAME");
			n06_data.setBen_Cust_Acc_Name(benf_Acc_Name);
			
			String benf_cust_acc = msgBodyMap.get("BENF_ACNT_NO");
			n06_data.setBen_Cust_Acc_No(benf_cust_acc);
			
			String benf_Cust_Acc_typ = msgBodyMap.get("BENF_ACNT_TYP");
			n06_data.setBen_Cust_Acc_Type(benf_Cust_Acc_typ);
			
			n06_data.setExport_status(null);
			n06_data.setHeader_id(null);
			
			n06_data.setRemittance_date(msgBodyMap.get("REMITTANCE_DATE"));
			n06_data.setRemittance_info(msgBodyMap.get("SENDR_TO_RCVR_INFO"));
			
			n06_data.setOriginator_Of_Remittance(msgBodyMap.get("ORGIN_REMIT"));
			n06_data.setSender_Acc_Name(msgBodyMap.get("CUST_ACNT_NAME"));
			String sendingBranchIfsc = headerMap.get("SNDR_IFSC");
			n06_data.setSending_Branch_IFSC(sendingBranchIfsc);
			
			String acnt_no = msgBodyMap.get("CUST_ACNT_NO");
			n06_data.setSending_Cust_Acc_No(acnt_no);
			
			n06_data.setSending_Cust_Acc_Type(msgBodyMap.get("CUST_ACNT_TYP"));
			
			String emailMob = msgBodyMap.get("CUST_MOB_EMAIL");
			n06_data.setSending_Cust_EmailMob(emailMob);
			n06_data.setTotal_Transactions(Integer.parseInt(msgBodyMap.get("TOT_NUM_LOOP")));
			n06_data.setTransaction_Loop_RefNo(msgBodyMap.get("TRANS_LOOP_REF_ID"));
			
			String transId = msgBodyMap.get("TRANS_REF_ID");
										//setting transId globally so that transactionDtls and messageNotification table
																	//can get the transId
			if(transId != null && transId.length() > 0)				
				n06_data.setTransaction_RefNo(transId);
			
			String date = msgBodyMap.get("VALUE_DATE");
			if(date != null && date.length() > 0)
				n06_data.setValueDate(commonClass.convertSQLDateFormat(date));
			
			common.setAccount_no(acnt_no);
			common.setSendingCustEmailMob(emailMob);
			common.setAmt(amt);
			common.setBenfAcnt(benf_cust_acc);
			common.setBenfName(benf_Acc_Name);
			common.setTransId(transId);
			common.setBenfAcntTyp(benf_Cust_Acc_typ);
			
			tablePojo[0] = n06_data;
			tablePojo[1] = common;
			return tablePojo;
		}
		
		protected Object[] setN10(Long headerId, HashMap<String,String> msgBodyMap){
			
			Object[] obj = new Object[2];
			CommonAckStatusVo common = new CommonAckStatusVo();
		
			
		
			N10Data n10 = new N10Data();
			n10.setAmt_credited_date(CommonClass.getSQLDateFormat(msgBodyMap.get("VALUE_DATE")));
			String time = CommonClass.formatTimeString(msgBodyMap.get("TIME"));
			n10.setAmt_credited_time(time);
			n10.setExport_status(null);
			n10.setHeader_id(headerId);
			n10.setOriginator_Ifsc_Code(msgBodyMap.get("IFSC_ORG_REMITTANCE"));
			n10.setRelate_ref(msgBodyMap.get("RELATED_REF"));
			n10.setTransaction_Loop_RefNo(msgBodyMap.get("TRANS_LOOP_REF_ID"));
			n10.setTransaction_refId(msgBodyMap.get("TRANS_REF_ID"));
			
			common.setStatus("Y");
			common.setTime(time);
			
			obj[0] = n10;
			obj[1] = common;
			
			return obj;
		}
		
		protected Object[] setN09(Long headerId,HashMap<String,String> msgBodyMap){
			
			Object[] obj = new Object[2];
			CommonAckStatusVo common = new CommonAckStatusVo();
			
			N09Data n09 = new N09Data();
			
			n09.setAmt(Double.parseDouble(msgBodyMap.get("AMT")));
			
			n09.setBatch_time(msgBodyMap.get("BATCH_TIME"));
			n09.setBeneficiary_Branch_Ifsc(msgBodyMap.get("BENF_IFSC"));
			n09.setBeneficiary_Cust_Address(msgBodyMap.get("BENF_ADRS"));
			n09.setBeneficiary_customer_account_name(msgBodyMap.get("BENF_ACNT_NAME"));
			n09.setBeneficiary_customer_account_no(msgBodyMap.get("BENF_ACNT_NO"));
			n09.setBeneficiary_customer_account_type(msgBodyMap.get("BENF_ACNT_TYP"));
			n09.setExport_status(null);
			n09.setHeader_id(headerId);
			n09.setOriginal_Value_Date(CommonClass.convertSQLDateFormat(msgBodyMap.get("ORG_VALUE_DATE")));
			n09.setRemittance_date(msgBodyMap.get("REMITTANCE_DATE"));
			n09.setReason_Code(msgBodyMap.get("REASON_CODE"));
			n09.setRejection_Reason(msgBodyMap.get("REJECT_REASON"));
			n09.setRelated_ref(msgBodyMap.get("RELATED_REF"));
			n09.setSender_Branch_Ifsc(msgBodyMap.get("CUST_IFSC"));
			n09.setSender_To_Rec_Info(msgBodyMap.get("SENDR_TO_RCVR_INFO"));
			n09.setTransaction_Loop_No(msgBodyMap.get("TRANS_LOOP_REF_ID"));
			n09.setTransaction_refId(msgBodyMap.get("TRANS_REF_ID"));
			n09.setTot_trans_rejcted(msgBodyMap.get("TOT_NUM_TRANS"));
			n09.setTtl_Amt(msgBodyMap.get("TOT_AMT_REJECTED"));
			n09.setValue_Date(CommonClass.convertSQLDateFormat(msgBodyMap.get("VALUE_DATE")));
			
			common.setStatus("N");
			common.setTime(CommonClass.formatTimeString(null));
			
			obj[0] = n09;
			obj[1] = common;
			
			return obj;
		}
		
		//RTGS Insert
		@SuppressWarnings("static-access")
		protected Object[] setR41(HashMap<String,String> headerMap,HashMap<String,String> msgBodyMap){
			
			Object[] obj =  new Object[2];
			R41Data r41 = new R41Data();
			CommonTransactionDtlsVo common = new CommonTransactionDtlsVo();
			
			
			
			
			r41.setValue_date(commonClass.convertSQLDateFormat(msgBodyMap.get("VALUE_DATE")));
			
			r41.setCurrency(msgBodyMap.get("CURRENCY"));
			
			double amt = Double.parseDouble(msgBodyMap.get("AMT"));
			r41.setAmt(amt);
			
			
			r41.setOrder_customer_account_no(msgBodyMap.get("CUST_ACNT_NO"));
			r41.setOrder_customer_name(msgBodyMap.get("CUST_ACNT_NAME"));
			r41.setOrder_customer_address(msgBodyMap.get("CUST_ADRS"));
			
			r41.setOrdering_institution(headerMap.get("SNDR_IFSC"));
			
			
			r41.setBeneficiary_account_no(msgBodyMap.get("BENF_ACNT_NO"));
			r41.setBeneficiary_customer_name(msgBodyMap.get("BENF_ACNT_NAME"));
				
			r41.setBeneficiary_customer_address(msgBodyMap.get("BENF_ADRS"));
	
			r41.setAccout_with_institution(msgBodyMap.get("ACCNT_WITH_INSTITN"));
			r41.setExport_status(null);
			r41.setHeader_id(null);
			r41.setIntermediary(null);
			r41.setReceiver_correspondent(msgBodyMap.get("RCVR_CORRESPONDENT"));
			r41.setSender_correspondent(msgBodyMap.get("SENDER_CORRESPONDENT"));
			r41.setSender_to_receiver_info(msgBodyMap.get("SNDR_TO_RCVR_INFO"));
			r41.setTransaction_refno(headerMap.get("UTR"));
			
			common.setAccount_no(msgBodyMap.get("CUST_ACNT_NO"));
			common.setAmt(amt);
			common.setBenfAcnt(msgBodyMap.get("BENF_ACNT_NO"));
//			common.setBenfAcntTyp(null);
			common.setBenfName(msgBodyMap.get("BENF_ACNT_NAME"));
			common.setSendingCustEmailMob(null);
			common.setTransId(headerMap.get("UTR"));
			try{
				
				obj[0]=r41;
				obj[1]=common;
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return obj;
		}
		
		@SuppressWarnings("static-access")
		protected Object[] setR42(HashMap<String,String> headerMap,HashMap<String,String> msgBodyMap){
			
			Object[] obj =  new Object[2];
			@SuppressWarnings("unused")
			CommonTransactionDtlsVo common = new CommonTransactionDtlsVo();
			R42Data r42 = new R42Data();
			
			r42.setAccout_with_institution(msgBodyMap.get("ACCNT_WITH_INSTITN"));
			
			double amt = Double.parseDouble(msgBodyMap.get("AMT"));
			r42.setAmt(amt);
			
			r42.setBeneficiary_institution(msgBodyMap.get("ACCNT_WITH_INSTITN"));
			r42.setCurrency(msgBodyMap.get("CURRENCY"));
			r42.setExport_status(null);
			r42.setHeader_id(null);
			r42.setIntermediary(null);
			r42.setOrder_institution(headerMap.get("SNDR_IFSC"));
			r42.setReceiver_correspondent(msgBodyMap.get("RCVR_CORRESPONDENT"));
			r42.setSender_correspondent(msgBodyMap.get("SENDER_CORRESPONDENT"));
			r42.setSender_to_receiver_info(msgBodyMap.get("SNDR_TO_RCVR_INFO"));
			r42.setTransaction_refno(headerMap.get("UTR"));
			
			r42.setValue_date(commonClass.convertSQLDateFormat(msgBodyMap.get("VALUE_DATE")));
			r42.setRelated_ref(null);
			
			
			common.setAccount_no(msgBodyMap.get("CUST_ACNT_NO"));
			common.setAmt(amt);
			common.setBenfAcnt(msgBodyMap.get("BENF_ACNT_NO"));
//			common.setBenfAcntTyp(null);
			common.setBenfName(msgBodyMap.get("BENF_ACNT_NAME"));
			common.setSendingCustEmailMob(null);
			common.setTransId(headerMap.get("UTR"));
			try{
				
				obj[0]=r42;
				obj[1]=common;
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			return obj;
		}
		
		protected Object[] setR09(Long headerId,String UTR_NO ,HashMap<String,String> msgBodyMap){
			
			Object[] obj = new Object[2];
			R09Data r09_data = new R09Data();
			CommonAckStatusVo commonAckStatusVo = new CommonAckStatusVo();
			
			r09_data.setReason_code(msgBodyMap.get("REASON_CODE"));
			String time = CommonClass.formatTimeString(msgBodyMap.get("SETTLMNT_TIME"));
			r09_data.setSettelment_time(time);
			commonAckStatusVo.setTime(time);
			String ackStat = msgBodyMap.get("SETTLD_INDICATOR");
			r09_data.setSettled_indicator(ackStat);
			r09_data.setHeader_id(headerId);
			
			commonAckStatusVo.setStatus(ackStat);		//this status set to update 
														//TransactionDtls and other related table 
			
			
			
			r09_data.setTransaction_refno(UTR_NO);
			
			
			obj[0] = r09_data;
			obj[1] = commonAckStatusVo;
			
			return obj;
		}
		
		protected void addR90(){
			R90Data r90 = new R90Data();
			r90.setAck_indicator(null);
			r90.setExport_status(null);
			r90.setHeader_id(null);
			r90.setReason_code(null);
			r90.setTransaction_refno(null);
			
		}
		
		//common
		
		@SuppressWarnings("static-access")
		public MessageHeader setMessageHeader(HashMap<String,String> headerMap,String transId){
			
			MessageHeader messageHeader = new MessageHeader();
			
			//header id of table is auto increment here
			messageHeader.setHeader_id(nfDao.generateNewId("MessageHeader", "header_id"));
			messageHeader.setBank_app_identifier(headerMap.get("SNDR_BNK_ID"));
			
			int delvry_flg = 0;
			String delvry_flgStr = headerMap.get("DVRY_NOTIFY_FLG");
			if(delvry_flgStr!=null && delvry_flgStr.length() > 0)
				delvry_flg = Integer.parseInt(delvry_flgStr);
			
			messageHeader.setDelivery_flag(delvry_flg);
			messageHeader.setFiller(headerMap.get("FILLER"));
			messageHeader.setIOIdentifier(headerMap.get("IO_ID"));
			messageHeader.setMessage_identifier(headerMap.get("MSG_ID"));
			String msgSubTyp = headerMap.get("MSG_SUBTYPE");
			messageHeader.setMessage_sub_type(msgSubTyp);
			messageHeader.setMessage_type(headerMap.get("MSG_TYPE"));
			messageHeader.setMUR(headerMap.get("MUR"));
			
			int non_delvry_warng_flg = 0;
			String non_delvry_warng_flgStr = headerMap.get("NON_DELV_FLG");
			if(non_delvry_warng_flgStr!=null && non_delvry_warng_flgStr.length() > 0){
				non_delvry_warng_flg = Integer.parseInt(non_delvry_warng_flgStr);
			}
			
			messageHeader.setNon_delivery_warning_flag(non_delvry_warng_flg);
			
			int obsol_period = 0;
			String obsol_periodStr = headerMap.get("OBSOL_PERIOD");
			if(obsol_periodStr != null && obsol_periodStr.length()>0)
				obsol_period = Integer.parseInt(obsol_periodStr);
			
			messageHeader.setObsolescence_period(obsol_period);
			
			int open_notify_flg = 0;
			String open_notify_flgStr = headerMap.get("OPEN_NOTIFY_FLG");
			if(open_notify_flgStr !=null && open_notify_flgStr.length() > 0)
				open_notify_flg = Integer.parseInt(open_notify_flgStr);
			messageHeader.setOpen_notification_flag(open_notify_flg);
			
			messageHeader.setOrginating_time(headerMap.get("ORGIN_TIME"));
			
			String date = headerMap.get("ORGIN_DT");
			if(date !=null && date.length() > 0)
				messageHeader.setOriginating_date(commonClass.convertSQLDateFormat(date));
			
			int possible_dup_flg = 0;
			String possible_dup_flgStr = headerMap.get("POSBL_DUP_EMSN_FLAG");
			if(possible_dup_flgStr != null && possible_dup_flgStr.length() > 0 )
				possible_dup_flg = Integer.parseInt(possible_dup_flgStr);
			
			messageHeader.setPossible_duplicate_flag(possible_dup_flg);
			messageHeader.setReceiver_address(headerMap.get("RCVR_IFSC"));
			
			int rtgs_priority = 0;
			String rtgs_priorityStr = headerMap.get("PRIORITY_FLAG");
			if(rtgs_priorityStr!=null && rtgs_priorityStr.length() > 0)
				rtgs_priority = Integer.parseInt(rtgs_priorityStr);
			
			messageHeader.setRTGS_priority(rtgs_priority);
			messageHeader.setSender_address(headerMap.get("SNDR_IFSC"));
			messageHeader.setSequence_no(headerMap.get("SEQ_NUM"));
			messageHeader.setService_identifier(headerMap.get("SNDR_BNK_ID"));
			
			int test_train_flag = 0;
			String test_train_flagStr = headerMap.get("TEST_TRAIN_FLAG");
			if(test_train_flagStr != null && test_train_flagStr.length() > 0)
				test_train_flag = Integer.parseInt(test_train_flagStr);
			
			messageHeader.setTesting_training_flag(test_train_flag);
			
			if(msgSubTyp.charAt(0)=='N' || msgSubTyp.charAt(0)=='n')
				messageHeader.setTransaction_refno(transId);
			
			if(msgSubTyp.charAt(0)=='R' || msgSubTyp.charAt(0)=='r')
				messageHeader.setUnique_transaction_reference(transId);
		
			return messageHeader;
					
		}
		
		public TransactionDtls setTransactionDtls(HashMap<String,String> headerMap,CommonTransactionDtlsVo common){
			
			TransactionDtls transactionDtls = new TransactionDtls();


			transactionDtls.setTransaction_refId(common.getTransId());
			transactionDtls.setAmt(common.getAmt());
			
			transactionDtls.setAccount_no(common.getAccount_no());
			
			//beneficiary
			transactionDtls.setBeneficiary_account(common.getBenfAcnt());
			transactionDtls.setBeneficiary_account_typ(common.getBenfAcntTyp());
			transactionDtls.setBeneficiary_branch_ifsc(headerMap.get("RCVR_IFSC"));
			transactionDtls.setBeneficiary_name(common.getBenfName());
			transactionDtls.setEmail(common.getSendingCustEmailMob());
			transactionDtls.setIfsc_code(headerMap.get("SNDR_IFSC"));
			transactionDtls.setIn_out_msg_flag("Out");
			transactionDtls.setIs_blocked("0");
			transactionDtls.setMessage_type(headerMap.get("MSG_SUBTYPE"));
			transactionDtls.setTransaction_date(new Date());
			transactionDtls.setTransaction_status("1");
			transactionDtls.setTransaction_time(CommonClass.getCurrentTimeStr());
			
		
//			transactionDtls.setBeneficiary_avail_bal(null);
//			transactionDtls.setBeneficiary_bank(null);
			transactionDtls.setMobile_no(null);
			transactionDtls.setRel_RefId(null);
			transactionDtls.setMessage_subject(headerMap.get("MSG_TYPE"));
			transactionDtls.setAvail_bal(null);
			transactionDtls.setBranch_id(null);
			transactionDtls.setBranch_name(null);
			transactionDtls.setCustomer_id(null);
			
			return transactionDtls;
			
		}
		
		public MessageNotificationDtls setMessageNotificationDtls(String transId,String msgSubType){
			
			MessageNotificationDtls messageNotificationDtls = new MessageNotificationDtls();
			
			messageNotificationDtls.setAck_nack_subtyp(msgSubType);
			messageNotificationDtls.setEmail_notify_status("1");
			messageNotificationDtls.setFlag(1);
//			messageNotificationDtls.setNotify_id(null);  		Auto-increment
			messageNotificationDtls.setSms_notify_status("1");
			messageNotificationDtls.setTransaction_date(null);
			messageNotificationDtls.setTransaction_refno(transId);
			
			return messageNotificationDtls;
		}
}
