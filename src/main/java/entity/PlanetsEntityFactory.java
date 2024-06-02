package entity;

import org.json.JSONObject;

public class PlanetsEntityFactory implements StarWarsEntityFactory {
    @Override
    public PlanetsEntity createEntity(JSONObject jsonObject, String type) {
        return new PlanetsEntity.Builder()
            .setType(type)
            .setName(jsonObject.optString("name"))
            .setRotationPeriod(jsonObject.optString("rotation_period"))
            .setOrbitalPeriod(jsonObject.optString("orbital_period"))
            .setDiameter(jsonObject.optString("diameter"))
            .setClimate(jsonObject.optString("climate"))
            .setGravity(jsonObject.optString("gravity"))
            .setTerrain(jsonObject.optString("terrain"))
            .setSurfaceWater(jsonObject.optString("surface_water"))
            .setPopulation(jsonObject.optString("population"))
            .setResidents(getUrls(jsonObject.optJSONArray("residents")))
            .setFilms(getUrls(jsonObject.optJSONArray("films")))
            .setCreated(jsonObject.optString("created"))
            .setEdited(jsonObject.optString("edited"))
            .setUrl(jsonObject.optString("url"))
            .build();
    }
}

