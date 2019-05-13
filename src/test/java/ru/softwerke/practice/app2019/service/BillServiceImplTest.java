package ru.softwerke.practice.app2019.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.softwerke.practice.app2019.model.Bill;
import ru.softwerke.practice.app2019.model.BillItem;
import ru.softwerke.practice.app2019.storage.Storage;
import ru.softwerke.practice.app2019.storage.filter.FilterConditional;
import ru.softwerke.practice.app2019.storage.filter.StorageFilter;
import ru.softwerke.practice.app2019.storage.filter.sorting.SortConditional;
import ru.softwerke.practice.app2019.utils.ParsingUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static ru.softwerke.practice.app2019.utils.MockitoUtils.argThat;

class BillServiceImplTest {
    private Storage<Bill> storage = (Storage<Bill>) Mockito.mock(Storage.class);
    private BillService billService = new BillServiceImpl(storage);

    @BeforeEach
    void setUp() {
        Mockito.reset(storage);
    }

    @Test
    void should_correctly_save_bill_and_set_id() {
        List<BillItem> billItems = new ArrayList<>();
        billItems.add(new BillItem(1, 2, BigDecimal.valueOf(2020.2)));
        billItems.add(new BillItem(2, 1, BigDecimal.valueOf(2000)));
        Bill bill = new Bill(1, billItems, LocalDateTime.now());

        Bill saved = billService.saveBill(bill);

        assertNotEquals(0, saved.getId());

        Mockito.verify(storage).save(eq(saved));
    }

    @Test
    void should_return_device_by_id() {
        List<BillItem> billItems = new ArrayList<>();
        billItems.add(new BillItem(1, 2, BigDecimal.valueOf(2020.2)));
        billItems.add(new BillItem(2, 1, BigDecimal.valueOf(2000)));
        Bill bill = new Bill(1, billItems, LocalDateTime.now());

        Bill saved = billService.saveBill(bill);
        Mockito.when(storage.getById(anyLong())).thenReturn(saved);

        Bill actual = billService.getBillById(saved.getId());

        assertEquals(saved, actual);

        Mockito.verify(storage).save(eq(saved));
        Mockito.verify(storage).getById(eq(saved.getId()));
    }

    @Test
    void should_correctly_convert_filter() {
        List<Long> deviceIds = ParsingUtil.getDeviceIds("1,2");
        List<SortConditional> sortConditionals = ParsingUtil.getSortParams("totalPrice,-purchaseDateTime,-customerId");

        BillFilter filter = new BillFilter()
                .withClientId(Long.valueOf(1))
                .withDateTime(LocalDateTime.now())
                .withDateTimeFrom(LocalDateTime.now())
                .withDateTimeTo(LocalDateTime.now())
                .withDeviceIds(deviceIds)
                .withTotalPrice(BigDecimal.valueOf(4000))
                .withTotalPriceFrom(BigDecimal.valueOf(2000))
                .withTotalPriceTo(BigDecimal.valueOf(6000))
                .withSortParams(sortConditionals)
                .withCount(10)
                .withPageNumber(0);


        StorageFilter<Bill> storageFilter = new StorageFilter<>();
        storageFilter.addCondition(FilterConditional.on(Bill::getClientId).eq(filter.getClientId()));
        storageFilter.addCondition(FilterConditional.on(Bill::getDateTime).eq(filter.getDateTime()));
        storageFilter.addCondition(FilterConditional.on(Bill::getTotalPrice).eq(filter.getTotalPrice()));
        for(long deviceId :filter.getDeviceIds()) {
            storageFilter.addCondition(FilterConditional.on((Bill it) -> it.containsDevice(deviceId)).eq(true));
        }
        storageFilter.addCondition(FilterConditional.on(Bill::getDateTime).inRange(filter.getDateTimeFrom(),filter.getDateTimeTo()));
        storageFilter.addCondition(FilterConditional.on(Bill::getTotalPrice).inRange(filter.getTotalPriceFrom(),filter.getTotalPriceTo()));
        storageFilter.addAllSorting(Bill.FIELD_PROVIDER,filter.getSortConditionals());
        storageFilter.setCount(filter.getCount());
        storageFilter.setPageNumber(filter.getPageNumber());

        List<Bill> bills = billService.getBills(filter);

        Mockito.verify(storage).get(eq(storageFilter));
        Mockito.verify(storage).get(argThat((it) -> it.getSortings().size() == 3));

    }
}