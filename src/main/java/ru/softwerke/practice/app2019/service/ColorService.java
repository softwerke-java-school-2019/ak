package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Color;

public interface ColorService {
    Color saveColor(Color color);

    Color getColorByName(String name);

    Color getColorByRGB(int rgb);

}
