package ru.softwerke.practice.app2019.storage.filter.sorting;

public class SortConditional {
    private String field;
    private Order order;

    public SortConditional(String field, Order order) {
        this.field = field;
        this.order = order;
    }

    public String getField() {
        return field;
    }

    public Order getOrder() {
        return order;
    }

    public enum Order{
        ASC,
        DESC
    }
}
