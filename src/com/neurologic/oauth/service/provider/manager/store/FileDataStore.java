/**
 * 
 */
package com.neurologic.oauth.service.provider.manager.store;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neurologic.exception.StoreException;

/**
 * @author Buhake Sindi
 * @since 07 September 2011
 *
 */
public abstract class FileDataStore<T extends StoreData> extends AbstractDataStore<T> {

	private String directory = ".";
	private String fileName;
	protected final Map<String, T> fileDataMap = new HashMap<String, T>();
	
	/**
	 * @return the directory
	 */
	public String getDirectory() {
		return directory;
	}

	/**
	 * @param directory the directory to set
	 */
	public void setDirectory(String directory) {
		this.directory = directory;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.DataStore#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String id) throws StoreException {
		// TODO Auto-generated method stub
		return fileDataMap.containsKey(id);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.DataStore#keys()
	 */
	@Override
	public String[] keys() throws StoreException {
		// TODO Auto-generated method stub
		List<String> keyList = new ArrayList<String>();
		for (String key : fileDataMap.keySet()) {
			keyList.add(key);
		}
		
		return keyList.toArray(new String[keyList.size()]);
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.DataStore#size()
	 */
	@Override
	public int size() throws StoreException {
		// TODO Auto-generated method stub
		return fileDataMap.size();
	}

	/* (non-Javadoc)
	 * @see com.neurologic.oauth.service.provider.manager.store.AbstractDataStore#fetch(java.lang.String)
	 */
	@Override
	protected T fetch(String id) throws StoreException {
		// TODO Auto-generated method stub
		return fileDataMap.get(id);
	}

	protected final File directory() {
		if (directory == null) {
			return null;
		}
		
		File directoryFile = new File(directory);
		if (directoryFile != null) {
			if (!directoryFile.exists() || !directoryFile.isDirectory()) {
				directoryFile.delete();
				directoryFile.mkdirs();
			}
		}
		
		return directoryFile;
	}
	
	public abstract void load() throws FileNotFoundException, IOException;
}
