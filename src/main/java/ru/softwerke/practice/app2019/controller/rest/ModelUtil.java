package ru.softwerke.practice.app2019.controller.rest;

import ru.softwerke.practice.app2019.model.Client;
import ru.softwerke.practice.app2019.model.Device;

public class ModelUtil {
    public static void checkDevice(Device device){
        if (device.getModel() == "") {
            throw new IllegalArgumentException("model of device required");
        }
        if (device.getManufacturer() == "") {
            throw new IllegalArgumentException("manufacturer of device required");
        }
        if (device.getModel().length() > 150) {
            throw new IllegalArgumentException("too long name of model");
        }
        if (device.getManufacturer().length() > 150) {
            throw new IllegalArgumentException("too long name of manufacturer");
        }
    }

    public static void checkClient(Client client){
        if (client.getFirstName() == "") {
            throw new IllegalArgumentException("first name of client required");
        }
        if (client.getLastName() == "") {
            throw new IllegalArgumentException("last name of client required");
        }
        if (client.getPatronymic() == "") {
            throw new IllegalArgumentException("patronymic of client required");
        }
        if (client.getFirstName().length() > 100) {
            throw new IllegalArgumentException("too long first name of client");
        }
        if (client.getLastName().length() > 100) {
            throw new IllegalArgumentException("too long last name of client");
        }
        if (client.getPatronymic().length() > 100) {
            throw new IllegalArgumentException("too long patronymic of client");
        }
    }


}
