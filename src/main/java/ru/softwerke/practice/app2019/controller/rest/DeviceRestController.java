package ru.softwerke.practice.app2019.controller.rest;

import jdk.nashorn.internal.objects.annotations.Getter;
import ru.softwerke.practice.app2019.model.Color;
import ru.softwerke.practice.app2019.model.Device;
import ru.softwerke.practice.app2019.service.DeviceDataService;
import ru.softwerke.practice.app2019.service.DeviceDataServiceImpl;
import ru.softwerke.practice.app2019.storage.DeviceFilter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Path("device")
public class DeviceRestController {
    private DeviceDataService deviceDataService;

    @Inject
    public DeviceRestController(DeviceDataService deviceDataService){
        this.deviceDataService = deviceDataService;
    }

    /*
    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getDevices(@QueryParam("color")Color color,
                                   @QueryParam("priceFrom")BigDecimal priceFrom,
                                   @QueryParam("priceTo")BigDecimal priceTo,
                                   @QueryParam("date")LocalDateTime date,
                                   @QueryParam("model")String model,
                                   @QueryParam("manufacturer")String manufacturer)  {
        DeviceFilter filter = new DeviceFilter()
                .withPriceFrom(priceFrom)
                .withPriceTo(priceTo)
                .withColor(color)
                .withDate(date)
                .withModel(model)
                .withManufacturer(manufacturer);
       // List<Device> devices = deviceDataService.getDevices(filter);
       // GenericEntity<List<Device>> entities = new GenericEntity<List<Device>>(devices){};
       // return Response.status(200).entity(entities).build();
        return deviceDataService.getDevices(filter);
        //return deviceDataService.getDevices();
    } */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getDevices(){
        return deviceDataService.getDevices();
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