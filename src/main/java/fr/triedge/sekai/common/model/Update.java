package fr.triedge.sekai.common.model;

import java.util.ArrayList;


public class Update {

	private String version;
	private ArrayList<UpdateElement> elements = new ArrayList<>();
	
	public Update() {
		this("0");
	}
	
	public Update(String version) {
		setVersion(version);
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public ArrayList<UpdateElement> getElements() {
		return elements;
	}
	public void setElements(ArrayList<UpdateElement> elements) {
		this.elements = elements;
	}
}
