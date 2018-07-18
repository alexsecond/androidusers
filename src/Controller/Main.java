/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.HttpConnection;

/**
 *
 * @author Alexander
 */
public class Main {

    public static void main(String[] args) {
        try {
            String jsonDirections;
            String url = "https://maps.googleapis.com/maps/api/directions/json";
            Map<String, String> map = new HashMap<String, String>();
            map.put("origin", "-17.384107,-66.276266");
            map.put("destination", "-17.379257,-66.161987");
            map.put("key", "AIzaSyDOg_-7Eii5avPw-7lQZYTYwq1UUufrAj4");
            
            
            
            HttpConnection conn = new HttpConnection(url);
            jsonDirections = conn.sendGetMethodRequest(map);
            try {
                JSONObject obj = new JSONObject(jsonDirections);
                JSONArray legs = obj.getJSONArray("routes")
                        .getJSONObject(0)
                        .getJSONArray("legs");
                
                JSONArray[] steps = new JSONArray[legs.length()];
                
                JSONArray step;
                for (int i = 0; i < legs.length(); i++) {
                    step = legs.getJSONObject(i).getJSONArray("steps");
                    steps[i] = step;
                    
                    JSONObject currentNode;
                    JSONObject endLocation;
                    for (int j = 0; j < step.length(); j++) {
                        currentNode = step.getJSONObject(j);
                        endLocation = currentNode.getJSONObject("end_location");
                        System.out.print(endLocation.getDouble("lat") + ":");
                        System.out.println(endLocation.getDouble("lng"));
                    }
                }
                
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
