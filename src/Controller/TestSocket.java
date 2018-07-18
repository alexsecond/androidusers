/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexander
 */
public class TestSocket {

    public static void main(String[] args) {
        Socket socket;
        try {
            socket = IO.socket("http://localhost:8080/test");
            socket.connect();

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... os) {
                    System.out.println("Se conecto con exito!!!");
                    System.out.println(os.length);
                }
            }).on("socket-connected", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    System.out.println(os[0].toString());
                    System.out.println("Socket conectado");
                }
            }).on("ack", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Generando una espera para dar una respuesta");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TestSocket.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    Ack ack = (Ack) args[args.length - 1];
                    ack.call("dato enviado");
                    System.out.println("se envio");
                }
            });;
        } catch (URISyntaxException ex) {
            Logger.getLogger(TestSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
