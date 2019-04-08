package ru.softwerke.practice.app2019.storage;

import ru.softwerke.practice.app2019.storage.filter.StorageFilter;

import java.util.List;
import java.util.UUID;

public interface Storage<T extends Unique> {
    UUID save(T device);

    List<T> get(StorageFilter<T> filter);

    T get(UUID id);
}
