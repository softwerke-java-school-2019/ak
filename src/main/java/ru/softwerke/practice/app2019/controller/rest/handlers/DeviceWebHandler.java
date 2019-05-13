package ru.softwerke.practice.app2019.controller.rest.handlers;

import ru.softwerke.practice.app2019.model.Device;
import ru.softwerke.practice.app2019.model.DeviceType;
import ru.softwerke.practice.app2019.service.ColorService;
import ru.softwerke.practice.app2019.service.DeviceFilter;
import ru.softwerke.practice.app2019.service.DeviceService;
import ru.softwerke.practice.app2019.service.preprocessing.ColorDevicePreprocessor;
import ru.softwerke.practice.app2019.storage.filter.sorting.SortConditional;
import ru.softwerke.practice.app2019.utils.ModelValidator;
import ru.softwerke.practice.app2019.utils.ParsingUtil;
import ru.softwerke.practice.app2019.utils.QueryValidator;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class DeviceWebHandler {
    private DeviceService deviceService;
    private ColorService colorService;

    @Inject
    public DeviceWebHandler(DeviceService deviceService, ColorService colorService) {
        this.deviceService = deviceService;
        this.colorService = colorService;
    }

    public List<Device> getDevices(String deviceTypeStr,
                                   String colorName,
                                   Integer colorRGB,
                                   BigDecimal price,
                                   BigDecimal priceFrom,
                                   BigDecimal priceTo,
                                   String dateStr,
                                   String dateFromStr,
                                   String dateToStr,
                                   String model,
                                   String manufacturer,
                                   String sortBy,
                                   int count,
                                   int pageNumber) {

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
                .withPageNumber(pageNumber - 1);
        ModelValidator.validateEntity(filter);
        return deviceService.getDevices(filter);
    }

    public Device createDevice(Device device) {
        QueryValidator.checkEmptyRequest(device);
        ModelValidator.validateEntity(device);
        ColorDevicePreprocessor colorDevicePreprocessor = new ColorDevicePreprocessor(colorService);
        device = colorDevicePreprocessor.process(device);
        return deviceService.saveDevice(device);
    }

    public Device getDevice(long id) {
        Device device = deviceService.getDeviceById(id);
        QueryValidator.checkIfNotFound(device, String.format("Device with id %s doesn't exist", id));
        return device;
    }
}
