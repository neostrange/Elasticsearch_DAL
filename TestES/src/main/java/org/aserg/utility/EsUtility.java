package org.aserg.utility;

import java.io.FileInputStream;
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
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.NoSuchNodeException;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * This is the ElasticSearch Utility class that encapsulates the ElasticSearch
 * Bulk processing functionality.
 * 
 * @author Yumna Ghazi
 *
 */
public class EsUtility {

	/**
	 * The logger for this class
	 */
	private static Logger log = LoggerFactory.getLogger(EsUtility.class);

	/**
	 * The ElasticSearch Transport Client that makes requests
	 */
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

		// Load ES Properties
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(IOFileUtility.ES_PATH));
		} catch (IOException e) {
			log.error("Error while trying to read ES properties", e);
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
	 * The ElasticSearch BulkListener, listens for BulkRequests
	 */
	private final static BulkProcessor.Listener listener = new BulkProcessor.Listener() {

		/**
		 * Invoked just before the bulk process is initiated
		 */
		public void beforeBulk(long executionId, BulkRequest request) {
			log.info("Bulk flush triggered [{}], where number of requests is ", executionId, request.numberOfActions());
			System.out.println(new Gson().toJson(request.getContext()));
		}

		/**
		 * Invoked after the bulk process, if it fails
		 */
		public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
			log.error("Error during bulk insert [{}]", executionId, failure);
			// make it sleep and re-add to bulk
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				log.error("Error while trying to wait for 10 seconds");
			}
			bulkProcessor.add(request);

		}

		/**
		 * Invoked in case of successful bulk operation
		 */
		public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
			if (response.hasFailures()) {
				for (BulkItemResponse bulkItemResponse : response.getItems()) {
					if (bulkItemResponse.isFailed()) {
						log.error("Bulk failed, id [{}]", bulkItemResponse.getId(),
								bulkItemResponse.getFailureMessage());
					}
				}
				log.error("There seems to have been a problem in Bulk flush, Retrying in 10 seconds...");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					log.error("Error while trying to wait for 10 seconds");
				}
				bulkProcessor.add(request);
			} else {
				log.info("Bulk execution completed [{}].\nTook (ms): {} \nFailures: {}\nCount: {}", executionId,
						response.getTookInMillis(), response.hasFailures(), response.getItems().length);
				// Flush BulkProcessor, just in case there are any pending
				// requests
				bulkProcessor.flush();
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
			bulkProcessor = BulkProcessor.builder(client, listener).setBulkActions(DEFAULT_BULK_ACTIONS)
					.setConcurrentRequests(DEFAULT_CONCURRENT_REQUESTS).setFlushInterval(DEFAULT_FLUSH_INTERVAL)
					.setBulkSize(DEFAULT_BULK_SIZE).build();
		} catch (NoSuchNodeException | NoNodeAvailableException e) {
			log.error("Error occurred while trying to establish connection", e);
			// TODO wait for reconnection..? Request/Docs loss?
		}

	}

	/**
	 * Close BulkProcessor and TransportClient
	 */
	public static void closeBulkProcessor() {
		bulkProcessor.flush();
		try {
			bulkProcessor.awaitClose(30, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error("Error occurred while trying to close bulkProcessor", e);
		}
		client.close();
	}

	/**
	 * Adds the given document to the BulkProcessor, to the given index and type
	 * 
	 * @param doc
	 *            the document to be indexed
	 * @param index
	 *            the ElasticSearch index where the document is to be stored
	 * @param type
	 *            the ElasticSearch type where the document is to be stored
	 */
	public static void pushDocument(String doc, String index, String type) {
		// check if ES nodes available
		try {

			if (client.listedNodes().size() > 0) {
				log.info("Added doc into BulkProcessor index [{}], type [{}]", index, type);
				bulkProcessor.add(new IndexRequest(index, type).source(doc));

			} else {
				log.warn("Couldn't find any available ElasticSearch nodes for pushing document");
				// TODO try to look for available nodes/ establish connection
				// again...
			}
		} catch (ElasticsearchException e) {
			log.error("Error occurred while trying to add document to index [{}], type [{}]", index, type, e);
		}
	}

	/**
	 * Push list of {@link MalwareIncident} to given index and type in
	 * ElasticSearch
	 * 
	 * @param list
	 *            list of MalwareIncident
	 * @param index
	 *            the ElasticSearch index where the document is to be stored
	 * @param type
	 *            the ElasticSearch type where the document is to be stored
	 */
	public static void pushMalwareData(List<MalwareIncident> list, String index, String type) {
		initBulkProcessor();
		for (MalwareIncident i : list) {
			bulkProcessor.add(new IndexRequest(index, type).source(new Gson().toJson(i)));
		}
		closeBulkProcessor();
	}

	/**
	 * Push list of {@link SshIncident} to given index and type in ElasticSearch
	 * 
	 * @param list
	 *            list of SshIncident
	 * @param index
	 *            the ElasticSearch index where the document is to be stored
	 * @param type
	 *            the ElasticSearch type where the document is to be stored
	 */
	public static void pushSshData(List<SshIncident> list, String index, String type) {
		initBulkProcessor();
		for (SshIncident i : list) {
			bulkProcessor.add(new IndexRequest(index, type).source(new Gson().toJson(i)));
		}
		closeBulkProcessor();
	}

	/**
	 * Push list of {@link SipIncident} to given index and type in ElasticSearch
	 * 
	 * @param list
	 *            list of SipIncident
	 * @param index
	 *            the ElasticSearch index where the document is to be stored
	 * @param type
	 *            the ElasticSearch type where the document is to be stored
	 */
	public static void pushSipData(List<SipIncident> list, String index, String type) {
		initBulkProcessor();
		for (SipIncident i : list) {
			bulkProcessor.add(new IndexRequest(index, type).source(new Gson().toJson(i)));
		}
		closeBulkProcessor();
	}

	/**
	 * Push list of {@link MysqlIncident} to given index and type in
	 * ElasticSearch
	 * 
	 * @param list
	 *            list of MysqlIncident
	 * @param index
	 *            the ElasticSearch index where the document is to be stored
	 * @param type
	 *            the ElasticSearch type where the document is to be stored
	 */
	public static void pushMysqlData(List<MysqlIncident> list, String index, String type) {
		initBulkProcessor();
		for (MysqlIncident i : list) {
			bulkProcessor.add(new IndexRequest(index, type).source(new Gson().toJson(i)));
		}
		closeBulkProcessor();
	}

	/**
	 * Push list of {@link MssqlIncident} to given index and type in
	 * ElasticSearch
	 * 
	 * @param list
	 *            list of MssqlIncident
	 * @param index
	 *            the ElasticSearch index where the document is to be stored
	 * @param type
	 *            the ElasticSearch type where the document is to be stored
	 */
	public static void pushMssqlData(List<MssqlIncident> list, String index, String type) {
		initBulkProcessor();
		for (MssqlIncident i : list) {
			bulkProcessor.add(new IndexRequest(index, type).source(new Gson().toJson(i)));
		}
		closeBulkProcessor();
	}

	/**
	 * Push list of {@link NetworkLayerIncident} to given index and type in
	 * ElasticSearch
	 * 
	 * @param list
	 *            list of NetworkLayerIncident
	 * @param index
	 *            the ElasticSearch index where the document is to be stored
	 * @param type
	 *            the ElasticSearch type where the document is to be stored
	 */
	public static void pushNetworkData(List<NetworkLayerIncident> list, String index, String type) {
		initBulkProcessor();
		for (NetworkLayerIncident i : list) {
			bulkProcessor.add(new IndexRequest(index, type).source(new Gson().toJson(i)));
		}
		closeBulkProcessor();
	}

	/**
	 * Push list of {@link WebIncident} to given index and type in ElasticSearch
	 * 
	 * @param list
	 *            list of WebIncident
	 * @param index
	 *            the ElasticSearch index where the document is to be stored
	 * @param type
	 *            the ElasticSearch type where the document is to be stored
	 */
	public static void pushWebData(List<WebIncident> list, String index, String type) {
		initBulkProcessor();
		for (WebIncident i : list) {
			bulkProcessor.add(new IndexRequest(index, type).source(new Gson().toJson(i)));
		}
		closeBulkProcessor();
	}

}
