package fr.triedge.sekai.common.model;


public class UpdateElement {

	private String sitePath;
	private String localPath;
	
	public UpdateElement() {
	}
	
	
	public UpdateElement(String sitePath, String localPath) {
		super();
		this.sitePath = sitePath;
		this.localPath = localPath;
	}

	public String getSitePath() {
		return sitePath;
	}
	public void setSitePath(String sitePath) {
		this.sitePath = sitePath;
	}
	public String getLocalPath() {
		return localPath;
	}
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
}
