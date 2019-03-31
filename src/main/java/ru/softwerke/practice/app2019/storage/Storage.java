package ru.softwerke.practice.app2019.storage;

import ru.softwerke.practice.app2019.model.Device;

import java.util.List;
import java.util.UUID;

public interface Storage {
     UUID saveDevice(Device device);

     List<Device> getDevices(DeviceFilter filter);

     Device getDevice(UUID id);
}
