package ru.softwerke.practice.app2019.storage.filter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StorageFilter<T> {
    private List<Conditional<T>> conditions = new ArrayList<>();
    private List<Comparator<T>> sortings = new ArrayList<>();

    public List<Conditional<T>> getConditions() {
        return conditions;
    }

    public List<Comparator<T>> getSortings() {
        return sortings;
    }

    public void addCondition(Conditional<T> conditional) {
        conditions.add(conditional);
    }

    public void addSorting(Comparator<T> sorting) {
        sortings.add(sorting);
    }
}
