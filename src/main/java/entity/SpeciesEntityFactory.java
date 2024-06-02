package entity;

import org.json.JSONObject;

public class SpeciesEntityFactory implements StarWarsEntityFactory {
    @Override
    public StarWarsEntity createEntity(JSONObject jsonObject, String type) {
        return SpeciesEntity.builder()
                .name(jsonObject.getString("name"))
                .classification(jsonObject.getString("classification"))
                .designation(jsonObject.getString("designation"))
                .averageHeight(jsonObject.getString("average_height"))
                .skinColors(jsonObject.getString("skin_colors"))
                .hairColors(jsonObject.getString("hair_colors"))
                .eyeColors(jsonObject.getString("eye_colors"))
                .averageLifespan(jsonObject.getString("average_lifespan"))
                .language(jsonObject.getString("language"))
                .created(jsonObject.getString("created"))
                .edited(jsonObject.getString("edited"))
                .url(jsonObject.getString("url"))
                .build();
    }
}
