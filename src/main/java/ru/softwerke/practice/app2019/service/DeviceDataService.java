package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Device;

import java.util.List;
import java.util.UUID;

public interface DeviceDataService {
    UUID saveDevice(Device device);

    Device getDeviceById(UUID id);

    List<Device> getDevices(DeviceFilter filter);

    default List<Device> getDevices(){
        return getDevices(DeviceFilter.EMPTY);
    }
}
