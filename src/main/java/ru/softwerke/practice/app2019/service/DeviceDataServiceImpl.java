package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Device;
import ru.softwerke.practice.app2019.storage.DeviceFilter;
import ru.softwerke.practice.app2019.storage.RuntimeStorage;
import ru.softwerke.practice.app2019.storage.Storage;

import java.util.List;
import java.util.UUID;

public class DeviceDataServiceImpl implements DeviceDataService {
    private Storage storage;

    public DeviceDataServiceImpl(Storage storage){
        this.storage = storage;
    }

    @Override
    public UUID putDevice(Device device) {
        return storage.saveDevice(device);
    }

    @Override
    public Device getDeviceById(UUID id) {
        return storage.getDevice(id);
    }

    @Override
    public List<Device> getDevices(DeviceFilter filter) {
        return storage.getDevices(filter);
    }
}
