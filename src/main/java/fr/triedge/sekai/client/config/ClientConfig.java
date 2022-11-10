package fr.triedge.sekai.client.config;

public class ClientConfig {

	private int serverPort;
	private String serverHost, linkUpdate, version;
	
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public String getServerHost() {
		return serverHost;
	}
	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}
	public String getLinkUpdate() {
		return linkUpdate;
	}
	public void setLinkUpdate(String linkUpdate) {
		this.linkUpdate = linkUpdate;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
}
