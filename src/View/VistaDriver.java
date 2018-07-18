/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author Alexander
 */
public class VistaDriver extends javax.swing.JFrame {

    /**
     * Creates new form VistaDriver
     */
    public VistaDriver() {
        initComponents();
    }
    
    public void addListenerConnect(ActionListener listener) {
        connect.addActionListener(listener);
    }
    
    public void addListenerAccept(ActionListener listener) {
        responseAccept.addActionListener(listener);
    }
    
    public void addListenerDenny(ActionListener listener) {
        responseDenny.addActionListener(listener);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        idDriver = new javax.swing.JTextField();
        requestService = new javax.swing.JTextField();
        currentLocation = new javax.swing.JTextField();
        connectionState = new javax.swing.JTextField();
        connect = new javax.swing.JButton();
        disconnect = new javax.swing.JButton();
        responseAccept = new javax.swing.JButton();
        responseDenny = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        idDriver.setEditable(false);
        idDriver.setText("Id Conductor: ");
        idDriver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idDriverActionPerformed(evt);
            }
        });

        requestService.setEditable(false);
        requestService.setText("Solicitud de servicio:  ");
        requestService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestServiceActionPerformed(evt);
            }
        });

        currentLocation.setEditable(false);
        currentLocation.setText("Posicion actual: ");
        currentLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentLocationActionPerformed(evt);
            }
        });

        connectionState.setEditable(false);
        connectionState.setText("Estado de conexion: ");
        connectionState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectionStateActionPerformed(evt);
            }
        });

        connect.setText("Conectar");

        disconnect.setText("Desconectar");
        disconnect.setEnabled(false);

        responseAccept.setText("Responder Aceptar");
        responseAccept.setEnabled(false);

        responseDenny.setText("Responder Denegar");
        responseDenny.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(responseDenny)
                    .addComponent(responseAccept)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(connect)
                        .addGap(18, 18, 18)
                        .addComponent(disconnect))
                    .addComponent(connectionState)
                    .addComponent(idDriver)
                    .addComponent(requestService, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                    .addComponent(currentLocation))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(connectionState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(idDriver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(requestService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(currentLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connect)
                    .addComponent(disconnect))
                .addGap(18, 18, 18)
                .addComponent(responseAccept)
                .addGap(18, 18, 18)
                .addComponent(responseDenny)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void idDriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idDriverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idDriverActionPerformed

    private void requestServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_requestServiceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_requestServiceActionPerformed

    private void currentLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_currentLocationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_currentLocationActionPerformed

    private void connectionStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectionStateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_connectionStateActionPerformed

    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaDriver().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton connect;
    private javax.swing.JTextField connectionState;
    private javax.swing.JTextField currentLocation;
    private javax.swing.JButton disconnect;
    private javax.swing.JTextField idDriver;
    private javax.swing.JTextField requestService;
    private javax.swing.JButton responseAccept;
    private javax.swing.JButton responseDenny;
    // End of variables declaration//GEN-END:variables

    public JTextField getConnectionState() {
        return connectionState;
    }
    
    public JTextField getIdDriver() {
        return idDriver;
    }
    
    public void enableConnect(boolean b) {
        connect.setEnabled(b);
    }

    public void enableDisconnect(boolean b) {
        disconnect.setEnabled(b);
    }

    public void addListenerDisconnect(ActionListener listener) {
        disconnect.addActionListener(listener);
    }

    public JTextField getCurrentLocation() {
        return currentLocation;
    }
    
    public JTextField getRequestService() {
        return requestService;
    }
    
    public JButton getResponseAccept() {
        return responseAccept;
    }
    
    public JButton getResponseDenny() {
        return responseDenny;
    }
}
