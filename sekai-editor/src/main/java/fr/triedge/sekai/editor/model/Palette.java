package fr.triedge.sekai.editor.model;

import java.util.ArrayList;

public class Palette {

	private String name;
	private int id;
	private ArrayList<Integer> colors = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<Integer> getColors() {
		return colors;
	}
	
	public void setColors(ArrayList<Integer> colors) {
		this.colors = colors;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
