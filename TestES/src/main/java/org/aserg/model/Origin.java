package org.aserg.model;

import org.elasticsearch.common.geo.GeoPoint;

/**
 * Information about the origin of the attack
 * 
 * @author YG
 *
 */
public class Origin {
	
	/**
	 * @param srcCountry
	 * @param srcCountryCode
	 * @param city
	 * @param geoPoint
	 */
	public Origin(String srcCountry, String srcCountryCode, String city, GeoPoint geoPoint) {
		super();
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
		return srcCountry;
	}
	public void setSrcCountry(String srcCountry) {
		this.srcCountry = srcCountry;
	}
	public String getSrcCountryCode() {
		return srcCountryCode;
	}
	public void setSrcCountryCode(String srcCountryCode) {
		this.srcCountryCode = srcCountryCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public GeoPoint getGeoPoint() {
		return geoPoint;
	}
	public void setGeoPoint(GeoPoint geolocation) {
		this.geoPoint = geolocation;
	}
	


}
