/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LocationMap;
import model.OnEventListener;
import model.WebSocketConection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Alexander
 */
public class AckTests {

    public static void main(String args[]) {

        OnEventListener listener = new OnEventListener() {
            @Override
            public void onReceiveLocations(LocationMap[] locations) {

            }

            @Override
            public void onReceiveMessages(Object message) {

            }

            @Override
            public void onDriverDisconnect(int id) {

            }

            @Override
            public void onResponseRequestDriver(int response, int idDriver) {
                
                System.out.println("id driver: " + idDriver);
                
                switch (response) {
                    case WebSocketConection.REQUEST_DRIVER_SUCCESSFUL:
                        System.out.println("Solicitud exitosa");
                        break;
                    case WebSocketConection.REQUEST_DRIVER_BUSY:
                        System.out.println("ocupado");
                        break;
                    case WebSocketConection.REQUEST_DRIVER_DISCONNECTED:
                        System.out.println("desconectado");
                        break;
                    default:
                        System.out.println("ninguno");
                        break;
                }
            }

            @Override
            public void onDriverConnected() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onDriverDisconnected() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onRequestDriver(Ack ack) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };

        WebSocketConection conn = new WebSocketConection(listener);

        try {
            Socket socket;
            socket = IO.socket("https://javasocketio.herokuapp.com/passenger");
            //socket = IO.socket("http://localhost:8080/passenger");

            socket.connect();

            socket.emit("drivers-available", "data", new Ack() {
                @Override
                public void call(Object... objects) {
                    JSONArray locs = (JSONArray) objects[0];
                    System.out.println(objects[0].getClass().toString());
                    System.out.println(locs.length());
                    System.out.println(locs.toString());

                    ThreadGroup group = new ThreadGroup("group");

                    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

                    for (int i = 0; i < locs.length(); i++) {

                        try {
                            JSONObject current = (JSONObject) locs.get(i);
                            int id = current.getInt("id");
                            double lat = current.getDouble("lat");
                            double lng = current.getDouble("lng");
                            new Thread(group, new Runnable() {
                                @Override
                                public void run() {
                                    String jsonRequest = getDistance(lat, lng);
                                    try {
                                        JSONObject response = new JSONObject(jsonRequest);
                                        JSONArray rows = response.getJSONArray("rows");

                                        JSONObject element = (JSONObject) rows.get(0);
                                        JSONObject atributes = (JSONObject) element.getJSONArray("elements").get(0);
                                        JSONObject duration = atributes.getJSONObject("duration");

                                        int valueDuration = duration.getInt("value");
                                        map.put(id, valueDuration);
                                        System.out.println(valueDuration);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, String.valueOf(id)).start();

                        } catch (JSONException ex) {
                            Logger.getLogger(AckTests.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                    System.out.println(group.activeCount() + " hilos activos");
                    while (group.activeCount() > 0) {
                        System.out.println(group.activeCount() + " hilos activos");
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AckTests.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    System.out.println("Termino");
                    System.out.println(map.toString());

                    socket.disconnect();

                    conn.getSocketPassenger().connect();
                    
                    Set entrySet = map.entrySet();

                    Iterator it = entrySet.iterator();
                    if (it.hasNext()) {
                        Map.Entry<Integer, Integer> mp = (Map.Entry) it.next();

                        conn.requestDriver(mp.getKey());

                    }

                    /*for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                        System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                        conn.requestDriver(entry.getKey());
                    }*/
                }
            });

        } catch (URISyntaxException ex) {
            Logger.getLogger(AckTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getDistance(double lat, double lng) {
        String res = "";
        try {
            String url;
            url = "https://maps.googleapis.com/maps/api/distancematrix/json?"
                    + "origins=-17.383561,-66.275178&destinations="
                    + String.valueOf(lat) + "," + String.valueOf(lng)
                    + "&key=AIzaSyDOg_-7Eii5avPw-7lQZYTYwq1UUufrAj4";

            URL urlRequest = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlRequest.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            connection.disconnect();

            //System.out.println(response.toString());
            res = response.toString();

        } catch (UnsupportedEncodingException ex) {
            //Logger.getLogger(Urlrequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            //Logger.getLogger(Urlrequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(Urlrequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}
