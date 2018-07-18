package model;

import com.google.gson.Gson;
import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alexander on 27/09/2017.
 */

public class JSONManager {
    public static LocationMap[] jsonToLocationMap(JSONArray jsonArray) {
        String json = jsonArray.toString();
        System.out.println(json);
        LocationMap[] locations = new Gson().fromJson(json, LocationMap[].class);
        return locations;
    }

    public static String locationMapToJson(LocationMap loc) {
        String jsonLocation = new Gson().toJson(loc);
        return jsonLocation;
    }

    public static JSONObject locationMapToJSONObject(LocationMap lm) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSONManager.locationMapToJson(lm));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}