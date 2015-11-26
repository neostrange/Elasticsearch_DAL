package org.aserg.model;

import org.elasticsearch.common.geo.GeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Information about the origin of the attack
 * 
 * @author YG
 *
 */
public class Origin {

	private static Logger log = LoggerFactory.getLogger(Origin.class);

	/**
	 * @param srcCountry
	 * @param srcCountryCode
	 * @param city
	 * @param geoPoint
	 */
	public Origin(String srcCountry, String srcCountryCode, String city, GeoPoint geoPoint) {
		super();
		log.trace("Create new Origin instance where srcCountry [{}], srcCountryCode [{}]", srcCountry, srcCountryCode);
		this.srcCountry = srcCountry;
		this.srcCountryCode = srcCountryCode;
		this.city = city;
		this.geoPoint = geoPoint;
	}

	/**
	 * The country from where the attack originated.
	 */
	private String srcCountry;
	/**
	 * The country code from where the attack originated.
	 */
	private String srcCountryCode;
	/**
	 * The city from where the attack originated.
	 */
	private String city;
	/**
	 * The geolocation information regarding the sourceIP.
	 */
	private GeoPoint geoPoint;

	public String getSrcCountry() {
		log.trace("Get srcCountry, returns [{}]", srcCountry);
		return srcCountry;
	}

	public void setSrcCountry(String srcCountry) {
		log.trace("Set srcCountry to [{}]", srcCountry);
		this.srcCountry = srcCountry;
	}

	public String getSrcCountryCode() {
		log.trace("Get srcCountryCode, returns [{}]", srcCountryCode);
		return srcCountryCode;
	}

	public void setSrcCountryCode(String srcCountryCode) {
		log.trace("Set srcCountryCode to [{}]", srcCountryCode);
		this.srcCountryCode = srcCountryCode;
	}

	public String getCity() {
		log.trace("Get city, returns [{}]", city);
		return city;
	}

	public void setCity(String city) {
		log.trace("Set city to [{}]", city);
		this.city = city;
	}

	public GeoPoint getGeoPoint() {
		log.trace("Get geoPoint, returns GeoPoint with lat [{}], lon [{}]", geoPoint.lat(), geoPoint.lon());
		return geoPoint;
	}

	public void setGeoPoint(GeoPoint geolocation) {
		log.trace("Set geoPoint to lat [{}], lon [{}]", geolocation.lat(), geolocation.lon());
		this.geoPoint = geolocation;
	}

}
