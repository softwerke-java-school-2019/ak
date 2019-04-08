package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Color;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class DeviceFilter {
    public final static DeviceFilter EMPTY = new DeviceFilter();

    private UUID id;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    private String model;
    private Color color;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String manufacturer;

    public UUID getId() {
        return id;
    }

    public BigDecimal getPriceFrom() {
        return priceFrom;
    }

    public BigDecimal getPriceTo() {
        return priceTo;
    }

    public String getModel() {
        return model;
    }

    public Color getColor() {
        return color;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() { return dateTo; }

    public String getManufacturer() {
        return manufacturer;
    }

    public DeviceFilter withId(UUID id) {
        this.id = id;
        return this;
    }

    public DeviceFilter withPriceFrom(BigDecimal priceFrom) {
        this.priceFrom = priceFrom;
        return this;
    }

    public DeviceFilter withPriceTo(BigDecimal priceTo) {
        this.priceTo = priceTo;
        return this;
    }

    public DeviceFilter withModel(String model) {
        this.model = model;
        return this;
    }

    public DeviceFilter withColor(Color color) {
        this.color = color;
        return this;
    }

    public DeviceFilter withDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
        return this;
    }

    public DeviceFilter withDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
        return this;
    }

    public DeviceFilter withManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

}
