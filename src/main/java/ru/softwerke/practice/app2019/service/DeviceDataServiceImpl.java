package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Device;
import ru.softwerke.practice.app2019.storage.Storage;
import ru.softwerke.practice.app2019.storage.filter.FilterConditional;
import ru.softwerke.practice.app2019.storage.filter.StorageFilter;

import java.util.List;
import java.util.UUID;

public class DeviceDataServiceImpl implements DeviceDataService {
    private Storage<Device> storage;

    public DeviceDataServiceImpl(Storage<Device> storage) {
        this.storage = storage;
    }

    @Override
    public Device saveDevice(Device device) {
        UUID id = UUID.randomUUID();
        device.setId(id);
        storage.save(device);
        return device;
    }

    @Override
    public Device getDeviceById(UUID id) {
        return storage.get(id);
    }

    @Override
    public List<Device> getDevices(DeviceFilter filter) {
        StorageFilter<Device> storageFilter = new StorageFilter<>();

        if (filter.getManufacturer() != null) {
            storageFilter.addCondition(FilterConditional.on(Device::getManufacturer).eq(filter.getManufacturer()));
        }

        if (filter.getModel() != null) {
            storageFilter.addCondition(FilterConditional.on(Device::getModel).eq(filter.getModel()));
        }

        if (filter.getColor() != null) {
            storageFilter.addCondition(FilterConditional.on(Device::getColor).eq(filter.getColor()));
        }

        storageFilter.addCondition(FilterConditional.on(Device::getPrice).inRange(filter.getPriceFrom(), filter.getPriceTo()));
        storageFilter.addCondition(FilterConditional.on(Device::getDate).inRange(filter.getDateFrom(), filter.getDateTo()));

        storageFilter.addAllSorting(Device.FIELD_PROVIDER, filter.getSortConditionals());
        storageFilter.setCount(filter.getCount());
        storageFilter.setPageNumber(filter.getPageNumber());
        return storage.get(storageFilter);
    }
}
