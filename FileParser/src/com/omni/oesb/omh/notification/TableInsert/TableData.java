package com.omni.oesb.omh.notification.TableInsert;

import java.util.Date;
import java.util.HashMap;

import com.omni.oesb.fileparser.dao.FileParserDao;
import com.omni.oesb.notification.TableInsert.vo.CommonAckStatusVo;
import com.omni.oesb.notification.TableInsert.vo.CommonTransactionDtlsVo;
import com.omni.oesb.notification.Util.ParserUtil;
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
	
	//NEFT Insert
		@SuppressWarnings("static-access")
		protected Object[] setN06(HashMap<String,String> headerMap,HashMap<String,String> msgBodyMap){
			
			Object tablePojo[] = new Object[2];
			
			CommonTransactionDtlsVo common = new CommonTransactionDtlsVo();
			N06Data n06_data = new N06Data();
			String currencyStr;
			String currencyAmt [];
			
			//amount
			currencyStr=null;
			currencyAmt=null;
			currencyStr = msgBodyMap.get("4038");
			currencyAmt = parserUtil.parseAmount(currencyStr);
			if(currencyAmt[1]!=null && currencyAmt[1].length() > 0)
				n06_data.setAmt(Double.parseDouble(currencyAmt[1]));
			
			//sum of current amt
			currencyStr=null;
			currencyAmt=null;
			currencyStr = msgBodyMap.get("4063");
			currencyAmt = parserUtil.parseAmount(currencyStr);
			double amt = 0;
			if(currencyAmt[1]!=null && currencyAmt[1].length() > 0){
				amt = Double.parseDouble(currencyAmt[1]);
				n06_data.setTtl_amt(amt);
			}
			n06_data.setBatch_Time(msgBodyMap.get("3535"));
			
			String benf_branch_ifsc = headerMap.get("RCVR_IFSC");
			n06_data.setBen_Branch_IFSC(benf_branch_ifsc);
			
			String benf_Cust_Acc_adrs = msgBodyMap.get("5565");
			n06_data.setBen_Cust_Acc_Address(benf_Cust_Acc_adrs);
			
			String benf_Acc_Name = msgBodyMap.get("6081");
			n06_data.setBen_Cust_Acc_Name(benf_Acc_Name);
			
			String benf_cust_acc = msgBodyMap.get("6061");
			n06_data.setBen_Cust_Acc_No(benf_cust_acc);
			
			String benf_Cust_Acc_typ = msgBodyMap.get("6310");
			n06_data.setBen_Cust_Acc_Type(benf_Cust_Acc_typ);
			
			n06_data.setExport_status(null);
			n06_data.setHeader_id(null);
			n06_data.setOriginator_Of_Remittance(msgBodyMap.get("7002"));
			n06_data.setRemittance_date(null);
			n06_data.setSender_Acc_Name(msgBodyMap.get("6091"));
			String sendingBranchIfsc = headerMap.get("SNDR_IFSC");
			n06_data.setSending_Branch_IFSC(sendingBranchIfsc);
			
			String acnt_no = msgBodyMap.get("6021");
			n06_data.setSending_Cust_Acc_No(acnt_no);
			
			n06_data.setSending_Cust_Acc_Type(msgBodyMap.get("6305"));
			
			String emailMob = msgBodyMap.get("5629");
			n06_data.setSending_Cust_EmailMob(emailMob);
			n06_data.setTotal_Transactions(Integer.parseInt(msgBodyMap.get("1106")));
			n06_data.setTransaction_Loop_RefNo(msgBodyMap.get("2020_1"));
			
			String transId = msgBodyMap.get("2020");
										//setting transId globally so that transactionDtls and messageNotification table
																	//can get the transId
			if(transId != null && transId.length() > 0)				
				n06_data.setTransaction_RefNo(transId);
			
			String date = msgBodyMap.get("3380");
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
		
		protected Object[] setN10(HashMap<String,String> msgBodyMap){
			
			Object[] obj = new Object[2];
			CommonAckStatusVo common = new CommonAckStatusVo();
			String date = null;
			String time = null;
			
			String AmtCredited_Date_Time = msgBodyMap.get("3501");
			if(AmtCredited_Date_Time !=null && AmtCredited_Date_Time.length() > 0){
				date = AmtCredited_Date_Time.substring(0,8);
				time = AmtCredited_Date_Time.substring(7,14);
			}
			N10Data n10 = new N10Data();
			n10.setAmt_credited_date(CommonClass.getSQLDateFormat(date));
			time = CommonClass.formatTimeString(time);
			n10.setAmt_credited_time(time);
			n10.setExport_status(null);
			n10.setHeader_id(null);
			n10.setOriginator_Ifsc_Code(msgBodyMap.get("5518"));
			n10.setRelate_ref(msgBodyMap.get("2006"));
			n10.setTransaction_Loop_RefNo(msgBodyMap.get("2020_1"));
			n10.setTransaction_refId(msgBodyMap.get("2020"));
			
			common.setStatus("Y");
			common.setTime(time);
			
			obj[0] = n10;
			obj[1] = common;
			
			return obj;
		}
		
		protected Object[] setN09(HashMap<String,String> msgBodyMap){
			
			Object[] obj = new Object[2];
			CommonAckStatusVo common = new CommonAckStatusVo();
			
			N09Data n09 = new N09Data();
			
			String []currency_amt =parserUtil.parseAmount(msgBodyMap.get("4038"));
			if(currency_amt[1]!=null)
				n09.setAmt(Double.parseDouble(currency_amt[1]));
			
			n09.setBatch_time(msgBodyMap.get("3535"));
			n09.setBeneficiary_Branch_Ifsc(msgBodyMap.get("5569"));
			n09.setBeneficiary_Cust_Address(msgBodyMap.get("5565"));
			n09.setBeneficiary_customer_account_name(msgBodyMap.get("6081"));
			n09.setBeneficiary_customer_account_no(msgBodyMap.get("6061"));
			n09.setBeneficiary_customer_account_type(msgBodyMap.get("6310"));
			n09.setExport_status(null);
			n09.setHeader_id(null);
			n09.setOriginal_Value_Date(CommonClass.convertSQLDateFormat(msgBodyMap.get("3381")));
			n09.setRemittance_date(msgBodyMap.get("3375"));
			n09.setReason_Code(msgBodyMap.get("6346"));
			n09.setRejection_Reason(msgBodyMap.get("6366"));
			n09.setRelated_ref(null);
			n09.setSender_Branch_Ifsc(msgBodyMap.get("5756"));
			n09.setSender_To_Rec_Info(msgBodyMap.get("7495"));
			n09.setTransaction_Loop_No(msgBodyMap.get("2020_1"));
			n09.setTransaction_refId(msgBodyMap.get("2020"));
			n09.setTtl_Trans_No(msgBodyMap.get("5185"));
			n09.setTtl_Amt(msgBodyMap.get("4115"));
			n09.setValue_Date(CommonClass.convertSQLDateFormat(msgBodyMap.get("3380")));
			
			common.setStatus("N");
			common.setTime(CommonClass.formatTimeString(null));
			
			obj[0] = n09;
			obj[1] = common;
			
			return obj;
		}
		
		//RTGS Insert
		@SuppressWarnings("static-access")
		protected Object[] setR41(String UTR_NO,HashMap<String,String> msgBodyMap){
			
			Object[] obj =  new Object[2];
			R41Data r41 = new R41Data();
			CommonTransactionDtlsVo common = new CommonTransactionDtlsVo();
			
			String dateAndCurrency = msgBodyMap.get("4488");
			
			String date = dateAndCurrency.substring(0,8);
			
			if(date != null && date.length() > 0)
				r41.setValue_date(commonClass.convertSQLDateFormat(date));
			
			String currenyStr = dateAndCurrency.substring(8);
			
			String currencyAmt [] = parserUtil.parseAmount(currenyStr);
			
			if(currencyAmt[0] != null && currencyAmt[0].length()> 0)
				r41.setCurrency(currencyAmt[0]);
			
			Double amt = 0.0;
			if(currencyAmt[1] != null && currencyAmt[1].length() > 0){
				amt = Double.parseDouble(currencyAmt[1]);
				r41.setAmt(amt);
			}
			
			String order_CustDtls[] = parserUtil.parserOrderCustomerForR41(msgBodyMap.get("5500"));
			
			r41.setOrder_customer_account_no(order_CustDtls[0].trim());
			r41.setOrder_customer_name(order_CustDtls[1]);
			r41.setOrder_customer_address(order_CustDtls[2].trim()+", "+order_CustDtls[3].trim());
			
			String senderIfsc = msgBodyMap.get("5517");
			r41.setOrdering_institution(senderIfsc);
			
			String benf_CustDtls[] = parserUtil.parseBenfCustomerForR41(msgBodyMap.get("5561"));
			
			if(benf_CustDtls.length == 4){
				r41.setBeneficiary_account_no(benf_CustDtls[0]);
				r41.setBeneficiary_customer_name(benf_CustDtls[1]);
				
				String benfAdrs = benf_CustDtls[2]+", "+benf_CustDtls[3];
				
				if(benfAdrs!=null && benfAdrs.length() > 0){
					r41.setBeneficiary_customer_address(benfAdrs);
				}
			}
			else if(benf_CustDtls.length == 2){
				r41.setBeneficiary_account_no(benf_CustDtls[0]);
				
				String benfAdrs = benf_CustDtls[1];
				
				if(benfAdrs!=null && benfAdrs.length() > 0){
					r41.setBeneficiary_customer_address(benfAdrs);
				}
			}
			
			r41.setAccout_with_institution(msgBodyMap.get("6516"));
			r41.setCharges_dtls(null);
			r41.setExport_status(null);
			r41.setHeader_id(null);
			r41.setIntermediary(null);
			r41.setPayment_dtls(null);
			r41.setReceiver_correspondent(msgBodyMap.get("5526"));
			r41.setSender_correspondent(msgBodyMap.get("6717"));
			r41.setSender_to_receiver_info(msgBodyMap.get("7495"));
			r41.setTransaction_refno(UTR_NO);
			
			common.setAccount_no(order_CustDtls[0]);
			common.setAmt(amt);
			common.setBenfAcnt(benf_CustDtls[0]);
			common.setBenfAcntTyp(null);
			common.setBenfName(benf_CustDtls[1]);
			common.setSendingCustEmailMob(null);
			common.setTransId(UTR_NO);
			try{
				
				obj[0]=r41;
				obj[1]=common;
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return obj;
		}
		
		protected Object[] setR42(String UTR_NO,HashMap<String,String> msgBodyMap){
			
			Object[] obj =  new Object[2];
			@SuppressWarnings("unused")
			CommonTransactionDtlsVo common = new CommonTransactionDtlsVo();
			R42Data r42 = new R42Data();
			
			r42.setAccout_with_institution(msgBodyMap.get("6516"));
			
			String date_currency_amt = msgBodyMap.get("4488");
			String date = null;
			String currency = null;
			String amt = null;
			if(date_currency_amt!=null && date_currency_amt.length()>0){
				date =  date_currency_amt.substring(0,8);
				String []currency_amt = parserUtil.parseAmount(date_currency_amt.substring(8,date_currency_amt.length()));
				currency = currency_amt[0];
				amt = currency_amt[1];
			}
			if(amt!=null)
				r42.setAmt(Double.parseDouble(amt));
			
			r42.setBeneficiary_institution(msgBodyMap.get("6521"));
			r42.setCurrency(currency);
			r42.setExport_status(null);
			r42.setHeader_id(null);
			r42.setIntermediary(null);
			r42.setOrder_institution(msgBodyMap.get("5517"));
			r42.setReceiver_correspondent(msgBodyMap.get("5526"));
			r42.setSender_correspondent(msgBodyMap.get("6717"));
			r42.setSender_to_receiver_info(null);
			r42.setTransaction_refno(UTR_NO);
			if(date!=null)
				r42.setValue_date(CommonClass.convertSQLDateFormat(date));
			r42.setRelated_ref(null);
			
			return obj;
		}
		
		protected Object[] setR09(String UTR_NO , HashMap<String,String> msgBodyMap){
			
			Object[] obj = new Object[2];
			R09Data r09_data = new R09Data();
			CommonAckStatusVo commonAckStatusVo = new CommonAckStatusVo();
			
			r09_data.setReason_code(msgBodyMap.get("6346"));
			String time = CommonClass.formatTimeString(msgBodyMap.get("3525"));
			r09_data.setSettelment_time(time);
			commonAckStatusVo.setTime(time);
			String ackStat = msgBodyMap.get("6450");
			r09_data.setSettled_indicator(ackStat);
			
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
			
			transactionDtls.setTransCompleteDate(null);
			transactionDtls.setTransCompTime(null);
			transactionDtls.setBeneficiary_avail_bal(null);
			transactionDtls.setBeneficiary_bank(null);
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
