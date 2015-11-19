/**
 * 
 */
package org.aserg.utility;

import java.io.IOException;

import com.maxmind.geoip.LookupService;

/**
 * @author Waseem
 *
 */
public class EnrichmentUtility {

	/**
	 * 
	 */
	public EnrichmentUtility() {
		// TODO Auto-generated constructor stub
	}

	public static String getCountry(String remote_host) {
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

}
