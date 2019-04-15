package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Bill;
import ru.softwerke.practice.app2019.storage.Storage;
import ru.softwerke.practice.app2019.storage.filter.FilterConditional;
import ru.softwerke.practice.app2019.storage.filter.StorageFilter;

import java.util.List;
import java.util.UUID;

public class BillDataServiceImpl implements BillDataService{
    private Storage<Bill> storage;

    public BillDataServiceImpl(Storage<Bill> storage) {
        this.storage = storage;
    }

    @Override
    public Bill saveBill(Bill bill) {
        UUID id = UUID.randomUUID();
        bill.setId(id);
        storage.save(bill);
        return bill;
    }

    @Override
    public Bill getBillById(UUID id) {
        return storage.get(id);
    }

    @Override
    public List<Bill> getBills(BillFilter filter) {
        StorageFilter<Bill> storageFilter = new StorageFilter<>();

        if (filter.getClientId() != null){
            storageFilter.addCondition(FilterConditional.on(Bill::getClientId).eq(filter.getClientId()));
        }

        if (!filter.getDeviceIds().isEmpty()){
            for (UUID deviceId : filter.getDeviceIds()){
                storageFilter.addCondition(FilterConditional.on((Bill it) -> it.containsDevice(deviceId)).eq(true));
            }

        }

        storageFilter.addCondition(FilterConditional.on(Bill::getDateTime).inRange(filter.getDateTimeFrom(), filter.getDateTimeTo()));
        storageFilter.addCondition(FilterConditional.on(Bill::getTotalPrice).inRange(filter.getTotalPriceFrom(), filter.getTotalPriceTo()));

        storageFilter.addAllSorting(Bill.FIELD_PROVIDER, filter.getSortConditionals());
        storageFilter.setCount(filter.getCount());
        storageFilter.setPageNumber(filter.getPageNumber());
        return storage.get(storageFilter);
    }
}
