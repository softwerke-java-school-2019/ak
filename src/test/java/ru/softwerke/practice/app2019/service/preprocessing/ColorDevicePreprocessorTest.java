package ru.softwerke.practice.app2019.service.preprocessing;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.softwerke.practice.app2019.model.Color;
import ru.softwerke.practice.app2019.model.Device;
import ru.softwerke.practice.app2019.service.ColorService;
import ru.softwerke.practice.app2019.service.ColorServiceImpl;
import ru.softwerke.practice.app2019.utils.ModelUtils;

import javax.ws.rs.WebApplicationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.*;

class ColorDevicePreprocessorTest {
    private ColorService colorService = Mockito.mock(ColorServiceImpl.class);
    ColorDevicePreprocessor colorDevicePreprocessor = new ColorDevicePreprocessor(colorService);

    @Test
    void should_check_bad_color(){
        Device device = ModelUtils.device();
        Color badColor = new Color("черный", 1);
        device.setColor(badColor);
        Mockito.when(colorService.getColorByName(anyString())).thenReturn(new Color("черный", 0));
        Mockito.when(colorService.getColorByRGB(anyInt())).thenReturn(null);

        assertThrows(WebApplicationException.class, () -> colorDevicePreprocessor.process(device));

        badColor = new Color("синий", 0);
        device.setColor(badColor);
        Mockito.when(colorService.getColorByName(anyString())).thenReturn(new Color("синий", 255));
        Mockito.when(colorService.getColorByRGB(anyInt())).thenReturn(new Color("черный", 0));

        assertThrows(WebApplicationException.class, () -> colorDevicePreprocessor.process(device));
    }

    @Test
    void should_check_good_color(){
        Device device = ModelUtils.device();
        Color goodColor = new Color("черный", 0);
        device.setColor(goodColor);
        Mockito.when(colorService.getColorByName(anyString())).thenReturn(new Color("черный", 0));
        Mockito.when(colorService.getColorByRGB(anyInt())).thenReturn(new Color("черный", 0));

        assertEquals(device, colorDevicePreprocessor.process(device));

        goodColor = new Color("новый", 5);
        device.setColor(goodColor);
        Mockito.when(colorService.getColorByName(anyString())).thenReturn(null);
        Mockito.when(colorService.getColorByRGB(anyInt())).thenReturn(null);
        Mockito.when(colorService.saveColor(anyObject())).thenReturn(goodColor);

        assertEquals(device, colorDevicePreprocessor.process(device));

    }

}