package model;

import io.socket.client.Ack;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Alexander on 27/09/2017.
 */
public class WebSocketConection {

    private Socket socketPassenger;
    private Socket socketDriver;

    private OnEventListener listener;

    public static final int REQUEST_DRIVER_SUCCESSFUL = 1;
    public static final int REQUEST_DRIVER_BUSY = 2;
    public static final int REQUEST_DRIVER_DISCONNECTED = 3;
    public static final int REQUEST_DRIER_DENNY = 4;

    public WebSocketConection(OnEventListener listener) {
        /*IO.Options options = new IO.Options();
        options.secure = true;
        options.forceNew = true;
         */
        this.listener = listener;

        try {
            socketPassenger = IO.socket("https://javasocketio.herokuapp.com/passenger");
            //socketPassenger = IO.socket("http://localhost:8080/passenger");

            socketDriver = IO.socket("https://javasocketio.herokuapp.com/driver");
            //socketDriver = IO.socket("http://localhost:8080/driver");
            
            initListeners();

            //socketPassenger.connect();
            //socketDriver.connect();
        } catch (URISyntaxException e) {
            System.out.println(e.toString());
        }
    }

    public Socket getSocketPassenger() {
        return socketPassenger;
    }

    public Socket getSocketDriver() {
        return socketDriver;
    }

    private void initListeners() {
        if (socketPassenger == null) {
            System.out.println("socket puntero nulo");
        } else {
            socketPassenger.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    listener.onPassengerConnected();
                }
            }).on("locations", new Emitter.Listener() {
                public void call(Object... args) {
                    JSONArray jsonArray = (JSONArray) args[0];

                    LocationMap[] locations;
                    locations = JSONManager.jsonToLocationMap(jsonArray);
                    listener.onReceiveLocations(locations);

                }
            }).on("driver-disconnect", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    listener.onDriverDisconnect((Integer) args[0]);
                }

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    
                }

            });
        }

        if (socketDriver == null) {
            System.out.println("socket puntero nulo");
        } else {
            socketDriver.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                }
            }).on("socket-connected", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    System.out.println("Driver conectado");
                    Ack ack = (Ack) args[args.length - 1];
                    listener.onDriverConnected(ack, socketDriver.id());
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    listener.onDriverDisconnected();
                }

            }).on("request-target-driver", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Ack ack = (Ack) args[args.length - 1];
                    listener.onRequestDriver(ack);
                }

            });
        }
    }

    public void requestDriver(int id) {
        JSONObject data;
        try {
            data = new JSONObject("{id: " + String.valueOf(id) + "}");
            socketPassenger.emit("request-for-driver-available", data, new Ack() {

                @Override
                public void call(Object... objects) {
                    int response = (Integer) objects[0];
                    int idDriver = (Integer) objects[1];
                    listener.onResponseRequestDriver(response, idDriver);
                }
            });

            data = new JSONObject("{id: " + String.valueOf(id) + "}");
        } catch (JSONException ex) {
            Logger.getLogger(WebSocketConection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendLocation(JSONObject loc) {
        socketDriver.emit("receive-location", loc);
    }

}
