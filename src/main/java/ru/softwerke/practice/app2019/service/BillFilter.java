package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Bill;
import ru.softwerke.practice.app2019.storage.filter.sorting.SortConditional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BillFilter {
    public final static BillFilter EMPTY = new BillFilter();

    private UUID id;
    private UUID clientId;
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;
    private BigDecimal totalPriceFrom;
    private BigDecimal totalPriceTo;
    private List<UUID> deviceIds = new ArrayList<>();
    private List<SortConditional> sortConditionals = new ArrayList<>();
    private int count;
    private int pageNumber;

    public UUID getId() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public LocalDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    public LocalDateTime getDateTimeTo() {
        return dateTimeTo;
    }

    public BigDecimal getTotalPriceFrom() {
        return totalPriceFrom;
    }

    public BigDecimal getTotalPriceTo() {
        return totalPriceTo;
    }

    public List<UUID> getDeviceIds() {
        return deviceIds;
    }

    public List<SortConditional> getSortConditionals() {
        return sortConditionals;
    }

    public int getCount() {
        return count;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public BillFilter withClientId(UUID clientId){
        this.clientId = clientId;
        return this;
    }

    public BillFilter withDateTimeFrom(LocalDateTime dateTimeFrom){
        this.dateTimeFrom = dateTimeFrom;
        return this;
    }

    public BillFilter withDateTimeTo(LocalDateTime dateTimeTo){
        this.dateTimeTo = dateTimeTo;
        return this;
    }

    public BillFilter withTotalPriceFrom(BigDecimal totalPriceFrom){
        this.totalPriceFrom = totalPriceFrom;
        return this;
    }

    public BillFilter withTotalPriceTo(BigDecimal totalPriceTo){
        this.totalPriceTo = totalPriceTo;
        return this;
    }

    public BillFilter withDeviceIds(List<UUID> deviceIds){
        this.deviceIds = deviceIds;
        return this;
    }

    public BillFilter withSortParams(List<SortConditional> sortConditionals){
        this.sortConditionals = sortConditionals;
        return this;
    }

    public BillFilter withCount(int count){
        this.count = count;
        return this;
    }

    public BillFilter withPageNumber(int pageNumber){
        this.pageNumber = pageNumber;
        return this;
    }

}
