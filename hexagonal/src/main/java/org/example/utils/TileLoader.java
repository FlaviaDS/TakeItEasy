package org.example.utils;
import org.example.model.HexTile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TileLoader {
    private static final String TILE_JSON = """
        [
            {"idTile":0,"leftPath":2,"rightPath":3,"topPath":1},
            {"idTile":1,"leftPath":2,"rightPath":3,"topPath":5},
            {"idTile":2,"leftPath":2,"rightPath":3,"topPath":9},
            {"idTile":3,"leftPath":2,"rightPath":4,"topPath":1},
            {"idTile":4,"leftPath":2,"rightPath":4,"topPath":5},
            {"idTile":5,"leftPath":2,"rightPath":4,"topPath":9},
            {"idTile":6,"leftPath":2,"rightPath":8,"topPath":1},
            {"idTile":7,"leftPath":2,"rightPath":8,"topPath":5},
            {"idTile":8,"leftPath":2,"rightPath":8,"topPath":9},
            {"idTile":9,"leftPath":6,"rightPath":3,"topPath":1},
            {"idTile":10,"leftPath":6,"rightPath":3,"topPath":5},
            {"idTile":11,"leftPath":6,"rightPath":3,"topPath":9},
            {"idTile":12,"leftPath":6,"rightPath":4,"topPath":1},
            {"idTile":13,"leftPath":6,"rightPath":4,"topPath":5},
            {"idTile":14,"leftPath":6,"rightPath":4,"topPath":9},
            {"idTile":15,"leftPath":6,"rightPath":8,"topPath":1},
            {"idTile":16,"leftPath":6,"rightPath":8,"topPath":5},
            {"idTile":17,"leftPath":6,"rightPath":8,"topPath":9},
            {"idTile":18,"leftPath":7,"rightPath":3,"topPath":1},
            {"idTile":19,"leftPath":7,"rightPath":3,"topPath":5},
            {"idTile":20,"leftPath":7,"rightPath":3,"topPath":9},
            {"idTile":21,"leftPath":7,"rightPath":4,"topPath":1},
            {"idTile":22,"leftPath":7,"rightPath":4,"topPath":5},
            {"idTile":23,"leftPath":7,"rightPath":4,"topPath":9},
            {"idTile":24,"leftPath":7,"rightPath":8,"topPath":1},
            {"idTile":25,"leftPath":7,"rightPath":8,"topPath":5},
            {"idTile":26,"leftPath":7,"rightPath":8,"topPath":9}
        ]
    """;

    private static final List<HexTile> tileDeck = new ArrayList<>();

    public static void loadTiles() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, Integer>> tileData = objectMapper.readValue(TILE_JSON, new TypeReference<>() {});
            tileDeck.clear();
            for (Map<String, Integer> data : tileData) {
                tileDeck.add(new HexTile(data.get("leftPath"), data.get("rightPath"), data.get("topPath")));
            }
            Collections.shuffle(tileDeck);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HexTile drawTile() {
        if (!tileDeck.isEmpty()) {
            return tileDeck.removeFirst();
        }
        return null;
    }

    public static int getRemainingTiles() {
        return tileDeck.size();
    }


}