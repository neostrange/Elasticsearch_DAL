package org.aserg.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Properties;

public class SqlUtility {
	final static String BASE_PATH = System.getProperty("user.dir")+"/config/Query.properties";
	
	public SqlUtility() {
	}
	
	public static Properties getproperty(){
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
		return p;	
	}
	
	public ResultSet getResultSet(String Query){
		return null;
	}
	public static void main (String args[]){
		System.out.print(SqlUtility.getproperty().getProperty("MALWARE_INCIDENT_QUERY"));
	}
				
}
