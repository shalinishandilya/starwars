package entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VehiclesEntity extends StarWarsEntity {
    private String name;
    private String model;
    private String manufacturer;
    private String costInCredits;
    private String length;
    private String maxAtmospheringSpeed;
    private String crew;
    private String passengers;
    private String cargoCapacity;
    private String consumables;
    private String vehicleClass;
    private List<String> pilots;
    private List<String> films;
    private String created;
    private String edited;
    private String url;



    public static class Builder {
        private String name;
        private String model;
        private String manufacturer;
        private String costInCredits;
        private String length;
        private String maxAtmospheringSpeed;
        private String crew;
        private String passengers;
        private String cargoCapacity;
        private String consumables;
        private String vehicleClass;
        private List<String> pilots = new ArrayList<>();
        private List<String> films = new ArrayList<>();
        private String created;
        private String edited;
        private String url;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public Builder setCostInCredits(String costInCredits) {
            this.costInCredits = costInCredits;
            return this;
        }

        public Builder setLength(String length) {
            this.length = length;
            return this;
        }

        public Builder setMaxAtmospheringSpeed(String maxAtmospheringSpeed) {
            this.maxAtmospheringSpeed = maxAtmospheringSpeed;
            return this;
        }

        public Builder setCrew(String crew) {
            this.crew = crew;
            return this;
        }

        public Builder setPassengers(String passengers) {
            this.passengers = passengers;
            return this;
        }

        public Builder setCargoCapacity(String cargoCapacity) {
            this.cargoCapacity = cargoCapacity;
            return this;
        }

        public Builder setConsumables(String consumables) {
            this.consumables = consumables;
            return this;
        }

        public Builder setVehicleClass(String vehicleClass) {
            this.vehicleClass = vehicleClass;
            return this;
        }

        public Builder setPilots(List<String> pilots) {
            this.pilots = pilots;
            return this;
        }

        public Builder setFilms(List<String> films) {
            this.films = films;
            return this;
        }

        public Builder setCreated(String created) {
            this.created = created;
            return this;
        }

        public Builder setEdited(String edited) {
            this.edited = edited;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public VehiclesEntity build() {
            VehiclesEntity entity = new VehiclesEntity();
            entity.name = this.name;
            entity.model = this.model;
            entity.manufacturer = this.manufacturer;
            entity.costInCredits = this.costInCredits;
            entity.length = this.length;
            entity.maxAtmospheringSpeed = this.maxAtmospheringSpeed;
            entity.crew = this.crew;
            entity.passengers = this.passengers;
            entity.cargoCapacity = this.cargoCapacity;
            entity.consumables = this.consumables;
            entity.vehicleClass = this.vehicleClass;
            entity.pilots = this.pilots;
            entity.films = this.films;
            entity.created = this.created;
            entity.edited = this.edited;
            entity.url = this.url;
            return entity;
        }
    }
}
