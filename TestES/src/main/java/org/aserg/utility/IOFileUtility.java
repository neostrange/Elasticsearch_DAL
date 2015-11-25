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
 * @author Waseem This class is responsible to return the result set on the
 *         bases of time and write the time of the last result in the file
 *
 */
public class IOFileUtility {

	private static Logger log = LoggerFactory.getLogger(IOFileUtility.class);
	// static constants for properties file path
	public static final String STATE_PATH = System.getProperty("user.dir") + "/config/state.properties";
	public static final String ARCHIVAL_PATH = System.getProperty("user.dir") + "/config/archivalAgent.properties";

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
		}
		else{
			log.warn("Value to be written to property [{}] was NULL, so it remains unchanged.", prop);
		}
	}

	public static String readProperty(String source, String propFile) {
		log.debug("Read property [{}], from property file [{}]", source, propFile);
		BufferedReader br;
		Properties p = new Properties();
		try {
			br = new BufferedReader(new FileReader(propFile));
			p.load(br);
		} catch (FileNotFoundException e) {
			log.error("Error occurred while trying to read from property file [{}] ", propFile, e);
		} catch (IOException e) {
			log.error("Error occurred while trying to read from property file [{}] ", propFile, e);
		}
		return p.getProperty(source);
	}

}
