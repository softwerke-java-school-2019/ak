package ru.softwerke.practice.app2019.storage;

import ru.softwerke.practice.app2019.storage.filter.Conditional;
import ru.softwerke.practice.app2019.storage.filter.StorageFilter;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RuntimeStorage<T extends Unique> implements Storage<T> {
    private List<T> objects = new CopyOnWriteArrayList<>();

    @Override
    public void save(T object) {
        objects.add(object);
    }

    @Override
    public List<T> get(StorageFilter<T> filter) {
        Stream<T> objectStream = objects.stream();

        List<Conditional<T>> conditions = filter.getConditions();
        for (Conditional<T> conditional : conditions) {
            objectStream = objectStream.filter(conditional::accept);
        }

        List<Comparator<T>> sorting = filter.getSortings();
        for (Comparator<T> sort : sorting) {
            objectStream = objectStream.sorted(sort);
        }

        int count = filter.getCount();
        int pageNumber = filter.getPageNumber();
        if (count > 0) {
            objectStream = objectStream.skip(pageNumber * count).limit(count);
        }
        return objectStream.collect(Collectors.toList());
    }

    @Override
    public T get(UUID id) {
        return objects.stream().filter(object -> object.getId().equals(id)).findFirst().orElse(null);
    }
}
