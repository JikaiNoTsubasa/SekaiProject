package fr.triedge.sekai.editor.model;

import java.util.ArrayList;


public class SpriteLayer {

	private ArrayList<Sprite> sprites = new ArrayList<>();
	private int id;

	public ArrayList<Sprite> getSprites() {
		return sprites;
	}

	public void setSprites(ArrayList<Sprite> sprites) {
		this.sprites = sprites;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
