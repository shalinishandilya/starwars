package entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilmsEntityFactory implements StarWarsEntityFactory {
    @Override
    public StarWarsEntity createEntity(JSONObject jsonObject, String type) {
        List<String> characters = parseJSONArrayToList(jsonObject.getJSONArray("characters"));
        List<String> planets = parseJSONArrayToList(jsonObject.getJSONArray("planets"));
        List<String> starships = parseJSONArrayToList(jsonObject.getJSONArray("starships"));
        List<String> vehicles = parseJSONArrayToList(jsonObject.getJSONArray("vehicles"));
        List<String> species = parseJSONArrayToList(jsonObject.getJSONArray("species"));

        return FilmsEntity.builder()
                .title(jsonObject.getString("title"))
                .episodeId(jsonObject.getInt("episode_id"))
                .openingCrawl(jsonObject.getString("opening_crawl"))
                .director(jsonObject.getString("director"))
                .producer(jsonObject.getString("producer"))
                .releaseDate(jsonObject.getString("release_date"))
                .characters(characters)
                .planets(planets)
                .starships(starships)
                .vehicles(vehicles)
                .species(species)
                .created(jsonObject.getString("created"))
                .edited(jsonObject.getString("edited"))
                .url(jsonObject.getString("url"))
                .build();
    }

    private List<String> parseJSONArrayToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}
