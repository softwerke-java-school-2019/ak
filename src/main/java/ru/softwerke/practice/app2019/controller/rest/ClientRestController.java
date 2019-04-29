package ru.softwerke.practice.app2019.controller.rest;

import ru.softwerke.practice.app2019.model.Client;
import ru.softwerke.practice.app2019.service.ClientService;
import ru.softwerke.practice.app2019.service.ClientFilter;
import ru.softwerke.practice.app2019.storage.filter.sorting.SortConditional;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.List;

@Path("customer")
public class ClientRestController {
    private ClientService clientService;

    @Inject
    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> getClients(@QueryParam("firstName") String firstName,
                                   @QueryParam("lastName") String lastName,
                                   @QueryParam("middleName") String patronymic,
                                   @QueryParam("birthdate") String birthDateStr,
                                   @QueryParam("birthdateFrom") String birthDateFromStr,
                                   @QueryParam("birthdateTo") String birthDateToStr,
                                   @QueryParam("sortBy") String sortBy,
                                   @DefaultValue("10") @QueryParam("count") int count,
                                   @DefaultValue("1") @QueryParam("pageNumber") int pageNumber) {

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
                .withPageNumber(pageNumber-1);
        ModelValidator.validateEntity(filter);
        return clientService.getClients(filter);

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Client createClient(Client client) {
        QueryValidator.checkEmptyRequest(client);
        ModelValidator.validateEntity(client);
        return clientService.saveClient(client);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client getClient(@PathParam("id") int id) {
        Client client = clientService.getClientById(id);
        QueryValidator.checkIfNotFound(client, String.format("Client with id %s doesn't exist", id));
        return client;
    }
}
