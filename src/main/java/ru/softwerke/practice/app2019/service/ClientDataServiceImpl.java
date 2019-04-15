package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Client;
import ru.softwerke.practice.app2019.storage.Storage;
import ru.softwerke.practice.app2019.storage.filter.FilterConditional;
import ru.softwerke.practice.app2019.storage.filter.StorageFilter;

import java.util.List;
import java.util.UUID;

public class ClientDataServiceImpl implements ClientDataService {
    private Storage<Client> storage;

    public ClientDataServiceImpl(Storage<Client> storage) {
        this.storage = storage;
    }

    @Override
    public Client saveClient(Client client) {
        UUID id = UUID.randomUUID();
        client.setId(id);
        storage.save(client);
        return client;
    }

    @Override
    public Client getClientById(UUID id) {
        return storage.get(id);
    }

    @Override
    public List<Client> getClients(ClientFilter filter) {
        StorageFilter<Client> storageFilter = new StorageFilter<>();

        if (filter.getFirstName() != null) {
            storageFilter.addCondition(FilterConditional.on(Client::getFirstName).eq(filter.getFirstName()));
        }

        if (filter.getLastName() != null) {
            storageFilter.addCondition(FilterConditional.on(Client::getLastName).eq(filter.getLastName()));
        }

        if (filter.getPatronymic() != null) {
            storageFilter.addCondition(FilterConditional.on(Client::getPatronymic).eq(filter.getPatronymic()));
        }

        storageFilter.addCondition(FilterConditional.on(Client::getBirthDate).inRange(filter.getBirthDateFrom(), filter.getBirthDateTo()));

        storageFilter.addAllSorting(Client.FIELD_PROVIDER, filter.getSortConditionals());
        storageFilter.setCount(filter.getCount());
        storageFilter.setPageNumber(filter.getPageNumber());
        return storage.get(storageFilter);
    }

}
