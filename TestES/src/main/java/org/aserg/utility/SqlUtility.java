package org.aserg.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * <h1> SqlUtility </h1>
 *  <p>
 * This class is responsible for creating, Initializing and providing Connection and ResultSet of the
 * Databases on the bases of Query Accessed.
 * <p>
 * Class has Queries that are accessed by other classes to get the results
 * 
 * @author Waseem
 * @version 0.1
 * @since 26-11-15 21:36
 */
public class SqlUtility {

	/**
	 * This variable will provide the logging of the complete class
	 */
	private static Logger log = LoggerFactory.getLogger(SqlUtility.class); 

	/**
	 * These variable will create a connection with Sqlite and mysql
	 * dionaeaConnection will provide connection to the logsql.sqlite database of Dionaea
	 * kippo,web,net will provide the connection of the mysql databases
	 */
	public static Connection sqliteConnection = null;
	public static Connection mysqlConnection = null;
	
	/**
	 * For creating the statement for the resultset 
	 * @see http://www.careerride.com/JDBC-PreparedStatement.aspx
	 */
	private static PreparedStatement preparedStatement = null;

	/**
	 * <p>
	 * For executing the queries
	 */
	private static ResultSet resultSet = null;

	private static String host = IOFileUtility.readProperty("HOST", IOFileUtility.ARCHIVAL_PATH);

	private static int port = Integer.parseInt(IOFileUtility.readProperty("PORT", IOFileUtility.ARCHIVAL_PATH));

	private static String user = IOFileUtility.readProperty("USER", IOFileUtility.ARCHIVAL_PATH);
	

	/**
	 * 
	 * <p>
	 * These are the list of queries for acquiring the data
	 */
	public static final String MALWARE_INCIDENT_QUERY = "select downloads.download as order_id, remote_host,local_port, connection_protocol,"
			+ "connection_type,datetime(connection_timestamp,'unixepoch','localtime') as connection_datetime,"
			+ "connection_transport,local_host,remote_port,download_url,download_md5_hash," + "virustotal_permalink,"
			+ "virustotalscan_scanner,virustotalscan_result " + "from connections "
			+ "inner join downloads on downloads.connection=connections.connection "
			+ "left join virustotals on virustotals.virustotal_md5_hash=downloads.download_md5_hash "
			+ "left join virustotalscans on virustotalscans.virustotal=virustotals.virustotal ";

	public static final String MALWARE_INCIDENT_QUERY_COUNT = "select connections.connection as order_id, count(connections.connection) as total,"
			+ "datetime(connection_timestamp,'unixepoch','localtime') as connection_datetime "
			+ "from connections " + "inner join downloads on downloads.connection=connections.connection "
			+ "left join virustotals on virustotals.virustotal_md5_hash=downloads.download_md5_hash "
			+ "left join virustotalscans on virustotalscans.virustotal=virustotals.virustotal ";

	public static final String MSSQL_INCIDENT_QUERY = "select cmf.connection as order_id ,cmf.*, mssql_commands.mssql_command_status as status, "
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
			+ "icmphdr.icmp_type,event.cid as order_id,event.sid, signature.sig_name, sig_class.sig_class_name "
			+ "FROM event INNER JOIN iphdr on (event.cid=iphdr.cid AND event.sid=iphdr.sid) "
			+ "LEFT JOIN icmphdr on (iphdr.cid = icmphdr.cid AND iphdr.sid=icmphdr.sid) LEFT JOIN tcphdr on (iphdr.cid = tcphdr.cid AND iphdr.sid = tcphdr.sid) "
			+ "LEFT JOIN udphdr on (iphdr.cid = udphdr.cid AND iphdr.sid = udphdr.sid)  INNER JOIN signature on (event.signature = signature.sig_id) "
			+ "LEFT JOIN sig_class on (signature.sig_class_id = sig_class.sig_class_id)";

	public static final String SSH_INCIDENT_QUERY = " SELECT sessions.id as order_id, sessions.ip as remote_host,"
			+ " starttime as connection_datetime, endtime, clients.version, auth.username, "
			+ "auth.password,auth.success as auth_success,auth.timestamp as auth_timestamp, "
			+ "input.input, input.success as input_success,input.timestamp as input_timestamp " + "FROM sessions "
			+ "LEFT JOIN auth ON auth.session = sessions.id " + "LEFT JOIN input ON input.session = sessions.id  "
			+ "LEFT JOIN clients ON clients.id = sessions.client ";

	public static final String WEB_INCIDENT_QUERY = "SELECT events.event_id as order_id,a_timestamp as connection_datetime,"
			+ "INET_NTOA(a_client_ip) as client_ip,a_client_port,INET_NTOA(a_server_ip) as remote_host ,a_server_port,"
			+ "b_method,b_path, b_path_parameter,b_protocol, b_user_agent,b_referer,f_content_length,"
			+ "f_content_type, f_status,id_severity,severity.severity,message_ruleMsg" 
			+ " from events "
			+ "left join events_messages on events_messages.event_id=events.event_id "
			+ "left join rule_message on rule_message.message_ruleId=events_messages.h_message_ruleId "
			+ "left join severity on severity.id_severity=events.`h_severity` ";

	public static final String SIP_INCIDENT_QUERY = "select connections.connection as order_id, remote_host,local_port, connection_protocol,"
			+ "connection_type,datetime(connection_timestamp,'unixepoch','localtime') as connection_datetime,"
			+ "connection_transport,local_host,remote_port,connections.connection,sip_commands.sip_command_method,"
			+ "sip_commands.sip_command_user_agent," + "sip_commands.sip_command_call_id " + "FROM sip_commands "
			+ "INNER JOIN connections on (sip_commands.connection=connections.connection)";

	public static final String SSH_MALWARE_INCIDENT_QUERY = "SELECT downloads.timestamp as connection_datetime, "
			+ "downloads.outfile as payload, sessions.id, url, sessions.ip as remotehost " + "FROM downloads "
			+ "LEFT JOIN sessions on downloads.session = sessions.id ";

	/**
	 * <h2>getDionaeaConnection</h2>
	 * <p>
	 * This function will initialize the connection to the Sqlite Database
	 * This function will be called by all populators that need connection 
	 * with logsql.sqlite database 
	 * 
	 * @exception ClassNotFoundException,SQLException
	 * @return dionaeaConnection
	 */
	public static Connection getSqliteConnection() {
		log.info("Trying to get Dionaea Connection...");
		try {
			if (sqliteConnection == null || sqliteConnection.isClosed()) {

				Class.forName(IOFileUtility.readProperty("SQLITE_DRIVER", IOFileUtility.ARCHIVAL_PATH));
				sqliteConnection = DriverManager.getConnection(
						IOFileUtility.readProperty("DATABASE_DIONAEA", IOFileUtility.ARCHIVAL_PATH),
						"PRAGMA journal_mode=WAL", null);
				Class.forName(IOFileUtility.readProperty("SQLITE_DRIVER", IOFileUtility.ARCHIVAL_PATH));
				sqliteConnection = DriverManager.getConnection(
						IOFileUtility.readProperty("DATABASE_DIONAEA", IOFileUtility.ARCHIVAL_PATH),
						"PRAGMA journal_mode=WAL", null);
				// github.com/neostrange/Elasticsearch_DAL.git
			}
		} catch (ClassNotFoundException | SQLException e) {
			if (e.getMessage().contains("BUSY"))
				log.error("Error occurred while trying to get Dionaea connection because database is locked ", e);
			else
				log.error("Error occurred while trying to get Dionaea connection ", e);
		}
		log.info("Dionaea Connection Successfully Created");
		return sqliteConnection;
	}

	/**
	 * <h2> getKippoConnection </h2>
	 * Function will initialize the connection to the all mysql Database
	 * MysqlDataSource is reciprocal of DriverManager
	 * @see https://docs.oracle.com/javase/tutorial/jdbc/basics/sqldatasources.html
	 * 
	 * @exception SqlException
	 * @return mysqlConnection
	 */
	public static Connection getMysqlConnection(String database, String password) {
		log.info("Trying to get [{}] Connection...", database);
		MysqlDataSource mds = null;
		try {
			if (mysqlConnection == null || mysqlConnection.isClosed()) {
				mds = new MysqlDataSource();
				mds.setServerName(host );
				mds.setPortNumber(port );
				mds.setDatabaseName(database);
				mds.setUser(user );
				mds.setPassword(password);
				mysqlConnection = mds.getConnection();

			}
		} catch (SQLException e) {
			log.error("Error occurred while trying to get Kippo Connection ", e);
		}
		log.info("[{}] Connection Successfully Created",database);
		return mysqlConnection;

	}
	
	/**
	 * <h2> closeDbInstances </h2>
	 * <p>
	 * Function will close all the instances of databases i-e ResultSet, Statement, Connection
	 * will test whether the connections, statement and connection are initialized
	 * Need to close in the sequence (1)ResultSet (2)Statement (3) Connection   
	 * @param con
	 */
	public static void closeDbInstances(Connection con) {
		log.info("Trying to close Dionaea Connection...");
		if (con != null && preparedStatement != null && resultSet != null) {
			try {
				resultSet.close();
				preparedStatement.close();
				con.close();
			} catch (SQLException e) {
				log.error("Error occurred while trying to close Dionaea connection ", e);
			}
		}
		log.info("Dionaea Connection Closed");
	}

	/**
	 * Function will return the resultset on the bases of query, time.
	 * Function tests whether the called database id mysql or sqlite 
	 * If function finds order_id its orders the results accordingly
	 * 
	 * @param query, con, time
	 * @return resultSet
	 */
	public static ResultSet getResultSet(String query, Connection con, String time) {
        long startTime = System.currentTimeMillis();
		String tempQuery = null;
		// for MySQL databases

		if (query.contains("severity") || query.contains("session") || query.contains("icmp"))
			tempQuery = query + " Having connection_datetime > ?";
		else
			tempQuery = query + " Where connection_datetime > ?";

		if (query.contains("session") )
			tempQuery = tempQuery + " order by connection_datetime asc";
		
		else 
			tempQuery = tempQuery + " order by order_id asc";	

		try {
			preparedStatement = con.prepareStatement(tempQuery);
			preparedStatement.setString(1, time);

			// schema = con.getSchema();
		} catch (SQLException e1) {
			log.error("Error occurred while trying to create SQL Statement ", e1);
		}

		try {
			log.info("Query to be executed: [{}]", tempQuery);
			resultSet = preparedStatement.executeQuery();

		} catch (MySQLSyntaxErrorException e) {
			log.error("Error occurred while trying to execute query [{}] in database ", tempQuery, e);

		} catch (SQLException e) {
			log.error("Error occurred while trying to execute query [{}] in database ", tempQuery, e);
		}
		log.info("Query executed successfully in [{}] ms", (System.currentTimeMillis()-startTime));
		return resultSet;
	}

}
