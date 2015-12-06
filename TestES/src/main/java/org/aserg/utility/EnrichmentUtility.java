package org.aserg.utility;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Locale;
import java.util.MissingResourceException;

import org.apache.commons.lang3.text.WordUtils;
import org.aserg.model.Origin;
import org.elasticsearch.common.geo.GeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maxmind.db.Reader.FileMode;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;

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
	 * The Maxmind GeoLite2-City database file
	 */
	private static File geoCityFile = new File(System.getProperty("user.dir") + "/resources/GeoLite2-City.mmdb");

	/**
	 * The Maxmind database reader
	 */
	private static DatabaseReader reader;
	/**
	 * The Maxmind City Response, for getting data from GeoLite City database
	 */
	private static CityResponse response = null;
	/**
	 * The Origin created using Maxmind
	 */
	private static Origin org;
	/**
	 * The Maxmind Location
	 */
	private static Location loc;

	// Static block for instantiating GeoLite database reader
	public static void initMaxmindDB(){
		try {
			reader = new DatabaseReader.Builder(geoCityFile).fileMode(FileMode.MEMORY).build();
		} catch (IOException e) {
			log.error("Error occurred while trying to instantiate Maxmind DB reader", e);
		}
	}

	/**
	 * Function for looking geolocation information against a specified IP
	 * 
	 * @param srcIP
	 *            the IP for which geolocation information is required
	 * @return the Origin created from the information gained via lookup
	 */
	public static Origin getOrigin(String srcIP) {
		org = null;
		log.info("Get origin, where source IP is [{}] ", srcIP);
		try {
			response = reader.city(InetAddress.getByName(srcIP));
			loc = response.getLocation();
		} catch (IOException e)
		{
			log.error("Error occurred while trying to read ", e);
			
		}catch(GeoIp2Exception e1) {
			log.warn("Origin was not created successfully, possibly because GeoLite lookup didn't find data for IP [{}]",
					srcIP);
		}
		if (response != null) {
			String code3;
			try {
				code3 = new Locale("en", response.getCountry().getIsoCode()).getISO3Country();
			} catch (MissingResourceException | NullPointerException e) {
				log.debug("Unable to getISO3Country code where source IP is [{}] ", srcIP);
				code3 = "";
			}
			try {
				org = new Origin(WordUtils.capitalizeFully(response.getCountry().getName()), code3.toUpperCase(),
						response.getCity().getName(),
						// check if loc is not null
						loc.getLatitude() != null ? new GeoPoint(loc.getLatitude() + "," + loc.getLongitude()) : null);

				log.info("Origin created successfully");
			} catch (NumberFormatException e) {
				log.error("Error occurred while trying to convert string values to GeoPoint", e);
			}
		} 
		// try {
		// reader.close();
		// } catch (IOException e) {
		// log.error("Error occurred while trying to close Maxmind DB", e);
		// }
		return org;
	}

}
