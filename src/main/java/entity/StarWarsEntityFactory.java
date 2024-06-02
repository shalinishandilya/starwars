package entity;

import entity.StarWarsEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public interface StarWarsEntityFactory {
    StarWarsEntity createEntity(JSONObject jsonObject, String type);

    default List<String> getUrls(JSONArray jsonArray) {
        List<String> urls = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                urls.add(jsonArray.optString(i));
            }
        }
        return urls;
    }
}
