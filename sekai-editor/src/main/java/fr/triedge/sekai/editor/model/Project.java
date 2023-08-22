package fr.triedge.sekai.editor.model;

import fr.triedge.sekai.editor.utils.Storage;

import java.io.IOException;
import java.util.ArrayList;


public class Project {

	private String name;
	
	private ArrayList<SpriteSheet> sprites = new ArrayList<>();
	private ArrayList<EditableMap> maps = new ArrayList<>();
	private ArrayList<Palette> palettes = new ArrayList<>();

	public String getName() {
		return name;
	}
	
	public void save() throws IOException {
		Storage.storeProjectToDefault(this, getName());
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<SpriteSheet> getSprites() {
		return sprites;
	}

	public void setSprites(ArrayList<SpriteSheet> sprites) {
		this.sprites = sprites;
	}

	public ArrayList<EditableMap> getMaps() {
		return maps;
	}

	public void setMaps(ArrayList<EditableMap> maps) {
		this.maps = maps;
	}

	public ArrayList<Palette> getPalettes() {
		return palettes;
	}

	public void setPalettes(ArrayList<Palette> palettes) {
		this.palettes = palettes;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
