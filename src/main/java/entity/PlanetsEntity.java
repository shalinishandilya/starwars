package entity;


import lombok.Data;

import java.util.List;

@Data
public class PlanetsEntity extends StarWarsEntity {
    private String type;
    private String name;
    private String rotationPeriod;
    private String orbitalPeriod;
    private String diameter;
    private String climate;
    private String gravity;
    private String terrain;
    private String surfaceWater;

    private String created;

    private String edited;
    private String population;
    private List<String> residents;
    private List<String> films;

    public PlanetsEntity(Builder builder) {
        this.type = builder.type;
        this.name = builder.name;
        this.rotationPeriod = builder.rotationPeriod;
        this.orbitalPeriod = builder.orbitalPeriod;
        this.diameter = builder.diameter;
        this.climate = builder.climate;
        this.gravity = builder.gravity;
        this.terrain = builder.terrain;
        this.surfaceWater = builder.surfaceWater;
        this.population = builder.population;
        this.residents = builder.residents;
        this.films = builder.films;
        this.created = builder.created;
        this.edited = builder.edited;

    }

    public static class Builder {
        private String type;
        private String name;
        private String created;
        private String edited;

        private String url;
        private String rotationPeriod;
        private String orbitalPeriod;
        private String diameter;
        private String climate;
        private String gravity;
        private String terrain;
        private String surfaceWater;
        private String population;
        private List<String> residents;
        private List<String> films;

        public Builder setCreated(String created) {
            this.created = created;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setRotationPeriod(String rotationPeriod) {
            this.rotationPeriod = rotationPeriod;
            return this;
        }

        public Builder setOrbitalPeriod(String orbitalPeriod) {
            this.orbitalPeriod = orbitalPeriod;
            return this;
        }

        public Builder setDiameter(String diameter) {
            this.diameter = diameter;
            return this;
        }

        public Builder setClimate(String climate) {
            this.climate = climate;
            return this;
        }

        public Builder setGravity(String gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setTerrain(String terrain) {
            this.terrain = terrain;
            return this;
        }

        public Builder setSurfaceWater(String surfaceWater) {
            this.surfaceWater = surfaceWater;
            return this;
        }

        public Builder setPopulation(String population) {
            this.population = population;
            return this;
        }

        public Builder setResidents(List<String> residents) {
            this.residents = residents;
            return this;
        }

        public Builder setFilms(List<String> films) {
            this.films = films;
            return this;
        }

        public PlanetsEntity build() {
            return new PlanetsEntity(this);
        }

        public Builder setEdited(String edited) {
            this.edited = edited;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }
    }

    // Getters
    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getRotationPeriod() {
        return rotationPeriod;
    }

    public String getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public String getDiameter() {
        return diameter;
    }

    public String getClimate() {
        return climate;
    }

    public String getGravity() {
        return gravity;
    }

    public String getTerrain() {
        return terrain;
    }

    public String getSurfaceWater() {
        return surfaceWater;
    }

    public String getPopulation() {
        return population;
    }

    public List<String> getResidents() {
        return residents;
    }

    public List<String> getFilms() {
        return films;
    }

    public String getCreated() {
        return created;
    }

    public String getEdited() {
        return edited;
    }
}
