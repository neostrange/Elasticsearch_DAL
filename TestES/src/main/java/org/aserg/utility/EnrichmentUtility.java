/**
 * 
 */
package org.aserg.utility;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import org.aserg.model.Origin;
import org.elasticsearch.common.geo.GeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

public class EnrichmentUtility {

	private static Logger log = LoggerFactory.getLogger(EnrichmentUtility.class);
	private static String dir = IOFileUtility.readProperty("GEOIP_FILE", IOFileUtility.ARCHIVAL_PATH);
	private static LookupService cl = null;

	public static void initLookupService() {
		try {
			cl = new LookupService(dir, LookupService.GEOIP_MEMORY_CACHE);

		} catch (IOException e) {
			log.error("Error occurred while trying to get Origin from source IP [{}] ", e);
		}
	}

	public static void closeLookupService() {
		if (cl != null)
			cl.close();
	}

	public static Origin getOrigin(String remote_host) {
		log.info("Get origin, where source IP is [{}] ", remote_host);
		Location loc = cl.getLocation(remote_host);
		if (loc != null) {
			String code3;
			try {
				code3 = new Locale("en", loc.countryCode).getISO3Country();
			} catch (MissingResourceException e) {
				log.debug("Unable to getISO3Country code where source IP is [{}] ", remote_host);
				code3 = "";
			}
			log.info("Origin created successfully");
			return new Origin(loc.countryName, code3, loc.city, new GeoPoint(loc.latitude + "," + loc.longitude));
		}
		log.warn("Origin was not created successfully, possibly because GEOIP lookup didn't yield expected response");
		return null;
	}

}
