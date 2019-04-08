package ru.softwerke.practice.app2019.controller.rest;

import ru.softwerke.practice.app2019.model.Client;
import ru.softwerke.practice.app2019.model.DateDeserializer;
import ru.softwerke.practice.app2019.service.ClientDataService;
import ru.softwerke.practice.app2019.service.ClientFilter;

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
    public List<Client> getDevices(@QueryParam("firstName") String firstName,
                                   @QueryParam("lastName") String lastName,
                                   @QueryParam("patronymic") String patronymic,
                                   @QueryParam("birthDateFrom") String birthDateFromStr,
                                   @QueryParam("birthDateTo") String birthDateToStr) {

        LocalDate birthDateFrom = null;
        LocalDate birthDateTo = null;

        if (birthDateFromStr != null) {
            birthDateFrom = LocalDate.parse(birthDateFromStr, DateDeserializer.formatter);
        }

        if (birthDateToStr != null) {
            birthDateTo = LocalDate.parse(birthDateToStr, DateDeserializer.formatter);
        }

        ClientFilter filter = new ClientFilter()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withPatronymic(patronymic)
                .withBirthDateFrom(birthDateFrom)
                .withBirthDateTo(birthDateTo);

        return clientDataService.getClients(filter);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> getDevices() {
        return clientDataService.getClients();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Client createDevice(Client client) {
        UUID clientId = clientDataService.saveClient(client);
        client.setId(clientId);
        return client;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client getClient(@PathParam("id") UUID id) {
        return clientDataService.getClientById(id);
    }
}
