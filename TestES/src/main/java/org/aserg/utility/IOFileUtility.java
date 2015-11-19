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

import com.maxmind.geoip.LookupService;


/**
 * @author Waseem
 * This class is responsible to return the result set on the bases of time
 * and write the time of the last result in the file
 *
 */
public class IOFileUtility {

	/**
	 * 
	 */

	private static final String BASE_PATH = System.getProperty("user.dir")+"/config/state.properties";
	
	public IOFileUtility() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getCountries(String remote_host) {
		String remote_country = "";
		try {

			String dir = "E:/TI/GeoIP.dat";
			LookupService cl = new LookupService(dir, LookupService.GEOIP_MEMORY_CACHE);
			remote_country = cl.getCountry(remote_host).getName();

		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return remote_country;
	}
	
	/**
	 * This Function will write the time in to the file on the bases
	 * @param time Which is basically time of last record that was inserted
	 * @param source Which is basically the DataSource of which we are writing the time e.g MalwareIncident, SipIncident
	 */
	public static void writeTime(String source, String time){
		Properties p = new Properties(); // Will create a properties object for load and store
		FileInputStream input = null;   // Will load all configurations in the memory for updation
		FileOutputStream output = null; // Will provide the property for writing
		/*
		 * This Block will load the configuration file into the memory
		 */
		try {
			input = new FileInputStream(BASE_PATH);
			p.load(input);
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			
			if(input != null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		/*
		 * This block will write the data into the property file
		 */
		try{
			output = new FileOutputStream(BASE_PATH);
			p.setProperty(source, time);
			p.store(output, null);
		} catch(IOException io){
			
			io.printStackTrace();
		} finally {
			
			if(output != null){
				try{
					output.close();
				} catch(IOException io){
					io.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This function will return the time on the bases of datasource parameter
	 * @param source
	 * @return time which is the time of last record that was inserted
	 */
	public static String readTime(String source){
		
		BufferedReader br;
		Properties p = new Properties();
		try {
			br = new BufferedReader(new FileReader(BASE_PATH));
			p.load(br);
		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p.getProperty(source);
	}

}
