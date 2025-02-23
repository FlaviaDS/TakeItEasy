package org.example.utils;

import org.example.model.HexTile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileDeckManager {
    private final List<HexTile> tileDeck = new ArrayList<>();

    public void loadTiles(List<HexTile> tiles) {
        tileDeck.clear();
        tileDeck.addAll(tiles);
        Collections.shuffle(tileDeck);
    }

    public HexTile drawTile() {
        return !tileDeck.isEmpty() ? tileDeck.remove(0) : null;
    }

    public int getRemainingTiles() {
        return tileDeck.size();
    }
}
