package com.omni.oesb.common;
/**
 * ServiceMaster is the Interface of all the service class.
 * service classes controls the adapter.
 * 
 * @author shinoj shayin 18/05/2013
 *
 */
public interface ServiceMaster {

	void startService();
	
	void stopService();
	
	boolean isServiceRunning();
	
}
