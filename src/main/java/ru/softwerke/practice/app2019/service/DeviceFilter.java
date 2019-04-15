package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Color;
import ru.softwerke.practice.app2019.storage.filter.sorting.SortConditional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private List<SortConditional> sortConditionals = new ArrayList<>();
    private int count;
    private int pageNumber;

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

    public List<SortConditional> getSortConditionals() { return sortConditionals; }

    public int getCount() { return count; }

    public int getPageNumber() { return pageNumber; }

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

    public DeviceFilter withSortConditionals(List<SortConditional> sortConditionals){
        this.sortConditionals = sortConditionals;
        return this;
    }

    public DeviceFilter withCount(int count){
        this.count = count;
        return this;
    }

    public DeviceFilter withPageNumber(int pageNumber){
        this.pageNumber = pageNumber;
        return this;
    }
}
