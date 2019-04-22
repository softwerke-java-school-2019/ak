package ru.softwerke.practice.app2019.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.Length;
import ru.softwerke.practice.app2019.model.date.DateDeserializer;
import ru.softwerke.practice.app2019.model.date.DateSerializer;
import ru.softwerke.practice.app2019.storage.Unique;
import ru.softwerke.practice.app2019.storage.filter.sorting.SortConditional;
import ru.softwerke.practice.app2019.storage.filter.sorting.SortableFieldProvider;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

public class Device implements Unique {
    private static final String ID_FIELD = "id";
    private static final String PRICE_FIELD = "price";
    private static final String MODEL_FIELD = "model";
    private static final String COLOR_FIELD = "color";
    private static final String DATE_FIELD = "date";
    private static final String MANUFACTURER_FIELD = "manufacturer";

    public static final SortableFieldProvider<Device> FIELD_PROVIDER = new DeviceSortableFieldProvider();

    @JsonProperty(ID_FIELD)
    private UUID id;

    @JsonProperty(PRICE_FIELD)
    @NotNull(message = "Price may not be null")
    @Digits(integer=6, fraction=2)
    private final BigDecimal price;

    @JsonProperty(MODEL_FIELD)
    @NotNull(message = "Model may not be null")
    @Length(min = 1, max = 100,  message = "Invalid model: length must be between 1 and 100")
    private final String model;

    @JsonProperty(COLOR_FIELD)
    @NotNull(message = "Color may not be null")
    private final Color color;

    @JsonProperty(DATE_FIELD)
    @NotNull(message = "Date may not be null")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private final LocalDate date;

    @JsonProperty(MANUFACTURER_FIELD)
    @NotNull(message = "Manufacturer may not be null")
    @Length(min = 1, max = 100,  message = "Invalid manufacturer: length must be between 1 and 100")
    private final String manufacturer;

    @JsonCreator
    public Device(
            @JsonProperty(value = PRICE_FIELD, required = true) BigDecimal price,
            @JsonProperty(value = MODEL_FIELD, required = true) String model,
            @JsonProperty(value = COLOR_FIELD, required = true) Color color,
            @JsonProperty(value = DATE_FIELD, required = true) LocalDate date,
            @JsonProperty(value = MANUFACTURER_FIELD, required = true) String manufacturer) {
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

    public static class DeviceSortableFieldProvider implements SortableFieldProvider<Device> {

        private DeviceSortableFieldProvider() {
        }

        @Override
        public Comparator<Device> getSortConditional(SortConditional sortConditional) {
            switch (sortConditional.getField()) {
                case PRICE_FIELD:
                    return Comparator.comparing(Device::getPrice);
                case MODEL_FIELD:
                    return Comparator.comparing(Device::getModel);
                case DATE_FIELD:
                    return Comparator.comparing(Device::getDate);
                case MANUFACTURER_FIELD:
                    return Comparator.comparing(Device::getManufacturer);
                case COLOR_FIELD:
                    return Comparator.comparing(Device::getColor);
                default:
                    throw new IllegalArgumentException("Unexpected sort param " + sortConditional.getField());
            }
        }
    }
}
