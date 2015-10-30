package TestES.TestES;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.google.gson.Gson;

public class testes {

	private Client client;

	public static void main(String args[]) {

		testes testEs = new testes();
		
		System.out.println(testEs.checkIndexExists("movie_db"));
		
		testEs.addDocument();
	}

	public testes() {
		Settings settings = ImmutableSettings.settingsBuilder().put("client.transport.sniff", true)
				.put("cluster.name", "elasticsearch").build();

		client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
	}

	public boolean checkIndexExists(String name) {
		IndicesExistsResponse response = client.admin().indices().prepareExists(name).execute().actionGet();
		return response.isExists();
	}

	public void createIndex(String name) {
		client.admin().indices().prepareCreate(name).execute().actionGet();
	}

	public void deleteIndex(String name) {
		client.admin().indices().prepareDelete(name).execute().actionGet();
	}

	public void closeIndex(String name) {
		client.admin().indices().prepareClose(name).execute().actionGet();
	}

	public void openIndex(String name) {
		client.admin().indices().prepareOpen(name).execute().actionGet();
	}
	
	public void getDocument(){
		GetResponse getResponse = this.client.prepareGet("movie_db","movie","1").execute().actionGet();
		System.out.println(getResponse.getField("Tomb Raider"));
	}
	
	public void addDocument(){
		movie movie1 = new movie();
		   movie1.setActors("abbass");
		   movie1.setDescription("test");
		   movie1.setGenre("2000");
		   movie1.setRelease("2001");
		   movie1.setRelease_year("2001");
		   movie1.setTitle("testing");
		   movie1.setId("222");

		   IndexRequest indexRequest = new IndexRequest("movie_db","movie", movie1.getId());
		   indexRequest.source(new Gson().toJson(movie1));
		   IndexResponse response = client.index(indexRequest).actionGet();
	}

}
