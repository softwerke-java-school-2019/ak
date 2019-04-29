package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Device;

import java.util.List;
import java.util.UUID;

public interface DeviceService {
    Device saveDevice(Device device);

    Device getDeviceById(int id);

    List<Device> getDevices(DeviceFilter filter);
}
