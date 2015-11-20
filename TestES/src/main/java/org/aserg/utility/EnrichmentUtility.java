/**
 * 
 */
package org.aserg.utility;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;

import org.aserg.model.Origin;
import org.elasticsearch.common.geo.GeoPoint;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.maxmind.geoip.regionName;
import com.maxmind.geoip.timeZone;

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

	public static Origin getOrigin(String remote_host) {
		try {

			String dir = "E:/TI/GeoLiteCity.dat";
			LookupService cl = new LookupService(dir, LookupService.GEOIP_MEMORY_CACHE);
			Location loc = cl.getLocation(remote_host);
			if(loc != null){
				String code3;
				try{
					code3 = new Locale("en",loc.countryCode).getISO3Country();
				} catch(MissingResourceException e){
					code3 = "";
				}
				
				return new Origin( loc.countryName ,code3, loc.city,new GeoPoint(loc.latitude + "," +loc.longitude));
			}
			cl.close();
			

		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return null;
	}

}
