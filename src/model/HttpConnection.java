package model;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Alexander on 26/10/2017.
 */

public class HttpConnection{

    private String url;
    private String response;

    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";

    public HttpConnection(String url) {
        this.url = url;
        response = null;
    }

    public String sendGetMethodRequest(Map<String,String> params) throws IOException {
        String res = "";
        String urlTarget = this.url + "?";

        for (Map.Entry<String, String> entry : params.entrySet()) {
            urlTarget += entry.getKey() + "=" + entry.getValue() + "&";
        }
        urlTarget = urlTarget.substring(0, urlTarget.length() - 1);

        URL urlRequest = new URL(urlTarget);
        
        System.out.println(urlRequest);
        
        HttpURLConnection connection = (HttpURLConnection) urlRequest.openConnection();

        connection.setRequestMethod(METHOD_GET);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        connection.disconnect();

        res = response.toString();
        return res;
    }

    public void sendPostMethodRequest(Map<String, String> params) throws IOException {
        this.response = null;
        
        String values = "";
        int length = params.size();
        int i = 0;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if(length - i > 1) {
                values += entry.getKey() + "=" + entry.getValue() + "&";
            }
            else {
                values += entry.getKey() + "=" + entry.getValue();
            }
            i++;
        }
        
        try {
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(values);
            wr.flush();
            wr.close();
            
            conn.getResponseCode();   
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
 
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            //Mostramos la respuesta del servidor por consola
            System.out.println(response);
            //cerramos la conexión
            in.close();
            
            this.response = response.toString();
            
            conn.disconnect();
        } catch (MalformedURLException ex) {
            Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
    }
    
    public String getResponse() {
        return response;
    }
    
    public static void main(String arg[]) throws IOException { 
        
        HttpConnection conn = new 
            HttpConnection("http://localhost/practicas/usuarios.php");
        Map<String, String> map= new HashMap<String, String>();
        map.put("username", "alex");
        
        String id = "1";
        
        try {
            conn.sendPostMethodRequest(map);
            id = conn.getResponse();
            System.out.println("Se inserto un usuario con id: " + conn.getResponse());
        } catch (IOException ex) {
            Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        
        Map<String, String> map1= new HashMap<String, String>();
        map1.put("id_user", id);
        String resp = conn.sendGetMethodRequest(map1);
        System.out.println(resp);
    }

}
