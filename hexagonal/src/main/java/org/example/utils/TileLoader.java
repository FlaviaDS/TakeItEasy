package org.example.utils;

import org.example.model.HexTile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TileLoader {
    private static final String TILE_JSON = """
        [
            {"idTile":0,"topPath":1,"rightPath":3,"leftPath":2},
            {"idTile":1,"topPath":5,"rightPath":3,"leftPath":2},
            {"idTile":2,"topPath":9,"rightPath":3,"leftPath":2},
            {"idTile":3,"topPath":1,"rightPath":4,"leftPath":2},
            {"idTile":4,"topPath":5,"rightPath":4,"leftPath":2},
            {"idTile":5,"topPath":9,"rightPath":4,"leftPath":2},
            {"idTile":6,"topPath":1,"rightPath":8,"leftPath":2},
            {"idTile":7,"topPath":5,"rightPath":8,"leftPath":2},
            {"idTile":8,"topPath":9,"rightPath":8,"leftPath":2},
            {"idTile":9,"topPath":1,"rightPath":3,"leftPath":6},
            {"idTile":10,"topPath":5,"rightPath":3,"leftPath":6},
            {"idTile":11,"topPath":9,"rightPath":3,"leftPath":6},
            {"idTile":12,"topPath":1,"rightPath":4,"leftPath":6},
            {"idTile":13,"topPath":5,"rightPath":4,"leftPath":6},
            {"idTile":14,"topPath":9,"rightPath":4,"leftPath":6},
            {"idTile":15,"topPath":1,"rightPath":8,"leftPath":6},
            {"idTile":16,"topPath":5,"rightPath":8,"leftPath":6},
            {"idTile":17,"topPath":9,"rightPath":8,"leftPath":6},
            {"idTile":18,"topPath":1,"rightPath":3,"leftPath":7},
            {"idTile":19,"topPath":5,"rightPath":3,"leftPath":7},
            {"idTile":20,"topPath":9,"rightPath":3,"leftPath":7},
            {"idTile":21,"topPath":1,"rightPath":4,"leftPath":7},
            {"idTile":22,"topPath":5,"rightPath":4,"leftPath":7},
            {"idTile":23,"topPath":9,"rightPath":4,"leftPath":7},
            {"idTile":24,"topPath":1,"rightPath":8,"leftPath":7},
            {"idTile":25,"topPath":5,"rightPath":8,"leftPath":7},
            {"idTile":26,"topPath":9,"rightPath":8,"leftPath":7}
        ]
    """;

    public static List<HexTile> loadTiles() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, Integer>> tileData = objectMapper.readValue(TILE_JSON, new TypeReference<>() {});
            List<HexTile> tiles = new ArrayList<>();
            for (Map<String, Integer> data : tileData) {
                tiles.add(new HexTile(data.get("topPath"), data.get("rightPath"), data.get("leftPath")));
            }
            return tiles;
        } catch (IOException e) {
            throw new RuntimeException("Error in Tiles loading", e);
        }
    }
}
