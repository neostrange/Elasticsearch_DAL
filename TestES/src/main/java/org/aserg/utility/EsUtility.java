package org.aserg.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

/**
 * This is the ElasticSearch Utility class that encapsulates the ElasticSearch
 * Bulk processing functionality.
 * 
 * @author Yumna Ghazi
 *
 */
public class EsUtility {

	private ESLogger logger;
	private TransportClient client;

	/**
	 * The default hostname where ElasticSearch resides
	 */
	private static String DEFAULT_HOSTNAME;
	/**
	 * The default ElasticSearch cluster name
	 */
	private static String DEFAULT_CLUSTERNAME;
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
	private BulkProcessor bulkProcessor;
	/**
	 * The ElasticSearch BulkListener
	 */
	private final BulkProcessor.Listener listener = new BulkProcessor.Listener() {

		public void beforeBulk(long executionId, BulkRequest request) {
			logger.info("Bulk flush triggered [" + executionId + "], where number of requests is "
					+ request.numberOfActions());
		}

		public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
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

	public EsUtility() {
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
		// initialize ES TransportClient and bulkProcessor
		client = new TransportClient();
		client.addTransportAddress(new InetSocketTransportAddress(DEFAULT_HOSTNAME, DEFAULT_PORT));
		bulkProcessor = BulkProcessor.builder(client, listener).setBulkActions(DEFAULT_BULK_ACTIONS)
				.setConcurrentRequests(DEFAULT_CONCURRENT_REQUESTS).setFlushInterval(DEFAULT_FLUSH_INTERVAL)
				.setBulkSize(DEFAULT_BULK_SIZE).build();

	}

	public BulkProcessor getBulkProcessor() {
		return bulkProcessor;
	}

	public void setBulkProcessor(BulkProcessor bulkProcessor) {
		this.bulkProcessor = bulkProcessor;
	}

	public TransportClient getClient() {
		return client;
	}

	public void setClient(TransportClient client) {
		this.client = client;
	}

}
