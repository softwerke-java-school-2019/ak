package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Client;
import ru.softwerke.practice.app2019.storage.Storage;
import ru.softwerke.practice.app2019.storage.filter.ComparisonConditional;
import ru.softwerke.practice.app2019.storage.filter.Conditional;
import ru.softwerke.practice.app2019.storage.filter.FilterCondition;
import ru.softwerke.practice.app2019.storage.filter.StorageFilter;

import java.time.LocalDate;
import java.util.ArrayList;
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
            storageFilter.addCondition(
                    new FilterCondition<>(Client::getFirstName, ComparisonConditional.eq(filter.getFirstName()))
            );
        }

        if (filter.getLastName() != null) {
            storageFilter.addCondition(
                    new FilterCondition<>(Client::getLastName, ComparisonConditional.eq(filter.getLastName()))
            );
        }

        if (filter.getPatronymic() != null) {
            storageFilter.addCondition(
                    new FilterCondition<>(Client::getPatronymic, ComparisonConditional.eq(filter.getPatronymic()))
            );
        }

        List<Conditional<LocalDate>> birthDateRange = new ArrayList<>(2);
        if (filter.getBirthDateFrom() != null) {
            birthDateRange.add(ComparisonConditional.greaterOrEq(filter.getBirthDateFrom()));
        }
        if (filter.getBirthDateTo() != null) {
            birthDateRange.add(ComparisonConditional.lessOrEq(filter.getBirthDateTo()));
        }
        if (!birthDateRange.isEmpty()) {
            storageFilter.addCondition(new FilterCondition<>(Client::getBirthDate, birthDateRange));
        }

        return storage.get(storageFilter);
    }
}
