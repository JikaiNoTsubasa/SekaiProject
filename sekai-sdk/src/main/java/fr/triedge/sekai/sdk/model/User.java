package fr.triedge.sekai.sdk.model;

import java.util.ArrayList;


public class User {

	private int id;
	private String username;
	private String password;
	private ArrayList<java.lang.Character> characters = new ArrayList<>();
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<java.lang.Character> getCharacters() {
		return characters;
	}
	public void setCharacters(ArrayList<java.lang.Character> characters) {
		this.characters = characters;
	}
}
