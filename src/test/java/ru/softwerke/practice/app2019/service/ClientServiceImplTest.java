package ru.softwerke.practice.app2019.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.softwerke.practice.app2019.model.Client;
import ru.softwerke.practice.app2019.storage.Storage;
import ru.softwerke.practice.app2019.storage.filter.FilterConditional;
import ru.softwerke.practice.app2019.storage.filter.StorageFilter;
import ru.softwerke.practice.app2019.utils.ParsingUtil;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static ru.softwerke.practice.app2019.utils.MockitoUtils.argThat;

class ClientServiceImplTest {
    private Storage<Client> storage = (Storage<Client>) Mockito.mock(Storage.class);
    private ClientService clientService = new ClientServiceImpl(storage);

    @BeforeEach
    void setUp() {
        Mockito.reset(storage);
    }

    @Test
    void should_correctly_save_device_and_set_id() {
        Client client = new Client("Ivan", "Ivanov", "Ivanovich", LocalDate.now());

        Client saved = clientService.saveClient(client);

        assertNotEquals(0, saved.getId());

        Mockito.verify(storage).save(eq(saved));
    }

    @Test
    void should_return_device_by_id() {
        Client client = new Client("Ivan", "Ivanov", "Ivanovich", LocalDate.now());

        Client saved = clientService.saveClient(client);
        Mockito.when(storage.getById(anyLong())).thenReturn(saved);

        Client actual = clientService.getClientById(saved.getId());

        assertEquals(saved, actual);

        Mockito.verify(storage).save(eq(saved));
        Mockito.verify(storage).getById(eq(saved.getId()));
    }

    @Test
    void should_not_return_device_by_id() {
        Client actual = clientService.getClientById(1);

        assertNull(actual);

        Mockito.verify(storage).getById(1);
    }

    @Test
    void should_correctly_convert_filter() {
        ClientFilter filter = new ClientFilter()
                .withFirstName("A")
                .withLastName("A")
                .withPatronymic("A")
                .withBirthDateFrom(LocalDate.now())
                .withBirthDateTo(LocalDate.now())
                .withBirthDate(LocalDate.now())
                .withSortParams(ParsingUtil.getSortParams("firstName,-lastName"))
                .withCount(10)
                .withPageNumber(0);

        List<Client> clients = clientService.getClients(filter);

        StorageFilter<Client> storageFilter = new StorageFilter<>();
        storageFilter.addCondition(FilterConditional.on(Client::getFirstName).eq(filter.getFirstName()));
        storageFilter.addCondition(FilterConditional.on(Client::getLastName).eq(filter.getLastName()));
        storageFilter.addCondition(FilterConditional.on(Client::getPatronymic).eq(filter.getPatronymic()));
        storageFilter.addCondition(FilterConditional.on(Client::getBirthDate).eq(filter.getBirthDate()));
        storageFilter.addCondition(FilterConditional.on(Client::getBirthDate).inRange(filter.getBirthDateFrom(), filter.getBirthDateTo()));
        storageFilter.addAllSorting(Client.FIELD_PROVIDER, filter.getSortConditionals());
        storageFilter.setCount(filter.getCount());
        storageFilter.setPageNumber(filter.getPageNumber());

        Mockito.verify(storage).get(eq(storageFilter));
        Mockito.verify(storage).get(argThat((it) -> it.getSortings().size() == 2));
    }

}