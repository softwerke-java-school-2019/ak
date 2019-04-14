package ru.softwerke.practice.app2019.storage.filter;

import ru.softwerke.practice.app2019.storage.filter.sorting.SortConditional;
import ru.softwerke.practice.app2019.storage.filter.sorting.SortableFieldProvider;

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

    public void addSorting(SortableFieldProvider<T> provider, SortConditional conditional) {
        Comparator<T> sorting = provider.getSortConditional(conditional);
        if (conditional.getOrder() == SortConditional.Order.DESC) {

            sorting = sorting.reversed();
        }
        sortings.add(sorting);
    }

    public void addAllSorting(SortableFieldProvider<T> provider, List<SortConditional> conditionals) {
        conditionals.forEach((o) -> addSorting(provider, o));
    }
}
