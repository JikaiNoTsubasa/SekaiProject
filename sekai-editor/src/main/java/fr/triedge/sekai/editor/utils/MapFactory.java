package fr.triedge.sekai.editor.utils;

import fr.triedge.sekai.editor.model.EditableMap;
import fr.triedge.sekai.editor.model.Tile;
import fr.triedge.sekai.editor.model.TileType;

public class MapFactory {
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
}
