package entity;

import org.json.JSONObject;

public class VehiclesEntityFactory implements StarWarsEntityFactory {
    @Override
    public VehiclesEntity createEntity(JSONObject jsonObject, String type) {
        return new VehiclesEntity.Builder()
            .setName(jsonObject.optString("name"))
            .setModel(jsonObject.optString("model"))
            .setManufacturer(jsonObject.optString("manufacturer"))
            .setCostInCredits(jsonObject.optString("cost_in_credits"))
            .setLength(jsonObject.optString("length"))
            .setMaxAtmospheringSpeed(jsonObject.optString("max_atmosphering_speed"))
            .setCrew(jsonObject.optString("crew"))
            .setPassengers(jsonObject.optString("passengers"))
            .setCargoCapacity(jsonObject.optString("cargo_capacity"))
            .setConsumables(jsonObject.optString("consumables"))
            .setVehicleClass(jsonObject.optString("vehicle_class"))
            .setPilots(getUrls(jsonObject.optJSONArray("pilots")))
            .setFilms(getUrls(jsonObject.optJSONArray("films")))
            .setCreated(jsonObject.optString("created"))
            .setEdited(jsonObject.optString("edited"))
            .setUrl(jsonObject.optString("url"))
            .build();
    }
}