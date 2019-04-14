package ru.softwerke.practice.app2019.storage.filter;

public class RangeConditional<T extends Comparable<? super T>> implements Conditional<T> {
    private final T from;
    private final T to;

    public RangeConditional(T from, T to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean accept(T object) {
        boolean accept = true;
        if (from != null) {
            accept = from.compareTo(object) <= 0;
        }
        if (to != null) {
            accept &= to.compareTo(object) >= 0;
        }
        return accept;
    }
}
