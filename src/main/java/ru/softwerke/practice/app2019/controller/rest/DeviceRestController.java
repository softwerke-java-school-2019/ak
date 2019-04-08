package ru.softwerke.practice.app2019.controller.rest;

import ru.softwerke.practice.app2019.model.Color;
import ru.softwerke.practice.app2019.model.DateDeserializer;
import ru.softwerke.practice.app2019.model.Device;
import ru.softwerke.practice.app2019.service.DeviceDataService;
import ru.softwerke.practice.app2019.service.DeviceFilter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Path("device")
public class DeviceRestController {
    private DeviceDataService deviceDataService;

    @Inject
    public DeviceRestController(DeviceDataService deviceDataService) {
        this.deviceDataService = deviceDataService;
    }


    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getDevices(@QueryParam("color") Color color,
                                   @QueryParam("priceFrom") BigDecimal priceFrom,
                                   @QueryParam("priceTo") BigDecimal priceTo,
                                   @QueryParam("dateFrom") String dateFromStr,
                                   @QueryParam("dateTo") String dateToStr,
                                   @QueryParam("model") String model,
                                   @QueryParam("manufacturer") String manufacturer) {

        LocalDate dateFrom = null;
        LocalDate dateTo = null;

        if (dateFromStr != null) {
            dateFrom = LocalDate.parse(dateFromStr, DateDeserializer.formatter);
        }

        if (dateToStr != null) {
            dateTo = LocalDate.parse(dateToStr, DateDeserializer.formatter);
        }

        DeviceFilter filter = new DeviceFilter()
                .withPriceFrom(priceFrom)
                .withPriceTo(priceTo)
                .withColor(color)
                .withDateFrom(dateFrom)
                .withDateTo(dateTo)
                .withModel(model)
                .withManufacturer(manufacturer);

        return deviceDataService.getDevices(filter);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getDevices() {
        return deviceDataService.getDevices();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Device createDevice(Device device) {
        UUID deviceId = deviceDataService.saveDevice(device);
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