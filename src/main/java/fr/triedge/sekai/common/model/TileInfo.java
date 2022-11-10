package fr.triedge.sekai.common.model;

import java.util.ArrayList;

public class TileInfo {

	private boolean walkable;
	private int x,y;
	private ArrayList<TileEvent> tileEvents = new ArrayList<>();

	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	public ArrayList<TileEvent> getTileEvents() {
		return tileEvents;
	}

	public void setTileEvents(ArrayList<TileEvent> tileEvents) {
		this.tileEvents = tileEvents;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
