package ru.softwerke.practice.app2019.storage;

import ru.softwerke.practice.app2019.storage.filter.StorageFilter;

import java.util.List;

public interface Storage<T extends Unique> {
    void save(T object);

    List<T> get(StorageFilter<T> filter);

    T getById(long id);
}
