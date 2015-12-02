/**
 * 
 */
package org.aserg.utility;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for reading from and writing to the configuration
 * properties file.
 *
 */
public class IOFileUtility {

	/**
	 * This variable will provide the logging of the complete class
	 */
	private static Logger log = LoggerFactory.getLogger(IOFileUtility.class);
	/**
	 * Static constant for state properties file path
	 */
	public static final String STATE_PATH = System.getProperty("user.dir") + "/config/state.properties";
	/**
	 * Static constant for archival agent properties file path
	 */
	public static final String ARCHIVAL_PATH = System.getProperty("user.dir") + "/config/archivalAgent.properties";
	/**
	 * Static constant for elasticsearch properties file path
	 */
	public static final String ES_PATH = System.getProperty("user.dir") + "/config/es.properties";

	/**
	 * This function loads the properties into the memory and then updates the
	 * specified property with given value, before writing back into properties
	 * file
	 * 
	 * @param prop
	 *            the property that needs to be updated
	 * @param value
	 *            the new value for the property
	 * @param propFile
	 *            the filename where this property needs to be changed
	 */
	public static void writeProperty(String prop, String value, String propFile) {
		Properties p = new Properties(); // Will create a properties object for
											// load and store
		FileInputStream input = null; // Will load all configurations in the
										// memory for updation
		FileOutputStream output = null; // Will provide the property for writing
		if (value != null) {
			log.debug("Write value [{}], to property [{}] in file [{}]", value, prop, propFile);
			/*
			 * This Block will load the configuration file into the memory
			 */

			try {
				input = new FileInputStream(propFile);
				p.load(input);
			} catch (IOException e) {
				log.error("Error occurred while trying to load properties file [{}] ", propFile, e);
			} finally {

				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						log.error("Error occurred while trying to close FileInputStream ", e);
					}
				}
			}
			/*
			 * This block will write the data into the property file
			 */
			try {
				output = new FileOutputStream(propFile);
				p.setProperty(prop, value);
				p.store(output, null);
			} catch (IOException io) {
				log.error("Error occurred while trying to write to property file [{}]", propFile, io);
			} finally {

				if (output != null) {
					try {
						output.close();
					} catch (IOException io) {
						log.error("Error occurred while trying to close FileOutputStream ", io);
					}
				}
			}
			log.info("Successfully written value [{}], to property [{}] ", value, prop);
		} else {
			log.warn("Value to be written to property [{}] was NULL, so it remains unchanged.", prop);
		}
	}

	/**
	 * This function reads the value of a certain property from a specified
	 * property file
	 * 
	 * @param prop
	 *            the property to be read
	 * @param propFile
	 *            the file path from where the property will be read
	 * @return
	 */
	public static String readProperty(String prop, String propFile) {
		log.debug("Read property [{}], from property file [{}]", prop, propFile);
		BufferedReader br = null;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(propFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Properties p = new Properties();
		try {
			br = new BufferedReader(fileReader);
			p.load(br);
		} catch (FileNotFoundException e) {
			log.error("Error occurred while trying to read from property file [{}] ", propFile, e);
		} catch (IOException e) {
			log.error("Error occurred while trying to read from property file [{}] ", propFile, e);
		} finally {

			if (br != null) {
				try {
					fileReader.close();
					br.close();
				} catch (IOException e) {
					log.error("Error occurred while trying to close Buffered Reader / File Reader", e);
				}
			}
		}
		return p.getProperty(prop);
	}

}
