package ru.softwerke.practice.app2019.storage;

import org.glassfish.jersey.internal.util.Producer;
import ru.softwerke.practice.app2019.model.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RuntimeStorage implements Storage {
    private List<Device> devices = new ArrayList<>();

    @Override
    public UUID saveDevice(Device device) {
        UUID deviceId = UUID.randomUUID();
        device.setId(deviceId);
        devices.add(device);
        return device.getId();
    }

    @Override
    public List<Device> getDevices(DeviceFilter filter) {
        return devices.stream().filter(device -> filterDevice(filter, device)).collect(Collectors.toList());
    }

    @Override
    public Device getDevice(UUID id) {
        return devices.stream().filter(device -> device.getId().equals(id)).findFirst().orElse(null);
    }

    private static boolean filterDevice(DeviceFilter filter, Device device) {
        return (isEqual(filter::getDate, device::getDate)
                && isEqual(filter::getManufacturer, device::getManufacturer)
                && isEqual(filter::getModel, device::getModel)
                && isEqual(filter::getColor, device::getColor)
                && (filter.getPriceFrom() == null || device.getPrice().compareTo(filter.getPriceFrom()) >= 0)
                && (filter.getPriceTo() == null || device.getPrice().compareTo(filter.getPriceTo()) <= 0));
    }

    private static <T> boolean isEqual(Producer<T> f1, Producer<T> f2) {
        T v1 = f1.call();
        T v2 = f2.call();
        if (v1 == null) {
            return true;
        }
        return v1.equals(v2);
    }
}
