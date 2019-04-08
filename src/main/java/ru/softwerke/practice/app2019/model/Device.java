package ru.softwerke.practice.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.softwerke.practice.app2019.storage.Unique;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Device implements Unique {
    private static final String ID_FIELD = "id";
    private static final String PRICE_FIELD = "price";
    private static final String MODEL_FIELD = "model";
    private static final String COLOR_FIELD = "color";
    private static final String DATE_FIELD = "date";
    private static final String MANUFACTURER_FIELD = "manufacturer";

    @JsonProperty(ID_FIELD)
    private UUID id;

    @JsonProperty(PRICE_FIELD)
    private final BigDecimal price;

    @JsonProperty(MODEL_FIELD)
    private final String model;

    @JsonProperty(COLOR_FIELD)
    private final Color color;

    @JsonProperty(DATE_FIELD)
    @JsonSerialize(using = DateSeriailizer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private final LocalDate date;

    @JsonProperty(MANUFACTURER_FIELD)
    private final String manufacturer;

    @JsonCreator
    public Device(
            @NotNull @JsonProperty(PRICE_FIELD) BigDecimal price,
            @NotNull @JsonProperty(MODEL_FIELD) String model,
            @NotNull @JsonProperty(COLOR_FIELD) Color color,
            @NotNull @JsonProperty(DATE_FIELD) LocalDate date,
            @NotNull @JsonProperty(MANUFACTURER_FIELD) String manufacturer) {
        this.price = price;
        this.model = model;
        this.color = color;
        this.manufacturer = manufacturer;
        this.date = date;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
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

    public LocalDate getDate() {
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
