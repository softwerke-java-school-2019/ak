package ru.softwerke.practice.app2019.controller.rest.handlers;

import ru.softwerke.practice.app2019.model.Color;
import ru.softwerke.practice.app2019.service.ColorService;

import javax.inject.Inject;

public class ColorWebHandler {
    private ColorService colorService;

    @Inject
    public ColorWebHandler(ColorService colorService) {
        this.colorService = colorService;
    }

    public Color getColor(int rgb){
        return colorService.getColorByRGB(rgb);
    }
}
