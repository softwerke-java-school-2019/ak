package ru.softwerke.practice.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Device {
    public static final String ID_FIELD = "id";
    public static final String PRICE_FIELD = "price";
    public static final String MODEL_FIELD = "model";
    public static final String COLOR_FIELD = "color";
    public static final String DATE_FIELD = "date";
    public static final String MANUFACTURER_FIELD = "manufacturer";

    @JsonProperty(ID_FIELD)
    private UUID id;

    @JsonProperty(PRICE_FIELD)
    private final BigDecimal price;

    @JsonProperty(MODEL_FIELD)
    private final String model;

    @JsonProperty(COLOR_FIELD)
    private final Color color;

    @JsonProperty(DATE_FIELD)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private final LocalDateTime date;

    @JsonProperty(MANUFACTURER_FIELD)
    private final String manufacturer;

    @JsonCreator
    public Device(
            @NotNull @JsonProperty(PRICE_FIELD) BigDecimal price,
            @NotNull @JsonProperty(MODEL_FIELD) String model,
            @NotNull @JsonProperty(COLOR_FIELD) Color color,
            @NotNull @JsonProperty(DATE_FIELD) LocalDateTime date,
            @NotNull @JsonProperty(MANUFACTURER_FIELD) String manufacturer) {
        this.price = price;
        this.model = model;
        this.color = color;
        this.date = date;
        this.manufacturer = manufacturer;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getModel() {
        return model;
    }

    public Color getColor() {
        return color;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;
        Device device = (Device) o;
        return id == device.id &&
                price.equals(device.price) &&
                model.equals(device.model) &&
                color.equals(device.color) &&
                date.equals(device.date) &&
                manufacturer.equals(device.manufacturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, model, color, date, manufacturer);
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", price=" + price +
                ", model='" + model + '\'' +
                ", color=" + color +
                ", date=" + date +
                ", manufacturer='" + manufacturer + '\'' +
                '}';
    }

}
