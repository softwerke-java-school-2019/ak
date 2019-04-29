package ru.softwerke.practice.app2019.service.preprocessing;

import ru.softwerke.practice.app2019.controller.rest.JSONErrorMessage;
import ru.softwerke.practice.app2019.model.Color;
import ru.softwerke.practice.app2019.model.Device;
import ru.softwerke.practice.app2019.service.ColorService;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;


public class ColorDevicePreprocessor implements Preprocessor<Device> {
    private ColorService colorService;

    public ColorDevicePreprocessor(ColorService colorService) {
        this.colorService = colorService;
    }

    @Override
    public Device process(Device device) {
        Color color = device.getColor();
        color.setName(color.getName().toLowerCase());
        Color foundByNameColor = colorService.getColorByName(color.getName());
        Color foundByRgbColor = colorService.getColorByRGB(color.getRgb());
        if (foundByRgbColor == null & foundByNameColor == null) {
            color = colorService.saveColor(color);
        } else if (foundByRgbColor != null & foundByNameColor != null & foundByNameColor.getRgb().equals(color.getRgb())) {
            color.setId(foundByNameColor.getId());
        } else {
            Response response = Response
                    .status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(JSONErrorMessage.create("validation", "Color RGB doesn't match color name"))
                    .build();
            throw new WebApplicationException(response);
        }
        device.setColor(color);
        return device;
    }
}
