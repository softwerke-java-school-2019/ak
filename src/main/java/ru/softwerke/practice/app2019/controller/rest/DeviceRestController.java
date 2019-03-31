package ru.softwerke.practice.app2019.controller.rest;

import ru.softwerke.practice.app2019.model.Color;
import ru.softwerke.practice.app2019.model.Device;
import ru.softwerke.practice.app2019.service.DeviceDataService;
import ru.softwerke.practice.app2019.service.DeviceDataServiceImpl;
import ru.softwerke.practice.app2019.storage.DeviceFilter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Path("device")
public class DeviceRestController {
    private DeviceDataService deviceDataService;

    @Inject
    public DeviceRestController(DeviceDataService deviceDataService){
        this.deviceDataService = deviceDataService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getDevices(@QueryParam("color")Color color,
                                   @QueryParam("priceFrom")BigDecimal priceFrom,
                                   @QueryParam("priceTo")BigDecimal priceTo) {
        DeviceFilter filter = new DeviceFilter();
        return deviceDataService.getDevices(filter);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Device createDevice(Device device) {
        UUID deviceId = deviceDataService.putDevice(device);
        device.setId(deviceId);
        return device;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Device getDevice(@PathParam("id") UUID id) {
        return deviceDataService.getDeviceById(id);
    }
}