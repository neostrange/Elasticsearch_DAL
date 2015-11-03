package org.aserg.model;

public class Connection {
	
	public Connection(Download download, String connection, String connection_type, String connection_transport,
			String connection_protocol, String connection_datetime, String connection_root, String connection_parent,
			String local_host, String local_port, String remote_host, String remote_port, String remote_country) {
		super();
		this.download = download;
		this.connection = connection;
		this.connection_type = connection_type;
		this.connection_transport = connection_transport;
		this.connection_protocol = connection_protocol;
		this.connection_datetime = connection_datetime;
		this.connection_root = connection_root;
		this.connection_parent = connection_parent;
		this.local_host = local_host;
		this.local_port = local_port;
		this.remote_host = remote_host;
		this.remote_port = remote_port;
		this.remote_country = remote_country;
	}
	
	private Download download;
	private String connection;
	private String connection_type;
	private String connection_transport;
	private String connection_protocol;
	private String connection_datetime;
	private String connection_root;
	private String connection_parent;
	private String local_host;
	private String local_port;
	private String remote_host;
	private String remote_port;
	private String remote_country;

	public Download getDownload() {
		return download;
	}
	public void setDownload(Download download) {
		this.download = download;
	}
	public String getConnection() {
		return connection;
	}
	public void setConnection(String connection) {
		this.connection = connection;
	}
	public String getConnection_type() {
		return connection_type;
	}
	public void setConnection_type(String connection_type) {
		this.connection_type = connection_type;
	}
	public String getConnection_transport() {
		return connection_transport;
	}
	public void setConnection_transport(String connection_transport) {
		this.connection_transport = connection_transport;
	}
	public String getConnection_protocol() {
		return connection_protocol;
	}
	public void setConnection_protocol(String connection_protocol) {
		this.connection_protocol = connection_protocol;
	}
	public String getConnection_datetime() {
		return connection_datetime;
	}
	public void setConnection_datetime(String connection_datetime) {
		this.connection_datetime = connection_datetime;
	}
	public String getConnection_root() {
		return connection_root;
	}
	public void setConnection_root(String connection_root) {
		this.connection_root = connection_root;
	}
	public String getConnection_parent() {
		return connection_parent;
	}
	public void setConnection_parent(String connection_parent) {
		this.connection_parent = connection_parent;
	}
	public String getLocal_host() {
		return local_host;
	}
	public void setLocal_host(String local_host) {
		this.local_host = local_host;
	}
	public String getLocal_port() {
		return local_port;
	}
	public void setLocal_port(String local_port) {
		this.local_port = local_port;
	}
	public String getRemote_host() {
		return remote_host;
	}
	public void setRemote_host(String remote_host) {
		this.remote_host = remote_host;
	}
	public String getRemote_port() {
		return remote_port;
	}
	public void setRemote_port(String remote_port) {
		this.remote_port = remote_port;
	}
	public String getRemote_country() {
		return remote_country;
	}
	public void setRemote_country(String remote_country) {
		this.remote_country = remote_country;
	}

}
