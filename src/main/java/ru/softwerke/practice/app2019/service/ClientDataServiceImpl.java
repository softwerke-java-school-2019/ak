package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Client;
import ru.softwerke.practice.app2019.storage.filter.sorting.SortConditional;
import ru.softwerke.practice.app2019.storage.Storage;
import ru.softwerke.practice.app2019.storage.filter.ComparisonConditional;
import ru.softwerke.practice.app2019.storage.filter.FilterCondition;
import ru.softwerke.practice.app2019.storage.filter.StorageFilter;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class ClientDataServiceImpl implements ClientDataService {
    private Storage<Client> storage;

    public ClientDataServiceImpl(Storage<Client> storage) {
        this.storage = storage;
    }

    @Override
    public UUID saveClient(Client client) {
        return storage.save(client);
    }

    @Override
    public Client getClientById(UUID id) {
        return storage.get(id);
    }

    @Override
    public List<Client> getClients(ClientFilter filter) {
        StorageFilter<Client> storageFilter = new StorageFilter<>();

        if (filter.getFirstName() != null) {
            storageFilter.addCondition(FilterCondition.on(Client::getFirstName).eq(filter.getFirstName()));
        }

        if (filter.getLastName() != null) {
            storageFilter.addCondition(FilterCondition.on(Client::getLastName).eq(filter.getLastName()));
        }

        if (filter.getPatronymic() != null) {
            storageFilter.addCondition(FilterCondition.on(Client::getPatronymic).eq(filter.getPatronymic()));
        }

        storageFilter.addCondition(FilterCondition.on(Client::getBirthDate).inRange(filter.getBirthDateFrom(), filter.getBirthDateTo()));

        storageFilter.addAllSorting(Client.FIELD_PROVIDER, filter.getSortConditionals());

        return storage.get(storageFilter);
    }

}
