/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.VistaDriver;
import io.socket.client.Ack;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.JSONManager;
import model.LocationMap;
import model.OnEventListener;
import model.User;
import model.WebSocketConection;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Alexander
 */
public class Controller implements ActionListener {
    
    private Ack ackResp;

    private OnEventListener events = new OnEventListener() {
        @Override
        public void onReceiveLocations(LocationMap[] locations) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onReceiveMessages(Object message) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onDriverDisconnect(int id) {
            view.getConnectionState().setText("Estado de conexion: Desconectado");
        }

        @Override
        public void onResponseRequestDriver(int response, int idDriver) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onDriverConnected(Ack ack, String idDriver) {
            
            JSONObject jsonLoc= new JSONObject();
            try {
                jsonLoc.put("id", idDriver);
                jsonLoc.put("lat", 0.0);
                jsonLoc.put("lng", 0.0);
                jsonLoc.put("busy", false);
                
            } catch (JSONException e) {
                System.out.println(idDriver);
                e.printStackTrace();
            }
            
            //sockets.getSocketDriver().emit("store-data", jsonLoc);
            ack.call(jsonLoc);
            
            view.getConnectionState().setText("Estado de conexion: Conectado");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendingLocation = true;
                    while (sendingLocation) {
                        
                        double lat = -17.38 - Math.random() / 100;
                        double lng = -66.27 - Math.random() / 100;
                        
                        String text = "lat: " + lat + " lng: " + lng;
                        view.getCurrentLocation().setText("Posicion actual: " + text);
                        
                        JSONObject JSONLoc = new JSONObject();
                        
                        try {
                            JSONLoc.put("lat", lat);
                            JSONLoc.put("lng", lng);
                        } catch (JSONException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        if(driverBusy) {
                            sockets.getSocketDriver().emit("send-location-to-passenger", JSONLoc);
                        }
                        else {
                            sockets.sendLocation(JSONLoc);
                        }
                        
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }).start();
        }

        @Override
        public void onDriverDisconnected() {

            view.getConnectionState().setText("Estado de conexion: Desconectado");
            view.getCurrentLocation().setText("Posicion actual: ");
            sendingLocation = false;
        }

        @Override
        public void onRequestDriver(Ack ack) {
            ackResp = ack;
            
            view.getRequestService().setText("Solicitud de servicio: Pasajero solicitando taxi...");
            view.getResponseAccept().setEnabled(true);
            view.getResponseDenny().setEnabled(true);
        }

        @Override
        public void onPassengerConnected() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    private User user;
    private VistaDriver view;
    private WebSocketConection sockets;
    
    

    private boolean sendingLocation = false;
    private boolean driverBusy = false;

    public Controller(User user) {
        this.user = user;
        sockets = new WebSocketConection(events);

        view = new VistaDriver();
        view.setVisible(true);

        view.addListenerConnect(this);
        view.addListenerDisconnect(this);
        view.addListenerAccept(this);
        view.addListenerDenny(this);
    }

    public static void main(String[] arg) {
        new Controller(new User(200, "Alexander", User.MODE_DRIVER));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());

        if (e.getActionCommand() == "Conectar") {
            sockets.getSocketDriver().connect();
            view.enableConnect(false);
            view.enableDisconnect(true);
            view.getConnectionState().setText("Estado de conexion: Conectando ...");
            view.getIdDriver().setText("Id Conductor: " + user.getId());
            
        } else if (e.getActionCommand() == "Desconectar") {
            sockets.getSocketDriver().disconnect();
            view.enableConnect(true);
            view.enableDisconnect(false);
            view.getIdDriver().setText("Id Conductor: ");
            view.getRequestService().setText("Solicitud de servicio:");
            
        } else if(e.getActionCommand() == "Responder Aceptar") {
            view.getResponseAccept().setEnabled(false);
            view.getResponseDenny().setEnabled(false);
            if(ackResp != null) {
                ackResp.call(1);
                ackResp = null;
                driverBusy = true;
            }
            
            view.getRequestService().setText("Solicitud de servicio: Confirmacion exitosa..");
            
            System.out.println("Responder Aceptar");
            
        } else if(e.getActionCommand() == "Responder Denegar") {
            view.getResponseAccept().setEnabled(false);
            view.getResponseDenny().setEnabled(false);
            if(ackResp != null) {
                ackResp.call(4);
                ackResp = null;
            }
            
            view.getRequestService().setText("Solicitud de servicio: Se ha denegado..");
            
            System.out.println("Responder Denegar");
        } else {
            System.out.println(e.getActionCommand());
        }
    }
}
