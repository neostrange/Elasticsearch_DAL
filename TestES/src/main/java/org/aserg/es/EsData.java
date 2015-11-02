package org.aserg.es;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import testes.testes.Connections;
import testes.testes.Download;
import testes.testes.Test;

import com.google.gson.Gson;
import com.maxmind.geoip.LookupService;

public class EsData {
	private static final String driver = "org.sqlite.JDBC";
	private static final String url = "jdbc:sqlite:src/main/java/Database/logsql62_2.sqlite";

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
			Settings settings = ImmutableSettings.settingsBuilder()
					.put("cluster.name", "elasticsearch").build();
			transportClient = new TransportClient(settings);
			transportClient = transportClient
					.addTransportAddress(new InetSocketTransportAddress(
							"localhost", 9300));

			// CreateIndexRequestBuilder createIndexRequestBuilder =
			// transportClient.admin().indices().prepareCreate("connection");
		}
	}

	public String getCountries(String remote_host) {
		String remote_country = "";
		try {

			String dir = "D:/GeoIP.dat";
			LookupService cl = new LookupService(dir,
					LookupService.GEOIP_MEMORY_CACHE);
			remote_country = cl.getCountry(remote_host).getName();

		} catch (IOException e) {
			System.out.println("IO Exception");
		}
		return remote_country;
	}

	public void getData() {
		try {
			System.out.println("OKKKKK");
			Statement stmt = con.createStatement();
			System.out.println("OKKKKKKKKK");
			ResultSet rs = stmt
					.executeQuery(" select connections.connection,connection_type,connection_transport,connection_protocol, "
							+ " connection_datetime,connection_root,connection_parent,local_host,local_port,remote_host, "
							+ " remote_port,download_url,virustotal_md5_hash,virustotalscan_scanner,virustotalscan_result "
							+ " from connections "
							+ " inner join downloads "
							+ " on downloads.connection=connections.connection "
							+ " left join virustotals "
							+ " on virustotals.virustotal_md5_hash=downloads.download_md5_hash "
							+ " inner join virustotalscans "
							+ " on virustotalscans.virustotal=virustotals.virustotal ");
			int count = 1;
			System.out.println("OKKK");
			// String hash = rs.getString("virustotal_md5_hash");
			bulkRequest = transportClient.prepareBulk();
            System.out.println("OKKKKKKKKK");
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
				String hash = rs.getString("virustotal_md5_hash");
				String scanner = rs.getString("virustotalscan_scanner");
				String result = rs.getString("virustotalscan_result");
				addData(conn, type, transport, protocol, datetime, root,
						parent, host, port, remotehost, remoteport,
						remotecountry, downurl, hash, scanner, result);
				System.out.println("Count >>:" + count);
				count++;
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

	public void addData(String conn, String type, String transport,
			String protocol, String datetime, String root, String parent,
			String host, String port, String remotehost, String remoteport,
			String country, String downurl, String hash, String scanner,
			String result)throws IOException {
		org.aserg.es.Connection c = new org.aserg.es.Connection();
		c.setConnection(conn);
		c.setConnection_type(type);
		c.setConnection_transport(transport);
		c.setConnection_protocol(protocol);
		c.setConnection_datetime(datetime);
		c.setConnection_root(root);
		c.setConnection_parent(parent);
		c.setLocal_host(host);
		c.setLocal_port(port);
		c.setRemote_host(remotehost);
		c.setRemote_port(remoteport);
		c.setRemote_country(country);
		c.setDownload_url(downurl);
		c.setDownload_url(downurl);
		c.setVirustotal_md5_hash(hash);
		
		//c.getDownload().setDownload_url(downurl);
		//Download download = new Download();
		//download.setDownload_url(downurl);
		//c.setDownload(download);
		//Download d=new Download();
		
		//vrt.setVirustotal_md5_hash(hash);
		//vrt.setVirusTotalscan();
		//c.setDownload(d);
		//c.getVirusTotal().setVirustotal_md5_hash(hash);
		//VirusTotal vrtl = new VirusTotal();
		//vrtl.setVirustotal_md5_hash(hash);
		//c.setVirusTotal(vrtl);
		
		List<String> scans = new ArrayList<String>();
		scans.add(scanner);
		
		List<String> res = new ArrayList<String>();
		res.add(result);
		c.setVirustotalscan_scanner(scans);
		c.setVirustotalscan_result(res);
		//c.getVirusTotalscan().setVirustotalscan_scanner(scans);
		//c.getVirusTotalscan().setVirustotalscan_result(res);
		//VirusTotalScan scan = new VirusTotalScan();
		//scan.setVirustotalscan_scanner(scans);
		//scan.setVirustotalscan_result(res);
		//c.setVirusTotalscan(scan);
		
		IndexRequest indexRequest = new IndexRequest("logsql98", "connections");
		indexRequest.source(new Gson().toJson(c));
		IndexResponse response = transportClient.index(indexRequest)
				.actionGet();

	}
	public static void main(String[] args){
		EsData t=new EsData();
		t.initConnection();
		t.initElasticSearchConnection();
		t.getData();
	}
}
