package org.aserg.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.aserg.model.NetworkLayerIncident;
import org.aserg.model.MalwareIncident;
import org.aserg.model.MssqlIncident;
import org.aserg.model.MysqlIncident;
import org.aserg.model.SipIncident;
import org.aserg.model.SshIncident;
import org.aserg.model.WebIncident;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.transport.ConnectTransportException;

import com.google.gson.Gson;

/**
 * This is the ElasticSearch Utility class that encapsulates the ElasticSearch
 * Bulk processing functionality.
 * 
 * @author Yumna Ghazi
 *
 */
public class EsUtility {

	private static ESLogger logger;
	private static TransportClient client;

	/**
	 * The default ElasticSearch cluster name
	 */
	private static String DEFAULT_CLUSTERNAME;

	/**
	 * The default ElasticSearch host name
	 */
	private static String DEFAULT_HOSTNAME;
	/**
	 * The default port ElasticSearch client listens on
	 */
	private static int DEFAULT_PORT;
	/**
	 * The default concurrent requests
	 */
	private static int DEFAULT_CONCURRENT_REQUESTS;
	/**
	 * The default number of actions to trigger a bulk flush
	 */
	private static int DEFAULT_BULK_ACTIONS;
	/**
	 * The default time interval to trigger a bulk flush
	 */
	private static TimeValue DEFAULT_FLUSH_INTERVAL;
	/**
	 * The default size of bulk to trigger a bulk flush
	 */
	public static ByteSizeValue DEFAULT_BULK_SIZE;
	/**
	 * The ElasticSearch BulkProcessor
	 */
	private static BulkProcessor bulkProcessor;

	/**
	 * Static block for initializing ElasticSearch environmental fields from
	 * "es.properties" file
	 */
	static {

		logger = Loggers.getLogger(EsUtility.class);
		// Load ES Properties
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("config/es.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// initialize variables from properties file
		DEFAULT_HOSTNAME = prop.getProperty("es.hostname");
		DEFAULT_CLUSTERNAME = prop.getProperty("es.clustername");
		DEFAULT_PORT = Integer.parseInt(prop.getProperty("es.port"));
		DEFAULT_CONCURRENT_REQUESTS = Integer.parseInt(prop.getProperty("es.concurrentrequests"));
		DEFAULT_BULK_ACTIONS = Integer.parseInt(prop.getProperty("es.bulkactions"));
		DEFAULT_FLUSH_INTERVAL = TimeValue.timeValueSeconds(Integer.parseInt(prop.getProperty("es.flushinterval")));
		DEFAULT_BULK_SIZE = new ByteSizeValue(Integer.parseInt(prop.getProperty("es.bulksize")), ByteSizeUnit.MB);

	}

	/**
	 * The ElasticSearch BulkListener
	 */
	private final static BulkProcessor.Listener listener = new BulkProcessor.Listener() {

		public void beforeBulk(long executionId, BulkRequest request) {
			logger.info("Bulk flush triggered [" + executionId + "], where number of requests is "
					+ request.numberOfActions());
		}

		public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
			logger.error("Error during bulk insert: " + executionId, failure);
		}

		public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
			if (response.hasFailures()) {
				throw new RuntimeException(response.buildFailureMessage());
			} else {
				logger.info("Bulk execution completed [" + executionId + "].\n" + "Took (ms): "
						+ response.getTookInMillis() + "\n" + "Failures: " + response.hasFailures() + "\n" + "Count: "
						+ response.getItems().length);
			}
		}
	};

	/**
	 * Initialize ES TransportClient and bulkProcessor, which begins listening
	 * for requests
	 */
	public static void initBulkProcessor() {
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", DEFAULT_CLUSTERNAME).build();
		client = new TransportClient(settings);
		try {
			client.addTransportAddress(new InetSocketTransportAddress(DEFAULT_HOSTNAME, DEFAULT_PORT));
		} catch (ConnectTransportException e) {
			logger.error("Error occurred while trying to connect to ElasticSearch at IP [{}] and Port [{}]",
					DEFAULT_HOSTNAME, DEFAULT_PORT);
			// TODO handle exception by waiting for connection
		}
		bulkProcessor = BulkProcessor.builder(client, listener).setBulkActions(DEFAULT_BULK_ACTIONS)
				.setConcurrentRequests(DEFAULT_CONCURRENT_REQUESTS).setFlushInterval(DEFAULT_FLUSH_INTERVAL)
				.setBulkSize(DEFAULT_BULK_SIZE).build();

	}

	/**
	 * Close BulkProcessor and TransportClient
	 * 
	 * @throws InterruptedException
	 */
	public static void closeBulkProcessor() {
		bulkProcessor.flush();
		try {
			bulkProcessor.awaitClose(30, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.error("Error occurred while trying to close BulkProcessor ", e);
		}
		client.close();
	}


	public static void pushMalwareData(List<MalwareIncident> list, String index, String type) {
		initBulkProcessor();
		for (MalwareIncident i : list) {
			bulkProcessor.add(new IndexRequest(index, type).source(new Gson().toJson(i)));
		}
		closeBulkProcessor();
	}

	public static void pushSshData(List<SshIncident> list, String index, String type) {
		initBulkProcessor();
		for (SshIncident i : list) {
			bulkProcessor.add(new IndexRequest(index, type).source(new Gson().toJson(i)));
		}
		closeBulkProcessor();
	}

	public static void pushSipData(List<SipIncident> list, String index, String type) {
		initBulkProcessor();
		for (SipIncident i : list) {
			bulkProcessor.add(new IndexRequest(index, type).source(new Gson().toJson(i)));
		}
		closeBulkProcessor();
	}

	public static void pushMysqlData(List<MysqlIncident> list, String index, String type) {
		initBulkProcessor();
		for (MysqlIncident i : list) {
			bulkProcessor.add(new IndexRequest(index, type).source(new Gson().toJson(i)));
		}
		closeBulkProcessor();
	}

	public static void pushMssqlData(List<MssqlIncident> list, String index, String type) {
		initBulkProcessor();
		for (MssqlIncident i : list) {
			bulkProcessor.add(new IndexRequest(index, type).source(new Gson().toJson(i)));
		}
		closeBulkProcessor();
	}

	public static void pushNetworkData(List<NetworkLayerIncident> list, String index, String type) {
		initBulkProcessor();
		for (NetworkLayerIncident i : list) {
			bulkProcessor.add(new IndexRequest(index, type).source(new Gson().toJson(i)));
		}
		closeBulkProcessor();
	}

	public static void pushWebData(List<WebIncident> list, String index, String type) {
		initBulkProcessor();
		for (WebIncident i : list) {
			bulkProcessor.add(new IndexRequest(index, type).source(new Gson().toJson(i)));
		}
		closeBulkProcessor();
	}

}
