package fr.triedge.sekai.editor.model;

import java.util.ArrayList;


public class SpriteSheet {

	private String name;
	private int characterHeight,characterWidth;
	private ArrayList<SpriteLayer> layers = new ArrayList<>();
	private int imageType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCharacterWidth() {
		return characterWidth;
	}

	public void setCharacterWidth(int characterWidth) {
		this.characterWidth = characterWidth;
	}

	public int getCharacterHeight() {
		return characterHeight;
	}

	public void setCharacterHeight(int characterHeight) {
		this.characterHeight = characterHeight;
	}

	public ArrayList<SpriteLayer> getLayers() {
		return layers;
	}

	public void setLayers(ArrayList<SpriteLayer> layers) {
		this.layers = layers;
	}

	public int getImageType() {
		return imageType;
	}

	public void setImageType(int imageType) {
		this.imageType = imageType;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
