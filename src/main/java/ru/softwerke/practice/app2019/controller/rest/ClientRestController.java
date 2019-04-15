package ru.softwerke.practice.app2019.controller.rest;

import ru.softwerke.practice.app2019.model.Client;
import ru.softwerke.practice.app2019.service.ClientDataService;
import ru.softwerke.practice.app2019.service.ClientFilter;
import ru.softwerke.practice.app2019.storage.filter.sorting.SortConditional;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Path("client")
public class ClientRestController {
    private ClientDataService clientDataService;

    @Inject
    public ClientRestController(ClientDataService clientDataService) {
        this.clientDataService = clientDataService;
    }

    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> getClients(@QueryParam("firstName") String firstName,
                                   @QueryParam("lastName") String lastName,
                                   @QueryParam("patronymic") String patronymic,
                                   @QueryParam("birthDateFrom") String birthDateFromStr,
                                   @QueryParam("birthDateTo") String birthDateToStr,
                                   @QueryParam("sortBy") String sortBy,
                                   @DefaultValue("50") @QueryParam("count") int count,
                                   @DefaultValue("0") @QueryParam("pageNumber") int pageNumber) {

        LocalDate birthDateFrom = ParsingUtil.getLocalDate(birthDateFromStr);;
        LocalDate birthDateTo = ParsingUtil.getLocalDate(birthDateToStr);
        List<SortConditional> sortConditionals = ParsingUtil.getSortParams(sortBy);

        ClientFilter filter = new ClientFilter()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withPatronymic(patronymic)
                .withBirthDateFrom(birthDateFrom)
                .withBirthDateTo(birthDateTo)
                .withSortParams(sortConditionals)
                .withCount(count)
                .withPageNumber(pageNumber);

        return clientDataService.getClients(filter);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> getClients() {
        return clientDataService.getClients();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Client createClient(Client client) {
        return clientDataService.saveClient(client);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client getClient(@PathParam("id") UUID id) {
        return clientDataService.getClientById(id);
    }
}
