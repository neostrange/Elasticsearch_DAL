package org.aserg.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.InitialContext;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class SqlUtility {

	public static final String MALWARE_INCIDENT_QUERY = "select downloads.download as order_id, remote_host,local_port, connection_protocol,"
			+ "connection_type,datetime(connection_timestamp,'unixepoch','localtime') as connection_datetime,"
			+ "connection_transport,local_host,remote_port,download_url,download_md5_hash," + "virustotal_permalink,"
			+ "virustotalscan_scanner,virustotalscan_result " + "from connections "
			+ "inner join downloads on downloads.connection=connections.connection "
			+ "left join virustotals on virustotals.virustotal_md5_hash=downloads.download_md5_hash "
			+ "left join virustotalscans on virustotalscans.virustotal=virustotals.virustotal ";

	public static final String MALWARE_INCIDENT_QUERY_COUNT = "select count(connections.connection) as total,datetime(connection_timestamp,'unixepoch','localtime') as connection_datetime "
			+ "from connections " + "inner join downloads on downloads.connection=connections.connection "
			+ "left join virustotals on virustotals.virustotal_md5_hash=downloads.download_md5_hash "
			+ "left join virustotalscans on virustotalscans.virustotal=virustotals.virustotal ";

	public static final String MSSQL_INCIDENT_QUERY = "select cmf.*, mssql_commands.mssql_command_status as status, "
			+ "mssql_commands.mssql_command_cmd as cmd "
			+ "from (select connections.connection, remote_host,local_port, connection_protocol,"
			+ "connection_type,datetime(connection_timestamp,'unixepoch','localtime') as connection_datetime,"
			+ "connection_transport,local_host,remote_port,"
			+ "mssql_fingerprint_hostname,mssql_fingerprint_cltintname " + "from connections "
			+ "inner join mssql_fingerprints on mssql_fingerprints.connection=connections.connection) as cmf "
			+ "left join mssql_commands on mssql_commands.connection= cmf.connection";

	public static final String MYSQL_INCIDENT_QUERY = "Select cmc.connection as order_id , cmc.remote_host, cmc.local_port, cmc.connection_protocol,"
			+ "cmc.connection_type,cmc.datetime as connection_datetime,cmc.connection_transport,cmc.local_host,"
			+ "cmc.connection,cmc.remote_port,mysql_command_args.mysql_command_arg_data,"
			+ "mysql_command_ops.mysql_command_op_name " + "from (select connections.connection, "
			+ "mysql_commands.mysql_command,mysql_commands.mysql_command_cmd, "
			+ " remote_host,local_port, connection_protocol,connection_type, "
			+ "datetime(connection_timestamp,'unixepoch','localtime') as datetime,"
			+ " connection_transport,local_host,remote_port "
			+ "from mysql_commands INNER JOIN connections ON (mysql_commands.connection = connections.connection)) as cmc"
			+ " LEFT JOIN mysql_command_args ON (mysql_command_args.mysql_command = cmc.mysql_command)"
			+ " LEFT JOIN mysql_command_ops ON (mysql_command_ops.mysql_command_cmd = cmc.mysql_command_cmd) ";

	public static final String NETWORK_LAYER_INCIDENT_QUERY = "select INET_NTOA(iphdr.ip_src) as remote_host, "
			+ "tcphdr.tcp_dport as tcp_local_port, udphdr.udp_dport as udp_local_port, 	"
			+ "event.timestamp as connection_datetime,INET_NTOA(iphdr.ip_dst) as local_host,"
			+ "tcphdr.tcp_sport as tcp_remote_port,	udphdr.udp_sport as udp_remote_port, "
			+ "icmphdr.icmp_type,event.cid,event.sid, signature.sig_name, sig_class.sig_class_name "
			+ "FROM event INNER JOIN iphdr on (event.cid=iphdr.cid AND event.sid=iphdr.sid) "
			+ "LEFT JOIN icmphdr on (iphdr.cid = icmphdr.cid AND iphdr.sid=icmphdr.sid) LEFT JOIN tcphdr on (iphdr.cid = tcphdr.cid AND iphdr.sid = tcphdr.sid) "
			+ "LEFT JOIN udphdr on (iphdr.cid = udphdr.cid AND iphdr.sid = udphdr.sid)  INNER JOIN signature on (event.signature = signature.sig_id) "
			+ "LEFT JOIN sig_class on (signature.sig_class_id = sig_class.sig_class_id)";

	public static final String SSH_INCIDENT_QUERY = " SELECT sessions.id as order_id, sensor, sessions.ip as remote_host, sensors.ip as localhost,"
			+ " starttime as connection_datetime, endtime, clients.version, auth.username, "
			+ "auth.password,auth.success as auth_success,auth.timestamp as auth_timestamp, "
			+ "input.input, input.success as input_success,input.timestamp as input_timestamp " + "FROM sessions "
			+ "LEFT JOIN auth ON auth.session = sessions.id LEFT JOIN input ON input.session = sessions.id  LEFT JOIN clients ON clients.id = sessions.client "
			+ "LEFT JOIN sensors on sensors.id = sessions.sensor ";

	public static final String WEB_INCIDENT_QUERY = "SELECT events.event_id as order_id,a_timestamp as connection_datetime,"
			+ "INET_NTOA(a_client_ip) as client_ip,a_client_port,INET_NTOA(a_server_ip) as remote_host ,a_server_port,"
			+ "b_method,b_path, b_path_parameter,b_protocol, b_user_agent,b_referer,f_content_length,"
			+ "f_content_type, f_status,id_severity,severity.severity,message_ruleMsg" + " from events "
			+ "left join events_messages on events_messages.event_id=events.event_id "
			+ "left join rule_message on rule_message.message_ruleid=events_messages.h_message_ruleId "
			+ "left join severity on severity.id_severity=events.`h_severity` ";

	public static final String SIP_INCIDENT_QUERY = "select remote_host,local_port, connection_protocol,"
			+ "connection_type,datetime(connection_timestamp,'unixepoch','localtime') as connection_datetime,"
			+ "connection_transport,local_host,remote_port," + "sip_commands.sip_command_method,"
			+ "sip_commands.sip_command_user_agent," + "sip_commands.sip_command_call_id " + "FROM sip_commands "
			+ "INNER JOIN connections on (sip_commands.connection=connections.connection)";

	public static final String SSH_MALWARE_INCIDENT_QUERY = "SELECT downloads.timestamp as connection_datetime, "
			+ "downloads.outfile as binary, sessions.id, "
			+ "sensors.ip as localhost, url, sessions.ip as remotehost FROM downloads "
			+ "LEFT JOIN sessions on downloads.session = sessions.id "
			+ "LEFT JOIN sensors on sensors.id = sessions.sensor ";

	final static String BASE_PATH = System.getProperty("user.dir") + "/config/db.properties";
	/**
	 * This variable will create a connection with Sqlite and mysql
	 */
	public static Connection dionaeaConnection = null;
	public static Connection kippoConnection = null;
	public static Connection webConnection = null;
	public static Connection netConnection = null;

	/**
	 * Block will initialize the connection to the Sqlite Database
	 * 
	 * @exception ClassNotFoundException,
	 *                SQLException
	 * @return Nothing
	 */
	static {

		try {
			Class.forName(getPropertyFromConf().getProperty("SQLITE_DRIVER"));
			dionaeaConnection = DriverManager.getConnection(getPropertyFromConf().getProperty("DATABASE_DIONAEA"));
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Static Block will initialize the connection to the Mysql Database
	 * MysqlDataSource is reciprocal of DriverManager
	 * 
	 * @exception SqlException
	 * @return Nothing
	 */
	static {
		
		MysqlDataSource mds = new MysqlDataSource();
		try{
			mds.setServerName(getPropertyFromConf().getProperty("HOST"));
			mds.setPortNumber(Integer.parseInt(getPropertyFromConf().getProperty("SSH_PORT")));
			mds.setDatabaseName(getPropertyFromConf().getProperty("SSH_DB_NAME"));
			mds.setUser(getPropertyFromConf().getProperty("SSH_USER"));
			mds.setPassword(getPropertyFromConf().getProperty("SSH_PASSWORD"));
		} catch(Exception e){
			e.printStackTrace();
		}

		// Getting Connection object
		try {
			kippoConnection = mds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static {
		
		MysqlDataSource mds = new MysqlDataSource();
		try{
			mds.setServerName(getPropertyFromConf().getProperty("HOST"));
			mds.setPortNumber(Integer.parseInt(getPropertyFromConf().getProperty("NETWORK_PORT")));
			mds.setDatabaseName(getPropertyFromConf().getProperty("NETWORK_DB_NAME"));
			mds.setUser(getPropertyFromConf().getProperty("NETWORK_USER"));
			mds.setPassword(getPropertyFromConf().getProperty("NETWORK_PASSWORD"));
		} catch(Exception e){
			e.printStackTrace();
		}
		

		// Getting Connection object
		try {
			netConnection = mds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static {
		MysqlDataSource mds = new MysqlDataSource();
		try{
			mds.setServerName(getPropertyFromConf().getProperty("HOST"));
			mds.setPortNumber(Integer.parseInt(getPropertyFromConf().getProperty("WEB_PORT")));
			mds.setDatabaseName(getPropertyFromConf().getProperty("WEB_DB_NAME"));
			mds.setUser(getPropertyFromConf().getProperty("WEB_USER"));
			mds.setPassword(getPropertyFromConf().getProperty("WEB_PASSWORD"));
		} catch(Exception e){
			e.printStackTrace();
		}
		

		// Getting Connection object
		try {
			webConnection = mds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Function will return the resultset on the bases of query
	 * 
	 * @param query
	 * @return rs
	 */
	public static ResultSet getResultSet(String query, Connection con, String time) {
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {

			if (query.contains("as order_id")) {
				System.out.println(query + "Where connection_datetime > '" + time + "' order by order_id asc");
				rs = stmt.executeQuery(query + " Where connection_datetime > '" + time + "'");

			} else {

				System.out.println(query + " Where connection_datetime > '" + time + "'");
				rs = stmt.executeQuery(query + " Where connection_datetime > '" + time + "'");
			}
		} catch (MySQLSyntaxErrorException e) {

			if (query.contains("as order_id")) {
				System.out.println(query + "Having connection_datetime > '" + time + "' order by order_id asc");
				try {
					rs = stmt.executeQuery(query + " Having connection_datetime > '" + time + "'");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {

				System.out.println(query + " Having connection_datetime > '" + time + "'");
				try {
					rs = stmt.executeQuery(query + " Having connection_datetime > '" + time + "'");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public static Properties getPropertyFromConf() {
		BufferedReader br;
		Properties p = new Properties();
		try {
			br = new BufferedReader(new FileReader(BASE_PATH));
			p.load(br);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

}
