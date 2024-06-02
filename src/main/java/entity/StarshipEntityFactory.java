package entity;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class StarshipEntityFactory implements StarWarsEntityFactory {
    @Override
    public StarWarsEntity createEntity(JSONObject jsonObject, String type) {
        List<String> films = new ArrayList<>();
        JSONArray filmsArray = jsonObject.getJSONArray("films");
        for (int i = 0; i < filmsArray.length(); i++) {
            films.add(filmsArray.getString(i));
        }

        return StarshipEntity.builder()
                .name(jsonObject.getString("name"))
                .model(jsonObject.getString("model"))
                .manufacturer(jsonObject.getString("manufacturer"))
                .costInCredits(jsonObject.getString("cost_in_credits"))
                .length(jsonObject.getString("length"))
                .maxAtmospheringSpeed(jsonObject.getString("max_atmosphering_speed"))
                .crew(jsonObject.getString("crew"))
                .passengers(jsonObject.getString("passengers"))
                .cargoCapacity(jsonObject.getString("cargo_capacity"))
                .consumables(jsonObject.getString("consumables"))
                .hyperdriveRating(jsonObject.getString("hyperdrive_rating"))
                .mglt(jsonObject.getString("MGLT"))
                .starshipClass(jsonObject.getString("starship_class"))
                .films(films)
                .created(jsonObject.getString("created"))
                .edited(jsonObject.getString("edited"))
                .url(jsonObject.getString("url"))
                .build();
    }
}
