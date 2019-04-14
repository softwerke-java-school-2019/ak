package ru.softwerke.practice.app2019.storage.filter.sorting;

import java.util.Comparator;

public interface SortableFieldProvider<T> {
    Comparator<T> getSortConditional(SortConditional sortConditional);
}
