package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Client;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    Client saveClient(Client client);

    Client getClientById(long id);

    List<Client> getClients(ClientFilter filter);
}

