/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Alexander
 */
public class User {
    private Integer id;
    private String name;
    private String remoteId;

    private LocationMap location;

    private String currentMode;

    public static final String MODE_PASSENGER = "passenger";
    public static final String MODE_DRIVER = "driver";

    public User(int id, String name, String currentMode) {
        this.id = id;
        this.name = name;
        this.currentMode = currentMode;
    }


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getCurrentMode() {
        return currentMode;
    }

    public void switchModeUser() {
        if(currentMode == MODE_PASSENGER) {
            currentMode = MODE_DRIVER;
        }
        else {
            currentMode = MODE_PASSENGER;
        }
    }

}
