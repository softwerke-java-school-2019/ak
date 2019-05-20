package ru.softwerke.practice.app2019.controller.rest.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.softwerke.practice.app2019.model.Bill;
import ru.softwerke.practice.app2019.service.*;
import ru.softwerke.practice.app2019.utils.ModelUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyObject;

class BillWebHandlerTest {
    private BillService billService = Mockito.mock(BillServiceImpl.class);
    private ClientService clientService = Mockito.mock(ClientServiceImpl.class);
    private DeviceService deviceService = Mockito.mock(DeviceServiceImpl.class);
    private BillWebHandler billWebHandler = new BillWebHandler(billService, clientService, deviceService);

    @Test
    void should_correctky_save_bill(){
        Bill saved = ModelUtils.bill();

        Bill bill = billWebHandler.createBill(saved);



    }

}