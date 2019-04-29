package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Bill;
import ru.softwerke.practice.app2019.storage.Storage;
import ru.softwerke.practice.app2019.storage.filter.FilterConditional;
import ru.softwerke.practice.app2019.storage.filter.StorageFilter;
import ru.softwerke.practice.app2019.utils.Identifier;

import java.util.List;

public class BillServiceImpl implements BillService {
    private Storage<Bill> storage;

    public BillServiceImpl(Storage<Bill> storage) {
        this.storage = storage;
    }

    @Override
    public Bill saveBill(Bill bill) {
        int id = Identifier.nextId();
        bill.setId(id);
        storage.save(bill);
        return bill;
    }

    @Override
    public Bill getBillById(int id) {
        return storage.get(id);
    }

    @Override
    public List<Bill> getBills(BillFilter filter) {
        StorageFilter<Bill> storageFilter = new StorageFilter<>();

        if (filter.getClientId() != null){
            storageFilter.addCondition(FilterConditional.on(Bill::getClientId).eq(filter.getClientId()));
        }

        if (filter.getDateTime() != null){
            storageFilter.addCondition(FilterConditional.on(Bill::getDateTime).eq(filter.getDateTime()));
        }

        if (filter.getTotalPrice() != null){
            storageFilter.addCondition(FilterConditional.on(Bill::getTotalPrice).eq(filter.getTotalPrice()));
        }

        if (!filter.getDeviceIds().isEmpty()){
            for (int deviceId : filter.getDeviceIds()){
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
