/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocketclientio;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

/**
 *
 * @author Alexander
 */
public class WebSocketClientIO {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            /*
            IO.Options options = new IO.Options();
            options.secure = true;
            options.forceNew = true;
            
            //Socket socket = IO.socket("https://javasocketio.herokuapp.com/", options);
            Socket socket = IO.socket("http://localhost:8080", options);
            socket.connect();
            //socket.send("Hello Server");
            socket.emit("new-message", "argument1");
            //socket.disconnect();
             */
            //Socket socket = IO.socket("https://javasocketio.herokuapp.com/");
            Socket socket = IO.socket("http://localhost:8080/room1");
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    socket.emit("new-message", "hi");
                    //socket.disconnect();
                }

            }).on("messages", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    System.out.println("Se ha recibido algun mensaje");
                    for (Object baz : args) {
                        System.out.println(baz.getClass());
                    }
                }

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    System.out.println("Se ha desconectado");
                }

            });
            socket.connect();
            socket.emit("prueba", "emitiendo");
        } catch (URISyntaxException ex) {
            Logger.getLogger(WebSocketClientIO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
