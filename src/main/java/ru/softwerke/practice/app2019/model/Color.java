package ru.softwerke.practice.app2019.model;

import ru.softwerke.practice.app2019.storage.Unique;

import java.util.Objects;

public class Color implements Unique, Comparable<Color> {
    private long id;
    private String name;
    private Integer rgb;

    public Color(String name, Integer rgb) {
        this.name = name;
        this.rgb = rgb;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRgb() {
        return rgb;
    }

    public void setRgb(int rgb) {
        this.rgb = rgb;
    }

    @Override
    public int compareTo(Color o) {
        return getRgb() - o.getRgb();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Color)) return false;
        Color color = (Color) o;
        return rgb == color.rgb &&
                name.equals(color.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rgb);
    }


}
