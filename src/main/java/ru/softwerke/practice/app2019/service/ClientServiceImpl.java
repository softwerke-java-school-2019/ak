package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Client;
import ru.softwerke.practice.app2019.storage.Storage;
import ru.softwerke.practice.app2019.storage.filter.FilterConditional;
import ru.softwerke.practice.app2019.storage.filter.StorageFilter;
import ru.softwerke.practice.app2019.utils.Identifier;

import java.util.List;

public class ClientServiceImpl implements ClientService {
    private Storage<Client> storage;

    public ClientServiceImpl(Storage<Client> storage) {
        this.storage = storage;
    }

    @Override
    public Client saveClient(Client client) {
        int id = Identifier.nextId();
        client.setId(id);
        storage.save(client);
        return client;
    }

    @Override
    public Client getClientById(int id) {
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

        if (filter.getBirthDate() != null) {
            storageFilter.addCondition(FilterConditional.on(Client::getBirthDate).eq(filter.getBirthDate()));
        }


        storageFilter.addCondition(FilterConditional.on(Client::getBirthDate).inRange(filter.getBirthDateFrom(), filter.getBirthDateTo()));

        storageFilter.addAllSorting(Client.FIELD_PROVIDER, filter.getSortConditionals());
        storageFilter.setCount(filter.getCount());
        storageFilter.setPageNumber(filter.getPageNumber());
        return storage.get(storageFilter);
    }

}
