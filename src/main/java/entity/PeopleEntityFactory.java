package entity;

import org.json.JSONObject;

public class PeopleEntityFactory implements StarWarsEntityFactory {
    @Override
    public StarWarsEntity createEntity(JSONObject jsonObject, String type) {
        return new PeopleEntity.PeopleEntityBuilder()
                .name(jsonObject.getString("name"))
        .height(jsonObject.getString("height"))
        .mass(jsonObject.getString("mass"))
        .hairColor(jsonObject.getString("hair_color"))
        .skinColor(jsonObject.getString("skin_color"))
        .eyeColor(jsonObject.getString("eye_color"))
        .birthYear(jsonObject.getString("birth_year"))
        .gender(jsonObject.getString("gender"))
        .homeworld(jsonObject.getString("homeworld")).build();
    }
}
