package fr.triedge.sekai.editor.model;

import java.util.ArrayList;


public class EditableMap {

	
	private ArrayList<Tile> goundTiles = new ArrayList<>();
	private ArrayList<Tile> objectTiles = new ArrayList<>();
	private String mapName;
	private String mapImage;
	private String chipset;
	private int mapHeight, mapWidth, tileSize;
	
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	public String getMapImage() {
		return mapImage;
	}
	public void setMapImage(String mapImage) {
		this.mapImage = mapImage;
	}
	public String getChipset() {
		return chipset;
	}
	public void setChipset(String chipset) {
		this.chipset = chipset;
	}

	public ArrayList<Tile> getGoundTiles() {
		return goundTiles;
	}
	public void setGoundTiles(ArrayList<Tile> goundTiles) {
		this.goundTiles = goundTiles;
	}

	public ArrayList<Tile> getObjectTiles() {
		return objectTiles;
	}
	public void setObjectTiles(ArrayList<Tile> objectTiles) {
		this.objectTiles = objectTiles;
	}
	public int getMapHeight() {
		return mapHeight;
	}
	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}
	public int getMapWidth() {
		return mapWidth;
	}
	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}
	public int getTileSize() {
		return tileSize;
	}
	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}
}
