package entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StarshipEntity extends StarWarsEntity {
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
    private String hyperdriveRating;
    private String mglt;
    private String starshipClass;
    private List<String> films;
    private String created;
    private String edited;
    private String url;
}
