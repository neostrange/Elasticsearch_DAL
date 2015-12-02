/**
 * 
 */
package org.aserg.utility;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;

import org.apache.commons.lang3.text.WordUtils;
import org.aserg.model.Origin;
import org.elasticsearch.common.geo.GeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

/**
 * This class contains functionality to enrich data, which at the moment is
 * restricted to geolocation information, mostly.
 *
 */
public class EnrichmentUtility {

	/**
	 * The logger for this class
	 */
	private static Logger log = LoggerFactory.getLogger(EnrichmentUtility.class);
	/**
	 * The path where the Maxmind GEOIP data file is stored
	 */
	private static String dir = IOFileUtility.readProperty("GEOIP_FILE", IOFileUtility.ARCHIVAL_PATH);
	/**
	 * The Maxmind GeoIP lookup service
	 */
	private static LookupService cl = null;
	/**
	 * The location associated with any given IP
	 */
	static Location loc = null;

	// Static block for instantiating LookupService
	static {
		try {
			cl = new LookupService(dir, LookupService.GEOIP_MEMORY_CACHE);

		} catch (IOException e) {
			log.error("Error occurred while trying to get Origin from source IP [{}] ", e);
		}
	}

	/**
	 * Function for looking up the geolocation information against a specified
	 * IP
	 * 
	 * @param srcIP
	 *            the IP for which geolocation information is required
	 * @return the Origin created from the information gained via lookup
	 */
	public static Origin getOrigin(String srcIP) {
		log.info("Get origin, where source IP is [{}] ", srcIP);
		loc = cl.getLocation(srcIP);
		if (loc != null) {
			String code3;
			try {
				code3 = new Locale("en", loc.countryCode).getISO3Country();
			} catch (MissingResourceException e) {
				log.debug("Unable to getISO3Country code where source IP is [{}] ", srcIP);
				code3 = "";
			}
			log.info("Origin created successfully");
			cl.close();
			return new Origin(WordUtils.capitalizeFully(loc.countryName), code3.toUpperCase(), loc.city,
					new GeoPoint(loc.latitude + "," + loc.longitude));
		}
		log.warn("Origin was not created successfully, possibly because GEOIP lookup didn't find data for IP [{}]",
				srcIP);
		return null;
	}

}
