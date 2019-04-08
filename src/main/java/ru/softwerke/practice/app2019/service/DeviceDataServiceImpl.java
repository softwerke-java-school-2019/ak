package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Device;
import ru.softwerke.practice.app2019.storage.Storage;
import ru.softwerke.practice.app2019.storage.filter.ComparisonConditional;
import ru.softwerke.practice.app2019.storage.filter.Conditional;
import ru.softwerke.practice.app2019.storage.filter.FilterCondition;
import ru.softwerke.practice.app2019.storage.filter.StorageFilter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class DeviceDataServiceImpl implements DeviceDataService {
    private Storage<Device> storage;

    public DeviceDataServiceImpl(Storage<Device> storage) {
        this.storage = storage;
    }

    @Override
    public UUID saveDevice(Device device) {
        return storage.save(device);
    }

    @Override
    public Device getDeviceById(UUID id) {
        return storage.get(id);
    }

    @Override
    public List<Device> getDevices(DeviceFilter filter) {
        StorageFilter<Device> storageFilter = new StorageFilter<>();

        if (filter.getManufacturer() != null) {
            storageFilter.addCondition(
                    new FilterCondition<>(Device::getManufacturer, ComparisonConditional.eq(filter.getManufacturer()))
            );
        }

        if (filter.getModel() != null) {
            storageFilter.addCondition(
                    new FilterCondition<>(Device::getModel, ComparisonConditional.eq(filter.getModel()))
            );
        }

        if (filter.getColor() != null) {
            storageFilter.addCondition(
                    new FilterCondition<>(Device::getColor, ComparisonConditional.eq(filter.getColor()))
            );
        }

        List<Conditional<BigDecimal>> priceRange = new ArrayList<>(2);
        if (filter.getPriceFrom() != null) {
            priceRange.add(ComparisonConditional.greaterOrEq(filter.getPriceFrom()));
        }
        if (filter.getPriceTo() != null) {
            priceRange.add(ComparisonConditional.lessOrEq(filter.getPriceTo()));
        }
        if (!priceRange.isEmpty()) {
            storageFilter.addCondition(new FilterCondition<>(Device::getPrice, priceRange));
        }

        List<Conditional<LocalDate>> dateRange = new ArrayList<>(2);
        if (filter.getDateFrom() != null) {
            dateRange.add(ComparisonConditional.greaterOrEq(filter.getDateFrom()));
        }
        if (filter.getDateTo() != null) {
            dateRange.add(ComparisonConditional.lessOrEq(filter.getDateTo()));
        }
        if (!dateRange.isEmpty()) {
            storageFilter.addCondition(new FilterCondition<>(Device::getDate, dateRange));
        }

        return storage.get(storageFilter);
    }

}
