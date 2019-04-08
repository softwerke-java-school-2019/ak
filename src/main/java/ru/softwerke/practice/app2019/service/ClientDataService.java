package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Client;

import java.util.List;
import java.util.UUID;

public interface ClientDataService {
    UUID saveClient(Client client);

    Client getClientById(UUID id);

    List<Client> getClients(ClientFilter filter);

    default List<Client> getClients(){
        return getClients(ClientFilter.EMPTY);
    }
}

