package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Bill;

import java.util.List;
import java.util.UUID;

public interface BillDataService {
    Bill saveBill(Bill bill);

    Bill getBillById(UUID id);

    List<Bill> getBills(BillFilter filter);

    default List<Bill> getBills(){
        return getBills(BillFilter.EMPTY);
    }
}
