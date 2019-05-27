package ru.softwerke.practice.app2019.controller.rest.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.softwerke.practice.app2019.model.Bill;
import ru.softwerke.practice.app2019.model.BillItem;
import ru.softwerke.practice.app2019.model.Client;
import ru.softwerke.practice.app2019.service.*;
import ru.softwerke.practice.app2019.utils.ModelUtils;
import ru.softwerke.practice.app2019.utils.ModelValidator;
import ru.softwerke.practice.app2019.utils.QueryValidator;

import javax.ws.rs.WebApplicationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.*;

class BillWebHandlerTest {
    private BillService billService = Mockito.mock(BillServiceImpl.class);
    private ClientService clientService = Mockito.mock(ClientServiceImpl.class);
    private DeviceService deviceService = Mockito.mock(DeviceServiceImpl.class);
    private BillWebHandler billWebHandler = new BillWebHandler(billService, clientService, deviceService);

    @Test
    void should_correctly_save_bill(){
        Bill saved = ModelUtils.bill();
        Client client = new Client("Ivan", "Ivanov", "Ivanovich", LocalDate.now());
        Mockito.when(clientService.getClientById(anyLong())).thenReturn(client);
        Mockito.when(deviceService.getDeviceById(anyLong())).thenReturn(ModelUtils.device());
        Mockito.when(billService.saveBill(anyObject())).thenReturn(saved);

        Bill actual = billWebHandler.createBill(saved);

        Mockito.verify(clientService).getClientById(eq(saved.getClientId()));

        for (BillItem billItem : saved.getBillItems()){
            Mockito.verify(deviceService).getDeviceById(eq(billItem.getDeviceId()));
        }
        Mockito.verify(billService).saveBill(eq(saved));

        assertEquals(saved, actual);
    }

    @Test
    void should_not_save_bill_with_not_found_client(){
        Bill saved = ModelUtils.bill();
        Mockito.when(deviceService.getDeviceById(anyLong())).thenReturn(ModelUtils.device());
        Mockito.when(billService.saveBill(anyObject())).thenReturn(saved);

        assertFalse(try_create_bill(saved));
    }

    @Test
    void should_not_save_bill_with_not_found_device(){
        Bill saved = ModelUtils.bill();
        Client client = new Client("Ivan", "Ivanov", "Ivanovich", LocalDate.now());
        Mockito.when(clientService.getClientById(anyLong())).thenReturn(client);
        Mockito.when(billService.saveBill(anyObject())).thenReturn(saved);

        assertFalse(try_create_bill(saved));
    }

    @Test
    void should_not_save_bill_with_empty_body(){
        assertFalse(try_create_bill(null));
    }

    private boolean try_create_bill(Bill saved){
        try{
            Bill actual = billWebHandler.createBill(saved);
            return true;
        }catch(WebApplicationException e) {
            return false;
        }
    }

}