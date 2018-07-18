package model;

import io.socket.client.Ack;
import java.util.EventListener;

/**
 * Created by Alexander on 02/10/2017.
 */

public interface OnEventListener extends EventListener {
    
    public void onReceiveLocations(LocationMap [] locations);

    public void onReceiveMessages(Object message);
    
    public void onDriverDisconnect(int id);
    
    public void onResponseRequestDriver(int response, int idDriver);

    public void onDriverConnected(Ack ack, String id);
    
    public void onDriverDisconnected();

    public void onRequestDriver(Ack ack);
    
    public void onPassengerConnected();
}
