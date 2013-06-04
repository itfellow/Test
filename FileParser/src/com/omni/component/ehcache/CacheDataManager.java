/**
 *
 */
package com.omni.component.ehcache;

import java.io.File;
import java.util.ResourceBundle;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.omni.util.PropAccess;

/**
 * This class handles cache data management
 *
 * @author Shinoj Shayin 12/04/2013
 */
public final class CacheDataManager {

	private static final ResourceBundle bundle = com.omni.util.common.PropAccess.getResourceBundle();
	private static String secfilePath = null;
	private static File secConfFile = null;

	private static CacheDataManager cacheDataManager = null;
	private static CacheManager cacheManager = null;
	private static Cache cache = null;

	/**
	 * gets the file path to ehcache.xml as string
	 */
	static {

		secfilePath = bundle.getString("EhCacheConf");

		secConfFile = new File(secfilePath);

	}

	// cache name is readed from the property file
	// cache name written in the property file is same as the cache name written
	// in ehcache.xml
	private CacheDataManager() {

		if (cacheManager == null) {

			System.out.println("Initializing cache...........");

			cacheManager = new CacheManager(secConfFile.toString());

			CacheDataManager.cache = cacheManager.getCache(bundle
					.getString("cacheName"));

			if (CacheDataManager.cache == null) {

				System.out.println("Cache Creation Failed");

			} else {

				System.out.println("Cache created ");

			}

		}
	}

	/**
	 * This method creates both cache and static object of CacheDataManager
	 * class. Name of the Cache is Defined in ehcache.xml and
	 * properties.properties
	 *
	 * @return com.omni.cache.CacheDataManager
	 *
	 * */
	public static CacheDataManager getCacheDataManager() {

		if (cacheDataManager == null)
			cacheDataManager = new CacheDataManager();

		return CacheDataManager.cacheDataManager;
	}

/*	*//**
	 * This method set class objects in java.util.HashMap collection to cache
	 *
	 * @param key
	 *            set the key for cached object
	 * @param dataMap
	 *            set the HashMap object to cache
	 * @exception Exception
	 *
	 * *//*
	public void setCacheDataWithHashMap(String key,
			HashMap dataMap) throws Exception {

		if (cache != null) {

			cache.put(new Element(key, dataMap));

			System.out.println("CacheDataManager: Element with key: \"" + key
					+ "\" has been Added or Updated");

		} else {
			System.out.println("CacheDataManager: Cache not created yet");

		}
	}*/

/*	*//**
	 * This Overloaded method set class objects in java.util.HashMap collection
	 * to cache
	 *
	 * @param key
	 *            set the key for cached object
	 * @param dataMap
	 *            set the HashMap object to cache
	 * @param isSerilaizable
	 *            The boolean value true sets the cacheData to store serializable
	 *            object
	 * @throws Exception
	 *//*
	public void setCacheDataWithHashMap(String key,
			HashMap dataMap, boolean isSerilaizable)
			throws Exception {

		if (cache != null) {

			if (isSerilaizable == true) {

				cache.putWithWriter(new Element(key, dataMap));

				System.out.println("CacheDataManager: Element with key: \""
						+ key + "\" has been Added or Updated (Serilaizable)");

			} else {

				cache.put(new Element(key, dataMap));

				System.out.println("CacheDataManager: Element with key: \""
						+ key + "\" has been Added or Updated");
			}

		} else {

			System.out.println("CacheDataManager: Cache not created yet");

		}
	}*/

	/**
	 * This method set class objects in java.util.HashMap collection to cache
	 *
	 * @param key
	 *            set the key for cached object
	 * @param dataObj
	 *            set the object to cache
	 * @exception Exception
	 *
	 * */
	public void setCacheDataWithObject(String key, Object dataObj)
			throws Exception {

		if (cache != null) {
			cache.put(new Element(key, dataObj));
			System.out.println("CacheDataManager: Element with key: \"" + key
					+ "\" has been Added or Updated");
		} else {
			System.out.println("CacheDataManager: Cache not created yet");

		}
	}


/*	*//**
	 * This Overloaded method set class objects in java.util.HashMap collection
	 * to cache
	 *
	 * @param key
	 *            set the key for cached object
	 * @param dataObj
	 *            set the object to cache
	 * @param isSerializable
	 *            The boolean value true set the cacheData to store as
	 *            serializable object
	 * @throws Exception
	 *//*
	public void setCacheDataWithObject(String key, Object dataObj,
			boolean isSerializable) throws Exception {

		if (cache != null) {
			if (isSerializable == true) {
				cache.putWithWriter(new Element(key, dataObj));
				System.out.println("CacheDataManager: Element with key: \""
						+ key + "\" has been Added or Updated (Serializable");
			} else {
				cache.put(new Element(key, dataObj));
				System.out.println("CacheDataManager: Element with key: \""
						+ key + "\" has been Added or Updated");
			}

		} else {
			System.out.println("CacheDataManager: Cache not created yet");

		}
	}*/

	/**
	 * This method Gets a NonSerializable value from an element in a cache.
	 *
	 * @param key
	 *            This sets the key against which data
	 * @return Object
	 */
	public Object getCacheData(String key) {

		Element element = null;
		Object value = null;

		if (cache != null) {
			element = cache.get(key);

		} else {
			System.out.println("CacheDataManager:Cache not created yet");

		}

		if (element == null) {
			System.out.println("CacheDataManager: Did not found, Element With Key:" + key);

		} else {
			value = element.getObjectValue();

		}
		return value;

	}

/*	*//**
	 * This method Gets a Serializable value from an element in a cache.
	 *
	 * @param key
	 * @return Serializable Object
	 *//*
	@SuppressWarnings("deprecation")
	public Serializable getCacheDataWithSerialization(String key) {
		Element element = null;
		Serializable value = null;

		if (cache != null) {
			element = cache.get(key);

		} else {
			System.out.println("CacheDataManager:Cache not created yet");

		}

		if (element == null) {
			System.out.println("CacheDataManager: The element with key:" + key
					+ "has not been found in cache");

		} else {
			value = element.getValue();

		}
		return value;
	}*/

	/**
	 * This method removes an element from cache
	 *
	 * @param key
	 * @throws Exception
	 */
	public void removeCacheData(String key) throws Exception {
		if (checkInCache(key) == true) {
			cache.remove(key);
		}
	}

	/**
	 * This method is used check whether an element with particular key is
	 * present in the cache
	 *
	 * @param key
	 * @return boolean return's boolean value true if present else it return's
	 *         false
	 * @throws Exception
	 */
	public boolean checkInCache(String key) throws Exception {

		Element element = null;

		if (cache != null) {
			element = cache.get(key);

		} else {
			System.out.println("CacheDataManager: Cache not created yet");

		}

		boolean returnValue = false;

		if (element != null) {
			System.out.println(key + " is in the cache!!!");
			returnValue = true;
		} else {
			System.out.println("CacheDataManager: CacheData Not Found For key: \""
							+ key + "\"");
		}

		return returnValue;
	}

}
