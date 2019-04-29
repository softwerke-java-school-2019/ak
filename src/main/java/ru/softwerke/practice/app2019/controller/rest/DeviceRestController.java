package ru.softwerke.practice.app2019.controller.rest;

import ru.softwerke.practice.app2019.model.Device;
import ru.softwerke.practice.app2019.model.DeviceType;
import ru.softwerke.practice.app2019.service.ColorService;
import ru.softwerke.practice.app2019.service.DeviceService;
import ru.softwerke.practice.app2019.service.DeviceFilter;
import ru.softwerke.practice.app2019.service.preprocessing.ColorDevicePreprocessor;
import ru.softwerke.practice.app2019.storage.filter.sorting.SortConditional;
import ru.softwerke.practice.app2019.utils.ModelValidator;
import ru.softwerke.practice.app2019.utils.ParsingUtil;
import ru.softwerke.practice.app2019.utils.QueryValidator;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Path("device")
public class DeviceRestController {
    private DeviceService deviceService;
    private ColorService colorService;

    @Inject
    public DeviceRestController(DeviceService deviceService, ColorService colorService) {
        this.deviceService = deviceService;
        this.colorService = colorService;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getDevices(@QueryParam("deviceType") String deviceTypeStr,
                                   @QueryParam("colorName") String colorName,
                                   @QueryParam("colorRGB") Integer colorRGB,
                                   @QueryParam("price") BigDecimal price,
                                   @QueryParam("priceFrom") BigDecimal priceFrom,
                                   @QueryParam("priceTo") BigDecimal priceTo,
                                   @QueryParam("manufactureDate") String dateStr,
                                   @QueryParam("manufactureDateFrom") String dateFromStr,
                                   @QueryParam("manufactureDateTo") String dateToStr,
                                   @QueryParam("modelName") String model,
                                   @QueryParam("manufacturer") String manufacturer,
                                   @QueryParam("sortBy") String sortBy,
                                   @DefaultValue("10") @QueryParam("pageItems")int count,
                                   @DefaultValue("1") @QueryParam("page") int pageNumber) {

        LocalDate dateFrom = ParsingUtil.getLocalDate(dateFromStr);
        LocalDate dateTo = ParsingUtil.getLocalDate(dateToStr);
        LocalDate date = ParsingUtil.getLocalDate(dateStr);
        List<SortConditional> sortConditionals = ParsingUtil.getSortParams(sortBy);

        DeviceFilter filter = new DeviceFilter()
                .withDeviceType(DeviceType.forValue(deviceTypeStr))
                .withColorName(colorName)
                .withColorRGB(colorRGB)
                .withPrice(price)
                .withPriceFrom(priceFrom)
                .withPriceTo(priceTo)
                .withDate(date)
                .withDateFrom(dateFrom)
                .withDateTo(dateTo)
                .withModel(model)
                .withManufacturer(manufacturer)
                .withSortConditionals(sortConditionals)
                .withCount(count)
                .withPageNumber(pageNumber-1);
        ModelValidator.validateEntity(filter);
        return deviceService.getDevices(filter);

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Device createDevice(Device device){
        QueryValidator.checkEmptyRequest(device);
        ModelValidator.validateEntity(device);
        ColorDevicePreprocessor colorDevicePreprocessor = new ColorDevicePreprocessor(colorService);
        device = colorDevicePreprocessor.process(device);
        return deviceService.saveDevice(device);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Device getDevice(@PathParam("id") int id) {
        Device device = deviceService.getDeviceById(id);
        QueryValidator.checkIfNotFound(device, String.format("Device with id %s doesn't exist", id));
        return device;
    }
}

