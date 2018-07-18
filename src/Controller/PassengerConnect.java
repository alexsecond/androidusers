/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.VistaPassenger;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LocationMap;
import model.OnEventListener;
import model.User;
import model.WebSocketConection;

/**
 *
 * @author Alexander
 */
public class PassengerConnect {
    private WebSocketConection conn;

    private OnEventListener listener = new OnEventListener() {
        @Override
        public void onReceiveLocations(LocationMap[] locations) {
            System.out.println(locations.toString());
            view.getLbDriversAvailable().setText("Moviles Disponibles: " + locations.length);
            if(locations.length > 0) {
                view.getBtnRequestDriver().setEnabled(true);
            }
            else {
                view.getBtnRequestDriver().setEnabled(false);
            }
        }

        @Override
        public void onReceiveMessages(Object message) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onDriverDisconnect(int id) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onResponseRequestDriver(int response, int idDriver) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onDriverConnected(Ack ack, String id) {
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
        
        @Override
        public void onPassengerConnected() {
            view.getLbConnectionState().setText("Estado: Conectado");
            view.getBtnConnect().setEnabled(true);
            view.getBtnConnect().setText("Desconectar");
        }
    };
    
    private User user;
    private VistaPassenger view;
    
    public PassengerConnect() {
        conn = new WebSocketConection(listener);
        //conn.getSocketPassenger().connect();
        view = new VistaPassenger();
        view.setVisible(true);
        initListeners();
    }
    
    public void initListeners() {
        view.getBtnConnect().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    conn.getSocketPassenger().connect();
                
                    view.getLbConnectionState().setText("Estado: Conectando..." );
                    view.getBtnConnect().setEnabled(false);
                }
                else if(e.getStateChange() == ItemEvent.DESELECTED) {
                    conn.getSocketPassenger().disconnect();
                    view.getLbConnectionState().setText("Estado: Desconectado" );
                    view.getBtnConnect().setText("Conectar");
                }
            }
        });
        
        view.getBtnRequestDriver().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
    }
    
    public static  void main(String[] args) {
        new PassengerConnect();
    }
}