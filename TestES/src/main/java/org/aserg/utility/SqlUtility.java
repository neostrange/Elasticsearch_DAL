package org.aserg.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SqlUtility {
	final static String BASE_PATH = System.getProperty("user.dir")+"/config/";
	Properties p = new Properties();
	
	public SqlUtility() {
	}
	
	public Properties getproperty(String config){
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(config));
			p.load(br);
		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;	
	}
	public static void main (String args[]){
		System.out.print(new SqlUtility().getproperty(BASE_PATH+"Query.properties").getProperty("MALWARE_INCIDENT_QUERY"));
	}
				
}
