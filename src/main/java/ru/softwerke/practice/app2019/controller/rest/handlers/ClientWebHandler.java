package ru.softwerke.practice.app2019.controller.rest.handlers;

import ru.softwerke.practice.app2019.model.Client;
import ru.softwerke.practice.app2019.service.ClientFilter;
import ru.softwerke.practice.app2019.service.ClientService;
import ru.softwerke.practice.app2019.storage.filter.sorting.SortConditional;
import ru.softwerke.practice.app2019.utils.ModelValidator;
import ru.softwerke.practice.app2019.utils.ParsingUtil;
import ru.softwerke.practice.app2019.utils.QueryValidator;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

public class ClientWebHandler {
    private ClientService clientService;

    @Inject
    public ClientWebHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    public List<Client> getClients(String firstName,
                                   String lastName,
                                   String patronymic,
                                   String birthDateStr,
                                   String birthDateFromStr,
                                   String birthDateToStr,
                                   String sortBy,
                                   int count,
                                   int pageNumber) {

        LocalDate birthDateFrom = ParsingUtil.getLocalDate(birthDateFromStr);
        LocalDate birthDateTo = ParsingUtil.getLocalDate(birthDateToStr);
        LocalDate birthDate = ParsingUtil.getLocalDate(birthDateStr);
        List<SortConditional> sortConditionals = ParsingUtil.getSortParams(sortBy);

        ClientFilter filter = new ClientFilter()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withPatronymic(patronymic)
                .withBirthDateFrom(birthDateFrom)
                .withBirthDateTo(birthDateTo)
                .withBirthDate(birthDate)
                .withSortParams(sortConditionals)
                .withCount(count)
                .withPageNumber(pageNumber - 1);
        ModelValidator.validateEntity(filter);
        return clientService.getClients(filter);
    }

    public Client createClient(Client client) {
        QueryValidator.checkEmptyRequest(client);
        ModelValidator.validateEntity(client);
        return clientService.saveClient(client);
    }

    public Client getClient(long id) {
        Client client = clientService.getClientById(id);
        QueryValidator.checkIfNotFound(client, String.format("Client with id %s doesn't exist", id));
        return client;
    }

}
