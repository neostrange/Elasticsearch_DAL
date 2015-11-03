package org.aserg.dal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aserg.model.Download;
import org.aserg.model.VirusTotal;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.maxmind.geoip.LookupService;

public class EsData {
	private static final String driver = "org.sqlite.JDBC";
	private static final String url = "jdbc:sqlite:/C:/Users/TI/Downloads/logsql62_2.sqlite";

	BulkRequestBuilder bulkRequest;
	private Connection con = null;
	private TransportClient transportClient = null;

	/* Getting Connections */
	public void initConnection() {
		if (con == null) {
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void initElasticSearchConnection() {
		if (transportClient == null) {
			Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch").build();
			transportClient = new TransportClient(settings);
			transportClient = transportClient.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
		}
	}

	public String getCountries(String remote_host) {
		String remote_country = "";
		try {

			String dir = "D:/GeoIP.dat";
			LookupService cl = new LookupService(dir, LookupService.GEOIP_MEMORY_CACHE);
			remote_country = cl.getCountry(remote_host).getName();

		} catch (IOException e) {
			System.out.println("IO Exception");
		}
		return remote_country;
	}

	public void getData() {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					" select connections.connection, connection_transport, connection_protocol, connection_type, connection_datetime, connection_root, connection_parent, local_host, local_port, remote_host, remote_port, download_url, downloads.download_md5_hash, virustotals.virustotal, virustotalscans.virustotalscan_scanner, virustotalscans.virustotalscan_result from downloads left join connections on downloads.connection = connections.connection left join (virustotalscans left join virustotals on virustotalscans.virustotal=virustotals.virustotal ) virustotals on virustotals.virustotal_md5_hash=downloads.download_md5_hash;");
			String hash_cmp = rs.getString("download_md5_hash");
			int count = 1;
			HashMap<String, String> vtc = new HashMap<String, String>();
			bulkRequest = transportClient.prepareBulk();
			while (rs.next()) {
				String conn = rs.getString("connection");
				String type = rs.getString("connection_type");
				String transport = rs.getString("connection_transport");
				String protocol = rs.getString("connection_protocol");
				String datetime = rs.getString("connection_datetime");
				String root = rs.getString("connection_root");
				String parent = rs.getString("connection_parent");
				String host = rs.getString("local_host");
				String port = rs.getString("local_port");
				String remotehost = rs.getString("remote_host");
				String remoteport = rs.getString("remote_port");
				String remotecountry = getCountries(rs.getString("remote_host"));
				String downurl = rs.getString("download_url");
				String hash = rs.getString("download_md5_hash");
				while (hash_cmp.equals(hash) == true && rs.next()) {
					vtc.put(rs.getString("virustotalscan_scanner"), rs.getString("virustotalscan_result"));
					hash_cmp = rs.getString("download_md5_hash");
					rs.next();
				}

				addData(conn, type, transport, protocol, datetime, root, parent, host, port, remotehost, remoteport,
						remotecountry, downurl, hash, vtc);
				System.out.println(count++);
			}
			if (bulkRequest.numberOfActions() > 0) {
				bulkRequest.execute();
				bulkRequest = transportClient.prepareBulk();
			}
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	public void addData(String conn, String type, String transport, String protocol, String datetime, String root,
			String parent, String host, String port, String remotehost, String remoteport, String country,
			String downurl, String hash, HashMap<String, String> vtc) throws IOException {

		Download download = new Download();
		download.setDownload_url(downurl);
		VirusTotal vtResult = new VirusTotal();
		vtResult.setVirustotal_md5_hash(hash);
		vtResult.setVtResults((HashMap<String, String>) vtc);
		download.setVtResult(vtResult);
		
		org.aserg.model.Connection c = new org.aserg.model.Connection(download, conn, type, transport, protocol,
				datetime, root, parent, host, port, remotehost, remoteport, country);



		IndexRequest indexRequest = new IndexRequest("logsql98", "connections");
		indexRequest.source(new Gson().toJson(c));
		IndexResponse response = transportClient.index(indexRequest).actionGet();

	}

	public static void main(String[] args) {
		EsData t = new EsData();
		t.initConnection();
		t.initElasticSearchConnection();
		t.getData();
	}
}
