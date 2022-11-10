package fr.triedge.sekai.common.utils;

import java.io.File;
import java.io.IOException;

import fr.triedge.sekai.client.config.ClientConfig;
import fr.triedge.sekai.common.model.Update;
import fr.triedge.sekai.pixis.model.EditableMap;
import fr.triedge.sekai.pixis.model.Tile;
import fr.triedge.sekai.pixis.model.TileType;

public class DefaultFactory {

	public static EditableMap generateDefaultEditableMap(String name, int height, int width, String chipset) {
		EditableMap map = new EditableMap();
		map.setMapName(name);
		map.setMapHeight(height);
		map.setMapWidth(width);
		map.setChipset(chipset);

		// Generate default background
		int w = map.getMapWidth();
		int h = map.getMapHeight();

		for (int x = 0; x < w; ++x) {
			for (int y = 0; y < h; ++y) {
				Tile tile = new Tile(x, y);
				tile.setType(TileType.GRASS);
				map.getGoundTiles().add(tile);
			}
		}

		return map;
	}

	public static void generateDefaultClientConfigFile(String path) {
		File configFile = new File(path);
		ClientConfig clientConfig = new ClientConfig();

		clientConfig.setServerHost("localhost");
		clientConfig.setServerPort(1989);
		clientConfig.setVersion("0.1");
		clientConfig.setLinkUpdate("http://triedge.fr/sekai/update.json");


		try {
			JSonHelper.storeToFile(clientConfig, configFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void generateDefaultUpdateFile(String path) {
		Update update = new Update();
		update.setVersion("0.2");
		try {
			JSonHelper.storeToFile(update, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
